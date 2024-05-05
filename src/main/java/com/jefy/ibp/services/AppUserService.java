package com.jefy.ibp.services;

import com.jefy.ibp.dtos.*;
import com.jefy.ibp.enums.Role;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 01/05/2024
 */
public interface AppUserService {
    List<AppUserDTO> getAll();

    AppUserDTO getById(Long id);

    AppUserDTO register(AppUserRequestDTO appUserRequestDTO);

    AppUserDTO update(AppUserRequestDTO appUserRequestDTO);

    void changeRole(Long userId, Role role);

    void delete(Long id)throws IOException;

    void changePassWord(Long userId, ChangePWRequestDTO changePWRequestDTO);

    void setImage(Long userId, MultipartFile image)  throws IOException;
}