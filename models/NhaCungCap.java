package models;

import java.util.Scanner;
import controllers.Validation;

public class NhaCungCap {
    Scanner input = new Scanner(System.in);
    NhaCungCap[] DSNCC;
    private String MaNCC;
    private String TenNCC;
    private String DiaChi;
    private String SDT;
    private String fax;

    public NhaCungCap() {
        MaNCC = null;
        TenNCC = null;
        DiaChi = null;
        SDT = null;
        fax = null;
    }

    public NhaCungCap(String maNCC, String tenNCC, String diaChi, String sDT, String fax) {
        this.MaNCC = maNCC;
        this.TenNCC = tenNCC;
        this.DiaChi = diaChi;
        this.SDT = sDT;
        this.fax = fax;
    }

    public String getMaNCC() {
        return MaNCC;
    }

    public void setMaNCC(String maNCC) {
        this.MaNCC = maNCC;
    }

    public String getTenNCC() {
        return TenNCC;
    }

    public void setTenNCC(String tenNCC) {
        this.TenNCC = tenNCC;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        this.DiaChi = diaChi;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String sDT) {
        this.SDT = sDT;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public void nhapMaNCC(NhaCungCap[] DSNCC) {
        while (true) {
            System.out.println("Nhập ID nhà cung cấp (ncc_): ");
            MaNCC = input.nextLine();
            if (MaNCC.isEmpty() || !MaNCC.matches("^ncc[0-9]+$") || MaNCC.length() > 7) {
                System.out.println("Nhập sai định dạng.");
            } else {
                int check = 0;
                for (NhaCungCap nhaCungCap : DSNCC) {
                    if (getMaNCC().equals(nhaCungCap.getMaNCC())) {
                        check = 1;
                        break;
                    }
                }
                if (check == 1) {
                    System.out.println("\t\t\t\t\t\t\t\t +----MÃ NHÀ CUNG CẤP BỊ TRÙNG----+");
                } else {
                    break;

                }
            }
        }
        setMaNCC(MaNCC);
    }

    public void nhapTenNCC(NhaCungCap[] DSNCC) {
        do {
            System.out.println("Nhập tên nhà cung cấp");
            TenNCC = input.nextLine();
            if (TenNCC.isEmpty()) {
                System.out.println("Nhập sai định dạng.");
            }
            if (TenNCC.length() > 30) {
                System.out.println("Nhập không quá 30 kí tự.");
            }
        } while (TenNCC.isEmpty() || TenNCC.length() > 30);
        setTenNCC(TenNCC);
    }

    public void nhapDiaChi(NhaCungCap[] DSNCC) {
        do {
            System.out.println("Nhập địa chỉ nhà cung cấp");
            DiaChi = input.nextLine();
            if (DiaChi.isEmpty()) {
                System.out.println("Nhập sai định dạng.");
            }
            if (DiaChi.length() > 40) {
                System.out.println("Nhập không quá 40 kí tự.");
            }
        } while (DiaChi.isEmpty() || DiaChi.length() > 40);
        setDiaChi(DiaChi);
    }

    public void nhapSDT(NhaCungCap[] DSNCC) {
        do {
            System.out.println("Nhập số điện thoại nhà cung cấp (vd: 0_(9số))");
            setSDT(input.nextLine());
        } while (!Validation.isValidPhoneNumber(getSDT()));
    }

    public void nhapFax(NhaCungCap[] DSNCC) {
        do {
            System.out.println("Nhập số fax nhà cung cấp (vd: 84_(8số))");
            setFax(input.nextLine());
        } while (!Validation.isValidFax(getFax()));
    }
}
