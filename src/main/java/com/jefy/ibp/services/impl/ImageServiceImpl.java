package com.jefy.ibp.services.impl;

import com.jefy.ibp.entities.AppUser;
import com.jefy.ibp.entities.Book;
import com.jefy.ibp.enums.ClassEntity;
import com.jefy.ibp.enums.Role;
import com.jefy.ibp.exceptions.OperationNotAuthorizedException;
import com.jefy.ibp.repositories.AppUserRepository;
import com.jefy.ibp.repositories.BookRepository;
import com.jefy.ibp.services.ImageService;
import lombok.RequiredArgsConstructor;
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
    public byte[] getImage(ClassEntity entity, Long imageOwnerId) throws IOException {

        AppUser loggedUser = appUserRepository.getAppUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (!Objects.equals(loggedUser.getId(), imageOwnerId) && loggedUser.getRole() != Role.ADMIN )
            throw new OperationNotAuthorizedException("this operation is not allowed");

        String imageName = switch (entity){
            case APP_USER -> appUserRepository.findById(imageOwnerId).map(AppUser::getImage).orElseThrow(() -> new IllegalArgumentException("the is no user with id"));
            case BOOK -> bookRepository.findById(imageOwnerId).map(Book::getImage).orElseThrow(() -> new IllegalArgumentException("the is no book with id"));
        };

        if (imageName == null || imageName.isBlank() || imageName.isEmpty()){
            throw new IllegalArgumentException("there is no image for this " + (entity == APP_USER ? "user":"book"));
        }

        return Files.readAllBytes(getImagePath(entity, imageName));
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
