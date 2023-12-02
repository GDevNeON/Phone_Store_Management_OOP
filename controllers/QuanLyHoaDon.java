package controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Scanner;

import models.HoaDon;
import models.KhachHang;
import models.NhanVien;

public class QuanLyHoaDon implements ControllerInterface {
  private static QuanLyHoaDon instance;
  private static Scanner input = new Scanner(System.in);
  private HoaDon[] re;
  private QuanLyChiTietHoaDon rdm = null;

  private QuanLyHoaDon() {
    getListHoaDon();
  }

  public static QuanLyHoaDon getInstance() {
    if (instance == null) {
      instance = new QuanLyHoaDon();
    }
    return instance;
  }

  public HoaDon[] getListHoaDon() {
    String[] result = new String[0];
    try {
      result = Stream.read("Database/HoaDon.txt");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    re = new HoaDon[result.length];
    for (int i = 0; i < result.length; i++) {
      String[] row = result[i].split(";");
      re[i] = new HoaDon(row[0], Integer.parseInt(row[1]), LocalDate.parse(row[2]), row[3], row[4], row[5]);
    }
    return re;
  }

  @Override
  public void Read() {
    rdm = QuanLyChiTietHoaDon.getInstance();
    System.out.println("Danh sách hóa đơn:");
    String header = String.format("| %-5s | %-15s | %-20s | %-25s | %-15s | %-20s |", "ID", "Giá", "Ngày mua",
        "Phương thức thanh toán", "Id khách hàng", "Nhân viên lập đơn");
    System.out.format(
        "+-------+-----------------+----------------------+---------------------------+-----------------+----------------------+%n");
    System.out.println(header);
    System.out.format(
        "+-------+-----------------+----------------------+---------------------------+-----------------+----------------------+%n");
    getListHoaDon();
    for (HoaDon HoaDon : re) {
      String read = String.format("| %-5s | %-15s | %-20s | %-25s | %-15s | %-20s |", HoaDon.getReceiptId(),
          HoaDon.getPrice(), HoaDon.getPurchaseDate(), HoaDon.getPurchaseMethod(), HoaDon.getCustomerId(),
          HoaDon.getEmployeeId());
      System.out.println(read);
    }
    System.out.format(
        "+-------+-----------------+----------------------+---------------------------+-----------------+----------------------+%n");

    // Xem chi tiết hóa đơn
    System.out.println("Xem thêm chi tiết hóa đơn ?");
    System.out.println("1.Có (Nhập số bất kì để thoát)");
    System.out.print("Mời nhập:");
    int choose = input.nextInt();
    if (choose == 1) {
      rdm.searchByCategory();
    }
    waitConsole();
  }

  @Override
  public void Create() {
    HoaDon hd = new HoaDon();
    System.out.print("Nhập mã hóa đơn: ");
    hd.setReceiptId(input.nextLine());

    int check = 0;
    for (HoaDon HoaDon : re) {
      if (hd.getReceiptId().equals(HoaDon.getReceiptId())) {
        check = 1;
        break;
      }
    }

    if (check == 1) {
      System.out.println("Mã hóa đơn bị trùng");
      return;
    }

    System.out.print("Nhập giá: ");
    hd.setPrice(input.nextInt());

    input.nextLine();
    KhachHang[] khList = QuanLyKhachHang.getInstance().getListCustomer();
    System.out.print("Nhập mã khách hàng: ");
    hd.setCustomerId(input.nextLine());

    boolean foundCustomer = false;
    for (KhachHang kh : khList) {
      if (kh.getCustomerId().equals(hd.getCustomerId())) {
        foundCustomer = true;
        break;
      }
    }

    // TODO: thêm khách hàng mới nếu mã khách hàng ko có
    if (foundCustomer == false) {
      System.out.println("Mã khách hàng không tìm thấy. Bạn có muốn thêm khách hàng mới không?");
      int choice = input.nextInt();
      if (choice == 1) {
        QuanLyKhachHang.getInstance().Create();
      } else {
        return;
      }
    }

    NhanVien[] nvList = QuanLyNhanVien.getInstance().getListEmployee();
    boolean foundEmployee = false;
    do {
      System.out.print("Nhập mã nhân viên: ");
      hd.setEmployeeId(input.nextLine());
      for (NhanVien nv : nvList) {
        if (nv.getWorkerId().equals(hd.getEmployeeId())) {
          foundEmployee = true;
          break;
        }
      }
    } while (foundEmployee == false);

    System.out.print("Nhập phương pháp mua hàng: ");
    hd.setPurchaseMethod(input.nextLine());

    LocalDate date = java.time.LocalDate.now();
    hd.setPurchaseDate(date);

    try {
      String inputString = hd.getReceiptId() + ";" + hd.getPrice() + ";" + hd.getPurchaseDate() + ";"
          + hd.getPurchaseMethod() + ";" + hd.getCustomerId() + ";" + hd.getEmployeeId();
      Stream.addOneLine("Database/HoaDon.txt", inputString);
      System.out.println("Nhập hóa đơn thành công");
    } catch (IOException e) {
      e.printStackTrace();
    }

    System.out.println();
    System.out.println("Thêm chi tiết hóa đơn cho hóa đơn này:");
    System.out.println("1.Có (Nhập số khác để thoát)");
    System.out.print("Mời nhập:");

    int choose = input.nextInt();
    if (choose == 1) {
      boolean check1 = true;
      do {
        rdm.Create();
        System.out.println("Thêm tiếp chi tiết hóa đơn:");
        System.out.println("1.Có (Nhập số khác để thoát)");
        System.out.print("Mời nhập:");
        int choose2 = input.nextInt();
        if (choose2 != 1)
          check1 = false;
      } while (check1);
    }
    waitConsole();
  }

  @Override
  public void Update() {
    System.out.print("Nhập ID hóa đơn cần chỉnh sửa: ");
    String ID_HoaDon = input.nextLine();
    HoaDon hdon = null;

    for (HoaDon HoaDon : re) {
      if (ID_HoaDon.equals(HoaDon.getReceiptId())) {
        hdon = HoaDon;
        break;
      }
    }

    if (hdon == null) {
      System.out.println("ID hóa đơn không tồn tại. Xin vui lòng nhập lại!");
      return;
    }

    System.out.println("Thông tin cũ của hóa đơn:");
    String header = String.format("| %-5s | %-15s | %-20s | %-25s | %-15s | %-20s |", "ID", "Giá", "Ngày mua",
        "Phương thức thanh toán", "Id khách hàng", "Mã nhân viên");
    System.out.format(
        "+-------+-----------------+----------------------+---------------------------+-----------------+----------------------+%n");
    System.out.println(header);
    System.out.format(
        "+-------+-----------------+----------------------+---------------------------+-----------------+----------------------+%n");
    String row = String.format("| %-5s | %-15s | %-20s | %-25s | %-15s | %-20s |", hdon.getReceiptId(),
        hdon.getPrice(), hdon.getPurchaseDate(), hdon.getPurchaseMethod(), hdon.getCustomerId(),
        hdon.getEmployeeId());
    System.out.println(row);
    System.out.format(
        "+-------+-----------------+----------------------+---------------------------+-----------------+----------------------+%n");

    String[] data = new String[re.length];

    for (int i = 0; i < re.length; i++) {
      if (ID_HoaDon.equals(re[i].getReceiptId())) {
        System.out.println("Sửa thông tin hóa đơn:");

        System.out.print("Nhập giá: ");
        hdon.setPrice(input.nextInt());
        re[i].setPrice(hdon.getPrice());

        input.nextLine();
        boolean foundCustomer = false;
        KhachHang[] khList = QuanLyKhachHang.getInstance().getListCustomer();
        do {
          System.out.print("Nhập mã khách hàng: ");
          hdon.setCustomerId(input.nextLine());
          re[i].setCustomerId(hdon.getCustomerId());
          for (KhachHang kh : khList) {
            if (kh.getCustomerId().equals(re[i].getCustomerId())) {
              foundCustomer = true;
              break;
            }
          }
        } while (foundCustomer == false);
        NhanVien[] nvList = QuanLyNhanVien.getInstance().getListEmployee();
        boolean foundEmployee = false;
        do {
          System.out.print("Nhập mã nhân viên: ");
          hdon.setEmployeeId(input.nextLine());
          re[i].setEmployeeId(hdon.getEmployeeId());
          for (NhanVien nv : nvList) {
            if (nv.getWorkerId().equals(re[i].getEmployeeId())) {
              foundEmployee = true;
              break;
            }
          }
        } while (foundEmployee == false);

        re[i].setPrice(re[i].getPrice());
        re[i].setPurchaseMethod(re[i].getPurchaseMethod());
      }
      data[i] = re[i].getReceiptId() + ";" + re[i].getPrice() + ";" + re[i].getPurchaseDate() + ";"
          + re[i].getPurchaseMethod() + ";" + re[i].getCustomerId() + ";" + re[i].getEmployeeId();
    }

    // Ghi file mảng đã cập nhật
    try {
      Stream.addAll("Database/HoaDon.txt", data);
      System.out.println("Sửa thông tin hoá đơn thành công");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void Delete() {
    System.out.print("Nhập ID hóa đơn cần xóa: ");
    String ID_HoaDon = input.nextLine();
    HoaDon hdon = null;

    for (HoaDon HoaDon : re) {
      if (ID_HoaDon.equals(HoaDon.getReceiptId())) {
        hdon = HoaDon;
        break;
      }
    }

    if (hdon == null) {
      System.out.println("ID hóa đơn không tồn tại. Xin vui lòng nhập lại!");
      return;
    }

    String[] data = new String[re.length];

    for (int i = 0; i < re.length; i++) {
      if (ID_HoaDon.equals(re[i].getReceiptId())) {
        hdon.setPrice(0);
        hdon.setCustomerId("null");
        hdon.setEmployeeId("null");
        hdon.setPurchaseMethod("null");
        re[i].setPrice(hdon.getPrice());
        re[i].setCustomerId(hdon.getCustomerId());
        re[i].setEmployeeId(hdon.getEmployeeId());
        re[i].setPurchaseMethod(hdon.getPurchaseMethod());
      }
      data[i] = re[i].getReceiptId() + ";" + re[i].getPrice() + ";" + re[i].getPurchaseDate() + ";"
          + re[i].getPurchaseMethod() + ";" + re[i].getCustomerId() + ";" + re[i].getEmployeeId();
    }

    // Ghi file mảng đã cập nhật
    try {
      Stream.addAll("Database/HoaDon.txt", data);
      System.out.println("Sửa thông tin hoá đơn thành công");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public HoaDon[] addHoaDon(HoaDon[] re, HoaDon HoaDon) {
    re = Arrays.copyOf(re, re.length + 1);
    re[re.length - 1] = HoaDon;
    return re;
  }

  @Override
  public void searchByCategory() {
    HoaDon[] result = new HoaDon[0];
    System.out.println("Nhập mục lục cần tìm kiếm: ");
    System.out.println("1.Mã hóa đơn");
    System.out.println("2.Mã nhân viên lập hóa đơn");
    System.out.println("3.Mã khách hàng");
    System.out.println("4.Khoảng ngày mua hàng");
    System.out.println("5.Tổng tiền của 1 hóa đơn");
    int choose = input.nextInt();
    switch (choose) {
      case 1 -> {
        input.nextLine();
        System.out.print("Nhập ID hóa đơn: ");
        String id = input.nextLine();
        for (HoaDon HoaDon : re) {
          if (id.equals(HoaDon.getReceiptId())) {
            result = addHoaDon(result, HoaDon);
          }
        }
      }
      case 2 -> {
        input.nextLine();
        System.out.print("Nhập mã nhân viên lập hóa đơn: ");
        String id = input.nextLine();
        for (HoaDon HoaDon : re) {
          if (id.equals(HoaDon.getEmployeeId())) {
            result = addHoaDon(result, HoaDon);
          }
        }
      }
      case 3 -> {
        input.nextLine();
        System.out.print("Nhập mã khách hàng của hóa đơn: ");
        String id = input.nextLine();
        for (HoaDon HoaDon : re) {
          if (id.equals(HoaDon.getCustomerId())) {
            result = addHoaDon(result, HoaDon);
          }
        }
      }
      case 4 -> {
        input.nextLine();
        System.out.print("Nhập ngày bắt đầu: ");
        String startDateStr = input.nextLine();
        System.out.print("Nhập ngày kết thúc: ");
        String endDateStr = input.nextLine();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
          LocalDate startDate = LocalDate.parse(startDateStr, formatter);
          LocalDate endDate = LocalDate.parse(endDateStr, formatter);

          for (HoaDon hoaDon : re) {
            LocalDate purchaseDate = hoaDon.getPurchaseDate();
            if ((purchaseDate.isEqual(startDate) || purchaseDate.isAfter(startDate))
                && purchaseDate.isBefore(endDate)) {
              result = addHoaDon(result, hoaDon);
            }
          }
        } catch (DateTimeParseException e) {
          e.printStackTrace();
        }

      }
      case 5 -> {
        input.nextLine();
        System.out.print("Từ giá: ");
        int begin = input.nextInt();
        System.out.print("Đến giá:");
        int end = input.nextInt();
        for (HoaDon HoaDon : re) {
          if (HoaDon.getPrice() <= end && HoaDon.getPrice() >= begin) {
            result = addHoaDon(result, HoaDon);
          }
        }
      }
    }

    if (result.length == 0) {
      System.out.println("Không có hóa đơn cần tìm");
    } else {
      System.out.println("Danh sách hóa đơn tìm được:");
      String header = String.format("| %-5s | %-15s | %-20s | %-25s | %-15s | %-20s |", "ID", "Giá", "Ngày mua",
          "Phương thức thanh toán", "Id khách hàng", "Nhân viên lập đơn");
      System.out.format(
          "+-------+-----------------+----------------------+---------------------------+-----------------+----------------------+%n");
      System.out.println(header);
      System.out.format(
          "+-------+-----------------+----------------------+---------------------------+-----------------+----------------------+%n");
      for (HoaDon recepit : result) {
        String read = String.format("| %-5s | %-15s | %-20s | %-25s | %-15s | %-20s |", recepit.getReceiptId(),
            recepit.getPrice(), recepit.getPurchaseDate(), recepit.getPurchaseMethod(),
            recepit.getCustomerId(), recepit.getEmployeeId());
        System.out.println(read);
      }
      System.out.format(
          "+-------+-----------------+----------------------+---------------------------+-----------------+----------------------+%n");

      waitConsole();
    }
  }

  public void waitConsole() {
    System.out.println("Ấn Enter để tiếp tục");
    input.nextLine();
  }
}
