package com.picpay.walletservice.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.picpay.walletservice.dtos.UserDto;
import com.picpay.walletservice.models.UserModel;
import com.picpay.walletservice.repositories.UserRepository;
import com.picpay.walletservice.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
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
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Override
    public Page<UserDto> findAll(Pageable pageable) {
        List<UserDto> usersDto = userRepository.findAll(pageable)
                .getContent().stream()
                .map(user -> mapper.map(user, UserDto.class))
                .toList();

        return new PageImpl<>(usersDto);
    }

    @Override
    public UserDto findById(UUID userId) {
        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));

        return mapper.map(user, UserDto.class);
    }

    @Override
    public UserDto save(UserDto userDto) {
        userDto.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        userDto.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        UserModel user = userRepository.save(mapper.map(userDto, UserModel.class));

        log.info("User saved successfully - User ID: {}", user.getId());
        return mapper.map(user, UserDto.class);
    }

    @Override
    public UserDto update(UUID userId, UserDto userDto) {
        UserModel user = mapper.map(findById(userId), UserModel.class);

        user.setFullName(userDto.getFullName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setEmail(userDto.getEmail());
        user.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

        user = userRepository.save(user);

        log.info("User updated successfully - User ID: {}", user.getId());
        return mapper.map(user, UserDto.class);
    }

    @Override
    public void deleteById(UUID userId) {
        findById(userId);
        userRepository.deleteById(userId);

        log.info("User delete successfully - User ID: {}", userId);
    }
}
