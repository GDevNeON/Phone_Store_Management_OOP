package views;

import java.util.Scanner;

import controllers.QuanLyChiTietHoaDon;
import controllers.QuanLyChiTietPhieuNhapHang;
import controllers.QuanLyConfigurations;
import controllers.QuanLyNhaCungCap;
import controllers.QuanLyPhieuNhapHang;
import controllers.QuanLySanPham;
import controllers.ThongKeKinhDoanh;

public class QuanLyKhoHangUI {
  private static QuanLyKhoHangUI instance;
  private static final Scanner input = new Scanner(System.in);

  public static QuanLyKhoHangUI getInstance() {
    if (instance == null) {
      instance = new QuanLyKhoHangUI();
    }
    return instance;
  }

  // Hàm xuất ra danh sách chức năng quản lý
  public static void method() {
    System.out.println("---------Danh sách chức năng---------");
    System.out.println("1.Quản lý sản phẩm");
    System.out.println("2.Quản lý nhập hàng");
    System.out.println("3.Quản lý cấu hình sản phẩm");
    System.out.println("4.Quản lý nhà cung cấp");
    System.out.println("5.Quản lý chi tiết phiếu nhập hàng");
    System.out.println("6.Quản lý chi tiết hóa đơn");
    System.out.println("7.Thống kê kinh doanh");
    System.out.println("0.Thoát chương trình.");
    System.out.println("-------------------------------------");
  }

  // Hàm xuất danh sách chức năng và gọi hàm từ lớp quản lý sản phẩm
  public static void quanLySanPhamUI() {
    String subCheck = "";
    do {
      System.out.println("Quản lý sản phẩm:");
      System.out.println("1.Xem danh sách sản phẩm");
      System.out.println("2.Thêm sản phẩm");
      System.out.println("3.Sửa sản phẩm");
      System.out.println("4.Xóa sản phẩm");
      System.out.println("5.Tìm kiếm sản phẩm");
      System.out.println("0.Thoát");
      System.out.println("Mời nhập: ");
      subCheck = input.nextLine();
      switch (subCheck) {
        case "1" -> QuanLySanPham.getInstance().Read();
        case "2" -> QuanLySanPham.getInstance().Create();
        case "3" -> QuanLySanPham.getInstance().Update();
        case "4" -> QuanLySanPham.getInstance().Delete();
        case "5" -> QuanLySanPham.getInstance().searchByCategory();
        case "0" -> {
          System.out.println("Thoát quản lý sản phẩm");
          return;
        }
        default -> System.out.println("\nBạn nhập sai chức năng, vui lòng nhập lại \n");
      }
    } while (subCheck != "0");
  }

  // Hàm xuất danh sách chức năng và gọi hàm từ lớp quản lý phiếu nhập
  public static void quanLyNhapHangUI() {
    String subCheck = "";
    do {
      System.out.println("Quản lý nhập hàng:");
      System.out.println("1.Xem danh sách phiếu nhập");
      System.out.println("2.Thêm phiếu nhập");
      System.out.println("3.Sửa phiếu nhập");
      System.out.println("4.Xóa phiếu nhập");
      System.out.println("5.Tìm kiếm phiếu nhập");
      System.out.println("0.Thoát");
      System.out.println("Mời nhập: ");
      subCheck = input.nextLine();
      switch (subCheck) {
        case "1" -> QuanLyPhieuNhapHang.getInstance().Read();
        case "2" -> QuanLyPhieuNhapHang.getInstance().Create();
        case "3" -> QuanLyPhieuNhapHang.getInstance().Update();
        case "4" -> QuanLyPhieuNhapHang.getInstance().Delete();
        case "5" -> QuanLyPhieuNhapHang.getInstance().searchByCategory();
        case "0" -> {
          System.out.println("Thoát quản lý phiếu nhập");
          return;
        }
        default -> System.out.println("\nBạn nhập sai chức năng, vui lòng nhập lại \n");
      }
    } while (subCheck != "0");
  }

  public static void quanLyNhaCungCapUI() {
    String subCheck = "";
    do {
      System.out.println("Quản lý nhà cung cấp:");
      System.out.println("1.Xem danh sách nhà cung cấp");
      System.out.println("2.Thêm nhà cung cấp");
      System.out.println("3.Sửa nhà cung cấp");
      System.out.println("4.Xóa nhà cung cấp");
      System.out.println("5.Tìm kiếm nhà cung cấp");
      System.out.println("0.Thoát");
      System.out.println("Mời nhập: ");
      subCheck = input.nextLine();
      switch (subCheck) {
        case "1" -> QuanLyNhaCungCap.getInstance().Read();
        case "2" -> QuanLyNhaCungCap.getInstance().Create();
        case "3" -> QuanLyNhaCungCap.getInstance().Update();
        case "4" -> QuanLyNhaCungCap.getInstance().Delete();
        case "5" -> QuanLyNhaCungCap.getInstance().searchByCategory();
        case "0" -> {
          System.out.println("Thoát quản lý nhà cung cấp");
          return;
        }
        default -> System.out.println("\nBạn nhập sai chức năng, vui lòng nhập lại \n");
      }
    } while (subCheck != "0");
  }

