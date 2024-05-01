package com.jefy.ibp.repositories;

import com.jefy.ibp.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 01/05/2024
 */
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String email);
}
