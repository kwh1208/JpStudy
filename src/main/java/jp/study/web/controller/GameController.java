package jp.study.web.controller;

import jakarta.servlet.http.HttpSession;
import jp.study.domain.Word;
import jp.study.web.repository.WordRepository;
import jp.study.web.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class GameController {

    private final GameService gameService;

    private final WordRepository repository;


    /*
    끝말잇기, 스무고개
     */

    @GetMapping("/game")
    public String game(){
        return "/game/main";
    }

    @GetMapping("/game/wordChain")
    public String wordChain(){
        return "/game/wordChain";
    }

    @ResponseBody
    @PostMapping("/game/wordChain")
    public String wordChain_post(@RequestBody String word){
        return gameService.wordChain(word);
    }

    @GetMapping("/game/twentyQuestion")
    public String twentyQuestions(HttpSession session, Model model){
        List<Word> words = repository.read();
        for (Word word : words) {
            if (word.getKanji()==null) continue;

            String tmp = word.getHiragana()+word.getKanji();
            session.setAttribute("word", tmp);
            model.addAttribute("answer", tmp);
        }
        return "/game/twentyQuestion";
    }

    @ResponseBody
    @PostMapping("/game/twentyQuestion")
    public String twentyQuestions_post(@RequestBody String question, HttpSession session){
        String word = (String) session.getAttribute("word");
        return gameService.twentyQuestions(question, word);
    }
}