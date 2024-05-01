package com.jefy.ibp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 01/05/2024
 */

@Getter
@AllArgsConstructor
public enum Role {
    USER("USER"),
    ADMIN("ADMIN");

    private final String name;

}
