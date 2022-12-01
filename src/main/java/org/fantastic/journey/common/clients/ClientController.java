package org.fantastic.journey.common.clients;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

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

        return repo.query(clientsQuery, (rs)-> {
            List<Client> clients = new ArrayList<>();
            if(rs.next()) {
                clients.add(Client.builder().id("").name("").birthAt("").memberId("").build());
            }
            return clients;
        });
    }

    @PostMapping("/client")
    public Client createClient(@RequestBody Map<String, Object> newClient) {
        String createClientQuery = """
                insert into client (id, name, phone_number, birth_at, member_id)
                values (?, ?, ?, ?, ?, ?)
                """;

//        this.repo.query(createClientQuery);

        return Client.builder().id(UUID.randomUUID().toString()).name(newClient.get("name").toString()).build();
    }
//
//    @PutMapping("/client")
//    public Client updateClient(@RequestBody Client updatedClient) {
//        clientRepository.save(updatedClient);
//
//        return updatedClient;
//    }
}