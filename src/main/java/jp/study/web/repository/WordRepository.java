package jp.study.web.repository;

import jp.study.domain.Hiragana;
import jp.study.domain.Word;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerArray;

@Repository
public class WordRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Word> rowMapper;

    public WordRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        rowMapper = (rs, rowNum) -> {
            Word word = new Word();
            word.setKr(rs.getString("kr"));
            word.setHiragana(rs.getString("hiragana"));
            word.setKanji(rs.getString("kanji"));
            return word;
        };
    }

    public List<Word> read(){
        return jdbcTemplate.query("SELECT * FROM WORD ORDER BY RAND() LIMIT 10;", rowMapper);
    }

}
