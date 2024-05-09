package com.jefy.ibp.services;

import com.jefy.ibp.dtos.*;
import com.jefy.ibp.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 01/05/2024
 */
public interface AppUserService {
    Page<UserResponse> getAll(int page, int size, String searchKey);

    UserResponse getById(Long id);

    UserResponse create(UserRequest userRequest);

    UserResponse update(UserRequest userRequest);

    void changeRole(Long userId, Role role);

    void delete(Long id)throws IOException;

    void changePassWord(Long userId, ChangePasswordRequest changePasswordRequest);

    void setImage(Long userId, MultipartFile image)  throws IOException;
}
