package models;

import java.util.Scanner;

import controllers.Validation;

public class NhanVien extends ConNguoi {
  private String workerId, role, shift;

  public NhanVien() {
  }

  public NhanVien(String workerId, String name, int age, String gender, String address, String email, String sdt,
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
    Validation validate = new Validation();
    Scanner sc = new Scanner(System.in);
    super.AddThongTin();

    // Nhập và kiểm tra vai trò
    System.out.println("Nhập vai trò (Sales, Marketing, Advertising, Customer Service): ");
    String[] validRoles = { "Sales", "Marketing", "Advertising", "Customer Service" };
    setRole(validate.validateInput(sc, validRoles, 15));

    // Nhập và kiểm tra ca trực
    System.out.println("Nhập ca trực (morning, afternoon, night): ");
    String[] validShifts = { "morning", "afternoon", "night" };
    setShift(validate.validateInput(sc, validShifts, 10));

    sc.close();
  }
}