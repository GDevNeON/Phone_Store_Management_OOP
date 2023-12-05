package App;

import views.QuanLyBanHangUI;
import views.QuanLyKhoHangUI;
import views.QuanLyNhanSuUI;

import java.util.Scanner;

import controllers.Login;

public class App {
  private static Scanner sc = new Scanner(System.in);

  public static void main(String[] args) {
    
    while (true) {
      System.out.println("Chào mừng đến với hệ thống quản lý cửa hàng điện thoại: ");
      System.out.println("Tên đăng nhập: ");
      String user = sc.nextLine();
      System.out.println("Mật khẩu: ");
      String pass = sc.nextLine();
      int check = Login.getInstance().check(user, pass);
      switch (check) {
        case 0:
          System.out.println("Tên đăng nhập hoặc mật khẩu sai. Vui lòng thử lại!");
          break;
        case 1:
          QuanLyBanHangUI.getInstance();
          break;
        case 2:
          QuanLyNhanSuUI.getInstance();
          break;
        case 3:
          QuanLyKhoHangUI.getInstance();
          break;
        default:
          System.out.println("Phân quyền không hợp lệ.");
      }
    }
  }
}