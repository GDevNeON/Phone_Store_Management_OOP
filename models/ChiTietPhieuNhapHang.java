package models;

public class ChiTietPhieuNhapHang {
  private String phieuNhapId, productId, price;
  private int amount;

  public ChiTietPhieuNhapHang() {
  }

  public ChiTietPhieuNhapHang(String phieuNhapId, String productId, int amount, String price) {
    this.phieuNhapId = phieuNhapId;
    this.productId = productId;
    this.amount = amount;
    this.price = price;
  }

  public String getPhieuNhapId() {
    return this.phieuNhapId;
  }

  public void setPhieuNhapId(String phieuNhapId) {
    this.phieuNhapId = phieuNhapId;
  }

  public String getProductId() {
    return this.productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public int getAmount() {
    return this.amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  public String getPrice() {
    return this.price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public ChiTietPhieuNhapHang phieuNhapId(String phieuNhapId) {
    setPhieuNhapId(phieuNhapId);
    return this;
  }

  public ChiTietPhieuNhapHang productId(String productId) {
    setProductId(productId);
    return this;
  }

  public ChiTietPhieuNhapHang amount(int amount) {
    setAmount(amount);
    return this;
  }

  public ChiTietPhieuNhapHang price(String price) {
    setPrice(price);
    return this;
  }
}