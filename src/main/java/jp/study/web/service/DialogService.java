package jp.study.web.service;

import jp.study.domain.Dialog;
import jp.study.web.repository.DialogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DialogService {
    private final GPTService gptService;
    private final DialogRepository repository;
    public String sendToGPT(String situation, String userRole, String botRole){
        return gptService.sendMessageDialogStart(situation, userRole, botRole);
    }

    public String sendToGPT(String situation, String userRole, String botRole, String[] message){
        return gptService.sendMessageDialogProceeding(situation, userRole, botRole, createDialog(message));
    }

    private String createDialog(String[] messages) {
        StringBuilder tmp = new StringBuilder();

        for (String message : messages) {
            tmp.append(message).append("\\n ");
        }

        return tmp.toString();
    }

    public List<Dialog> getDialog(){
        return repository.read();
    }
}

