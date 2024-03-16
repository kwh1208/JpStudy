package jp.study.web.service;

import jp.study.domain.Hiragana;
import jp.study.domain.Katakana;
import jp.study.web.repository.HiraganaRepository;
import jp.study.web.repository.KatakanaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicIntegerArray;

@Service
@RequiredArgsConstructor
public class LetterService {
    private final HiraganaRepository hiraganaRepository;
    private final KatakanaRepository katakanaRepository;


    public List<Hiragana> getHiragana() {

        return hiraganaRepository.read();
    }

    public List<Katakana> getKatakana() {

        return katakanaRepository.read();
    }
}
