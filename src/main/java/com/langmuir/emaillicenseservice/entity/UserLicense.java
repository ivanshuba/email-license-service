package com.langmuir.emaillicenseservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class UserLicense {

  @Id
  @GeneratedValue(generator = "system-uuid")
  private Long id;

  private String email;
  private String licenseKey;

  public UserLicense() {
  }

  public UserLicense(String email, String licenseKey) {
    this.email = email;
    this.licenseKey = licenseKey;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public String getLicenseKey() {
    return licenseKey;
  }

  public void setLicenseKey(String licenseKey) {
    this.licenseKey = licenseKey;
  }
}
