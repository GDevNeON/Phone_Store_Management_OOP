package Controller;

import Entity.ChiTietPhieuNhapHang;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class QuanLyChiTietPhieuNhapHang extends ChiTietPhieuNhapHang {
    private ChiTietPhieuNhapHang[] dsct;
    public static Scanner sc = new Scanner(System.in);

    public QuanLyChiTietPhieuNhapHang() throws FileNotFoundException {
        super();
        getListChiTietPhieuNhapHang();
    }

    public ChiTietPhieuNhapHang[] getListChiTietPhieuNhapHang() {
        String[] result = new String[0];
        try {
            result = Stream.read("Database/ChiTietPhieuNhap.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        dsct = new ChiTietPhieuNhapHang[result.length];
        for (int i = 0; i < result.length; i++) {
            String[] row = result[i].split(";");
            dsct[i] = new ChiTietPhieuNhapHang(row[0], row[1], Integer.parseInt(row[2]), Integer.parseInt(row[3]));
        }
        return dsct;
    }

    public void waitConsole() {
        System.out.println("\t\t\t\t\t\t\t\t - Ấn Enter để tiếp tục");
        sc.nextLine();
    }

    public void Read() {
        System.out.println("\t\t\t\t\t\t\t\t +-----DANH SÁCH CHI TIẾT PHIẾU NHẬP HÀNG-----+");
        String header = String.format("| %-15s | %-15s | %-10s | %-25s |", "ID Phiếu nhập", "ID Sản phẩm", "Số lượng",
                "Giá");
        System.out.format("+-----------------+-----------------+------------+---------------------------+%n");
        System.out.println(header);
        System.out.format("+-----------------+-----------------+------------+---------------------------+%n");

        getListChiTietPhieuNhapHang();

        for (ChiTietPhieuNhapHang chiTietPhieu : dsct) {
            String read = String.format("| %-15s | %-15s | %-10s | %-25s |", chiTietPhieu.getID_PhieuNhap(),
                    chiTietPhieu.getID_Product(), chiTietPhieu.getAmount(), chiTietPhieu.getPrice());
            System.out.println(read);
        }
        System.out.format("+-----------------+-----------------+------------+---------------------------+%n");
        waitConsole();
    }

    public void Create() {
        System.out.println("\t\t\t\t\t\t\t\t +-----NHẬP THÔNG TIN CHI TIẾT PHIẾU NHẬP-----+");
        System.out.println("Nhập mã phiếu nhập: ");
        setID_PhieuNhap(sc.nextLine());
        int check = 0;
        for (ChiTietPhieuNhapHang chiTietPhieu : dsct) {
            if (getID_PhieuNhap().equals(chiTietPhieu.getID_PhieuNhap())) {
                check = 1;
                break;
            }
        }

        if (check == 1) {
            System.out.println("\t\t\t\t\t\t\t\t +-----MÃ PHIẾU NHẬP BỊ TRÙNG-----+");
            return;
        }

        System.out.println("Nhập mã sản phẩm: ");
        setID_Product(sc.nextLine());

        System.out.println("Nhập số lượng: ");
        setAmount(sc.nextInt());

        System.out.println("Nhập giá: ");
        setPrice(sc.nextInt());

        try {
            String sc = getID_PhieuNhap() + ";" + getID_Product() + ";" + getAmount() + ";" + getPrice();
            Stream.addOneLine("Database/ChiTietPhieuNhap.txt", sc);
            System.out.println("\t\t\t\t\t\t\t\t +-----NHẬP CHI TIẾT PHIẾU NHẬP THÀNH CÔNG-----+");
            waitConsole();
        } catch (IOException e) {
            e.printStackTrace();
        }
        getListChiTietPhieuNhapHang();
    }

    public void Update() {
        try {
            System.out.println("\t\t\t\t\t\t\t\t +-----CHỈNH SỬA CHI TIẾT PHIẾU NHẬP-----+");
            System.out.print("Nhập mã phiếu nhập cần chỉnh sửa: ");
            String ID_PhieuNhap = sc.nextLine();
            ChiTietPhieuNhapHang ctpnh = null;

            for (ChiTietPhieuNhapHang chiTietPhieu : dsct) {
                if (chiTietPhieu.getID_PhieuNhap().equals(ID_PhieuNhap)) {
                    ctpnh = chiTietPhieu;
                    break;
                }
            }

            if (ctpnh == null) {
                System.out.println("\t\t\t\t\t\t\t\t +-----MÃ PHIẾU NHẬP KHÔNG TỒN TẠI-----+");
                return;
            }

            System.out.println("\t\t\t\t\t\t\t\t +-----THÔNG TIN CHI TIẾT PHIẾU NHẬP TRƯỚC KHI CHỈNH SỬA-----+");
            String header = String.format("| %-15s | %-15s | %-10s | %-25s |", "ID Phiếu nhập", "ID Sản phẩm", "Số lượng",
                    "Giá");
            System.out.format("+-----------------+-----------------+------------+---------------------------+%n");
            System.out.println(header);
            System.out.format("+-----------------+-----------------+------------+---------------------------+%n");
            String row = String.format("| %-15s | %-15s | %-10s | %-25s |", ctpnh.getID_PhieuNhap(), ctpnh.getID_Product(),
                    ctpnh.getAmount(), ctpnh.getPrice());
            System.out.println(row);
            System.out.format("+-----------------+-----------------+------------+---------------------------+%n");

            String[] data = new String[dsct.length];

            System.out.println("\t\t\t\t\t\t\t\t +-----SỬA THÔNG TIN CHI TIẾT PHIẾU-----+");
            System.out.println("\t\t\t\t\t\t\t\t |0.Thoát                               |");
            System.out.println("\t\t\t\t\t\t\t\t +--------------------------------------+");
            System.out.println("\t\t\t\t\t\t\t\t |1.Sửa ID chi tiết phiếu               |");
            System.out.println("\t\t\t\t\t\t\t\t |2.Sửa ID sản phẩm                     |");
            System.out.println("\t\t\t\t\t\t\t\t |3.Sửa số lượng sản phẩm               |");
            System.out.println("\t\t\t\t\t\t\t\t |4.Sửa giá tiền                        |");
            System.out.println("\t\t\t\t\t\t\t\t |5.Sửa tất cả thông tin                |");
            System.out.println("\t\t\t\t\t\t\t\t +--------------------------------------+");
            System.out.print("\t\t\t\t\t\t\t\t Mời bạn nhập lựa chọn: ");
            int choose = sc.nextInt();
            sc.nextLine();
            if (choose == 0) {
            }
            else {
                switch (choose) {
                    case 1 -> {
                        for (int i = 0; i < dsct.length; i++) {
                            if (dsct[i].getID_PhieuNhap().equals(ID_PhieuNhap)) {
                                System.out.print("Nhập ID chi tiết phiếu nhập: ");
                                setID_PhieuNhap(sc.nextLine());
                                int check = 0;
                                for (ChiTietPhieuNhapHang chiTietPhieu : dsct) {
                                    if (getID_PhieuNhap().equals(chiTietPhieu.getID_PhieuNhap())) {
                                        check = 1;
                                        break;
                                    }
                                }

                                if (check == 1) {
                                    System.out.println("\t\t\t\t\t\t\t\t MÃ TÀI KHOẢN BỊ TRÙNG!");
                                    return;
                                }
                                dsct[i].setID_PhieuNhap(getID_PhieuNhap());
                            }
                            data[i] = dsct[i].getID_PhieuNhap() + ";" + dsct[i].getID_Product() + ";" + dsct[i].getAmount() + ";" + dsct[i].getPrice();
                        }
                        try {
                            Stream.addAll("Database/ChiTietPhieuNhap.txt", data);
                            System.out.println("\t\t\t\t\t\t\t\t +-----CHỈNH SỬA CHI TIẾT PHIẾU NHẬP THÀNH CÔNG-----+");
                            waitConsole();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    case 2 -> {
                        for (int i = 0; i < dsct.length; i++) {
                            if (dsct[i].getID_PhieuNhap().equals(ID_PhieuNhap)) {
                                System.out.print("Nhập ID sản phẩm: ");
                                setID_Product(sc.nextLine());
                                dsct[i].setID_Product(getID_Product());
                            }
                            data[i] = dsct[i].getID_PhieuNhap() + ";" + dsct[i].getID_Product() + ";" + dsct[i].getAmount() + ";" + dsct[i].getPrice();
                        }
                        try {
                            Stream.addAll("Database/ChiTietPhieuNhap.txt", data);
                            System.out.println("\t\t\t\t\t\t\t\t +-----CHỈNH SỬA CHI TIẾT PHIẾU NHẬP THÀNH CÔNG-----+");
                            waitConsole();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    case 3 -> {
                        for (int i = 0; i < dsct.length; i++) {
                            if (dsct[i].getID_PhieuNhap().equals(ID_PhieuNhap)) {
                                System.out.print("Nhập số lượng sản phẩm: ");
                                setAmount(sc.nextInt());
                                dsct[i].setAmount(getAmount());
                            }
                            data[i] = dsct[i].getID_PhieuNhap() + ";" + dsct[i].getID_Product() + ";" + dsct[i].getAmount() + ";" + dsct[i].getPrice();
                        }
                        try {
                            Stream.addAll("Database/ChiTietPhieuNhap.txt", data);
                            System.out.println("\t\t\t\t\t\t\t\t +-----CHỈNH SỬA CHI TIẾT PHIẾU NHẬP THÀNH CÔNG-----+");
                            waitConsole();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    case 4 -> {
                        for (int i = 0; i < dsct.length; i++) {
                            if (dsct[i].getID_PhieuNhap().equals(ID_PhieuNhap)) {
                                System.out.print("Nhập giá tiền: ");
                                setPrice(sc.nextInt());
                                dsct[i].setPrice(getPrice());
                            }
                            data[i] = dsct[i].getID_PhieuNhap() + ";" + dsct[i].getID_Product() + ";" + dsct[i].getAmount() + ";" + dsct[i].getPrice();
                        }
                        try {
                            Stream.addAll("Database/ChiTietPhieuNhap.txt", data);
                            System.out.println("\t\t\t\t\t\t\t\t +-----CHỈNH SỬA CHI TIẾT PHIẾU NHẬP THÀNH CÔNG-----+");
                            waitConsole();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    case 5 -> {
                        for (int i = 0; i < dsct.length; i++) {
                            if (dsct[i].getID_PhieuNhap().equals(ID_PhieuNhap)) {
                                System.out.print("Nhập ID chi tiết phiếu nhập: ");
                                setID_PhieuNhap(sc.nextLine());
                                int check = 0;
                                for (ChiTietPhieuNhapHang chiTietPhieu : dsct) {
                                    if (getID_PhieuNhap().equals(chiTietPhieu.getID_PhieuNhap())) {
                                        check = 1;
                                        break;
                                    }
                                }

                                if (check == 1) {
                                    System.out.println("\t\t\t\t\t\t\t\t MÃ TÀI KHOẢN BỊ TRÙNG!");
                                    return;
                                }
                                System.out.print("Nhập ID sản phẩm: ");
                                setID_Product(sc.nextLine());

                                System.out.print("Nhập số lượng sản phẩm: ");
                                setAmount(sc.nextInt());

                                System.out.print("Nhập giá: ");
                                setPrice(sc.nextInt());

                                dsct[i].setID_PhieuNhap(getID_PhieuNhap());
                                dsct[i].setID_Product(getID_Product());
                                dsct[i].setAmount(getAmount());
                                dsct[i].setPrice(getPrice());
                            }
                            data[i] = dsct[i].getID_PhieuNhap() + ";" + dsct[i].getID_Product() + ";" + dsct[i].getAmount() + ";" + dsct[i].getPrice();
                        }
                        try {
                            Stream.addAll("Database/ChiTietPhieuNhap.txt", data);
                            System.out.println("\t\t\t\t\t\t\t\t +-----CHỈNH SỬA CHI TIẾT PHIẾU NHẬP THÀNH CÔNG-----+");
                            waitConsole();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    default -> System.out.println("\t\t\t\t\t\t\t\t +-----LỰA CHỌN KHÔNG HỢP LỆ-----+");
                }
            }
        } catch (InputMismatchException ei) {
            System.out.println("\t\t\t\t\t\t\t\t +-----GIÁ TRỊ KHÔNG HỢP LỆ. VUI LÒNG NHẬP LẠI-----+");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void Delete() {
        try {
            System.out.println("\t\t\t\t\t\t\t\t +-----XÓA CHI TIẾT PHIẾU NHẬP HÀNG-----+");
            System.out.print("Nhập mã chi tiết phiếu nhập cần xóa: ");
            String ID_PhieuNhap = sc.nextLine();

            ChiTietPhieuNhapHang xoachiTiet = null;
            for (ChiTietPhieuNhapHang chiTietPhieu : dsct) {
                if (chiTietPhieu.getID_PhieuNhap().equals(ID_PhieuNhap)) {
                    xoachiTiet = chiTietPhieu;
                    break;
                }
            }

            if (xoachiTiet == null) {
                System.out.println("\t\t\t\t\t\t\t\t +-----MÃ PHIẾU NHẬP KHÔNG TỒN TẠI-----+");
                return;
            }

            for (int i = 0; i < dsct.length; i++) {
                if (ID_PhieuNhap.equals(dsct[i].getID_PhieuNhap())) {
                    dsct = deleteChiTietPhieuNhapHang(dsct, i);
                    break;
                }
            }
            String[] data = new String[dsct.length];
            for (int i = 0; i < dsct.length; i++) {
                data[i] = dsct[i].getID_PhieuNhap() + ";" + dsct[i].getID_Product() + ";" + dsct[i].getAmount() + ";"
                        + dsct[i].getPrice();
            }

            try {
                Stream.addAll("Database/ChiTietPhieuNhap.txt", data);
                System.out.println("\t\t\t\t\t\t\t\t +-----XÓA CHI TIẾT HÓA ĐƠN THÀNH CÔNG-----+");
                waitConsole();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (InputMismatchException ei) {
            System.out.println("\t\t\t\t\t\t\t\t +-----GIÁ TRỊ KHÔNG HỢP LỆ. VUI LÒNG NHẬP LẠI-----+");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public ChiTietPhieuNhapHang[] deleteChiTietPhieuNhapHang(ChiTietPhieuNhapHang[] dsct, int index) {
        ChiTietPhieuNhapHang[] newCT = new ChiTietPhieuNhapHang[dsct.length - 1];
        for (int i = 0, j = 0; i < dsct.length; i++) {
            if (i != index) {
                newCT[j++] = dsct[i];
            }
        }
        return newCT;
    }

    public ChiTietPhieuNhapHang[] addChiTietPhieuNhapHang(ChiTietPhieuNhapHang[] dsct, ChiTietPhieuNhapHang pnh) {
        dsct = Arrays.copyOf(dsct, dsct.length + 1);
        dsct[dsct.length - 1] = pnh;
        return dsct;
    }

    public void Search_byCategory() {
        ChiTietPhieuNhapHang[] result = new ChiTietPhieuNhapHang[0];
        System.out.println("\t\t\t\t\t\t\t\t +--------NHẬP MỤC LỤC CẨN TÌM KIẾM--------+");
        System.out.println("\t\t\t\t\t\t\t\t |0.Thoát                                  |");
        System.out.println("\t\t\t\t\t\t\t\t +-----------------------------------------+");
        System.out.println("\t\t\t\t\t\t\t\t |1.ID Phiếu nhập hàng                     |");
        System.out.println("\t\t\t\t\t\t\t\t |2.ID Sản phẩm                            |");
        System.out.println("\t\t\t\t\t\t\t\t |3.Số lượng                               |");
        System.out.println("\t\t\t\t\t\t\t\t |4.Giá tiền                               |");
        System.out.println("\t\t\t\t\t\t\t\t +-----------------------------------------+");
        System.out.print("\t\t\t\t\t\t\t - Mời Bạn Nhập Lựa Chọn: ");
        int choose = sc.nextInt();
        if (choose == 0)
            return;
        else {
            switch (choose) {
                case 1 -> {
                    sc.nextLine();
                    System.out.print("Nhập mã phiếu nhập hàng: ");
                    String id = sc.nextLine();
                    for (ChiTietPhieuNhapHang chiTietPhieu : dsct) {
                        if (chiTietPhieu.getID_PhieuNhap().toLowerCase().contains(id.toLowerCase())) {
                            result = addChiTietPhieuNhapHang(result, chiTietPhieu);
                        }
                    }
                }
                case 2 -> {
                    sc.nextLine();
                    System.out.print("Nhập mã sản phẩm: ");
                    String id = sc.nextLine();
                    for (ChiTietPhieuNhapHang chiTietPhieu : dsct) {
                        if (chiTietPhieu.getID_Product().toLowerCase().contains(id.toLowerCase())) {
                            result = addChiTietPhieuNhapHang(result, chiTietPhieu);
                        }
                    }
                }
                case 3 -> {
                    sc.nextLine();
                    System.out.print("Nhập số lượng: ");
                    Integer amount = sc.nextInt();
                    for (ChiTietPhieuNhapHang chiTietPhieu : dsct) {
                        if (amount.equals(getAmount())) {
                            result = addChiTietPhieuNhapHang(result, chiTietPhieu);
                        }
                    }
                }
                case 4 -> {
                    sc.nextLine();
                    System.out.print("Nhập giá tiền: ");
                    Integer price = sc.nextInt();
                    for (ChiTietPhieuNhapHang chiTietPhieu : dsct) {
                        if (price.equals(getPrice())) {
                            result = addChiTietPhieuNhapHang(result, chiTietPhieu);
                        }
                    }
                }
                default -> {
                    System.out.println("Lựa chọn không hợp lệ!");
                }
            }
        }
        String header = String.format("| %-15s | %-15s | %-10s | %-25s |", "ID Phiếu nhập", "ID Sản phẩm", "Số lượng", "Giá");
        System.out.format("+-----------------+-----------------+------------+---------------------------+%n");
        System.out.println(header);
        System.out.format("+-----------------+-----------------+------------+---------------------------+%n");

        for (ChiTietPhieuNhapHang chiTietPhieu : result) {
            String row = String.format("| %-15s | %-15s | %-10s | %-25s |", chiTietPhieu.getID_PhieuNhap(), chiTietPhieu.getID_Product(), chiTietPhieu.getAmount(), chiTietPhieu.getPrice());
            System.out.println(row);
        }
        System.out.format("+-----------------+-----------------+------------+---------------------------+%n");
        waitConsole();
    }
}
