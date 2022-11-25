package com.picpay.walletservice.repositories;

import com.picpay.walletservice.models.AccountModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<AccountModel, UUID> {

    Boolean existsByUserId(UUID userId);

    Optional<AccountModel> findByUserCpfAndStatus(String cpf, String status);
}
