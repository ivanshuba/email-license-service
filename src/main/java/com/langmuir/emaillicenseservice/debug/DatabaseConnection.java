package com.langmuir.emaillicenseservice.debug;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

  public static void main(String[] args) {
    String url = "jdbc:postgresql://localhost:5432/db";
    String user = "user";
    String password = "password";

    try (Connection connection = DriverManager.getConnection(url, user, password)) {
      System.out.println("Connected to PostgreSQL successfully!");
    } catch (SQLException e) {
      System.err.println("Connection to PostgreSQL failed: " + e.getMessage());
      e.printStackTrace();
    }
  }
}