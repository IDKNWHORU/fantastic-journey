package org.fantastic.journey.common.clients;

import java.util.List;

import org.springframework.web.bind.annotation.*;

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
        Client client = Client.builder()
                .name(newClient.getName())
                .phoneNumber(newClient.getPhoneNumber())
                .birthAt(newClient.getBirthAt())
                .photoShot(newClient.getPhotoShot())
                .memberId(newClient.getMemberId())
                .cabinet(newClient.getCabinet())
                .expiredAt(newClient.getExpiredAt())
                .build();
        clientRepository.insert(client);

        return client;
    }

    @PutMapping("/client")
    public Client updateClient(@RequestBody Client client) {
        Client updatedClient = Client.builder()
                .id(client.getId())
                .name(client.getName())
                .phoneNumber(client.getPhoneNumber())
                .birthAt(client.getBirthAt())
                .photoShot(client.getPhotoShot())
                .memberId(client.getMemberId())
                .cabinet(client.getCabinet())
                .expiredAt(client.getExpiredAt())
                .build();
        clientRepository.save(updatedClient);

        return client;
    }
}