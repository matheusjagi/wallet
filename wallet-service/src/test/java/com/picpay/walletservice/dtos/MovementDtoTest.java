package com.picpay.walletservice.dtos;

import com.picpay.walletservice.builders.MovementBuilder;
import com.picpay.walletservice.enums.FinancialOperationType;
import com.picpay.walletservice.enums.MovementType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MovementDtoTest {

    private MovementDto movementDto;
    private MovementDto movementDtoBuilder;

    @BeforeEach
    void setUp() {
        movementDtoBuilder = MovementBuilder
                .build(MovementType.FINANCIAL_OPERATION, Boolean.TRUE, FinancialOperationType.DEPOSIT);

        movementDto = MovementBuilder
                .instance(MovementType.FINANCIAL_OPERATION, Boolean.TRUE, FinancialOperationType.DEPOSIT);
    }

    @Test
    void createDtoTest() {
        assertNotNull(movementDto);
        assertNotNull(movementDto.getId());
        assertNotNull(movementDto.getMovementDate());
        assertNotNull(movementDto.getDescription());
        assertNotNull(movementDto.getType());
        assertNotNull(movementDto.getEffectiveness());
        assertNotNull(movementDto.getPreviousAmount());
        assertNotNull(movementDto.getCurrentAmount());
        assertNotNull(movementDto.getSourceAccountId());
        assertNotNull(movementDto.getFinancialOperationType());
        assertNotNull(movementDto.getTargetAccountId());
        assertNotNull(movementDto.getBarcodeNumber());
    }

    @Test
    void builderTest() {
        assertNotNull(movementDtoBuilder);
        assertNotNull(movementDtoBuilder.getId());
        assertNotNull(movementDtoBuilder.getMovementDate());
        assertNotNull(movementDtoBuilder.getDescription());
        assertNotNull(movementDtoBuilder.getType());
        assertNotNull(movementDtoBuilder.getEffectiveness());
        assertNotNull(movementDtoBuilder.getPreviousAmount());
        assertNotNull(movementDtoBuilder.getCurrentAmount());
        assertNotNull(movementDtoBuilder.getSourceAccountId());
        assertNotNull(movementDtoBuilder.getFinancialOperationType());
        assertNotNull(movementDtoBuilder.getTargetAccountId());
        assertNotNull(movementDtoBuilder.getBarcodeNumber());
    }
}