package jp.study.web.controller;

import jakarta.servlet.http.HttpSession;
import jp.study.domain.Dialog;
import jp.study.web.service.DialogService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class DialogController {

    private final DialogService service;

    @GetMapping("/dialog/jp")
    String dialog_jp(Model model){
        List<Dialog> dialogs = service.getDialog();
        model.addAttribute("dialog", dialogs);
        return "dialog/jp";
    }

    @PostMapping("/dialog/jp")
    String dialog_jp_post(@RequestParam String situation, Model model){
        log.info("situation={}", situation);
        String[] split = situation.split("/");
        Dialog dialog = new Dialog();
        dialog.setSituation(split[0]);
        dialog.setUserRole(split[1]);
        dialog.setBotRole(split[2]);

        String message = service.sendToGPT(dialog.getSituation(), dialog.getUserRole(), dialog.getBotRole());
        model.addAttribute("dialog", dialog);
        model.addAttribute("message", message);
        return "/dialog/result";
    }

    @GetMapping("/dialog/speak")
    String dialog_speak(Model model){
        List<Dialog> dialogs = service.getDialog();
        model.addAttribute("dialog", dialogs);
        return "dialog/speak";
    }

    @PostMapping("/dialog/speak")
    String dialog_speak_post(@RequestParam String situation, Model model){
        log.info("situation={}", situation);
        String[] split = situation.split("/");
        Dialog dialog = new Dialog();
        dialog.setSituation(split[0]);
        dialog.setUserRole(split[1]);
        dialog.setBotRole(split[2]);

        String message = service.sendToGPT(dialog.getSituation(), dialog.getUserRole(), dialog.getBotRole());
        model.addAttribute("dialog", dialog);
        model.addAttribute("message", message);
        return "dialog/resultSpeak";
    }

    @ResponseBody
    @PostMapping("/dialog/message")
    String dialog_message(@RequestBody DialogRequest result){
        log.info("result = {}, {}, {}", result.situation, result.getUserRole(), result.getBotRole());

        return service.sendToGPT(result.getSituation(), result.getUserRole(), result.getBotRole(), result.getMessages());
    }

    @Getter
    @Setter
    private static class DialogRequest{
        String situation;
        String userRole;
        String botRole;
        String[] messages;

        public DialogRequest() {
        }
    }

}
