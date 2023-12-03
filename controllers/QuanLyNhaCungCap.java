
package controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
import models.NhaCungCap;

public class QuanLyNhaCungCap implements ControllerInterface {
    public NhaCungCap[] DSNCC;
    Scanner input = new Scanner(System.in);
    private static QuanLyNhaCungCap instance;
    NhaCungCap ncc = new NhaCungCap();

    public static QuanLyNhaCungCap getInstance() {
        if (instance == null) {
            instance = new QuanLyNhaCungCap();
        }
        return instance;
    }

    private QuanLyNhaCungCap() {
        getListNhaCungCap();
    }

    public NhaCungCap[] getListNhaCungCap() {
        String[] result = new String[0];
        try {
            result = Stream.read("Database/NhaCungCap.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        DSNCC = new NhaCungCap[result.length];
        for (int i = 0; i < result.length; i++) {
            String[] row = result[i].split(";");
            DSNCC[i] = new NhaCungCap(row[0], row[1], row[2], row[3], row[4]);
        }
        return DSNCC;
    }

    public void waitConsole() {
        System.out.println("\t\t\t\t\t\t\t\t - Ấn Enter để tiếp tục");
        input.nextLine();
    }

    @Override
    public void Read() {
        System.out.println("\t\t\t\t\t\t\t\t +----DANH SÁCH NHÀ CUNG CẤP----+");
        String header = String.format("| %-8s | %-30s | %-40s | %-12s | %-12s |", "ID", "Tên nhà cung cấp", "Địa chỉ",
                "Điện thoại", "Fax");
        System.out.format(
                "+----------+--------------------------------+------------------------------------------+--------------+--------------+%n");
        System.out.println(header);
        System.out.format(
                "+----------+--------------------------------+------------------------------------------+--------------+--------------+%n");

        getListNhaCungCap();

        for (int i = 0; i < DSNCC.length; i++) {
            String read = String.format("| %-8s | %-30s | %-40s | %-12s | %-12s |", DSNCC[i].getMaNCC(),
                    DSNCC[i].getTenNCC(), DSNCC[i].getDiaChi(), DSNCC[i].getSDT(), DSNCC[i].getFax());
            System.out.println(read);
        }
        System.out.format(
                "+----------+--------------------------------+------------------------------------------+--------------+--------------+%n");
        waitConsole();
    }

    @Override
    public void Create() {
        System.out.println("\t\t\t\t\t\t\t\t +----NHẬP THÔNG TIN NHÀ CUNG CẤP----+");

        ncc.nhapMaNCC(DSNCC);
        ncc.nhapTenNCC(DSNCC);
        ncc.nhapDiaChi(DSNCC);
        ncc.nhapSDT(DSNCC);
        ncc.nhapFax(DSNCC);

        try {
            String input = ncc.getMaNCC() + ";" + ncc.getTenNCC() + ";" + ncc.getDiaChi() + ";" + ncc.getSDT() + ";"
                    + ncc.getFax();
            Stream.addOneLine("Database/NhaCungCap.txt", input);
            System.out.println("\t\t\t\t\t\t\t\t +----NHẬP THÔNG TIN NHÀ CUNG CẤP THÀNH CÔNG----+");
            waitConsole();
        } catch (IOException e) {
            e.printStackTrace();
        }
        getListNhaCungCap();
    }

    @Override
    public void Update() {
        try {
            System.out.println("\t\t\t\t\t\t\t\t +----CẬP NHẬT THÔNG TIN NHÀ CUNG CẤP----+");
            System.out.print("- Mời nhập ID nhà cung cấp cần chỉnh sửa: ");
            String maNCC = input.nextLine();
            NhaCungCap nc_cap = null;

            for (NhaCungCap nhaCungCap : DSNCC) {
                if (nhaCungCap.getMaNCC().equals(maNCC)) {
                    nc_cap = nhaCungCap;
                    break;
                }
            }

            if (nc_cap == null) {
                System.out.println("\t\t\t\t\t\t\t\t +-----MÃ NHÀ CUNG CẤP KHÔNG TỒN TẠI-----+");
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

            System.out.println("\t\t\t\t\t\t\t\t +----THÔNG TIN NHÀ CUNG CẤP TRƯỚC KHI CHỈNH SỬA----+");
            String header = String.format("| %-8s | %-30s | %-40s | %-12s | %-12s |", "ID", "Tên nhà cung cấp",
                    "Địa chỉ", "Điện thoại", "Fax");
            System.out.format(
                    "+----------+--------------------------------+------------------------------------------+--------------+--------------+%n");
            System.out.println(header);
            System.out.format(
                    "+----------+--------------------------------+------------------------------------------+--------------+--------------+%n");

            String row = String.format("| %-8s | %-30s | %-40s | %-12s | %-12s |", nc_cap.getMaNCC(),
                    nc_cap.getTenNCC(),
                    nc_cap.getDiaChi(), nc_cap.getSDT(), nc_cap.getFax());
            System.out.println(row);
            System.out.format(
                    "+----------+--------------------------------+------------------------------------------+--------------+--------------+%n");

            if (choose == 1) {
                System.out.println("\t\t\t\t\t\t\t\t +--------NHẬP MỤC LỤC CẨN SỬA-------------+");
                System.out.println("\t\t\t\t\t\t\t\t |0. Thoát                                 |");
                System.out.println("\t\t\t\t\t\t\t\t +-----------------------------------------+");
                System.out.println("\t\t\t\t\t\t\t\t |1. ID nhà cung cấp                       |");
                System.out.println("\t\t\t\t\t\t\t\t |2. Tên nhà cung cấp                      |");
                System.out.println("\t\t\t\t\t\t\t\t |3. Địa chỉ nhà cung cấp                  |");
                System.out.println("\t\t\t\t\t\t\t\t |4. Số điện thoại                         |");
                System.out.println("\t\t\t\t\t\t\t\t |5. Fax                                   |");
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

                String[] data = new String[DSNCC.length];

                for (int i = 0; i < DSNCC.length; i++) {
                    if (DSNCC[i].getMaNCC().equals(maNCC)) {
                        System.out.println("Nhập thông tin nhà cung cấp:");

                        switch (index) {
                            case 0:
                                return;
                            case 1:
                                input.nextLine();
                                ncc.nhapMaNCC(DSNCC);
                                DSNCC[i].setMaNCC(ncc.getMaNCC());
                                break;
                            case 2:
                                ncc.nhapTenNCC(DSNCC);
                                DSNCC[i].setTenNCC(ncc.getTenNCC());
                                break;
                            case 3:
                                ncc.nhapDiaChi(DSNCC);
                                DSNCC[i].setDiaChi(ncc.getDiaChi());
                                break;
                            case 4:
                                ncc.nhapSDT(DSNCC);
                                DSNCC[i].setSDT(ncc.getSDT());
                                break;
                            case 5:
                                ncc.nhapFax(DSNCC);
                                DSNCC[i].setFax(ncc.getFax());
                                break;
                        }
                    }
                    data[i] = DSNCC[i].getMaNCC() + ";" + DSNCC[i].getTenNCC() + ";" + DSNCC[i].getDiaChi() + ";"
                            + DSNCC[i].getSDT() + ";" + DSNCC[i].getFax();
                }
                try {
                    Stream.addAll("Database/NhaCungCap.txt", data);
                    System.out.println("\t\t\t\t\t\t\t\t +----SỬA THÔNG TIN NHÀ CUNG CẤP THÀNH CÔNG----+");
                    waitConsole();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                String[] data = new String[DSNCC.length];

                for (int i = 0; i < DSNCC.length; i++) {
                    if (DSNCC[i].getMaNCC().equals(maNCC)) {
                        System.out.println("Nhập thông tin nhà cung cấp:");
                        input.nextLine();

                        ncc.nhapMaNCC(DSNCC);
                        ncc.nhapTenNCC(DSNCC);
                        ncc.nhapDiaChi(DSNCC);
                        ncc.nhapSDT(DSNCC);
                        ncc.nhapFax(DSNCC);

                        DSNCC[i].setMaNCC(ncc.getMaNCC());
                        DSNCC[i].setTenNCC(ncc.getTenNCC());
                        DSNCC[i].setDiaChi(ncc.getDiaChi());
                        DSNCC[i].setSDT(ncc.getSDT());
                        DSNCC[i].setFax(ncc.getFax());
                    }
                    data[i] = DSNCC[i].getMaNCC() + ";" + DSNCC[i].getTenNCC() + ";" + DSNCC[i].getDiaChi() + ";"
                            + DSNCC[i].getSDT() + ";" + DSNCC[i].getFax();
                }

                try {
                    Stream.addAll("Database/NhaCungCap.txt", data);
                    System.out.println("\t\t\t\t\t\t\t\t +----SỬA THÔNG TIN NHÀ CUNG CẤP THÀNH CÔNG----+");
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
            System.out.println("\t\t\t\t\t\t\t\t +----XÓA THÔNG TIN NHÀ CUNG CẤP----+");
            System.out.print("-Nhập ID nhà cung cấp cần xóa: ");
            String maNCC = input.nextLine();

            NhaCungCap nc_cap = null;
            for (NhaCungCap nhaCungCap : DSNCC) {
                if (nhaCungCap.getMaNCC().equals(maNCC)) {
                    nc_cap = nhaCungCap;
                    break;
                }
            }

            if (nc_cap == null) {
                System.out.println("\t\t\t\t\t\t\t\t -MÃ NHÀ CUNG CẤP KHÔNG TỒN TẠI!");
                return;
            }

            for (int i = 0; i < DSNCC.length; i++) {
                if (maNCC.equals(DSNCC[i].getMaNCC())) {
                    DSNCC = deleteNhaCungCap(DSNCC, i);
                    break;
                }
            }
            String[] data = new String[DSNCC.length];
            for (int i = 0; i < DSNCC.length; i++) {
                data[i] = DSNCC[i].getMaNCC() + ";" + DSNCC[i].getTenNCC() + ";" + DSNCC[i].getDiaChi() + ";"
                        + DSNCC[i].getSDT() + ";" + DSNCC[i].getFax();
            }
            try {
                Stream.addAll("Database/NhaCungCap.txt", data);
                System.out.println("\t\t\t\t\t\t\t\t +----XÓA THÔNG TIN NHÀ CUNG CẤP THÀNH CÔNG----+");
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

    public NhaCungCap[] deleteNhaCungCap(NhaCungCap[] DSNCC, int index) {
        NhaCungCap[] newNCC = new NhaCungCap[DSNCC.length - 1];
        for (int i = 0, j = 0; i < DSNCC.length; i++) {
            if (i != index) {
                newNCC[j++] = DSNCC[i];
            }
        }
        return newNCC;
    }

    public NhaCungCap[] addNhaCungCap(NhaCungCap[] DSNCC, NhaCungCap nc_cap) {
        DSNCC = Arrays.copyOf(DSNCC, DSNCC.length + 1);
        DSNCC[DSNCC.length - 1] = nc_cap;
        return DSNCC;
    }

    @Override
    public void searchByCategory() {
        try {
            NhaCungCap[] result = new NhaCungCap[0];
            System.out.println("\t\t\t\t\t\t\t\t +--------NHẬP MỤC LỤC CẨN TÌM KIẾM--------+");
            System.out.println("\t\t\t\t\t\t\t\t |0. Thoát                                 |");
            System.out.println("\t\t\t\t\t\t\t\t +-----------------------------------------+");
            System.out.println("\t\t\t\t\t\t\t\t |1. ID nhà cung cấp                       |");
            System.out.println("\t\t\t\t\t\t\t\t |2. Tên nhà cung cấp                      |");
            System.out.println("\t\t\t\t\t\t\t\t |3. Địa chỉ nhà cung cấp                  |");
            System.out.println("\t\t\t\t\t\t\t\t |4. Số điện thoại                         |");
            System.out.println("\t\t\t\t\t\t\t\t |5. Fax                                   |");
            System.out.println("\t\t\t\t\t\t\t\t +-----------------------------------------+");
            System.out.print("\t\t\t\t\t\t\t\t - Mời Bạn Nhập Lựa Chọn: ");
            int choose = input.nextInt();

            switch (choose) {
                case 0 -> {
                    return;
                }
                case 1 -> {
                    input.nextLine();
                    System.out.println("Nhập ID nhà cung cấp: ");
                    String maNCC = input.nextLine();
                    for (NhaCungCap nhaCungCap : DSNCC) {
                        if (nhaCungCap.getMaNCC().toLowerCase().contains(maNCC.toLowerCase())) {
                            result = addNhaCungCap(result, nhaCungCap);
                        }
                    }
                }
                case 2 -> {
                    System.out.println("Nhập tên nhà cung cấp: ");
                    input.nextLine();
                    String tenNCC = input.nextLine();
                    for (NhaCungCap nhaCungCap : DSNCC) {
                        if (nhaCungCap.getTenNCC().toLowerCase().contains(tenNCC.toLowerCase())) {
                            result = addNhaCungCap(result, nhaCungCap);
                        }
                    }
                }
                case 3 -> {
                    input.nextLine();
                    System.out.println("Nhập đia chỉ: ");
                    String diaChi = input.nextLine();
                    for (NhaCungCap nhaCungCap : DSNCC) {
                        if (nhaCungCap.getDiaChi().toLowerCase().contains(diaChi.toLowerCase())) {
                            result = addNhaCungCap(result, nhaCungCap);
                        }
                    }
                }
                case 4 -> {
                    input.nextLine();
                    System.out.println("Nhập số điện thoại: ");
                    String SDT = input.nextLine();
                    for (NhaCungCap nhaCungCap : DSNCC) {
                        if (nhaCungCap.getSDT().contains(SDT)) {
                            result = addNhaCungCap(result, nhaCungCap);
                        }
                    }
                }
                case 5 -> {
                    input.nextLine();
                    System.out.println("Nhập fax: ");
                    String Fax = input.nextLine();
                    for (NhaCungCap nhaCungCap : DSNCC) {
                        if (nhaCungCap.getFax().contains(Fax)) {
                            result = addNhaCungCap(result, nhaCungCap);
                        }
                    }
                }
                default -> {
                    System.out.println("Lựa chọn không hợp lệ!");
                }
            }

            System.out.println("\t\t\t\t\t\t\t\t +----THÔNG TIN NHÀ CUNG CẤP TÌM ĐƯỢC----+");
            String header = String.format("| %-8s | %-30s | %-40s | %-12s | %-12s |", "Mã", "Tên nhà cung cấp",
                    "Địa chỉ", "Điện thoại", "Fax");
            System.out.format(
                    "+----------+--------------------------------+------------------------------------------+--------------+--------------+%n");
            System.out.println(header);
            System.out.format(
                    "+----------+--------------------------------+------------------------------------------+--------------+--------------+%n");
            for (int i = 0; i < result.length; i++) {
                String read = String.format("| %-8s | %-30s | %-40s | %-12s | %-12s |", result[i].getMaNCC(),
                        result[i].getTenNCC(), result[i].getDiaChi(), result[i].getSDT(), result[i].getFax());
                System.out.println(read);
            }
            System.out.format(
                    "+----------+--------------------------------+------------------------------------------+--------------+--------------+%n");
            waitConsole();
        } catch (InputMismatchException ei) {
            System.out.println("\t\t\t\t\t\t\t\t GIÁ TRỊ KHÔNG HỢP LỆ. VUI LÒNG NHẬP LẠI!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
