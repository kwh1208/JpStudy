package jp.study.web.repository;

import jp.study.domain.Dialog;
import jp.study.domain.Sentence;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicIntegerArray;

@Repository
public class DialogRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Dialog> rowMapper;

    public DialogRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        rowMapper = (rs, rowNum) -> {
            Dialog dialog = new Dialog();
            dialog.setSituation(rs.getString("situation"));
            dialog.setUserRole(rs.getString("userRole"));
            dialog.setBotRole(rs.getString("botRole"));
            dialog.setSituationKr(rs.getString("situationKr"));
            dialog.setUserRoleKr(rs.getString("userRoleKr"));
            dialog.setBotRoleKr(rs.getString("botRoleKr"));
            return dialog;
        };
    }


    public List<Dialog> read(){
        return jdbcTemplate.query("SELECT * FROM dialog ORDER BY RAND() LIMIT 3;", rowMapper);
    }
}
