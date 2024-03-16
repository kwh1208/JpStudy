package jp.study.web.repository;

import jp.study.domain.Hiragana;
import jp.study.domain.Katakana;
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
public class KatakanaRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Katakana> rowMapper;

    public KatakanaRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        rowMapper = (rs, rowNum) -> {
            Katakana katakana = new Katakana();
            katakana.setJp(rs.getString("jp"));
            katakana.setKr(rs.getString("kr"));
            katakana.setEn(rs.getString("en"));
            return katakana;
        };
    }


    public List<Katakana> read(){

        return jdbcTemplate.query("SELECT * FROM katakana ORDER BY RAND() LIMIT 10;", rowMapper);
    }

}
