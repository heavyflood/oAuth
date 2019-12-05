package com.sicc.oAuth.client;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sicc.oAuth.client.Client;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {

    Client findByClientId(String clientId);

}
