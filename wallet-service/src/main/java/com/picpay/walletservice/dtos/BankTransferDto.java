package com.picpay.walletservice.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BankTransferDto implements Serializable {

    @NotBlank
    private String userCpf;

    @NotBlank
    private String accountPassword;

    @NotNull
    private Double operationAmount;

    @NotBlank
    private String description;

    @NotBlank
    private String accountNumber;

    @NotBlank
    private String accountAgency;

    @NotBlank
    private String bankNumber;
}
