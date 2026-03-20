package com.prashant.smatpersonalfinance.repository;

import com.prashant.smatpersonalfinance.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity,Long> {
    Optional<ProfileEntity> findByActivatedToken(String activatedToken);
    Optional<ProfileEntity> findByEmail(String email);
}
