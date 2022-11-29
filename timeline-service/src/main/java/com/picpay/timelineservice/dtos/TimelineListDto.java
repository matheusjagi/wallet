package com.picpay.timelineservice.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TimelineListDto {

    private String id;

    private String userId;

    private String sourceAccountId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private String movementDate;

    private String description;

    private Double transactionAmount;

    private String movementType;

    private Boolean effectiveness;

    private Double previousAmount;

    private Double currentAmount;

    private String financialOperationType;

    private String targetAccountId;

    private String barcodeNumber;
}
