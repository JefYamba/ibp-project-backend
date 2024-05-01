package com.jefy.ibp.dtos;

import org.springframework.beans.factory.annotation.Value;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 01/05/2024
 */
public interface Constants {
    String DEFAULT_PASSWORD = "password";
    String APP_ROOT_URL = "ibp/v1";

    String USERS_IMAGES_URL = APP_ROOT_URL + "/user";
    String BOOKS_IMAGES_URL = APP_ROOT_URL + "/book";

    String AUTHENTIFICATION_URL = APP_ROOT_URL + "/auth";
    String USERS_URL = APP_ROOT_URL + "/users";
    String BOOKS_URL = APP_ROOT_URL + "/books";
    String IMAGES_URL = APP_ROOT_URL + "/images";
}
