package View;

import Controller.QuanLyHoaDon;
import Controller.QuanLyKhachHang;
import Controller.QuanLyBaoHanh;
import Controller.QuanLyGiamGia;
import Controller.QuanLyThanhToan;
import java.io.FileNotFoundException;
import java.util.Scanner;

import Controller.Validation;

public class QuanLyBanHangUI {

    private static final Scanner input = new Scanner(System.in);

    public static void listFunctionsView() {
        System.out.println("--------- Quản Lý Khách Hàng ---------");
        System.out.println("1.Quản lý khách hàng");
        System.out.println("2.Quản lý hóa đơn");
        System.out.println("3.Quản lý bảo hành");
        System.out.println("4.Quản lý giảm giá");
        System.out.println("5.Quản lý thanh toán");
        System.out.println("0.Đăng xuất.");
        System.out.println("--------------------------------------");
    }

    public static void quanLyKhachHangUI() throws FileNotFoundException {
        QuanLyKhachHang cus = new QuanLyKhachHang();
        int subCheck = 0;
        do {
            System.out.println("Danh Sách chức năng:");
            System.out.println("1.Xem danh sách khách hàng");
            System.out.println("2.Thêm khách hàng");
            System.out.println("3.Sửa khách hàng");
            System.out.println("4.Xóa khách hàng");
            System.out.println("5.Tìm kiếm");
            System.out.println("0.Thoát");
            System.out.println("Mời nhập: ");
            subCheck = input.nextInt();
            switch (subCheck) {
                case 1 -> cus.Read();
                case 2 -> cus.Create();
                case 3 -> cus.Update();
                case 4 -> cus.Delete();
                case 5 -> cus.Search_byCategory();
                case 0 -> System.out.println("\nThoát quản lý khách hàng\n");
                default -> System.out.println("\nBạn nhập sai chức năng, vui lòng nhập lại \n");
            }
        }
        while(subCheck != 0);
    }

    public static void quanLyHoaDon() throws FileNotFoundException {
        QuanLyHoaDon qlhd = new QuanLyHoaDon();
        int subCheck = 0;
        do {
            System.out.println("Danh Sách chức năng:");
            System.out.println("1.Xem danh sách hóa đơn");
            System.out.println("2.Thêm hóa đơn");
            System.out.println("3.Sửa hóa đơn");
            System.out.println("4.Xóa hóa đơn");
            System.out.println("5.Tìm kiếm");
            System.out.println("0.Thoát");
            System.out.println("Mời nhập: ");
            subCheck = input.nextInt();
            switch (subCheck) {
                case 1 -> {
                    qlhd.Read();
                }
                case 2 -> {
                    qlhd.Create();
                }
                case 3 -> {
                    qlhd.Update();
                }
                case 4 -> {
                    qlhd.Delete();
                }
                case 5 -> {
                    qlhd.Search_byCategory();
                }
                case 0 -> {
                    System.out.println("\nThoát quản lý khách hàng\n");
                }
                default -> System.out.println("\nBạn nhập sai chức năng, vui lòng nhập lại \n");
            }
        }
        while(subCheck != 0);
    }
    public static void quanlyBaoHanh() throws FileNotFoundException {

        QuanLyBaoHanh qlbh = new QuanLyBaoHanh();
        int subCheck = 0;
        do {
            System.out.println("Danh Sách chức năng:");
            System.out.println("1.Xem danh sách hóa đơn");
            System.out.println("2.Thêm hóa đơn");
            System.out.println("3.Sửa hóa đơn");
            System.out.println("4.Xóa hóa đơn");
            System.out.println("5.Tìm kiếm");
            System.out.println("0.Thoát");
            System.out.println("Mời nhập: ");
            subCheck = input.nextInt();
            switch (subCheck) {
                case 1 -> {
                    qlbh.Read();
                }
                case 2 -> {
                    qlbh.Create();
                }
                case 3 -> {
                    qlbh.Update();
                }
                case 4 -> {
                    qlbh.Delete();
                }
                case 5 -> {
                    qlbh.Search_byCategory();
                }
                case 0 -> {
                    System.out.println("\nThoát quản lý bảo hành\n");
                }
                default -> System.out.println("\nBạn nhập sai chức năng, vui lòng nhập lại \n");
            }
        }
        while(subCheck != 0);
    }

    public static void quanlyGiamGia() throws FileNotFoundException {

        QuanLyGiamGia qlgg = new QuanLyGiamGia();
        int subCheck = 0;
        do {
            System.out.println("Danh Sách chức năng:");
            System.out.println("1.Xem danh sách sản phẩm được giảm giá");
            System.out.println("2.Thêm sản phẩm bạn muốn giảm giá");
            System.out.println("3.Sửa sản phẩm trạng thái giảm giá");
            System.out.println("4.Xóa sản phẩm");
            System.out.println("5.Tìm kiếm");
            System.out.println("0.Thoát");
            System.out.println("Mời nhập: ");
            subCheck = input.nextInt();
            switch (subCheck) {
                case 1 -> {
                    qlgg.Read();
                }
                case 2 -> {
                    qlgg.Create();
                }
                case 3 -> {
                    qlgg.Update();
                }
                case 4 -> {
                    qlgg.Delete();
                }
                case 5 -> {
                    qlgg.Search_byCategory();
                }
                case 0 -> {
                    System.out.println("\nThoát quản lý bảo hành\n");
                }
                default -> System.out.println("\nBạn nhập sai chức năng, vui lòng nhập lại \n");
            }
        }
        while(subCheck != 0);
    }

    public static void quanlyThanhToan() throws FileNotFoundException {

        QuanLyThanhToan qltt = new QuanLyThanhToan();
        int subCheck = 0;
        do {
            System.out.println("Danh Sách chức năng:");
            System.out.println("1.Xem sản phẩm và danh sách khách hàng thanh toán");
            System.out.println("2.Thêm ");
            System.out.println("3.Sửa ");
            System.out.println("4.Xóa ");
            System.out.println("5.Tìm kiếm");
            System.out.println("0.Thoát");
            System.out.println("Mời nhập: ");
            subCheck = input.nextInt();
            switch (subCheck) {
                case 1 -> {
                    qltt.Read();
                }
                case 2 -> {
                    qltt.Create();
                }
                case 3 -> {
                    qltt.Update();
                }
                case 4 -> {
                    qltt.Delete();
                }
                case 5 -> {
                    qltt.Search_byCategory();
                }
                case 0 -> {
                    System.out.println("\nThoát quản lý thanh toán\n");
                }
                default -> System.out.println("\nBạn nhập sai chức năng, vui lòng nhập lại \n");
            }
        }
        while(subCheck != 0);
    }


    public QuanLyBanHangUI() throws FileNotFoundException {
        int function = 0;
        do {
            listFunctionsView();
            System.out.print("Chọn chức năng: ");
            function = input.nextInt();
            switch(function) {
                case 1 -> {
                    quanLyKhachHangUI();
                }
                case 2 -> {
                    quanLyHoaDon();
                }
                case 3 -> {
                    quanlyBaoHanh();
                }
                case 4 -> {
                    quanlyGiamGia();
                }
                case 5 -> {
                    quanlyThanhToan();
                }
                case 0 ->{
                    System.out.println("\nĐăng xuất thành công");
                }
                default ->{
                    System.out.println("\nNhập sai chức năng, vui lòng nhập lại!\n");
                }
            }
        }
        while (function != 0);
    }
}
