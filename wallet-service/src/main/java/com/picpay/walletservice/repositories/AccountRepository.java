package com.picpay.walletservice.repositories;

import com.picpay.walletservice.models.AccountModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<AccountModel, UUID> {
}
