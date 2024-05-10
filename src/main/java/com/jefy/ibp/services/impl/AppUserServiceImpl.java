package com.jefy.ibp.services.impl;

import com.jefy.ibp.dtos.*;
import com.jefy.ibp.entities.AppUser;
import com.jefy.ibp.enums.Role;
import com.jefy.ibp.exceptions.EntityNotValidException;
import com.jefy.ibp.exceptions.OperationNotAuthorizedException;
import com.jefy.ibp.exceptions.RecordNotFoundException;
import com.jefy.ibp.repositories.AppUserRepository;
import com.jefy.ibp.services.AppUserService;
import com.jefy.ibp.validators.PasswordValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.Set;

import static com.jefy.ibp.dtos.Constants.DEFAULT_PASSWORD;
import static com.jefy.ibp.enums.ClassEntity.APP_USER;
import static com.jefy.ibp.services.impl.ImageServiceImpl.deleteImageFileFromDirectory;

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
    public Page<UserResponse> getAll(int page, int size, String searchKey) {

        if (searchKey == null || searchKey.isBlank()) {
            return appUserRepository.findAll(PageRequest.of(page,size, Sort.by(Sort.Direction.ASC,"firstName","lastName"))).map(UserResponse::fromEntity);
        } else {
            return appUserRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(searchKey, searchKey, searchKey,
                    PageRequest.of(page,size, Sort.by(Sort.Direction.ASC,"firstName","lastName"))).map(UserResponse::fromEntity
            );
        }
    }

    @Override
    public UserResponse getById(Long id) {
        AppUser loggedUser = appUserRepository.getAppUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (!Objects.equals(loggedUser.getId(), id) && loggedUser.getRole() != Role.ADMIN )
            throw new OperationNotAuthorizedException("this operation is not allowed");

        return appUserRepository.findById(id).map(UserResponse::fromEntity).orElseThrow(
                () -> new RecordNotFoundException("user does not exist")
        );
    }

    @Override
    public UserResponse create(UserRequest userRequest){
        if (userRequest == null)
            throw new IllegalArgumentException("UserResponse cannot be null");

        String encodedPassword = passwordEncoder.encode(DEFAULT_PASSWORD);
        AppUser appUser = AppUser.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .email(userRequest.getEmail())
                .gender(userRequest.getGender())
                .birthDate(userRequest.getBirthDate())
                .phoneNumber(userRequest.getPhoneNumber())
                .address(userRequest.getAddress())
                .password(encodedPassword)
                .role(Role.USER)
                .build();

        return UserResponse.fromEntity(appUserRepository.save(appUser));
    }

    @Override
    public UserResponse update(UserRequest userRequest) {

        if (userRequest == null)
            throw new IllegalArgumentException("user cannot be null");

        AppUser loggedUser = appUserRepository.getAppUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (!Objects.equals(loggedUser.getId(), userRequest.getId()) && loggedUser.getRole() != Role.ADMIN )
            throw new OperationNotAuthorizedException("this operation is not allowed");

        if (userRequest.getId() == null)
            throw new IllegalArgumentException("id cannot be null");


        AppUser appUser = appUserRepository.findById(userRequest.getId())
                .orElseThrow(() -> new RecordNotFoundException("user does not exist"));

        appUser.setFirstName(userRequest.getFirstName());
        appUser.setLastName(userRequest.getLastName());
        appUser.setEmail(userRequest.getEmail());
        appUser.setGender(userRequest.getGender());
        appUser.setBirthDate(userRequest.getBirthDate());
        appUser.setPhoneNumber(userRequest.getPhoneNumber());
        appUser.setAddress(userRequest.getAddress());

        return UserResponse.fromEntity(appUserRepository.save(appUser));
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
    public void changePassWord(Long userId, ChangePasswordRequest changePasswordRequest){
        AppUser loggedUser = appUserRepository.getAppUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        if (!Objects.equals(loggedUser.getId(), userId))
            throw new OperationNotAuthorizedException("this operation is not allowed");

        if (changePasswordRequest == null) {
            throw new IllegalArgumentException("User Id and email cannot be null");
        }

        if (!appUserRepository.existsById(userId)) {
            throw new RecordNotFoundException("user does not exist");
        }

        Set<String> errors = passwordValidator.validatePassword(changePasswordRequest, loggedUser);
        if (!errors.isEmpty()){
            throw new EntityNotValidException(errors);
        }
        AppUser appUser = appUserRepository.findById(userId).orElseThrow(
                () -> new RecordNotFoundException("user does not exist")
        );
        appUser.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        appUserRepository.save(appUser);
    }

    @Override
    public void setImage(Long userId, MultipartFile image)  throws IOException {

        AppUser loggedUser = appUserRepository.getAppUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (!Objects.equals(loggedUser.getId(), userId) && loggedUser.getRole() != Role.ADMIN )
            throw new OperationNotAuthorizedException("this operation is not allowed");


        if (image == null || userId == null || image.isEmpty() ){
            throw new IllegalArgumentException("User Id and image cannot be null");
        }

        AppUser user = appUserRepository.findById(userId).orElseThrow(
                () -> new RecordNotFoundException("user does not exist")
        );

        if (!(user.getImage() == null || user.getImage().isBlank())){
            deleteImageFileFromDirectory(APP_USER, user.getImage());
        }

        user.setImage(ImageServiceImpl.saveImageInDirectory(APP_USER, userId, image));
        appUserRepository.save(user);
    }


}
