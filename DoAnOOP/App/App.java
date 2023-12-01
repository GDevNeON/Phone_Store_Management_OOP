package App;

import Controller.Login;
import View.QuanLyBanHangUI;
import View.QuanLyKhoHangUI;
import View.QuanLyNhanSuUI;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class App {
    // Hàm main check đăng nhập theo phân quyền đã cho
    public static void main(String[] args) throws FileNotFoundException {
        Login login = new Login();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("WELCOME TO PHONE-STORE-MANAGEMENT SYSTEM: ");
            System.out.println("Username: ");
            String User = sc.nextLine();
            System.out.println("Password: ");
            String Pass = sc.nextLine();

            int check = login.check(User, Pass);

            if (check == 0) {
                System.out.println("Invalid Username or Password ! Please Retry !.");
            }
            if (check == 1) {
                new QuanLyBanHangUI();
            }
            if (check == 2) {
                new QuanLyNhanSuUI();
            }
            if (check == 3) {
                new QuanLyKhoHangUI();
            }
        }
    }
}
