package com.picpay.movementservice.services;

import com.picpay.movementservice.dtos.MovementDto;

public interface BankTransferService {

    void transfer(MovementDto movementDto);
}
