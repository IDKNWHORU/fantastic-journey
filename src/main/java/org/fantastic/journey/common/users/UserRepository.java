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
                SELECT email, passwd
                  FROM users
                """;

        return (List<User>) jdbcTemplate.query(query, (rs, rowNum) -> {
            return new User(rs.getString("email"), rs.getString("passwd"));
        });
    }
}
