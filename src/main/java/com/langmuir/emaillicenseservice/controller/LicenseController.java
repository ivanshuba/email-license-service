package com.langmuir.emaillicenseservice.controller;

import com.langmuir.emaillicenseservice.service.LicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/licenses")
public class LicenseController {

  @Autowired
  private LicenseService licenseService;

  @PostMapping("/generate")
  public ResponseEntity<String> generateLicense(@RequestBody String email) {
    String licenseKey = licenseService.generateLicenseKey(email);
    return ResponseEntity.ok(licenseKey);
  }

  public ResponseEntity<Boolean> validateLicense(@RequestParam String email, @RequestParam String licenseKey) {
    boolean isValidLicense = licenseService.validateLicense(email, licenseKey);
    return ResponseEntity.ok(isValidLicense);
  }
}
