package com.picpay.movementservice.repositories;

import com.picpay.movementservice.models.MovementModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MovementRepository extends JpaRepository<MovementModel, UUID> {
}
