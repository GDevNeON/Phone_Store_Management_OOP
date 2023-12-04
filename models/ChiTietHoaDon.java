package models;

public class ChiTietHoaDon {
    private String receiptId;
    private String productId;
    private int amount;
    private int price;

    public ChiTietHoaDon() {
    }

    public ChiTietHoaDon(String receiptId, String productId, int amount, int price) {
        this.receiptId = receiptId;
        this.productId = productId;
        this.amount = amount;
        this.price = price;
    }

    public ChiTietHoaDon(String productId, int amount, int price) {
    }

    public String getReceiptId() {
        return this.receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
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

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public ChiTietHoaDon receiptId(String receiptId) {
        setReceiptId(receiptId);
        return this;
    }

    public ChiTietHoaDon productId(String productId) {
        setProductId(productId);
        return this;
    }

    public ChiTietHoaDon amount(int amount) {
        setAmount(amount);
        return this;
    }

    public ChiTietHoaDon price(int price) {
        setPrice(price);
        return this;
    }

}
