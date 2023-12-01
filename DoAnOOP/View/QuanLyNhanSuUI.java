package View;

import java.io.FileNotFoundException;
import java.util.Scanner;

import Controller.QuanLyTaiKhoan;
import Controller.QuanLyNhanVien;
import Controller.QuanLyNguoiQuanLy;
import Controller.Validation;


public class QuanLyNhanSuUI {
    public static Scanner input = new Scanner(System.in);
    public static void listFunctionsView() {
        System.out.println("---------Quản lý nhân sự---------");
        System.out.println("1.Quản lý tài khoản");
        System.out.println("2.Quản lý nhân viên");
        System.out.println("3.Quản lý người quản lý");
        System.out.println("0.Thoát chương trình.");
        System.out.println("-------------------------------------");
    }

    public static void quanLyTaiKhoanUI() throws FileNotFoundException {
        QuanLyTaiKhoan qltk = new QuanLyTaiKhoan();
        int subCheck = 0;
        do {
            System.out.println("Quản lý tài khoản:");
            System.out.println("1.Xem danh sách tài khoản");
            System.out.println("2.Thêm tài khoản");
            System.out.println("3.Sửa tài khoản");
            System.out.println("4.Xóa tài khoản");
            System.out.println("5.Tìm kiếm tài khoản");
            System.out.println("0.Thoát");
            System.out.println("Mời nhập: ");
            subCheck = input.nextInt();
            switch (subCheck) {
                case 1 -> qltk.Read();
                case 2 -> qltk.Create();
                case 3 -> qltk.Update();
                case 4 -> qltk.Delete();
                case 5 -> qltk.Search_byCategory();
                case 0 -> System.out.println("Thoát quản lý tài khoản");
                default -> System.out.println("\nBạn nhập sai chức năng, vui lòng nhập lại \n");
            }
        } while (subCheck != 0);
    }

    public static void quanLyNhanVienUI() throws FileNotFoundException {
        QuanLyNhanVien qlnv = new QuanLyNhanVien();
        int subCheck = 0;
        do {
            System.out.println("Quản lý nhân viên:");
            System.out.println("1.Xem danh sách nhân viên");
            System.out.println("2.Thêm nhân viên");
            System.out.println("3.Sửa nhân viên");
            System.out.println("4.Xóa nhân viên");
            System.out.println("5.Tìm kiếm nhân viên");
            System.out.println("0.Thoát");
            System.out.println("Mời nhập: ");
            subCheck = input.nextInt();
            switch (subCheck) {
                case 1 -> qlnv.Read();
                case 2 -> qlnv.Create();
                case 3 -> qlnv.Update();
                case 4 -> qlnv.Delete();
                case 5 -> qlnv.Search_byCategory();
                case 0 -> System.out.println("Thoát quản lý nhân viên");
                default -> System.out.println("\nBạn nhập sai chức năng, vui lòng nhập lại \n");
            }
        } while (subCheck != 0);
    }
    public static void quanLyNguoiQuanLyUI() throws FileNotFoundException {
        QuanLyNguoiQuanLy qlnql = new QuanLyNguoiQuanLy();
        int subCheck = 0;
        do {
            System.out.println("Quản lý người quản lý:");
            System.out.println("1.Xem danh sách người quản lý");
            System.out.println("2.Thêm người quản lý");
            System.out.println("3.Sửa người quản lý");
            System.out.println("4.Xóa người quản lý");
            System.out.println("5.Tìm kiếm người quản lý");
            System.out.println("0.Thoát");
            System.out.println("Mời nhập: ");
            subCheck = input.nextInt();
            switch (subCheck) {
                case 1 -> qlnql.Read();
                case 2 -> qlnql.Create();
                case 3 -> qlnql.Update();
                case 4 -> qlnql.Delete();
                case 5 -> qlnql.Search_byCategory();
                case 0 -> System.out.println("Thoát quản lý người quản lý");
                default -> System.out.println("\nBạn nhập sai chức năng, vui lòng nhập lại \n");
            }
        } while (subCheck != 0);
    }
    public QuanLyNhanSuUI() throws FileNotFoundException{
        int check = 0;
        do {
            listFunctionsView();
            System.out.print("Chọn chức năng: ");
            check = input.nextInt();
            switch (check) {
                case 1 -> {
                    quanLyTaiKhoanUI();
                }
                case 2 -> {
                    quanLyNhanVienUI();
                }
                case 3 -> {
                    quanLyNguoiQuanLyUI();
                }
                case 0 -> {
                    System.out.println("\nThoát chương trình quản lý khách hàng thành công");
                }
                default -> {
                    System.out.println("\nNhập sai chức năng, vui lòng nhập lại!\n");
                }
            }
        } while (check != 0);
    }

}
