package com.picpay.movementservice.services.impl;

import com.picpay.movementservice.dtos.MovementDto;
import com.picpay.movementservice.models.FinancialOperationModel;
import com.picpay.movementservice.models.MovementModel;
import com.picpay.movementservice.repositories.FinancialOperationRepository;
import com.picpay.movementservice.repositories.MovementRepository;
import com.picpay.movementservice.services.FinancialOperationService;
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
public class FinancialOperationServiceImpl implements FinancialOperationService {

    private final FinancialOperationRepository financialOperationRepository;
    private final MovementRepository movementRepository;
    private final ModelMapper mapper;

    @Override
    public void run(MovementDto movementDto) {
        Boolean completedTransaction = UtilsService.createRandomBoolean();

        MovementModel movement = mapper.map(movementDto, MovementModel.class);
        movement.setEffectiveness(completedTransaction);

        FinancialOperationModel financialOperation = new FinancialOperationModel(
                null, movementDto.getFinancialOperationType(), movement);
        financialOperationRepository.save(financialOperation);

        if (!completedTransaction) {
            log.error("Financial operation failed. Movement ID: {}", movement.getId());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Financial operation failed.");
        }

        log.debug("Financial operation successfully. Movement ID: {}", movement.getId());
    }
}
