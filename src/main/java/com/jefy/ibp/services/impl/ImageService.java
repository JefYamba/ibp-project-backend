package com.jefy.ibp.services.impl;

import com.jefy.ibp.enums.ClassEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import static com.jefy.ibp.utils.ImageUtility.getImageExtension;
import static com.jefy.ibp.utils.ImageUtility.getImagePath;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 01/05/2024
 */
@Service
public class ImageService {

    public static  String saveImageInDirectory(ClassEntity entity, Long id, MultipartFile image) throws IOException {
        String imageCompleteName = String.format("%s.%s",id, getImageExtension(image));
        Path imagePath = getImagePath(entity, imageCompleteName);
        Files.copy(image.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);

        return imageCompleteName;
    }

    public static byte[] getImage(ClassEntity entity, String imageName) throws IOException {
        return Files.readAllBytes(getImagePath(entity, imageName));
    }


    public static void deleteImageFileFromDirectory(ClassEntity entity, String imageName) throws IOException {
        Path imagePath = getImagePath(entity, imageName);
        if (imagePath.toFile().isFile() && imagePath.toFile().exists()){
            Files.delete(imagePath);
        } else {
            throw new FileNotFoundException(imageName);
        }
    }


}
