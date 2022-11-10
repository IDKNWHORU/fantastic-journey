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
        StringBuilder query = new StringBuilder();
        query.append("SELECT email");
        query.append(" from users");
        query.append(" where seq = 1");

        return jdbcTemplate.query(query.toString(), (rs, rowNum) -> {
            return rs.getString("email");
        }).toString();
    }
}
