package controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

import models.ChiTietPhieuNhapHang;
import models.PhieuNhapHang;
import models.SanPham;

public class QuanLyChiTietPhieuNhapHang extends QuanLyPhieuNhapHang implements ControllerInterface {
  private static QuanLyChiTietPhieuNhapHang instance;
  private ChiTietPhieuNhapHang[] dsct;
  private static Scanner sc = new Scanner(System.in);

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
      dsct[i] = new ChiTietPhieuNhapHang(row[0], row[1], Integer.parseInt(row[2]), Integer.parseInt(row[3]));
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
    System.out.println("Nhập mã phiếu nhập: ");
    chiTietPhieuNhapHang.setPhieuNhapId(sc.nextLine());
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
    chiTietPhieuNhapHang.setAmount(sc.nextInt());

    System.out.println("Nhập giá: ");
    setPrice(sc.nextInt());

    System.out.println("Nhập mã sản phẩm: ");
    chiTietPhieuNhapHang.setProductId(sc.nextLine());
    boolean foundProduct = false;
    SanPham[] spList = QuanLySanPham.getInstance().getListSanPham();
    for (SanPham sp : spList) {
      if (chiTietPhieuNhapHang.getProductId().equals(sp.getProductId())) {
        sp.setAmountRemaining(chiTietPhieuNhapHang.getAmount() + sp.getAmountRemaining());
        foundProduct = true;
        break;
      }
    }

    if (foundProduct == false) {
      System.out.println(
          "\t\t\t\t\t\t\t\t +-----KHÔNG TÌM THẤY MÃ SẢN PHẨM TRONG DANH SÁCH SẢN PHẨM. BẠN CÓ MUỐN THÊM MỚI KHÔNG?-----+");
      System.out.println("1. Có");
      System.out.println("2. Không");
      int choose = sc.nextInt();
      if (choose == 1) {
        QuanLySanPham.getInstance().Create();
      } else if (choose == 2) {
        return;
      }
    }

    try {
      String sc = chiTietPhieuNhapHang.getPhieuNhapId() + ";" + chiTietPhieuNhapHang.getProductId() + ";"
          + chiTietPhieuNhapHang.getAmount()
          + ";" + getPrice();
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
      int choose = sc.nextInt();
      sc.nextLine();
      if (choose == 0) {
      } else {
        switch (choose) {
          case 1 -> {
            for (int i = 0; i < dsct.length; i++) {
              if (dsct[i].getPhieuNhapId().equals(ID_PhieuNhap)) {
                System.out.print("Nhập ID chi tiết phiếu nhập: ");
                ctpnh.setPhieuNhapId(sc.nextLine());
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
          case 2 -> {
            for (int i = 0; i < dsct.length; i++) {
              if (dsct[i].getPhieuNhapId().equals(ID_PhieuNhap)) {
                System.out.print("Nhập ID sản phẩm: ");
                ctpnh.setProductId(sc.nextLine());
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
          case 3 -> {
            for (int i = 0; i < dsct.length; i++) {
              if (dsct[i].getPhieuNhapId().equals(ID_PhieuNhap)) {
                System.out.print("Nhập số lượng sản phẩm: ");
                ctpnh.setAmount(sc.nextInt());
                // SanPham[] spList = QuanLySanPham.getInstance().getListSanPham();
                // for (SanPham sp : spList) {
                // if (dsct[i].getProductId().equals(sp.getProductId())) {
                // if (ctpnh.getAmount() > dsct[i].getAmount()) {
                // sp.setAmountRemaining(sp.);
                // }
                // }
                // }
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
          case 4 -> {
            for (int i = 0; i < dsct.length; i++) {
              if (dsct[i].getPhieuNhapId().equals(ID_PhieuNhap)) {
                System.out.print("Nhập giá tiền: ");
                setPrice(sc.nextInt());
                dsct[i].setPrice(getPrice());
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
          case 5 -> {
            for (int i = 0; i < dsct.length; i++) {
              if (dsct[i].getPhieuNhapId().equals(ID_PhieuNhap)) {
                System.out.print("Nhập ID chi tiết phiếu nhập: ");
                ctpnh.setPhieuNhapId(sc.nextLine());
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
                ctpnh.setProductId(sc.nextLine());
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
                  System.out.print("Nhập số lượng sản phẩm: ");
                  ctpnh.setAmount(sc.nextInt());

                  System.out.print("Nhập giá: ");
                  setPrice(sc.nextInt());

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
    int choose = sc.nextInt();
    if (choose == 0)
      return;
    else {
      switch (choose) {
        case 1 -> {
          sc.nextLine();
          System.out.print("Nhập mã phiếu nhập hàng: ");
          String id = sc.nextLine();
          for (ChiTietPhieuNhapHang chiTietPhieu : dsct) {
            if (chiTietPhieu.getPhieuNhapId().toLowerCase().contains(id.toLowerCase())) {
              result = addChiTietPhieuNhapHang(result, chiTietPhieu);
            }
          }
        }
        case 2 -> {
          sc.nextLine();
          System.out.print("Nhập mã sản phẩm: ");
          String id = sc.nextLine();
          for (ChiTietPhieuNhapHang chiTietPhieu : dsct) {
            if (chiTietPhieu.getProductId().toLowerCase().contains(id.toLowerCase())) {
              result = addChiTietPhieuNhapHang(result, chiTietPhieu);
            }
          }
        }
        case 3 -> {
          sc.nextLine();
          System.out.print("Nhập số lượng: ");
          Integer amount = sc.nextInt();
          for (ChiTietPhieuNhapHang chiTietPhieu : dsct) {
            if (amount.equals(chiTietPhieu.getAmount())) {
              result = addChiTietPhieuNhapHang(result, chiTietPhieu);
            }
          }
        }
        case 4 -> {
          sc.nextLine();
          System.out.print("Nhập giá tiền: ");
          Integer price = sc.nextInt();
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