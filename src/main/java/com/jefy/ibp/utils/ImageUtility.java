package com.jefy.ibp.utils;

import com.jefy.ibp.dtos.Constants;
import com.jefy.ibp.enums.ClassEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;


/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 01/05/2024
 */
public class ImageUtility {

    public static String getUrl(ClassEntity entity, String completeName) {
        String url = switch (entity) {
            case APP_USER -> Constants.USERS_IMAGES_URL + "/" + completeName;
            case BOOK -> Constants.BOOKS_IMAGES_URL + "/" + completeName;
        };
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(url).toUriString();
    }

    public static Path getImagePath(ClassEntity entity, String imageName) {
        String absolutePath = switch (entity) {
            case APP_USER -> "src/main/resources/static/images/users";
            case BOOK -> "src/main/resources/static/images/books";
        };
        return Paths.get(absolutePath + File.separator + imageName);
    }

    public static String getImageExtension(MultipartFile image) {
        return Arrays.stream(Objects.requireNonNull(
                image.getOriginalFilename()
        ).split("\\.")).toList().getLast();
    }
}
