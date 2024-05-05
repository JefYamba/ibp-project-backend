package com.jefy.ibp.dtos;

import com.jefy.ibp.enums.Gender;
import lombok.*;

import java.time.LocalDate;

import static com.jefy.ibp.enums.ClassEntity.APP_USER;
import static com.jefy.ibp.utils.ImageUtility.getUrl;

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
public class AppUserRequestDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private LocalDate birthDate;
    private String phoneNumber;
    private String address;
    private String email;
}
