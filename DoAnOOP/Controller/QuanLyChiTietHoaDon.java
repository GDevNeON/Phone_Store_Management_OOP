package Controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import Entity.ChiTietHoaDon;

public class QuanLyChiTietHoaDon extends ChiTietHoaDon{
    private ChiTietHoaDon[] rd;
    static Scanner input = new Scanner(System.in);

    public QuanLyChiTietHoaDon() throws FileNotFoundException {
        super();
        getListReceiptDetail();
    }

    public ChiTietHoaDon[] getListReceiptDetail() throws FileNotFoundException{
        String[] result = Stream.read("Database/ChiTietHoaDon.txt");
        rd = new ChiTietHoaDon[result.length];
        for(int i = 0; i < result.length; i++) {
            String[] row = result[i].split(";");
            rd[i] = new ChiTietHoaDon(row[0], row[1], Integer.parseInt(row[2]), Integer.parseInt(row[3]));
        }
        return rd;
    }

    public void Read() throws FileNotFoundException {
        System.out.println("Danh sách chi tiết hóa đơn:");
        String header = String.format("| %-10s | %-13s | %-10s | %-10s | ", "ID Hóa Đơn", "ID Sản Phẩm", "Số lượng", "Giá");
        System.out.format("+------------+---------------+------------+------------+%n");
        System.out.println(header);
        System.out.format("+------------+---------------+------------+------------+%n");

        getListReceiptDetail();
        for(int i = 0; i < rd.length; i++) {
            String read = String.format("| %-10s | %-13s | %-10s | %-10s |", rd[i].getID_Receipt(), rd[i].getID_Product(), rd[i].getAmount(), rd[i].getPrice());
            System.out.println(read);
        }
        System.out.format("+------------+---------------+------------+------------+%n");
        waitConsole();
    }

