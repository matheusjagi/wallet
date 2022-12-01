package com.picpay.walletservice.builders;

import com.picpay.walletservice.models.UserModel;
import com.picpay.walletservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Component
public class UserBuilder {

    @Autowired
    private UserRepository userRepository;

    private UserModel build(String cpf) {
        return UserModel.builder()
                .email("matheus.jagi@gmail.com")
                .fullName("Matheus Jagi")
                .phoneNumber("+5527996382021")
                .cpf(cpf)
                .creationDate(LocalDateTime.now(ZoneId.of("UTC")))
                .lastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")))
                .build();
    }

    public UserModel create(String cpf) {
        return userRepository.save(build(cpf));
    }

    public UserModel findById(UUID id) {
        return userRepository.getReferenceById(id);
    }
}
