package Controller;

import Entity.Customer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class QuanLyKhachHang extends Customer implements ControllerInterface {

    public static Scanner input = new Scanner(System.in);
    public Customer[] cs;

    public QuanLyKhachHang() throws FileNotFoundException {
        super();
        getListCustomer();
    }

    public Customer[] getListCustomer() {
        String[] result = new String[0];
        try {
            result = Stream.read("Database/KhachHang.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        cs = new Customer[result.length];
        for (int i = 0; i < result.length; i++) {
            String[] row = result[i].split(";");
            cs[i] = new Customer(row[0], row[1], Integer.parseInt(row[2]), row[3], row[4],row[5], row[6], row[7]);
        }
        return cs;
    }

    @Override
    public void Read() {
        System.out.println("\t\t\t\t\t\t\t\t +----DANH SÁCH KHÁCH HÀNG----+");
        String header = String.format("| %-5s | %-25s | %-4s | %-9s | %-30s | %-25s | %-15s | %-20s |", "ID", "Họ tên", "Tuổi", "Giới Tính", "Địa chỉ", "Email", "Số điện thoại", "Loại khách hàng");
        System.out.format("+-------+---------------------------+------+-----------+--------------------------------+---------------------------+-----------------+----------------------+%n");
        System.out.println(header);
        System.out.format("+-------+---------------------------+------+-----------+--------------------------------+---------------------------+-----------------+----------------------+%n");

        getListCustomer();
        for (Customer c : cs) {
            if (cs[0].getCustomer_ID().contains("kh")) {
                String read = String.format("| %-5s | %-25s | %-4s | %-9s | %-30s | %-25s | %-15s | %-20s |", c.getCustomer_ID(), c.getName(), c.getAge(), c.getGender(), c.getAddress(), c.getEmail(), c.getSDT(), c.getKindOfCustomer());
                System.out.println(read);
            }
        }
        System.out.format("+-------+---------------------------+------+-----------+--------------------------------+---------------------------+-----------------+----------------------+%n");
        waitConsole();
    }

    @Override
    public void Create() {
        boolean checkIDexist;
        do {
            checkIDexist = false;
            System.out.println("\t\t\t\t\t\t\t\t ----NHẬP THÔNG TIN KHÁCH HÀNG----");
            System.out.print("Nhập ID khách hàng: ");
            setCustomer_ID(input.nextLine());

            for (Customer customer : cs) {
                if (customer != null) {
                    if (getCustomer_ID().equals(customer.getCustomer_ID())) {
                        System.out.println("\t\t\t\t\t\t\t\t -MÃ KHÁCH HÀNG BỊ TRÙNG!");
                        checkIDexist = true;
                        break;

                    }
                }
            }
        }while (checkIDexist);
        super.AddThongTin();

        System.out.print("Nhập loại khách hàng: ");
        setKindOfCustomer(input.nextLine());

        try {
            String input = getCustomer_ID() + ";" + getName() + ";" + getAge() + ";" + getGender() + ";" + getAddress() + ";" + getEmail() + ";" + getSDT() + ";" + getKindOfCustomer();
            Stream.addOneLine("Database/KhachHang.txt", input); //link database here
            System.out.println("Nhập khách hàng thành công");
            waitConsole();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Update() {
        System.out.print("Nhập ID khách hàng cần chỉnh sửa: ");
        String ID_KhachHang = input.nextLine();
        Customer k_hang = null;

        for (Customer KhachHang : cs) {
            if (KhachHang.getCustomer_ID().equals(ID_KhachHang)) {
                k_hang = KhachHang;
                break;
            }
        }

        if (k_hang == null) {
            System.out.println("ID khách hàng không tồn tại!");
            return;
        }

        System.out.println("Thông tin khách hàng: ");
        System.out.println("\t\t\t\t\t\t\t\t +----DANH SÁCH KHÁCH HÀNG----+");
        String header = String.format("| %-5s | %-25s | %-4s | %-9s | %-30s | %-25s | %-15s | %-20s |", "ID", "Họ tên", "Tuổi", "Giới Tính", "Địa chỉ", "Email", "Số điện thoại", "Loại khách hàng");
        System.out.format("+-------+---------------------------+------+-----------+--------------------------------+---------------------------+-----------------+----------------------+%n");
        System.out.println(header);
        System.out.format("+-------+---------------------------+------+-----------+--------------------------------+---------------------------+-----------------+----------------------+%n");
        String row = String.format("| %-5s | %-25s | %-4s | %-9s | %-30s | %-25s | %-15s | %-20s |", k_hang.getCustomer_ID(), k_hang.getName(), k_hang.getAge(), k_hang.getGender(),
                k_hang.getAddress(), k_hang.getEmail(), k_hang.getSDT(), k_hang.getKindOfCustomer());
        System.out.println(row);
        System.out.format("+-------+---------------------------+------+-----------+--------------------------------+---------------------------+-----------------+----------------------+%n");

        String[] data = new String[cs.length];

        for (int i = 0; i < cs.length; i++) {
            if (ID_KhachHang.equals(cs[i].getCustomer_ID())) {
                System.out.println("Nhập thông tin khách hàng:");
                super.AddThongTin();

                System.out.print("Nhập loại khách hàng: ");
                setKindOfCustomer(input.nextLine());

                cs[i].setName(getName());
                cs[i].setGender(getGender());
                cs[i].setAddress(getAddress());
                cs[i].setEmail(getEmail());
                cs[i].setAge(getAge());
                cs[i].setSDT(getSDT());
                cs[i].setKindOfCustomer(getKindOfCustomer());
            }
            data[i] = cs[i].getCustomer_ID() + ";" + cs[i].getName() + ";" + cs[i].getAge() + ";" + cs[i].getGender() + ";" + cs[i].getAddress() + ";" + cs[i].getEmail() + ";" + cs[i].getSDT() + ";" + cs[i].getKindOfCustomer();
        }
        try {
            Stream.addAll("Database/KhachHang.txt", data); // link database here
            System.out.println("Sửa thông tin khách hàng thành công");
            waitConsole();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Delete() {
        System.out.print("Nhập ID khách hàng cần xóa: ");
        String ID_KhachHang = input.nextLine();
        Customer k_hang = null;

        for (Customer KhachHang : cs) {
            if (KhachHang.getCustomer_ID().equals(ID_KhachHang)) {
                k_hang = KhachHang;
                break;
            }
        }

        if (k_hang == null) {
            System.out.println("ID khách hàng không tồn tại. Xin vui lòng nhập lại!");
            return;
        }

        for (int i = 0; i < cs.length; i++) {
            if (ID_KhachHang.equals(cs[i].getCustomer_ID())) {
                cs = deleteCustomer(cs, i);
                break;
            }
        }
        String[] data = new String[cs.length];
        for (int i = 0; i < cs.length; i++) {
            data[i] = cs[i].getCustomer_ID() + ";" + cs[i].getName() + ";" + cs[i].getAge() + ";" + cs[i].getGender() + ";" + cs[i].getAddress() + ";" + cs[i].getEmail() + ";" + cs[i].getSDT() + ";" + cs[i].getKindOfCustomer();
        }
        try {
            Stream.addAll("Database/KhachHang.txt", data); // link database here
            System.out.println("Xóa khách hàng thành công");
            waitConsole();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Customer[] deleteCustomer(Customer[] cs, int index) {
        Customer[] newCs = new Customer[cs.length - 1];
        for (int i = 0, j = 0; i < cs.length; i++) {
            if (i != index) {
                newCs[j++] = cs[i];
            }
        }
        return newCs;
    }

    public Customer[] addCustomer(Customer[] cs, Customer customer) {
        cs = Arrays.copyOf(cs, cs.length + 1);
        cs[cs.length - 1] = customer;
        return cs;
    }

    @Override
    public void Search_byCategory() {
        Customer[] result = new Customer[0];
        System.out.println("Nhập mục lục cần tìm kiếm: ");
        System.out.println("1.Mã khách hàng");
        System.out.println("2.Tên khách hàng");
        System.out.println("3.Địa chỉ khách hàng");
        System.out.println("4.Số điện thoại khách hàng");
        System.out.print("Nhập lựa chọn: ");
        int choose = input.nextInt();

        switch (choose) {
            case 1 -> {
                System.out.print("Nhập ID khách hàng: ");
                String Customer_ID = input.nextLine();
                for (int i = 0; i < cs.length; i++) {
                    if (Customer_ID.equals(cs[i].getCustomer_ID())) {
                        result = addCustomer(result, cs[i]);
                    }
                }
            }
            case 2 -> {
                input.nextLine();
                System.out.print("Nhập tên khách hàng: ");
                String nameCustomer = input.nextLine();
                for (Customer KhachHang : cs) {
                    if (KhachHang.getName().toLowerCase().contains(nameCustomer.toLowerCase())) {
                        result = addCustomer(result, KhachHang);
                    }
                }
            }
            case 3 -> {
                input.nextLine();
                System.out.print("Nhập địa chỉ của khách hàng: ");
                String address = input.nextLine();
                for (Customer KhachHang : cs) {
                    if (KhachHang.getAddress().toLowerCase().contains(address.toLowerCase())) {
                        result = addCustomer(result, KhachHang);
                    }
                }
            }
            case 4 -> {
                input.nextLine();
                System.out.print("Nhập số điện thoại của khách hàng: ");
                String phoneNumber = input.nextLine();
                for (Customer KhachHang : cs) {
                    if (KhachHang.getSDT().toLowerCase().contains(phoneNumber.toLowerCase())) {
                        result = addCustomer(result, KhachHang);
                    }
                }
            }
        }

        System.out.println("Danh sách khách hàng tìm được:");
        String header = String.format("| %-5s | %-25s | %-4s | %-9s | %-30s | %-25s | %-15s | %-20s |", "ID", "Họ tên", "Tuổi", "Giới Tính", "Địa chỉ", "Email", "Số điện thoại", "Loại khách hàng");
        System.out.format("+-------+---------------------------+------+-----------+--------------------------------+---------------------------+-----------------+----------------------+%n");
        System.out.println(header);
        System.out.format("+-------+---------------------------+------+-----------+--------------------------------+---------------------------+-----------------+----------------------+%n");
        for (Customer customer : result) {
            String read = String.format("| %-5s | %-25s | %-4s | %-9s | %-30s | %-25s | %-15s | %-20s |", customer.getCustomer_ID(), customer.getName(), customer.getAge(), customer.getGender(), customer.getAddress(), customer.getEmail(), customer.getSDT(), customer.getKindOfCustomer());
            System.out.println(read);
        }
        System.out.format("+-------+---------------------------+------+-----------+--------------------------------+---------------------------+-----------------+----------------------+%n");
        waitConsole();
    }

    public void waitConsole() {
        System.out.println("Ấn Enter để tiếp tục");
        input.nextLine();
    }

}
