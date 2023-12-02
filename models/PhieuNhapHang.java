package models;

import java.time.LocalDate;

public class PhieuNhapHang {
  private int price;
  private String workerId, supplierName;
  private String receiptId;
  private LocalDate inputDate;

  public PhieuNhapHang() {
  }

  public PhieuNhapHang(int price, String workerId, String supplierName, String receiptId, LocalDate inputDate) {
    this.price = price;
    this.workerId = workerId;
    this.supplierName = supplierName;
    this.receiptId = receiptId;
    this.inputDate = inputDate;
  }

  public int getPrice() {
    return this.price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public String getWorkerId() {
    return this.workerId;
  }

  public void setWorkerId(String workerId) {
    this.workerId = workerId;
  }

  public String getSupplierName() {
    return this.supplierName;
  }

  public void setSupplierName(String supplierName) {
    this.supplierName = supplierName;
  }

  public String getReceiptId() {
    return this.receiptId;
  }

  public void setReceiptId(String receiptId) {
    this.receiptId = receiptId;
  }

  public LocalDate getInputDate() {
    return this.inputDate;
  }

  public void setInputDate(LocalDate inputDate) {
    this.inputDate = inputDate;
  }

  public PhieuNhapHang price(int price) {
    setPrice(price);
    return this;
  }

  public PhieuNhapHang workerId(String workerId) {
    setWorkerId(workerId);
    return this;
  }

  public PhieuNhapHang supplierName(String supplierName) {
    setSupplierName(supplierName);
    return this;
  }

  public PhieuNhapHang receiptId(String receiptId) {
    setReceiptId(receiptId);
    return this;
  }

  public PhieuNhapHang inputDate(LocalDate inputDate) {
    setInputDate(inputDate);
    return this;
  }
}