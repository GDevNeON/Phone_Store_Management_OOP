package Entity;

import java.util.InputMismatchException;
import java.util.Scanner;

import Controller.Validation;

public class Person {
    static Scanner input = new Scanner(System.in);
    private String name, gender, address, email, SDT;
    private int age;

    public Person(String name, int age, String gender, String address, String email, String SDT) {
        this.name = name;
        this.gender = gender;
        this.address = address;
        this.email = email;
        this.SDT = SDT;
        this.age = age;
    }

    public Person() {
        super();
    }

    //Getter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }



    public void AddThongTin() {
        try {
            do {
                System.out.print("Nhập họ tên: ");
                setName(input.nextLine());
            } while (!Validation.isValidName(name));
            do {
                System.out.print("Nhập tuổi: ");
                setAge(input.nextInt());
            } while (!Validation.isValidAge(age));
            do {
                System.out.print("Nhập giới tính: ");
                setGender(input.nextLine());
            } while (!Validation.isValidGender(gender));
            do {
                System.out.print("Nhập địa chỉ: ");
                setAddress(input.nextLine());
            } while (!Validation.isValidAddress(address));
            do {
                System.out.print("Nhập Email: ");
                setEmail(input.nextLine());
            }
            while (!Validation.isValidEmail(email));
            do {
                System.out.print("Nhập số điện thoại: ");
                SDT = input.nextLine();
            } while (!Validation.isValidPhoneNumber(SDT));

        } catch (InputMismatchException ie) {
            System.out.println("Giá trị không hợp lệ, hãy nhập lại");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

