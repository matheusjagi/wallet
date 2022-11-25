package com.picpay.movementservice.repositories;

import com.picpay.movementservice.models.FinancialOperationModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FinancialOperationRepository extends JpaRepository<FinancialOperationModel, UUID> {
}
