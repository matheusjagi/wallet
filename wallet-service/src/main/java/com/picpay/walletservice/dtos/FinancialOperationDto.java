package com.picpay.walletservice.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FinancialOperationDto implements Serializable {

    public interface FinancialOperationView {
        public static interface Withdraw {}
        public static interface Deposit {}
    }

    @NotBlank(groups = FinancialOperationDto.FinancialOperationView.Withdraw.class)
    @JsonView(FinancialOperationDto.FinancialOperationView.Withdraw.class)
    private String userCpf;

    @NotNull(groups = FinancialOperationDto.FinancialOperationView.Withdraw.class)
    @JsonView(FinancialOperationDto.FinancialOperationView.Withdraw.class)
    private Integer accountPassword;

    @NotNull(groups = {FinancialOperationDto.FinancialOperationView.Withdraw.class,
            FinancialOperationDto.FinancialOperationView.Deposit.class})
    @JsonView({FinancialOperationDto.FinancialOperationView.Withdraw.class,
            FinancialOperationDto.FinancialOperationView.Deposit.class})
    private Double operationAmount;

    @NotBlank(groups = {FinancialOperationDto.FinancialOperationView.Withdraw.class,
            FinancialOperationDto.FinancialOperationView.Deposit.class})
    @JsonView({FinancialOperationDto.FinancialOperationView.Withdraw.class,
            FinancialOperationDto.FinancialOperationView.Deposit.class})
    private String description;

    @NotBlank(groups = FinancialOperationDto.FinancialOperationView.Deposit.class)
    @JsonView(FinancialOperationDto.FinancialOperationView.Deposit.class)
    private String accountNumber;

    @NotBlank(groups = FinancialOperationDto.FinancialOperationView.Deposit.class)
    @JsonView(FinancialOperationDto.FinancialOperationView.Deposit.class)
    private String accountAgency;

    @NotBlank(groups = FinancialOperationDto.FinancialOperationView.Deposit.class)
    @JsonView(FinancialOperationDto.FinancialOperationView.Deposit.class)
    private String bankNumber;
}
