package org.fantastic.journey.common.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserBean {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserBean(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String findUserMail(int id) {
        String query = """
                SELECT email
                  FROM users
                 WHERE seq = 1
                """;

        return jdbcTemplate.query(query, (rs, rowNum) -> {
            return rs.getString("email");
        }).toString();
    }
}
