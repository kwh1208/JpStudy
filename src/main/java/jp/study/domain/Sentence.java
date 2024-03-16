package jp.study.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicBoolean;

@Getter
@Setter
public class Sentence {

    String jp;

    String kr;

    String correction;

    AtomicBoolean correct = new AtomicBoolean(false);


    public boolean getCorrectValue() {
        return correct.get();
    }

    public void setCorrectValue(boolean tmp) {
        correct.set(tmp);
    }

}
