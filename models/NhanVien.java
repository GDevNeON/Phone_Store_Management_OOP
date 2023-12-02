package models;

import java.util.Scanner;

public class NhanVien extends ConNguoi {
  private String workerId, role, shift;

  public NhanVien() {
  }

  public NhanVien(String name, String gender, String address, String email, String sdt, int age, String workerId,
      String role, String shift) {
    super(name, gender, address, email, sdt, age);
    this.workerId = workerId;
    this.role = role;
    this.shift = shift;
  }

  public String getWorkerId() {
    return this.workerId;
  }

  public void setWorkerId(String workerId) {
    this.workerId = workerId;
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

  public NhanVien workerId(String workerId) {
    setWorkerId(workerId);
    return this;
  }

  public NhanVien role(String role) {
    setRole(role);
    return this;
  }

  public NhanVien shift(String shift) {
    setShift(shift);
    return this;
  }

  @Override
  public void AddThongTin() {
    Scanner input = new Scanner(System.in);
    super.AddThongTin();

    System.out.print("Nhập chức vụ của nhân viên:");
    setRole(input.nextLine());

    System.out.print("Nhập ca làm việc của nhân viên: ");
    setShift(input.nextLine());
  }
}