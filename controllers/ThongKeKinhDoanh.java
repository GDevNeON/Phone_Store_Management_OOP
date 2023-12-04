package controllers;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Scanner;

import models.ThongKe;

public class ThongKeKinhDoanh implements ControllerInterface {
    Scanner input = new Scanner(System.in);
    private Validation validate = new Validation();
    private static ThongKeKinhDoanh instance;
    private ThongKe[] TK;

    public static ThongKeKinhDoanh getInstance() {
        if (instance == null) {
            instance = new ThongKeKinhDoanh();
        }
        return instance;
    }

    private ThongKeKinhDoanh() {
        getListThongKe();
    }

    public ThongKe[] getListThongKe() {
        String[] result = new String[0];
        try {
            result = Stream.read("Database/ThongKe.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        TK = new ThongKe[result.length];
        for (int i = 0; i < result.length; i++) {
            String[] row = result[i].split(";");
            TK[i] = new ThongKe(row[0], Integer.parseInt(row[1]), Integer.parseInt(row[2]), LocalDate.parse(row[3]),
                    Integer.parseInt(row[4]));
        }
        return TK;
    }

    public void waitConsole() {
        System.out.println("\t\t\t\t\t\t\t\t - Ấn Enter để tiếp tục");
        input.nextLine();
    }

    public void Read() {
    }

    public void Create() {
    }

    public void Update() {
    }

    public void Delete() {
    }

    public ThongKe[] addSanPham(ThongKe[] TK, ThongKe thongKe) {
        TK = Arrays.copyOf(TK, TK.length + 1);
        TK[TK.length - 1] = thongKe;
        return TK;
    }

    @Override
    public void searchByCategory() {
        ThongKe[] result = new ThongKe[0];
        System.out.println("\t\t\t\t\t\t\t\t +--------NHẬP MỤC LỤC CẨN THỐNG KÊ--------+");
        System.out.println("\t\t\t\t\t\t\t\t |0. Thoát                                 |");
        System.out.println("\t\t\t\t\t\t\t\t +-----------------------------------------+");
        System.out.println("\t\t\t\t\t\t\t\t |1. ID sản phẩm                           |");
        System.out.println("\t\t\t\t\t\t\t\t |2. Thống kê theo khoảng thời gian        |");
        System.out.println("\t\t\t\t\t\t\t\t +-----------------------------------------+");
        System.out.print("\t\t\t\t\t\t\t\t - Mời Bạn Nhập Lựa Chọn: ");
        String choose = input.nextLine();

        switch (choose) {
            case "0" -> {
                return;
            }
            case "1" -> {
                System.out.println("Nhập ID sản phẩm: ");
                String productId = input.nextLine();
                for (ThongKe thongKe : TK) {
                    if (thongKe.getProductId().contains(productId)) {
                        result = addSanPham(result, thongKe);
                    }
                }
            }
            case "2" -> {
                String startDateString, endDateString;
                LocalDate startDate = null, endDate = null;

                do {
                    System.out.println("Nhập ngày bắt đầu (yyyy-MM-dd): ");
                    startDateString = input.nextLine();
                } while (!validate.isValidDate(startDateString));

                do {
                    System.out.println("Nhập ngày kết thúc (yyyy-MM-dd): ");
                    endDateString = input.nextLine();
                } while (!validate.isValidDate(endDateString));

                startDate = LocalDate.parse(startDateString);
                endDate = LocalDate.parse(endDateString);

                for (ThongKe thongKe : TK) {
                    LocalDate thongKeDate = thongKe.getDate();
                    if ((thongKeDate.isEqual(startDate) || thongKeDate.isAfter(startDate))
                            && (thongKeDate.isEqual(endDate) || thongKeDate.isBefore(endDate.plusDays(1)))) {
                        result = addSanPham(result, thongKe);
                    }
                }
            }

            default -> {
                System.out.println("Lựa chọn không hợp lệ!");
            }
        }

        System.out.println("Danh sách thống kê:");
        String header = String.format("| %-8s | %-10s | %-12s | %-15s | %-15s |", "ID", "Số lượng", "Giá",
                "Ngày", "Tổng");
        System.out.format("+----------+------------+--------------+-----------------+-----------------+%n");
        System.out.println(header);
        System.out.format("+----------+------------+--------------+-----------------+-----------------+%n");
        for (ThongKe thongKe : result) {
            String read = String.format("| %-8s | %-10s | %-12s | %-15s | %-15s |", thongKe.getProductId(),
                    thongKe.getAmount(), thongKe.getPrice(), thongKe.getDate(), thongKe.getTotalAmount());
            System.out.println(read);
        }
        System.out.format("+----------+------------+--------------+-----------------+-----------------+%n");
        waitConsole();
    }
}
