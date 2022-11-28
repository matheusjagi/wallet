package com.picpay.walletservice.dtos.events;

import com.picpay.walletservice.enums.MovementType;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateAmountEventDto {

    private UUID accountId;

    private Double amount;

    private MovementType movementType;
}
