package org.fantastic.journey.common.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class ClientController {
    @Autowired
    private final ClientDao clientDao;
    @Autowired
    private final ProductDao productDao;
    @Autowired
    private final CabinetDao cabinetDao;

    @Autowired
    private final MemberDao memberDao;

    public ClientController(ClientDao clientDao, ProductDao productDao, CabinetDao cabinetDao, MemberDao memberDao) {
        this.clientDao = clientDao;
        this.productDao = productDao;
        this.cabinetDao = cabinetDao;
        this.memberDao = memberDao;
    }

    @GetMapping("/clients")
    public List<Client> getClients() {
        return this.clientDao.getAll();
    }

    @GetMapping("/client/{id}")
    public Client getClientInfo(@PathVariable("id") String clientId) {
        return this.clientDao.get(clientId);
    }

    @PostMapping("/client")
    public Client createClient(@RequestBody Map<String, Object> newClient) {
        String clientId = UUID.randomUUID().toString();

        Map<String, Object> cabinet = (Map) newClient.getOrDefault("cabinet", null);
        List<Map<String, Object>> products = (List) newClient.get("products");
        List<Product> newProducts = new ArrayList<>();

        Cabinet newCabinet = cabinet == null ? null : getCabinet((int)cabinet.get("id"), cabinet.get("start_at").toString(), cabinet.get("expire_at").toString());

        if(newCabinet != null) {
            cabinetDao.add(newCabinet);
        }

        String memberId = (newClient.get("memberId") == null) ? null : newClient.get("memberId").toString();
        String phoneNumber = (newClient.get("phoneNumber") == null) ? null : newClient.get("phoneNumber").toString();
        String birthAt = (newClient.get("birthAt") == null) ? null : newClient.get("birthAt").toString();

        try {
            this.clientDao.add(Client.builder()
                    .id(clientId)
                    .name(newClient.get("name").toString())
                    .phoneNumber(phoneNumber)
                    .birthAt(birthAt)
                    .memberId(memberId).build());
        } catch (Exception e) {
            if(newCabinet != null) {
                cabinetDao.delete(newCabinet.getId());
            }

            throw new Error("Error cause by creating client" + e.getMessage());
        }

        if (newCabinet != null) {
            Member member = new Member(clientId, newCabinet.getId());
            memberDao.add(member);
        }

        if (products != null) {
            products.forEach(product -> {
                Product newProduct = new Product();

                newProduct.setName(product.get("name").toString());
                newProduct.setStart_at(product.get("start_at").toString());
                newProduct.setExpire_at(product.get("expire_at").toString());

                newProducts.add(newProduct);

                this.productDao.add(clientId, newProduct);
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
//
//    @PutMapping("/client/{id}")
//    public Client editClient(@PathVariable("id") String clientId, @RequestBody Map<String, Object> editedClient) {
//        String updateClientQuery = """
//                update client
//                   set name = ?,
//                       phone_number = ?,
//                       birth_at = ?,
//                       member_id = ?
//                 where id = ?;
//                """;
//
//        List<Client> beforeClientData = getClientInfo(clientId);
//
//        for(Client client: beforeClientData) {
//        }
//
//        this.repo.update(updateClientQuery,
//                editedClient.get("name"),
//                editedClient.get("phoneNumber"),
//                editedClient.get("birthAt"),
//                editedClient.get("memberId"),
//                clientId);
//
//        return new Client("","","","","", new Cabinet(), new ArrayList<>());
//    }
//
//    @GetMapping("/cabinets")
//    public List<Cabinet> getcabinets() {
//        return this.repo.query("""
//                select *
//                  from cabinet
//                """, (rs) -> {
//            List<Cabinet> cabinets = new ArrayList<>();
//            while(rs.next()) {
//                Cabinet cabinet = getCabinet(
//                        rs.getInt("id"),
//                        rs.getString("start_at"),
//                        rs.getString("expire_at"));
//
//                cabinets.add(cabinet);
//            }
//            return cabinets;
//        });
//    }
//
    public Cabinet getCabinet(int id, String startAt, String expireAt) {
        Cabinet cabinet = new Cabinet();
        cabinet.setId(id);
        cabinet.setStartAt(startAt);
        cabinet.setExpireAt(expireAt);

        return cabinet;
    }
}