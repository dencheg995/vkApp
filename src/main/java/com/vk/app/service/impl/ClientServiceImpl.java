package com.vk.app.service.impl;

import com.vk.app.module.Client;
import com.vk.app.repository.ClientRepository;
import com.vk.app.service.interfaces.ClientService;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client addClient(Client client) {
        phoneNumberValidation(client);
        return clientRepository.saveAndFlush(client);
    }

    private void phoneNumberValidation(Client client) {
        String phoneNumber = client.getPhoneNumber();
        Pattern pattern = Pattern.compile("^((8|\\+7|7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$");
        Matcher matcher = pattern.matcher(phoneNumber);
        if (matcher.matches()) {
            if (phoneNumber.startsWith("8")) {
                phoneNumber = phoneNumber.replaceFirst("8", "7");
            }
            client.setPhoneNumber(phoneNumber.replaceAll("[+()-]", "")
                    .replaceAll("\\s", ""));
        }
    }
}
