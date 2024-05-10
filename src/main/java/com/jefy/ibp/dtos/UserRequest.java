package com.jefy.ibp.dtos;

import com.jefy.ibp.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 01/05/2024
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {

    private Long id;

    @NotBlank(message = "Firstname must be filled in")
    private String firstName;

    @NotBlank(message = "Lastname must be filled in")
    private String lastName;
    private Gender gender;
    private LocalDate birthDate;
    private String phoneNumber;
    private String address;

    @Email(
            message = "Email must be like well formated (eg: example@text.com)",
            regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
    )
    @NotBlank(message = "Email must be filled in")
    private String email;

}
