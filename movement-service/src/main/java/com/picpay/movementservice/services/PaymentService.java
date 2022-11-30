package com.picpay.movementservice.services;

import com.picpay.movementservice.dtos.MovementDto;

public interface PaymentService {

    void paymentInvoice(MovementDto movementDto);
}
