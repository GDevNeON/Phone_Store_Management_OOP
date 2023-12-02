package controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

import models.KhachHang;
import models.SanPham;
import models.BaoHanh;

public class QuanLyBaoHanh implements ControllerInterface {
  private static QuanLyBaoHanh instance;
  private Scanner sc = new Scanner(System.in);
  private BaoHanh[] warranty;

  public static QuanLyBaoHanh getInstance() {
    if (instance == null) {
      instance = new QuanLyBaoHanh();
    }
    return instance;
  }

  private QuanLyBaoHanh() {
    getListWarranties();
  }

  // Lấy danh sách bảo hành từ file
  public BaoHanh[] getListWarranties() {
    String[] result = new String[0];
    try {
      result = Stream.read("Database/BaoHanh.txt");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    warranty = new BaoHanh[result.length];
    for (int i = 0; i < result.length; i++) {
      String[] row = result[i].split(";");
      warranty[i] = new BaoHanh(row[0], LocalDate.parse(row[1]), row[2], row[3], row[4], row[5], row[6]);
    }
    return warranty;
  }

  @Override
  public void Read() {
    System.out.println("\t\t\t\t\t\t\t\t +----DANH SÁCH BẢO HÀNH----+");
    String header = String.format("| %-5s | %-25s | %-4s | %-9s | %-13s | %-25s | %-12s |",
        "ID sản phẩm",
        "Ngày bảo hành",
        "Số lượng",
        "Phương thức bảo hành",
        "ID khách hàng",
        "Tên khách hàng",
        "SĐT");
    System.out.format(
        "+-------------+---------------------------+----------+----------------------+---------------+---------------------------+--------------+%n");
    System.out.println(header);
    System.out.format(
        "+-------------+---------------------------+----------+----------------------+---------------+---------------------------+--------------+%n");
    getListWarranties();

    for (BaoHanh w : warranty) {
      String read = String.format("| %-11s | %-25s | %-8s | %-20s | %-13s | %-25s | %-12s |",
          w.getProductId(),
          w.getProductDate(),
          w.getYearsOfWarranty(),
          w.getWarrantyMethod(),
          w.getCustomerId(),
          w.getCustomerName(),
          w.getSdt());
      System.out.println(read);
    }
    System.out.format(
        "+-------------+---------------------------+----------+----------------------+---------------+---------------------------+--------------+%n");
    waitConsole();
  }

  @Override
  public void Create() {
    System.out.println("\t\t\t\t\t\t\t\t +----NHẬP THÔNG TIN BẢO HÀNH----+");
    BaoHanh warrantyModel = new BaoHanh();
    System.out.println("Nhập ID Sản phẩm (sp_): ");
    warrantyModel.setProductId(sc.nextLine());
    int check = 0;
    SanPham[] spList = QuanLySanPham.getInstance().getListSanPham();
    for (SanPham sp : spList) {
      if (warrantyModel.getProductId().equals(sp.getProductId())) {
        check = 1;
        break;
      }
    }
    if (check == 0) {
      System.out.println("\t\t\t\t\t\t\t\t +----MÃ SẢN PHẨM KHÔNG TỒN TẠI. VUI LÒNG KIỂM TRA LẠI----+");
      return;
    }

    System.out.println("Nhập Ngày sản xuất: ");
    warrantyModel.setProductDate(LocalDate.parse(sc.nextLine()));

    System.out.println("Nhập Năm bảo hành: ");
    warrantyModel.setYearsOfWarranty(sc.nextLine());

    System.out.println(" Phương thức bảo hành: ");
    warrantyModel.setWarrantyMethod(sc.nextLine());

    System.out.println("Nhập ID Khách hàng: ");
    warrantyModel.setCustomerId(sc.nextLine());
    KhachHang[] customers = QuanLyKhachHang.getInstance().getListCustomer();

    // TODO: check ràng buộc khách hàng, có thông tin khách hàng auto nhập vào tên +
    // sdt
    for (KhachHang customer : customers) {
      if (customer.getCustomerId().equalsIgnoreCase(warrantyModel.getCustomerId())) {
        warrantyModel.setCustomerName(customer.getName());
        warrantyModel.setSdt(customer.getSdt());
        break;
      }
    }

    updateList(0, warranty);
    getListWarranties();
  }

  @Override
  public void Update() {
    try {
      System.out.println("\t\t\t\t\t\t\t\t +----CẬP NHẬT THÔNG TIN BẢO HÀNH----+");
      System.out.println("Nhập ID của sản phẩm cần sửa: ");
      String ID_Product = sc.nextLine();
      BaoHanh id = null;

      for (BaoHanh warrantyModel : warranty) {
        if (warrantyModel.getProductId().equals(ID_Product)) {
          id = warrantyModel;
          break;
        }
      }

      if (id == null) {
        System.out.println("\t\t\t\t\t\t\t\t +-----MÃ SẢN PHẨM KHÔNG TỒN TẠI-----+");
        return;
      }

      System.out.println("1. Sửa 1 phần của dòng");
      System.out.println("2. Sửa toàn bộ dòng");
      System.out.print("Nhập số 1 hoặc 2: ");
      int choose = sc.nextInt();
      while (true) {
        if (choose < 1 || choose > 2) {
          System.out.print("Nhập lại: ");
          choose = sc.nextInt();
        } else {
          break;
        }
      }

      System.out.println("\t\t\t\t\t\t\t\t +----THÔNG TIN BẢO HÀNH TRƯỚC KHI CHỈNH SỬA----+");
      String header = String.format("| %-11s | %-25s | %-8s | %-20s | %-13s | %-25s | %-12s |",
          "ID sản phẩm",
          "Ngày bảo hành",
          "Số lượng",
          "Phương thức bảo hành",
          "ID khách hàng",
          "Tên khách hàng",
          "SĐT");
      System.out.format(
          "+-------------+---------------------------+----------+----------------------+---------------+---------------------------+--------------+%n");
      System.out.println(header);
      System.out.format(
          "+-------------+---------------------------+----------+----------------------+---------------+---------------------------+--------------+%n");
      String row = String.format("| %-11s | %-25s | %-8s | %-20s | %-13s | %-25s | %-12s |",
          id.getProductId(),
          id.getProductDate(),
          id.getYearsOfWarranty(),
          id.getWarrantyMethod(),
          id.getCustomerId(),
          id.getCustomerName(),
          id.getSdt());
      System.out.println(row);
      System.out.format(
          "+-------------+---------------------------+----------+----------------------+---------------+---------------------------+--------------+%n");
      if (choose == 1) {
        System.out.println("\t\t\t\t\t\t\t\t +--------NHẬP MỤC LỤC CẨN SỬA-------------+");
        System.out.println("\t\t\t\t\t\t\t\t |0. Thoát                                 |");
        System.out.println("\t\t\t\t\t\t\t\t +-----------------------------------------+");
        System.out.println("\t\t\t\t\t\t\t\t |1. ID sản phẩm                           |");
        System.out.println("\t\t\t\t\t\t\t\t |2. Ngày sản xuất                         |");
        System.out.println("\t\t\t\t\t\t\t\t |3. Năm bảo hành                          |");
        System.out.println("\t\t\t\t\t\t\t\t |4. Phương thức bảo hành                  |");
        System.out.println("\t\t\t\t\t\t\t\t |5. ID Khách hàng                         |");
        System.out.println("\t\t\t\t\t\t\t\t +-----------------------------------------+");
        System.out.print("\t\t\t\t\t\t\t\t - Mời Bạn Nhập Lựa Chọn: ");
        int index = sc.nextInt();
        while (true) {
          if (index < 0 || index > 5) {
            System.out.print("Nhập lại: ");
            index = sc.nextInt();
          } else {
            break;
          }
        }

        String[] data = new String[warranty.length];

        for (int i = 0; i < warranty.length; i++) {
          if (warranty[i].getProductId().equals(ID_Product)) {
            System.out.println("Nhập thông tin bảo hành:");
            switch (index) {
              case 0:
                return;
              case 1:
                System.out.println("Nhập ID sản phẩm: ");
                sc.nextLine();
                id.setProductId(sc.nextLine());
                int check = 0;
                SanPham[] spList = QuanLySanPham.getInstance().getListSanPham();
                for (SanPham sp : spList) {
                  if (id.getProductId().equals(sp.getProductId())) {
                    check = 1;
                    break;
                  }
                }
                if (check == 0) {
                  System.out.println("\t\t\t\t\t\t\t\t +----MÃ SẢN PHẨM KHÔNG TỒN TẠI----+");
                  return;
                }
                warranty[i].setProductId(id.getProductId());
                break;
              case 2:
                System.out.println("Nhập Ngày sản xuất: ");
                sc.nextLine();
                id.setProductDate(LocalDate.parse(sc.nextLine()));
                warranty[i].setProductDate(id.getProductDate());
                break;
              case 3:
                System.out.println("Nhập Năm bảo hành: ");
                sc.nextLine();
                id.setYearsOfWarranty(sc.nextLine());
                warranty[i].setYearsOfWarranty(id.getYearsOfWarranty());
                break;
              case 4:
                System.out.println("Nhập Phương thức bảo hành: ");
                sc.nextLine();
                id.setWarrantyMethod(sc.nextLine());
                warranty[i].setWarrantyMethod(id.getWarrantyMethod());
                break;
              case 5:
                System.out.println("Nhập ID Khách hàng: ");
                boolean foundCustomer = false;
                do {
                  sc.nextLine();
                  id.setCustomerId(sc.nextLine());
                  KhachHang[] customers = QuanLyKhachHang.getInstance().getListCustomer();
                  for (KhachHang customer : customers) {
                    if (customer.getCustomerId().equals(id.getCustomerId())) {
                      foundCustomer = true;
                      warranty[i].setCustomerId(id.getCustomerId());
                      break;
                    }
                  }
                } while (foundCustomer == false);
                break;
            }
          }

          data[i] = warranty[i].getProductId() + ";" +
              warranty[i].getProductDate() + ";" +
              warranty[i].getYearsOfWarranty() + ";" +
              warranty[i].getWarrantyMethod() + ";" +
              warranty[i].getCustomerId() + ";" +
              warranty[i].getCustomerName() + ";" +
              warranty[i].getSdt();
        }
        try {
          Stream.addAll("Database/BaoHanh.txt", data);
          System.out.println("\t\t\t\t\t\t\t\t+----SỬA THÔNG TIN BẢO HÀNH THÀNH CÔNG----+");
          waitConsole();
        } catch (IOException e) {
          e.printStackTrace();
        }
      } else {
        // Sửa toàn bộ dòng
        String[] data = new String[warranty.length];

        for (int i = 0; i < warranty.length; i++) {
          if (warranty[i].getProductId().equals(ID_Product)) {
            System.out.println("Nhập thông tin bảo hành:");

            System.out.println("Nhập Ngày sản xuất: ");
            sc.nextLine();
            id.setProductDate(LocalDate.parse(sc.nextLine()));

            System.out.println("Nhập Năm bảo hành: ");
            id.setYearsOfWarranty(sc.nextLine());

            System.out.println("Nhập Phương thức bảo hành: ");
            id.setWarrantyMethod(sc.nextLine());

            System.out.println("Nhập ID Khách hàng: ");
            boolean foundCustomer = false;
            do {
              sc.nextLine();
              id.setCustomerId(sc.nextLine());
              KhachHang[] customers = QuanLyKhachHang.getInstance().getListCustomer();
              for (KhachHang customer : customers) {
                if (customer.getCustomerId().equals(id.getCustomerId())) {
                  foundCustomer = true;
                  id.setCustomerName(customer.getName());
                  id.setSdt(customer.getSdt());
                  break;
                }
              }
            } while (foundCustomer == false);

            warranty[i].setProductDate(id.getProductDate());
            warranty[i].setYearsOfWarranty(id.getYearsOfWarranty());
            warranty[i].setWarrantyMethod(id.getWarrantyMethod());
            warranty[i].setCustomerId(id.getCustomerId());
            warranty[i].setCustomerName(id.getCustomerName());
            warranty[i].setSdt(id.getSdt());
          }
          data[i] = warranty[i].getProductId() + ";" +
              warranty[i].getProductDate() + ";" +
              warranty[i].getYearsOfWarranty() + ";" +
              warranty[i].getWarrantyMethod() + ";" +
              warranty[i].getCustomerId() + ";" +
              warranty[i].getCustomerName() + ";" +
              warranty[i].getSdt();
        }
        try {
          Stream.addAll("Database/BaoHanh.txt", data);
          System.out.println("\t\t\t\t\t\t\t\t+----SỬA THÔNG TIN BẢO HÀNH THÀNH CÔNG----+");
          waitConsole();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    } catch (InputMismatchException ei) {
      System.out.println("\t\t\t\t\t\t\t\t GIÁ TRỊ KHÔNG HỢP LỆ. VUI LÒNG NHẬP LẠI!");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  @Override
  public void Delete() {
    try {
      // Nhập và kiểm tra ID sản phẩm có tồn tại không
      // Nếu có thì xóa luôn và thông báo thành công
      // Còn không thì thông báo ID không tồn tại
      System.out.println("\t\t\t\t\t\t\t\t +----XÓA THÔNG TIN BẢO HÀNH----+");
      System.out.print("Nhập ID sản phẩm cần xóa: ");
      String ID_Product = sc.nextLine();

      BaoHanh product = null;
      for (BaoHanh w : warranty) {
        if (w.getProductId().equals(ID_Product)) {
          product = w;
          break;
        }
      }

      if (product == null) {
        System.out.println("\t\t\t\t\t\t\t\t +-----MÃ BẢO HÀNH KHÔNG TỒN TẠI-----+");
        return;
      }

      for (int i = 0; i < warranty.length; i++) {
        if (ID_Product.equals(warranty[i].getProductId())) {
          warranty = deleteWarranty(warranty, i);
          break;
        }
      }

      // Add lại nguyên danh sách đã xóa dòng dữ liệu
      updateList(1, warranty);

    } catch (InputMismatchException ei) {
      System.out.println("\t\t\t\t\t\t\t\t GIÁ TRỊ KHÔNG HỢP LỆ. VUI LÒNG NHẬP LẠI!");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  // Xóa phần tử khỏi mảng
  public BaoHanh[] deleteWarranty(BaoHanh[] warranty, int index) {
    BaoHanh[] newCs = new BaoHanh[warranty.length - 1];
    for (int i = 0, j = 0; i < warranty.length; i++) {
      if (i != index) {
        newCs[j++] = warranty[i];
      }
    }
    return newCs;
  }

  // Thêm phần tử vào mảng
  public BaoHanh[] addWarranty(BaoHanh[] warranty, BaoHanh sanpham) {
    warranty = Arrays.copyOf(warranty, warranty.length + 1);
    warranty[warranty.length - 1] = sanpham;
    return warranty;
  }

  @Override
  public void searchByCategory() {
    String find;
    System.out.println("\t\t\t\t\t\t\t\t +--------NHẬP MỤC LỤC CẨN TÌM-------------+");
    System.out.println("\t\t\t\t\t\t\t\t |0. Thoát                                 |");
    System.out.println("\t\t\t\t\t\t\t\t +-----------------------------------------+");
    System.out.println("\t\t\t\t\t\t\t\t |1. ID sản phẩm                           |");
    System.out.println("\t\t\t\t\t\t\t\t |2. Ngày sản xuất                         |");
    System.out.println("\t\t\t\t\t\t\t\t |3. Năm bảo hành                          |");
    System.out.println("\t\t\t\t\t\t\t\t |4. Phương thức bảo hành                  |");
    System.out.println("\t\t\t\t\t\t\t\t |5. ID Khách hàng                         |");
    System.out.println("\t\t\t\t\t\t\t\t +-----------------------------------------+");
    System.out.print("\t\t\t\t\t\t\t\t - Mời Bạn Nhập Lựa Chọn: ");
    int index = sc.nextInt();

    while (true) {
      if (index < 0 || index > 5) {
        System.out.print("Nhập lại: ");
        index = sc.nextInt();
      } else {
        break;
      }
    }

    System.out.print("Nhập nội dung cần tìm: ");
    sc.nextLine();
    find = sc.nextLine();

    System.out.println("\t\t\t\t\t\t\t\t +----DANH SÁCH BẢO HÀNH----+");
    String header = String.format("| %-11s | %-25s | %-8s | %-20s | %-13s | %-25s | %-12s |",
        "ID sản phẩm",
        "Ngày bảo hành",
        "Số lượng",
        "Phương thức bảo hành",
        "ID khách hàng",
        "Tên khách hàng",
        "SĐT");
    System.out.format(
        "+-------------+---------------------------+----------+----------------------+---------------+---------------------------+--------------+%n");
    System.out.println(header);

    for (int i = 0; i < warranty.length; i++) {
      switch (index) {
        case 0:
          return;
        case 1:
          if (warranty[i].getProductId().contains(find))
            OutputData(i);
          break;
        case 2:
          if (warranty[i].getProductDate().getYear() == Integer.parseInt(find))
            OutputData(i);
          break;
        case 3:
          if (warranty[i].getYearsOfWarranty().equals(find))
            OutputData(i);
          break;
        case 4:
          if (warranty[i].getWarrantyMethod().contains(find))
            OutputData(i);
          break;
        case 5:
          if (warranty[i].getCustomerId().equals(find))
            OutputData(i);
          break;
      }
    }
    System.out.format(
        "+-------------+---------------------------+----------+----------------------+---------------+---------------------------+--------------+%n");
  }

  public void OutputData(int i) {
    String row = String.format("| %-11s | %-25s | %-8s | %-20s | %-13s | %-25s | %-12s |",
        warranty[i].getProductId(),
        warranty[i].getProductDate(),
        warranty[i].getYearsOfWarranty(),
        warranty[i].getWarrantyMethod(),
        warranty[i].getCustomerId(),
        warranty[i].getCustomerName(),
        warranty[i].getSdt());
    System.out.println(row);
  }

  public String[] stringToInputInFile(BaoHanh[] warranty) {
    String[] data = new String[warranty.length];

    for (int i = 0; i < warranty.length; i++) {
      data[i] = warranty[i].getProductId() + ";" +
          warranty[i].getProductDate() + ";" +
          warranty[i].getYearsOfWarranty() + ";" +
          warranty[i].getWarrantyMethod() + ";" +
          warranty[i].getCustomerId() + ";" +
          warranty[i].getCustomerName() + ";" +
          warranty[i].getSdt();
    }

    return data;
  }

  public void updateList(int select, BaoHanh[] warranty) {
    switch (select) {
      case 0:
        try {
          String[] data = stringToInputInFile(warranty);
          Stream.addAll("Database/BaoHanh.txt", data);
          System.out.println("\t\t\t\t\t\t\t\t +----NHẬP THÔNG TIN BẢO HÀNH THÀNH CÔNG----+");
          waitConsole();
        } catch (IOException e) {
          e.printStackTrace();
        }
        break;

      case 1:
        try {
          String[] data = stringToInputInFile(warranty);
          Stream.addAll("Database/BaoHanh.txt", data);
          System.out.println("\t\t\t\t\t\t\t\t +----SỬA THÔNG TIN BẢO HÀNH THÀNH CÔNG----+");
          waitConsole();
        } catch (IOException e) {
          e.printStackTrace();
        }
        break;
    }
  }

  public void waitConsole() {
    System.out.println("\t\t\t\t\t\t\t\t - Ấn Enter để tiếp tục");
    sc.nextLine();
  }
}