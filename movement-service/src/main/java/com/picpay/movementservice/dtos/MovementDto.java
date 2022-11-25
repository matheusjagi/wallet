package com.picpay.movementservice.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.picpay.movementservice.enums.FinancialOperationType;
import com.picpay.movementservice.enums.MovementType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovementDto implements Serializable {

    public interface MovementView {
        public static interface FinacialOperations {}
        public static interface BankTransfer {}
        public static interface Payments {}
    }

    @JsonView({MovementView.FinacialOperations.class, MovementView.BankTransfer.class,
            MovementView.Payments.class,})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime movementDate;

    @JsonView({MovementView.FinacialOperations.class, MovementView.BankTransfer.class,
            MovementView.Payments.class,})
    private String description;

    @JsonView({MovementView.FinacialOperations.class, MovementView.BankTransfer.class,
            MovementView.Payments.class,})
    private Double transactionAmount;

    @JsonView({MovementView.FinacialOperations.class, MovementView.BankTransfer.class,
            MovementView.Payments.class,})
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private MovementType type;

    @JsonView({MovementView.FinacialOperations.class, MovementView.BankTransfer.class,
            MovementView.Payments.class,})
    private Double previousAmount;

    @JsonView({MovementView.FinacialOperations.class, MovementView.BankTransfer.class,
            MovementView.Payments.class,})
    private Double currentAmount;

    @JsonView({MovementView.FinacialOperations.class, MovementView.BankTransfer.class,
            MovementView.Payments.class,})
    private UUID accountId;

    @NotBlank(groups = MovementView.FinacialOperations.class)
    @JsonView(MovementView.FinacialOperations.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private FinancialOperationType financialOperationType;

    @NotBlank(groups = MovementView.BankTransfer.class)
    @JsonView(MovementView.BankTransfer.class)
    private UUID sourceAccountId;

    @NotBlank(groups = MovementView.BankTransfer.class)
    @JsonView(MovementView.BankTransfer.class)
    private UUID targetAccountId;

    @NotBlank(groups = MovementView.Payments.class)
    @JsonView(MovementView.Payments.class)
    private String barcodeNumber;
}
