package com.picpay.movementservice.services.impl;

import com.picpay.movementservice.dtos.MovementDto;
import com.picpay.movementservice.dtos.events.UpdateAmountEventDto;
import com.picpay.movementservice.enums.MovementType;
import com.picpay.movementservice.models.MovementModel;
import com.picpay.movementservice.models.PaymentModel;
import com.picpay.movementservice.publishers.UpdateAmountEventPublisher;
import com.picpay.movementservice.repositories.PaymentRepository;
import com.picpay.movementservice.services.PaymentService;
import com.picpay.movementservice.services.utils.UtilsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Log4j2
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final UpdateAmountEventPublisher updateAmountEventPublisher;
    private final ModelMapper mapper;


    @Override
    public void paymentInvoice(MovementDto movementDto) {
        MovementModel movement = mapper.map(movementDto, MovementModel.class);
        movement.setEffectiveness(true);

        PaymentModel payment = new PaymentModel(null, movementDto.getBarcodeNumber(), movement);
        paymentRepository.save(payment);

        log.debug("Payment invoice successfully. Movement ID: {}", movement.getId());

        updateAmountEventPublisher.publisher(new UpdateAmountEventDto(movementDto.getSourceAccountId(),
                movement.getTransactionAmount(), MovementType.PAYMENT));
    }
}
