package com.picpay.walletservice.builders;

import com.picpay.walletservice.dtos.MovementDto;
import com.picpay.walletservice.enums.FinancialOperationType;
import com.picpay.walletservice.enums.MovementType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Component
public class MovementBuilder {

    public static MovementDto build(MovementType movementType,
                                       Boolean effecttiveness,
                                       FinancialOperationType financialOperationType) {
        UUID id = UUID.randomUUID();

        return MovementDto.builder()
                .id(id)
                .movementDate(LocalDateTime.now(ZoneId.of("UTC")))
                .description("description")
                .transactionAmount(1000D)
                .type(movementType)
                .effectiveness(effecttiveness)
                .previousAmount(500D)
                .currentAmount(1000D)
                .sourceAccountId(id)
                .financialOperationType(financialOperationType)
                .targetAccountId(UUID.randomUUID())
                .barcodeNumber("34191.09834 89775.140323 17339.090007 3 91550000082129")
                .build();
    }

    public static MovementDto instance(MovementType movementType,
                                    Boolean effecttiveness,
                                    FinancialOperationType financialOperationType) {
        UUID id = UUID.randomUUID();

        return new MovementDto(
                id,
                LocalDateTime.now(ZoneId.of("UTC")),
                "description",
                1000D,
                movementType,
                effecttiveness,
                500D,
                1000D,
                id,
                financialOperationType,
                UUID.randomUUID(),
                "34191.09834 89775.140323 17339.090007 3 91550000082129"
        );
    }
}
