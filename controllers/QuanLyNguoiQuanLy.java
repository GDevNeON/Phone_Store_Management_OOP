package controllers;

import java.io.IOException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

import models.QuanLy;

public class QuanLyNguoiQuanLy implements ControllerInterface {
  private static QuanLyNguoiQuanLy instance;
  private static Scanner sc = new Scanner(System.in);
  private QuanLy[] manager;

  private QuanLyNguoiQuanLy() {
    getListManagers();
  }

  public static QuanLyNguoiQuanLy getInstance() {
    if (instance == null) {
      instance = new QuanLyNguoiQuanLy();
    }
    return instance;
  }

  // Lấy danh sách người quản lý từ file
  public QuanLy[] getListManagers() {
    String[] result = new String[0];
    try {
      result = Stream.read("Database/NguoiQuanLy.txt");
    } catch (IOException e) {
      e.printStackTrace();
    }

    manager = new QuanLy[result.length];
    for (int i = 0; i < result.length; i++) {
      String[] row = result[i].split(";");
      manager[i] = new QuanLy(row[0], row[1], row[2], row[3], row[4], Integer.parseInt(row[5]), row[6], row[7],
          row[8], Integer.parseInt(row[9]));
    }
    return manager;
  }

  @Override
  public void Read() {
    System.out.println("\t\t\t\t\t\t\t\t +----DANH SÁCH NGƯỜI QUẢN LÝ----+");
    String header = String.format("| %-5s | %-20s | %-4s | %-9s | %-30s | %-25s | %-10s | %-20s | %-11s |",
        "ID",
        "Họ tên",
        "Tuổi",
        "Giới Tính",
        "Địa chỉ",
        "Email",
        "Số điện thoại",
        "Chức vụ",
        "Ca trực");
    System.out.format(
        "+-------+----------------------+------+-----------+--------------------------------+---------------------------+---------------+----------------------+-------------+%n");
    System.out.println(header);
    System.out.format(
        "+-------+----------------------+------+-----------+--------------------------------+---------------------------+---------------+----------------------+-------------+%n");
    getListManagers();
    for (QuanLy m : manager) {
      String read = String.format("| %-5s | %-20s | %-4s | %-9s | %-30s | %-25s | %-13s | %-20s | %-11s |",
          m.getManagerId(),
          m.getName(),
          m.getAge(),
          m.getGender(),
          m.getAddress(),
          m.getEmail(),
          m.getSdt(),
          m.getRole(),
          m.getShift());
      System.out.println(read);
    }
    System.out.format(
        "+-------+----------------------+------+-----------+--------------------------------+---------------------------+---------------+----------------------+-------------+%n");
    waitConsole();
  }

  @Override
  public void Create() {
    System.out.println("\t\t\t\t\t\t\t\t +----NHẬP THÔNG TIN NGƯỜI QUẢN LÝ----+");
    QuanLy qly = new QuanLy();
    System.out.println("Nhập ID người quản lý (ql_): ");
    qly.setManagerId(sc.nextLine());
    int check = 0;
    for (QuanLy m : manager) {
      if (qly.getManagerId().equals(m.getManagerId())) {
        check = 1;
        break;
      }
    }
    if (check == 1) {
      System.out.println("\t\t\t\t\t\t\t\t +-----MÃ NGƯỜI QUẢN LÝ BỊ TRÙNG-----+");
      return;
    }

    qly.AddThongTin();

    try {
      String inputStringData = qly.getManagerId() + ";" +
          qly.getName() + ";" +
          qly.getAge() + ";" +
          qly.getGender() + ";" +
          qly.getAddress() + ";" +
          qly.getEmail() + ";" +
          qly.getSdt() + ";" +
          qly.getRole() + ";" +
          qly.getShift();
      Stream.addOneLine("Database/NguoiQuanLy.txt", inputStringData);
      System.out.println("\t\t\t\t\t\t\t\t +----NHẬP THÔNG TIN NGƯỜI QUẢN LÝ THÀNH CÔNG----+");
      waitConsole();
    } catch (IOException e) {
      e.printStackTrace();
    }

    getListManagers();
  }

