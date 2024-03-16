package jp.study.web.repository;

import jp.study.domain.Hiragana;
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
public class HiraganaRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Hiragana> rowMapper;

    public HiraganaRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        rowMapper = (rs, rowNum) -> {
            Hiragana hiragana = new Hiragana();
            hiragana.setJp(rs.getString("jp"));
            hiragana.setKr(rs.getString("kr"));
            hiragana.setEn(rs.getString("en"));
            return hiragana;
        };
    }


    public List<Hiragana> read(){

        return jdbcTemplate.query("SELECT * FROM hiragana ORDER BY RAND() LIMIT 10;", rowMapper);
    }

}
