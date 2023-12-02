package controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

import models.GiamGia;

public class QuanLyGiamGia implements ControllerInterface {
    public GiamGia[] DSGG;
    Scanner input = new Scanner(System.in);

    public QuanLyGiamGia() {
        super();
        getListGiamGia();
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
            DSGG[i] = new GiamGia(row[0], row[1], row[2], row[3], row[4], row[5], row[6]);
        }
        return DSGG;
    }

    public void waitConsole() {
        System.out.println("\t\t\t\t\t\t\t\t -Ấn Enter để tiếp tục");
        input.nextLine();
    }

    @Override
    public void Read() {
        System.out.println("\t\t\t\t\t\t\t\t +----DANH SÁCH GIẢM GIÁ----+");
        String header = String.format("| %-5s | %-25s | %-15s | %-10s | %-10s | %-15s | %-15s |", "ID",
                "Loại khách hàng",
                "Tên sản phẩm", "% Giảm Giá", "Trạng thái", "Ngày bắt đầu", "Ngày kết thúc");
        System.out.format(
                "+-------+---------------------------+-----------------+------------+------------+-----------------+-----------------+%n");
        System.out.println(header);
        System.out.format(
                "+-------+---------------------------+-----------------+------------+------------+-----------------+-----------------+%n");

        getListGiamGia();

        for (int i = 0; i < DSGG.length; i++) {
            String read = String.format("| %-5s | %-25s | %-15s | %-10s | %-10s | %-15s | %-15s |",
                    DSGG[i].getDiscount_ID(), DSGG[i].getKindOfCustomer(), DSGG[i].getProduct_name(),
                    DSGG[i].getDiscount_rate(), DSGG[i].getStatus(), DSGG[i].getStartDate(), DSGG[i].getEndDate());
            System.out.println(read);
        }
        System.out.format(
                "+-------+---------------------------+-----------------+------------+------------+-----------------+-----------------+%n");
        waitConsole();
    }

    @Override
    public void Create() {
        try {
            System.out.println("\t\t\t\t\t\t\t\t +----NHẬP THÔNG TIN GIẢM GIÁ----+");
            System.out.println("Nhập ID giảm giá (gg_): ");
            setDiscount_ID(input.nextLine());

            int check = 0;
            for (GiamGia giamGia : DSGG) {
                if (getDiscount_ID().equals(giamGia.getDiscount_ID())) {
                    check = 1;
                    break;
                }
            }

            if (check == 1) {
                System.out.println("\t\t\t\t\t\t\t\t +----MÃ GIẢM GIÁ BỊ TRÙNG----+");
                return;
            }

            System.out.println("Nhập loại khách hàng:");
            setKindOfCustomer(input.nextLine());

            System.out.println("Nhập tên sản phẩm: ");
            setProduct_name(input.nextLine());

            System.out.println("Nhập % giảm giá: ");
            setDiscount_rate(input.nextLine());

            System.out.println("Nhập trạng thái: ");
            setStatus(input.nextLine());

            System.out.println("Nhập ngày bắt đầu giảm giá: ");
            setStartDate(input.nextLine());

            System.out.println("Nhập ngày kết thúc giảm giá: ");
            setEndDate(input.nextLine());

            try {
                String input = getDiscount_ID() + ";" + getKindOfCustomer() + ";" + getProduct_name() + ";"
                        + getDiscount_rate() + ";" + getStatus() + ";" + getStartDate() + ";" + getEndDate();
                Stream.addOneLine("Database/GiamGia.txt", input);
                System.out.println("\t\t\t\t\t\t\t\t +----NHẬP THÔNG TIN GIẢM GIÁ THÀNH CÔNG----+");
                waitConsole();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (InputMismatchException ei) {
            System.out.println("\t\t\t\t\t\t\t\t GIÁ TRỊ KHÔNG HỢP LỆ. VUI LÒNG NHẬP LẠI!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        getListGiamGia();
    }

    @Override
    public void Update() {
        try {
            System.out.println("\t\t\t\t\t\t\t\t +----CẬP NHẬT LẠI THÔNG TIN GIẢM GIÁ----+");
            System.out.print("- Mời bạn nhập ID giảm giá cần chỉnh sửa: ");
            String Discount_ID = input.nextLine();
            GiamGia g_gia = null;

            for (GiamGia giamGia : DSGG) {
                if (giamGia.getDiscount_ID().equals(Discount_ID)) {
                    g_gia = giamGia;
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
            String header = String.format("| %-5s | %-25s | %-15s | %-10s | %-10s | %-15s | %-15s |", "ID",
                    "Loại khách hàng", "Tên sản phẩm", "% Giảm Giá", "Trạng thái", "Ngày bắt đầu", "Ngày kết thúc");
            System.out.format(
                    "+-------+---------------------------+-----------------+------------+------------+-----------------+-----------------+%n");
            System.out.println(header);
            System.out.format(
                    "+-------+---------------------------+-----------------+------------+------------+-----------------+-----------------+%n");
            String row = String.format("| %-5s | %-25s | %-15s | %-10s | %-10s | %-15s | %-15s |",
                    g_gia.getDiscount_ID(), g_gia.getKindOfCustomer(), g_gia.getProduct_name(),
                    g_gia.getDiscount_rate(), g_gia.getStatus(), g_gia.getStartDate(), g_gia.getEndDate());
            System.out.println(row);
            System.out.format(
                    "+-------+---------------------------+-----------------+------------+------------+-----------------+-----------------+%n");

            if (choose == 1) {
                System.out.println("\t\t\t\t\t\t\t\t +--------NHẬP MỤC LỤC CẨN SỬA-------------+");
                System.out.println("\t\t\t\t\t\t\t\t |0. Thoát                                 |");
                System.out.println("\t\t\t\t\t\t\t\t +-----------------------------------------+");
                System.out.println("\t\t\t\t\t\t\t\t |1. Mã giảm giá                           |");
                System.out.println("\t\t\t\t\t\t\t\t |2. Loại khách hàng                       |");
                System.out.println("\t\t\t\t\t\t\t\t |3. Tên sản phẩm                          |");
                System.out.println("\t\t\t\t\t\t\t\t |4. % giảm giá                            |");
                System.out.println("\t\t\t\t\t\t\t\t |5. Trạng thái                            |");
                System.out.println("\t\t\t\t\t\t\t\t +-----------------------------------------+");
                System.out.print("\t\t\t\t\t\t\t\t - Mời Bạn Nhập Lựa Chọn: ");
                int index = input.nextInt();

                while (true) {
                    if (index < 0 || index > 5) {
                        System.out.print("Nhập lại: ");
                        index = input.nextInt();
                    } else {
                        break;
                    }
                }

                String[] data = new String[DSGG.length];

                for (int i = 0; i < DSGG.length; i++) {
                    if (Discount_ID.equals(DSGG[i].getDiscount_ID())) {
                        System.out.println("Nhập thông tin giảm giá:");

                        switch (index) {
                            case 0:
                                return;
                            case 1:
                                System.out.println("Nhập ID giảm giá:");
                                input.nextLine();
                                setDiscount_ID(input.nextLine());
                                int check = 0;
                                for (GiamGia giamGia : DSGG) {
                                    if (getDiscount_ID().equals(giamGia.getDiscount_ID())) {
                                        check = 1;
                                        break;
                                    }
                                }
                                if (check == 1) {
                                    System.out.println("\t\t\t\t\t\t\t\t +-----MÃ GIẢM GIÁ BỊ TRÙNG-----+");
                                    return;
                                }
                                DSGG[i].setDiscount_ID(getDiscount_ID());
                                break;
                            case 2:
                                System.out.println("Nhập loại khách hàng: ");
                                input.nextLine();
                                setKindOfCustomer(input.nextLine());
                                DSGG[i].setKindOfCustomer(getKindOfCustomer());
                                break;
                            case 3:
                                System.out.println("Nhập tên sản phẩm: ");
                                input.nextLine();
                                setProduct_name(input.nextLine());
                                DSGG[i].setProduct_name(getProduct_name());
                                break;
                            case 4:
                                System.out.println("Nhập % giảm giá: ");
                                input.nextLine();
                                setDiscount_rate(input.nextLine());
                                DSGG[i].setDiscount_rate(getDiscount_rate());
                                break;
                            case 5:
                                System.out.println("Nhập trạng thái: ");
                                input.nextLine();
                                setStatus(input.nextLine());
                                DSGG[i].setStatus(getStatus());
                                break;
                            case 6:
                                System.out.println("Nhập ngày bắt đầu giảm giá: ");
                                input.nextLine();
                                setStartDate(input.nextLine());
                                DSGG[i].setStartDate(getStartDate());
                                break;
                            case 7:
                                System.out.println("Nhập ngày kết thúc giảm giá: ");
                                input.nextLine();
                                setEndDate(input.nextLine());
                                DSGG[i].setEndDate(getEndDate());
                                break;
                        }
                    }
                    data[i] = DSGG[i].getDiscount_ID() + ";" + DSGG[i].getKindOfCustomer() + ";"
                            + DSGG[i].getProduct_name() + ";" + DSGG[i].getDiscount_rate() + ";" + DSGG[i].getStatus()
                            + ";"
                            + DSGG[i].getStartDate() + ";" + DSGG[i].getEndDate();
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
                    if (Discount_ID.equals(DSGG[i].getDiscount_ID())) {
                        System.out.println("Nhập ID giảm giá:");
                        setDiscount_ID(input.nextLine());

                        int check = 0;
                        for (GiamGia giamGia : DSGG) {
                            if (getDiscount_ID().equals(giamGia.getDiscount_ID())) {
                                check = 1;
                                break;
                            }
                        }

                        if (check == 1) {
                            System.out.println("\t\t\t\t\t\t\t\t +-----MÃ GIẢM GIÁ BỊ TRÙNG-----+");
                            return;
                        }

                        System.out.println("Nhập loại khách hàng: ");
                        setKindOfCustomer(input.nextLine());

                        System.out.println("Nhập tên sản phẩm: ");
                        setProduct_name(input.nextLine());

                        System.out.println("Nhập % giảm giá: ");
                        setDiscount_rate(input.nextLine());

                        System.out.println("Nhập trạng thái: ");
                        setStatus(input.nextLine());

                        System.out.println("Nhập ngày bắt đầu giảm giá: ");
                        setStartDate(input.nextLine());

                        System.out.println("Nhập ngày kết thúc giảm giá: ");
                        setEndDate(input.nextLine());

                        DSGG[i].setDiscount_ID(getDiscount_ID());
                        DSGG[i].setKindOfCustomer(getKindOfCustomer());
                        DSGG[i].setProduct_name(getProduct_name());
                        DSGG[i].setDiscount_rate(getDiscount_rate());
                        DSGG[i].setStatus(getStatus());
                        DSGG[i].setStartDate(getStartDate());
                        DSGG[i].setEndDate(getEndDate());
                    }
                    data[i] = DSGG[i].getDiscount_ID() + ";" + DSGG[i].getKindOfCustomer() + ";"
                            + DSGG[i].getProduct_name()
                            + ";" + DSGG[i].getDiscount_rate() + ";" + DSGG[i].getStatus() + ";"
                            + DSGG[i].getStartDate()
                            + ";" + DSGG[i].getEndDate();
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
                if (giamGia.getDiscount_ID().equals(Discount_ID)) {
                    giam_gia = giamGia;
                    break;
                }
            }

            if (giam_gia == null) {
                System.out.println("\t\t\t\t\t\t\t\t +----MÃ GIẢM GIÁ KHÔNG TỒN TẠI----+");
                return;
            }

            for (int i = 0; i < DSGG.length; i++) {
                if (Discount_ID.equals(DSGG[i].getDiscount_ID())) {
                    DSGG = deleteGiamGia(DSGG, i);
                    break;
                }
            }
            String[] data = new String[DSGG.length];
            for (int i = 0; i < DSGG.length; i++) {
                data[i] = DSGG[i].getDiscount_ID() + ";" + DSGG[i].getKindOfCustomer() + ";" + DSGG[i].getProduct_name()
                        + ";" + DSGG[i].getDiscount_rate() + ";" + DSGG[i].getStatus() + ";" + DSGG[i].getStartDate()
                        + ";" + DSGG[i].getEndDate();
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
    public void Search_byCategory() {
        GiamGia[] result = new GiamGia[0];
        System.out.println("\t\t\t\t\t\t\t\t +--------NHẬP MỤC LỤC CẨN TÌM KIẾM--------+");
        System.out.println("\t\t\t\t\t\t\t\t |0. Thoát                                 |");
        System.out.println("\t\t\t\t\t\t\t\t +-----------------------------------------+");
        System.out.println("\t\t\t\t\t\t\t\t |1. Mã giảm giá                           |");
        System.out.println("\t\t\t\t\t\t\t\t |2. Loại khách hàng                       |");
        System.out.println("\t\t\t\t\t\t\t\t |3. Tên sản phẩm                          |");
        System.out.println("\t\t\t\t\t\t\t\t |4. % giảm giá                            |");
        System.out.println("\t\t\t\t\t\t\t\t |5. Trạng thái                            |");
        System.out.println("\t\t\t\t\t\t\t\t +-----------------------------------------+");
        System.out.print("\t\t\t\t\t\t\t\t - Mời Bạn Nhập Lựa Chọn: ");
        int choose = input.nextInt();

        switch (choose) {
            case 0 -> {
                return;
            }
            case 1 -> {
                input.nextLine();
                System.out.print("Nhập mã giảm giá: ");
                String Discount_ID = input.nextLine();
                for (GiamGia giamGia : DSGG) {
                    if (Discount_ID.contains(giamGia.getDiscount_ID())) {
                        result = addGiamGia(result, giamGia);
                    }
                }
            }
            case 2 -> {
                input.nextLine();
                System.out.print("Nhập loại khách hàng: ");
                String KindOfCustomer = input.nextLine();
                for (GiamGia giamGia : DSGG) {
                    if (giamGia.getKindOfCustomer().toLowerCase().contains(KindOfCustomer.toLowerCase())) {
                        result = addGiamGia(result, giamGia);
                    }
                }
            }
            case 3 -> {
                input.nextLine();
                System.out.print("Nhập tên sản phẩm: ");
                String Product_name = input.nextLine();
                for (GiamGia giamGia : DSGG) {
                    if (giamGia.getProduct_name().toLowerCase().contains(Product_name.toLowerCase())) {
                        result = addGiamGia(result, giamGia);
                    }
                }
            }
            case 4 -> {
                input.nextLine();
                System.out.print("Nhập % giảm giá: ");
                String Discount_rate = input.nextLine();
                for (GiamGia giamGia : DSGG) {
                    if (Discount_rate.contains(giamGia.getDiscount_rate())) {
                        result = addGiamGia(result, giamGia);
                    }
                }
            }
            case 5 -> {
                input.nextLine();
                System.out.print("Nhập trạng thái: ");
                String Status = input.nextLine();
                for (GiamGia giamGia : DSGG) {
                    if (Status.contains(giamGia.getStatus())) {
                        result = addGiamGia(result, giamGia);
                    }
                }
            }
            default -> {
                System.out.println("Lựa chọn không hợp lệ!");
            }
        }

        System.out.println("\t\t\t\t\t\t\t\t +----THÔNG TIN GIẢM GIÁ TÌM ĐƯỢC----+");
        String header = String.format("| %-5s | %-25s | %-15s | %-10s | %-10s | %-15s | %-15s |", "ID",
                "Loại khách hàng", "Tên sản phẩm", "% Giảm Giá", "Trạng thái", "Ngày bắt đầu", "Ngày kết thúc");
        System.out.format(
                "+-------+---------------------------+-----------------+------------+------------+-----------------+-----------------+%n");
        System.out.println(header);
        System.out.format(
                "+-------+---------------------------+-----------------+------------+------------+-----------------+-----------------+%n");

        for (GiamGia giamGia : result) {
            String row = String.format("| %-5s | %-25s | %-15s | %-10s | %-10s | %-15s | %-15s |",
                    giamGia.getDiscount_ID(), giamGia.getKindOfCustomer(), giamGia.getProduct_name(),
                    giamGia.getDiscount_rate(), giamGia.getStatus(), giamGia.getStartDate(), giamGia.getEndDate());
            System.out.println(row);
        }
        System.out.format(
                "+-------+---------------------------+-----------------+------------+------------+-----------------+-----------------+%n");
        waitConsole();
    }
}