  public static void quanLyCauHinhUI() {
    String subCheck = "";
    do {
      System.out.println("Quản lý cấu hình:");
      System.out.println("1.Xem danh sách cấu hình");
      System.out.println("2.Thêm cấu hình");
      System.out.println("3.Sửa cấu hình");
      System.out.println("4.Xóa cấu hình");
      System.out.println("5.Tìm kiếm cấu hình");
      System.out.println("0.Thoát");
      System.out.println("Mời nhập: ");
      subCheck = input.nextLine();
      switch (subCheck) {
        case "1" -> QuanLyConfigurations.getInstance().Read();
        case "2" -> QuanLyConfigurations.getInstance().Create();
        case "3" -> QuanLyConfigurations.getInstance().Update();
        case "4" -> QuanLyConfigurations.getInstance().Delete();
        case "5" -> QuanLyConfigurations.getInstance().searchByCategory();
        case "0" -> {
          System.out.println("Thoát quản lý cấu hình sản phẩm");
          return;
        }
        default -> System.out.println("\nBạn nhập sai chức năng, vui lòng nhập lại \n");
      }
    } while (subCheck != "0");
  }

  public static void quanLyChiTietPhieuNhapHangUI() {
    String subCheck = "";
    do {
      System.out.println("Quản lý chi tiết phiếu nhập hàng:");
      System.out.println("1.Xem danh sách chi tiết phiếu nhập hàng");
      System.out.println("2.Thêm chi tiết phiếu nhập hàng");
      System.out.println("3.Sửa chi tiết phiếu nhập hàng");
      System.out.println("4.Xóa chi tiết phiếu nhập hàng");
      System.out.println("5.Tìm kiếm chi tiết phiếu nhập hàng");
      System.out.println("0.Thoát");
      System.out.println("Mời nhập: ");
      subCheck = input.nextLine();
      switch (subCheck) {
        case "1" -> QuanLyChiTietPhieuNhapHang.getInstance().Read();
        case "2" -> QuanLyChiTietPhieuNhapHang.getInstance().Create();
        case "3" -> QuanLyChiTietPhieuNhapHang.getInstance().Update();
        case "4" -> QuanLyChiTietPhieuNhapHang.getInstance().Delete();
        case "5" -> QuanLyChiTietPhieuNhapHang.getInstance().searchByCategory();
        case "0" -> {
          System.out.println("Thoát quản lý cấu hình sản phẩm");
          return;
        }
        default -> System.out.println("\nBạn nhập sai chức năng, vui lòng nhập lại \n");
      }
    } while (subCheck != "0");
  }

  public static void quanLyChiTietHoaDonUI() {
    String subCheck = "";
    do {
      System.out.println("Quản lý chi tiết hóa đơn:");
      System.out.println("1.Xem danh sách chi tiết hóa đơn");
      System.out.println("2.Thêm chi tiết hóa đơn");
      System.out.println("3.Sửa chi tiết hóa đơn");
      System.out.println("4.Xóa chi tiết hóa đơn");
      System.out.println("5.Tìm kiếm chi tiết hóa đơn");
      System.out.println("0.Thoát");
      System.out.println("Mời nhập: ");
      subCheck = input.nextLine();
      switch (subCheck) {
        case "1" -> QuanLyChiTietHoaDon.getInstance().Read();
        case "2" -> QuanLyChiTietHoaDon.getInstance().Create();
        case "3" -> QuanLyChiTietHoaDon.getInstance().Update();

        case "4" -> {
          input.nextLine();
          String HD;
          do {
            System.out.print("Nhập mã hoá đơn muốn xóa:(hd[0-n])--hd1->hd999 :");
            HD = input.nextLine();
            if (HD.isEmpty() || !HD.matches("^hd[0-9]+$") || HD.length() > 5) {
              System.out.println("Bạn nhập không đúng định dạng hãy nhập lại.");
            }
          } while (HD.isEmpty() || !HD.matches("^hd[0-9]+$") || HD.length() > 5);

          QuanLyChiTietHoaDon.getInstance().Delete(HD);

        }
        case "5" -> QuanLyChiTietHoaDon.getInstance().searchByCategory();
        case "0" -> {
          System.out.println("Thoát quản lý cấu hình sản phẩm");
          return;
        }
        default -> System.out.println("\nBạn nhập sai chức năng, vui lòng nhập lại \n");
      }
    } while (subCheck != "0");
  }

  private QuanLyKhoHangUI() {
    String check = "";
    do {
      method();
      System.out.print("Chọn chức năng: ");
      check = input.nextLine();
      switch (check) {
        case "1" -> {
          quanLySanPhamUI();
        }
        case "2" -> {
          quanLyNhapHangUI();
        }
        case "3" -> {
          quanLyCauHinhUI();
        }
        case "4" -> {
          quanLyNhaCungCapUI();
        }
        case "5" -> {
          quanLyChiTietPhieuNhapHangUI();
        }
        case "6" -> {
          quanLyChiTietHoaDonUI();
        }
        case "7" -> {
          ThongKeKinhDoanh.getInstance().searchByCategory();
        }
        case "0" -> {
          System.out.println("\nThoát chương trình quản lý khách hàng thành công");
          return;
        }
        default -> {
          System.out.println("\nNhập sai chức năng, vui lòng nhập lại!\n");
        }
      }
    } while (check != "0");
  }
}
