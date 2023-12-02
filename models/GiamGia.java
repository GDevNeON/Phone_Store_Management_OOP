package models;

import java.time.LocalDate;

public class GiamGia {
  private String discountId;
  private String kindOfCustomer;
  private String productName;
  private String discountRate;
  private String status;
  private LocalDate startDate;
  private LocalDate endDate;

  public GiamGia() {
  }

  public GiamGia(String discountId, String kindOfCustomer, String productName, String discountRate, String status,
      LocalDate startDate, LocalDate endDate) {
    this.discountId = discountId;
    this.kindOfCustomer = kindOfCustomer;
    this.productName = productName;
    this.discountRate = discountRate;
    this.status = status;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  public String getDiscountId() {
    return this.discountId;
  }

  public void setDiscountId(String discountId) {
    this.discountId = discountId;
  }

  public String getKindOfCustomer() {
    return this.kindOfCustomer;
  }

  public void setKindOfCustomer(String kindOfCustomer) {
    this.kindOfCustomer = kindOfCustomer;
  }

  public String getProductName() {
    return this.productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public String getDiscountRate() {
    return this.discountRate;
  }

  public void setDiscountRate(String discountRate) {
    this.discountRate = discountRate;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public LocalDate getStartDate() {
    return this.startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return this.endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public GiamGia discountId(String discountId) {
    setDiscountId(discountId);
    return this;
  }

  public GiamGia kindOfCustomer(String kindOfCustomer) {
    setKindOfCustomer(kindOfCustomer);
    return this;
  }

  public GiamGia productName(String productName) {
    setProductName(productName);
    return this;
  }

  public GiamGia discountRate(String discountRate) {
    setDiscountRate(discountRate);
    return this;
  }

  public GiamGia status(String status) {
    setStatus(status);
    return this;
  }

  public GiamGia startDate(LocalDate startDate) {
    setStartDate(startDate);
    return this;
  }

  public GiamGia endDate(LocalDate endDate) {
    setEndDate(endDate);
    return this;
  }

}