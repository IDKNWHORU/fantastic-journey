package org.fantastic.journey.common.clients.dao;

import org.fantastic.journey.common.clients.model.Cabinet;
import org.fantastic.journey.common.clients.model.Client;
import org.fantastic.journey.common.clients.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ClientDao {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public ClientDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Client> getAll() {
        return this.jdbcTemplate.query("""
                  SELECT *
                  FROM CLIENT
                """, (rs) -> {
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

    public int add(Client client) {
        this.jdbcTemplate.update("""
                  insert into client (id, name, phone_number, birth_at, member_id)
                  values (?, ?, ?, ?, ?)
                """, client.getId(), client.getName(), client.getPhoneNumber(), client.getBirthAt(), client.getMemberId());
        return 1;
    }

    public Client get(String id) {
        return this.jdbcTemplate.queryForObject("""
                        WITH member_product_view_table AS (
                            SELECT client, group_concat(concat(mp.PRODUCT, ';', mp.start_at, ';', mp.expire_at)) AS products
                              FROM MEMBER_PRODUCT mp
                             GROUP BY CLIENT
                              )
                        SELECT cl.id, cl.name, cl.PHONE_NUMBER, cl.birth_at, cl.MEMBER_ID, mb.CABINET, cb.START_AT, cb.EXPIRE_AT, member_product_view_table.products
                          FROM client cl
                          LEFT JOIN member mb
                            ON cl.id = mb.client
                          LEFT JOIN cabinet cb
                            ON mb.CABINET = cb.id
                          LEFT JOIN member_product_view_table
                            ON cl.id = member_product_view_table.client
                         WHERE cl.id = ?
                        """, new Object[]{id}, (rs, rowNum) -> {
                    Cabinet allocatedCabinet = rs.getInt("cabinet") > 0 ? new Cabinet() : null;

                    if (allocatedCabinet != null) {
                        allocatedCabinet.setId(rs.getInt("cabinet"));
                        allocatedCabinet.setStartAt(rs.getString("start_at"));
                        allocatedCabinet.setExpireAt(rs.getString("expire_at"));
                    }

                    String productsStr = rs.getString("products");

                    List<Product> memberProduct = productsStr != null ? new ArrayList<>() : null;

                    if (productsStr != null) {
                        for (String productStr : productsStr.split(",")) {
                            String[] productInfo = productStr.split(";");
                            Product product = new Product();

                            product.setName(productInfo[0]);
                            product.setStartAt(productInfo[1]);
                            product.setExpireAt(productInfo[2]);

                            memberProduct.add(product);
                        }
                    }

                    return Client.builder()
                            .id(rs.getString("id"))
                            .name(rs.getString("name"))
                            .phoneNumber(rs.getString("phone_number"))
                            .birthAt(rs.getString("birth_at"))
                            .memberId(rs.getString("member_id"))
                            .cabinet(allocatedCabinet)
                            .products(memberProduct)
                            .build();
                }
        );
    }

    public int deleteAll() {
        return this.jdbcTemplate.update("""
                  delete from client
                """);
    }
}
