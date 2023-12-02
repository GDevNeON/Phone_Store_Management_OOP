package models;

import java.time.LocalDate;

public class BaoHanh {
  private String productId;
  private LocalDate productDate;
  private String yearsOfWarranty;
  private String warrantyMethod;
  private String customerId;
  private String customerName;
  private String sdt;

  public BaoHanh() {
  }

  public BaoHanh(String productId, LocalDate productDate, String yearsOfWarranty, String warrantyMethod,
      String customerId, String customerName, String sdt) {
    this.productId = productId;
    this.productDate = productDate;
    this.yearsOfWarranty = yearsOfWarranty;
    this.warrantyMethod = warrantyMethod;
    this.customerId = customerId;
    this.customerName = customerName;
    this.sdt = sdt;
  }

  public String getProductId() {
    return this.productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public LocalDate getProductDate() {
    return this.productDate;
  }

  public void setProductDate(LocalDate productDate) {
    this.productDate = productDate;
  }

  public String getYearsOfWarranty() {
    return this.yearsOfWarranty;
  }

  public void setYearsOfWarranty(String yearsOfWarranty) {
    this.yearsOfWarranty = yearsOfWarranty;
  }

  public String getWarrantyMethod() {
    return this.warrantyMethod;
  }

  public void setWarrantyMethod(String warrantyMethod) {
    this.warrantyMethod = warrantyMethod;
  }

  public String getCustomerId() {
    return this.customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

  public String getCustomerName() {
    return this.customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public String getSdt() {
    return this.sdt;
  }

  public void setSdt(String sdt) {
    this.sdt = sdt;
  }

  public BaoHanh productId(String productId) {
    setProductId(productId);
    return this;
  }

  public BaoHanh productDate(LocalDate productDate) {
    setProductDate(productDate);
    return this;
  }

  public BaoHanh yearsOfWarranty(String yearsOfWarranty) {
    setYearsOfWarranty(yearsOfWarranty);
    return this;
  }

  public BaoHanh warrantyMethod(String warrantyMethod) {
    setWarrantyMethod(warrantyMethod);
    return this;
  }

  public BaoHanh customerId(String customerId) {
    setCustomerId(customerId);
    return this;
  }

  public BaoHanh customerName(String customerName) {
    setCustomerName(customerName);
    return this;
  }

  public BaoHanh sdt(String sdt) {
    setSdt(sdt);
    return this;
  }
}