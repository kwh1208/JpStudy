package jp.study.web.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class GPTService {

    @Value("${apiUrl}")
    private String apiUrl;
    @Value("${apiKey}")
    private String apiKey;

    public String sendMessageKR(String message){
        String requestBody = "{\n" +
                "    \"model\": \"ft:gpt-3.5-turbo-1106:personal::8ksfQB9C\",\n" +
                "    \"messages\": [{\"role\": \"system\", \"content\": \"You are a translation checker from Japanese to Korean. If the translation is correct, send only the O mark with numbering. If the translation is wrong, please send only the correct interpretation after 'Correction:' with numbering and X mark.\"}, \n" +
                "    {\"role\": \"user\", \"content\":\""+" Check that the translation of the following sentence is correct. These are sentences. "+ message + "\"}]\n}";

        // HTTP 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + apiKey);

        // HTTP 요청 엔터티 생성
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // RestTemplateBuilder를 사용하여 RestTemplate 생성
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        RestTemplate restTemplate = restTemplateBuilder.build();

        // GPT API 호출 및 응답 받기
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        // API 응답 데이터 파싱
        String responseBody = responseEntity.getBody();
        return parseAssistantResponse(responseBody);
    }

    public String sendMessageJP(String message){
        String requestBody = "{\n" +
                "    \"model\": \"ft:gpt-3.5-turbo-1106:personal::8ksfQB9C\",\n" +
                "    \"messages\": [{\"role\": \"system\", \"content\": \"You are a translation checker from Korean to Japanese. If the translation is correct, send only the O mark with numbering. If the translation is wrong, send only the correct interpretation after 'Correction:' with numbering and X mark. \"}, \n" +
                "    {\"role\": \"user\", \"content\":\""+" Check that the translation of the following sentence is correct. These are sentences. "+ message + "\"}]\n}";

        // HTTP 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + apiKey);

        // HTTP 요청 엔터티 생성
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // RestTemplateBuilder를 사용하여 RestTemplate 생성
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        RestTemplate restTemplate = restTemplateBuilder.build();

        // GPT API 호출 및 응답 받기
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        // API 응답 데이터 파싱
        String responseBody = responseEntity.getBody();
        return parseAssistantResponse(responseBody);
    }

    public String sendMessageDialogStart(String situation, String userRole, String botRole){
        String requestBody = "{\n" +
                "    \"model\": \"gpt-3.5-turbo\",\n" +
                "    \"messages\": [{\"role\": \"system\", \"content\": \"you are helpful assitant supporting peple learning Japanese. " +
                "      your name is RolePlayingBot. " +
                "      Please assume that the user you are assisting is a advanced in Japanese. " +
                "      And please write only the sentence without the charcter role. " +
                "      Let's have a conversation in Japanese. please answer in Japanese only without providing a translation. And please don't write down the pronunciation either. " +
                "      Let us assume that the situation in "+situation+". I am "+userRole+". The character i want you act as is "+botRole+". Please make sure that I'm a advanced in Japanese. Now, start a conversation with the first sentence! \"}]}";

        // HTTP 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + apiKey);

        // HTTP 요청 엔터티 생성
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // RestTemplateBuilder를 사용하여 RestTemplate 생성
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        RestTemplate restTemplate = restTemplateBuilder.build();

        // GPT API 호출 및 응답 받기
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        // API 응답 데이터 파싱
        String responseBody = responseEntity.getBody();
        return parseAssistantResponse(responseBody);
    }

    public String sendMessageDialogProceeding(String situation, String userRole, String botRole, String message){
        String requestBody = "{\n" +
                "    \"model\": \"gpt-3.5-turbo\",\n" +
                "    \"messages\": [{\"role\": \"system\", \"content\": \"you are helpful assitant supporting peple learning Japanese. your name is RolePlayingBot. Please assume that the user you are assisting is a advanced in Japanese. And please write only the sentence without the charcter role. please answer in Japanese only without providing a translation. And please don't write down the pronunciation either. And We were in the middle of a conversation."+
                "      Let us assume that the situation in "+situation+". I am "+userRole+". The character i want you act as is "+botRole+". And These are conversations we've had so far. \"}, \n" +
                "    {\"role\": \"user\", \"content\":\""+" "+ message + "\"}]\n}";

        // HTTP 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + apiKey);

        // HTTP 요청 엔터티 생성
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // RestTemplateBuilder를 사용하여 RestTemplate 생성
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        RestTemplate restTemplate = restTemplateBuilder.build();

        // GPT API 호출 및 응답 받기
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        // API 응답 데이터 파싱
        String responseBody = responseEntity.getBody();
        return parseAssistantResponse(responseBody);
    }

    private String parseAssistantResponse(String responseBody) {
        return responseBody.split("\"content\": \"")[1].split("\"")[0];
    }
}