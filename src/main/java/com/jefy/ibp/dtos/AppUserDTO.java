package com.jefy.ibp.dtos;

import com.jefy.ibp.entities.AppUser;
import com.jefy.ibp.enums.Gender;
import com.jefy.ibp.enums.Role;
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
public class AppUserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private LocalDate birthDate;
    private String phoneNumber;
    private String address;
    private String image;
    private String email;
    private Role role;

    public static AppUserDTO fromEntity(AppUser appUser) {
        if (appUser == null || appUser.getId() == null) {
            return null;
        }
        return AppUserDTO.builder()
                .id(appUser.getId())
                .firstName(appUser.getFirstName())
                .lastName(appUser.getLastName())
                .gender(appUser.getGender())
                .birthDate(appUser.getBirthDate())
                .phoneNumber(appUser.getPhoneNumber())
                .address(appUser.getAddress())
                .image(
                        (appUser.getImage() == null || appUser.getImage().isBlank())? "" : getUrl(APP_USER,appUser.getImage())
                )
                .email(appUser.getEmail())
                .role(appUser.getRole())
                .build();
    }
}
