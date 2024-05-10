package com.jefy.ibp.services.impl;

import com.jefy.ibp.entities.AppUser;
import com.jefy.ibp.enums.ClassEntity;
import com.jefy.ibp.enums.Role;
import com.jefy.ibp.repositories.AppUserRepository;
import com.jefy.ibp.repositories.BookRepository;
import com.jefy.ibp.services.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

import static com.jefy.ibp.enums.ClassEntity.APP_USER;
import static com.jefy.ibp.utils.ImageUtility.getImageExtension;
import static com.jefy.ibp.utils.ImageUtility.getImagePath;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 01/05/2024
 */
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final AppUserRepository appUserRepository;
    private final BookRepository bookRepository;

    @Override
    public byte[] getImage(ClassEntity entity, String imageName) throws IOException{
        if (imageName == null || imageName.isBlank() || imageName.isEmpty()){
            throw new IllegalArgumentException("the image name is null or empty");
        }

        if (entity ==APP_USER){
            if (appUserRepository.existsByImage(imageName)){
                AppUser loggedUser = appUserRepository.getAppUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
                if (!Objects.equals(loggedUser.getId(), appUserRepository.findByImage(imageName).getId()) && loggedUser.getRole() != Role.ADMIN ){
                    throw new AccessDeniedException("Operation not authorized");
                }

                return Files.readAllBytes(getImagePath(entity, imageName));
            } else {
                throw new IllegalArgumentException("The is no user with this image " + imageName);
            }
        } else {
            if (bookRepository.existsByImage(imageName)){
                return Files.readAllBytes(getImagePath(entity, imageName));
            } else {
                throw new IllegalArgumentException("The is no book with this image " + imageName);
            }
        }
    }

    public static  String saveImageInDirectory(ClassEntity entity, Long id, MultipartFile image) throws IOException {
        String imageCompleteName = String.format("%s.%s",id, getImageExtension(image));
        Path imagePath = getImagePath(entity, imageCompleteName);
        Files.copy(image.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);

        return imageCompleteName;
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
