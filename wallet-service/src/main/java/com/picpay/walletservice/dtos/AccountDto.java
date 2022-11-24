package com.picpay.walletservice.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.picpay.walletservice.enums.AccountStatus;
import com.picpay.walletservice.enums.AccountType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDto implements Serializable {

    public interface AccountView {
        public static interface Registration {}
        public static interface BankDeposit {}
        public static interface List {}
    }

    @JsonView(AccountView.List.class)
    private UUID id;

    @JsonView(AccountView.List.class)
    private String number;

    @JsonView(AccountView.List.class)
    private String agency;

    @JsonView(AccountView.List.class)
    private String bankNumber;

//    @NotBlank(groups = AccountDto.AccountView.BankDeposit.class)
    @JsonView(AccountView.List.class)
    private Double balance;

    @NotBlank(groups = AccountView.Registration.class)
    @JsonView({AccountView.Registration.class, AccountView.List.class})
    private String type;

    @JsonView(AccountView.List.class)
    private AccountStatus status;

    @JsonView(AccountView.List.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime creationDate;

    @JsonView(AccountView.List.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime lastUpdateDate;

    @NotNull(groups = AccountView.Registration.class)
    @JsonView(AccountView.Registration.class)
    private UUID userId;

    @JsonView(AccountView.List.class)
    private String userFullName;
}
