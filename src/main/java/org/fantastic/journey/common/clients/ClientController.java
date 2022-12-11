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
                select id, name, phone_number, birth_at, member_id
                  from client c
                """;

        return repo.query(clientsQuery, (rs) -> {
            List<Client> clients = new ArrayList<>();
            if (rs.next()) {
                clients.add(Client.builder()
                        .id(rs.getString("id"))
                        .name(rs.getString("name"))
                        .phoneNumber(rs.getString("phone_number"))
                        .birthAt(rs.getString("birth_at"))
                        .memberId(rs.getString("member_id"))
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
        List<Map<String, Object>> products = (List) newClient.get("products");
        List<Product> newProducts = new ArrayList<>();

        String memberId = null;
        String phoneNumber = null;
        String birthAt = null;
        if (newClient.get("member_id") != null)
            memberId = newClient.get("member_id").toString();

        if (newClient.get("phoneNumber") != null)
            phoneNumber = newClient.get("phoneNumber").toString();

        if (newClient.get("birthAt") != null)
            birthAt = newClient.get("birthAt").toString();

        this.repo.update(createClientQuery,
                clientId,
                newClient.get("name").toString(),
                phoneNumber,
                birthAt,
                memberId);

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

        if (products != null) {
            products.forEach(product -> {
                Product newProduct = new Product();

                newProduct.setName(product.get("name").toString());
                newProduct.setStart_at(product.get("start_at").toString());
                newProduct.setExpire_at(product.get("expire_at").toString());

                newProducts.add(newProduct);

                this.repo.update("""
                        insert into member_product(client, product, start_at, expire_at)
                        values (?, ?, ?, ?);
                        """, clientId, newProduct.getName(), newProduct.getStart_at(), newProduct.getExpire_at());
            });
        }

        return Client.builder()
                .id(clientId)
                .name(newClient.get("name").toString())
                .phoneNumber(phoneNumber)
                .birthAt(birthAt)
                .memberId(memberId)
                .cabinet(newCabinet)
                .products(newProducts)
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