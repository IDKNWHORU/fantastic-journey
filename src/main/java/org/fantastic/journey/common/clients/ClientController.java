package org.fantastic.journey.common.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

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
                SELECT *
                  FROM CLIENT
                """;

        return repo.query(clientsQuery, (rs) -> {
            List<Client> clients = new ArrayList<>();
            while (rs.next()) {
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

    @GetMapping("/client/{id}")
    public Object getClientInfo(@PathVariable("id") String clientId) {
        String findClientQuery = """
                WITH mpvt AS (
                    SELECT client, group_concat(concat(mp.PRODUCT, ';', mp.start_at, ';', mp.expire_at)) AS products
                      FROM MEMBER_PRODUCT mp
                     GROUP BY CLIENT
                      )
                SELECT cl.id, cl.name, cl.PHONE_NUMBER, cl.birth_at, cl.MEMBER_ID, mb.CABINET, cb.START_AT, cb.EXPIRE_AT, mpvt.products
                  FROM client cl
                  LEFT JOIN member mb
                    ON cl.id = mb.client
                  LEFT JOIN cabinet cb
                    ON mb.CABINET = cb.id
                  LEFT JOIN mpvt
                    ON cl.id = mpvt.client
                 WHERE cl.id = ?
                """;


        return repo.query(findClientQuery, new Object[]{clientId}, (rs) -> {
            List<Client> clients = new ArrayList<>();
            while (rs.next()) {
                Cabinet allocatedCabinet = rs.getInt("cabinet") > 0 ? new Cabinet(): null;

                if (allocatedCabinet != null) {
                    allocatedCabinet.setId(rs.getInt("cabinet"));
                    allocatedCabinet.setStartAt(rs.getString("start_at"));
                    allocatedCabinet.setExpireAt(rs.getString("expire_at"));
                }

                String productsStr = rs.getString("products");

                List<Product> memberProduct = productsStr != null ? new ArrayList<>(): null;

                if (productsStr != null) {
                    for(String productStr: productsStr.split(",")) {
                        String[] productInfo = productStr.split(";");
                        Product product = new Product();

                        product.setName(productInfo[0]);
                        product.setStart_at(productInfo[1]);
                        product.setExpire_at(productInfo[2]);

                        memberProduct.add(product);
                    }
                }

                clients.add(Client.builder()
                        .id(rs.getString("id"))
                        .name(rs.getString("name"))
                        .phoneNumber(rs.getString("phone_number"))
                        .birthAt(rs.getString("birth_at"))
                        .memberId(rs.getString("member_id"))
                        .cabinet(allocatedCabinet)
                        .products(memberProduct)
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

        Map<String, Object> cabinet = (Map) newClient.getOrDefault("cabinet", null);
        List<Map<String, Object>> products = (List) newClient.get("products");
        List<Product> newProducts = new ArrayList<>();

        String memberId = null;
        String phoneNumber = null;
        String birthAt = null;
        if (newClient.get("memberId") != null)
            memberId = newClient.get("memberId").toString();

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
            Cabinet nc = getCabinet((int)cabinet.get("id"), cabinet.get("start_at").toString(), cabinet.get("expire_at").toString());

            CabinetService cabinetService = new CabinetService(this.repo);

            cabinetService.addCabinet(nc);
            cabinetService.addMemberCabinet(clientId, nc.getId());
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
                .cabinet(cabinet == null ? null : new Cabinet())
                .products(newProducts)
                .build();
    }

    @PutMapping("/client/{id}")
    public Client editClient(@PathVariable("id") String clientId) {
        return new Client("","","","","", new Cabinet(), new ArrayList<>());
    }

    @GetMapping("/cabinets")
    public List<Cabinet> getcabinets() {
        return this.repo.query("""
                select *
                  from cabinet
                """, (rs) -> {
            List<Cabinet> cabinets = new ArrayList<>();
            while(rs.next()) {
                Cabinet cabinet = getCabinet(
                        rs.getInt("id"),
                        rs.getString("start_at"),
                        rs.getString("expire_at"));

                cabinets.add(cabinet);
            }
            return cabinets;
        });
    }

    public Cabinet getCabinet(int id, String startAt, String expireAt) {
        Cabinet cabinet = new Cabinet();
        cabinet.setId(id);
        cabinet.setStartAt(startAt);
        cabinet.setExpireAt(expireAt);

        return cabinet;
    }
}