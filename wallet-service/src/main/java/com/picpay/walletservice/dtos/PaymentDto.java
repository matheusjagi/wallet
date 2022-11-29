package com.picpay.walletservice.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.picpay.walletservice.dtos.events.MovementEventDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentDto implements Serializable {

    @NotBlank
    private String userCpf;

    @NotNull
    private Integer accountPassword;

    @NotNull
    private Double operationAmount;

    @NotBlank
    private String barcodeNumber;

    @NotBlank
    private String description;
}
