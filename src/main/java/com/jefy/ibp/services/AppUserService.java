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
    Page<AppUserDTO> getAll(int page, int size, String searchKey);

    AppUserDTO getById(Long id);

    AppUserDTO create(AppUserRequestDTO appUserRequestDTO);

    AppUserDTO update(AppUserRequestDTO appUserRequestDTO);

    void changeRole(Long userId, Role role);

    void delete(Long id)throws IOException;

    void changePassWord(Long userId, ChangePWRequestDTO changePWRequestDTO);

    void setImage(Long userId, MultipartFile image)  throws IOException;
}
