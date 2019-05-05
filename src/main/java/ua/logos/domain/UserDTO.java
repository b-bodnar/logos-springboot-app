package ua.logos.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    private Long id;

    @NotNull(message = "Field 'NAME' can not be NULL")
    @Size(min = 2, max = 20, message = "Field 'NAME should be between 2 and 20'")
    private String name; // { "name" : "12" }

    @NotNull(message = "Field 'EMAIL' cen not be NULL")
    @Size(min = 20, max = 100, message = "between 20 and 100")
    private String email;

    @NotNull
    @Size
    private String password;

    @NotNull
    @Size
    private String passwordConfirm;

    @NotNull
    @Size
    private String sexType;

    private boolean isDeleted;

    private String image;
}
