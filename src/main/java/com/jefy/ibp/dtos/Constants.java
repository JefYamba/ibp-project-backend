package com.jefy.ibp.dtos;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 01/05/2024
 */
public interface Constants {
    String DEFAULT_PASSWORD = "password";
    String APP_ROOT_URL = "ibp/v1";

    String AUTHENTIFICATION_URL = APP_ROOT_URL + "/auth";
    String USERS_URL = APP_ROOT_URL + "/users";
    String BOOKS_URL = APP_ROOT_URL + "/books";
    String ANNOUNCEMENTS_URL = APP_ROOT_URL + "/announcements";
    String MESSAGES_URL = APP_ROOT_URL + "/messages";
    String IMAGES_URL = APP_ROOT_URL + "/images";

    String USERS_IMAGES_URL = IMAGES_URL + "/user";
    String BOOKS_IMAGES_URL = IMAGES_URL + "/book";
}
