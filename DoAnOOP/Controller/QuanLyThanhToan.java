package Controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

import Entity.Payment;


public class QuanLyThanhToan extends Payment implements ControllerInterface {
    Scanner sc = new Scanner(System.in);
    Payment[] payment;


    public QuanLyThanhToan() throws FileNotFoundException {
        super();
        getListPayment();
    }


    //Lấy danh sách thanh toán từ file
    public Payment[] getListPayment() throws FileNotFoundException {
        String[] result = new String[0];
        try {
            result = Stream.read("Database/ThanhToan.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        payment = new Payment[result.length];
        for (int i = 0; i < result.length; i++) {
            String[] row = result[i].split(";");
            payment[i] = new Payment(row[0], row[1], row[2], row[3], LocalDate.parse(row[4]), row[5], row[6]);
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

        try {
            getListPayment();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (Payment p : payment) {
            String read = String.format("| %-11s | %-15s | %-10s | %-9s | %-20s | %-25s | %-15s |",
                    p.getPayment_ID(),
                    p.getCustomer_ID(),
                    p.getID_Receipt(),
                    p.getAmount(),
                    p.getPayment_Date(),
                    p.getPayment_Method(),
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

        System.out.println("Nhập mã thanh toán (tt_): ");
        setPayment_ID(sc.nextLine());
        int check = 0;
        for (Payment p : payment) {
            if (getPayment_ID().equals(p.getPayment_ID())) {
                check = 1;
                break;
            }
        }
        if (check == 1) {
            System.out.println("\t\t\t\t\t\t\t\t +----MÃ THANH TOÁN BỊ TRÙNG----+");
            return;
        }

        System.out.println("Nhập mã khách hàng: ");
        setCustomer_ID(sc.nextLine());
        check = 0;
        for (Payment p : payment) {
            if (getCustomer_ID().equals(p.getCustomer_ID())) {
                check = 1;
                break;
            }
        }
        if (check == 1) {
            System.out.println("\t\t\t\t\t\t\t\t +----MÃ KHÁCH HÀNG BỊ TRÙNG----+");
            return;
        }

        System.out.println("Nhập mã đơn hàng: ");
        setID_Receipt(sc.nextLine());
        check = 0;
        for (Payment p : payment) {
            if (getID_Receipt().equals(p.getID_Receipt())) {
                check = 1;
                break;
            }
        }
        if (check == 1) {
            System.out.println("\t\t\t\t\t\t\t\t +----MÃ ĐƠN HÀNG BỊ TRÙNG----+");
            return;
        }

        System.out.println("Nhập số lượng hàng: ");
        setAmount(sc.nextLine());

        System.out.println("Nhập ngày đặt hàng: ");
        setPayment_Date(LocalDate.parse(sc.nextLine()));

        System.out.println("Nhập phương thức đặt hàng: ");
        setPayment_Method(sc.nextLine());

        System.out.println("Nhập trạng thái của đơn hàng: ");
        setStatus(sc.nextLine());

        //cập nhật file
        updateList(0, payment);

        try {
            getListPayment();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void Update() {
        try {
            System.out.println("\t\t\t\t\t\t\t\t +----CẬP NHẬT THÔNG TIN THANH TOÁN----+");
            System.out.print("- Mời nhập ID thanh toán cần chỉnh sửa: ");
            String ID_Payment = sc.nextLine();
            Payment id = null;

            for (Payment p : payment) {
                if (p.getPayment_ID().equals(ID_Payment)) {
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
                    id.getPayment_ID(),
                    id.getCustomer_ID(),
                    id.getID_Receipt(),
                    id.getAmount(),
                    id.getPayment_Date(),
                    id.getPayment_Method(),
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
                    if (payment[i].getPayment_ID().equals(ID_Payment)) {
                        System.out.println("Nhập thông tin thanh toán:");

                        switch (index) {
                            case 0:
                                return;
                            case 1:
                                System.out.println("Nhập mã thanh toán (tt_): ");
                                sc.nextLine();
                                setPayment_ID(sc.nextLine());
                                int check = 0;
                                for (Payment p : payment) {
                                    if (getPayment_ID().equals(p.getPayment_ID())) {
                                        check = 1;
                                        break;
                                    }
                                }
                                if (check == 1) {
                                    System.out.println("\t\t\t\t\t\t\t\t +----MÃ THANH TOÁN BỊ TRÙNG----+");
                                    return;
                                }
                                payment[i].setPayment_ID(getPayment_ID());
                                break;
                            case 2:
                                System.out.println("Nhập mã khách hàng: ");
                                sc.nextLine();
                                setCustomer_ID(sc.nextLine());
                                check = 0;
                                for (Payment p : payment) {
                                    if (getCustomer_ID().equals(p.getCustomer_ID())) {
                                        check = 1;
                                        break;
                                    }
                                }
                                if (check == 1) {
                                    System.out.println("\t\t\t\t\t\t\t\t +----MÃ KHÁCH HÀNG BỊ TRÙNG----+");
                                    return;
                                }
                                payment[i].setCustomer_ID(getCustomer_ID());
                                break;
                            case 3:
                                System.out.println("Nhập mã đơn hàng: ");
                                sc.nextLine();
                                setID_Receipt(sc.nextLine());
                                check = 0;
                                for (Payment p : payment) {
                                    if (getID_Receipt().equals(p.getID_Receipt())) {
                                        check = 1;
                                        break;
                                    }
                                }
                                if (check == 1) {
                                    System.out.println("\t\t\t\t\t\t\t\t +----MÃ ĐƠN HÀNG BỊ TRÙNG----+");
                                    return;
                                }
                                payment[i].setID_Receipt(getID_Receipt());
                                break;
                            case 4:
                                System.out.println("Nhập số lượng hàng: ");
                                sc.nextLine();
                                setAmount(sc.nextLine());
                                payment[i].setAmount(getAmount());
                                break;
                            case 5:
                                System.out.println("Nhập ngày đặt hàng: ");
                                sc.nextLine();
                                setPayment_Date(LocalDate.parse(sc.nextLine()));
                                payment[i].setPayment_Date(getPayment_Date());
                                break;
                            case 6:
                                System.out.println("Nhập phương thức đặt hàng: ");
                                sc.nextLine();
                                setPayment_Method(sc.nextLine());
                                payment[i].setPayment_Method(getPayment_Method());
                                break;
                            case 7:
                                System.out.println("Nhập trạng thái của đơn hàng: ");
                                sc.nextLine();
                                setStatus(sc.nextLine());
                                payment[i].setStatus(getStatus());
                                break;
                        }
                    }
                    data[i] = payment[i].getPayment_ID() + ";" +
                            payment[i].getCustomer_ID() + ";" +
                            payment[i].getID_Receipt() + ";" +
                            payment[i].getAmount() + ";" +
                            payment[i].getPayment_Date() + ";" +
                            payment[i].getPayment_Method() + ";" +
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
                    if (payment[i].getPayment_ID().equals(ID_Payment)) {
                        System.out.println("Nhập thông tin thanh toán:");

                        System.out.println("Nhập mã thanh toán (tt_): ");
                        sc.nextLine();
                        setPayment_ID(sc.nextLine());
                        int check = 0;
                        for (Payment p : payment) {
                            if (getPayment_ID().equals(p.getPayment_ID())) {
                                check = 1;
                                break;
                            }
                        }
                        if (check == 1) {
                            System.out.println("\t\t\t\t\t\t\t\t +----MÃ THANH TOÁN BỊ TRÙNG----+");
                            return;
                        }

                        System.out.println("Nhập mã khách hàng: ");
                        setCustomer_ID(sc.nextLine());
                        check = 0;
                        for (Payment p : payment) {
                            if (getCustomer_ID().equals(p.getCustomer_ID())) {
                                check = 1;
                                break;
                            }
                        }
                        if (check == 1) {
                            System.out.println("\t\t\t\t\t\t\t\t +----MÃ KHÁCH HÀNG BỊ TRÙNG----+");
                            return;
                        }

                        System.out.println("Nhập mã đơn hàng: ");
                        setID_Receipt(sc.nextLine());
                        check = 0;
                        for (Payment p : payment) {
                            if (getID_Receipt().equals(p.getID_Receipt())) {
                                check = 1;
                                break;
                            }
                        }
                        if (check == 1) {
                            System.out.println("\t\t\t\t\t\t\t\t +----MÃ ĐƠN HÀNG BỊ TRÙNG----+");
                            return;
                        }

                        System.out.println("Nhập số lượng hàng: ");
                        setAmount(sc.nextLine());

                        System.out.println("Nhập ngày đặt hàng: ");
                        setPayment_Date(LocalDate.parse(sc.nextLine()));

                        System.out.println("Nhập phương thức đặt hàng: ");
                        setPayment_Method(sc.nextLine());

                        System.out.println("Nhập trạng thái của đơn hàng: ");
                        setStatus(sc.nextLine());

                        payment[i].setPayment_ID(getPayment_ID());
                        payment[i].setCustomer_ID(getCustomer_ID());
                        payment[i].setID_Receipt(getID_Receipt());
                        payment[i].setAmount(getAmount());
                        payment[i].setPayment_Date(getPayment_Date());
                        payment[i].setPayment_Method(getPayment_Method());
                        payment[i].setStatus(getStatus());
                    }
                    data[i] = payment[i].getPayment_ID() + ";" +
                            payment[i].getCustomer_ID() + ";" +
                            payment[i].getID_Receipt() + ";" +
                            payment[i].getAmount() + ";" +
                            payment[i].getPayment_Date() + ";" +
                            payment[i].getPayment_Method() + ";" +
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

            Payment id = null;
            for (Payment p : payment) {
                if (p.getPayment_ID().equals(ID_Payment)) {
                    id = p;
                    break;
                }
            }

            if (id == null) {
                System.out.println("\t\t\t\t\t\t\t\t +-----MÃ THANH TOÁN KHÔNG TỒN TẠI-----+");
                return;
            }

            for (int i = 0; i < payment.length; i++) {
                if (ID_Payment.equals(payment[i].getPayment_ID())) {
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
    public Payment[] deletePayment(Payment[] payment, int index) {
        Payment[] newCs = new Payment[payment.length - 1];
        for (int i = 0, j = 0; i < payment.length; i++) {
            if (i != index) {
                newCs[j++] = payment[i];
            }
        }
        return newCs;
    }


    // Thêm phần tử vào mảng
    public Payment[] addPayment(Payment[] payment, Payment sanpham) {
        payment = Arrays.copyOf(payment, payment.length + 1);
        payment[payment.length - 1] = sanpham;
        return payment;
    }


    @Override
    public void Search_byCategory() {
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
                    if (payment[i].getPayment_ID().equalsIgnoreCase(find))
                        OutputData(i);
                    break;
                case 2:
                    if (payment[i].getCustomer_ID().equalsIgnoreCase(find))
                        OutputData(i);
                    break;
                case 3:
                    if (payment[i].getID_Receipt().equalsIgnoreCase(find))
                        OutputData(i);
                    break;
                case 4:
                    if (payment[i].getAmount().equalsIgnoreCase(find))
                        OutputData(i);
                    break;
                case 5:
                    if (payment[i].getPayment_Date().equals(LocalDate.parse(find)))
                        OutputData(i);
                    break;
                case 6:
                    if (payment[i].getPayment_Method().equalsIgnoreCase(find))
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
                payment[i].getPayment_ID(),
                payment[i].getCustomer_ID(),
                payment[i].getID_Receipt(),
                payment[i].getAmount(),
                payment[i].getPayment_Date(),
                payment[i].getPayment_Method(),
                payment[i].getStatus());
        System.out.println(row);
    }


    public String[] stringToInputInFile(Payment[] payment) {
        String[] data = new String[payment.length];

        for (int i = 0; i < payment.length; i++) {
            data[i] = payment[i].getPayment_ID() + ";" +
                    payment[i].getCustomer_ID() + ";" +
                    payment[i].getID_Receipt() + ";" +
                    payment[i].getAmount() + ";" +
                    payment[i].getPayment_Date() + ";" +
                    payment[i].getPayment_Method() + ";" +
                    payment[i].getStatus();
        }

        return data;
    }


    public void updateList(int select, Payment[] payment) {
        switch (select) {
            case 0:
                try {
                    String inputStringData = getPayment_ID() + ";" +
                            getCustomer_ID() + ";" +
                            getID_Receipt() + ";" +
                            getAmount() + ";" +
                            getPayment_Date() + ";" +
                            getPayment_Method() + ";" +
                            getStatus();
                    Stream.addOneLine("Database/ThanhToan.txt", inputStringData);
                    System.out.println("\t\t\t\t\t\t\t\t +----NHẬP THÔNG TIN THANH TOÁN THÀNH CÔNG----+");
                    waitConsole();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case 1 :
                String[] data = stringToInputInFile(payment);

                try {
                    Stream.addAll("Database/ThanhToan.txt", data);
                    System.out.println("\t\t\t\t\t\t\t\t +----SỬA THÔNG TIN THANH TOÁN THÀNH CÔNG----+");
                    waitConsole();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }


    public int getListLength(Payment[] payment) {
        return payment.length;
    }


    public void waitConsole() {
        System.out.println("\t\t\t\t\t\t\t\t - Ấn Enter để tiếp tục");
        sc.nextLine();
    }
}
