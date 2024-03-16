package jp.study.web.controller;

import jakarta.servlet.http.HttpSession;
import jp.study.domain.Result;
import jp.study.domain.Sentence;
import jp.study.web.service.SentenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class SentenceController {
    private final SentenceService service;

    /*
    일본어 한국어로 번역
     */


    @GetMapping("/sentence/kr")
    String sentence_kr(Model model, HttpSession session){
        getQuiz(model, session);
        return "sentence/kr";
    }



    @PostMapping("/sentence/kr")
    String sentence_kr_post(@RequestParam String[] ans, HttpSession session, Model model){
        List<String> answer = new ArrayList<>(Arrays.stream(ans).toList());
        ArrayList<Result> results = new ArrayList<>();
        int score = 0;
        for (int i = 0; i < answer.size(); i++) {
            if (answer.get(i).isEmpty()){
                answer.set(i, "못푼 문제입니다.");
            }
        }
        List<Sentence> sentences_quiz = (List<Sentence>)session.getAttribute("sentences");
        List<Sentence> sentences = service.sendToGPTKR(answer, sentences_quiz);

        for (int i = 0; i < 10; i++) {
            Result result = new Result();
            result.setQuestion(sentences.get(i).getJp());
            result.setSubmit(answer.get(i));
            if (sentences.get(i).getCorrectValue()){
                result.setAnswer("");
                result.setCorrect(true);
                score++;
            }
            else {
                result.setAnswer(sentences.get(i).getCorrection());
                result.setCorrect(false);
            }
            results.add(result);
        }
        ArrayList<Result> result1 = new ArrayList<>();
        ArrayList<Result> result2 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            result1.add(results.get(i));
        }
        for (int i = 0; i < 5; i++) {
            result2.add(results.get(i+5));
        }

        model.addAttribute("result1", result1);
        model.addAttribute("result2", result2);
        model.addAttribute("score", score);
        session.invalidate();

        return "sentence/resultKr";
    }
    
    /*
    일본어 작문
     */

    @GetMapping("/sentence/jp")
    String sentence_jp(Model model, HttpSession session){
        getQuiz(model, session);
        return "sentence/jp";
    }

    @PostMapping("/sentence/jp")
    String sentence_jp_post(@RequestParam String[] ans, HttpSession session, Model model){
        List<String> answer = new ArrayList<>(Arrays.stream(ans).toList());
        ArrayList<Result> results = new ArrayList<>();
        int score = 0;
        for (int i = 0; i < answer.size(); i++) {
            if (answer.get(i).isEmpty()){
                answer.set(i, "못푼 문제입니다.");
            }
        }
        List<Sentence> sentences_quiz = (List<Sentence>)session.getAttribute("sentences");
        List<Sentence> sentences = service.sendToGPTJP(answer, sentences_quiz);

        for (int i = 0; i < 10; i++) {
            Result result = new Result();
            result.setQuestion(sentences.get(i).getKr());
            result.setSubmit(answer.get(i));
            if (sentences.get(i).getCorrectValue()){
                result.setAnswer("");
                result.setCorrect(true);
                score++;
            }
            else {
                result.setAnswer(sentences.get(i).getCorrection());
                result.setCorrect(false);
            }
            results.add(result);
        }
        ArrayList<Result> result1 = new ArrayList<>();
        ArrayList<Result> result2 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            result1.add(results.get(i));
        }
        for (int i = 0; i < 5; i++) {
            result2.add(results.get(i+5));
        }

        model.addAttribute("result1", result1);
        model.addAttribute("result2", result2);
        model.addAttribute("score", score);
        session.invalidate();

        return "sentence/resultJp";
    }


    /*
    일본어 듣기
     */
    @GetMapping("/sentence/listen")
    String sentence_listen(Model model, HttpSession session){
        getQuiz(model, session);
        return "sentence/listen";
    }

    @PostMapping("/sentence/listen")
    String sentence_listen_post(@RequestParam String[] ans, HttpSession session, Model model){
        List<String> answer = new ArrayList<>(Arrays.stream(ans).toList());
        ArrayList<Result> results = new ArrayList<>();
        int score = 0;
        for (int i = 0; i < answer.size(); i++) {
            if (answer.get(i).isEmpty()){
                answer.set(i, "못푼 문제입니다.");
            }
        }
        List<Sentence> sentences_quiz = (List<Sentence>)session.getAttribute("sentences");
        List<Sentence> sentences = service.sendToGPTKR(answer, sentences_quiz);

        for (int i = 0; i < 10; i++) {
            Result result = new Result();
            result.setQuestion(sentences.get(i).getJp());
            result.setSubmit(answer.get(i));
            if (sentences.get(i).getCorrectValue()){
                result.setAnswer("");
                result.setCorrect(true);
                score++;
            }
            else {
                result.setAnswer(sentences.get(i).getCorrection());
                result.setCorrect(false);
            }
            results.add(result);
        }
        ArrayList<Result> result1 = new ArrayList<>();
        ArrayList<Result> result2 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            result1.add(results.get(i));
        }
        for (int i = 0; i < 5; i++) {
            result2.add(results.get(i+5));
        }

        model.addAttribute("result1", result1);
        model.addAttribute("result2", result2);
        model.addAttribute("score", score);
        session.invalidate();

        return "sentence/resultListen";
    }
    /*
    말하기
     */

    @GetMapping("/sentence/speak")
    String sentence_speak(Model model, HttpSession session){
        getQuiz(model, session);
        return "sentence/speak";
    }

    @PostMapping("/sentence/speak")
    String sentence_speak_post(@RequestParam String[] ans, HttpSession session, Model model){
        List<String> answer = new ArrayList<>(Arrays.stream(ans).toList());
        ArrayList<Result> results = new ArrayList<>();
        int score = 0;
        for (int i = 0; i < answer.size(); i++) {
            if (answer.get(i).isEmpty()){
                answer.set(i, "못푼 문제입니다.");
            }
        }
        List<Sentence> sentences_quiz = (List<Sentence>)session.getAttribute("sentences");
        List<Sentence> sentences = service.sendToGPTKR(answer, sentences_quiz);

        for (int i = 0; i < 10; i++) {
            Result result = new Result();
            result.setQuestion(sentences.get(i).getJp());
            result.setSubmit(answer.get(i));
            if (sentences.get(i).getCorrectValue()){
                result.setAnswer("");
                result.setCorrect(true);
                score++;
            }
            else {
                result.setAnswer(sentences.get(i).getCorrection());
                result.setCorrect(false);
            }
            results.add(result);
        }
        ArrayList<Result> result1 = new ArrayList<>();
        ArrayList<Result> result2 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            result1.add(results.get(i));
        }
        for (int i = 0; i < 5; i++) {
            result2.add(results.get(i+5));
        }

        model.addAttribute("result1", result1);
        model.addAttribute("result2", result2);
        model.addAttribute("score", score);
        session.invalidate();

        return "sentence/resultSpeak";
    }


    private void getQuiz(Model model, HttpSession session) {
        List<Sentence> sentences = service.getSentence();
        session.setAttribute("sentences", sentences);
        List<Sentence> sentence1 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            sentence1.add(sentences.get(i));
        }
        List<Sentence> sentence2 = new ArrayList<>();
        for (int i = 5; i < 10; i++) {
            sentence2.add(sentences.get(i));
        }
        model.addAttribute("sentence1", sentence1);
        model.addAttribute("sentence2", sentence2);

    }



}