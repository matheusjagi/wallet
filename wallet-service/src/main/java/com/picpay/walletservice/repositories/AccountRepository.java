package com.picpay.walletservice.repositories;

import com.picpay.walletservice.enums.AccountStatus;
import com.picpay.walletservice.models.AccountModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<AccountModel, UUID> {

    Boolean existsByUserId(UUID userId);

    Optional<AccountModel> findByUserCpfAndStatus(String cpf, AccountStatus status);

    Optional<AccountModel> findByNumberAndAgencyAndBankNumberAndStatus(String number, String agency,
                                                                             String bankNumber, AccountStatus status);
    @Modifying(clearAutomatically = true)
    @Query("update AccountModel account set account.amount = account.amount + :transactionAmount " +
            "where account.id = :accountId")
    void increaseAccountBalance(@Param("accountId") UUID accountId,
                                @Param("transactionAmount") Double transactionAmount);

    @Modifying(clearAutomatically = true)
    @Query("update AccountModel account set account.amount = account.amount - :transactionAmount " +
            "where account.id = :accountId")
    void decreaseAccountBalance(@Param("accountId") UUID accountId,
                                @Param("transactionAmount") Double transactionAmount);
}
