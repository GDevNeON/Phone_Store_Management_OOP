package Entity;
public class ChiTietPhieuNhapHang {
    private String ID_PhieuNhap, ID_Product;
    private int amount;
    private int price;


    public ChiTietPhieuNhapHang(String ID_PhieuNhap, String ID_Product, int amount, int price) {
        this.ID_PhieuNhap = ID_PhieuNhap;
        this.ID_Product = ID_Product;
        this.amount = amount;
        this.price = price;
    }

    public ChiTietPhieuNhapHang() {

    }

    public String getID_PhieuNhap() {
        return ID_PhieuNhap;
    }

    public void setID_PhieuNhap(String ID_PhieuNhap) {
        this.ID_PhieuNhap = ID_PhieuNhap;
    }

    public String getID_Product() {
        return ID_Product;
    }

    public void setID_Product(String ID_Product) {
        this.ID_Product = ID_Product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
