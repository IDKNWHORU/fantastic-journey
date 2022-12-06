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
                select *
                  from client;
                """;

        return repo.query(clientsQuery, (rs) -> {
            List<Client> clients = new ArrayList<>();
            if (rs.next()) {
                clients.add(Client.builder().id(rs.getString("id")).name(rs.getString("name")).phoneNumber(rs.getString("phone_number")).birthAt(rs.getString("birth_at")).memberId(rs.getString("member_id")).build());
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

        this.repo.update(createClientQuery,
                clientId,
                newClient.get("name").toString(),
                newClient.getOrDefault("phoneNumber", "           ").toString(),
                newClient.getOrDefault("birthAt", "        ").toString(),
                newClient.getOrDefault("member_id", "").toString());

        return Client.builder()
                .id(clientId)
                .name(newClient.get("name").toString())
                .phoneNumber(newClient.getOrDefault("phoneNumber", "           ").toString())
                .birthAt(newClient.getOrDefault("birthAt", "        ").toString())
                .memberId(newClient.getOrDefault("member_id", "").toString())
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