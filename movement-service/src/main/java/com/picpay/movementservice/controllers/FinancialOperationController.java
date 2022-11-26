package com.picpay.movementservice.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.picpay.movementservice.dtos.MovementDto;
import com.picpay.movementservice.services.FinancialOperationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movements/financial-operations")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
@Log4j2
public class FinancialOperationController {

    private final FinancialOperationService financialOperationService;

    @PostMapping("/withdraw")
    public ResponseEntity<Void> withdraw(@RequestBody @Validated(MovementDto.MovementView.FinacialOperations.class)
                                            @JsonView(MovementDto.MovementView.FinacialOperations.class)
                                            MovementDto movementDto) {
        log.debug("POST request to withdraw account: {}", movementDto);
        financialOperationService.run(movementDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/deposit")
    public ResponseEntity<Void> deposit(@RequestBody @Validated(MovementDto.MovementView.FinacialOperations.class)
                                           @JsonView(MovementDto.MovementView.FinacialOperations.class)
                                           MovementDto movementDto) {
        log.debug("POST request to deposit account: {}", movementDto);
        financialOperationService.run(movementDto);
        return ResponseEntity.ok().build();
    }
}
