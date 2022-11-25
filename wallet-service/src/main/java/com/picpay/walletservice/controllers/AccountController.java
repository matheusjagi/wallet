package com.picpay.walletservice.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.picpay.walletservice.dtos.AccountDto;
import com.picpay.walletservice.services.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/accounts")
@Log4j2
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    @JsonView(AccountDto.AccountView.List.class)
    public ResponseEntity<Page<AccountDto>> getAll(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        log.debug("GET request to search a account page.");
        return ResponseEntity.ok(accountService.findAll(pageable));
    }

    @GetMapping("/{id}")
    @JsonView(AccountDto.AccountView.List.class)
    public ResponseEntity<AccountDto> getOne(@PathVariable(value = "id") UUID accountId) {
        log.debug("GET request to lookup a account with ID: {}", accountId);
        return ResponseEntity.ok(accountService.findById(accountId));
    }

    @PostMapping
    @JsonView(AccountDto.AccountView.List.class)
    public ResponseEntity<AccountDto> register(@RequestBody @Validated(AccountDto.AccountView.Registration.class)
                                                   @JsonView(AccountDto.AccountView.Registration.class) AccountDto accountDto) {
        log.debug("POST request to save a account: {}", accountDto);

        accountDto = accountService.create(accountDto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/accounts/{id}")
                .buildAndExpand(accountDto.getId())
                .toUri();

        return ResponseEntity
                .created(uri)
                .body(accountDto);
    }

    @PatchMapping("/{id}")
    @JsonView(AccountDto.AccountView.List.class)
    public ResponseEntity<AccountDto> updateTypeAccount(@PathVariable("id") UUID accountId,
                                                        @RequestBody @Validated(AccountDto.AccountView.Update.class)
                                                        @JsonView(AccountDto.AccountView.Update.class) AccountDto accountDto) {
        log.debug("PATCH request to updated type account: {}", accountDto);
        return ResponseEntity.ok(accountService.updateTypeAccount(accountId, accountDto));
    }

    @PatchMapping("/{id}/balance")
    @JsonView(AccountDto.AccountView.List.class)
    public ResponseEntity<AccountDto> updateBalance(@PathVariable("id") UUID accountId,
                                                    @RequestBody @Validated(AccountDto.AccountView.BalanceUpdate.class)
                                                    @JsonView(AccountDto.AccountView.BalanceUpdate.class) AccountDto accountDto) {
        log.debug("PATCH request to updated balance account: {}", accountDto);
        return ResponseEntity.ok(accountService.updateAccountAmount(accountId, accountDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") UUID accountId) {
        log.debug("DELETE request to delete a account with ID: {}", accountId);
        accountService.deleteById(accountId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Account deleted successfully.");
    }
}
