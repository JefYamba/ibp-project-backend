package com.jefy.ibp.services;

import com.jefy.ibp.dtos.*;
import com.jefy.ibp.enums.Role;
import com.jefy.ibp.exceptions.EntityNotValidException;
import com.jefy.ibp.exceptions.RecordNotFoundException;
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

    AppUserDTO getById(Long id) throws RecordNotFoundException;

    AppUserDTO register(AppUserDTO appUserDto)throws EntityNotValidException, IllegalArgumentException ;

    AppUserDTO update(AppUserDTO appUserDto) throws IllegalArgumentException, EntityNotValidException, RecordNotFoundException;

    void changeRole(Long userId, Role role)throws EntityNotValidException, RecordNotFoundException ;

    void delete(Long id) throws RecordNotFoundException, IOException;

    void changePassWord(Long userId, ChangePWRequestDTO changePWRequestDTO)throws IllegalArgumentException, EntityNotValidException, RecordNotFoundException ;

    void setImage(Long userId, MultipartFile image)  throws RecordNotFoundException, IOException;
}