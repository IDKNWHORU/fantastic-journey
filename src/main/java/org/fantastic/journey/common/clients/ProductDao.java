package org.fantastic.journey.common.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDao {
    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int add(String clientId, Product product) {
        this.jdbcTemplate.update("""
                insert into member_product(client, product, start_at, expire_at)
                values (?, ?, ?, ?);
                """, clientId, product.getName(), product.getStart_at(), product.getExpire_at());

        return 1;
    }
}
