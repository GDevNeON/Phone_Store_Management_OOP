package controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

import models.ChiTietPhieuNhapHang;
import models.PhieuNhapHang;
import models.SanPham;

public class QuanLyChiTietPhieuNhapHang  implements ControllerInterface {
  private static QuanLyChiTietPhieuNhapHang instance;
  private ChiTietPhieuNhapHang[] dsct;
  private static Scanner sc = new Scanner(System.in);
  private Validation kiemTra = new Validation();

  private QuanLyChiTietPhieuNhapHang() {
    super();
    getListChiTietPhieuNhapHang();
  }

  public static QuanLyChiTietPhieuNhapHang getInstance() {
    if (instance == null) {
      instance = new QuanLyChiTietPhieuNhapHang();
    }
    return instance;
  }

  public ChiTietPhieuNhapHang[] getListChiTietPhieuNhapHang() {
    String[] result = new String[0];
    try {
      result = Stream.read("Database/ChiTietPhieuNhap.txt");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    dsct = new ChiTietPhieuNhapHang[result.length];
    for (int i = 0; i < result.length; i++) {
      String[] row = result[i].split(";");
      dsct[i] = new ChiTietPhieuNhapHang(row[0], row[1], Integer.parseInt(row[2]),row[3]);
    }
    return dsct;
  }

  public void waitConsole() {
    System.out.println("\t\t\t\t\t\t\t\t - Ấn Enter để tiếp tục");
    sc.nextLine();
  }

  public void Read() {
    System.out.println("\t\t\t\t\t\t\t\t +-----DANH SÁCH CHI TIẾT PHIẾU NHẬP HÀNG-----+");
    String header = String.format("| %-15s | %-15s | %-10s | %-25s |", "ID Phiếu nhập", "ID Sản phẩm", "Số lượng",
        "Giá");
    System.out.format("+-----------------+-----------------+------------+---------------------------+%n");
    System.out.println(header);
    System.out.format("+-----------------+-----------------+------------+---------------------------+%n");

    getListChiTietPhieuNhapHang();

    for (ChiTietPhieuNhapHang chiTietPhieu : dsct) {
      String read = String.format("| %-15s | %-15s | %-10s | %-25s |", chiTietPhieu.getPhieuNhapId(),
          chiTietPhieu.getProductId(), chiTietPhieu.getAmount(), chiTietPhieu.getPrice());
      System.out.println(read);
    }
    System.out.format("+-----------------+-----------------+------------+---------------------------+%n");
    waitConsole();
  }

  public void Create() {
    System.out.println("\t\t\t\t\t\t\t\t +-----NHẬP THÔNG TIN CHI TIẾT PHIẾU NHẬP-----+");
    ChiTietPhieuNhapHang chiTietPhieuNhapHang = new ChiTietPhieuNhapHang();
    System.out.println("Nhập mã chi tiết phiếu nhập: ");
    String ID_chiTiet = sc.nextLine();
    while (ID_chiTiet.isEmpty() || !kiemTra.isValidIDCTP(ID_chiTiet)){
        System.out.print("Không đúng định dạng! Vui lòng nhập lại ID chi tiết phiếu: ");
        ID_chiTiet = sc.nextLine();
    }
    chiTietPhieuNhapHang.setPhieuNhapId(ID_chiTiet);
    int check = 0;
    for (ChiTietPhieuNhapHang chiTietPhieu : dsct) {
      if (chiTietPhieuNhapHang.getPhieuNhapId().equals(chiTietPhieu.getPhieuNhapId())) {
        check = 1;
        break;
      }
    }

    if (check == 1) {
      System.out.println("\t\t\t\t\t\t\t\t +-----MÃ PHIẾU NHẬP BỊ TRÙNG-----+");
      return;
    }

    boolean foundPhieuNhap = false;
    PhieuNhapHang[] phieuNhapHangList = QuanLyPhieuNhapHang.getInstance().getListPhieuNhapHang();
    for (PhieuNhapHang phieuNhapHang : phieuNhapHangList) {
      if (chiTietPhieuNhapHang.getPhieuNhapId().equals(phieuNhapHang.getReceiptId())) {
        foundPhieuNhap = true;
        break;
      }
    }

    if (foundPhieuNhap == false) {
      System.out.println("\t\t\t\t\t\t\t\t +-----KHÔNG TÌM THẤY MÃ PHIẾU NHẬP TRONG DANH SÁCH PHIẾU NHẬP-----+");
      return;
    }

    System.out.println("Nhập số lượng: ");
    while (!sc.hasNextInt()) {
      System.out.println("Vui lòng nhập một số nguyên.");
      sc.next();
    }
    chiTietPhieuNhapHang.setAmount(sc.nextInt());

    System.out.println("Nhập giá: ");
    String price = sc.nextLine();
    while (price.isEmpty() || kiemTra.isInteger(price)){
      System.out.print("Không đúng định dạng! Vui lòng nhập lại: ");
      price = sc.nextLine();
    }
    chiTietPhieuNhapHang.setPrice(price);

    System.out.println("Nhập mã sản phẩm: ");
    String ID_sanPham = sc.nextLine();
    while (ID_sanPham.isEmpty() || !kiemTra.isValidIDproduct(ID_sanPham)) {
        System.out.print("Không đúng định dạng! Vui lòng nhập lại ID sản phẩm: ");
        ID_sanPham = sc.nextLine();
    }
    chiTietPhieuNhapHang.setProductId(ID_sanPham);
    boolean foundProduct = false;
    SanPham[] spList = QuanLySanPham.getInstance().getListSanPham();
    for (SanPham sp : spList) {
      if (chiTietPhieuNhapHang.getProductId().equals(sp.getProductId())) {
        sp.setAmount(chiTietPhieuNhapHang.getAmount() + sp.getAmount());
        foundProduct = true;
        break;
      }
    }

    if (foundProduct == false) {
      System.out.println(
          "\t\t\t\t\t\t\t\t +-----KHÔNG TÌM THẤY MÃ SẢN PHẨM TRONG DANH SÁCH SẢN PHẨM. BẠN CÓ MUỐN THÊM MỚI KHÔNG?-----+");
      System.out.println("1. Có");
      System.out.println("2. Không");
      String choose = sc.nextLine();
      if (choose == "1") {
        QuanLySanPham.getInstance().Create();
      } else if (choose == "2") {
        return;
      }
    }

    try {
      String sc = chiTietPhieuNhapHang.getPhieuNhapId() + ";" + chiTietPhieuNhapHang.getProductId() + ";"
          + chiTietPhieuNhapHang.getAmount()
          + ";" + chiTietPhieuNhapHang.getPrice();
      Stream.addOneLine("Database/ChiTietPhieuNhap.txt", sc);
      System.out.println("\t\t\t\t\t\t\t\t +-----NHẬP CHI TIẾT PHIẾU NHẬP THÀNH CÔNG-----+");
      waitConsole();
    } catch (IOException e) {
      e.printStackTrace();
    }
    getListChiTietPhieuNhapHang();
  }

  public void Update() {
    try {
      System.out.println("\t\t\t\t\t\t\t\t +-----CHỈNH SỬA CHI TIẾT PHIẾU NHẬP-----+");
      System.out.print("Nhập mã phiếu nhập cần chỉnh sửa: ");
      String ID_PhieuNhap = sc.nextLine();
      while (ID_PhieuNhap.isEmpty() || !kiemTra.isValidIDCTP(ID_PhieuNhap)){
          System.out.print("Không đúng định dạng! Vui lòng nhập lại ID chi tiết phiếu: ");
          ID_PhieuNhap = sc.nextLine();
      }
      ChiTietPhieuNhapHang ctpnh = null;

      for (ChiTietPhieuNhapHang chiTietPhieu : dsct) {
        if (chiTietPhieu.getPhieuNhapId().equals(ID_PhieuNhap)) {
          ctpnh = chiTietPhieu;
          break;
        }
      }

      if (ctpnh == null) {
        System.out.println("\t\t\t\t\t\t\t\t +-----MÃ PHIẾU NHẬP KHÔNG TỒN TẠI-----+");
        return;
      }

      System.out.println("\t\t\t\t\t\t\t\t +-----THÔNG TIN CHI TIẾT PHIẾU NHẬP TRƯỚC KHI CHỈNH SỬA-----+");
      String header = String.format("| %-15s | %-15s | %-10s | %-25s |", "ID Phiếu nhập", "ID Sản phẩm", "Số lượng",
          "Giá");
      System.out.format("+-----------------+-----------------+------------+---------------------------+%n");
      System.out.println(header);
      System.out.format("+-----------------+-----------------+------------+---------------------------+%n");
      String row = String.format("| %-15s | %-15s | %-10s | %-25s |", ctpnh.getPhieuNhapId(), ctpnh.getProductId(),
          ctpnh.getAmount(), ctpnh.getPrice());
      System.out.println(row);
      System.out.format("+-----------------+-----------------+------------+---------------------------+%n");

      String[] data = new String[dsct.length];

      System.out.println("\t\t\t\t\t\t\t\t +-----SỬA THÔNG TIN CHI TIẾT PHIẾU-----+");
      System.out.println("\t\t\t\t\t\t\t\t |0.Thoát                               |");
      System.out.println("\t\t\t\t\t\t\t\t +--------------------------------------+");
      System.out.println("\t\t\t\t\t\t\t\t |1.Sửa ID chi tiết phiếu               |");
      System.out.println("\t\t\t\t\t\t\t\t |2.Sửa ID sản phẩm                     |");
      System.out.println("\t\t\t\t\t\t\t\t |3.Sửa số lượng sản phẩm               |");
      System.out.println("\t\t\t\t\t\t\t\t |4.Sửa giá tiền                        |");
      System.out.println("\t\t\t\t\t\t\t\t |5.Sửa tất cả thông tin                |");
      System.out.println("\t\t\t\t\t\t\t\t +--------------------------------------+");
      System.out.print("\t\t\t\t\t\t\t\t Mời bạn nhập lựa chọn: ");
      String choose = sc.nextLine();
        switch (choose) {
          case "0" ->{
            System.out.println("\t\t\t\t\t\t\t\t THOÁT CHƯƠNG TRÌNH THÀNH CÔNG!");
          }
          case "1" -> {
            for (int i = 0; i < dsct.length; i++) {
              if (dsct[i].getPhieuNhapId().equals(ID_PhieuNhap)) {
                System.out.print("Nhập ID chi tiết phiếu nhập: ");
                String ID_chiTiet = sc.nextLine();
                while (ID_chiTiet.isEmpty() || !kiemTra.isValidIDCTP(ID_chiTiet)){
                    System.out.print("Không đúng định dạng! Vui lòng nhập lại ID chi tiết phiếu: ");
                    ID_chiTiet = sc.nextLine();
                }
                ctpnh.setPhieuNhapId(ID_chiTiet);
                int check = 0;
                for (ChiTietPhieuNhapHang chiTietPhieu : dsct) {
                  if (ctpnh.getPhieuNhapId().equals(chiTietPhieu.getPhieuNhapId())) {
                    check = 1;
                    break;
                  }
                }

                if (check == 1) {
                  System.out.println("\t\t\t\t\t\t\t\t MÃ TÀI KHOẢN BỊ TRÙNG!");
                  return;
                }
                dsct[i].setPhieuNhapId(ctpnh.getPhieuNhapId());
              }
              data[i] = dsct[i].getPhieuNhapId() + ";" + dsct[i].getProductId() + ";" + dsct[i].getAmount() + ";"
                  + dsct[i].getPrice();
            }
            try {
              Stream.addAll("Database/ChiTietPhieuNhap.txt", data);
              System.out.println("\t\t\t\t\t\t\t\t +-----CHỈNH SỬA CHI TIẾT PHIẾU NHẬP THÀNH CÔNG-----+");
              waitConsole();
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
          case "2" -> {
            for (int i = 0; i < dsct.length; i++) {
              if (dsct[i].getPhieuNhapId().equals(ID_PhieuNhap)) {
                System.out.print("Nhập ID sản phẩm: ");
                String ID_product = sc.nextLine();
                while (ID_product.isEmpty() || !kiemTra.isValidIDproduct(ID_product)){
                    System.out.print("Không đúng định đạng! Vui lòng nhập lại ID sản phẩm: ");
                    ID_product = sc.nextLine();
                }
                ctpnh.setProductId(ID_product);
                SanPham[] spList = QuanLySanPham.getInstance().getListSanPham();
                boolean foundProduct = false;
                for (SanPham sp : spList) {
                  if (ctpnh.getProductId().equals(sp.getProductId())) {
                    foundProduct = true;
                    dsct[i].setProductId(ctpnh.getProductId());
                    break;
                  }

                  if (foundProduct == false) {
                    System.out.println(
                        "\t\t\t\t\t\t\t\t +-----KHÔNG TÌM THẤY MÃ SẢN PHẨM TRONG DANH SÁCH SẢN PHẨM. BẠN CÓ MUỐN THÊM MỚI KHÔNG?-----+");
                    System.out.println("1. Có");
                    System.out.println("2. Không");
                    int choose1 = sc.nextInt();
                    if (choose1 == 1) {
                      QuanLySanPham.getInstance().Create();
                      dsct[i].setProductId(spList[spList.length - 1].getProductId());
                    } else if (choose1 == 2) {
                      System.out.println(
                          "\t\t\t\t\t\t\t\t +-----CẬP NHẬT THẤT BẠI-----+");
                      return;
                    }
                  }
                  data[i] = dsct[i].getPhieuNhapId() + ";" + dsct[i].getProductId() + ";" + dsct[i].getAmount() + ";"
                      + dsct[i].getPrice();
                }
                try {
                  Stream.addAll("Database/ChiTietPhieuNhap.txt", data);
                  System.out.println("\t\t\t\t\t\t\t\t +-----CHỈNH SỬA CHI TIẾT PHIẾU NHẬP THÀNH CÔNG-----+");
                  waitConsole();
                } catch (IOException e) {
                  e.printStackTrace();
                }
              }
            }
          }
          case "3" -> {
            for (int i = 0; i < dsct.length; i++) {
              if (dsct[i].getPhieuNhapId().equals(ID_PhieuNhap)) {
                System.out.print("Nhập số lượng sản phẩm: ");
                while (!sc.hasNextInt()) {
                  System.out.println("Vui lòng nhập một số nguyên.");
                  sc.next();
                }
               
                ctpnh.setAmount(sc.nextInt());
                dsct[i].setAmount(ctpnh.getAmount());
              }
              data[i] = dsct[i].getPhieuNhapId() + ";" + dsct[i].getProductId() + ";" + dsct[i].getAmount() + ";"
                  + dsct[i].getPrice();
            }
            try {
              Stream.addAll("Database/ChiTietPhieuNhap.txt", data);
              System.out.println("\t\t\t\t\t\t\t\t +-----CHỈNH SỬA CHI TIẾT PHIẾU NHẬP THÀNH CÔNG-----+");
              waitConsole();
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
          case "4" -> {
            for (int i = 0; i < dsct.length; i++) {
              if (dsct[i].getPhieuNhapId().equals(ID_PhieuNhap)) {
                System.out.print("Nhập giá tiền: ");
                String price = sc.nextLine();
                while (price.isEmpty() || kiemTra.isInteger(price) == false) {
                  System.out.print("Không đúng định dạng! Vui lòng nhập lại: ");
                  price = sc.nextLine();
                }
                ctpnh.setPrice(price);
                dsct[i].setPrice(ctpnh.getPrice());
              }
              data[i] = dsct[i].getPhieuNhapId() + ";" + dsct[i].getProductId() + ";" + dsct[i].getAmount() + ";"
                  + dsct[i].getPrice();
            }
            try {
              Stream.addAll("Database/ChiTietPhieuNhap.txt", data);
              System.out.println("\t\t\t\t\t\t\t\t +-----CHỈNH SỬA CHI TIẾT PHIẾU NHẬP THÀNH CÔNG-----+");
              waitConsole();
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
          case "5" -> {
            for (int i = 0; i < dsct.length; i++) {
              if (dsct[i].getPhieuNhapId().equals(ID_PhieuNhap)) {
                System.out.print("Nhập ID chi tiết phiếu nhập: ");
                String ID_chiTiet = sc.nextLine();
                while (ID_chiTiet.isEmpty() || !kiemTra.isValidIDCTP(ID_chiTiet)){
                    System.out.print("Không đúng định dạng! Vui lòng nhập lại ID chi tiết phiếu: ");
                    ID_chiTiet = sc.nextLine();
                }
                ctpnh.setPhieuNhapId(ID_chiTiet);
                int check = 0;
                for (ChiTietPhieuNhapHang chiTietPhieu : dsct) {
                  if (ctpnh.getPhieuNhapId().equals(chiTietPhieu.getPhieuNhapId())) {
                    check = 1;
                    break;
                  }
                }

                if (check == 1) {
                  System.out.println("\t\t\t\t\t\t\t\t MÃ TÀI KHOẢN BỊ TRÙNG!");
                  return;
                }

                boolean foundPhieuNhap = false;
                PhieuNhapHang[] phieuNhapHangList = QuanLyPhieuNhapHang.getInstance().getListPhieuNhapHang();
                for (PhieuNhapHang phieuNhapHang : phieuNhapHangList) {
                  if (ctpnh.getPhieuNhapId().equals(phieuNhapHang.getReceiptId())) {
                    foundPhieuNhap = true;
                    break;
                  }
                }

                if (foundPhieuNhap == false) {
                  System.out
                      .println("\t\t\t\t\t\t\t\t +-----KHÔNG TÌM THẤY MÃ PHIẾU NHẬP TRONG DANH SÁCH PHIẾU NHẬP-----+");
                  return;
                }

                System.out.print("Nhập ID sản phẩm: ");
                String ID_product = sc.nextLine();
                while (ID_product.isEmpty() || !kiemTra.isValidIDproduct(ID_product)){
                    System.out.print("Không đúng định đạng! Vui lòng nhập lại ID sản phẩm: ");
                    ID_product = sc.nextLine();
                }
                ctpnh.setProductId(ID_product);
                SanPham[] spList = QuanLySanPham.getInstance().getListSanPham();
                boolean foundProduct = false;
                for (SanPham sp : spList) {
                  if (ctpnh.getProductId().equals(sp.getProductId())) {
                    foundProduct = true;
                    dsct[i].setProductId(ctpnh.getProductId());
                    break;
                  }

                  if (foundProduct == false) {
                    System.out.println(
                        "\t\t\t\t\t\t\t\t +-----KHÔNG TÌM THẤY MÃ SẢN PHẨM TRONG DANH SÁCH SẢN PHẨM. BẠN CÓ MUỐN THÊM MỚI KHÔNG?-----+");
                    System.out.println("1. Có");
                    System.out.println("2. Không");
                    String choose1 = sc.nextLine();
                    if (choose1 == "1") {
                      QuanLySanPham.getInstance().Create();
                      dsct[i].setProductId(spList[spList.length - 1].getProductId());
                    } else if (choose1 == "2") {
                      System.out.println(
                          "\t\t\t\t\t\t\t\t +-----CẬP NHẬT THẤT BẠI-----+");
                      return;
                    }
                  }
                  System.out.print("Nhập số lượng sản phẩm: ");
                  while (!sc.hasNextInt()) {
                    System.out.println("Vui lòng nhập một số nguyên.");
                    sc.next();
                  }
                  ctpnh.setAmount(sc.nextInt());

                  System.out.print("Nhập giá: ");
                  String price = sc.nextLine();
                  while (price.isEmpty() || kiemTra.isInteger(price)) {
                    System.out.print("Không đúng định dạng! Vui lòng nhập lại: ");
                    price = sc.nextLine();
                  }
                  ctpnh.setPrice(price);

                  dsct[i].setPhieuNhapId(ctpnh.getPhieuNhapId());
                  dsct[i].setProductId(ctpnh.getProductId());
                  dsct[i].setAmount(ctpnh.getAmount());
                  dsct[i].setPrice(ctpnh.getPrice());
                }
                data[i] = dsct[i].getPhieuNhapId() + ";" + dsct[i].getProductId() + ";" + dsct[i].getAmount() + ";"
                    + dsct[i].getPrice();
              }
              try {
                Stream.addAll("Database/ChiTietPhieuNhap.txt", data);
                System.out.println("\t\t\t\t\t\t\t\t +-----CHỈNH SỬA CHI TIẾT PHIẾU NHẬP THÀNH CÔNG-----+");
                waitConsole();
              } catch (IOException e) {
                e.printStackTrace();
              }
            }
          }
          default -> System.out.println("\t\t\t\t\t\t\t\t +-----LỰA CHỌN KHÔNG HỢP LỆ-----+");
        }
      
    } catch (InputMismatchException ei) {
      System.out.println("\t\t\t\t\t\t\t\t +-----GIÁ TRỊ KHÔNG HỢP LỆ. VUI LÒNG NHẬP LẠI-----+");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public void Delete() {
    try {
      System.out.println("\t\t\t\t\t\t\t\t +-----XÓA CHI TIẾT PHIẾU NHẬP HÀNG-----+");
      System.out.print("Nhập mã chi tiết phiếu nhập cần xóa: ");
      String ID_PhieuNhap = sc.nextLine();
      while (ID_PhieuNhap.isEmpty() || !kiemTra.isValidIDCTP(ID_PhieuNhap)){
          System.out.print("Không đúng định dạng! Vui lòng nhập lại ID chi tiết phiếu: ");
          ID_PhieuNhap = sc.nextLine();
      }

      ChiTietPhieuNhapHang xoachiTiet = null;
      for (ChiTietPhieuNhapHang chiTietPhieu : dsct) {
        if (chiTietPhieu.getPhieuNhapId().equals(ID_PhieuNhap)) {
          xoachiTiet = chiTietPhieu;
          break;
        }
      }

      if (xoachiTiet == null) {
        System.out.println("\t\t\t\t\t\t\t\t +-----MÃ PHIẾU NHẬP KHÔNG TỒN TẠI-----+");
        return;
      }

      for (int i = 0; i < dsct.length; i++) {
        if (ID_PhieuNhap.equals(dsct[i].getPhieuNhapId())) {
          dsct = deleteChiTietPhieuNhapHang(dsct, i);
          break;
        }
      }
      String[] data = new String[dsct.length];
      for (int i = 0; i < dsct.length; i++) {
        data[i] = dsct[i].getPhieuNhapId() + ";" + dsct[i].getProductId() + ";" + dsct[i].getAmount() + ";"
            + dsct[i].getPrice();
      }

      try {
        Stream.addAll("Database/ChiTietPhieuNhap.txt", data);
        System.out.println("\t\t\t\t\t\t\t\t +-----XÓA CHI TIẾT HÓA ĐƠN THÀNH CÔNG-----+");
        waitConsole();
      } catch (IOException e) {
        e.printStackTrace();
      }
    } catch (InputMismatchException ei) {
      System.out.println("\t\t\t\t\t\t\t\t +-----GIÁ TRỊ KHÔNG HỢP LỆ. VUI LÒNG NHẬP LẠI-----+");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public ChiTietPhieuNhapHang[] deleteChiTietPhieuNhapHang(ChiTietPhieuNhapHang[] dsct, int index) {
    ChiTietPhieuNhapHang[] newCT = new ChiTietPhieuNhapHang[dsct.length - 1];
    for (int i = 0, j = 0; i < dsct.length; i++) {
      if (i != index) {
        newCT[j++] = dsct[i];
      }
    }
    return newCT;
  }

  public ChiTietPhieuNhapHang[] addChiTietPhieuNhapHang(ChiTietPhieuNhapHang[] dsct, ChiTietPhieuNhapHang pnh) {
    dsct = Arrays.copyOf(dsct, dsct.length + 1);
    dsct[dsct.length - 1] = pnh;
    return dsct;
  }

  public void searchByCategory() {
    ChiTietPhieuNhapHang[] result = new ChiTietPhieuNhapHang[0];
    System.out.println("\t\t\t\t\t\t\t\t +--------NHẬP MỤC LỤC CẨN TÌM KIẾM--------+");
    System.out.println("\t\t\t\t\t\t\t\t |0.Thoát                                  |");
    System.out.println("\t\t\t\t\t\t\t\t +-----------------------------------------+");
    System.out.println("\t\t\t\t\t\t\t\t |1.ID Phiếu nhập hàng                     |");
    System.out.println("\t\t\t\t\t\t\t\t |2.ID Sản phẩm                            |");
    System.out.println("\t\t\t\t\t\t\t\t |3.Số lượng                               |");
    System.out.println("\t\t\t\t\t\t\t\t |4.Giá tiền                               |");
    System.out.println("\t\t\t\t\t\t\t\t +-----------------------------------------+");
    System.out.print("\t\t\t\t\t\t\t - Mời Bạn Nhập Lựa Chọn: ");
    String choose = sc.nextLine();
      switch (choose) {
        case "0" -> {
          System.out.println("\t\t\t\t\t\t\t\t THOÁT CHƯƠNG TRÌNH THÀNH CÔNG!");
        }
        case "1" -> {
          System.out.print("Nhập mã phiếu nhập hàng: ");
          String id = sc.nextLine();
          for (ChiTietPhieuNhapHang chiTietPhieu : dsct) {
            if (chiTietPhieu.getPhieuNhapId().toLowerCase().contains(id.toLowerCase())) {
              result = addChiTietPhieuNhapHang(result, chiTietPhieu);
            }
          }
        }
        case "2" -> {
          System.out.print("Nhập mã sản phẩm: ");
          String id = sc.nextLine();
          for (ChiTietPhieuNhapHang chiTietPhieu : dsct) {
            if (chiTietPhieu.getProductId().toLowerCase().contains(id.toLowerCase())) {
              result = addChiTietPhieuNhapHang(result, chiTietPhieu);
            }
          }
        }
        case "3" -> {
          System.out.println("Nhập số lượng từ: ");
          int begin = sc.nextInt();
          System.out.println("Đến: ");
          int end = sc.nextInt();
          for (ChiTietPhieuNhapHang ctp : dsct) {
            if (ctp.getAmount() <= end && ctp.getAmount() >= begin) {
              result = addChiTietPhieuNhapHang  (result, ctp);
            }
          }
        }
        case "4" -> {
          System.out.print("Nhập giá tiền: ");
          String price = sc.nextLine();
          for (ChiTietPhieuNhapHang chiTietPhieu : dsct) {
            if (price.equals(chiTietPhieu.getPrice())) {
              result = addChiTietPhieuNhapHang(result, chiTietPhieu);
            }
          }
        }
        default -> {
          System.out.println("Lựa chọn không hợp lệ!");
        }
    }
    String header = String.format("| %-15s | %-15s | %-10s | %-25s |", "ID Phiếu nhập", "ID Sản phẩm", "Số lượng",
        "Giá");
    System.out.format("+-----------------+-----------------+------------+---------------------------+%n");
    System.out.println(header);
    System.out.format("+-----------------+-----------------+------------+---------------------------+%n");

    for (ChiTietPhieuNhapHang chiTietPhieu : result) {
      String row = String.format("| %-15s | %-15s | %-10s | %-25s |", chiTietPhieu.getPhieuNhapId(),
          chiTietPhieu.getProductId(), chiTietPhieu.getAmount(), chiTietPhieu.getPrice());
      System.out.println(row);
    }
    System.out.format("+-----------------+-----------------+------------+---------------------------+%n");
    waitConsole();
  }

}