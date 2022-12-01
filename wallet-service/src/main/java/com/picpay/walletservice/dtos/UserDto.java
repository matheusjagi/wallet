package com.picpay.walletservice.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto implements Serializable {

    public interface UserView {
        public static interface Registration {}
        public static interface Update {}
        public static interface List {}
    }

    @JsonView(UserView.List.class)
    private UUID id;

    @NotBlank(groups = {UserView.Registration.class, UserView.Update.class})
    @Email(groups = {UserView.Registration.class, UserView.Update.class})
    @JsonView({UserView.Registration.class, UserView.Update.class, UserView.List.class})
    private String email;

    @NotBlank(groups = {UserView.Registration.class, UserView.Update.class})
    @Size(min = 3, max = 150, groups = {UserView.Registration.class, UserView.Update.class})
    @JsonView({UserView.Registration.class, UserView.Update.class, UserView.List.class})
    private String fullName;

    @NotBlank(groups = {UserView.Registration.class, UserView.Update.class})
    @Size(min = 14, max = 14, groups = {UserView.Registration.class, UserView.Update.class})
    @JsonView({UserView.Registration.class, UserView.Update.class, UserView.List.class})
    private String phoneNumber;

    @NotBlank(groups = UserView.Registration.class)
    @Size(min = 11, max = 11, groups = UserView.Registration.class)
    @JsonView(UserView.Registration.class)
    private String cpf;

    @JsonView(UserView.List.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime creationDate;

    @JsonView(UserView.List.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime lastUpdateDate;
}
