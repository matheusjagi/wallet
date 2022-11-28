package com.picpay.walletservice.enums.strategies;

import com.picpay.walletservice.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public enum MovementStrategy {

    BANK_TRANSFER{
        @Override
        public void updateAmount(AccountRepository accountRepository, UUID accountId, Double transactionAmount) {
            accountRepository.increaseAccountBalance(accountId, transactionAmount);
        }
    },
    PAYMENT{
        @Override
        public void updateAmount(AccountRepository accountRepository, UUID accountId, Double transactionAmount) {
            accountRepository.decreaseAccountBalance(accountId, transactionAmount);
        }
    },
    WITHDRAW {
        @Override
        public void updateAmount(AccountRepository accountRepository, UUID accountId, Double transactionAmount) {
            accountRepository.decreaseAccountBalance(accountId, transactionAmount);
        }
    },
    DEPOSIT {
        @Override
        public void updateAmount(AccountRepository accountRepository, UUID accountId, Double transactionAmount) {
            accountRepository.increaseAccountBalance(accountId, transactionAmount);
        }
    };

    public abstract void updateAmount(AccountRepository accountRepository, UUID accountId, Double transactionAmount);
}
