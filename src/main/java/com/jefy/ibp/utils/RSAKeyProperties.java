package com.jefy.ibp.utils;

import io.swagger.v3.oas.annotations.info.Contact;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 01/05/2024
 */
@Component
public class RSAKeyProperties {
    private RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;

    public RSAKeyProperties() {
        KeyPair pair = KeyGeneratorUtility.generateKeyPair();
        this.publicKey = (RSAPublicKey) pair.getPublic();
        this.privateKey = (RSAPrivateKey) pair.getPrivate();
    }

    public RSAPublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(RSAPublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public RSAPrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(RSAPrivateKey privateKey) {
        this.privateKey = privateKey;
    }
}