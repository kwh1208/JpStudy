package jp.study.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicBoolean;

@Getter
@Setter
public class Katakana {

    String jp;

    String kr;

    String en;

    String ans;

    AtomicBoolean correct = new AtomicBoolean(false);

    public boolean getCorrectValue() {
        return correct.get();
    }
}
