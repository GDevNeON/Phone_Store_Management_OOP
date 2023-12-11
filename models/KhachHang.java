package models;

import java.util.Scanner;

import controllers.Validation;

public class KhachHang extends ConNguoi {
  Scanner input = new Scanner(System.in);
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
    Validation validate = new Validation();
    
    String test;
        int tmp;
        try {
            do {
                System.out.print("Nhập họ tên: ");
                test = sc.nextLine();
                setName(test);
            } while (!Validation.isValidName(test));
            do {
                tmp = Validation.readValidAge();
                setAge(tmp);
            } while (!Validation.isValidAge(tmp));
            do {
                System.out.print("Nhập giới tính: ");
                test = sc.nextLine();
                setGender(test);
            } while (!Validation.isValidGender(test));
            do {
                System.out.print("Nhập địa chỉ: ");
                test = sc.nextLine();
                setAddress(test);
            } while (!Validation.isValidAddress(test));
            do {
                System.out.print("Nhập Email: ");
                test = sc.nextLine();
                setEmail(test);
            } while (!Validation.isValidEmail(test));
            do {
                System.out.print("Nhập số điện thoại: ");
                test = sc.nextLine();
                setSdt(test);
            } while (!Validation.isValidPhoneNumber(test));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    String value;
    do {
      System.out.println("Nhập loại khách hàng (walk-in/regular customer): ");
      value = input.nextLine();
      if (value.isBlank() || !validate.isValidKindOfCustomer(value)) {
        System.out.println("Loại khách hàng không hợp lệ. Nhập lại: ");
      } else {
        setKindOfCustomer(value);
      }
    } while (!validate.isValidKindOfCustomer(value));
  }
}