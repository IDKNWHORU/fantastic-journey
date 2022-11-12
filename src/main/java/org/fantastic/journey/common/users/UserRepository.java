package org.fantastic.journey.common.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> findUser() {
        String query = """
                SELECT id, passwd, phone_number, create_at
                  FROM users
                """;

        return jdbcTemplate.query(query, (rs, rowNum) -> new User(rs.getString("id"), rs.getString("passwd"), rs.getString("phone_number"), rs.getTimestamp("create_at")));
    }
}
