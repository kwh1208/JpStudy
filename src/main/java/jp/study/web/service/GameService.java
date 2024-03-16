package jp.study.web.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GameService {
    @Value("${apiUrl}")
    private String apiUrl;
    @Value("${apiKey}")
    private String apiKey;

    public String wordChain(String word){
        String requestBody = "{\n" +
                "        \"model\": \"gpt-3.5-turbo\",\n" +
                "            \"messages\": [{\"role\": \"system\", \"content\": \"you are helpful assitant supporting peple learning Japanese. You are a Word Chain Gamebot. Continue the word chain without adding any other words. And please answer the word only in hiragana. And Don't say words that end in a 'ん'. And please don't write down the pronunciation either.\"},\n" +
                "        {\"role\": \"user\", \"content\": \""+word+" \"}]\n" +
                "    }";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        RestTemplate restTemplate = restTemplateBuilder.build();

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        String responseBody = responseEntity.getBody();
        return parseAssistantResponse(responseBody);
    }

    public String twentyQuestions(String question, String word){
        String requestBody = "{\n" +
                "    \"model\": \"gpt-3.5-turbo\",\n" +
                "    \"messages\": [{\"role\": \"system\", \"content\": \"you are helpful assitant supporting peple learning Japanese. You are a twenty questions Gamebot. The answer is the '"+word+"'. your role is answering questions. Explain the questions I ask based on the word. I'll ask you a question from now on.\"}, \n" +
                "    {\"role\": \"user\", \"content\":\""+question+"\"}]\n" +
                "}";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        RestTemplate restTemplate = restTemplateBuilder.build();

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        String responseBody = responseEntity.getBody();
        String result = parseAssistantResponse(responseBody);
        if (result.contains(word)){
            result.replaceAll(word, "これ");
        }
        return result;
    }


    private String parseAssistantResponse(String responseBody) {
        return responseBody.split("\"content\": \"")[1].split("\"")[0];
    }
}
