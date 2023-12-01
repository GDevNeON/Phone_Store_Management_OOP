package Controller;

import Entity.Account;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Login {

  public static Account[] acc;
  public static Scanner input = new Scanner(System.in);

  public Login() {
    super();
  }

  public static int check(String username, String password) {
    try {
      String[] result = Stream.read("Database/TaiKhoan.txt");
      acc = new Account[result.length];
      for (int i = 0; i < result.length; i++) {
        String[] row = result[i].split(";");
        acc[i] = new Account(row[0], row[1], row[2], row[3]);
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