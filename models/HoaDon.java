package models;

import java.time.LocalDate;

public class HoaDon {
  private String receiptId;
  private int price;
  private LocalDate purchaseDate;
  private String purchaseMethod;
  private String customerId;
  private String employeeId;

  public HoaDon() {
  }

  public HoaDon(String receiptId, int price, LocalDate purchaseDate, String purchaseMethod, String customerId,
      String employeeId) {
    this.receiptId = receiptId;
    this.price = price;
    this.purchaseDate = purchaseDate;
    this.purchaseMethod = purchaseMethod;
    this.customerId = customerId;
    this.employeeId = employeeId;
  }

  public String getReceiptId() {
    return this.receiptId;
  }

  public void setReceiptId(String receiptId) {
    this.receiptId = receiptId;
  }

  public int getPrice() {
    return this.price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public LocalDate getPurchaseDate() {
    return this.purchaseDate;
  }

  public void setPurchaseDate(LocalDate purchaseDate) {
    this.purchaseDate = purchaseDate;
  }

  public String getPurchaseMethod() {
    return this.purchaseMethod;
  }

  public void setPurchaseMethod(String purchaseMethod) {
    this.purchaseMethod = purchaseMethod;
  }

  public String getCustomerId() {
    return this.customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

  public String getEmployeeId() {
    return this.employeeId;
  }

  public void setEmployeeId(String employeeId) {
    this.employeeId = employeeId;
  }

  public HoaDon receiptId(String receiptId) {
    setReceiptId(receiptId);
    return this;
  }

  public HoaDon price(int price) {
    setPrice(price);
    return this;
  }

  public HoaDon purchaseDate(LocalDate purchaseDate) {
    setPurchaseDate(purchaseDate);
    return this;
  }

  public HoaDon purchaseMethod(String purchaseMethod) {
    setPurchaseMethod(purchaseMethod);
    return this;
  }

  public HoaDon customerId(String customerId) {
    setCustomerId(customerId);
    return this;
  }

  public HoaDon employeeId(String employeeId) {
    setEmployeeId(employeeId);
    return this;
  }

}