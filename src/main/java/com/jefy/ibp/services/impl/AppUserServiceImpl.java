package com.jefy.ibp.services.impl;

import com.jefy.ibp.dtos.*;
import com.jefy.ibp.entities.AppUser;
import com.jefy.ibp.enums.ClassEntity;
import com.jefy.ibp.enums.Role;
import com.jefy.ibp.exceptions.EntityNotValidException;
import com.jefy.ibp.exceptions.RecordNotFoundException;
import com.jefy.ibp.repositories.AppUserRepository;
import com.jefy.ibp.services.AppUserService;
import com.jefy.ibp.validators.AppUserValidator;
import com.jefy.ibp.validators.PasswordValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @Override
    public List<AppUserDTO> getAll() {
        return appUserRepository.findAll().stream()
                .map(AppUserDTO::fromEntity)
                .toList();
    }

    @Override
    public AppUserDTO getById(Long id) throws RecordNotFoundException {
        return appUserRepository.findById(id).map(AppUserDTO::fromEntity).orElseThrow(
                () -> new RecordNotFoundException("user does not exist")
        );
    }

    @Override
    public AppUserDTO register(AppUserDTO appUserDto) throws EntityNotValidException, IllegalArgumentException {
        if (appUserDto == null)
            throw new IllegalArgumentException("AppUserDTO cannot be null");

        Map<String, String> errors = AppUserValidator.validateUser(appUserDto);

        if (!errors.isEmpty()){
            throw new EntityNotValidException(errors);
        }
        String encodedPassword = passwordEncoder.encode(DEFAULT_PASSWORD);
        AppUser appUser = AppUserDTO.toEntity(appUserDto);
        appUser.setPassword(encodedPassword);
        appUser.setRole(Role.USER);
        AppUser savedUser = appUserRepository.save(appUser);
        return AppUserDTO.fromEntity(savedUser);
    }

    @Override
    public AppUserDTO update(AppUserDTO appUserDto) throws IllegalArgumentException, EntityNotValidException, RecordNotFoundException {

        if (appUserDto == null || appUserDto.getId() == null)
            throw new IllegalArgumentException("AppUserDTO Or Id cannot be null");

        Map<String, String> errorsUser = AppUserValidator.validateUser(appUserDto);
        if (!errorsUser.isEmpty()){
            throw new EntityNotValidException(errorsUser);
        }

        AppUser appUser = appUserRepository.findById(appUserDto.getId())
                .orElseThrow(() -> new RecordNotFoundException("user does not exist"));

        appUser.setFirstName(appUserDto.getFirstName());
        appUser.setLastName(appUserDto.getLastName());
        appUser.setEmail(appUserDto.getEmail());
        appUser.setGender(appUserDto.getGender());
        appUser.setBirthDate(appUserDto.getBirthDate());
        appUser.setPhoneNumber(appUserDto.getPhoneNumber());
        appUser.setAddress(appUserDto.getAddress());

        AppUser savedUser = appUserRepository.save(appUser);
        return AppUserDTO.fromEntity(savedUser);
    }



    @Override
    public void changeRole(Long userId, Role role) throws EntityNotValidException, RecordNotFoundException {
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
    public void delete(Long id) throws RecordNotFoundException, IOException {
        AppUser appUser = appUserRepository.findById(id).orElseThrow(
                () -> new RecordNotFoundException("user does not exist")
        );
        if (!(appUser.getImage() == null || appUser.getImage().isBlank())){
            deleteImageFileFromDirectory(APP_USER, appUser.getImage());
        }
        appUserRepository.delete(appUser);
    }

    @Override
    public void changePassWord(Long userId, ChangePWRequestDTO changePWRequestDTO) throws IllegalArgumentException, EntityNotValidException, RecordNotFoundException {
        if (changePWRequestDTO == null || userId == null) {
            throw new IllegalArgumentException("User Id and email cannot be null");
        }
        Map<String, String> errors = PasswordValidator.validatePassword(changePWRequestDTO);
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
    public void setImage(Long userId, MultipartFile image) throws RecordNotFoundException, IOException {

        if (image == null || userId == null || image.isEmpty() ){
            throw new IllegalArgumentException("User Id and image cannot be null");
        }

        AppUser user = appUserRepository.findById(userId).orElseThrow(
                () -> new RecordNotFoundException("user does not exist")
        );

        if (!(user.getImage() == null || user.getImage().isBlank())){
            deleteImageFileFromDirectory(APP_USER, user.getImage());
        }

        String imageUrl = ImageService.saveImageInDirectory(APP_USER, userId, image);
        user.setImage(imageUrl);
        appUserRepository.save(user);
    }


}
