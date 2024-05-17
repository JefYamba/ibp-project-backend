package com.jefy.ibp.dtos;

import com.jefy.ibp.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 17/05/2024
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoleRequest {
    private Role role;
}
