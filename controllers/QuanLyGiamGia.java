package controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

import models.GiamGia;

public class QuanLyGiamGia implements ControllerInterface {
    public GiamGia[] DSGG;
    Scanner input = new Scanner(System.in);
    private static QuanLyGiamGia instance;
    GiamGia giamGia = new GiamGia();

    private QuanLyGiamGia() {
        getListGiamGia();
    }

    public static QuanLyGiamGia getInstance() {
        if (instance == null) {
            instance = new QuanLyGiamGia();
        }
        return instance;
    }

    public GiamGia[] getListGiamGia() {
        String[] result = new String[0];
        try {
            result = Stream.read("Database/GiamGia.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        DSGG = new GiamGia[result.length];
        for (int i = 0; i < result.length; i++) {
            String[] row = result[i].split(";");
            DSGG[i] = new GiamGia(row[0], row[1], row[2], row[3], LocalDate.parse(row[4]), LocalDate.parse(row[5]));
        }
        return DSGG;
    }

    public void waitConsole() {
        System.out.println("\t\t\t\t\t\t\t\t - Ấn Enter để tiếp tục");
        input.nextLine();
    }

    @Override
    public void Read() {
        System.out.println("\t\t\t\t\t\t\t\t +----DANH SÁCH GIẢM GIÁ----+");
        String header = String.format("| %-8s | %-25s | %-25s | %-10s | %-15s | %-15s |", "ID",
                "Loại khách hàng",
                "Tên sản phẩm", "% Giảm giá", "Ngày bắt đầu", "Ngày kết thúc");
        System.out.format(
                "+----------+---------------------------+---------------------------+------------+-----------------+-----------------+%n");
        System.out.println(header);
        System.out.format(
                "+----------+---------------------------+---------------------------+------------+-----------------+-----------------+%n");

        getListGiamGia();

        for (int i = 0; i < DSGG.length; i++) {
            String read = String.format("| %-8s | %-25s | %-25s | %-10s | %-15s | %-15s |",
                    DSGG[i].getDiscountId(), DSGG[i].getKindOfCustomer(), DSGG[i].getProductName(),
                    DSGG[i].getDiscountRate(), DSGG[i].getStartDate(), DSGG[i].getEndDate());
            System.out.println(read);
        }
        System.out.format(
                "+----------+---------------------------+---------------------------+------------+-----------------+-----------------+%n");
        waitConsole();
    }

    @Override
    public void Create() {
        System.out.println("\t\t\t\t\t\t\t\t +----NHẬP THÔNG TIN GIẢM GIÁ----+");

        giamGia.nhapDiscountId(DSGG);
        giamGia.nhapKindOfCustomer(DSGG);
        giamGia.nhapProductName(DSGG);
        giamGia.nhapDiscountRate(DSGG);
        giamGia.nhapStartDate(DSGG);
        giamGia.nhapEndDate(DSGG);

        try {
            String input = giamGia.getDiscountId() + ";" + giamGia.getKindOfCustomer() + ";" +
                    giamGia.getProductName() + ";" + giamGia.getDiscountRate() + ";" + giamGia.getStartDate() + ";"
                    + giamGia.getEndDate();
            Stream.addOneLine("Database/GiamGia.txt", input);
            System.out.println("\t\t\t\t\t\t\t\t +----NHẬP THÔNG TIN GIẢM GIÁ THÀNH CÔNG----+");
            waitConsole();
        } catch (IOException e) {
            e.printStackTrace();
        }
        getListGiamGia();
    }

    @Override
    public void Update() {
        try {
            System.out.println("\t\t\t\t\t\t\t\t +----CẬP NHẬT LẠI THÔNG TIN GIẢM GIÁ----+");
            System.out.print("- Mời bạn nhập ID giảm giá cần chỉnh sửa: ");
            String DiscountId = input.nextLine();
            GiamGia g_gia = null;

            for (GiamGia giamgia : DSGG) {
                if (giamgia.getDiscountId().equals(DiscountId)) {
                    g_gia = giamgia;
                    break;
                }
            }

            if (g_gia == null) {
                System.out.println("\t\t\t\t\t\t\t\t +----MÃ GIẢM GIÁ KHÔNG TỒN TẠI----+");
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

            System.out.println("\t\t\t\t\t\t\t\t +----THÔNG TIN GIẢM GIÁ TRƯỚC KHI ĐƯỢC CHỈNH SỬA----+");
            String header = String.format("| %-8s | %-25s | %-25s | %-10s | %-15s | %-15s |", "ID",
                    "Loại khách hàng", "Tên sản phẩm", "% Giảm giá", "Ngày bắt đầu", "Ngày kết thúc");
            System.out.format(
                    "+----------+---------------------------+---------------------------+------------+-----------------+-----------------+%n");
            System.out.println(header);
            System.out.format(
                    "+----------+---------------------------+---------------------------+------------+-----------------+-----------------+%n");
            String row = String.format("| %-8s | %-25s | %-25s | %-10s | %-15s | %-15s |",
                    g_gia.getDiscountId(), g_gia.getKindOfCustomer(), g_gia.getProductName(),
                    g_gia.getDiscountRate(), g_gia.getStartDate(), g_gia.getEndDate());
            System.out.println(row);
            System.out.format(
                    "+----------+---------------------------+---------------------------+------------+-----------------+-----------------+%n");

            if (choose == 1) {
                System.out.println("\t\t\t\t\t\t\t\t +--------NHẬP MỤC LỤC CẨN SỬA-------------+");
                System.out.println("\t\t\t\t\t\t\t\t |0. Thoát                                 |");
                System.out.println("\t\t\t\t\t\t\t\t +-----------------------------------------+");
                System.out.println("\t\t\t\t\t\t\t\t |1. Mã giảm giá                           |");
                System.out.println("\t\t\t\t\t\t\t\t |2. Loại khách hàng                       |");
                System.out.println("\t\t\t\t\t\t\t\t |3. Tên sản phẩm                          |");
                System.out.println("\t\t\t\t\t\t\t\t |4. % giảm giá                            |");
                System.out.println("\t\t\t\t\t\t\t\t |5. Ngày bắt đầu giảm giá                 |");
                System.out.println("\t\t\t\t\t\t\t\t |6. Ngày kết thúc giảm giá                |");
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

                String[] data = new String[DSGG.length];
                for (int i = 0; i < DSGG.length; i++) {
                    if (DSGG[i].getDiscountId().equals(DiscountId)) {
                        System.out.println("Nhập thông tin giảm giá:");

                        switch (index) {
                            case 0:
                                return;
                            case 1:
                                input.nextLine();
                                giamGia.nhapDiscountId(DSGG);
                                DSGG[i].setDiscountId(giamGia.getDiscountId());
                                break;
                            case 2:
                                giamGia.nhapKindOfCustomer(DSGG);
                                DSGG[i].setKindOfCustomer(giamGia.getKindOfCustomer());
                                break;
                            case 3:
                                giamGia.nhapProductName(DSGG);
                                DSGG[i].setProductName(giamGia.getProductName());
                                break;
                            case 4:
                                giamGia.nhapDiscountRate(DSGG);
                                DSGG[i].setDiscountRate(giamGia.getDiscountRate());
                                break;
                            case 5:
                                giamGia.nhapStartDate(DSGG);
                                DSGG[i].setStartDate(giamGia.getStartDate());
                                break;
                            case 6:
                                giamGia.nhapEndDate(DSGG);
                                DSGG[i].setEndDate(giamGia.getEndDate());
                                break;
                            default:
                                System.out.println("Lựa chọn không hợp lệ!");
                        }
                    }
                    data[i] = DSGG[i].getDiscountId() + ";" + DSGG[i].getKindOfCustomer() + ";"
                            + DSGG[i].getProductName() + ";" + DSGG[i].getDiscountRate() + ";" + DSGG[i].getStartDate()
                            + ";" + DSGG[i].getEndDate();
                }
                try {
                    Stream.addAll("Database/GiamGia.txt", data);
                    System.out.println("\t\t\t\t\t\t\t\t +----SỬA THÔNG TIN GIẢM GIÁ THÀNH CÔNG----+");
                    waitConsole();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                String[] data = new String[DSGG.length];

                for (int i = 0; i < DSGG.length; i++) {
                    if (DSGG[i].getDiscountId().equals(DiscountId)) {
                        System.out.println("Nhập thông tin giảm giá:");
                        input.nextLine();

                        giamGia.nhapDiscountId(DSGG);
                        giamGia.nhapKindOfCustomer(DSGG);
                        giamGia.nhapProductName(DSGG);
                        giamGia.nhapDiscountRate(DSGG);
                        giamGia.nhapStartDate(DSGG);
                        giamGia.nhapEndDate(DSGG);

                        DSGG[i].setDiscountId(giamGia.getDiscountId());
                        DSGG[i].setKindOfCustomer(giamGia.getKindOfCustomer());
                        DSGG[i].setProductName(giamGia.getProductName());
                        DSGG[i].setDiscountRate(giamGia.getDiscountRate());
                        DSGG[i].setStartDate(giamGia.getStartDate());
                        DSGG[i].setEndDate(giamGia.getEndDate());
                    }
                    data[i] = DSGG[i].getDiscountId() + ";" + DSGG[i].getKindOfCustomer() + ";"
                            + DSGG[i].getProductName() + ";" + DSGG[i].getDiscountRate() + ";"
                            + DSGG[i].getStartDate() + ";" + DSGG[i].getEndDate();
                }
                try {
                    Stream.addAll("Database/GiamGia.txt", data);
                    System.out.println("\t\t\t\t\t\t\t\t +----SỬA THÔNG TIN GIẢM GIÁ THÀNH CÔNG----+");
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
            System.out.println("\t\t\t\t\t\t\t\t +----XÓA THÔNG TIN GIẢM GIÁ----+");
            System.out.print("Nhập ID giảm giá cần xóa: ");
            String Discount_ID = input.nextLine();

            GiamGia giam_gia = null;
            for (GiamGia giamGia : DSGG) {
                if (giamGia.getDiscountId().equals(Discount_ID)) {
                    giam_gia = giamGia;
                    break;
                }
            }

            if (giam_gia == null) {
                System.out.println("\t\t\t\t\t\t\t\t +----MÃ GIẢM GIÁ KHÔNG TỒN TẠI----+");
                return;
            }

            for (int i = 0; i < DSGG.length; i++) {
                if (Discount_ID.equals(DSGG[i].getDiscountId())) {
                    DSGG = deleteGiamGia(DSGG, i);
                    break;
                }
            }
            String[] data = new String[DSGG.length];
            for (int i = 0; i < DSGG.length; i++) {
                data[i] = DSGG[i].getDiscountId() + ";" + DSGG[i].getKindOfCustomer() + ";"
                        + DSGG[i].getProductName() + ";" + DSGG[i].getDiscountRate() + ";"
                        + DSGG[i].getStartDate() + ";" + DSGG[i].getEndDate();
            }
            try {
                Stream.addAll("Database/GiamGia.txt", data);
                System.out.println("\t\t\t\t\t\t\t\t +----XÓA THÔNG TIN GIẢM GIÁ THÀNH CÔNG----+");
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

    public GiamGia[] addGiamGia(GiamGia[] DSGG, GiamGia giamGia) {
        DSGG = Arrays.copyOf(DSGG, DSGG.length + 1);
        DSGG[DSGG.length - 1] = giamGia;
        return DSGG;
    }

    public GiamGia[] deleteGiamGia(GiamGia[] DSGG, int index) {
        GiamGia[] newGg = new GiamGia[DSGG.length - 1];
        for (int i = 0, j = 0; i < DSGG.length; i++) {
            if (i != index) {
                newGg[j++] = DSGG[i];
            }
        }
        return newGg;
    }

    @Override
    public void searchByCategory() {
        try {
            GiamGia[] result = new GiamGia[0];
            System.out.println("\t\t\t\t\t\t\t\t +--------NHẬP MỤC LỤC CẨN TÌM KIẾM--------+");
            System.out.println("\t\t\t\t\t\t\t\t |0. Thoát                                 |");
            System.out.println("\t\t\t\t\t\t\t\t +-----------------------------------------+");
            System.out.println("\t\t\t\t\t\t\t\t |1. Mã giảm giá                           |");
            System.out.println("\t\t\t\t\t\t\t\t |2. Loại khách hàng                       |");
            System.out.println("\t\t\t\t\t\t\t\t |3. Tên sản phẩm                          |");
            System.out.println("\t\t\t\t\t\t\t\t |4. % giảm giá                            |");
            System.out.println("\t\t\t\t\t\t\t\t +-----------------------------------------+");
            System.out.print("\t\t\t\t\t\t\t\t - Mời Bạn Nhập Lựa Chọn: ");
            int choose = input.nextInt();

            switch (choose) {
                case 0 -> {
                    return;
                }
                case 1 -> {
                    input.nextLine();
                    System.out.println("Nhập ID giảm giá: ");
                    String DiscountId = input.nextLine();
                    for (GiamGia giamGia : DSGG) {
                        if (giamGia.getDiscountId().contains(DiscountId)) {
                            result = addGiamGia(result, giamGia);
                        }
                    }
                }
                case 2 -> {
                    input.nextLine();
                    System.out.println("Nhập loại khách hàng: ");
                    String KindOfCustomer = input.nextLine();
                    for (GiamGia giamGia : DSGG) {
                        if (giamGia.getKindOfCustomer().toLowerCase().contains(KindOfCustomer.toLowerCase())) {
                            result = addGiamGia(result, giamGia);
                        }
                    }
                }
                case 3 -> {
                    input.nextLine();
                    System.out.println("Nhập tên sản phẩm: ");
                    String Product_name = input.nextLine();
                    for (GiamGia giamGia : DSGG) {
                        if (giamGia.getProductName().toLowerCase().contains(Product_name.toLowerCase())) {
                            result = addGiamGia(result, giamGia);
                        }
                    }
                }
                case 4 -> {
                    input.nextLine();
                    System.out.println("Nhập % giảm giá từ: ");
                    int begin = input.nextInt();
                    System.out.println("Đến: ");
                    int end = input.nextInt();

                    for (GiamGia giamGia : DSGG) {
                        String discountRateString = giamGia.getDiscountRate().replaceAll("%", "");

                        try {
                            int discountRate = Integer.parseInt(discountRateString);
                            if (discountRate <= end && discountRate >= begin) {
                                result = addGiamGia(result, giamGia);
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Định dạng không hợp lệ: " + giamGia.getDiscountRate());
                        }
                    }
                }
                default -> {
                    System.out.println("Lựa chọn không hợp lệ!");
                }
            }

            System.out.println("\t\t\t\t\t\t\t\t +----THÔNG TIN GIẢM GIÁ TÌM ĐƯỢC----+");
            String header = String.format("| %-8s | %-25s | %-25s | %-10s | %-15s | %-15s |", "ID",
                    "Loại khách hàng", "Tên sản phẩm", "% Giảm giá", "Ngày bắt đầu", "Ngày kết thúc");
            System.out.format(
                    "+----------+---------------------------+---------------------------+------------+-----------------+-----------------+%n");
            System.out.println(header);
            System.out.format(
                    "+----------+---------------------------+---------------------------+------------+-----------------+-----------------+%n");

            for (GiamGia giamGia : result) {
                String row = String.format("| %-8s | %-25s | %-25s | %-10s | %-15s | %-15s |",
                        giamGia.getDiscountId(), giamGia.getKindOfCustomer(), giamGia.getProductName(),
                        giamGia.getDiscountRate(), giamGia.getStartDate(), giamGia.getEndDate());
                System.out.println(row);
            }
            System.out.format(
                    "+----------+---------------------------+---------------------------+------------+-----------------+-----------------+%n");
            waitConsole();
        } catch (InputMismatchException ei) {
            System.out.println("\t\t\t\t\t\t\t\t GIÁ TRỊ KHÔNG HỢP LỆ. VUI LÒNG NHẬP LẠI!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
