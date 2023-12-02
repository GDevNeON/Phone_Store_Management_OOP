package controllers;

import java.io.FileNotFoundException;
import java.util.Scanner;

import models.TaiKhoan;

public class Login {

  public static TaiKhoan[] acc;
  public static Scanner input = new Scanner(System.in);
  private static Login instance;

  public static Login getInstance() {
    if (instance == null) {
      instance = new Login();
    }
    return instance;
  }

  private Login() {
  }

  public int check(String username, String password) {
    try {
      String[] result = Stream.read("Database/TaiKhoan.txt");
      acc = new TaiKhoan[result.length];
      for (int i = 0; i < result.length; i++) {
        String[] row = result[i].split(";");
        acc[i] = new TaiKhoan(row[0], row[1], row[2], row[3]);
      }

      for (int j = 0; j < acc.length; j++) {
        if (acc[j].getUsername().equals(username) && acc[j].getPassword().equals(password)) {
          if (acc[j].getPosition().equals("employee")) {
            return 1;
          }
          if (acc[j].getPosition().equals("manager")) {
            return 2;
          }
          if (acc[j].getPosition().equals("admin")) {
            return 3;
          }
        }
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return 0;
  }
}