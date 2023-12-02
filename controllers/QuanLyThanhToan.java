package controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

import models.KhachHang;
import models.ThanhToan;

public class QuanLyThanhToan implements ControllerInterface {
    private static QuanLyThanhToan instance;
    private Scanner sc = new Scanner(System.in);
    private ThanhToan[] payment;

    public static QuanLyThanhToan getInstance() {
        if (instance == null) {
            instance = new QuanLyThanhToan();
        }
        return instance;
    }

    private QuanLyThanhToan() {
        getListPayments();
    }

    public ThanhToan[] getListPayments() {
        String[] result = new String[0];
        try {
            result = Stream.read("Database/ThanhToan.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        payment = new ThanhToan[result.length];
        for (int i = 0; i < result.length; i++) {
            String[] row = result[i].split(";");
            payment[i] = new ThanhToan(row[0], row[1], row[2], Integer.parseInt(row[3]), LocalDate.parse(row[4]),
                    row[5], row[6]);
        }
        return payment;
    }

@Override
    public void Read() {
        System.out.println("Danh sách thanh toán:");
        String header = String.format("| %-5s | %-15s | %-10s | %-9s | %-20s | %-25s | %-15s |",
                "ID Đơn Hàng",
                "ID Khách Hàng",
                "ID Hóa Đơn",
                "Số lượng",
                "Ngày thanh toán",
                "Phương thức thanh toán",
                "Trạng thái");
        System.out.format(
                "+-------------+-----------------+------------+-----------+----------------------+---------------------------+-----------------+%n");
        System.out.println(header);
        System.out.format(
                "+-------------+-----------------+------------+-----------+----------------------+---------------------------+-----------------+%n");
            getListPayments();
            
        for (ThanhToan p : payment) {
            String read = String.format("| %-11s | %-15s | %-10s | %-9s | %-20s | %-25s | %-15s |",
                    p.getPaymentId(),
                    p.getCustomerId(),
                    p.getReceiptId(),
                    p.getAmount(),
                    p.getPaymentDate(),
                    p.getPaymentMethod(),
                    p.getStatus());
            System.out.println(read);
        }
        System.out.format(
                "+-------------+-----------------+------------+-----------+----------------------+---------------------------+-----------------+%n");
        waitConsole();
    }
    @Override
    public void Create() {
    System.out.println("\t\t\t\t\t\t\t\t +----NHẬP THÔNG TIN THANH TOÁN----+");
    ThanhToan thanhToanModel = new ThanhToan();

    System.out.println("Nhập mã thanh toán (tt_): ");
    thanhToanModel.setPaymentId(sc.nextLine());

    ThanhToan[] ttList = QuanLyThanhToan.getInstance().getListPayments();
    int check = 0;
    for (ThanhToan p : ttList) {
        if (thanhToanModel.getPaymentId().equals(p.getPaymentId())) {
            check = 1;
            break;
        }
    }
    if (check == 1) {
        System.out.println("\t\t\t\t\t\t\t\t +----MÃ THANH TOÁN ĐÃ TỒN TẠI----+");
        return;
    }

    System.out.println("Nhập mã khách hàng: ");
    thanhToanModel.setCustomerId(sc.nextLine());

    KhachHang[] customers = QuanLyKhachHang.getInstance().getListCustomer();
    check = 0;
    for (KhachHang customer : customers) {
        if (customer.getCustomerId().equalsIgnoreCase(thanhToanModel.getCustomerId())) {
            check = 1;
            break;
        }
    }
    if (check == 0) {
        System.out.println("\t\t\t\t\t\t\t\t +----MÃ KHÁCH HÀNG KHÔNG TỒN TẠI----+");
        return;
    }

    System.out.println("Nhập mã đơn hàng: ");
    thanhToanModel.setReceiptId(sc.nextLine());

    check = 0;
    for (ThanhToan p : payment) {
        if (thanhToanModel.getReceiptId().equals(p.getReceiptId())) {
            check = 1;
            break;
        }
    }
    if (check == 1) {
        System.out.println("\t\t\t\t\t\t\t\t +----MÃ ĐƠN HÀNG BỊ TRÙNG----+");
        return;
    }

    System.out.println("Nhập số lượng hàng: ");
    thanhToanModel.setAmount(sc.nextInt());

    System.out.println("Nhập ngày đặt hàng: ");
    thanhToanModel.setPaymentDate(LocalDate.parse(sc.nextLine()));

    System.out.println("Nhập phương thức đặt hàng: ");
    thanhToanModel.setPaymentMethod(sc.nextLine());

    System.out.println("Nhập trạng thái của đơn hàng: ");
    thanhToanModel.setStatus(sc.nextLine());

    updateList(0, payment);
    getListPayments(); 
}

    @Override
    public void Update() {
        try {
            System.out.println("\t\t\t\t\t\t\t\t +----CẬP NHẬT THÔNG TIN THANH TOÁN----+");
            System.out.print("- Mời nhập ID thanh toán cần chỉnh sửa: ");
            String ID_Payment = sc.nextLine();
            ThanhToan id = null;

            for (ThanhToan p : payment) {
                if (p.getPaymentId().equals(ID_Payment)) {
                    id = p;
                    break;
                }
            }
            if (id == null) {
                System.out.println("\t\t\t\t\t\t\t\t +-----MÃ THANH TOÁN KHÔNG TỒN TẠI-----+");
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

            System.out.println("\t\t\t\t\t\t\t\t +----THÔNG TIN THANH TOÁN TRƯỚC KHI CHỈNH SỬA----+");
            String header = String.format("| %-5s | %-15s | %-10s | %-9s | %-20s | %-25s | %-15s |",
                    "ID Đơn Hàng",
                    "ID Khách Hàng",
                    "ID Hóa Đơn",
                    "Số lượng",
                    "Ngày thanh toán",
                    "Phương thức thanh toán",
                    "Trạng thái");
            System.out.format(
                    "+-------------+-----------------+------------+-----------+----------------------+---------------------------+-----------------+%n");
            System.out.println(header);
            System.out.format(
                    "+-------------+-----------------+------------+-----------+----------------------+---------------------------+-----------------+%n");
            String row = String.format("| %-11s | %-15s | %-10s | %-9s | %-20s | %-25s | %-15s |",
                    id.getPaymentId(),
                    id.getCustomerId(),
                    id.getReceiptId(),
                    id.getAmount(),
                    id.getPaymentDate(),
                    id.getPaymentMethod(),
                    id.getStatus());
            System.out.println(row);
            System.out.format(
                    "+-------------+-----------------+------------+-----------+----------------------+---------------------------+-----------------+%n%n");

            if (choose == 1) {
                System.out.println("\t\t\t\t\t\t\t\t +-----------NHẬP MỤC LỤC CẨN SỬA----------+");
                System.out.println("\t\t\t\t\t\t\t\t |0. Thoát                                 |");
                System.out.println("\t\t\t\t\t\t\t\t +-----------------------------------------+");
                System.out.println("\t\t\t\t\t\t\t\t |1. ID thanh toán                         |");
                System.out.println("\t\t\t\t\t\t\t\t |2. ID khách hàng                         |");
                System.out.println("\t\t\t\t\t\t\t\t |3. ID đơn hàng                           |");
                System.out.println("\t\t\t\t\t\t\t\t |4. Số lượng                              |");
                System.out.println("\t\t\t\t\t\t\t\t |5. Ngày thanh toán                       |");
                System.out.println("\t\t\t\t\t\t\t\t |6. Phương thức thanh toán                |");
                System.out.println("\t\t\t\t\t\t\t\t |7. Trạng thái                            |");
                System.out.println("\t\t\t\t\t\t\t\t +-----------------------------------------+");
                System.out.print("\t\t\t\t\t\t\t\t - Mời Bạn Nhập Lựa Chọn: ");
                int index = sc.nextInt();
                while (true) {
                    if (index < 0 || index > 7) {
                        System.out.print("Nhập lại: ");
                        index = sc.nextInt();
                    } else
                        break;
                }

                String[] data = new String[payment.length];

                for (int i = 0; i < payment.length; i++) {
                    if (payment[i].getPaymentId().equals(ID_Payment)) {
                        System.out.println("Nhập thông tin thanh toán:");

                        switch (index) {
                            case 0:
                                return;
                            case 1:
                                System.out.println("Nhập mã thanh toán (tt_): ");
                                sc.nextLine();
                                id.setPaymentId(sc.nextLine());
                                int check = 0;
                                ThanhToan[] ttList  = QuanLyThanhToan.getInstance().getListPayments();
                                for (ThanhToan p : payment) {
                                    if (id.getPaymentId().equals(p.getPaymentId())) {
                                        check = 1;
                                        break;
                                    }
                                }
                                if (check == 1) {
                                    System.out.println("\t\t\t\t\t\t\t\t +----MÃ THANH TOÁN BỊ TRÙNG----+");
                                    return;
                                }
                                payment[i].setPaymentId(id.getPaymentId());
                                break;
                            case 2:
                            System.out.println("Nhập ID Khách hàng: ");
                            boolean foundCustomer = false;
                            do {
                            sc.nextLine();
                            id.setCustomerId(sc.nextLine());
                            KhachHang[] customers = QuanLyKhachHang.getInstance().getListCustomer();
                            for (KhachHang customer : customers) {
                                if (customer.getCustomerId().equals(id.getCustomerId())) {
                                foundCustomer = true;
                                payment[i].setCustomerId(id.getCustomerId());
                                break;
                                }
                            }
                            } while (foundCustomer == false);
                            break;   
                            case 3:                  
                                System.out.println("Nhập mã đơn hàng: ");
                                sc.nextLine();
                                id.setReceiptId(sc.nextLine());
                                check = 0;
                                for (ThanhToan p : payment) {
                                    if (id.getReceiptId().equals(p.getReceiptId())) {
                                        check = 1;
                                        break;
                                    }
                                }
                                if (check == 1) {
                                    System.out.println("\t\t\t\t\t\t\t\t +----MÃ ĐƠN HÀNG BỊ TRÙNG----+");
                                    return;
                                }
                                payment[i].setReceiptId(id.getReceiptId());
                                break;
                            case 4:
                                System.out.println("Nhập số lượng hàng: ");
                                sc.nextInt();
                                id.setAmount(sc.nextInt());
                                payment[i].setAmount(id.getAmount());
                                break;
                            case 5:
                                System.out.println("Nhập ngày đặt hàng: ");
                                sc.nextLine();
                                id.setPaymentDate(LocalDate.parse(sc.nextLine()));
                                payment[i].setPaymentDate(id.getPaymentDate());
                                break;
                            case 6:
                                System.out.println("Nhập phương thức đặt hàng: ");
                                sc.nextLine();
                                id.setPaymentMethod(sc.nextLine());
                                payment[i].setPaymentMethod(id.getPaymentMethod());
                                break;
                            case 7:
                                System.out.println("Nhập trạng thái của đơn hàng: ");
                                sc.nextLine();
                                id.setStatus(sc.nextLine());
                                payment[i].setStatus(id.getStatus());
                                break;
                        }
                    }
                    data[i] = payment[i].getPaymentId() + ";" +
                            payment[i].getCustomerId() + ";" +
                            payment[i].getReceiptId() + ";" +
                            payment[i].getAmount() + ";" +
                            payment[i].getPaymentDate() + ";" +
                            payment[i].getPaymentMethod() + ";" +
                            payment[i].getStatus();
                }
                try {
                    Stream.addAll("Database/ThanhToan.txt", data);
                    System.out.println("\t\t\t\t\t\t\t\t+----SỬA THÔNG TIN SẢN PHẨM THÀNH CÔNG----+");
                    waitConsole();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                String[] data = new String[payment.length];

                for (int i = 0; i < payment.length; i++) {
                    if (payment[i].getPaymentId().equals(ID_Payment)) {
                        System.out.println("Nhập thông tin thanh toán:");

                        System.out.println("Nhập mã thanh toán (tt_): ");
                        sc.nextLine();
                        id.setPaymentId(sc.nextLine());
                        int check = 0;
                        for (ThanhToan p : payment) {
                            if (id.getPaymentId().equals(p.getPaymentId())) {
                                check = 1;
                                break;
                            }
                        }
                        if (check == 1) {
                            System.out.println("\t\t\t\t\t\t\t\t +----MÃ THANH TOÁN BỊ TRÙNG----+");
                            return;
                        }

                        System.out.println("Nhập mã khách hàng: ");
                        id.setCustomerId(sc.nextLine());
                        check = 0;
                        for (ThanhToan p : payment) {
                            if (id.getCustomerId().equals(p.getCustomerId())) {
                                check = 1;
                                break;
                            }
                        }
                        if (check == 1) {
                            System.out.println("\t\t\t\t\t\t\t\t +----MÃ KHÁCH HÀNG BỊ TRÙNG----+");
                            return;
                        }

                        System.out.println("Nhập mã đơn hàng: ");
                        id.setReceiptId(sc.nextLine());
                        check = 0;
                        for (ThanhToan p : payment) {
                            if (id.getReceiptId().equals(p.getReceiptId())) {
                                check = 1;
                                break;
                            }
                        }
                        if (check == 1) {
                            System.out.println("\t\t\t\t\t\t\t\t +----MÃ ĐƠN HÀNG BỊ TRÙNG----+");
                            return;
                        }

                        System.out.println("Nhập số lượng hàng: ");
                        id.setAmount(sc.nextInt());

                        System.out.println("Nhập ngày đặt hàng: ");
                        id.setPaymentDate(LocalDate.parse(sc.nextLine()));

                        System.out.println("Nhập phương thức đặt hàng: ");
                        id.setPaymentMethod(sc.nextLine());

                        System.out.println("Nhập trạng thái của đơn hàng: ");
                        id.setStatus(sc.nextLine());

                        payment[i].setPaymentId(id.getPaymentId());
                        payment[i].setCustomerId(id.getCustomerId());
                        payment[i].setReceiptId(id.getReceiptId());
                        payment[i].setAmount(id.getAmount());
                        payment[i].setPaymentDate(id.getPaymentDate());
                        payment[i].setPaymentMethod(id.getPaymentMethod());
                        payment[i].setStatus(id.getStatus());
                    }
                    data[i] = payment[i].getPaymentId() + ";" +
                            payment[i].getCustomerId() + ";" +
                            payment[i].getReceiptId() + ";" +
                            payment[i].getAmount() + ";" +
                            payment[i].getPaymentDate() + ";" +
                            payment[i].getPaymentMethod() + ";" +
                            payment[i].getStatus();
                }
                try {
                    Stream.addAll("Database/ThanhToan.txt", data);
                    System.out.println("\t\t\t\t\t\t\t\t+----SỬA THÔNG TIN SẢN PHẨM THÀNH CÔNG----+");
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
            System.out.println("\t\t\t\t\t\t\t\t +----XÓA THÔNG TIN THANH TOÁN----+");
            System.out.print("Nhập ID thanh toán cần xóa: ");
            String ID_Payment = sc.nextLine();

            ThanhToan id = null;
            for (ThanhToan p : payment) {
                if (p.getPaymentId().equals(ID_Payment)) {
                    id = p;
                    break;
                }
            }

            if (id == null) {
                System.out.println("\t\t\t\t\t\t\t\t +-----MÃ THANH TOÁN KHÔNG TỒN TẠI-----+");
                return;
            }

            for (int i = 0; i < payment.length; i++) {
                if (ID_Payment.equals(payment[i].getPaymentId())) {
                    payment = deletePayment(payment, i);
                    break;
                }
            }

            //Cập nhật lại toàn bộ list vào file
            updateList(1, payment);

        } catch (InputMismatchException ei) {
            System.out.println("\t\t\t\t\t\t\t\t GIÁ TRỊ KHÔNG HỢP LỆ. VUI LÒNG NHẬP LẠI!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    // Xóa phần tử khỏi mảng
    public ThanhToan[] deletePayment(ThanhToan[] payment, int index) {
        ThanhToan[] newCs = new ThanhToan[payment.length - 1];
        for (int i = 0, j = 0; i < payment.length; i++) {
            if (i != index) {
                newCs[j++] = payment[i];
            }
        }
        return newCs;
    }


    // Thêm phần tử vào mảng
    public ThanhToan[] addPayment(ThanhToan[] payment, ThanhToan sanpham) {
        payment = Arrays.copyOf(payment, payment.length + 1);
        payment[payment.length - 1] = sanpham;
        return payment;
    }


    @Override
    public void searchByCategory() {
        String find;
        System.out.println("\t\t\t\t\t\t\t\t +--------NHẬP MỤC LỤC CẨN TÌM KIẾM--------+");
        System.out.println("\t\t\t\t\t\t\t\t |0. Thoát                                 |");
        System.out.println("\t\t\t\t\t\t\t\t +-----------------------------------------+");
        System.out.println("\t\t\t\t\t\t\t\t |1. ID thanh toán                         |");
        System.out.println("\t\t\t\t\t\t\t\t |2. ID khách hàng                         |");
        System.out.println("\t\t\t\t\t\t\t\t |3. ID đơn hàng                           |");
        System.out.println("\t\t\t\t\t\t\t\t |4. Số lượng                              |");
        System.out.println("\t\t\t\t\t\t\t\t |5. Ngày thanh toán                       |");
        System.out.println("\t\t\t\t\t\t\t\t |6. Phương thức thanh toán                |");
        System.out.println("\t\t\t\t\t\t\t\t |7. Trạng thái                            |");
        System.out.println("\t\t\t\t\t\t\t\t +-----------------------------------------+");
        System.out.print("\t\t\t\t\t\t\t\t - Mời Bạn Nhập Lựa Chọn: ");
        int index = sc.nextInt();

        while (true) {
            if (index < 0 || index > 7) {
                System.out.print ("Nhập lại: ");
                index = sc.nextInt();
            }
            else {
                break;
            }
        }

        System.out.print ("Nhập nội dung cần tìm: ");
        sc.nextLine();
        find = sc.nextLine();

        System.out.println("\t\t\t\t\t\t\t\t +----DANH SÁCH THANH TOÁN----+");
        String header = String.format("| %-5s | %-15s | %-10s | %-10s | %-20s | %-25s | %-15s |",
                "ID hóa đơn",
                "ID khách hàng",
                "ID đơn hàng",
                "Số lượng",
                "Ngày thanh toán",
                "Phuong thức thanh toán",
                "Trạng thái");
        System.out.format(
                "+-------------+-----------------+------------+-----------+----------------------+---------------------------+-----------------+%n");
        System.out.println(header);

        for(int i = 0; i < payment.length; i++) {
            switch (index) {
                case 0:
                    return;
                case 1:
                    if (payment[i].getPaymentId().contains(find))
                        OutputData(i);
                    break;
                case 2:
                    if (payment[i].getCustomerId().equals(find))
                        OutputData(i);
                    break;
                case 3:
                    if (payment[i].getReceiptId().equals(find))
                        OutputData(i);
                    break;
                case 4:
                    if (String.valueOf(payment[i].getAmount()).equalsIgnoreCase(find))
                    OutputData(i);
                    break;
                case 5:
                    if (payment[i].getPaymentDate().equals(LocalDate.parse(find)))
                        OutputData(i);
                    break;
                case 6:
                    if (payment[i].getPaymentMethod().equalsIgnoreCase(find))
                        OutputData(i);
                    break;
                case 7:
                    if (payment[i].getStatus().equalsIgnoreCase(find))
                        OutputData(i);
                    break;
            }
        }
        System.out.format(
                "+-------------+-----------------+------------+-----------+----------------------+---------------------------+-----------------+%n");
    }


    public void OutputData(int i) {
        String row = String.format("| %-11s | %-15s | %-10s | %-9s | %-20s | %-25s | %-15s |",
                payment[i].getPaymentId(),
                payment[i].getCustomerId(),
                payment[i].getReceiptId(),
                payment[i].getAmount(),
                payment[i].getPaymentDate(),
                payment[i].getPaymentMethod(),
                payment[i].getStatus());
        System.out.println(row);
    }


    public String[] stringToInputInFile(ThanhToan[] payment) {
        String[] data = new String[payment.length];

        for (int i = 0; i < payment.length; i++) {
            data[i] = payment[i].getPaymentId() + ";" +
                    payment[i].getCustomerId() + ";" +
                    payment[i].getReceiptId() + ";" +
                    payment[i].getAmount() + ";" +
                    payment[i].getPaymentDate() + ";" +
                    payment[i].getPaymentMethod() + ";" +
                    payment[i].getStatus();
        }

        return data;
    }


    public void updateList(int select, ThanhToan[]payment) {
        switch (select) {
            case 0:
                try {
                    String[] inputStringData = stringToInputInFile(payment);
                    Stream.addAll("Database/ThanhToan.txt", inputStringData);
                    System.out.println("\t\t\t\t\t\t\t\t +----NHẬP THÔNG TIN THANH TOÁN THÀNH CÔNG----+");
                    waitConsole();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 1 :
            try {
                String[] data = stringToInputInFile(payment);
                    Stream.addAll("Database/ThanhToan.txt", data);
                    System.out.println("\t\t\t\t\t\t\t\t +----SỬA THÔNG TIN THANH TOÁN THÀNH CÔNG----+");
                    waitConsole();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }


    //public int getListLength(Payment[] payment) {
    //    return payment.length;
    //}


    public void waitConsole() {
        System.out.println("\t\t\t\t\t\t\t\t - Ấn Enter để tiếp tục");
    }
}
