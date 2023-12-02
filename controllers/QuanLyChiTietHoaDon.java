package controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import models.ChiTietHoaDon;
import models.HoaDon;
import models.SanPham;

public class QuanLyChiTietHoaDon extends ChiTietHoaDon implements ControllerInterface {
  private static QuanLyChiTietHoaDon instance;
  private ChiTietHoaDon[] rd;
  private static Scanner input = new Scanner(System.in);

  public static QuanLyChiTietHoaDon getInstance() {
    if (instance == null) {
      instance = new QuanLyChiTietHoaDon();
    }
    return instance;
  }

  private QuanLyChiTietHoaDon() {
    super();
    getListReceiptDetail();
  }

  public ChiTietHoaDon[] getListReceiptDetail() {
    String[] result = new String[0];
    try {
      Stream.read("Database/ChiTietHoaDon.txt");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    rd = new ChiTietHoaDon[result.length];
    for (int i = 0; i < result.length; i++) {
      String[] row = result[i].split(";");
      rd[i] = new ChiTietHoaDon(row[0], row[1], Integer.parseInt(row[2]), Integer.parseInt(row[3]));
    }
    return rd;
  }

  @Override
  public void Read() {
    System.out.println("Danh sách chi tiết hóa đơn:");
    String header = String.format("| %-10s | %-13s | %-10s | %-10s | ", "ID Hóa Đơn", "ID Sản Phẩm", "Số lượng",
        "Giá");
    System.out.format("+------------+---------------+------------+------------+%n");
    System.out.println(header);
    System.out.format("+------------+---------------+------------+------------+%n");

    getListReceiptDetail();
    for (int i = 0; i < rd.length; i++) {
      String read = String.format("| %-10s | %-13s | %-10s | %-10s |", rd[i].getReceiptId(),
          rd[i].getProductId(), rd[i].getAmount(), rd[i].getPrice());
      System.out.println(read);
    }
    System.out.format("+------------+---------------+------------+------------+%n");
    waitConsole();
  }

  @Override
  public void Create() {
    ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon();
    HoaDon[] hoaDons = QuanLyHoaDon.getInstance().getListHoaDon();
    System.out.print("Nhập mã hóa đơn: ");
    chiTietHoaDon.setReceiptId(input.nextLine());

    boolean foundReceipt = false;
    for (HoaDon hoaDon : hoaDons) {
      if (hoaDon.getReceiptId().equals(chiTietHoaDon.getReceiptId())) {
        foundReceipt = true;
        break;
      }
    }
    if (foundReceipt == false) {
      System.out.println("Không tìm thấy hóa đơn.");
      return;
    }

    System.out.print("Nhập mã Sản Phẩm: ");
    chiTietHoaDon.setProductId(input.nextLine());

    System.out.print("Nhập mã Số lượng: ");
    chiTietHoaDon.setAmount(Integer.parseInt(input.next()));

    System.out.print("Nhập giá:");
    chiTietHoaDon.setPrice(Integer.parseInt(input.next()));

    try {
      String input = getReceiptId() + ";" + getProductId() + ";" + getAmount() + ";" + getPrice();
      Stream.addOneLine("Database/ChiTietHoaDon.txt", input);
      System.out.println("Nhập chi tiết hóa đơn thành công");
      waitConsole();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void Update() {
    System.out.print("Nhập ID hóa đơn cần chỉnh sửa: ");
    String ID_Receipt = input.nextLine();
    ChiTietHoaDon cthd = null;

    for (int i = 0; i < rd.length; i++) {
      if (ID_Receipt.equals(rd[i].getReceiptId())) {
        cthd = rd[i];
      }
    }

    if (cthd == null) {
      System.out.println("\t\t\t\t\t\t\t\t +-----MÃ HÓA ĐƠN KHÔNG TỒN TẠI-----+");
      return;
    }

    System.out.println("Nhập ID sản phẩm:");
    String ID_Product = input.nextLine();

    SanPham[] spList = QuanLySanPham.getInstance().getListSanPham();
    boolean foundProduct = false;
    do {
      for (SanPham sp : spList) {
        if (ID_Product.equals(sp.getProductId())) {
          foundProduct = true;
          break;
        }
      }
    } while (foundProduct == false);

    System.out.println("Thông tin hóa đơn cho sản phẩm có id " + cthd.getProductId() + " là:");
    String header = String.format("| %-10s | %-12s | %-10s | %-10s | ", "ID Hóa Đơn", "ID Sản Phẩm", "Số lượng",
        "Giá");
    System.out.format("+------------+--------------+------------+------------+%n");
    System.out.println(header);
    System.out.format("+------------+--------------+------------+------------+%n");
    String row = String.format("| %-10s | %-12s | %-10s | %-10s |", cthd.getReceiptId(), cthd.getProductId(),
        cthd.getAmount(), cthd.getPrice());
    System.out.println(row);
    System.out.format("+------------+--------------+------------+------------+%n");
    System.out.println("Enter để tiếp tục:");
    String[] data = new String[rd.length];
    input.nextLine();
    for (int i = 0; i < rd.length; i++) {
      if (ID_Receipt.equals(rd[i].getReceiptId()) && ID_Product.equals(rd[i].getProductId())) {
        System.out.println("Nhập lại thông tin chi tiết hóa đơn:");

        System.out.print("Nhập số lượng: ");
        setAmount(input.nextInt());

        System.out.print("Nhập giá: ");
        setPrice(input.nextInt());

        rd[i].setAmount(getAmount());
        rd[i].setPrice(getPrice());

      }
      data[i] = rd[i].getReceiptId() + ";" + rd[i].getProductId() + ";" + rd[i].getAmount() + ";"
          + rd[i].getPrice();

    }
    try {
      Stream.addAll("Database/ChiTietHoaDon.txt", data);
      System.out.println("Sửa thông tin chi tiết hoá đơn thành công");
      // waitConsole();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // TODO: Nếu xóa chi tiết hóa đơn -> Phải xóa hóa đơn, cần fix
  public void Delete(String id) {
    for (int i = 0; i < rd.length; i++) {
      if (id.equals(rd[i].getReceiptId())) {
        rd = deleteReceiptDetail(rd, i);
        i -= 1;
      }
    }

    String[] data = new String[rd.length];
    for (int i = 0; i < rd.length; i++) {
      data[i] = rd[i].getReceiptId() + ";" + rd[i].getProductId() + ";" + rd[i].getAmount() + ";"
          + rd[i].getPrice();
    }
    try {
      Stream.addAll("Database/ChiTietHoaDon.txt", data);
      System.out.println("Xóa chi tiết hoá đơn thành công");
      waitConsole();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public ChiTietHoaDon[] deleteReceiptDetail(ChiTietHoaDon[] rd, int index) {
    ChiTietHoaDon[] newRd = new ChiTietHoaDon[rd.length - 1];
    for (int i = 0, j = 0; i < rd.length; i++) {
      if (i != index) {
        newRd[j++] = rd[i];
      }
    }
    return newRd;
  }

  public ChiTietHoaDon[] addReceiptDetail(ChiTietHoaDon[] rd, ChiTietHoaDon receipt) {
    rd = Arrays.copyOf(rd, rd.length + 1);
    rd[rd.length - 1] = receipt;
    return rd;
  }

  //TODO: Fix tìm kiếm
  @Override
  public void searchByCategory() {
    ChiTietHoaDon[] result = new ChiTietHoaDon[0];
    System.out.println("Nhập mục lục cần tìm kiếm: ");
    System.out.println("1.Mã hóa đơn");
    System.out.println("2.Mã Sản Phẩm");
    System.out.println("Mời nhập:");
    int choose = input.nextInt();
    input.nextLine();
    switch (choose) {
      case 1 -> {
        System.out.print("Nhập ID hóa đơn: ");
        String id = input.nextLine();
        for (int i = 0; i < rd.length; i++) {
          if (id.equals(rd[i].getReceiptId())) {
            result = addReceiptDetail(result, rd[i]);
          }
        }
      }
      case 2 -> {
        input.nextLine();
        System.out.print("Nhập Mã Sản Phẩm: ");
        String id = input.nextLine();
        for (int i = 0; i < rd.length; i++) {
          if (id.equals(rd[i].getProductId())) {
            result = addReceiptDetail(result, rd[i]);
          }
        }
      }
    }

    System.out.println("Danh sách chi tiết hóa hàng tìm được:");
    String header = String.format("| %-10s | %-10s | %-10s | %-10s | ", "ID Hóa Đơn", "ID Sản Phẩm", "Số lượng",
        "Giá");
    System.out.format("+------------+-------------+------------+------------+%n");
    System.out.println(header);
    System.out.format("+------------+-------------+------------+------------+%n");
    for (ChiTietHoaDon receipt : result) {
      String read = String.format("| %-10s | %-11s | %-10s | %-10s |", receipt.getReceiptId(),
          receipt.getProductId(), receipt.getAmount(), receipt.getPrice());
      System.out.println(read);
    }
    System.out.format("+------------+-------------+------------+------------+%n");
    waitConsole();
  }

  public void waitConsole() {
    System.out.println("Ấn Enter để tiếp tục");
    input.nextLine();
  }

  @Override
  public void Delete() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'Delete'");
  }
}