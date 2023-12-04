package models;

import java.util.Scanner;

import controllers.Validation;

public class KhachHang extends ConNguoi {
  private String customerId;
  private String kindOfCustomer;

  public KhachHang() {
  }

  public KhachHang(String name, String gender, String address, String email, String sdt, int age, String customerId,
      String kindOfCustomer) {
    super(name, gender, address, email, sdt, age);
    this.customerId = customerId;
    this.kindOfCustomer = kindOfCustomer;
  }

  public String getCustomerId() {
    return this.customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

  public String getKindOfCustomer() {
    return this.kindOfCustomer;
  }

  public void setKindOfCustomer(String kindOfCustomer) {
    this.kindOfCustomer = kindOfCustomer;
  }

  public KhachHang customerId(String customerId) {
    setCustomerId(customerId);
    return this;
  }

  public KhachHang kindOfCustomer(String kindOfCustomer) {
    setKindOfCustomer(kindOfCustomer);
    return this;
  }

  @Override
  public void AddThongTin() {
    Scanner sc = new Scanner(System.in);
    String value;
    super.AddThongTin();

    System.out.println("Nhập loại khách hàng (walk-in/regular customer): ");
    sc.nextLine();
    while (true) {
      value = sc.nextLine();
      if (value.isBlank() || !Validation.isValidCustomer(value)) {
        System.out.println("ID khách hàng không hợp lệ. Nhập lại: ");
      } else {
        setKindOfCustomer(value);
        break;
      }
    }
    sc.close();
  }
}