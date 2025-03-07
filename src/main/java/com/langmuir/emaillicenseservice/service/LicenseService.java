package com.langmuir.emaillicenseservice.service;

import com.langmuir.emaillicenseservice.entity.UserLicense;
import com.langmuir.emaillicenseservice.repository.UserLicenseRepository;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class LicenseService {

  private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(LicenseService.class);
  
  @Autowired
  private JavaMailSender mailSender;

  @Autowired
  private UserLicenseRepository userLicenseRepository;
  
  public String generateLicenseKey(String email) {
    List<UserLicense> licensesByEmail = userLicenseRepository.findByEmail(email);
    if (Objects.nonNull(licensesByEmail) && !licensesByEmail.isEmpty()) {
      return licensesByEmail.get(0).getLicenseKey();
    }

    String licenseKey = UUID.randomUUID().toString();

    saveLicenseInDatabase(email, licenseKey);

    return licenseKey;
  }

  private void saveLicenseInDatabase(String email, String licenseKey) {
    UserLicense userLicense = new UserLicense(email, licenseKey);
    userLicenseRepository.save(userLicense);
  }

  public boolean validateLicense(String email, String licenseKey) {
    return Objects.nonNull(userLicenseRepository.findByEmailAndLicenseKey(email, licenseKey));
  }

  public boolean validateEmail(String email) {
    return userLicenseRepository.existsByEmail(email);
  }

  public void sendLicenseEmail(String email, String licenseKey) {
    String subject = "Your FireCAM License Key";
    String body = """
        Hello,
        Your license key is: %s
        Thank you for purchasing FireCAM plugin!
        Feel free to contact us at %s for questions.
        """.formatted(licenseKey, "support@langmuir.com");

    sendEmail(email, subject, body);
  }

  private void sendEmail(String to, String subject, String body) {
    // Create a simple mail message
    SimpleMailMessage emailMessage = new SimpleMailMessage();
    emailMessage.setTo(to);
    emailMessage.setSubject(subject);
    emailMessage.setText(body);
    
    try {
      mailSender.send(emailMessage);
    } catch (Exception e) {
      logger.error("Sending email failed: ", e);
    }
  }
}
