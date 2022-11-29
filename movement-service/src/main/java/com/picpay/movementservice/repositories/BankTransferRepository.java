package com.picpay.movementservice.repositories;

import com.picpay.movementservice.models.BankTransferModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BankTransferRepository extends JpaRepository<BankTransferModel, UUID> {
}
