package com.picpay.movementservice.services;

import com.picpay.movementservice.dtos.MovementDto;

public interface FinancialOperationService {
    void run(MovementDto movementDto);
}
