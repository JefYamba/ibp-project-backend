package com.jefy.ibp.controllers;

import com.jefy.ibp.dtos.AppUserDTO;
import com.jefy.ibp.dtos.ChangePWRequestDTO;
import com.jefy.ibp.enums.Role;
import com.jefy.ibp.exceptions.EntityNotValidException;
import com.jefy.ibp.exceptions.RecordNotFoundException;
import com.jefy.ibp.services.AppUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(
            description = "get all the user from the database",
            summary = "fetch all users",
            responses = { @ApiResponse( description = "Success", responseCode = "200") }
    )
    @GetMapping
    public ResponseEntity<List<AppUserDTO>> getAllUsers() {
        return ResponseEntity.ok(appUserService.getAll());
    }

    @Operation(
            description = "get a user from the database",
            summary = "fetch user by id",
            responses = {
                    @ApiResponse( description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Not found", responseCode = "404")
            }
    )
    @GetMapping("/{user_id}")
    public ResponseEntity<AppUserDTO> getUser(@PathVariable("user_id") Long userId) {
        try {
            return ResponseEntity.ok(appUserService.getById(userId));
        } catch (RecordNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(
            description = "register user",
            summary = "register user",
            responses = {
                    @ApiResponse( description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Bad request", responseCode = "400")
            }
    )
    @PostMapping
    public ResponseEntity<AppUserDTO> registerUser(@RequestBody AppUserDTO appUserDTO) {
        try {
            return ResponseEntity.ok(appUserService.register(appUserDTO));
        } catch (EntityNotValidException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(
            description = "update the user",
            summary = "update user",
            responses = {
                    @ApiResponse( description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Not found", responseCode = "404"),
                    @ApiResponse(description = "Bad request", responseCode = "400")
            }
    )
    @PutMapping
    public ResponseEntity<AppUserDTO> updateUser(@RequestBody AppUserDTO appUserDTO) {
        try {
            return ResponseEntity.ok(appUserService.update(appUserDTO));
        } catch (RecordNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (EntityNotValidException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    @Operation(
            description = "change the user password with by user id",
            summary = "change role password",
            responses = {
                    @ApiResponse( description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Not found", responseCode = "404"),
                    @ApiResponse(description = "Bad request", responseCode = "400")
            }
    )
    @PutMapping("/{user_id}/update_password")
    public ResponseEntity<Map<String,String>> changePasswordUser(@PathVariable("user_id") Long userId, @RequestBody ChangePWRequestDTO changePWRequestDTO) {
        Map<String,String> response = new HashMap<>();
        try {
            appUserService.changePassWord(userId,changePWRequestDTO);
            response.put("message", "password changed successfully");
            return ResponseEntity.ok(response);
        } catch (RecordNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            response.put("error",e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (EntityNotValidException e) {
            return ResponseEntity.badRequest().body(e.getErrors());
        } catch (Exception e) {
            response.put("error",e.getMessage());

            return ResponseEntity.internalServerError().body(response);
        }
    }


    @Operation(
            description = "change the user role with by user id",
            summary = "change role user",
            responses = {
                    @ApiResponse( description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Not found", responseCode = "404"),
                    @ApiResponse(description = "Bad request", responseCode = "400")
            }
    )
    @PutMapping("/{user_id}/update_role")
    public ResponseEntity<Map<String,String>> changeRoleUser(@PathVariable("user_id") Long userId, @RequestBody Role role) {
        Map<String,String> response = new HashMap<>();
        try {
            appUserService.changeRole(userId, role);
            response.put("message", "role changed successfully");
            return ResponseEntity.ok(response);
        } catch (RecordNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (EntityNotValidException e) {
            return ResponseEntity.badRequest().body(e.getErrors());
        } catch (Exception e) {
            response.put("error",e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @Operation(
            description = "delete user by user id",
            summary = "delete user",
            responses = {
                    @ApiResponse( description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Not found", responseCode = "404"),
            }
    )
    @DeleteMapping("/{user_id}")
    public ResponseEntity<Map<String,String>> deleteUser(@PathVariable("user_id") Long userId) {
        Map<String,String> response = new HashMap<>();
        try {
            appUserService.delete(userId);
            response.put("message", "role changed successfully");
            return ResponseEntity.ok(response);
        } catch (RecordNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            response.put("error",e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @Operation(
            description = "set user image profile using his id",
            summary = "set user image profile",
            responses = {
                    @ApiResponse( description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Not found", responseCode = "404"),
            }
    )
    @PostMapping("/{user_id}")
    public ResponseEntity<Map<String,String>> setUserImageProfile(@PathVariable("user_id") Long userId, @RequestPart MultipartFile image) {
        Map<String,String> response = new HashMap<>();
        System.out.println(image.getOriginalFilename());
        try {
            appUserService.setImage(userId, image);
            response.put("message", "role changed successfully");
            return ResponseEntity.ok(response);
        } catch (RecordNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            response.put("error",e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }


}
