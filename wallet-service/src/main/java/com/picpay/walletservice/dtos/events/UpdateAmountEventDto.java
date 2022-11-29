package com.picpay.walletservice.dtos.events;

import com.picpay.walletservice.enums.MovementType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAmountEventDto {

    private UUID accountId;

    private Double amount;

    private MovementType movementType;
}
