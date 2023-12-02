package controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
import models.SanPham;

public class QuanLySanPham implements ControllerInterface {
  private SanPham[] DSSP;
  private static Scanner input = new Scanner(System.in);
  private static QuanLySanPham instance;

  private QuanLySanPham() {
    getListSanPham();
  }

  public static QuanLySanPham getInstance() {
    if (instance == null) {
      instance = new QuanLySanPham();
    }
    return instance;
  }

  // lấy danh sách sản phẩm từ file
  public SanPham[] getListSanPham() {
    String[] result = new String[0];
    try {
      result = Stream.read("Database/SanPham.txt");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    DSSP = new SanPham[result.length];
    for (int i = 0; i < result.length; i++) {
      String[] row = result[i].split(";");
      DSSP[i] = new SanPham(row[0], row[1], row[2], Integer.parseInt(row[3]), Integer.parseInt(row[4]),
          Integer.parseInt(row[5]), Integer.parseInt(row[6]));
    }
    return DSSP;
  }

  // Đợi cho đến khi người dùng enter lần
  public void waitConsole() {
    System.out.println("\t\t\t\t\t\t\t\t - Ấn Enter để tiếp tục");
    input.nextLine();
  }

  // Xuất danh sách sản phẩm có giao diện
  @Override
  public void Read() {
    // Tiêu đề và tên các cột
    System.out.println("\t\t\t\t\t\t\t\t +----DANH SÁCH SẢN PHẨM----+");
    String header = String.format("| %-15s | %-10s | %-25s | %-9s | %-16s | %-12s | %-10s |", "ID Sản phẩm", "Loại",
        "Tên sản phẩm", "Số lượng", "Số lượng còn lại", "Giá tiền", "Trạng thái");
    System.out.format(
        "+-----------------+------------+---------------------------+-----------+------------------+--------------+------------+%n");
    System.out.println(header);
    System.out.format(
        "+-----------------+------------+---------------------------+-----------+------------------+--------------+------------+%n");
    getListSanPham();
    // Xuất các phần tử theo cột
    for (SanPham sanpham : DSSP) {
      String read = String.format("| %-15s | %-10s | %-25s | %-9s | %-16s | %-12s | %-10s |",
          sanpham.getProductId(), sanpham.getTypeOfProductId(), sanpham.getName(),
          sanpham.getAmount(), sanpham.getAmountRemaining(), sanpham.getPrice(), sanpham.getStatus());
      System.out.println(read);
    }
    System.out.format(
        "+-----------------+------------+---------------------------+-----------+------------------+--------------+------------+%n");
    waitConsole();
  }

  // Thêm phần tử vào mảng có giao diện
  @Override
  public void Create() {
    // Phải nhập ID trước để kiểm tra có thông tin nào trùng ID thì báo lỗi
    System.out.println("\t\t\t\t\t\t\t\t +----NHẬP THÔNG TIN SẢN PHẨM----+");
    SanPham sanPham = new SanPham();
    System.out.println("Nhập ID sản phẩm (sp_): ");
    sanPham.setProductId(input.nextLine());

    int check = 0;
    for (SanPham sanpham : DSSP) {
      if (sanPham.getProductId().equals(sanpham.getProductId())) {
        check = 1;
        break;
      }
    }
    if (check == 1) {
      System.out.println("\t\t\t\t\t\t\t\t +----MÃ SẢN PHẨM BỊ TRÙNG----+");
      return;
    }

    // Nếu ID không trùng thì tiếp tục nhập các thông tin còn lại (không nhập lại ID
    // vì hàm trên đã gọi hàm setID_Product)

    String name;
    do {
      System.out.println("Nhập tên sản phẩm: ");
      name = input.nextLine();
    } while (name.isEmpty());
    sanPham.setName(name);

    String ID_TypeOfProduct;
    do {
      System.out.println("Nhập loại: ");
      ID_TypeOfProduct = input.nextLine();
    } while (ID_TypeOfProduct.isEmpty());
    sanPham.setTypeOfProductId(ID_TypeOfProduct);

    System.out.println("Nhập số lượng: ");
    int amount = input.nextInt();
    sanPham.setAmount(amount);

    int amount_remaining;
    do {
      System.out.println("Nhập số lượng còn lại: ");
      amount_remaining = input.nextInt();
      sanPham.setAmountRemaining(amount_remaining);
    } while (amount_remaining > amount);

    System.out.println("Nhập giá tiền: ");
    sanPham.setPrice(input.nextInt());

    System.out.println("Nhập trạng thái: ");
    sanPham.setStatus(input.nextInt());

    // Sau khi nhập dữ liệu thì chuyển dữ liệu nhập vào thành chuỗi và nhập vào file
    // bằng hàm Stream.addOneLine)
    try {
      String input = sanPham.getProductId() + ";" + sanPham.getTypeOfProductId() + ";" + sanPham.getName() + ";"
          + sanPham.getAmount() + ";"
          + sanPham.getAmountRemaining() + ";" + sanPham.getPrice() + ";" + sanPham.getStatus();
      Stream.addOneLine("Database/SanPham.txt", input);
      System.out.println("\t\t\t\t\t\t\t\t +----NHẬP SẢN PHẨM THÀNH CÔNG----+");
      waitConsole();
    } catch (IOException e) {
      e.printStackTrace();
    }
    getListSanPham();
  }

  // cập nhật dòng dữ liệu theo ID có giao diện
  @Override
  public void Update() {
    // Nhập ID để kiểm tra dòng dữ liệu với ID này có tồn tại không
    try {
      System.out.println("\t\t\t\t\t\t\t\t +----CẬP NHẬT THÔNG TIN SẢN PHẨM----+");
      System.out.print("- Mời nhập ID sản phẩm cần chỉnh sửa: ");
      String ID_Product = input.nextLine();
      SanPham s_pham = null;

      for (SanPham sanpham : DSSP) {
        if (sanpham.getProductId().equals(ID_Product)) {
          s_pham = sanpham;
          break;
        }
      }

      if (s_pham == null) {
        System.out.println("\t\t\t\t\t\t\t\t +-----MÃ SẢN PHẨM KHÔNG TỒN TẠI-----+");
        return;
      }

      System.out.println("1. Sửa 1 phần của dòng");
      System.out.println("2. Sửa toàn bộ dòng");
      System.out.print("Nhập số 1 hoặc 2: ");
      int choose = input.nextInt();
      while (true) {
        if (choose < 1 || choose > 2) {
          System.out.print("Nhập lại: ");
          choose = input.nextInt();
        } else {
          break;
        }
      }
      // Nếu dòng dữ liệu có tồn tại thì xuất ra thông tin cũ
      // và tiếp tục nhập các thông tin còn lại (Update thì
      // phải nhập lại tất cả thông tin, bao gồm cả check
      // ID có trùng vs sản phẩm khác không)
      System.out.println("\t\t\t\t\t\t\t\t +----THÔNG TIN SẢN PHẨM TRƯỚC KHI CHỈNH SỬA----+");
      String header = String.format("| %-15s | %-10s | %-25s | %-9s | %-16s | %-12s | %-10s |", "ID Sản phẩm",
          "Loại", "Tên sản phẩm", "Số lượng", "Số lượng còn lại", "Giá tiền", "Trạng thái");
      System.out.format(
          "+-----------------+------------+---------------------------+-----------+------------------+--------------+------------+%n");
      System.out.println(header);
      System.out.format(
          "+-----------------+------------+---------------------------+-----------+------------------+--------------+------------+%n");

      String row = String.format("| %-15s | %-10s | %-25s | %-9s | %-16s | %-12s | %-10s |",
          s_pham.getProductId(), s_pham.getTypeOfProductId(), s_pham.getName(),
          s_pham.getAmount(), s_pham.getAmountRemaining(), s_pham.getPrice(), s_pham.getStatus());
      System.out.println(row);
      System.out.format(
          "+-----------------+------------+---------------------------+-----------+------------------+--------------+------------+%n");

      if (choose == 1) {
        System.out.println("\t\t\t\t\t\t\t\t +--------NHẬP MỤC LỤC CẨN SỬA-------------+");
        System.out.println("\t\t\t\t\t\t\t\t |0. Thoát                                 |");
        System.out.println("\t\t\t\t\t\t\t\t +-----------------------------------------+");
        System.out.println("\t\t\t\t\t\t\t\t |1. ID sản phẩm                           |");
        System.out.println("\t\t\t\t\t\t\t\t |2. Loại sản phẩm                         |");
        System.out.println("\t\t\t\t\t\t\t\t |3. Tên sản phẩm                          |");
        System.out.println("\t\t\t\t\t\t\t\t |4. Số lượng                              |");
        System.out.println("\t\t\t\t\t\t\t\t |5. Số lượng còn lại                      |");
        System.out.println("\t\t\t\t\t\t\t\t |6. Giá tiền                              |");
        System.out.println("\t\t\t\t\t\t\t\t |7. Trạng thái                            |");
        System.out.println("\t\t\t\t\t\t\t\t +-----------------------------------------+");
        System.out.print("\t\t\t\t\t\t\t\t - Mời Bạn Nhập Lựa Chọn: ");
        int index = input.nextInt();

        while (true) {
          if (index < 0 || index > 7) {
            System.out.print("Nhập lại: ");
            index = input.nextInt();
          } else {
            break;
          }
        }

        String[] data = new String[DSSP.length];

        for (int i = 0; i < DSSP.length; i++) {
          if (DSSP[i].getProductId().equals(ID_Product)) {
            System.out.println("Nhập thông tin sản phẩm:");

            switch (index) {
              case 0:
                return;
              case 1:
                System.out.println("Nhập ID sản phẩm: ");
                input.nextLine();
                s_pham.setProductId(input.nextLine());
                int check = 0;
                for (SanPham sanpham : DSSP) {
                  if (s_pham.getProductId().equals(sanpham.getProductId())) {
                    check = 1;
                    break;
                  }
                }
                if (check == 1) {
                  System.out.println("\t\t\t\t\t\t\t\t +----MÃ SẢN PHẨM BỊ TRÙNG----+");
                  return;
                }
                DSSP[i].setProductId(s_pham.getProductId());
                break;
              case 2:
                String ID_TypeOfProduct;
                do {
                  System.out.println("Nhập loại: ");
                  ID_TypeOfProduct = input.nextLine();
                } while (ID_TypeOfProduct.isEmpty());
                s_pham.setTypeOfProductId(ID_TypeOfProduct);
                DSSP[i].setTypeOfProductId(s_pham.getTypeOfProductId());
                break;
              case 3:
                System.out.println("Nhập tên: ");
                input.nextLine();
                s_pham.setName(input.nextLine());
                DSSP[i].setName(s_pham.getName());
                break;
              case 4:
                System.out.println("Nhập số lượng: ");
                int amount = input.nextInt();
                s_pham.setAmount(amount);
                DSSP[i].setAmount(s_pham.getAmount());
                break;
              case 5:
                int amount_remaining;
                do {
                  System.out.println("Nhập số lượng còn lại: ");
                  amount_remaining = input.nextInt();
                  s_pham.setAmountRemaining(amount_remaining);
                } while (s_pham.getAmountRemaining() > s_pham.getAmount());
                DSSP[i].setAmountRemaining(s_pham.getAmountRemaining());
                break;
              case 6:
                System.out.println("Nhập giá tiền: ");
                input.nextLine();
                s_pham.setPrice(input.nextInt());
                DSSP[i].setPrice(s_pham.getPrice());
                break;
              case 7:
                System.out.println("Nhập trạng thái: ");
                input.nextLine();
                s_pham.setStatus(input.nextInt());
                DSSP[i].setStatus(s_pham.getStatus());
                break;
            }
          }
          data[i] = DSSP[i].getProductId() + ";" + DSSP[i].getTypeOfProductId() + ";" + DSSP[i].getName()
              + ";"
              + DSSP[i].getAmount() + ";" + DSSP[i].getAmountRemaining() + ";" + DSSP[i].getPrice() + ";"
              + DSSP[i].getStatus();
        }
        try {
          Stream.addAll("Database/SanPham.txt", data);
          System.out.println("\t\t\t\t\t\t\t\t+----SỬA THÔNG TIN SẢN PHẨM THÀNH CÔNG----+");
          waitConsole();
        } catch (IOException e) {
          e.printStackTrace();
        }
      } else {
        String[] data = new String[DSSP.length];

        for (int i = 0; i < DSSP.length; i++) {
          if (DSSP[i].getProductId().equals(ID_Product)) {
            System.out.println("Nhập thông tin sản phẩm:");

            System.out.println("Nhập ID sản phẩm: ");
            input.nextLine();
            s_pham.setProductId(input.nextLine());

            int check = 0;
            for (SanPham sanpham : DSSP) {
              if (s_pham.getProductId().equals(sanpham.getProductId())) {
                check = 1;
                break;
              }
            }
            if (check == 1) {
              System.out.println("\t\t\t\t\t\t\t\t +-----MÃ SẢN PHẨM BỊ TRÙNG-----+");
              return;
            }

            String ID_TypeOfProduct;
            do {
              System.out.println("Nhập loại: ");
              ID_TypeOfProduct = input.nextLine();
            } while (ID_TypeOfProduct.isEmpty());
            s_pham.setTypeOfProductId(ID_TypeOfProduct);

            String name;
            do {
              System.out.println("Nhập tên sản phẩm: ");
              name = input.nextLine();
            } while (name.isEmpty());
            s_pham.setName(name);

            System.out.println("Nhập số lượng: ");
            int amount = input.nextInt();
            s_pham.setAmount(amount);

            int amount_remaining;
            do {
              System.out.println("Nhập số lượng còn lại: ");
              amount_remaining = input.nextInt();
              s_pham.setAmountRemaining(amount_remaining);
            } while (amount_remaining > amount);

            System.out.println("Nhập giá tiền: ");
            s_pham.setPrice(input.nextInt());

            System.out.println("Nhập trạng thái: ");
            s_pham.setStatus(input.nextInt());

            DSSP[i].setProductId(s_pham.getProductId());
            DSSP[i].setTypeOfProductId(s_pham.getTypeOfProductId());
            DSSP[i].setName(s_pham.getName());
            DSSP[i].setAmount(s_pham.getAmount());
            DSSP[i].setAmountRemaining(s_pham.getAmountRemaining());
            DSSP[i].setPrice(s_pham.getPrice());
            DSSP[i].setStatus(s_pham.getStatus());
          }
          data[i] = DSSP[i].getProductId() + ";" + DSSP[i].getTypeOfProductId() + ";" + DSSP[i].getName()
              + ";"
              + DSSP[i].getAmount() + ";" + DSSP[i].getAmountRemaining() + ";" + DSSP[i].getPrice() + ";"
              + DSSP[i].getStatus();
        }

        // Cập nhật lại cả danh sách vào file
        try {
          Stream.addAll("Database/SanPham.txt", data);
          System.out.println("\t\t\t\t\t\t\t\t +----SỬA THÔNG TIN SẢN PHẨM THÀNH CÔNG----+");
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

  // Hàm xóa phần tử ghi lại file có giao diện
  @Override
  public void Delete() {
    try {
      // Nhập và kiểm tra ID sản phẩm có tồn tại không
      // Nếu có thì xóa luôn và thông báo thành công
      // Còn không thì thông báo ID không tồn tại
      System.out.println("\t\t\t\t\t\t\t\t +----XÓA THÔNG TIN SẢN PHẨM----+");
      System.out.print("Nhập ID sản phẩm cần xóa: ");
      String ID_Product = input.nextLine();

      SanPham product = null;
      for (SanPham sanpham : DSSP) {
        if (sanpham.getProductId().equals(ID_Product)) {
          product = sanpham;
          break;
        }
      }

      if (product == null) {
        System.out.println("\t\t\t\t\t\t\t\t +-----MÃ SẢN PHẨM KHÔNG TỒN TẠI-----+");
        return;
      }

      for (int i = 0; i < DSSP.length; i++) {
        if (ID_Product.equals(DSSP[i].getProductId())) {
          DSSP = deleteSanPham(DSSP, i);
          break;
        }
      }
      String[] data = new String[DSSP.length];
      for (int i = 0; i < DSSP.length; i++) {
        data[i] = DSSP[i].getProductId() + ";" + DSSP[i].getTypeOfProductId() + ";" + DSSP[i].getName() + ";"
            + DSSP[i].getAmount() + ";" + DSSP[i].getAmountRemaining() + ";" + DSSP[i].getPrice() + ";"
            + DSSP[i].getStatus();
      }

      // Add lại nguyên danh sách đã xóa dòng dữ liệu
      try {
        Stream.addAll("Database/SanPham.txt", data);
        System.out.println("\t\t\t\t\t\t\t\t +----XÓA THÔNG TIN SẢN PHẨM THÀNH CÔNG----+");
        waitConsole();
      } catch (IOException e) {
        e.printStackTrace();
      }
    } catch (InputMismatchException ei) {
      System.out.println("\t\t\t\t\t\t\t\t GIÁ TRỊ KHÔNG HỢP LỆ. VUI LÒNG NHẬP LẠI!");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  // Xóa phần tử khỏi mảng
  public SanPham[] deleteSanPham(SanPham[] DSSP, int index) {
    SanPham[] newCs = new SanPham[DSSP.length - 1];
    for (int i = 0, j = 0; i < DSSP.length; i++) {
      if (i != index) {
        newCs[j++] = DSSP[i];
      }
    }
    return newCs;
  }

  // Thêm phần tử khỏi mảng
  public SanPham[] addSanPham(SanPham[] DSSP, SanPham sanpham) {
    DSSP = Arrays.copyOf(DSSP, DSSP.length + 1);
    DSSP[DSSP.length - 1] = sanpham;
    return DSSP;
  }

  // Tìm kiếm theo loại danh mục có giao diện
  @Override
  public void searchByCategory() {
    SanPham[] result = new SanPham[0];
    System.out.println("\t\t\t\t\t\t\t\t +--------NHẬP MỤC LỤC CẨN TÌM KIẾM--------+");
    System.out.println("\t\t\t\t\t\t\t\t |0. Thoát                                 |");
    System.out.println("\t\t\t\t\t\t\t\t +-----------------------------------------+");
    System.out.println("\t\t\t\t\t\t\t\t |1. ID sản phẩm                           |");
    System.out.println("\t\t\t\t\t\t\t\t |2. Loại sản phẩm                         |");
    System.out.println("\t\t\t\t\t\t\t\t |3. Tên sản phẩm                          |");
    System.out.println("\t\t\t\t\t\t\t\t |4. Số lượng                              |");
    System.out.println("\t\t\t\t\t\t\t\t |5. Số lượng còn lại                      |");
    System.out.println("\t\t\t\t\t\t\t\t |6. Giá tiền                              |");
    System.out.println("\t\t\t\t\t\t\t\t +-----------------------------------------+");
    System.out.print("\t\t\t\t\t\t\t\t - Mời Bạn Nhập Lựa Chọn: ");
    int choose = input.nextInt();

    switch (choose) {
      case 0 -> {
        return;
      }
      case 1 -> {
        input.nextLine();
        System.out.println("Nhập ID sản phẩm: ");
        String ID_Product = input.nextLine();
        for (SanPham sanpham : DSSP) {
          if (sanpham.getProductId().toLowerCase().contains(ID_Product.toLowerCase())) {
            result = addSanPham(result, sanpham);
          }
        }
      }
      case 2 -> {
        input.nextLine();
        System.out.println("Nhập loại sản phẩm: ");
        String ID_Typeofproduct = input.nextLine();
        for (SanPham sanpham : DSSP) {
          if (sanpham.getTypeOfProductId().toLowerCase().contains(ID_Typeofproduct.toLowerCase())) {
            result = addSanPham(result, sanpham);
          }
        }
      }
      case 3 -> {
        input.nextLine();
        System.out.println("Nhập tên sản phẩm: ");
        String name = input.nextLine();
        for (SanPham sanpham : DSSP) {
          if (sanpham.getName().toLowerCase().contains(name.toLowerCase())) {
            result = addSanPham(result, sanpham);
          }
        }
      }
      case 4 -> {
        input.nextLine();
        System.out.println("Nhập số lượng từ: ");
        int begin = input.nextInt();
        System.out.println("Đến: ");
        int end = input.nextInt();
        for (SanPham sanpham : DSSP) {
          if (sanpham.getAmount() <= end && sanpham.getAmount() >= begin) {
            result = addSanPham(result, sanpham);
          }
        }
      }
      case 5 -> {
        input.nextLine();
        System.out.println("Nhập số lượng còn lại từ: ");
        int begin = input.nextInt();
        System.out.println("Đến: ");
        int end = input.nextInt();
        for (SanPham sanpham : DSSP) {
          if (sanpham.getAmountRemaining() <= end && sanpham.getAmountRemaining() >= begin) {
            result = addSanPham(result, sanpham);
          }
        }
      }
      case 6 -> {
        input.nextLine();
        System.out.println("Từ giá: ");
        int begin = input.nextInt();
        System.out.println("Đến giá: ");
        int end = input.nextInt();
        for (SanPham sanpham : DSSP) {
          if (sanpham.getPrice() <= end && sanpham.getPrice() >= begin) {
            result = addSanPham(result, sanpham);
          }
        }
      }
      default -> {
        System.out.println("Lựa chọn không hợp lệ!");
      }
    }

    System.out.println("\t\t\t\t\t\t\t\t +----THÔNG TIN SẢN PHẨM TÌM ĐƯỢC----+");
    String header = String.format("| %-15s | %-10s | %-25s | %-9s | %-16s | %-12s | %-10s |", "ID Sản phẩm", "Loại",
        "Tên sản phẩm", "Số lượng", "Số lượng còn lại", "Giá tiền", "Trạng thái");
    System.out.format(
        "+-----------------+------------+---------------------------+-----------+------------------+--------------+------------+%n");
    System.out.println(header);
    System.out.format(
        "+-----------------+------------+---------------------------+-----------+------------------+--------------+------------+%n");

    for (SanPham sanpham : result) {
      String read = String.format("| %-15s | %-10s | %-25s | %-9s | %-16s | %-12s | %-10s |",
          sanpham.getProductId(), sanpham.getTypeOfProductId(), sanpham.getName(),
          sanpham.getAmount(), sanpham.getAmountRemaining(), sanpham.getPrice(), sanpham.getStatus());
      System.out.println(read);
    }
    System.out.format(
        "+-----------------+------------+---------------------------+-----------+------------------+--------------+------------+%n");
    waitConsole();
  }
}