  @Override
  public void Update() {
    try {
      System.out.println("\t\t\t\t\t\t\t\t +----CẬP NHẬT THÔNG TIN NGƯỜI QUẢN LÝ----+");
      System.out.print("-Mời nhập ID của người quản lý cần sửa: ");
      String ID_Manager = sc.nextLine();
      QuanLy id = null;

      for (QuanLy m : manager) {
        if (m.getManagerId().equals(ID_Manager)) {
          id = m;
          break;
        }
      }
      if (id == null) {
        System.out.println("\t\t\t\t\t\t\t\t +-----MÃ NGƯỜI QUẢN LÝ KHÔNG TỒN TẠI-----+");
        return;
      }

      System.out.println("1. Sửa 1 phần của dòng");
      System.out.println("2. Sửa toàn bộ dòng");
      System.out.print("Nhập số 1 hoặc 2: ");
      int choose = sc.nextInt();
      while (true) {
        if (choose < 1 || choose > 2) {
          System.out.print("Nhập lại: ");
          choose = sc.nextInt();
        } else {
          break;
        }
      }

      System.out.println("\t\t\t\t\t\t\t\t +----THÔNG TIN NGƯỜI QUẢN LÝ TRƯỚC KHI CHỈNH SỬA----+");
      String header = String.format("| %-5s | %-20s | %-4s | %-9s | %-30s | %-25s | %-13s | %-20s | %-11s |",
          "ID",
          "Họ tên",
          "Tuổi",
          "Giới Tính",
          "Địa chỉ",
          "Email",
          "Số điện thoại",
          "Chức vụ",
          "Ca trực");
      System.out.format(
          "+-------+----------------------+------+-----------+--------------------------------+---------------------------+---------------+----------------------+-------------+%n");
      System.out.println(header);
      System.out.format(
          "+-------+----------------------+------+-----------+--------------------------------+---------------------------+---------------+----------------------+-------------+%n");
      String row = String.format("| %-5s | %-20s | %-4s | %-9s | %-30s | %-25s | %-13s | %-20s | %-11s |",
          id.getManagerId(),
          id.getName(),
          id.getAge(),
          id.getGender(),
          id.getAddress(),
          id.getEmail(),
          id.getSdt(),
          id.getRole(),
          id.getShift());
      System.out.println(row);
      System.out.format(
          "+-------+----------------------+------+-----------+--------------------------------+---------------------------+---------------+----------------------+-------------+%n");

      if (choose == 1) {
        System.out.println("\t\t\t\t\t\t\t\t +----------NHẬP MỤC LỤC CẨN SỬA-----------+");
        System.out.println("\t\t\t\t\t\t\t\t |0. Thoát                                 |");
        System.out.println("\t\t\t\t\t\t\t\t +-----------------------------------------+");
        System.out.println("\t\t\t\t\t\t\t\t |1. ID người quản lý                      |");
        System.out.println("\t\t\t\t\t\t\t\t |2. Vai trò                               |");
        System.out.println("\t\t\t\t\t\t\t\t |3. Ca làm                                |");
        System.out.println("\t\t\t\t\t\t\t\t +-----------------------------------------+");
        System.out.print("\t\t\t\t\t\t\t\t - Mời Bạn Nhập Lựa Chọn: ");
        int index = sc.nextInt();
        while (true) {
          if (index < 0 || index > 3) {
            System.out.print("Nhập lại: ");
            index = sc.nextInt();
          } else {
            break;
          }
        }

        String[] data = new String[manager.length];

        for (int i = 0; i < manager.length; i++) {
          if (manager[i].getManagerId().equals(ID_Manager)) {
            System.out.println("Nhập thông tin người quản lý:");
            switch (index) {
              case 0:
                return;
              case 1:
                System.out.println("Nhập ID người quản lý (ql_): ");
                sc.nextLine();
                id.setManagerId(sc.nextLine());
                int check = 0;
                for (QuanLy m : manager) {
                  if (id.getManagerId().equals(m.getManagerId())) {
                    check = 1;
                    break;
                  }
                }
                if (check == 1) {
                  System.out.println("\t\t\t\t\t\t\t\t +-----MÃ NGƯỜI QUẢN LÝ BỊ TRÙNG-----+");
                  return;
                }
                manager[i].setManagerId(id.getManagerId());
                break;
              case 2:
                System.out.println("Nhập vai trò: ");
                sc.nextLine();
                id.setRole(sc.nextLine());
                manager[i].setRole(id.getRole());
                break;
              case 3:
                System.out.println("Nhập ca làm: ");
                sc.nextLine();
                id.setShift(sc.nextLine());
                manager[i].setShift(id.getShift());
                break;
            }
          }
          data[i] = manager[i].getManagerId() + ";" +
              manager[i].getName() + ";" +
              manager[i].getAge() + ";" +
              manager[i].getGender() + ";" +
              manager[i].getAddress() + ";" +
              manager[i].getEmail() + ";" +
              manager[i].getSdt() + ";" +
              manager[i].getRole() + ";" +
              manager[i].getShift();
        }
        try {
          Stream.addAll("Database/NguoiQuanLy.txt", data);
          System.out.println("\t\t\t\t\t\t\t\t+----SỬA THÔNG TIN SẢN PHẨM THÀNH CÔNG----+");
          waitConsole();
        } catch (IOException e) {
          e.printStackTrace();
        }

      } else {
        String[] data = new String[manager.length];

        for (int i = 0; i < manager.length; i++) {
          if (manager[i].getManagerId().equals(ID_Manager)) {
            System.out.println("Nhập thông tin người quản lý:");
            id.AddThongTin();
            int check = 0;
            for (QuanLy m : manager) {
              if (id.getManagerId().equals(m.getManagerId())) {
                check = 1;
                break;
              }
            }
            if (check == 1) {
              System.out.println("\t\t\t\t\t\t\t\t +-----MÃ NGƯỜI QUẢN LÝ BỊ TRÙNG-----+");
              return;
            }
            manager[i].setManagerId(id.getManagerId());
            manager[i].setName(id.getName());
            manager[i].setAge(id.getAge());
            manager[i].setGender(id.getGender());
            manager[i].setAddress(id.getAddress());
            manager[i].setEmail(id.getEmail());
            manager[i].setSdt(id.getSdt());
            manager[i].setRole(id.getRole());
            manager[i].setShift(id.getShift());
          }
          data[i] = manager[i].getManagerId() + ";" +
              manager[i].getName() + ";" +
              manager[i].getAge() + ";" +
              manager[i].getGender() + ";" +
              manager[i].getAddress() + ";" +
              manager[i].getEmail() + ";" +
              manager[i].getSdt() + ";" +
              manager[i].getRole() + ";" +
              manager[i].getShift();
        }
        try {
          Stream.addAll("Database/NguoiQuanLy.txt", data);
          System.out.println("\t\t\t\t\t\t\t\t+----SỬA THÔNG TIN SẢN PHẨM THÀNH CÔNG----+");
          waitConsole();
        } catch (IOException e) {
          e.printStackTrace();
        }
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
      System.out.println("\t\t\t\t\t\t\t\t +----XÓA THÔNG TIN NGƯỜI QUẢN LÝ----+");
      System.out.println("Nhập ID người quản lý cần xóa: ");
      String ID_Manager = sc.nextLine();

      QuanLy id = null;
      for (QuanLy m : manager) {
        if (m.getManagerId().equals(ID_Manager)) {
          id = m;
          break;
        }
      }

      if (id == null) {
        System.out.println("\t\t\t\t\t\t\t\t +-----MÃ NGƯỜI QUẢN LÝ KHÔNG TỒN TẠI-----+");
        return;
      }

      for (int i = 0; i < manager.length; i++) {
        if (ID_Manager.equals(manager[i].getManagerId())) {
          manager = deleteManager(manager, i);
          break;
        }
      }

      // Cập nhật lại toàn bộ list vào file
      updateList(1, manager);
    } catch (InputMismatchException ei) {
      System.out.println("\t\t\t\t\t\t\t\t GIÁ TRỊ KHÔNG HỢP LỆ. VUI LÒNG NHẬP LẠI!");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public QuanLy[] deleteManager(QuanLy[] manager, int index) {
    QuanLy[] newCs = new QuanLy[manager.length - 1];
    for (int i = 0, j = 0; i < manager.length; i++) {
      if (i != index) {
        newCs[j++] = manager[i];
      }
    }
    return newCs;
  }

  public QuanLy[] addManager(QuanLy[] manager, QuanLy nql) {
    manager = Arrays.copyOf(manager, manager.length + 1);
    manager[manager.length - 1] = nql;
    return manager;
  }

  @Override
  public void searchByCategory() {
    String find;
    System.out.println("\t\t\t\t\t\t\t\t +--------NHẬP MỤC LỤC CẨN TÌM KIẾM--------+");
    System.out.println("\t\t\t\t\t\t\t\t |0. Thoát                                 |");
    System.out.println("\t\t\t\t\t\t\t\t +-----------------------------------------+");
    System.out.println("\t\t\t\t\t\t\t\t |1. ID người quản lý                      |");
    System.out.println("\t\t\t\t\t\t\t\t |2. Vai trò                               |");
    System.out.println("\t\t\t\t\t\t\t\t |3. Ca làm                                |");
    System.out.println("\t\t\t\t\t\t\t\t +-----------------------------------------+");
    System.out.print("\t\t\t\t\t\t\t\t - Mời Bạn Nhập Lựa Chọn: ");
    int index = sc.nextInt();

    while (true) {
      if (index < 1 || index > 3) {
        System.out.print("Nhập lại: ");
        index = sc.nextInt();
      } else {
        break;
      }
    }

    System.out.print("Nhập nội dung cần tìm: ");
    sc.nextLine();
    find = sc.nextLine();

    System.out.println("\t\t\t\t\t\t\t\t +----THÔNG TIN NGƯỜI QUẢN LÝ TÌM ĐƯỢC----+");
    String header = String.format("| %-5s | %-20s | %-4s | %-9s | %-30s | %-25s | %-13s | %-20s | %-11s |",
        "ID",
        "Họ tên",
        "Tuổi",
        "Giới Tính",
        "Địa chỉ",
        "Email",
        "Số điện thoại",
        "Chức vụ",
        "Ca trực",
        "SLNVQL");
    System.out.format(
        "+-------+----------------------+------+-----------+--------------------------------+---------------------------+---------------+----------------------+-------------+%n");
    System.out.println(header);
    System.out.format(
        "+-------+----------------------+------+-----------+--------------------------------+---------------------------+---------------+----------------------+-------------+%n");

    for (int i = 0; i < manager.length; i++) {
      switch (index) {
        case 0:
          return;
        case 1:
          if (manager[i].getManagerId().equals(find))
            OutputData(i);
          break;
        case 2:
          if (manager[i].getRole().equals(find))
            OutputData(i);
          break;
        case 3:
          if (manager[i].getShift().equals(find))
            OutputData(i);
          break;
      }
    }
    System.out.format(
        "+-------+----------------------+------+-----------+--------------------------------+---------------------------+---------------+----------------------+-------------+%n");
  }

  public void OutputData(int i) {
    String row = String.format("| %-5s | %-20s | %-4s | %-9s | %-30s | %-25s | %-13s | %-20s | %-11s |",
        manager[i].getManagerId(),
        manager[i].getName(),
        manager[i].getAge(),
        manager[i].getGender(),
        manager[i].getAddress(),
        manager[i].getEmail(),
        manager[i].getSdt(),
        manager[i].getRole(),
        manager[i].getShift());
    System.out.println(row);
  }

  public String[] stringToInputInFile(QuanLy[] manager) {
    String[] data = new String[manager.length];

    for (int i = 0; i < manager.length; i++) {
      data[i] = manager[i].getManagerId() + ";" +
          manager[i].getName() + ";" +
          manager[i].getAge() + ";" +
          manager[i].getGender() + ";" +
          manager[i].getAddress() + ";" +
          manager[i].getEmail() + ";" +
          manager[i].getSdt() + ";" +
          manager[i].getRole() + ";" +
          manager[i].getShift();
    }

    return data;
  }

  public void updateList(int select, QuanLy[] manager) {
    switch (select) {
      case 0:
        try {
          String[] data = stringToInputInFile(manager);
          Stream.addAll("Database/NguoiQuanLy.txt", data);
          System.out.println("\t\t\t\t\t\t\t\t +----NHẬP THÔNG TIN NGƯỜI QUẢN LÝ THÀNH CÔNG----+");
          waitConsole();
        } catch (IOException e) {
          e.printStackTrace();
        }
        break;

      case 1:
        String[] data = stringToInputInFile(manager);

        try {
          Stream.addAll("Database/NguoiQuanLy.txt", data);
          System.out.println("\t\t\t\t\t\t\t\t +----SỬA THÔNG TIN QUẢN LÝ THÀNH CÔNG----+");
          waitConsole();
        } catch (IOException e) {
          e.printStackTrace();
        }
        break;
    }
  }

  public int getListLength(QuanLy[] manager) {
    return manager.length;
  }

  public void waitConsole() {
    System.out.println("\t\t\t\t\t\t\t\t - Ấn Enter để tiếp tục");
    sc.nextLine();
  }
}