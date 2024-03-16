package jp.study.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicBoolean;

@Getter
@Setter
public class Word {
    String kr;

    String hiragana;

    String kanji;

    String ans;

    AtomicBoolean correct = new AtomicBoolean(false);

    public boolean getCorrectValue() {
        return correct.get();
    }
}
