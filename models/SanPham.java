package models;

public class SanPham {
  private String productId;
  private String typeOfProductId;
  private String name;
  private int amount;
  private int amountRemaining;
  private int price;
  private int status;

  public SanPham() {
    productId = null;
    typeOfProductId = null;
    name = null;
    amount = 0;
    amountRemaining = 0;
    price = 0;
    status = 0;
  }

  public SanPham(String productId, String typeOfProductId, String name, int amount, int amountRemaining, int price,
      int status) {
    this.productId = productId;
    this.typeOfProductId = typeOfProductId;
    this.name = name;
    this.amount = amount;
    this.amountRemaining = amountRemaining;
    this.price = price;
    this.status = status;
  }

  public String getProductId() {
    return this.productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public String getTypeOfProductId() {
    return this.typeOfProductId;
  }

  public void setTypeOfProductId(String typeOfProductId) {
    this.typeOfProductId = typeOfProductId;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAmount() {
    return this.amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  public int getAmountRemaining() {
    return this.amountRemaining;
  }

  public void setAmountRemaining(int amountRemaining) {
    this.amountRemaining = amountRemaining;
  }

  public int getPrice() {
    return this.price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public int getStatus() {
    return this.status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public SanPham productId(String productId) {
    setProductId(productId);
    return this;
  }

  public SanPham typeOfProductId(String typeOfProductId) {
    setTypeOfProductId(typeOfProductId);
    return this;
  }

  public SanPham name(String name) {
    setName(name);
    return this;
  }

  public SanPham amount(int amount) {
    setAmount(amount);
    return this;
  }

  public SanPham amountRemaining(int amountRemaining) {
    setAmountRemaining(amountRemaining);
    return this;
  }

  public SanPham price(int price) {
    setPrice(price);
    return this;
  }

  public SanPham status(int status) {
    setStatus(status);
    return this;
  }
}