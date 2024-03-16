package jp.study.web.controller;

import jakarta.servlet.http.HttpSession;
import jp.study.domain.Hiragana;
import jp.study.domain.Result;
import jp.study.domain.Word;
import jp.study.web.service.WordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class WordController {

    private final WordService service;
    /*
    일본어보고 한국어로
     */
    @GetMapping("/word/kr")
    public String wordJp(Model model, HttpSession session){
        getWord(model, session);
        return "word/kr";
    }



    @PostMapping("/word/kr")
    public String wordJp_post(@RequestParam String[] ans, HttpSession session, Model model){
        List<String> answer = Arrays.stream(ans).toList();
        ArrayList<Result> results = new ArrayList<>();
        List<Word> words = (List<Word>)session.getAttribute("words");
        int score = 0;
        for (int i = 0; i < 10; i++) {
            Result result = new Result();
            if (words.get(i).getKanji()!=null){
            result.setQuestion(words.get(i).getKanji());
            }
            else {
                result.setQuestion("");
            }
            result.setQuestion2(words.get(i).getHiragana());
            String tmp = answer.get(i);
            if (answer.get(i).isBlank()){
                tmp = "못 푼 문제입니다.";
            }
            result.setSubmit(tmp);
            result.setAnswer(words.get(i).getKr());
            if (result.checkAns()){
                score++;
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
        return "word/resultKr";
    }

    /*
    한국어보고 일본어로
     */
    @GetMapping("/word/jp")
    public String wordKo(Model model, HttpSession session){
        getWord(model, session);
        return "word/jp";
    }



    @PostMapping("/word/jp")
    public String wordKo_post(@RequestParam String[] ans, HttpSession session, Model model){
        List<String> answer = Arrays.stream(ans).toList();
        ArrayList<Result> results = new ArrayList<>();
        List<Word> words = (List<Word>)session.getAttribute("words");
        int score = 0;
        for (int i = 0; i < 10; i++) {
            Result result = new Result();
            if (words.get(i).getKanji()!=null){
                result.setQuestion(words.get(i).getKanji());
            }
            else {
                result.setQuestion("");
            }
            result.setQuestion2(words.get(i).getHiragana());
            String tmp = answer.get(i);
            if (answer.get(i).isBlank()){
                tmp = "못 푼 문제입니다.";
            }
            result.setSubmit(tmp);
            result.setAnswer(words.get(i).getKr());
            if (result.checkAns()){
                score++;
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
        return "word/resultJp";
    }
    /*
    일본어보고 읽기
     */

    @GetMapping("/word/speak")
    public String wordSpeak(Model model, HttpSession session){
        getWord(model, session);
        return "word/speak";
    }

    @PostMapping("/word/speak")
    public String word_speak_post(@RequestParam String[] ans, HttpSession session, Model model){
        List<String> answer = Arrays.stream(ans).toList();
        ArrayList<Result> results = new ArrayList<>();
        List<Word> words = (List<Word>)session.getAttribute("words");
        int score = 0;
        for (int i = 0; i < 10; i++) {
            Result result = new Result();
            if (words.get(i).getKanji()!=null){
                result.setQuestion(words.get(i).getKanji());
            }
            else {
                result.setQuestion("");
            }
            result.setQuestion2(words.get(i).getHiragana());
            String tmp = answer.get(i);
            if (answer.get(i).isBlank()){
                tmp = "못 푼 문제입니다.";
            }
            result.setSubmit(tmp);
            result.setAnswer(words.get(i).getKr());
            if (result.checkAns()){
                score++;
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
        return "word/resultSpeak";
    }

    /*
    일본어듣고 맞추기
     */

    @GetMapping("/word/listen")
    public String wordListen(Model model, HttpSession session){
        getWord(model, session);
        return "word/listen";
    }

    @PostMapping("/word/listen")
    public String wordListen_post(@RequestParam String[] ans, HttpSession session, Model model){
        List<String> answer = Arrays.stream(ans).toList();
        ArrayList<Result> results = new ArrayList<>();
        List<Word> words = (List<Word>)session.getAttribute("words");
        int score = 0;
        for (int i = 0; i < 10; i++) {
            Result result = new Result();
            if (words.get(i).getKanji()!=null){
                result.setQuestion(words.get(i).getKanji());
            }
            else {
                result.setQuestion("");
            }
            result.setQuestion2(words.get(i).getHiragana());
            String tmp = answer.get(i);
            if (answer.get(i).isBlank()){
                tmp = "못 푼 문제입니다.";
            }
            result.setSubmit(tmp);
            result.setAnswer(words.get(i).getKr());
            if (result.checkAns()){
                score++;
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
        return "word/resultListen";
    }

    private void getWord(Model model, HttpSession session) {
        List<Word> words = service.getWord();
        session.setAttribute("words", words);
        List<Word> word1 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            word1.add(words.get(i));
        }
        List<Word> word2 = new ArrayList<>();
        for (int i = 5; i < 10; i++) {
            word2.add(words.get(i));
        }
        model.addAttribute("word1", word1);
        model.addAttribute("word2", word2);
    }
}
