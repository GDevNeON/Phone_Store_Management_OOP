package models;

import java.time.LocalDate;
import java.util.Scanner;

public class ThongKe {
    Scanner input = new Scanner(System.in);
    ThongKe[] TK;
    private String productId;
    private int amount;
    private int price;
    private int totalAmount;
    private LocalDate date;

    public ThongKe() {
        super();
        totalAmount = 0;
        date = null;
        productId = null;
        price = 0;
        amount = 0;
    }

    public ThongKe(String productId, int amount, int price, LocalDate date, int totalAmount) {
        this.productId = productId;
        this.totalAmount = totalAmount;
        this.date = date;
        this.amount = amount;
        this.price = price;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId){
        this.productId = productId;
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

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