    public void Create() {
        System.out.print("Nhập mã hóa đơn: ");
        setID_Receipt(input.nextLine());

        System.out.print("Nhập mã Sản Phẩm: ");
        setID_Product(input.nextLine());

        System.out.print("Nhập mã Số lượng: ");
        setAmount(Integer.parseInt(input.next()));

        System.out.print("Nhập giá:");
        setPrice(Integer.parseInt(input.next()));

        try {
            String input = getID_Receipt() + ";" + getID_Product() + ";" + getAmount() +  ";" + getPrice();
            Stream.addOneLine("Database/ChiTietHoaDon.txt", input);
            System.out.println("Nhập chi tiết hóa đơn thành công");
            waitConsole();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Update() {
        System.out.print("Nhập ID hóa đơn cần chỉnh sửa: ");
        String ID_Receipt = input.nextLine();

        System.out.println("Nhập ID sản phẩm:");
        String ID_Product = input.nextLine();

        ChiTietHoaDon cthd = null;

        for (ChiTietHoaDon receipt : rd) {

            if (ID_Receipt.equals(receipt.getID_Receipt()) && ID_Product.equals(receipt.getID_Product())) {
                cthd = receipt;
                break;
            }
        }

        if(cthd == null) {
            System.out.println("ID hóa đơn cho sản phẩm này không tồn tại. Xin vui lòng nhập lại!");
            return;
        }

        System.out.println("Thông tin hóa đơn cho sản phẩm có id " + cthd.getID_Product() + " là:");
        String header = String.format("| %-10s | %-12s | %-10s | %-10s | ", "ID Hóa Đơn", "ID Sản Phẩm", "Số lượng", "Giá");
        System.out.format("+------------+--------------+------------+------------+%n");
        System.out.println(header);
        System.out.format("+------------+--------------+------------+------------+%n");
        String row = String.format("| %-10s | %-12s | %-10s | %-10s |", cthd.getID_Receipt(), cthd.getID_Product(), cthd.getAmount(), cthd.getPrice());
        System.out.println(row);
        System.out.format("+------------+--------------+------------+------------+%n");
        System.out.println("Enter để tiếp tục:");
        String[] data = new String[rd.length];
        input.nextLine();
        for (int i = 0; i < rd.length; i++) {
            if (ID_Receipt.equals(rd[i].getID_Receipt()) && ID_Product.equals(rd[i].getID_Product())) {
                System.out.println("Nhập lại thông tin chi tiết hóa đơn:");

                System.out.print("Nhập số lượng: ");
                setAmount(input.nextInt());

                System.out.print("Nhập giá: ");
                setPrice(input.nextInt());

                rd[i].setAmount(getAmount());
                rd[i].setPrice(getPrice());

            }
            data[i] =  rd[i].getID_Receipt() + ";" + rd[i].getID_Product() + ";" + rd[i].getAmount() + ";" + rd[i].getPrice();

        }
        try {
            Stream.addAll("Database/ChiTietHoaDon.txt", data);
            System.out.println("Sửa thông tin chi tiết hoá đơn thành công");
            // waitConsole();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void Delete(String id) {

        for (int i = 0; i < rd.length; i++) {
            if (id.equals(rd[i].getID_Receipt())) {
                rd = deleteReceiptDetail(rd, i);
                i -= 1;
            }
        }

        String[] data = new String[rd.length];
        for (int i = 0; i < rd.length; i++) {
            data[i] =  rd[i].getID_Receipt() + ";" + rd[i].getID_Product() + ";"  + rd[i].getAmount() + ";" + rd[i].getPrice();
        }
        try {
            Stream.addAll("Database/ChiTietHoaDon.txt", data);
            System.out.println("Xóa chi tiết hoá đơn thành công");
            waitConsole();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ChiTietHoaDon[] deleteReceiptDetail(ChiTietHoaDon[] rd, int index) {
        ChiTietHoaDon[] newRd = new ChiTietHoaDon[rd.length - 1];
        for (int i = 0, j = 0; i < rd.length; i++) {
            if (i != index) {
                newRd[j++] = rd[i];
            }
        }
        return newRd;
    }

    public ChiTietHoaDon[] addReceiptDetail(ChiTietHoaDon[] rd, ChiTietHoaDon receipt) {
        rd = Arrays.copyOf(rd, rd.length + 1);
        rd[rd.length -1] = receipt;
        return rd;
    }

    public void Search_byCategory() {
        ChiTietHoaDon[] result = new ChiTietHoaDon[0];
        System.out.println("Nhập mục lục cần tìm kiếm: ");
        System.out.println("1.Mã hóa đơn");
        System.out.println("2.Mã Sản Phẩm");
        System.out.println("Mời nhập:");
        int choose = input.nextInt();
        input.nextLine();
        switch (choose) {
            case 1 -> {
                System.out.print("Nhập ID hóa đơn: ");
                String id = input.nextLine();
                for(int i = 0; i < rd.length; i++) {
                    if (id.equals(rd[i].getID_Receipt())) {
                        result = addReceiptDetail(result, rd[i]);
                    }
                }
            }
            case 2 -> {
                input.nextLine();
                System.out.print("Nhập Sản Phẩm: ");
                String id = input.nextLine();
                for(int i = 0; i < rd.length; i++) {
                    if (id.equals(rd[i].getID_Product())) {
                        result = addReceiptDetail(result, rd[i]);
                    }
                }
            }
        }

        System.out.println("Danh sách chi tiết hóa hàng tìm được:");
        String header = String.format("| %-10s | %-10s | %-10s | %-10s | ", "ID Hóa Đơn", "ID Sản Phẩm", "Số lượng", "Giá");
        System.out.format("+------------+-------------+------------+------------+%n");
        System.out.println(header);
        System.out.format("+------------+-------------+------------+------------+%n");
        for (ChiTietHoaDon recepit : result) {
            String read = String.format("| %-10s | %-11s | %-10s | %-10s |", recepit.getID_Receipt(), recepit.getID_Product(), recepit.getAmount(), recepit.getPrice());
            System.out.println(read);
        }
        System.out.format("+------------+-------------+------------+------------+%n");
        waitConsole();
    }

    public void waitConsole() {
        System.out.println("Ấn Enter để tiếp tục");
        input.nextLine();
    }
}
