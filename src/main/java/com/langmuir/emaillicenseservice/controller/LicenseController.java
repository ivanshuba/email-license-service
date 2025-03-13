package com.langmuir.emaillicenseservice.controller;

import com.langmuir.emaillicenseservice.service.LicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/licenses")
public class LicenseController {

  @Autowired
  private LicenseService licenseService;

  @PostMapping("/generate")
  public ResponseEntity<String> generateLicense(@RequestBody String email) {
    String licenseKey = licenseService.generateLicenseKey(email);
    return ResponseEntity.ok(licenseKey);
  }

  @GetMapping("/validate/license")
  public ResponseEntity<Boolean> validateLicense(@RequestParam String email, @RequestParam String licenseKey) {
    boolean isValidLicense = licenseService.validateLicense(email, licenseKey);
    return ResponseEntity.ok(isValidLicense);
  }

  @GetMapping("/validate/email")
  public ResponseEntity<Boolean> validateEmail(@RequestParam String email) {
    boolean isValidEmail = licenseService.validateEmail(email);
    return ResponseEntity.ok(isValidEmail);
  }

  @GetMapping("/buy")
  public String buyLicensePage(@RequestParam("email") String email, Model model) {
    // Add email to the model so that HTML template can display it
    model.addAttribute("email", email);
    // Serve the "buy-license" template
    return "buy-license";
  }

  @PostMapping("/complete-buy")
  public String completeBuy(@RequestParam("email") String email, Model model) {
    // Generate or fetch the license for the provided email address
    String licenseKey = licenseService.generateLicenseKey(email);

    // Send the generated license to the given email address
    licenseService.sendLicenseEmail(email, licenseKey);

    model.addAttribute("email", email);
    model.addAttribute("license", licenseKey);

    // Redirect to the thank-you page with the email parameter
    return "thank-you";
  }

  @GetMapping("/thank-you")
  public String thankYouPage(@RequestParam("email") String email,
                             @RequestParam("license") String license,
                             Model model) {
    // Add the generated license number to the model so that it can be displayed on the thank-you page
    model.addAttribute("email", email);
    model.addAttribute("license", license);
    return "thank-you";
  }
}


