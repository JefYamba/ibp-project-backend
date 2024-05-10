package com.jefy.ibp.controllers;

import com.jefy.ibp.dtos.UserRequest;
import com.jefy.ibp.dtos.ChangePasswordRequest;
import com.jefy.ibp.dtos.UserResponse;
import com.jefy.ibp.enums.Role;
import com.jefy.ibp.services.AppUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.jefy.ibp.dtos.Constants.USERS_URL;
import static org.springframework.http.HttpStatus.*;

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
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Get all the users [For admin only]",
            description = "Fetch a page of users",
            responses =  {
                    @ApiResponse(description = "Success", responseCode = "200")
            }
    )
    public ResponseEntity<Page<UserResponse>> getAllUsers(
            @RequestParam(value = "q", defaultValue = "") String searchKey,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return ResponseEntity.status(OK).body(appUserService.getAll(page,size, searchKey));
    }


    @GetMapping("/{user_id}")
    @Operation(
            summary = "Get user by Id [For admin or current logged user only]",
            description = "fetch a user using the id",
            responses =  {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Not authorized", responseCode = "403"),
                    @ApiResponse(description = "Not found", responseCode = "404"),
            }
    )
    public ResponseEntity<UserResponse> get(@PathVariable("user_id") Long userId) {
        return ResponseEntity.status(OK).body(appUserService.getById(userId));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Add a user [For admin only]",
            description = "register a new user",
            responses =  {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Bad request/ Invalid parameter", responseCode = "400"),
                    @ApiResponse(description = "Not acceptable/ Invalid object", responseCode = "406"),
            }
    )
    public ResponseEntity<UserResponse> register(@RequestBody @Valid UserRequest userRequest) {
            return ResponseEntity.status(OK).body(appUserService.create(userRequest));
    }

    @PutMapping
    @Operation(
            summary = "Update a user [For admin or current logged user only]",
            description = "modifies an existing user",
            responses =  {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Not authorized", responseCode = "403"),
                    @ApiResponse(description = "Bad request/ Invalid parameter", responseCode = "400"),
                    @ApiResponse(description = "Not acceptable/ Invalid object", responseCode = "406"),
            }
    )
    public ResponseEntity<UserResponse> update(@RequestBody @Valid UserRequest userRequest) {
            return ResponseEntity.status(OK).body(appUserService.update(userRequest));
    }

    @PutMapping("/{user_id}/update_password")
    @Operation(

            summary = "Change user's password [For current logged user only]",
            description = "Change user's password",
            responses =  {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Not authorized", responseCode = "403"),
                    @ApiResponse(description = "Bad request/ Invalid parameter", responseCode = "400"),
                    @ApiResponse(description = "Not acceptable/ Invalid object", responseCode = "406"),
            }
    )
    public ResponseEntity<String> changePassword(@PathVariable("user_id") Long userId, @RequestBody @Valid ChangePasswordRequest changePasswordRequest) {
        appUserService.changePassWord(userId, changePasswordRequest);
        return ResponseEntity.status(OK).body("user password updated successfully");

    }

    @PutMapping("/{user_id}/update_role")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(

            summary = "Change user's role [For admin only]",
            description = "Change user's role",
            responses =  {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Bad request/ Invalid parameter", responseCode = "400"),
            }
    )
    public ResponseEntity<String> changeRole(@PathVariable("user_id") Long userId, @RequestBody Role role) {
        appUserService.changeRole(userId, role);
        return ResponseEntity.status(OK).body("user role updated successfully");
    }

    @DeleteMapping("/{user_id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Delete a user [For admin only]",
            description = "Delete an existing user",
            responses =  {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Bad request/ Invalid parameter", responseCode = "400"),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500"),
            }
    )
    public ResponseEntity<String> delete(@PathVariable("user_id") Long userId) throws IOException {
        appUserService.delete(userId);
        return ResponseEntity.status(OK).body("user deleted successfully");
    }

    @PostMapping("/{user_id}")
    @Operation(
            summary = "Register user image profile [For admin or current logged user only]",
            description = "set an image profile for an existing user",
            responses =  {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Bad request/ Invalid parameter", responseCode = "400"),
                    @ApiResponse(description = "Not authorized", responseCode = "403"),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500"),
            }
    )
    public ResponseEntity<String> setImageProfile(@PathVariable("user_id") Long userId, @RequestPart MultipartFile image) throws IOException {
        appUserService.setImage(userId, image);
        return ResponseEntity.status(OK).body("user image profile saved successfully");
    }


}
