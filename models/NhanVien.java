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
    Scanner sc = new Scanner(System.in);
    String value;
    super.AddThongTin();

    System.out.println("Nhập vai trò: () ");
    String verify1[] = { "Sales", "Marketing", "Advertising", "Customer Service" };
    while (true) {
      value = sc.nextLine();
      if (value.isBlank() || value.length() > 30) {
        System.out.println("Vai trò không hợp lệ. Nhập lại: ");
      } else {
        boolean flag = false;
        for (String v : verify1) {
          if (value.equals(v)) {
            flag = true;
            break;
          }
          flag = false;
        }
        if (flag) {
          setRole(value);
          break;
        } else {
          System.out.println("Vai trò không hợp lệ. Nhập lại: ");
        }
      }
    }

    System.out.println("Nhập ca trực (morning, afternoon, night): ");
    String verify[] = { "morning", "afternoon", "night" };
    while (true) {
      value = sc.nextLine();
      if (value.isBlank() || value.length() > 10) {
        System.out.println("Ca trực không hợp lệ. Nhập lại: ");
      } else {
        boolean flag = false;
        for (String v : verify) {
          if (value.equals(v)) {
            flag = true;
            break;
          }
          flag = false;
        }
        if (flag) {
          setShift(value);
          break;
        } else {
          System.out.println("Ca trực không hợp lệ. Nhập lại: ");
        }
      }
    }
    sc.close();
  }
}