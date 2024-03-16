package jp.study.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result {
    String question;

    String question2;

    String submit;

    String answer;

    String answer2;

    boolean correct;

    public boolean checkAns(){
        return answer.equals(submit);
    }

}
