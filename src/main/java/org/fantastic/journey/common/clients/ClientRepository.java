package org.fantastic.journey.common.clients;

import org.springframework.data.repository.CrudRepository;

interface ClientRepository extends CrudRepository<Client, String>, WithInsert<Client> {}
