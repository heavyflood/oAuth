package com.sicc.oAuth.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.sicc.oAuth.client.Client;
import com.sicc.oAuth.client.ClientRepository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ClientService implements ClientDetailsService {

    @Autowired
    private ClientRepository repository;

    private Logger logger = LoggerFactory.getLogger("ClientService");

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {

        BaseClientDetails clientDetails = new BaseClientDetails();

        Client client = repository.findByClientId(clientId);

        if(client == null) throw new ClientRegistrationException("��ϵ� Ŭ���̾�Ʈ ������ �������� �ʽ��ϴ�.");
        logger.debug("client : " + client.toString());

        clientDetails.setClientId(clientId);
        clientDetails.setClientSecret(client.getClientSecret());
        clientDetails.setResourceIds(StringUtils.commaDelimitedListToSet(client.getResourceIds()));
        clientDetails.setAutoApproveScopes(StringUtils.commaDelimitedListToSet(client.getAutoApproveScopes()));
        clientDetails.setScope(StringUtils.commaDelimitedListToSet(client.getScope()));
        clientDetails.setAuthorizedGrantTypes(StringUtils.commaDelimitedListToSet(client.getAuthorizedGrantTypes()));
        clientDetails.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList(client.getAuthorities()));
        clientDetails.setAccessTokenValiditySeconds(client.getAccessTokenValiditySeconds());
        clientDetails.setRefreshTokenValiditySeconds(client.getRefreshTokenValiditySeconds());
        
        return clientDetails;
    }

    public Client query(String clientId) {
        return repository.findByClientId(clientId);
    }


    public Map<String, String> generate() {
        Map<String, String> data = new HashMap<>();

        RandomValueStringGenerator generator = new RandomValueStringGenerator();
        generator.setRandom(ThreadLocalRandom.current());
        generator.setLength(64);

        data.put("client_id", generator.generate());
        data.put("client_secret", generator.generate());

        return data;
    }


}

