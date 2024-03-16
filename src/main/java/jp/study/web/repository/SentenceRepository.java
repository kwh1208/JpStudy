package jp.study.web.repository;

import jp.study.domain.Hiragana;
import jp.study.domain.Sentence;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerArray;

@Repository
@Slf4j
public class SentenceRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Sentence> rowMapper;

    public SentenceRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        rowMapper = (rs, rowNum) -> {
            Sentence sentence = new Sentence();
            sentence.setJp(rs.getString("jp"));
            sentence.setKr(rs.getString("kr"));
            return sentence;
        };
    }


    public List<Sentence> read(){

        return jdbcTemplate.query("SELECT * FROM sentence ORDER BY RAND() LIMIT 10;", rowMapper);
    }
}