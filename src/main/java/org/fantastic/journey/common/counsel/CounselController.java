package org.fantastic.journey.common.counsel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CounselController {
    private final JdbcTemplate jdbcTemplate;

    class Counsel {
        String comment;
        Timestamp createAt;

        public Counsel(String comment, Timestamp createAt) {
            this.comment = comment;
            this.createAt = createAt;
        }
    }
    @Autowired
    public CounselController(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/counsels")
    public List<Counsel> getCounsels() {
        String counselsQuery = """
                  SELECT *
                    FROM COUNSEL
                """;

        return jdbcTemplate.query(counselsQuery, (rs)->{
            List<Counsel> counsels = new ArrayList<>();
            if(rs.next()) {
                counsels.add(new Counsel(rs.getString("comment"), rs.getTimestamp("createAt")));
            }
            return counsels;
        });
    }
}
