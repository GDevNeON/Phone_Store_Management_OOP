package controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

import models.GiamGia;
import models.SanPham;

public class QuanLyGiamGia implements ControllerInterface {
    private static final String Discount_ID = null;
    private static QuanLyGiamGia instance;
    Scanner input = new Scanner(System.in);
    private GiamGia[] discount;
    public static QuanLyGiamGia getInstance() {
        if(instance == null){
            instance = new QuanLyGiamGia();
    } return instance;
}
    private QuanLyGiamGia(){
            getListGiamGia();
    }
    public GiamGia[] getListGiamGia() {
        String[] result = new String[0];
        try {
            result = Stream.read("Database/GiamGia.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        discount = new GiamGia[result.length];
        for (int i = 0; i < result.length; i++) {
            String[] row = result[i].split(";");
            discount[i] = new GiamGia(row[0], row[1], row[2], row[3], row[4], LocalDate.parse(row[5]),  LocalDate.parse(row[6]));
        }
        return discount;
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

        for (int i = 0; i < discount.length; i++) {
            String read = String.format("| %-5s | %-25s | %-15s | %-10s | %-10s | %-15s | %-15s |",
                    discount[i].getDiscountId(), discount[i].getKindOfCustomer(), discount[i].getProductName(),
                    discount[i].getDiscountRate(), discount[i].getStatus(), discount[i].getStartDate(), discount[i].getEndDate());
            System.out.println(read);
        }
        System.out.format(
                "+-------+---------------------------+-----------------+------------+------------+-----------------+-----------------+%n");
        waitConsole();
    }

    @Override
    public void Create() {
            System.out.println("\t\t\t\t\t\t\t\t +----NHẬP THÔNG TIN GIẢM GIÁ----+");
            GiamGia discountModel = new GiamGia();
            System.out.println("Nhập ID giảm giá (gg_): ");
            discountModel.setDiscountId(input.nextLine());

            int check = 0;
            GiamGia[] dclist = QuanLyGiamGia.getInstance().getListGiamGia();
            for (GiamGia giamgia : dclist) {
                if (discountModel.getDiscountId().equals(giamgia.getDiscountId())) {
                    check = 1;
                    break;
                }
            }

            if (check == 1) {
                System.out.println("\t\t\t\t\t\t\t\t +----MÃ GIẢM GIÁ BỊ TRÙNG----+");
                return;
            }

            System.out.println("Nhập loại khách hàng:");
            discountModel.setKindOfCustomer(input.nextLine());

            System.out.println("Nhập tên sản phẩm: ");
            discountModel.setProductName(input.nextLine());

            System.out.println("Nhập % giảm giá: ");
            discountModel.setDiscountRate(input.nextLine());

            System.out.println("Nhập trạng thái: ");
            discountModel.setStatus(input.nextLine());

            System.out.println("Nhập ngày bắt đầu giảm giá: ");
            discountModel.setStartDate(LocalDate.parse(input.nextLine()));

            System.out.println("Nhập ngày kết thúc giảm giá: ");
            discountModel.setEndDate(LocalDate.parse(input.nextLine()));

            try {
                String input = discountModel.getDiscountId() + ";" + discountModel.getKindOfCustomer() + ";" +
                        discountModel.getProductName() + ";" + discountModel.getDiscountRate() + ";" +
                        discountModel.getStatus() + ";" + discountModel.getStartDate() + ";" +
                        discountModel.getEndDate();
                Stream.addOneLine("Database/GiamGia.txt", input);
                System.out.println("\t\t\t\t\t\t\t\t +----NHẬP THÔNG TIN GIẢM GIÁ THÀNH CÔNG----+");
                waitConsole();
                getListGiamGia(); // Assuming this method returns the updated list
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
            


    @Override
    public void Update() {
        try {
            System.out.println("\t\t\t\t\t\t\t\t +----CẬP NHẬT LẠI THÔNG TIN GIẢM GIÁ----+");
            System.out.print("- Mời bạn nhập ID giảm giá cần chỉnh sửa: ");
            String DiscountId = input.nextLine();
            GiamGia id = null;

            for (GiamGia discountModel : discount) {
                if (discountModel.getDiscountId().equals(DiscountId)) {
                    id = discountModel;
                    break;
                }
            }

            if (id == null) {
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
                    id.getDiscountId(), id.getKindOfCustomer(), id.getProductName(),
                    id.getDiscountRate(), id.getStatus(), id.getStartDate(), id.getEndDate());
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

                String[] data = new String[discount.length];
                for (int i = 0; i < discount.length; i++) {
                    if (discount[i].getDiscountId().equals(Discount_ID)) {
                        System.out.println("Nhập thông tin giảm giá:");

                        switch (index) {
                            case 0:
                                return;
                            case 1:
                                System.out.println("Nhập ID giảm giá:");
                                input.nextLine();
                                id.setDiscountId(input.nextLine());
                                int check = 0;
                                GiamGia[] DSGG = QuanLyGiamGia.getInstance().getListGiamGia();
                                for (GiamGia giamgia : DSGG) {
                                    if (id.getDiscountId().equals(giamgia.getDiscountId())) {
                                        check = 1;
                                        break;
                                    }
                                }
                                if (check == 0) {
                                    System.out.println("\t\t\t\t\t\t\t\t +-----MÃ GIẢM GIÁ KHÔNG TỒN TẠI-----+");
                                    return;
                                }
                                discount[i].setDiscountId(id.getDiscountId());
                                break;
                            case 2:
                                System.out.println("Nhập loại khách hàng: ");
                                input.nextLine();
                                id.setKindOfCustomer(input.nextLine());
                                discount[i].setKindOfCustomer(id.getKindOfCustomer());
                                break;
                            case 3:
                                System.out.println("Nhập tên sản phẩm: ");
                                input.nextLine();
                                id.setProductName(input.nextLine());
                                discount[i].setProductName(id.getProductName());
                                break;
                            case 4:
                                System.out.println("Nhập % giảm giá: ");
                                input.nextLine();
                                id.setDiscountRate(input.nextLine());
                                discount[i].setDiscountRate(id.getDiscountRate());
                                break;
                            case 5:
                                System.out.println("Nhập trạng thái: ");
                                input.nextLine();
                                id.setStatus(input.nextLine());
                                discount[i].setStatus(id.getStatus());
                                break;
                            case 6:
                                System.out.println("Nhập ngày bắt đầu giảm giá: ");
                                input.nextLine();
                                id.setStartDate(LocalDate.parse(input.nextLine()));
                                discount[i].setStartDate(id.getStartDate());
                                break;
                            case 7:
                                System.out.println("Nhập ngày kết thúc giảm giá: ");
                                input.nextLine();
                                id.setEndDate(LocalDate.parse(input.nextLine()));
                                discount[i].setEndDate(id.getEndDate());
                                break;
                        }
                    }
                    data[i] = discount[i].getDiscountId() + ";" + discount[i].getKindOfCustomer() + ";"
                            + discount[i].getProductName() + ";" + discount[i].getDiscountRate() + ";" + discount[i].getStatus()
                            + ";"
                            + discount[i].getStartDate() + ";" + discount[i].getEndDate();
                }
                try {
                    Stream.addAll("Database/GiamGia.txt", data);
                    System.out.println("\t\t\t\t\t\t\t\t +----SỬA THÔNG TIN GIẢM GIÁ THÀNH CÔNG----+");
                    waitConsole();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                String[] data = new String[discount.length];

                for (int i = 0; i < discount.length; i++) {
                    if (Discount_ID.equals(discount[i].getDiscountId())) {
                        System.out.println("Nhập thông tin giảm giá:");
                        System.out.println("Nhập ID giảm giá:");
                        input.nextLine();
                        id.setDiscountId(input.nextLine());

                        int check = 0;
                        for (GiamGia giamGia : discount) {
                            if (id.getDiscountId().equals(giamGia.getDiscountId())) {
                                check = 1;
                                break;
                            }
                        }

                        if (check == 1) {
                            System.out.println("\t\t\t\t\t\t\t\t +-----MÃ GIẢM GIÁ BỊ TRÙNG-----+");
                            return;
                        }

                        System.out.println("Nhập loại khách hàng: ");
                        id.setKindOfCustomer(input.nextLine());

                        System.out.println("Nhập tên sản phẩm: ");
                        id.setProductName(input.nextLine());

                        System.out.println("Nhập % giảm giá: ");
                        id.setDiscountRate(input.nextLine());

                        System.out.println("Nhập trạng thái: ");
                        id.setStatus(input.nextLine());

                        System.out.println("Nhập ngày bắt đầu giảm giá: ");
                        id.setStartDate(LocalDate.parse(input.nextLine()));

                        System.out.println("Nhập ngày kết thúc giảm giá: ");
                        id.setEndDate(LocalDate.parse(input.nextLine()));

                        discount[i].setDiscountId(id.getDiscountId());
                        discount[i].setKindOfCustomer(id.getKindOfCustomer());
                        discount[i].setProductName(id.getProductName());
                        discount[i].setDiscountRate(id.getDiscountRate());
                        discount[i].setStatus(id.getStatus());
                        discount[i].setStartDate(id.getStartDate());
                        discount[i].setEndDate(id.getEndDate());
                    }
                    data[i] = discount[i].getDiscountId() + ";" + discount[i].getKindOfCustomer() + ";"
                            + discount[i].getProductName()
                            + ";" + discount[i].getDiscountRate() + ";" + discount[i].getStatus() + ";"
                            + discount[i].getStartDate()
                            + ";" + discount[i].getEndDate();
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
            for (GiamGia giamGia : discount) {
                if (giamGia.getDiscountId().equals(Discount_ID)) {
                    giam_gia = giamGia;
                    break;
                }
            }

            if (giam_gia == null) {
                System.out.println("\t\t\t\t\t\t\t\t +----MÃ GIẢM GIÁ KHÔNG TỒN TẠI----+");
                return;
            }

            for (int i = 0; i < discount.length; i++) {
                if (Discount_ID.equals(discount[i].getDiscountId())) {
                    discount = deleteGiamGia(discount, i);
                    break;
                }
            }
            String[] data = new String[discount.length];
            for (int i = 0; i < discount.length; i++) {
                data[i] = discount[i].getDiscountId() + ";" + discount[i].getKindOfCustomer() + ";" + discount[i].getProductName()
                        + ";" + discount[i].getDiscountRate() + ";" + discount[i].getStatus() + ";" + discount[i].getStartDate()
                        + ";" + discount[i].getEndDate();
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
                newGg[j++] = discount[i];
            }
        }
        return newGg;
    }

    @Override
    public void searchByCategory() {
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
                for (GiamGia giamGia : discount) {
                    if (Discount_ID.contains(giamGia.getDiscountId())) {
                        result = addGiamGia(result, giamGia);
                    }
                }
            }
            case 2 -> {
                input.nextLine();
                System.out.print("Nhập loại khách hàng: ");
                String KindOfCustomer = input.nextLine();
                for (GiamGia giamGia : discount) {
                    if (giamGia.getKindOfCustomer().toLowerCase().contains(KindOfCustomer.toLowerCase())) {
                        result = addGiamGia(result, giamGia);
                    }
                }
            }
            case 3 -> {
                input.nextLine();
                System.out.print("Nhập tên sản phẩm: ");
                String Product_name = input.nextLine();
                for (GiamGia giamGia : discount) {
                    if (giamGia.getProductName().toLowerCase().contains(Product_name.toLowerCase())) {
                        result = addGiamGia(result, giamGia);
                    }
                }
            }
            case 4 -> {
                input.nextLine();
                System.out.print("Nhập % giảm giá: ");
                String Discount_rate = input.nextLine();
                for (GiamGia giamGia : discount) {
                    if (Discount_rate.contains(giamGia.getDiscountRate())) {
                        result = addGiamGia(result, giamGia);
                    }
                }
            }
            case 5 -> {
                input.nextLine();
                System.out.print("Nhập trạng thái: ");
                String Status = input.nextLine();
                for (GiamGia giamGia : discount) {
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
                    giamGia.getDiscountId(), giamGia.getKindOfCustomer(), giamGia.getProductName(),
                    giamGia.getDiscountRate(), giamGia.getStatus(), giamGia.getStartDate(), giamGia.getEndDate());
            System.out.println(row);
        }
        System.out.format(
                "+-------+---------------------------+-----------------+------------+------------+-----------------+-----------------+%n");
        waitConsole();
    }
}
