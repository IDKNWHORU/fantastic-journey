package org.fantastic.journey.common.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CabinetDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CabinetDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int add(Cabinet cabinet) {
        return this.jdbcTemplate.update("""
                insert into cabinet(id, start_at, expire_at)
                             values (?, ?, ?)
                """, cabinet.getId(), cabinet.getStartAt(), cabinet.getExpireAt());
    }

    public int delete(int cabinet) {
        return this.jdbcTemplate.update("""
                delete from cabinet
                 where id = ?
                """, cabinet);
    }

    public Cabinet get(int cabinet) {
        return this.jdbcTemplate.queryForObject("""
                select * from cabinet
                 where id = ?
                """, new Object[]{cabinet}, (rs, rowNum) -> {
            Cabinet cabinet1 = new Cabinet();
            cabinet1.setId(rs.getInt("id"));
            cabinet1.setStartAt(rs.getString("start_at"));
            cabinet1.setExpireAt(rs.getString("expire_at"));
            return cabinet1;
        });
    }
}