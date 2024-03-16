package jp.study.web.service;

import jp.study.domain.Sentence;
import jp.study.web.repository.SentenceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class SentenceService {
    private final GPTService gptService;
    private final SentenceRepository sentenceRepository;

    public List<Sentence> sendToGPTKR(List<String> answer, List<Sentence> sentences_quiz){
        String gptMessage = "";

        for (int i = 0; i < sentences_quiz.size(); i++) {
            gptMessage = createGptMessageKR(sentences_quiz.get(i).getJp(), answer.get(i), i + 1, gptMessage);
        }

        String result = gptService.sendMessageKR(gptMessage);

        List<Sentence> sentences = analyzeResult(result);


        for (int i = 0; i < sentences_quiz.size(); i++) {
            sentences.get(i).setJp(sentences_quiz.get(i).getJp());
            sentences.get(i).setKr(answer.get(i));
        }

        return sentences;

    }

    private String createGptMessageJP(String jp, String kr, Integer num, String gptMessage){

        gptMessage+=" "+num+". " + kr +" \\n"+" reply: "+jp+"\\n";

        return gptMessage;

    }

    public List<Sentence> sendToGPTJP(List<String> answer, List<Sentence> sentences_quiz){
        String gptMessage = "";

        for (int i = 0; i < sentences_quiz.size(); i++) {
            gptMessage = createGptMessageJP(sentences_quiz.get(i).getKr(), answer.get(i), i + 1, gptMessage);
        }


        String result = gptService.sendMessageJP(gptMessage);


        List<Sentence> sentences = analyzeResult(result);


        for (int i = 0; i < sentences_quiz.size(); i++) {
            sentences.get(i).setKr(sentences_quiz.get(i).getJp());
            sentences.get(i).setJp(answer.get(i));
        }


        return sentences;

    }




    public List<Sentence> getSentence(){
        return sentenceRepository.read();
    }






    /*

     */


    private String createGptMessageKR(String jp, String kr, Integer num, String gptMessage){

        gptMessage+=" "+num+". " + jp +" \\n"+" reply: "+kr+"\\n";

        return gptMessage;

    }



    private List<Sentence> analyzeResult(String result){
        List<Sentence> sentences = new ArrayList<>();
        String[] parts = result.split("\\d");
        for (String part : parts) {
            Sentence tmp = new Sentence();
            if (part.contains("O")){
                tmp.setCorrectValue(true);
            }
            else if (part.contains("X")){
                String correctionResult = null;

                correctionResult = part.replaceAll("correction", "").replaceAll("Correction", "").replaceAll("\\\\n", "").replaceAll(":", "")
                                .replaceAll("X", "").replaceAll("\\.", "");

                tmp.setCorrectValue(false);
                tmp.setCorrection(correctionResult);
            }

            sentences.add(tmp);
        }
        sentences.remove(0);

        return sentences;
    }
}
