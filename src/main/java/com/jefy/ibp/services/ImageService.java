package com.jefy.ibp.services;

import com.jefy.ibp.enums.ClassEntity;

import java.io.IOException;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 08/05/2024
 */
public interface ImageService {

    byte[] getImage(ClassEntity entity, Long imageOwnerId) throws IOException;
}
