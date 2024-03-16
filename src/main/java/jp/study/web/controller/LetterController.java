package jp.study.web.controller;

import jakarta.servlet.http.HttpSession;
import jp.study.domain.Hiragana;
import jp.study.domain.Katakana;
import jp.study.domain.Result;
import jp.study.web.service.LetterService;
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
public class LetterController {

    private final LetterService service;

    /*
    히라가나 한국어로 쓰기
     */

    @GetMapping("/hiragana/kr")
    String hiragana_kr(Model model, HttpSession session){
        List<Hiragana> hiraganas = service.getHiragana();
        session.setAttribute("hiraganas", hiraganas);
        List<Hiragana> hiragana1 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            hiragana1.add(hiraganas.get(i));
        }
        List<Hiragana> hiragana2 = new ArrayList<>();
        for (int i = 5; i < 10; i++) {
            hiragana2.add(hiraganas.get(i));
        }
        model.addAttribute("hiragana1", hiragana1);
        model.addAttribute("hiragana2", hiragana2);
        return "starter/hiragana_kr";
    }

    @PostMapping("/hiragana/kr")
    String hiragana_kr_post(@RequestParam String[] ans, HttpSession session, Model model){
        List<String> answer = Arrays.stream(ans).toList();
        ArrayList<Result> results = new ArrayList<>();
        List<Hiragana> hiraganas = (List<Hiragana>)session.getAttribute("hiraganas");
        int score = 0;
        for (int i = 0; i < 10; i++) {
            Result result = new Result();
            result.setQuestion(hiraganas.get(i).getJp());
            String tmp = answer.get(i);
            if (answer.get(i).isBlank()){
                tmp = "못 푼 문제입니다.";
            }
            result.setSubmit(tmp);
            result.setAnswer(hiraganas.get(i).getKr());
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
        return "starter/starter_result";
    }


    /*
    가타카나 한국어로 쓰기
     */

    @GetMapping("/katakana/kr")
    String katakana_kr(Model model, HttpSession session){
        List<Katakana> katakanas = service.getKatakana();
        session.setAttribute("katakanas", katakanas);
        List<Katakana> katakana1 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            katakana1.add(katakanas.get(i));
        }
        List<Katakana> katakana2 = new ArrayList<>();
        for (int i = 5; i < 10; i++) {
            katakana2.add(katakanas.get(i));
        }
        model.addAttribute("katakana1", katakana1);
        model.addAttribute("katakana2", katakana2);
        return "starter/katakana_kr";
    }



    @PostMapping("/katakana/kr")
    String katakana_kr_post(@RequestParam String[] ans, HttpSession session, Model model){
        List<String> answer = Arrays.stream(ans).toList();
        ArrayList<Result> results = new ArrayList<>();
        List<Katakana> Katakanas = (List<Katakana>)session.getAttribute("katakanas");
        int score = 0;
        for (int i = 0; i < 10; i++) {
            Result result = new Result();
            result.setQuestion(Katakanas.get(i).getJp());
            String tmp = answer.get(i);
            if (answer.get(i).isBlank()){
                tmp = "못 푼 문제입니다.";
            }
            result.setSubmit(tmp);
            result.setAnswer(Katakanas.get(i).getKr());
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
        return "starter/starter_result";
    }


}