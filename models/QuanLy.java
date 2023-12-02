package models;

import java.util.Scanner;

public class QuanLy extends ConNguoi {
  private String managerId;
  private String role;
  private String shift;
  private int slnv;

  public QuanLy() {
  }

  public QuanLy(String name, String gender, String address, String email, String sdt, int age, String managerId, String role, String shift, int slnv) {
    super(name, gender, address, email, sdt, age);
    this.managerId = managerId;
    this.role = role;
    this.shift = shift;
    this.slnv = slnv;
  }

  public String getManagerId() {
    return this.managerId;
  }

  public void setManagerId(String managerId) {
    this.managerId = managerId;
  }

  public String getRole() {
    return this.role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getShift() {
    return this.shift;
  }

  public void setShift(String shift) {
    this.shift = shift;
  }

  public int getSlnv() {
    return this.slnv;
  }

  public void setSlnv(int slnv) {
    this.slnv = slnv;
  }

  public QuanLy managerId(String managerId) {
    setManagerId(managerId);
    return this;
  }

  public QuanLy role(String role) {
    setRole(role);
    return this;
  }

  public QuanLy shift(String shift) {
    setShift(shift);
    return this;
  }

  public QuanLy slnv(int slnv) {
    setSlnv(slnv);
    return this;
  }

  @Override
  public void AddThongTin() {
    Scanner sc = new Scanner(System.in);

    super.AddThongTin();

    System.out.println("Nhập mã người quản lý: ");
    setManagerId(sc.nextLine());

    System.out.println("Nhập chức vụ: ");
    setRole(sc.nextLine());

    System.out.println("Nhập ca trực: ");
    setShift(sc.nextLine());
  }
}