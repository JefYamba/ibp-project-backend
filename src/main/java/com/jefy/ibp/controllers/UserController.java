package com.jefy.ibp.controllers;

import com.jefy.ibp.dtos.AppUserRequestDTO;
import com.jefy.ibp.dtos.ChangePWRequestDTO;
import com.jefy.ibp.dtos.ResponseDTO;
import com.jefy.ibp.enums.Role;
import com.jefy.ibp.exceptions.EntityNotValidException;
import com.jefy.ibp.exceptions.OperationNotAuthorizedException;
import com.jefy.ibp.exceptions.RecordNotFoundException;
import com.jefy.ibp.services.AppUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

import static com.jefy.ibp.dtos.Constants.USERS_URL;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

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
    public ResponseEntity<ResponseDTO> getAllUsers(
            @RequestParam(value = "q", defaultValue = "") String searchKey,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return ResponseEntity.status(OK).body(ResponseDTO.builder()
                .timeStamp(LocalDateTime.now())
                .status(OK)
                .statusCode(OK.value())
                .message("users fetched successfully")
                .data(of("users", appUserService.getAll(page,size, searchKey)))
                .build()
        );
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
    public ResponseEntity<ResponseDTO> get(@PathVariable("user_id") Long userId) {

        try {
            return ResponseEntity.status(OK).body(ResponseDTO.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(OK)
                    .statusCode(OK.value())
                    .message("user fetched successfully")
                    .data(of("user", appUserService.getById(userId)))
                    .build()

            );
        } catch (RecordNotFoundException e){
            return ResponseEntity.status(BAD_REQUEST).body(ResponseDTO.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(NOT_FOUND)
                    .statusCode(NOT_FOUND.value())
                    .message("user not found")
                    .errors(of("errors", e.getMessage()))
                    .build()
            );
        } catch (OperationNotAuthorizedException e){
            return ResponseEntity.status(FORBIDDEN).body(ResponseDTO.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(FORBIDDEN)
                    .statusCode(FORBIDDEN.value())
                    .message("not authorized operation")
                    .errors(of("errors", e.getMessage()))
                    .build()
            );
        }
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
    public ResponseEntity<ResponseDTO> register(@RequestBody AppUserRequestDTO appUserRequestDTO) {

        try {
            return ResponseEntity.status(OK).body(ResponseDTO.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(CREATED)
                    .statusCode(CREATED.value())
                    .message("user added successfully")
                    .data(of("user", appUserService.create(appUserRequestDTO)))
                    .build()

            );
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(BAD_REQUEST).body(ResponseDTO.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(BAD_REQUEST)
                    .statusCode(BAD_REQUEST.value())
                    .message("Could not create an new user")
                    .errors(of("errors", e.getMessage()))
                    .build()
            );
        } catch (EntityNotValidException e){
            return ResponseEntity.status(NOT_ACCEPTABLE).body(ResponseDTO.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(NOT_ACCEPTABLE)
                    .statusCode(NOT_ACCEPTABLE.value())
                    .message("Could not create an new user, all the required field must be correctly filled")
                    .errors(of("errors", e.getErrors()))
                    .build()
            );
        }

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
    public ResponseEntity<ResponseDTO> update(@RequestBody AppUserRequestDTO appUserRequestDTO) {

        try {
            return ResponseEntity.status(OK).body(ResponseDTO.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(OK)
                    .statusCode(OK.value())
                    .message("user updated successfully")
                    .data(of("user", appUserService.update(appUserRequestDTO)))
                    .build()

            );
        } catch (IllegalArgumentException | RecordNotFoundException e){
            return ResponseEntity.status(BAD_REQUEST).body(ResponseDTO.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(BAD_REQUEST)
                    .statusCode(BAD_REQUEST.value())
                    .message("Could not updated user")
                    .errors(of("errors", e.getMessage()))
                    .build()
            );
        } catch (EntityNotValidException e){
            return ResponseEntity.status(NOT_ACCEPTABLE).body(ResponseDTO.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(NOT_ACCEPTABLE)
                    .statusCode(NOT_ACCEPTABLE.value())
                    .message("Could not update an new user, all the required field must be correctly filled")
                    .errors(of("errors", e.getErrors()))
                    .build()
            );
        } catch (OperationNotAuthorizedException e){
            return ResponseEntity.status(FORBIDDEN).body(ResponseDTO.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(FORBIDDEN)
                    .statusCode(FORBIDDEN.value())
                    .message("not authorized operation")
                    .errors(of("errors", e.getMessage()))
                    .build()
            );
        }
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
    public ResponseEntity<ResponseDTO> changePassword(@PathVariable("user_id") Long userId, @RequestBody ChangePWRequestDTO changePWRequestDTO) {

        try {
            appUserService.changePassWord(userId,changePWRequestDTO);
            return ResponseEntity.status(OK).body(ResponseDTO.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(OK)
                    .statusCode(OK.value())
                    .message("user password updated successfully")
                    .build()
            );
        } catch (IllegalArgumentException | RecordNotFoundException e){
            return ResponseEntity.status(BAD_REQUEST).body(ResponseDTO.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(BAD_REQUEST)
                    .statusCode(BAD_REQUEST.value())
                    .message("Could not update user password")
                    .errors(of("errors", e.getMessage()))
                    .build()
            );
        } catch (EntityNotValidException e){
            return ResponseEntity.status(NOT_ACCEPTABLE).body(ResponseDTO.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(NOT_ACCEPTABLE)
                    .statusCode(NOT_ACCEPTABLE.value())
                    .message("Could not update user password, all the required field must be correctly filled")
                    .errors(of("errors", e.getErrors()))
                    .build()
            );
        } catch (OperationNotAuthorizedException e){
            return ResponseEntity.status(FORBIDDEN).body(ResponseDTO.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(FORBIDDEN)
                    .statusCode(FORBIDDEN.value())
                    .message("not authorized operation")
                    .errors(of("errors", e.getMessage()))
                    .build()
            );
        }
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
    public ResponseEntity<ResponseDTO> changeRole(@PathVariable("user_id") Long userId, @RequestBody Role role) {
        try {
            appUserService.changeRole(userId, role);
            return ResponseEntity.status(OK).body(ResponseDTO.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(OK)
                    .statusCode(OK.value())
                    .message("user role updated successfully")
                    .build()
            );
        } catch (IllegalArgumentException | RecordNotFoundException e){
            return ResponseEntity.status(BAD_REQUEST).body(ResponseDTO.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(BAD_REQUEST)
                    .statusCode(BAD_REQUEST.value())
                    .message("Could not update user role")
                    .errors(of("errors", e.getMessage()))
                    .build()
            );
        }
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
    public ResponseEntity<ResponseDTO> delete(@PathVariable("user_id") Long userId) {

        try {
            appUserService.delete(userId);
            return ResponseEntity.status(OK).body(ResponseDTO.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(OK)
                    .statusCode(OK.value())
                    .message("user deleted successfully")
                    .build()

            );
        }catch (RecordNotFoundException e){
            return ResponseEntity.status(BAD_REQUEST).body(ResponseDTO.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(BAD_REQUEST)
                    .statusCode(BAD_REQUEST.value())
                    .message("user does not exist")
                    .errors(of("errors", e.getMessage()))
                    .build()
            );
        } catch (IOException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ResponseDTO.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(INTERNAL_SERVER_ERROR)
                    .statusCode(INTERNAL_SERVER_ERROR.value())
                    .message("Could not delete the user, problem occurred on deleting the image")
                    .errors(of("errors", e.getMessage()))
                    .build()
            );
        }
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
    public ResponseEntity<ResponseDTO> setImageProfile(@PathVariable("user_id") Long userId, @RequestPart MultipartFile image) {
        try {
            appUserService.setImage(userId, image);
            return ResponseEntity.status(OK).body(ResponseDTO.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(OK)
                    .statusCode(OK.value())
                    .message("user image profile saved successfully")
                    .build()
            );
        } catch (IllegalArgumentException | RecordNotFoundException e){
            return ResponseEntity.status(BAD_REQUEST).body(ResponseDTO.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(BAD_REQUEST)
                    .statusCode(BAD_REQUEST.value())
                    .message("Could not save user image profile")
                    .errors(of("errors", e.getMessage()))
                    .build()
            );
        } catch (IOException e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ResponseDTO.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(INTERNAL_SERVER_ERROR)
                    .statusCode(INTERNAL_SERVER_ERROR.value())
                    .message("Could not user image profile")
                    .errors(of("errors", e.getMessage()))
                    .build()
            );
        }  catch (OperationNotAuthorizedException e){
            return ResponseEntity.status(FORBIDDEN).body(ResponseDTO.builder()
                    .timeStamp(LocalDateTime.now())
                    .status(FORBIDDEN)
                    .statusCode(FORBIDDEN.value())
                    .message("not authorized operation")
                    .errors(of("errors", e.getMessage()))
                    .build()
            );
        }
    }


}
