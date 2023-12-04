package models;

import java.time.LocalDate;

public class PhieuNhapHang {
  private String price;
  private String workerId, supplierName;
  private String receiptId;
  private LocalDate inputDate;

  public PhieuNhapHang() {
  }

  public PhieuNhapHang(String receiptId, String workerId, String supplierName, LocalDate inputDate,String price) {
    this.price = price;
    this.workerId = workerId;
    this.supplierName = supplierName;
    this.receiptId = receiptId;
    this.inputDate = inputDate;
  }

  public String getPrice() {
    return this.price;
  }

  public void setPrice(String price) {
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
}