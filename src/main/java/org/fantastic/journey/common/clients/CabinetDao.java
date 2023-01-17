package org.fantastic.journey.common.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CabinetDao {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public CabinetDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int add(Cabinet cabinet) {
        this.jdbcTemplate.update("""
                insert into cabinet(id, start_at, expire_at)
                             values (?, ?, ?)
                """, cabinet.getId(), cabinet.getStartAt(), cabinet.getExpireAt());

        return 1;
    }

    public int addConstraintMember(String client, int cabinet) {
        this.jdbcTemplate.update("""
                insert into member(client, cabinet)
                values (?, ?)
                """, client, cabinet);

        return 1;
    }

    public int delete(int cabinet) {
        this.jdbcTemplate.update("""
                delete from cabinet
                 where id = ?
                """, cabinet);

        return 1;
    }
}