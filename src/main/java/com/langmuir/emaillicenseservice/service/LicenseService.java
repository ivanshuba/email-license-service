package com.langmuir.emaillicenseservice.service;

import com.langmuir.emaillicenseservice.entity.UserLicense;
import com.langmuir.emaillicenseservice.repository.UserLicenseRepository;
import java.util.Objects;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LicenseService {
  
  @Autowired
  private UserLicenseRepository userLicenseRepository;
  
  public String generateLicenseKey(String email) {
    String licenseKey = UUID.randomUUID().toString();
    UserLicense userLicense = new UserLicense(email, licenseKey);
    userLicenseRepository.save(userLicense);
    return licenseKey;
  }
  
  public boolean validateLicense(String email, String licenseKey) {
    return Objects.nonNull(userLicenseRepository.findByEmailAndLicenseKey(email, licenseKey));
  }
}
