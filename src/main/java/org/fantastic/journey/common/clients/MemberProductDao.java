package org.fantastic.journey.common.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MemberProductDao {
    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public MemberProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int add(String clientId, Product product) {
        return this.jdbcTemplate.update("""
                insert into member_product(client, product, start_at, expire_at)
                values (?, ?, ?, ?);
                """, clientId, product.getName(), product.getStartAt(), product.getExpireAt());
    }
}
