package com.jefy.ibp.utils;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 01/05/2024
 */
public class KeyGeneratorUtility {
    public static KeyPair generateKeyPair() {
        KeyPair keyPair;

        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            keyPair = generator.generateKeyPair();

        } catch (Exception e){
            throw new IllegalStateException("Could not generate RSA key pair", e);
        }

        return keyPair;
    }
}
