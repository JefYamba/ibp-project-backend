package com.jefy.ibp.services.impl;

import com.jefy.ibp.dtos.*;
import com.jefy.ibp.entities.AppUser;
import com.jefy.ibp.enums.Role;
import com.jefy.ibp.exceptions.EntityNotValidException;
import com.jefy.ibp.exceptions.RecordNotFoundException;
import com.jefy.ibp.repositories.AppUserRepository;
import com.jefy.ibp.services.AppUserService;
import com.jefy.ibp.validators.AppUserValidator;
import com.jefy.ibp.validators.PasswordValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

import static com.jefy.ibp.dtos.Constants.DEFAULT_PASSWORD;
import static com.jefy.ibp.enums.ClassEntity.APP_USER;
import static com.jefy.ibp.services.impl.ImageService.deleteImageFileFromDirectory;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 01/05/2024
 */
@Service
@RequiredArgsConstructor
@Transactional
public class AppUserServiceImpl implements AppUserService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordValidator passwordValidator;

    @Override
    public Page<AppUserDTO> getAll(int page, int size, String searchKey) {

        if (searchKey == null || searchKey.isBlank()) {
            return appUserRepository.findAll(PageRequest.of(page,size, Sort.by(Sort.Direction.ASC,"firstName","lastName"))).map(AppUserDTO::fromEntity);
        } else {
            return appUserRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(searchKey, searchKey, searchKey,
                    PageRequest.of(page,size, Sort.by(Sort.Direction.ASC,"firstName","lastName"))).map(AppUserDTO::fromEntity
            );
        }
    }

    @Override
    public AppUserDTO getById(Long id) {
        return appUserRepository.findById(id).map(AppUserDTO::fromEntity).orElseThrow(
                () -> new RecordNotFoundException("user does not exist")
        );
    }

    @Override
    public AppUserDTO create(AppUserRequestDTO appUserRequestDTO){
        if (appUserRequestDTO == null)
            throw new IllegalArgumentException("AppUserDTO cannot be null");

        Map<String, String> errors = AppUserValidator.validateUser(appUserRequestDTO);

        if (!errors.isEmpty()){
            throw new EntityNotValidException(errors);
        }
        String encodedPassword = passwordEncoder.encode(DEFAULT_PASSWORD);
        AppUser appUser = AppUser.builder()
                .firstName(appUserRequestDTO.getFirstName())
                .lastName(appUserRequestDTO.getLastName())
                .email(appUserRequestDTO.getEmail())
                .gender(appUserRequestDTO.getGender())
                .birthDate(appUserRequestDTO.getBirthDate())
                .phoneNumber(appUserRequestDTO.getPhoneNumber())
                .address(appUserRequestDTO.getAddress())
                .password(encodedPassword)
                .role(Role.USER)
                .build();

        return AppUserDTO.fromEntity(appUserRepository.save(appUser));
    }

    @Override
    public AppUserDTO update(AppUserRequestDTO appUserRequestDTO)  throws Exception {

        if (appUserRequestDTO == null || appUserRequestDTO.getId() == null)
            throw new IllegalArgumentException("AppUserDTO Or Id cannot be null");

        Map<String, String> errorsUser = AppUserValidator.validateUser(appUserRequestDTO);
        if (!errorsUser.isEmpty()){
            throw new EntityNotValidException(errorsUser);
        }

        AppUser appUser = appUserRepository.findById(appUserRequestDTO.getId())
                .orElseThrow(() -> new RecordNotFoundException("user does not exist"));

        appUser.setFirstName(appUserRequestDTO.getFirstName());
        appUser.setLastName(appUserRequestDTO.getLastName());
        appUser.setEmail(appUserRequestDTO.getEmail());
        appUser.setGender(appUserRequestDTO.getGender());
        appUser.setBirthDate(appUserRequestDTO.getBirthDate());
        appUser.setPhoneNumber(appUserRequestDTO.getPhoneNumber());
        appUser.setAddress(appUserRequestDTO.getAddress());

        return AppUserDTO.fromEntity(appUserRepository.save(appUser));
    }



    @Override
    public void changeRole(Long userId, Role role){
        if (role == null || userId == null) {
            throw new IllegalArgumentException("User Id and role cannot be null");
        }
        AppUser appUser = appUserRepository.findById(userId).orElseThrow(
                () -> new RecordNotFoundException("user does not exist")
        );
        appUser.setRole(role);
        appUserRepository.save(appUser);
    }

    @Override
    public void delete(Long id) throws IOException {
        AppUser appUser = appUserRepository.findById(id).orElseThrow(
                () -> new RecordNotFoundException("user does not exist")
        );
        if (!(appUser.getImage() == null || appUser.getImage().isBlank())){
            deleteImageFileFromDirectory(APP_USER, appUser.getImage());
        }
        appUserRepository.delete(appUser);
    }

    @Override
    public void changePassWord(Long userId, ChangePWRequestDTO changePWRequestDTO){
        if (changePWRequestDTO == null || userId == null ) {
            throw new IllegalArgumentException("User Id and email cannot be null");
        }

        if (!appUserRepository.existsById(userId)) {
            throw new RecordNotFoundException("user does not exist");
        }

        Map<String, String> errors = passwordValidator.validatePassword(changePWRequestDTO, appUserRepository.findById(userId).orElse(null));
        if (!errors.isEmpty()){
            throw new EntityNotValidException(errors);
        }
        AppUser appUser = appUserRepository.findById(userId).orElseThrow(
                () -> new RecordNotFoundException("user does not exist")
        );
        appUser.setPassword(passwordEncoder.encode(changePWRequestDTO.getNewPassword()));
        appUserRepository.save(appUser);
    }

    @Override
    public void setImage(Long userId, MultipartFile image)  throws IOException {

        if (image == null || userId == null || image.isEmpty() ){
            throw new IllegalArgumentException("User Id and image cannot be null");
        }

        AppUser user = appUserRepository.findById(userId).orElseThrow(
                () -> new RecordNotFoundException("user does not exist")
        );

        if (!(user.getImage() == null || user.getImage().isBlank())){
            deleteImageFileFromDirectory(APP_USER, user.getImage());
        }

        user.setImage(ImageService.saveImageInDirectory(APP_USER, userId, image));
        appUserRepository.save(user);
    }


}
