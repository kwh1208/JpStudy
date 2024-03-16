package jp.study.web.repository;

import jp.study.domain.Katakana;
import jp.study.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@Slf4j
public class MemberRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Member> rowMapper;
    public MemberRepository(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
        rowMapper = (rs, rowNum) -> {
            Member member = new Member();
            member.setEmail(rs.getString("email"));
            member.setName(rs.getString("name"));
            member.setPassword(rs.getString("password"));
            member.setLoginId(rs.getString("loginId"));
            return member;
        };
    }


    public AtomicInteger join(Member member){
        AtomicInteger result = new AtomicInteger();

        result.set(jdbcTemplate.update("insert into member (loginId, name, password, email) values (?, ?, ?, ?)",
                member.getLoginId(), member.getName(), member.getPassword(), member.getEmail()));

        return result;

    }

    public Member findById(Member member){
        List<Member> result = jdbcTemplate.query("select * from member where loginId = ?", rowMapper, member.getLoginId());
        return result.get(0);
    }

    public Member findId(Member member){
        List<Member> result = jdbcTemplate.query("select * from member where email = ? and name = ?", rowMapper, member.getEmail(), member.getName());
        return result.get(0);
    }

    public Member findPassword(Member member){
        List<Member> result = jdbcTemplate.query("select * from member where email = ? and name = ? and loginId = ?", rowMapper, member.getEmail(), member.getName(), member.getLoginId());
        return result.get(0);
    }
}
