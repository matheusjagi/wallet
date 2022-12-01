package com.picpay.walletservice.controllers;

import com.picpay.walletservice.builders.AccountBuilder;
import com.picpay.walletservice.builders.BankTransferBuilder;
import com.picpay.walletservice.builders.FinancialOperationBuilder;
import com.picpay.walletservice.builders.PaymentBuilder;
import com.picpay.walletservice.builders.UserBuilder;
import com.picpay.walletservice.clients.MovementClient;
import com.picpay.walletservice.dtos.BankTransferDto;
import com.picpay.walletservice.dtos.FinancialOperationDto;
import com.picpay.walletservice.dtos.PaymentDto;
import com.picpay.walletservice.models.AccountModel;
import com.picpay.walletservice.models.UserModel;
import com.picpay.walletservice.publishers.TimelineEventPublisher;
import com.picpay.walletservice.utils.TestUtil;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class WalletBaseControllerTest {

    private static final String URI = "/wallets";
    public static final Double INITIAL_DEPOSIT = 10000D;
    public static final Double WITHDRAWAL_AMOUNT = 250D;
    public static final Double PAYMENT_AMOUNT = 120D;
    public static final Double BANK_TRANSFER_AMOUNT = 500D;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserBuilder userBuilder;

    @Autowired
    private AccountBuilder accountBuilder;

    @MockBean
    private MovementClient movementClient;

    @MockBean
    private TimelineEventPublisher timelineEventPublisher;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Spy
    ModelMapper mapper = new ModelMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        doNothing().when(movementClient).deposit(any());
        doNothing().when(timelineEventPublisher).publisher(any());
    }

    @Test
    @SneakyThrows
    void deposit() {
        UserModel user = userBuilder.create("11122233344");
        AccountModel account = accountBuilder.create(user);

        FinancialOperationDto financialOperationDto = FinancialOperationBuilder
                .build(user.getCpf(), account.getPassword(), INITIAL_DEPOSIT, account.getNumber());

        mockMvc.perform(post(URI + "/deposit")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(financialOperationDto)))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void withdraw() {
        UserModel user = userBuilder.create("12345678900");
        AccountModel account = accountBuilder.create(user);

        FinancialOperationDto financialOperationDto = FinancialOperationBuilder
                .build(user.getCpf(), account.getPassword(), WITHDRAWAL_AMOUNT, account.getNumber());

        mockMvc.perform(post(URI + "/withdraw")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(financialOperationDto)))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void paymentInvoice() {
        UserModel user = userBuilder.create("77788899910");
        AccountModel account = accountBuilder.create(user);

        PaymentDto paymentDto = PaymentBuilder.build(user.getCpf(), account.getPassword(), PAYMENT_AMOUNT);

        mockMvc.perform(post(URI + "/payment/invoice")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(paymentDto)))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void bankTransfer() {
        UserModel user = userBuilder.create("44455566678");
        AccountModel account = accountBuilder.create(user);

        BankTransferDto bankTransferDto = BankTransferBuilder
                .build(user.getCpf(), account.getPassword(), BANK_TRANSFER_AMOUNT, account.getNumber(),
                        account.getAgency(), account.getBankNumber());

        mockMvc.perform(post(URI + "/transfer")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(bankTransferDto)))
                .andExpect(status().isOk());
    }
}