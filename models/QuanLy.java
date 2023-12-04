package models;

import java.util.Scanner;

public class QuanLy extends ConNguoi {
    Scanner sc = new Scanner(System.in);
    private String managerId;
    private String role;
    private String shift;

    public QuanLy() {
    }

    public QuanLy(String managerId, String name, int age, String gender, String address, String email, String sdt,
            String role, String shift) {
        super(name, age, gender, address, email, sdt);
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
        String test;
        super.AddThongTin();

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

        System.out.println("Nhập ca trực (morning, afternoon, night): ");
        String verify[] = { "morning", "afternoon", "night" };
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