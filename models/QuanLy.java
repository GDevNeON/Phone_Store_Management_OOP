package models;

import java.util.Scanner;

import controllers.Validation;

public class QuanLy extends ConNguoi {
  private Validation validate = new Validation();
  private String managerId;
  private String role;
  private String shift;

  public QuanLy() {
  }

  public QuanLy(String name, String gender, String address, String email, String sdt, int age, String managerId,
      String role, String shift) {
    super(name, age, address, email, sdt, gender);
    this.managerId = managerId;
    this.role = role;
    this.shift = shift;
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

  @Override
  public void AddThongTin() {
    Scanner sc = new Scanner(System.in);
    String test;
    super.AddThongTin();

    System.out.println("Nhập mã người quản lý (ql_): ");
    sc.nextLine();
    while (true) {
      test = sc.nextLine();
      if (test.isBlank() || !validate.isValidIDmanager(test)) { // nếu như xâu test rỗng hoặc chứa toàn khoảng trắng,
                                                                // NHẬP LẠI ĐEEEEEEEE!!!!
        System.out.println("ID người quản lý không hợp lệ. Nhập lại: ");
      } else {
        setManagerId(test);
        break;
      }
    }

    System.out.println("Nhập chức vụ: (low/mid/high-tier) "); // thêm danh sách chức vụ tránh thầy nhập bừa
    String verify1[] = { "low-tier", "mid-tier", "high-tier" };
    while (true) {
      test = sc.nextLine();
      if (test.isBlank() || test.length() > 20) { // nếu như xâu test rỗng hoặc chứa toàn khoảng trắng, NHẬP LẠI
                                                  // ĐEEEEEEEE!!!!
        System.out.println("Chức vụ không hợp lệ. Nhập lại: ");
      } else {
        boolean flag = false;
        for (String v : verify1) {
          if (test.equals(v)) {
            flag = true;
            break;
          }
          flag = false;
        }
        if (flag) {
          setRole(test);
          break;
        } else {
          System.out.println("Chức vụ không hợp lệ. Nhập lại: ");
        }
      }
    }

    System.out.println("Nhập ca trực (ca sang, ca trua, ca chieu, ca toi): ");
    String verify[] = { "ca sang", "ca trua", "ca chieu", "ca toi" };
    while (true) {
      test = sc.nextLine();
      if (test.isBlank() || test.length() > 10) { // nếu như xâu test rỗng hoặc chứa toàn khoảng trắng, NHẬP LẠI
                                                  // ĐEEEEEEEE!!!!
        System.out.println("Ca trực không hợp lệ. Nhập lại: ");
      } else {
        boolean flag = false;
        for (String v : verify) {
          if (test.equals(v)) {
            flag = true;
            break;
          }
          flag = false;
        }
        if (flag) {
          setShift(test);
          break;
        } else {
          System.out.println("Ca trực không hợp lệ. Nhập lại: ");
        }
      }
    }
  }
}