package com.picpay.walletservice.services.impl;

import com.picpay.walletservice.dtos.AccountDto;
import com.picpay.walletservice.dtos.UserDto;
import com.picpay.walletservice.enums.AccountStatus;
import com.picpay.walletservice.enums.AccountType;
import com.picpay.walletservice.models.AccountModel;
import com.picpay.walletservice.models.UserModel;
import com.picpay.walletservice.repositories.AccountRepository;
import com.picpay.walletservice.services.AccountService;
import com.picpay.walletservice.services.UserService;
import com.picpay.walletservice.services.utils.UtilsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserService userService;
    private final ModelMapper mapper;

    @Override
    public Page<AccountDto> findAll(Pageable pageable) {
        List<AccountDto> accountsDto = accountRepository.findAll(pageable)
                .getContent().stream()
                .map(user -> mapper.map(user, AccountDto.class))
                .toList();

        return new PageImpl<>(accountsDto);
    }

    @Override
    public AccountDto findById(UUID accountId) {
        AccountModel account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found."));

        return mapper.map(account, AccountDto.class);
    }

    @Override
    public AccountDto create(AccountDto accountDto) {
        AccountModel account = mapper.map(accountDto, AccountModel.class);

        account.setNumber(UtilsService.createRandomNumber(10L));
        account.setAgency("0009");
        account.setBankNumber("999");
        account.setBalance(0.0);
        account.setStatus(AccountStatus.ACTIVE);
        account.setType(AccountType.valueOf(accountDto.getType()));
        account.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        account.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        account.setUser(new UserModel(accountDto.getUserId()));

        accountRepository.save(account);

        log.info("Account saved successfully - Account ID: {}", account.getId());
        return mapper.map(account, AccountDto.class);
    }

    @Override
    public AccountDto update(UUID accountId, AccountDto accountDto) {
//        UserModel user = mapper.map(findById(userId), UserModel.class);
//
//        user.setFullName(userDto.getFullName());
//        user.setPhoneNumber(userDto.getPhoneNumber());
//        user.setEmail(userDto.getEmail());
//        user.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
//
//        user = accountRepository.save(user);
//
//        log.info("User updated successfully - User ID: {}", user.getId());
//        return mapper.map(user, UserDto.class);
        return null;
    }

    @Override
    public void deleteById(UUID accountId) {
//        findById(userId);
//        accountRepository.deleteById(userId);
//
//        log.info("User delete successfully - User ID: {}", userId);
    }
}
