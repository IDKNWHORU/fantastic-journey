package org.fantastic.journey.common.clients;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {

    private final ClientRepository clientRepository;

    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @GetMapping("/clients")
    public List<Client> getClients() {
        return (List<Client>)clientRepository.findAll();
    }

    @PostMapping("/client")
    public Client createClient(@RequestBody Client newClient) {
        clientRepository.insert(newClient);

        return newClient;
    }
}
