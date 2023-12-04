package controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

import models.SanPham;

public class QuanLySanPham implements ControllerInterface {
    public SanPham[] DSSP;
    Scanner input = new Scanner(System.in);
    private static QuanLySanPham instance;
    SanPham sanPham = new SanPham();

    private QuanLySanPham() {
        getListSanPham();
    }

    public static QuanLySanPham getInstance() {
        if (instance == null) {
            instance = new QuanLySanPham();
        }
        return instance;
    }

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
                    Integer.parseInt(row[5]));
        }
        return DSSP;
    }

    public void waitConsole() {
        System.out.println("\t\t\t\t\t\t\t\t - Ấn Enter để tiếp tục");
        input.nextLine();
    }

    @Override
    public void Read() {
        System.out.println("\t\t\t\t\t\t\t\t +----DANH SÁCH SẢN PHẨM----+");
        String header = String.format("| %-15s | %-10s | %-25s | %-9s | %-12s | %-10s |", "ID Sản phẩm", "Loại",
                "Tên sản phẩm", "Số lượng", "Giá tiền", "Trạng thái");
        System.out.format(
                "+-----------------+------------+---------------------------+-----------+--------------+------------+%n");
        System.out.println(header);
        System.out.format(
                "+-----------------+------------+---------------------------+-----------+--------------+------------+%n");

        getListSanPham();

        for (SanPham sanpham : DSSP) {
            String read = String.format("| %-15s | %-10s | %-25s | %-9s | %-12s | %-10s |",
                    sanpham.getProductId(), sanpham.getTypeOfProductId(), sanpham.getName(),
                    sanpham.getAmount(), sanpham.getPrice(), sanpham.getStatus());
            System.out.println(read);
        }
        System.out.format(
                "+-----------------+------------+---------------------------+-----------+--------------+------------+%n");
        waitConsole();
    }

    @Override
    public void Create() {
        System.out.println("\t\t\t\t\t\t\t\t +----NHẬP THÔNG TIN SẢN PHẨM----+");

        sanPham.nhapProductId(DSSP);
        sanPham.nhapTypeOfProductId(DSSP);
        sanPham.nhapName(DSSP);
        sanPham.nhapAmount(DSSP);
        sanPham.nhapPrice(DSSP);
        sanPham.nhapStatus(DSSP);

        try {
            String input = sanPham.getProductId() + ";" + sanPham.getTypeOfProductId() + ";" + sanPham.getName() + ";"
                    + sanPham.getAmount() + ";" + sanPham.getPrice() + ";" + sanPham.getStatus();
            Stream.addOneLine("Database/SanPham.txt", input);
            System.out.println("\t\t\t\t\t\t\t\t +----NHẬP SẢN PHẨM THÀNH CÔNG----+");
            waitConsole();
        } catch (IOException e) {
            e.printStackTrace();
        }
        getListSanPham();
    }

    @Override
    public void Update() {
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

            System.out.println("\t\t\t\t\t\t\t\t +----THÔNG TIN SẢN PHẨM TRƯỚC KHI CHỈNH SỬA----+");
            String header = String.format("| %-15s | %-10s | %-25s | %-9s | %-12s | %-10s |", "ID Sản phẩm", "Loại",
                    "Tên sản phẩm", "Số lượng", "Giá tiền", "Trạng thái");
            System.out.format(
                    "+-----------------+------------+---------------------------+-----------+--------------+------------+%n");
            System.out.println(header);
            System.out.format(
                    "+-----------------+------------+---------------------------+-----------+--------------+------------+%n");

            String row = String.format("| %-15s | %-10s | %-25s | %-9s | %-12s | %-10s |",
                    s_pham.getProductId(), s_pham.getTypeOfProductId(), s_pham.getName(),
                    s_pham.getAmount(), s_pham.getPrice(), s_pham.getStatus());
            System.out.println(row);
            System.out.format(
                    "+-----------------+------------+---------------------------+-----------+--------------+------------+%n");

            if (choose == 1) {
                System.out.println("\t\t\t\t\t\t\t\t +--------NHẬP MỤC LỤC CẨN SỬA-------------+");
                System.out.println("\t\t\t\t\t\t\t\t |0. Thoát                                 |");
                System.out.println("\t\t\t\t\t\t\t\t +-----------------------------------------+");
                System.out.println("\t\t\t\t\t\t\t\t |1. ID sản phẩm                           |");
                System.out.println("\t\t\t\t\t\t\t\t |2. Loại sản phẩm                         |");
                System.out.println("\t\t\t\t\t\t\t\t |3. Tên sản phẩm                          |");
                System.out.println("\t\t\t\t\t\t\t\t |4. Số lượng                              |");
                System.out.println("\t\t\t\t\t\t\t\t |5. Giá tiền                              |");
                System.out.println("\t\t\t\t\t\t\t\t |6. Trạng thái                            |");
                System.out.println("\t\t\t\t\t\t\t\t +-----------------------------------------+");
                System.out.print("\t\t\t\t\t\t\t\t - Mời Bạn Nhập Lựa Chọn: ");
                int index = input.nextInt();

                while (true) {
                    if (index < 0 || index > 6) {
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
                                input.nextLine();
                                sanPham.nhapProductId(DSSP);
                                DSSP[i].setProductId(sanPham.getProductId());
                                break;
                            case 2:
                                sanPham.nhapTypeOfProductId(DSSP);
                                DSSP[i].setTypeOfProductId(sanPham.getTypeOfProductId());
                                break;
                            case 3:
                                sanPham.nhapName(DSSP);
                                DSSP[i].setName(sanPham.getName());
                                break;
                            case 4:
                                sanPham.nhapAmount(DSSP);
                                DSSP[i].setAmount(sanPham.getAmount());
                                break;
                            case 5:
                                sanPham.nhapPrice(DSSP);
                                DSSP[i].setPrice(sanPham.getPrice());
                                break;
                            case 6:
                                sanPham.nhapStatus(DSSP);
                                DSSP[i].setStatus(sanPham.getStatus());
                                break;
                        }
                    }
                    data[i] = DSSP[i].getProductId() + ";" + DSSP[i].getTypeOfProductId() + ";" + DSSP[i].getName()
                            + ";" + DSSP[i].getAmount() + ";" + DSSP[i].getPrice() + ";" + DSSP[i].getStatus();
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
                        input.nextLine();

                        sanPham.nhapProductId(DSSP);
                        sanPham.nhapTypeOfProductId(DSSP);
                        sanPham.nhapName(DSSP);
                        sanPham.nhapAmount(DSSP);
                        sanPham.nhapPrice(DSSP);
                        sanPham.nhapStatus(DSSP);

                        DSSP[i].setProductId(sanPham.getProductId());
                        DSSP[i].setTypeOfProductId(sanPham.getTypeOfProductId());
                        DSSP[i].setName(sanPham.getName());
                        DSSP[i].setAmount(sanPham.getAmount());
                        DSSP[i].setPrice(sanPham.getPrice());
                        DSSP[i].setStatus(sanPham.getStatus());
                    }
                    data[i] = DSSP[i].getProductId() + ";" + DSSP[i].getTypeOfProductId() + ";" + DSSP[i].getName()
                            + ";"
                            + DSSP[i].getAmount() + ";" + DSSP[i].getPrice() + ";"
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

    @Override
    public void Delete() {
        try {
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
                        + DSSP[i].getAmount() + ";" + DSSP[i].getPrice() + ";"
                        + DSSP[i].getStatus();
            }

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

    public SanPham[] deleteSanPham(SanPham[] DSSP, int index) {
        SanPham[] newSp = new SanPham[DSSP.length - 1];
        for (int i = 0, j = 0; i < DSSP.length; i++) {
            if (i != index) {
                newSp[j++] = DSSP[i];
            }
        }
        return newSp;
    }

    public SanPham[] addSanPham(SanPham[] DSSP, SanPham sanpham) {
        DSSP = Arrays.copyOf(DSSP, DSSP.length + 1);
        DSSP[DSSP.length - 1] = sanpham;
        return DSSP;
    }

    @Override
    public void searchByCategory() {
        try {
            SanPham[] result = new SanPham[0];
            System.out.println("\t\t\t\t\t\t\t\t +--------NHẬP MỤC LỤC CẨN TÌM KIẾM--------+");
            System.out.println("\t\t\t\t\t\t\t\t |0. Thoát                                 |");
            System.out.println("\t\t\t\t\t\t\t\t +-----------------------------------------+");
            System.out.println("\t\t\t\t\t\t\t\t |1. ID sản phẩm                           |");
            System.out.println("\t\t\t\t\t\t\t\t |2. Loại sản phẩm                         |");
            System.out.println("\t\t\t\t\t\t\t\t |3. Tên sản phẩm                          |");
            System.out.println("\t\t\t\t\t\t\t\t |4. Số lượng                              |");
            System.out.println("\t\t\t\t\t\t\t\t |5. Giá tiền                              |");
            System.out.println("\t\t\t\t\t\t\t\t +-----------------------------------------+");
            System.out.print("\t\t\t\t\t\t\t\t - Mời Bạn Nhập Lựa Chọn: ");
            String choose = input.nextLine();

            switch (choose) {
                case "0" -> {
                    return;
                }
                case "1" -> {
                    input.nextLine();
                    System.out.println("Nhập ID sản phẩm: ");
                    String ID_Product = input.nextLine();
                    for (SanPham sanPham : DSSP) {
                        if (sanPham.getProductId().toLowerCase().contains(ID_Product.toLowerCase())) {
                            result = addSanPham(result, sanPham);
                        }
                    }
                }
                case "2" -> {
                    input.nextLine();
                    System.out.println("Nhập loại sản phẩm: ");
                    String ID_Typeofproduct = input.nextLine();
                    for (SanPham sanPham : DSSP) {
                        if (sanPham.getTypeOfProductId().toLowerCase().contains(ID_Typeofproduct.toLowerCase())) {
                            result = addSanPham(result, sanPham);
                        }
                    }
                }
                case "3" -> {
                    input.nextLine();
                    System.out.println("Nhập tên sản phẩm: ");
                    String name = input.nextLine();
                    for (SanPham sanPham : DSSP) {
                        if (sanPham.getName().toLowerCase().contains(name.toLowerCase())) {
                            result = addSanPham(result, sanPham);
                        }
                    }
                }
                case "4" -> {
                    input.nextLine();
                    System.out.println("Nhập số lượng từ: ");
                    int begin = input.nextInt();
                    System.out.println("Đến: ");
                    int end = input.nextInt();
                    for (SanPham sanPham : DSSP) {
                        if (sanPham.getAmount() <= end && sanPham.getAmount() >= begin) {
                            result = addSanPham(result, sanPham);
                        }
                    }
                }
                case "5" -> {
                    input.nextLine();
                    System.out.println("Từ giá: ");
                    int begin = input.nextInt();
                    System.out.println("Đến giá: ");
                    int end = input.nextInt();
                    for (SanPham sanPham : DSSP) {
                        if (sanPham.getPrice() <= end && sanPham.getPrice() >= begin) {
                            result = addSanPham(result, sanPham);
                        }
                    }
                }
                default -> {
                    System.out.println("Lựa chọn không hợp lệ!");
                }
            }
            
            System.out.println("\t\t\t\t\t\t\t\t +----THÔNG TIN SẢN PHẨM TÌM ĐƯỢC----+");
            String header = String.format("| %-15s | %-10s | %-25s | %-9s | %-12s | %-10s |", "ID Sản phẩm", "Loại",
                    "Tên sản phẩm", "Số lượng", "Giá tiền", "Trạng thái");
            System.out.format(
                    "+-----------------+------------+---------------------------+-----------+--------------+------------+%n");
            System.out.println(header);
            System.out.format(
                    "+-----------------+------------+---------------------------+-----------+--------------+------------+%n");

            for (SanPham sanPham : result) {
                String read = String.format("| %-15s | %-10s | %-25s | %-9s | %-12s | %-10s |",
                        sanPham.getProductId(), sanPham.getTypeOfProductId(), sanPham.getName(),
                        sanPham.getAmount(), sanPham.getPrice(), sanPham.getStatus());
                System.out.println(read);
            }
            System.out.format(
                    "+-----------------+------------+---------------------------+-----------+--------------+------------+%n");
            waitConsole();
        } catch (InputMismatchException ei) {
            System.out.println("\t\t\t\t\t\t\t\t GIÁ TRỊ KHÔNG HỢP LỆ. VUI LÒNG NHẬP LẠI!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
