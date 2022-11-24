package com.picpay.walletservice.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.picpay.walletservice.dtos.UserDto;
import com.picpay.walletservice.services.UserService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/users")
@Log4j2
public class UserController {

    private final UserService userService;

    @GetMapping
    @JsonView(UserDto.UserView.List.class)
    public ResponseEntity<Page<UserDto>> getAll(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        log.debug("GET request to search a users page.");
        return ResponseEntity.ok(userService.findAll(pageable));
    }

    @GetMapping("/{id}")
    @JsonView(UserDto.UserView.List.class)
    public ResponseEntity<UserDto> getOne(@PathVariable(value = "id") UUID userId) {
        log.debug("GET request to lookup a user with ID: {}", userId);
        return ResponseEntity.ok(userService.findById(userId));
    }

    @PostMapping("/signup")
    @JsonView(UserDto.UserView.List.class)
    public ResponseEntity<UserDto> register(@RequestBody
                                                      @Validated(UserDto.UserView.Registration.class)
                                                      @JsonView(UserDto.UserView.Registration.class) UserDto userDto) {
        log.debug("POST request to save a user: {}", userDto);

        userDto = userService.save(userDto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/users/{id}")
                .buildAndExpand(userDto.getId())
                .toUri();

        return ResponseEntity
                .created(uri)
                .body(userDto);
    }

    @PutMapping("/{id}")
    @JsonView(UserDto.UserView.List.class)
    public ResponseEntity<UserDto> update(@PathVariable("id") UUID userId,
                                          @RequestBody @Validated(UserDto.UserView.Update.class)
                                          @JsonView(UserDto.UserView.Update.class) UserDto userDto) {
        log.debug("PUT request to updated a user: {}", userDto);
        return ResponseEntity.ok(userService.update(userId, userDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") UUID userId) {
        log.debug("DELETE request to delete a user with ID: {}", userId);
        userService.deleteById(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User deleted successfully.");
    }
}
