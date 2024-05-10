package com.jefy.ibp.controllers;

import com.jefy.ibp.services.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static com.jefy.ibp.dtos.Constants.IMAGES_URL;
import static com.jefy.ibp.enums.ClassEntity.APP_USER;
import static com.jefy.ibp.enums.ClassEntity.BOOK;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 01/05/2024
 */
@RestController
@RequestMapping(IMAGES_URL)
@RequiredArgsConstructor
@Tag(name = "Image")
public class ImageController {

    private final ImageService imageService;

    @GetMapping(path = "/user/{imageName}", produces = {IMAGE_PNG_VALUE, IMAGE_JPEG_VALUE})
    @Operation(
            summary = "Get User image profile [For admin or current logged only]",
            description = "fetch a user's image profile using the image complete name",
            responses =  {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Bad Request / Invalid parameter", responseCode = "400"),
                    @ApiResponse(description = "Forbidden", responseCode = "403"),
                    @ApiResponse(description = "Internal server error", responseCode = "403"),
            }
    )
    public ResponseEntity<byte[]> getUrlImageUser(@PathVariable String imageName) {

        try {
            return ResponseEntity.status(OK).body(imageService.getImage(APP_USER, imageName));
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(BAD_REQUEST).build();
        } catch (AccessDeniedException e){
            return ResponseEntity.status(UNAUTHORIZED).build();
        } catch (IOException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping(path = "/book/{imageName}", produces = {IMAGE_PNG_VALUE, IMAGE_JPEG_VALUE})
    @Operation(
            summary = "Get Book image cover",
            description = "fetch a book's image cover using the image complete name",
            responses =  {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Bad Request / Invalid parameter", responseCode = "400"),
                    @ApiResponse(description = "Internal server error", responseCode = "403"),
            }
    )
    public ResponseEntity<byte[]> getUrlImageBook(@PathVariable String imageName) {
        try {
            return ResponseEntity.status(OK).body(imageService.getImage(BOOK, imageName));
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(BAD_REQUEST).build();
        } catch (IOException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }



}
