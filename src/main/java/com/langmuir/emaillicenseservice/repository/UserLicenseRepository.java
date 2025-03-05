package com.langmuir.emaillicenseservice.repository;

import com.langmuir.emaillicenseservice.entity.UserLicense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLicenseRepository extends JpaRepository<UserLicense, Long> {
  UserLicense findByEmailAndLicenseKey(String email, String licenseKey);
}
