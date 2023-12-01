package Entity;

import java.time.LocalDate;

public class PhieuNhapHang {
    private int price;
    private String ID_Worker, name_Supplier;
    private String ID_PhieuNhap;
    private LocalDate ngayNhap;

    public PhieuNhapHang() {
    }

    public PhieuNhapHang(String ID_PhieuNhap, String ID_Worker, String name_Supplier, LocalDate ngayNhap, int price) {
        this.price = price;
        this.ID_Worker = ID_Worker;
        this.name_Supplier = name_Supplier;
        this.ID_PhieuNhap = ID_PhieuNhap;
        this.ngayNhap = ngayNhap;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getID_Worker() {
        return ID_Worker;
    }

    public void setID_Worker(String ID_Worker) {
        this.ID_Worker = ID_Worker;
    }

    public String getName_Supplier() {
        return name_Supplier;
    }

    public void setName_Supplier(String name_Supplier) {
        this.name_Supplier = name_Supplier;
    }

    public String getID_PhieuNhap() {
        return ID_PhieuNhap;
    }

    public void setID_PhieuNhap(String ID_PhieuNhap) {
        this.ID_PhieuNhap = ID_PhieuNhap;
    }

    public LocalDate getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(LocalDate ngayNhap) {
        this.ngayNhap = ngayNhap;
    }
}
