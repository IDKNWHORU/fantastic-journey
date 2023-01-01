package org.fantastic.journey.common.clients;

import org.springframework.jdbc.core.JdbcTemplate;

public class CabinetService {

    private final JdbcTemplate repo;

    CabinetService(JdbcTemplate repo) {
        this.repo = repo;
    }

    public int addCabinet(Cabinet cabinet) {
        String addCabinetQuery = """
                insert into cabinet(id, start_at, expire_at)
                             values (?, ?, ?)
                """;

        return this.repo.update(addCabinetQuery, cabinet.getId(), cabinet.getStartAt(), cabinet.getExpireAt());
    }

    public int addMemberCabinet(String client, int cabinet) {
        String addMemberCabinetQuery = """
                insert into member(client, cabinet)
                values (?, ?)
                """;

        return this.repo.update(addMemberCabinetQuery, client, cabinet);
    }
}