package models;

import java.time.LocalDate;

public class ThanhToan {

  private String paymentId;
  private String customerId;
  private String receiptId;
  private int amount;
  private LocalDate paymentDate;
  private String paymentMethod;
  private String status;

  public ThanhToan() {
  }

  public ThanhToan(String paymentId, String customerId, String receiptId, int amount, LocalDate paymentDate,
      String paymentMethod, String status) {
    this.paymentId = paymentId;
    this.customerId = customerId;
    this.receiptId = receiptId;
    this.amount = amount;
    this.paymentDate = paymentDate;
    this.paymentMethod = paymentMethod;
    this.status = status;
  }

  public String getPaymentId() {
    return this.paymentId;
  }

  public void setPaymentId(String paymentId) {
    this.paymentId = paymentId;
  }

  public String getCustomerId() {
    return this.customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

  public String getReceiptId() {
    return this.receiptId;
  }

  public void setReceiptId(String receiptId) {
    this.receiptId = receiptId;
  }

  public int getAmount() {
    return this.amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  public LocalDate getPaymentDate() {
    return this.paymentDate;
  }

  public void setPaymentDate(LocalDate paymentDate) {
    this.paymentDate = paymentDate;
  }

  public String getPaymentMethod() {
    return this.paymentMethod;
  }

  public void setPaymentMethod(String paymentMethod) {
    this.paymentMethod = paymentMethod;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public ThanhToan paymentId(String paymentId) {
    setPaymentId(paymentId);
    return this;
  }

  public ThanhToan customerId(String customerId) {
    setCustomerId(customerId);
    return this;
  }

  public ThanhToan receiptId(String receiptId) {
    setReceiptId(receiptId);
    return this;
  }

  public ThanhToan amount(int amount) {
    setAmount(amount);
    return this;
  }

  public ThanhToan paymentDate(LocalDate paymentDate) {
    setPaymentDate(paymentDate);
    return this;
  }

  public ThanhToan paymentMethod(String paymentMethod) {
    setPaymentMethod(paymentMethod);
    return this;
  }

  public ThanhToan status(String status) {
    setStatus(status);
    return this;
  }
}