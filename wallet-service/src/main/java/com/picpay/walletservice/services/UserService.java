package com.picpay.walletservice.services;

import com.picpay.walletservice.dtos.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserService {

    Page<UserDto> findAll(Pageable pageable);

    UserDto findById(UUID userId);

    UserDto save(UserDto userDto);

    UserDto update(UUID userId, UserDto userDto);

    void deleteById(UUID userId);
}
