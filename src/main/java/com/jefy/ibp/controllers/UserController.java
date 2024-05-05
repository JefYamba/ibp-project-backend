package com.jefy.ibp.controllers;

import com.jefy.ibp.dtos.AppUserDTO;
import com.jefy.ibp.dtos.AppUserRequestDTO;
import com.jefy.ibp.dtos.ChangePWRequestDTO;
import com.jefy.ibp.enums.Role;
import com.jefy.ibp.exceptions.EntityNotValidException;
import com.jefy.ibp.exceptions.RecordNotFoundException;
import com.jefy.ibp.services.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jefy.ibp.dtos.Constants.USERS_URL;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 01/05/2024
 */
@RestController
@RequestMapping(USERS_URL)
@RequiredArgsConstructor
public class UserController {
    private final AppUserService appUserService;


    @GetMapping
    public ResponseEntity<List<AppUserDTO>> getAllUsers() {
        return ResponseEntity.ok(appUserService.getAll());
    }


    @GetMapping("/{user_id}")
    public ResponseEntity<AppUserDTO> getUser(@PathVariable("user_id") Long userId) {
        return ResponseEntity.ok(appUserService.getById(userId));
    }


    @PostMapping
    public ResponseEntity<AppUserDTO> registerUser(@RequestBody AppUserRequestDTO appUserRequestDTO) {
        return ResponseEntity.ok(appUserService.register(appUserRequestDTO));
    }


    @PutMapping
    public ResponseEntity<AppUserDTO> updateUser(@RequestBody AppUserRequestDTO appUserRequestDTO) {
        return ResponseEntity.ok(appUserService.update(appUserRequestDTO));
    }

    @PutMapping("/{user_id}/update_password")
    public ResponseEntity<Map<String,String>> changePasswordUser(@PathVariable("user_id") Long userId, @RequestBody ChangePWRequestDTO changePWRequestDTO) {
        Map<String,String> response = new HashMap<>();
        try {
            appUserService.changePassWord(userId,changePWRequestDTO);
            response.put("response", "password changed successfully");
            return ResponseEntity.ok(response);
        } catch (RecordNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            response.put("error",e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (EntityNotValidException e) {
            return ResponseEntity.badRequest().body(e.getErrors());
        } catch (Exception e) {
            response.put("errors",e.getMessage());

            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PutMapping("/{user_id}/update_role")
    public ResponseEntity<Map<String,String>> changeRoleUser(@PathVariable("user_id") Long userId, @RequestBody Role role) {
        Map<String,String> response = new HashMap<>();
        try {
            appUserService.changeRole(userId, role);
            response.put("response", "role changed successfully");
            return ResponseEntity.ok(response);
        } catch (RecordNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (EntityNotValidException e) {
            return ResponseEntity.badRequest().body(e.getErrors());
        } catch (Exception e) {
            response.put("errors",e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }


    @DeleteMapping("/{user_id}")
    public ResponseEntity<Map<String,String>> deleteUser(@PathVariable("user_id") Long userId) {
        Map<String,String> response = new HashMap<>();
        try {
            appUserService.delete(userId);
            response.put("response", "role changed successfully");
            return ResponseEntity.ok(response);
        } catch (RecordNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            response.put("errors",e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }


    @PostMapping("/{user_id}")
    public ResponseEntity<Map<String,String>> setUserImageProfile(@PathVariable("user_id") Long userId, @RequestPart MultipartFile image) {
        Map<String,String> response = new HashMap<>();
        System.out.println(image.getOriginalFilename());
        try {
            appUserService.setImage(userId, image);
            response.put("response", "role changed successfully");
            return ResponseEntity.ok(response);
        } catch (RecordNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            response.put("errors",e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }


}
