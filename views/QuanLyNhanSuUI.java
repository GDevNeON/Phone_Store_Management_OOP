package views;

import java.util.Scanner;

import controllers.QuanLyNguoiQuanLy;
import controllers.QuanLyNhanVien;
import controllers.QuanLyTaiKhoan;

public class QuanLyNhanSuUI {
  private static final Scanner input = new Scanner(System.in);
  private static QuanLyNhanSuUI instance;

  public static QuanLyNhanSuUI getInstance() {
    if (instance == null) {
      instance = new QuanLyNhanSuUI();
    }
    return instance;
  }

  public static void listFunctionsView() {
    System.out.println("---------Quản lý nhân sự---------");
    System.out.println("1.Quản lý tài khoản");
    System.out.println("2.Quản lý nhân viên");
    System.out.println("3.Quản lý người quản lý");
    System.out.println("0.Thoát chương trình.");
    System.out.println("-------------------------------------");
  }

  public static void quanLyTaiKhoanUI() {
    String subCheck = "0";
    do {
      System.out.println("Quản lý tài khoản:");
      System.out.println("1.Xem danh sách tài khoản");
      System.out.println("2.Thêm tài khoản");
      System.out.println("3.Sửa tài khoản");
      System.out.println("4.Xóa tài khoản");
      System.out.println("5.Tìm kiếm tài khoản");
      System.out.println("0.Thoát");
      System.out.println("Mời nhập: ");
      subCheck = input.nextLine();
      switch (subCheck) {
        case "1" -> QuanLyTaiKhoan.getInstance().Read();
        case "2" -> QuanLyTaiKhoan.getInstance().Create();
        case "3" -> QuanLyTaiKhoan.getInstance().Update();
        case "4" -> QuanLyTaiKhoan.getInstance().Delete();
        case "5" -> QuanLyTaiKhoan.getInstance().searchByCategory();
        case "0" -> {
          System.out.println("\t\t\t\t\t\t\t\t THOÁT CHƯƠNG TRÌNH THÀNH CÔNG!");
          subCheck = "0";
        }
        default -> System.out.println("\nBạn nhập sai chức năng, vui lòng nhập lại \n");
      }
    } while (subCheck != "0");
  }

  public static void quanLyNhanVienUI() {
    String subCheck = "0";
    do {
      System.out.println("Quản lý nhân viên:");
      System.out.println("1.Xem danh sách nhân viên");
      System.out.println("2.Thêm nhân viên");
      System.out.println("3.Sửa nhân viên");
      System.out.println("4.Xóa nhân viên");
      System.out.println("5.Tìm kiếm nhân viên");
      System.out.println("0.Thoát");
      System.out.println("Mời nhập: ");
      subCheck = input.nextLine();
      switch (subCheck) {
        case "1" -> QuanLyNhanVien.getInstance().Read();
        case "2" -> QuanLyNhanVien.getInstance().Create();
        case "3" -> QuanLyNhanVien.getInstance().Update();
        case "4" -> QuanLyNhanVien.getInstance().Delete();
        case "5" -> QuanLyNhanVien.getInstance().searchByCategory();
        case "0" -> {
          System.out.println("Thoát quản lý nhân viên");
          subCheck = "0";
        }
        default -> System.out.println("\nBạn nhập sai chức năng, vui lòng nhập lại \n");
      }
    } while (subCheck != "0");
  }

  public static void quanLyNguoiQuanLyUI() {
    String subCheck = "0";
    do {
      System.out.println("Quản lý người quản lý:");
      System.out.println("1.Xem danh sách người quản lý");
      System.out.println("2.Thêm người quản lý");
      System.out.println("3.Sửa người quản lý");
      System.out.println("4.Xóa người quản lý");
      System.out.println("5.Tìm kiếm người quản lý");
      System.out.println("0.Thoát");
      System.out.println("Mời nhập: ");
      subCheck = input.nextLine();
      switch (subCheck) {
        case "1" -> QuanLyNguoiQuanLy.getInstance().Read();
        case "2" -> QuanLyNguoiQuanLy.getInstance().Create();
        case "3" -> QuanLyNguoiQuanLy.getInstance().Update();
        case "4" -> QuanLyNguoiQuanLy.getInstance().Delete();
        case "5" -> QuanLyNguoiQuanLy.getInstance().searchByCategory();
        case "0" -> {
          System.out.println("Thoát quản lý người quản lý");
          subCheck = "0";
        }
        default -> System.out.println("\nBạn nhập sai chức năng, vui lòng nhập lại \n");
      }
    } while (subCheck != "0");
  }

  public QuanLyNhanSuUI() {
    String check = "0";
    do {
      listFunctionsView();
      System.out.print("Chọn chức năng: ");
      check = input.nextLine();
      switch (check) {
        case "1" -> {
          quanLyTaiKhoanUI();
        }
        case "2" -> {
          quanLyNhanVienUI();
        }
        case "3" -> {
          quanLyNguoiQuanLyUI();
        }
        case "0" -> {
          System.out.println("\nThoát chương trình quản lý khách hàng thành công");
          check = "0"; 
        }
        default -> {
          System.out.println("\nNhập sai chức năng, vui lòng nhập lại!\n");
        }
      }
    } while (check != "0");
  }

}
