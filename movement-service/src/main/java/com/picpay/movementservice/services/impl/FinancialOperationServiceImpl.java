package com.picpay.movementservice.services.impl;

import com.picpay.movementservice.dtos.MovementDto;
import com.picpay.movementservice.dtos.events.TimelineEventDto;
import com.picpay.movementservice.exceptions.FinancialOperationException;
import com.picpay.movementservice.models.FinancialOperationModel;
import com.picpay.movementservice.models.MovementModel;
import com.picpay.movementservice.publishers.TimelineEventPublisher;
import com.picpay.movementservice.repositories.FinancialOperationRepository;
import com.picpay.movementservice.services.FinancialOperationService;
import com.picpay.movementservice.services.utils.UtilsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class FinancialOperationServiceImpl implements FinancialOperationService {

    private final FinancialOperationRepository financialOperationRepository;
    private final TimelineEventPublisher timelineEventPublisher;
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
            timelineEventPublisher.publisher(createTimelineEventDto(movementDto));
            throw new FinancialOperationException();
        }

        log.debug("Financial operation successfully. Movement ID: {}", movement.getId());
    }

    private TimelineEventDto createTimelineEventDto(MovementDto movementDto) {
        TimelineEventDto timelineEventDto = mapper.map(movementDto, TimelineEventDto.class);
        timelineEventDto.setEffectiveness(false);
        timelineEventDto.setMovementType(movementDto.getType().name());
        return timelineEventDto;
    }
}
