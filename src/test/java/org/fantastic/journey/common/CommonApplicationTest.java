package org.fantastic.journey.common;

import org.fantastic.journey.common.clients.Client;
import org.fantastic.journey.common.clients.ClientController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CommonApplicationTest {
    private final ClientController controller;

    @Autowired
    public CommonApplicationTest(ClientController controller) {
        this.controller = controller;
    }

    @Test
    public void contextLoads(){
        assertThat(controller).isNotNull();
    }

    @Test
    public void emptyGetClientWillEmptyList() {
        List<Client> emptyList =  controller.getClients();

        assertThat(emptyList.size()).isZero();
    }

    @Test
    public void createNewClientTest() {
        String id = "550e8400-e29b-41d4-a716-446655440000";
        Client client = Client.builder().id(id).name("hong gil dong").build();

        controller.addClient(client);
        List<Client> client2 = controller.getClientInfo(id);

        checkSameClient(client, client2.get(0));
    }

    private void checkSameClient(Client client, Client client2) {
        assertThat(client.getId()).isEqualTo(client2.getId());
        assertThat(client.getName()).isEqualTo(client2.getName());
        assertThat(client.getPhoneNumber()).isEqualTo(client2.getPhoneNumber());
        assertThat(client.getBirthAt()).isEqualTo(client2.getBirthAt());
        assertThat(client.getMemberId()).isEqualTo(client2.getMemberId());
        assertThat(client.getCabinet()).isEqualTo(client2.getCabinet());
        assertThat(client.getProducts()).isEqualTo(client2.getProducts());
    }
}
