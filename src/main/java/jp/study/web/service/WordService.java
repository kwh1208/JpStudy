package jp.study.web.service;

import jp.study.domain.Word;
import jp.study.web.repository.WordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicIntegerArray;

@Service
@RequiredArgsConstructor
public class WordService {
    private final WordRepository wordRepository;


    public List<Word> getWord(){
        return wordRepository.read();
    }
}