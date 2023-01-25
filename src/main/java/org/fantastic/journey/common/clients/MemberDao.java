package org.fantastic.journey.common.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao {
    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public MemberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int add(Member member) {
        this.jdbcTemplate.update("""
                insert into member(client, cabinet)
                values (?, ?)
                """, member.getClient(), member.getCabinet());

        return 1;
    }
}
