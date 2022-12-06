package org.fantastic.journey.common.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class ClientController {

    private final JdbcTemplate repo;


    @Autowired
    public ClientController(JdbcTemplate repo) {
        this.repo = repo;
    }

    @GetMapping("/clients")
    public List<Client> getClients() {
        String clientsQuery = """
                select c.id, name, phone_number, birth_at, member_id, d.cabinet, e.start_at cabinet_start_at, e.expire_at cabinet_expire_at
                  from client c
                  left join member d
                    on c.id = d.client
                  left join cabinet e
                    on d.cabinet = e.id
                """;

        return repo.query(clientsQuery, (rs) -> {
            List<Client> clients = new ArrayList<>();
            if (rs.next()) {
                Cabinet cabinet = null;

                if (rs.getString("cabinet") != null) {
                    cabinet = new Cabinet();
                    cabinet.setId(rs.getInt("cabinet"));
                    cabinet.setStartAt(rs.getString("cabinet_start_at"));
                    cabinet.setExpireAt(rs.getString("cabinet_expire_at"));
                }

                clients.add(Client.builder()
                        .id(rs.getString("id"))
                        .name(rs.getString("name"))
                        .phoneNumber(rs.getString("phone_number"))
                        .birthAt(rs.getString("birth_at"))
                        .memberId(rs.getString("member_id"))
                        .cabinet(cabinet)
                        .build());
            }
            return clients;
        });
    }

    @PostMapping("/client")
    public Client createClient(@RequestBody Map<String, Object> newClient) {
        String createClientQuery = """
                insert into client (id, name, phone_number, birth_at, member_id)
                values (?, ?, ?, ?, ?)
                """;
        String clientId = UUID.randomUUID().toString();
        Cabinet newCabinet = null;
        Map<String, Object> cabinet = (Map) newClient.getOrDefault("cabinet", null);

        this.repo.update(createClientQuery,
                clientId,
                newClient.get("name").toString(),
                newClient.getOrDefault("phoneNumber", "           ").toString(),
                newClient.getOrDefault("birthAt", "        ").toString(),
                newClient.getOrDefault("member_id", "").toString());

        if (cabinet != null) {
            newCabinet = new Cabinet();
            newCabinet.setId((int) cabinet.get("id"));
            newCabinet.setStartAt(cabinet.get("start_at").toString());
            newCabinet.setExpireAt(cabinet.get("expire_at").toString());
            this.repo.update("""
                             insert into cabinet(id, start_at, expire_at)
                             values (?, ?, ?)
                            """, cabinet.get("id"),
                    cabinet.get("start_at"),
                    cabinet.get("expire_at"));
            this.repo.update("""
                            insert into member(client, cabinet)
                            values (?, ?)
                            """,
                    clientId, cabinet.get("id"));
        }

        return Client.builder()
                .id(clientId)
                .name(newClient.get("name").toString())
                .phoneNumber(newClient.getOrDefault("phoneNumber", "           ").toString())
                .birthAt(newClient.getOrDefault("birthAt", "        ").toString())
                .memberId(newClient.getOrDefault("member_id", "").toString())
                .cabinet(newCabinet)
                .build();
    }
//
//    @PutMapping("/client")
//    public Client updateClient(@RequestBody Client updatedClient) {
//        clientRepository.save(updatedClient);
//
//        return updatedClient;
//    }
}