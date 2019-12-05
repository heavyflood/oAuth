package com.sicc.oAuth.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sicc.oAuth.client.ClientService;

import java.util.Map;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService service;

    @RequestMapping("/generate")
    public Map<String, String> generate() {
        return service.generate();
    }
}
