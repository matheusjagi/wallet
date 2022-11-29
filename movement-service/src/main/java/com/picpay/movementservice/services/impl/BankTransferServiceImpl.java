package com.picpay.movementservice.services.impl;

import com.picpay.movementservice.dtos.MovementDto;
import com.picpay.movementservice.dtos.events.UpdateAmountEventDto;
import com.picpay.movementservice.enums.MovementType;
import com.picpay.movementservice.models.BankTransferModel;
import com.picpay.movementservice.models.MovementModel;
import com.picpay.movementservice.publishers.UpdateAmountEventPublisher;
import com.picpay.movementservice.repositories.BankTransferRepository;
import com.picpay.movementservice.services.BankTransferService;
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
public class BankTransferServiceImpl implements BankTransferService {

    private final BankTransferRepository bankTransferRepository;
    private final UpdateAmountEventPublisher updateAmountEventPublisher;
    private final ModelMapper mapper;


    @Override
    public void transfer(MovementDto movementDto) {
        MovementModel movement = mapper.map(movementDto, MovementModel.class);
        movement.setEffectiveness(true);

        BankTransferModel bankTransfer = new BankTransferModel(null, movementDto.getTargetAccountId(), movement);
        bankTransferRepository.save(bankTransfer);

        log.debug("Bank transfer successfully. Movement ID: {}", movement.getId());

        updateAmountEventPublisher.publisher(new UpdateAmountEventDto(movementDto.getSourceAccountId(),
                movement.getTransactionAmount(), MovementType.BANK_TRANSFER));
    }
}
