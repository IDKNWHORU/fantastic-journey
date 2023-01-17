package org.fantastic.journey.common;

import static org.assertj.core.api.Assertions.assertThat;

import org.fantastic.journey.common.clients.Client;
import org.fantastic.journey.common.clients.ClientController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CommonApplicationTest {

    @Autowired
    private ClientController controller;

    @Test
    public void contextLoads(){
        assertThat(controller).isNotNull();
    }

    @Test
    public void emptyGetClientWillEmptyList() {
        List<Client> emptyList =  controller.getClients();

        assertThat(emptyList.size()).isZero();
    }
}
