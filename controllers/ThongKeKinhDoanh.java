package controllers;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

import models.ThongKe;

public class ThongKeKinhDoanh implements ControllerInterface {
    Scanner input = new Scanner(System.in);
    private Validation validate = new Validation();
    private static ThongKeKinhDoanh instance;
    private ThongKe[] TK;
    private int totalAmount = 0;
    private int totalPrice = 0;

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
            TK[i] = new ThongKe(row[0], row[1], Integer.parseInt(row[2]), Integer.parseInt(row[3]),
                    LocalDate.parse(row[4]),
                    Integer.parseInt(row[5]));
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

    public int calculateTotalQuantityForCustomer(String customerId) {
        int totalQuantity = 0;
        for (ThongKe thongKe : TK) {
            if (thongKe.getCustomerId().equals(customerId)) {
                totalQuantity += thongKe.getAmount();
            }
        }
        return totalQuantity;
    }

    public ThongKe[] addSanPham(ThongKe[] TK, ThongKe thongKe) {
        TK = Arrays.copyOf(TK, TK.length + 1);
        TK[TK.length - 1] = thongKe;

        totalAmount += thongKe.getAmount();
        totalPrice += thongKe.getTotalAmount();

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
        System.out.println("\t\t\t\t\t\t\t\t |3. Khách hàng mua nhiều                  |");
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
                System.out.println("Danh sách thống kê:");
                String header = String.format("| %-8s | %-12s | %-10s | %-12s | %-15s | %-15s |", "ID khách",
                        "ID sản phẩm",
                        "Số lượng", "Giá",
                        "Ngày", "Tổng");
                System.out.format(
                        "+----------+--------------+------------+--------------+-----------------+-----------------+%n");
                System.out.println(header);
                System.out.format(
                        "+----------+--------------+------------+--------------+-----------------+-----------------+%n");
                for (ThongKe thongKe : result) {
                    String read = String.format("| %-8s | %-12s | %-10s | %-12s | %-15s | %-15s |",
                            thongKe.getCustomerId(),
                            thongKe.getProductId(),
                            thongKe.getAmount(), thongKe.getPrice(), thongKe.getDate(), thongKe.getTotalAmount());
                    System.out.println(read);
                }
                System.out.format(
                        "+----------+--------------+------------+--------------+-----------------+-----------------+%n");
                System.out.println("Tổng số lượng đã bán ra: " + totalAmount);
                System.out.println("Doanh thu: " + totalPrice);
            }
            case "2" -> {
                LocalDate startDate = inputDate("Nhập ngày bắt đầu (yyyy-MM-dd): ");
                LocalDate endDate = inputDate("Nhập ngày kết thúc (yyyy-MM-dd): ");

                for (ThongKe thongKe : TK) {
                    LocalDate thongKeDate = thongKe.getDate();
                    if ((thongKeDate.isEqual(startDate) || thongKeDate.isAfter(startDate))
                            && (thongKeDate.isEqual(endDate) || thongKeDate.isBefore(endDate.plusDays(1)))) {
                        result = addSanPham(result, thongKe);
                    }
                }
                System.out.println("Danh sách thống kê:");
                String header = String.format("| %-8s | %-12s | %-10s | %-12s | %-15s | %-15s |", "ID khách",
                        "ID sản phẩm",
                        "Số lượng", "Giá",
                        "Ngày", "Tổng");
                System.out.format(
                        "+----------+--------------+------------+--------------+-----------------+-----------------+%n");
                System.out.println(header);
                System.out.format(
                        "+----------+--------------+------------+--------------+-----------------+-----------------+%n");
                for (ThongKe thongKe : result) {
                    String read = String.format("| %-8s | %-12s | %-10s | %-12s | %-15s | %-15s |",
                            thongKe.getCustomerId(),
                            thongKe.getProductId(),
                            thongKe.getAmount(), thongKe.getPrice(), thongKe.getDate(), thongKe.getTotalAmount());
                    System.out.println(read);
                }
                System.out.format(
                        "+----------+--------------+------------+--------------+-----------------+-----------------+%n");
                System.out.println("Tổng số lượng đã bán ra: " + totalAmount);
                System.out.println("Doanh thu: " + totalPrice);
            }
            case "3" -> {
                // Sắp xếp danh sách theo tổng số lượng mua giảm dần
                Arrays.sort(TK,
                        Comparator.comparingInt(
                                thongKe -> calculateTotalQuantityForCustomer(((ThongKe) thongKe).getCustomerId()))
                                .reversed());
                System.out.println("Danh sách thống kê:");
                String header = String.format("| %-8s | %-12s | %-10s | %-12s | %-15s | %-15s |", "ID khách",
                        "ID sản phẩm",
                        "Số lượng", "Giá",
                        "Ngày", "Tổng");
                System.out.format(
                        "+----------+--------------+------------+--------------+-----------------+-----------------+%n");
                System.out.println(header);
                System.out.format(
                        "+----------+--------------+------------+--------------+-----------------+-----------------+%n");
                for (ThongKe thongKe : TK) {
                    String read = String.format("| %-8s | %-12s | %-10s | %-12s | %-15s | %-15s |",
                            thongKe.getCustomerId(),
                            thongKe.getProductId(), thongKe.getAmount(), thongKe.getPrice(), thongKe.getDate(),
                            thongKe.getTotalAmount());
                    System.out.println(read);
                }
                System.out.format(
                        "+----------+--------------+------------+--------------+-----------------+-----------------+%n");
                System.out.println("Khách hàng mua nhiều nhất:");
                System.out.println("ID khách hàng: " + TK[0].getCustomerId());
                System.out.println("Tổng số lượng mua: " + calculateTotalQuantityForCustomer(TK[0].getCustomerId()));
            }
            default -> {
                System.out.println("Lựa chọn không hợp lệ!");
            }
        }
        waitConsole();
    }

    private LocalDate inputDate(String i) {
        LocalDate date = null;
        do {
            System.out.print(i);
            String dateString = input.nextLine();
            if (validate.isValidDate(dateString)) {
                date = LocalDate.parse(dateString);
            } else {
                System.out.println("Ngày không hợp lệ, vui lòng nhập lại!");
            }
        } while (date == null);
        return date;
    }
}
