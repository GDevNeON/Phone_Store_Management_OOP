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
        OutputHeader();
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
    String test;

    System.out.println("Nhập mã thanh toán (tt_): ");
    while (true) {
        test = sc.nextLine();
        if (test.isBlank() || (!Validation.isValidIDpayment(test))) {   //nếu như xâu test rỗng hoặc chứa toàn khoảng trắng, NHẬP LẠI ĐEEEEEEEE!!!!
                System.out.println("ID Sản phẩm không hợp lệ. Nhập lại: ");
        } else {
            break;
        }
    }
    thanhToanModel.setPaymentId(test);
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

    System.out.println("Nhập mã khách hàng (kh_): ");
    while (true) {
        test = sc.nextLine();
        if (test.isBlank() || (!Validation.isValidIDcustomer(test))) {   //nếu như xâu test rỗng hoặc chứa toàn khoảng trắng, NHẬP LẠI ĐEEEEEEEE!!!!
                System.out.println("ID Sản phẩm không hợp lệ. Nhập lại: ");
        } else {
            break;
        }
    }
    thanhToanModel.setCustomerId(test);
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

    System.out.println("Nhập mã đơn hàng (hd_): ");
    while (true) {
        test = sc.nextLine();
        if (test.isBlank() || (!Validation.isValidIDreceipt(test))) {   //nếu như xâu test rỗng hoặc chứa toàn khoảng trắng, NHẬP LẠI ĐEEEEEEEE!!!!
                System.out.println("ID Sản phẩm không hợp lệ. Nhập lại: ");
        } else {
            break;
        }
    }
    thanhToanModel.setReceiptId(test);
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
    while (sc.hasNextInt()) {
        thanhToanModel.setAmount(sc.nextInt());
    }

    System.out.println("Nhập ngày đặt hàng: ");
    while (true) {
        test = sc.nextLine();
        if (test.isBlank() || test.length() != 10) {
            System.out.println("Ngày không hợp lệ. Nhập lại: ");
        } else {
            if (Validation.isValidDate(test)) {
                thanhToanModel.setPaymentDate(LocalDate.parse(sc.nextLine()));
                break;
            } else {
                System.out.println("Ngày không hợp lệ. Nhập lại: ");
            }
        }
    }

    System.out.println("Nhập phương thức đặt hàng: ");
    while (true) {
        test = sc.nextLine();
        if (test.isBlank() || test.length() > 20) {   //nếu như xâu test rỗng hoặc chứa toàn khoảng trắng, NHẬP LẠI ĐEEEEEEEE!!!!
            System.out.println("Không hợp lệ. Nhập lại: ");
        } else {
            thanhToanModel.setPaymentMethod(test);
            break;
        }
    }

    System.out.println("Nhập trạng thái của đơn hàng (0 hoặc 1) (0: chưa xử lý, 1: đã xử lý): ");
    while (true) {
        test = sc.nextLine();
        if (test.isBlank()) {   //nếu như xâu test rỗng hoặc chứa toàn khoảng trắng, NHẬP LẠI ĐEEEEEEEE!!!!
            System.out.println("Trạng thái không được để trống. Nhập lại: ");
        } else {
            if (test.compareTo("0") == 0 || test.compareTo("1") == 0) {
                thanhToanModel.setStatus(test);
                break;
            }
            else {
                System.out.println("Trạng thái không hợp lệ. Nhập lại: ");
            }
        }
    }

    updateList(0, payment);
    getListPayments(); 
}

    @Override
    public void Update() {
        try {
            System.out.println("\t\t\t\t\t\t\t\t +----CẬP NHẬT THÔNG TIN THANH TOÁN----+");
            System.out.print("- Mời nhập ID thanh toán cần chỉnh sửa: ");
            String ID_Payment;
            ThanhToan id = null;

            while (true) {
				ID_Payment = sc.nextLine();
				if (ID_Payment.isBlank()) {   //nếu như xâu test rỗng hoặc chứa toàn khoảng trắng, NHẬP LẠI ĐEEEEEEEE!!!!
						System.out.println("Mã thanh toán không được để trống. Nhập lại: ");
				} else {
						break;
				}
			}

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
            OutputHeader();
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
                            case 0 -> {
                                return;
                            }
                            case 1 -> {
                                String test;
                                System.out.println("Nhập ID thanh toán (tt_): ");
                                sc.nextLine();
                                while (true) {
                                    test = sc.nextLine();
                                    if (test.isBlank() || (!Validation.isValidIDpayment(test))) {   //nếu như xâu test rỗng hoặc chứa toàn khoảng trắng, NHẬP LẠI ĐEEEEEEEE!!!!
                                            System.out.println("Mã thanh toán không hợp lệ. Nhập lại: ");
                                    } else {
                                        break;
                                    }
                                }
                                id.setPaymentId(test);
                                ThanhToan[] ttList = QuanLyThanhToan.getInstance().getListPayments();
                                int check = 0;
                                for (ThanhToan p : ttList) {
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
                            }

                            case 2 -> {
                                String test;
                                System.out.println("Nhập ID Khách hàng: ");
                                boolean foundCustomer = false;
                                do {
                                    sc.nextLine();
                                    while (true) {
                                        test = sc.nextLine();
                                        if (test.isBlank() || (!Validation.isValidIDcustomer(test))) {   //nếu như xâu test rỗng hoặc chứa toàn khoảng trắng, NHẬP LẠI ĐEEEEEEEE!!!!
                                                System.out.println("ID thanh toán không hợp lệ. Nhập lại: ");
                                        } else {
                                            break;
                                        }
                                    }
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
                            }
                               
                            case 3 -> {
                                String test;
                                System.out.println("Nhập mã thanh toán (tt_): ");
                                sc.nextLine();
                                while (true) {
                                    test = sc.nextLine();
                                    if (test.isBlank() || (!Validation.isValidIDreceipt(test))) {   //nếu như xâu test rỗng hoặc chứa toàn khoảng trắng, NHẬP LẠI ĐEEEEEEEE!!!!
                                            System.out.println("ID thanh toán không hợp lệ. Nhập lại: ");
                                    } else {
                                        break;
                                    }
                                }
                                id.setPaymentId(test);
                                int check = 0;
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
                            }            
                            case 4 -> {
                                System.out.println("Nhập số lượng hàng: ");
                                sc.nextLine();
                                while (sc.hasNextInt()) {
                                    sc.nextInt();
                                    id.setAmount(sc.nextInt());
                                }
                                payment[i].setAmount(id.getAmount());
                                break;
                            }
                                
                            case 5 -> {
                                String test;
                                System.out.println("Nhập ngày đặt hàng: ");
                                sc.nextLine();
                                while (true) {
                                    test = sc.nextLine();
                                    if (test.isBlank() || test.length() != 10) {
                                        System.out.println("Ngày không hợp lệ. Nhập lại: ");
                                    } else {
                                        if (Validation.isValidDate(test)) {
                                            id.setPaymentDate(LocalDate.parse(sc.nextLine()));
                                            break;
                                        } else {
                                            System.out.println("Ngày không hợp lệ. Nhập lại: ");
                                        }
                                    }
                                }
                                payment[i].setPaymentDate(id.getPaymentDate());
                                break;
                            }
                                
                            case 6 -> {
                                String test;
                                System.out.println("Nhập phương thức đặt hàng: ");
                                sc.nextLine();
                                while (true) {
                                    test = sc.nextLine();
                                    if (test.isBlank() || test.length() > 20) {   //nếu như xâu test rỗng hoặc chứa toàn khoảng trắng, NHẬP LẠI ĐEEEEEEEE!!!!
                                        System.out.println("Không hợp lệ. Nhập lại: ");
                                    } else {
                                        id.setPaymentMethod(test);
                                        break;
                                    }
                                }
                                payment[i].setPaymentMethod(id.getPaymentMethod());
                                break;
                            }
                                
                            case 7 -> {
                                String test;
                                System.out.println("Nhập trạng thái của đơn hàng (0 hoặc 1) (0: chưa xử lý, 1: đã xử lý): ");
                                sc.nextLine();
                                while (true) {
                                    test = sc.nextLine();
                                    if (test.isBlank()) {   //nếu như xâu test rỗng hoặc chứa toàn khoảng trắng, NHẬP LẠI ĐEEEEEEEE!!!!
                                        System.out.println("Trạng thái không được để trống. Nhập lại: ");
                                    } else {
                                        if (test.compareTo("0") == 0 || test.compareTo("1") == 0) {
                                            id.setStatus(test);
                                            break;
                                        }
                                        else {
                                            System.out.println("Trạng thái không hợp lệ. Nhập lại: ");
                                        }
                                    }
                                }
                                payment[i].setStatus(id.getStatus());
                                break;
                            } 
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

                            {
                                String test;
                                System.out.println("Nhập ID thanh toán (tt_): ");
                                sc.nextLine();
                                while (true) {
                                    test = sc.nextLine();
                                    if (test.isBlank() || (!Validation.isValidIDpayment(test))) {   //nếu như xâu test rỗng hoặc chứa toàn khoảng trắng, NHẬP LẠI ĐEEEEEEEE!!!!
                                            System.out.println("Mã thanh toán không hợp lệ. Nhập lại: ");
                                    } else {
                                        break;
                                    }
                                }
                                id.setPaymentId(test);
                                ThanhToan[] ttList = QuanLyThanhToan.getInstance().getListPayments();
                                int check = 0;
                                for (ThanhToan p : ttList) {
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
                            }

                            {
                                String test;
                                System.out.println("Nhập ID Khách hàng: ");
                                boolean foundCustomer = false;
                                do {
                                    sc.nextLine();
                                    while (true) {
                                        test = sc.nextLine();
                                        if (test.isBlank() || (!Validation.isValidIDcustomer(test))) {   //nếu như xâu test rỗng hoặc chứa toàn khoảng trắng, NHẬP LẠI ĐEEEEEEEE!!!!
                                                System.out.println("ID thanh toán không hợp lệ. Nhập lại: ");
                                        } else {
                                            break;
                                        }
                                    }
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
                            }
                               
                            {
                                String test;
                                System.out.println("Nhập mã thanh toán (tt_): ");
                                sc.nextLine();
                                while (true) {
                                    test = sc.nextLine();
                                    if (test.isBlank() || (!Validation.isValidIDreceipt(test))) {   //nếu như xâu test rỗng hoặc chứa toàn khoảng trắng, NHẬP LẠI ĐEEEEEEEE!!!!
                                            System.out.println("ID thanh toán không hợp lệ. Nhập lại: ");
                                    } else {
                                        break;
                                    }
                                }
                                id.setPaymentId(test);
                                int check = 0;
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
                            }        

                            {
                                System.out.println("Nhập số lượng hàng: ");
                                sc.nextLine();
                                while (sc.hasNextInt()) {
                                    sc.nextInt();
                                    id.setAmount(sc.nextInt());
                                }
                                payment[i].setAmount(id.getAmount());
                            }
                                
                            {
                                String test;
                                System.out.println("Nhập ngày đặt hàng: ");
                                sc.nextLine();
                                while (true) {
                                    test = sc.nextLine();
                                    if (test.isBlank() || test.length() != 10) {
                                        System.out.println("Ngày không hợp lệ. Nhập lại: ");
                                    } else {
                                        if (Validation.isValidDate(test)) {
                                            id.setPaymentDate(LocalDate.parse(sc.nextLine()));
                                            break;
                                        } else {
                                            System.out.println("Ngày không hợp lệ. Nhập lại: ");
                                        }
                                    }
                                }
                                payment[i].setPaymentDate(id.getPaymentDate());
                            }
                                
                            {
                                String test;
                                System.out.println("Nhập phương thức đặt hàng: ");
                                sc.nextLine();
                                while (true) {
                                    test = sc.nextLine();
                                    if (test.isBlank() || test.length() > 20) {   //nếu như xâu test rỗng hoặc chứa toàn khoảng trắng, NHẬP LẠI ĐEEEEEEEE!!!!
                                        System.out.println("Không hợp lệ. Nhập lại: ");
                                    } else {
                                        id.setPaymentMethod(test);
                                        break;
                                    }
                                }
                                payment[i].setPaymentMethod(id.getPaymentMethod());
                            }
                                
                            {
                                String test;
                                System.out.println("Nhập trạng thái của đơn hàng (0 hoặc 1) (0: chưa xử lý, 1: đã xử lý): ");
                                sc.nextLine();
                                while (true) {
                                    test = sc.nextLine();
                                    if (test.isBlank()) {   //nếu như xâu test rỗng hoặc chứa toàn khoảng trắng, NHẬP LẠI ĐEEEEEEEE!!!!
                                        System.out.println("Trạng thái không được để trống. Nhập lại: ");
                                    } else {
                                        if (test.compareTo("0") == 0 || test.compareTo("1") == 0) {
                                            id.setStatus(test);
                                            break;
                                        }
                                        else {
                                            System.out.println("Trạng thái không hợp lệ. Nhập lại: ");
                                        }
                                    }
                                }
                                payment[i].setStatus(id.getStatus());
                            } 

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
            String ID_Payment;

            while (true) {
				ID_Payment = sc.nextLine();
				if (ID_Payment.isBlank()) {   //nếu như xâu test rỗng hoặc chứa toàn khoảng trắng, NHẬP LẠI ĐEEEEEEEE!!!!
						System.out.println("Mã thanh toán không được để trống. Nhập lại: ");
				} else {
						break;
				}
			}

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
        String find = null;
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
        switch (index) {
            case 1 -> {
                sc.nextLine();
                while (true) {
                    find = sc.nextLine();
                    if (find.isBlank() || (!Validation.isValidIDpayment(find))) {   //nếu như xâu find rỗng hoặc chứa toàn khoảng trắng, NHẬP LẠI ĐEEEEEEEE!!!!
                            System.out.println("Mã thanh toán không hợp lệ. Nhập lại: ");
                    } else {
                        break;
                    }
                }
                break;
            }

            case 2 -> {
                sc.nextLine();
                while (true) {
                    find = sc.nextLine();
                    if (find.isBlank() || (!Validation.isValidIDcustomer(find))) {   //nếu như xâu find rỗng hoặc chứa toàn khoảng trắng, NHẬP LẠI ĐEEEEEEEE!!!!
                            System.out.println("ID thanh toán không hợp lệ. Nhập lại: ");
                    } else {
                        break;
                    }
                }
                break;
            }
                
            case 3 -> {
                sc.nextLine();
                while (true) {
                    find = sc.nextLine();
                    if (find.isBlank() || (!Validation.isValidIDreceipt(find))) {   //nếu như xâu find rỗng hoặc chứa toàn khoảng trắng, NHẬP LẠI ĐEEEEEEEE!!!!
                            System.out.println("ID thanh toán không hợp lệ. Nhập lại: ");
                    } else {
                        break;
                    }
                }
                break;
            }            
            case 4 -> {
                sc.nextLine();
                while (sc.hasNextInt()) {
                    find = sc.nextLine();
                    break;
                }
                break;
            }
                
            case 5 -> {
                sc.nextLine();
                while (true) {
                    find = sc.nextLine();
                    if (find.isBlank() || find.length() != 10) {
                        System.out.println("Ngày không hợp lệ. Nhập lại: ");
                    } else {
                        if (Validation.isValidDate(find)) {
                            break;
                        } else {
                            System.out.println("Ngày không hợp lệ. Nhập lại: ");
                        }
                    }
                }
                break;
            }
                
            case 6 -> {
                sc.nextLine();
                while (true) {
                    find = sc.nextLine();
                    if (find.isBlank() || find.length() > 20) {   //nếu như xâu find rỗng hoặc chứa toàn khoảng trắng, NHẬP LẠI ĐEEEEEEEE!!!!
                        System.out.println("Không hợp lệ. Nhập lại: ");
                    } else {
                        break;
                    }
                }
                break;
            }
                
            case 7 -> {
                sc.nextLine();
                while (true) {
                    find = sc.nextLine();
                    if (find.isBlank()) {   //nếu như xâu find rỗng hoặc chứa toàn khoảng trắng, NHẬP LẠI ĐEEEEEEEE!!!!
                        System.out.println("Trạng thái không được để trống. Nhập lại: ");
                    } else {
                        if (find.compareTo("0") == 0 || find.compareTo("1") == 0) {
                            break;
                        }
                        else {
                            System.out.println("Trạng thái không hợp lệ. Nhập lại: ");
                        }
                    }
                }
                break;
            } 
        }

        System.out.println("\t\t\t\t\t\t\t\t +----DANH SÁCH THANH TOÁN----+");
        OutputHeader();

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


    public void OutputHeader() {
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


    public void waitConsole() {
        System.out.println("\t\t\t\t\t\t\t\t - Ấn Enter để tiếp tục");
    }
}
