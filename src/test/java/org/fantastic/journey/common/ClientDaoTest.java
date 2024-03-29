package org.fantastic.journey.common;

import org.fantastic.journey.common.clients.dao.ClientDao;
import org.fantastic.journey.common.clients.model.Client;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ClientDaoTest {
    private final ClientDao clientDao;

    @Autowired
    public ClientDaoTest(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @BeforeEach
    public void setUp() {
        clientDao.deleteAll();
    }

    @Test
    public void emptyGetClientWillEmptyList() {
        List<Client> emptyList =  clientDao.getAll();

        assertThat(emptyList.size()).isZero();
    }

    @Test
    public void createNewClientTest() {
        String id = "550e8400-e29b-41d4-a716-446655440000";
        Client client = Client.builder().id(id).name("hong gil dong").build();

        clientDao.add(client);
        Client client2 = clientDao.get(id);

        checkSameClient(client, client2);
    }

    @Test
    public void notFoundClientWillThrowEmptyResultDataAccessException() {
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            clientDao.get("hello-unknown-user-id");
        });
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
