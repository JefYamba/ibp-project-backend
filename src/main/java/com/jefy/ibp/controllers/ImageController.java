package com.jefy.ibp.controllers;

import com.jefy.ibp.enums.ClassEntity;
import com.jefy.ibp.services.impl.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static com.jefy.ibp.dtos.Constants.IMAGES_URL;
import static com.jefy.ibp.enums.ClassEntity.APP_USER;
import static com.jefy.ibp.enums.ClassEntity.BOOK;
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
public class ImageController {


    @Operation(
            description = "get user image profile",
            summary = "get user image profile",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Bad request / Invalid parameter", responseCode = "400")
            }
    )
    @GetMapping(path = "/user/{imageName}", produces = {IMAGE_PNG_VALUE, IMAGE_JPEG_VALUE})
    public ResponseEntity<byte[]> getUrlImageUser(@PathVariable String imageName) {
        return sendImage(imageName, APP_USER);
    }

    @Operation(
            description = "get book image image cover",
            summary = "get book image image cover",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Bad request / Invalid parameter", responseCode = "400")
            }
    )
    @GetMapping(path = "/book/{imageName}", produces = {IMAGE_PNG_VALUE, IMAGE_JPEG_VALUE})
    public ResponseEntity<byte[]> getUrlImageBook(@PathVariable String imageName) {
        return sendImage(imageName, BOOK);
    }

    private ResponseEntity<byte[]> sendImage(@PathVariable String imageName, ClassEntity classEntity) {
        try {
            return ResponseEntity.ok(ImageService.getImage(classEntity, imageName));
        } catch (IOException unused){
            return ResponseEntity.badRequest().build();
        }
    }

}
