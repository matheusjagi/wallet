package com.picpay.movementservice.dtos.events;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TimelineEventDto {

    private UUID id;

    private UUID userId;

    private UUID sourceAccountId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime movementDate;

    private String description;

    private Double transactionAmount;

    private String movementType;

    private Boolean effectiveness;

    private Double previousAmount;

    private Double currentAmount;

    private String financialOperationType;

    private UUID targetAccountId;

    private String barcodeNumber;
}
