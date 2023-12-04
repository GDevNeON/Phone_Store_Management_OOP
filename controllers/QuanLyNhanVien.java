package controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

import models.NhanVien;

public class QuanLyNhanVien implements ControllerInterface {
  private static Scanner input = new Scanner(System.in);
  private NhanVien[] nv;
  private static QuanLyNhanVien instance;

  private QuanLyNhanVien() {
    getListEmployee();
  }

  public static QuanLyNhanVien getInstance() {
    if (instance == null) {
      instance = new QuanLyNhanVien();
    }
    return instance;
  }

  public NhanVien[] getListEmployee() {
    String[] result = new String[0];
    try {
      result = Stream.read("Database/NhanVien.txt");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    nv = new NhanVien[result.length];
    for (int i = 0; i < result.length; i++) {
      String[] row = result[i].split(";");
      nv[i] = new NhanVien(row[0], row[1], row[2], row[3], row[4], Integer.parseInt(row[5]), row[6], row[7],
          row[8]);
    }
    return nv;
  }

  public void waitConsole() {
    System.out.println("\t\t\t\t\t\t\t\t -Ấn Enter để tiếp tục");
    input.nextLine();
  }

  @Override
  public void Read() {
    System.out.println("\t\t\t\t\t\t\t\t +----DANH SÁCH NHÂN VIÊN----+");
    String header = String.format("| %-5s | %-25s | %-4s | %-9s | %-30s | %-25s | %-15s | %-20s | %-9s | ", "ID",
        "Họ tên", "Tuổi", "Giới Tính", "Địa chỉ", "Email", "Số điện thoại", "Chức vụ", "Ca trực");
    System.out.format(
        "+-------+---------------------------+------+-----------+--------------------------------+---------------------------+-----------------+----------------------+----------+%n");
    System.out.println(header);
    System.out.format(
        "+-------+---------------------------+------+-----------+--------------------------------+---------------------------+-----------------+----------------------+----------+%n");
    getListEmployee();
    for (NhanVien emPloyee : nv) {
      if (nv[0].getWorkerId().indexOf("nv") >= 0) {
        String read = String.format("| %-5s | %-25s | %-4s | %-9s | %-30s | %-25s | %-15s | %-20s | %-9s |",
            emPloyee.getWorkerId(), emPloyee.getName(), emPloyee.getAge(),
            emPloyee.getGender(), emPloyee.getAddress(), emPloyee.getEmail(), emPloyee.getSdt(),
            emPloyee.getRole(), emPloyee.getShift());
        System.out.println(read);
      }
    }
    System.out.format(
        "+-------+---------------------------+------+-----------+--------------------------------+---------------------------+-----------------+----------------------+----------+%n");
    waitConsole();
  }

  @Override
  public void Create() {
    NhanVien nvien = new NhanVien();
    boolean checkIDexist = false;
    do {
      System.out.println("\t\t\t\t\t\t\t\t +----NHẬP THÔNG TIN NHÂN VIÊN----+");
      System.out.print("Nhập ID nhân viên:");
      nvien.setWorkerId(input.nextLine());
      for (NhanVien employee : nv) {
        // if (employee != null) {
        if (nvien.getWorkerId().equals(employee.getWorkerId())) {
          System.out.println("\t\t\t\t\t\t\t\t -MÃ NHÂN VIÊN BỊ TRÙNG!");
          checkIDexist = true;
          break;
        }
        // }
      }
    } while (checkIDexist);

    input.nextLine();
    nvien.AddThongTin();

    try {
      String input = nvien.getWorkerId() + ";" + nvien.getName() + ";" + nvien.getAge() + ";" + nvien.getGender() + ";"
          + nvien.getAddress()
          + ";" + nvien.getEmail() + ";" + nvien.getSdt() + ";" + nvien.getRole() + ";" + nvien.getShift();
      Stream.addOneLine("Database/NhanVien.txt", input); // database here
      System.out.println("\t\t\t\t\t\t\t\t -NHẬP NHÂN VIÊN THÀNH CÔNG");
      waitConsole();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  @Override
  public void Update() {
    try {
      System.out.println("\t\t\t\t\t\t\t\t +----CẬP NHẬT LẠI THÔNG TIN NHÂN VIÊN----+");
      input.nextLine();
      System.out.print("\t\t\t\t\t\t\t\t - Mời bạn nhập mã nhân viên cần chỉnh sửa: ");
      String ID_Employee = input.nextLine();
      NhanVien employee = null;

      for (NhanVien emPloyee : nv) {
        if (emPloyee.getWorkerId().equals(ID_Employee)) {
          employee = emPloyee;
          break;
        }
      }

      if (employee == null) {
        System.out.println("\t\t\t\t\t\t\t\t -MÃ NHÂN VIÊN KHÔNG TỒN TẠI!");
        return;
      }

      System.out.println("\t\t\t\t\t\t\t\t +----THÔNG TIN NHÂN VIÊN TRƯỚC KHI ĐƯỢC CHỈNH SỬA----+");
      String header = String.format("| %-5s | %-25s | %-4s | %-9s | %-30s | %-25s | %-15s | %-20s | %-9s |", "ID",
          "Họ tên", "Tuổi", "Giới Tính", "Địa chỉ", "Email", "Số điện thoại", "Chức vụ", "Ca trực");
      System.out.format(
          "+-------+---------------------------+------+-----------+--------------------------------+---------------------------+-----------------+----------------------+----------+%n");
      System.out.println(header);
      System.out.format(
          "+-------+---------------------------+------+-----------+--------------------------------+---------------------------+-----------------+----------------------+----------+%n");
      String row = String.format("| %-5s | %-25s | %-4s | %-9s | %-30s | %-25s | %-15s | %-20s | %-9s |",
          employee.getWorkerId(), employee.getName(), employee.getAge(), employee.getGender(),
          employee.getAddress(), employee.getEmail(), employee.getSdt(), employee.getRole(),
          employee.getShift());
      System.out.println(row);

      String[] data = new String[nv.length];

      for (int i = 0; i < nv.length; i++) {
        if (ID_Employee.equals(nv[i].getWorkerId())) {
          System.out.println("Nhập thông tin nhân viên:");
          employee.AddThongTin();

          nv[i].setName(employee.getName());
          nv[i].setGender(employee.getGender());
          nv[i].setAddress(employee.getAddress());
          nv[i].setEmail(employee.getEmail());
          nv[i].setAge(employee.getAge());
          nv[i].setSdt(employee.getSdt());
          nv[i].setShift(employee.getShift());
        }
        data[i] = nv[i].getWorkerId() + ";" + nv[i].getName() + ";" + nv[i].getAge() + ";" + nv[i].getGender()
            + ";" + nv[i].getAddress() + ";" + nv[i].getEmail() + ";" + nv[i].getSdt() + ";"
            + nv[i].getRole() + ";" + nv[i].getShift();
      }
      try {
        Stream.addAll("Database/NhanVien.txt", data); // link database here
        System.out.println("\t\t\t\t\t\t\t\t----SỬA THÔNG TIN NHÂN VIÊN THÀNH CÔNG----");
        waitConsole();
      } catch (IOException e) {
        e.printStackTrace();
      }
    } catch (InputMismatchException ei) {
      System.out.println("\t\t\t\t\t\t\t\t GIÁ TRỊ KHÔNG HỢP LỆ. VUI LÒNG NHẬP LẠI!");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  @Override
  public void Delete() {
    try {
      System.out.println("\t\t\t\t\t\t\t\t +----XÓA THÔNG TIN NHÂN VIÊN----+");
      System.out.print("\t\t\t\t\t\t\t\t -Nhập mã nhân viên cần xóa: ");
      String ID_Nhanvien = input.nextLine();
      NhanVien n_vien = null;

      for (NhanVien emPloyee : nv) {
        if (emPloyee.getWorkerId().equals(ID_Nhanvien)) {
          n_vien = emPloyee;
          break;
        }
      }

      if (n_vien == null) {
        System.out.println("\t\t\t\t\t\t\t\t -MÃ NHÂN VIÊN KHÔNG TỒN TẠI!");
        return;
      }

      for (int i = 0; i < nv.length; i++) {
        if (ID_Nhanvien.equals(nv[i].getWorkerId())) {
          nv = deleteEmployee(nv, i);
          break;
        }
      }

      String[] data = new String[nv.length];
      for (int i = 0; i < nv.length; i++) {
        data[i] = nv[i].getWorkerId() + ";" + nv[i].getName() + ";" + nv[i].getAge() + ";" + nv[i].getGender()
            + ";" + nv[i].getAddress() + ";" + nv[i].getEmail() + ";" + nv[i].getSdt() + ";"
            + nv[i].getRole() + ";" + nv[i].getShift();
      }
      try {
        Stream.addAll("Database/NhanVien.txt", data); // link database here
        System.out.println("\t\t\t\t\t\t\t\t +----XÓA THÔNG TIN NHÂN VIÊN THÀNH CÔNG----+");
        waitConsole();
      } catch (IOException e) {
        e.printStackTrace();
      }
    } catch (InputMismatchException ei) {
      System.out.println("Nhập giá trị không hợp lệ, vui lòng nhập lại!");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public NhanVien[] deleteEmployee(NhanVien[] nv, int index) {
    NhanVien[] newCs = new NhanVien[nv.length - 1];
    for (int i = 0, j = 0; i < nv.length; i++) {
      if (i != index) {
        newCs[j++] = nv[i];
      }
    }
    return newCs;
  }

  public NhanVien[] addEmployee(NhanVien[] nv, NhanVien emPloyee) {
    nv = Arrays.copyOf(nv, nv.length + 1);
    nv[nv.length - 1] = emPloyee;
    return nv;
  }

  @Override
  public void searchByCategory() {
    NhanVien[] result = new NhanVien[0];
    System.out.println("\t\t\t\t\t\t\t\t +--------NHẬP MỤC LỤC CẨN TÌM KIẾM--------+");
    System.out.println("\t\t\t\t\t\t\t\t |0.Thoát                                  |");
    System.out.println("\t\t\t\t\t\t\t\t +-----------------------------------------+");
    System.out.println("\t\t\t\t\t\t\t\t |1.Mã nhân viên                           |");
    System.out.println("\t\t\t\t\t\t\t\t |2.Tên nhân viên                          |");
    System.out.println("\t\t\t\t\t\t\t\t |3.Địa chỉ nhân viên                      |");
    System.out.println("\t\t\t\t\t\t\t\t |4.Số điện thoại nhân viên                |");
    System.out.println("\t\t\t\t\t\t\t\t |5.Chức vụ nhân viên                      |");
    System.out.println("\t\t\t\t\t\t\t\t |6.Ca trực của nhân viên                  |");
    System.out.println("\t\t\t\t\t\t\t\t +-----------------------------------------+");
    System.out.print("\t\t\t\t\t\t\t\t - Mời Bạn Nhập Lựa Chọn: ");
    int choose = input.nextInt();
    if (choose == 0) {
    } else {
      switch (choose) {
        case 1 -> {
          input.nextLine();
          System.out.print("Nhập mã nhân viên: ");
          String ID_Worker = input.nextLine();
          for (NhanVien emPloyee : nv) {
            if (ID_Worker.contains(emPloyee.getWorkerId())) {
              result = addEmployee(result, emPloyee);
            }
          }
        }
        case 2 -> {
          input.nextLine();
          System.out.print("Nhập họ tên nhân viên: ");
          String nameWorker = input.nextLine();
          for (NhanVien emPloyee : nv) {
            if (emPloyee.getName().toLowerCase().contains(nameWorker.toLowerCase())) {
              result = addEmployee(result, emPloyee);
            }
          }
        }
        case 3 -> {
          input.nextLine();
          System.out.print("Nhập địa chỉ của nhân viên: ");
          String address = input.nextLine();
          for (NhanVien emPloyee : nv) {
            if (emPloyee.getAddress().toLowerCase().contains(address.toLowerCase())) {
              result = addEmployee(result, emPloyee);
            }
          }
        }
        case 4 -> {
          input.nextLine();
          System.out.print("Nhập số điện thoại của nhân viên: ");
          String phoneNumber = input.nextLine();
          for (NhanVien emPloyee : nv) {
            if (emPloyee.getSdt().toLowerCase().contains(phoneNumber.toLowerCase())) {
              result = addEmployee(result, emPloyee);
            }
          }
        }
        case 5 -> {
          input.nextLine();
          System.out.print("Nhập chức vụ của nhân viên: ");
          String Role = input.nextLine();
          for (NhanVien emPloyee : nv) {
            if (emPloyee.getRole().toLowerCase().contains(Role.toLowerCase())) {
              result = addEmployee(result, emPloyee);
            }
          }
        }
        case 6 -> {
          input.nextLine();
          System.out.print("Nhập ca trực của nhân viên: ");
          String Shift = input.nextLine();
          for (NhanVien emPloyee : nv) {
            if (emPloyee.getShift().toLowerCase().contains(Shift.toLowerCase())) {
              result = addEmployee(result, emPloyee);
            }
          }
        }
      }
      System.out.println("\t\t\t\t\t\t\t\t +----THÔNG TIN NHÂN VIÊN TÌM ĐƯỢC----+");
      String header = String.format("| %-5s | %-25s | %-4s | %-9s | %-30s | %-25s | %-15s | %-20s | %-9s |", "ID",
          "Họ tên", "Tuổi", "Giới Tính", "Địa chỉ", "Email", "Số điện thoại", "Chức vụ", "Ca trực");
      System.out.format(
          "+-------+---------------------------+------+-----------+--------------------------------+---------------------------+-----------------+----------------------+----------+%n");
      System.out.println(header);
      System.out.format(
          "+-------+---------------------------+------+-----------+--------------------------------+---------------------------+-----------------+----------------------+----------+%n");

      for (NhanVien DSNV : result) {
        String row = String.format("| %-5s | %-25s | %-4s | %-9s | %-30s | %-25s | %-15s | %-20s | %-9s |",
            DSNV.getName(), DSNV.getAge(), DSNV.getGender(),
            DSNV.getAddress(), DSNV.getEmail(), DSNV.getWorkerId(), DSNV.getRole(), DSNV.getShift(),
            DSNV.getSdt());
        System.out.println(row);
        waitConsole();
      }
    }
  }

  public void Output() {
    System.out.println("\t\t\t\t\t\t\t\t +----DANH SÁCH NHÂN VIÊN----+");
    String header = String.format("| %-5s | %-25s | %-4s | %-9s | %-30s | %-25s | %-15s | %-20s | %-9s |", "ID",
        "Họ tên", "Tuổi", "Giới Tính", "Địa chỉ", "Email", "Số điện thoại", "Chức vụ", "Ca trực");
    System.out.format(
        "+-------+---------------------------+------+-----------+--------------------------------+---------------------------+-----------------+----------------------+----------+%n");
    System.out.println(header);
    for (NhanVien DSNV : nv) {
      String row = String.format("| %-5s | %-25s | %-4s | %-9s | %-30s | %-25s | %-15s | %-20s | %-9s |",
          DSNV.getName(), DSNV.getAge(), DSNV.getGender(),
          DSNV.getAddress(), DSNV.getEmail(), DSNV.getWorkerId(), DSNV.getRole(), DSNV.getShift(),
          DSNV.getSdt());
      System.out.println(row);
    }
  }
}