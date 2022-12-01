package com.picpay.walletservice.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.picpay.walletservice.dtos.events.MovementEventDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentDto implements Serializable {

    @NotBlank
    private String userCpf;

    @NotBlank
    private String accountPassword;

    @NotNull
    private Double operationAmount;

    @NotBlank
    private String barcodeNumber;

    @NotBlank
    private String description;
}
