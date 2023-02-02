package org.fantastic.journey.common;

import org.fantastic.journey.common.clients.ClientController;
import org.fantastic.journey.common.clients.model.Cabinet;
import org.fantastic.journey.common.clients.model.Client;
import org.fantastic.journey.common.clients.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ClientControllerTest {

    ClientController clientController;

    @Autowired
    public ClientControllerTest(ClientController clientController) {
        this.clientController = clientController;
    }

    @Test
    public void addAndGet() {
        Map<String, Object> client = new HashMap<>(){{
            put("name", "jun ho");
            put("cabinet", new HashMap<String, Object>(){{
                put("id", 1);
                put("start_at", "20230101");
                put("expire_at", "20230301");
            }});
            put("products", List.of(new HashMap<String, String>(){{
                put("name", "헬스 - 2개월");
                put("start_at", "20230101");
                put("expire_at", "20230301");
            }}));
        }};

        Client client1 = this.clientController.createClient(client);
        Client client2 = this.clientController.getClientInfo(client1.getId());

        assert client2 != null;

        checkSameClient(client1, client2);

        assert this.clientController.getClients().size() == 1;
    }

    public void checkSameClient(Client client1, Client client2) {
        Cabinet cabinet1 = client1.getCabinet();
        Cabinet cabinet2 = client2.getCabinet();
        List<Product> products1 = client1.getProducts();
        List<Product> products2 = client2.getProducts();

        assertEquals(client1.getName(), client2.getName());
        assertEquals(cabinet1.getId(), cabinet2.getId());
        assertEquals(cabinet1.getStartAt(), cabinet2.getStartAt());
        assertEquals(cabinet1.getExpireAt(), cabinet2.getExpireAt());
        assert products1.size() == products2.size();
        assertEquals(products1.get(0).getName(), products2.get(0).getName());
        assertEquals(products1.get(0).getStartAt(), products2.get(0).getStartAt());
        assertEquals(products1.get(0).getExpireAt(), products2.get(0).getExpireAt());
    }
}
