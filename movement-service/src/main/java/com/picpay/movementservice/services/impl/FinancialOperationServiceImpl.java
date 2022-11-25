package com.picpay.movementservice.services.impl;

import com.picpay.movementservice.dtos.MovementDto;
import com.picpay.movementservice.models.FinancialOperationModel;
import com.picpay.movementservice.models.MovementModel;
import com.picpay.movementservice.repositories.FinancialOperationRepository;
import com.picpay.movementservice.repositories.MovementRepository;
import com.picpay.movementservice.services.FinancialOperationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class FinancialOperationServiceImpl implements FinancialOperationService {

    private final FinancialOperationRepository financialOperationRepository;
    private final MovementRepository movementRepository;
    private final ModelMapper mapper;

    @Override
    public void run(MovementDto movementDto) {
        MovementModel movement = mapper.map(movementDto, MovementModel.class);
        movementRepository.save(movement);

        movement.setEffectiveness(true);
        FinancialOperationModel financialOperation = new FinancialOperationModel(
                null, movementDto.getFinancialOperationType(), movement);

        financialOperationRepository.save(financialOperation);
        log.debug("Financial operation successfully. Movement ID: {}", movement.getId());
    }
}
