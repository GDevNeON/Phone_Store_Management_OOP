package controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import models.KhachHang;

public class QuanLyKhachHang implements ControllerInterface {
  private static QuanLyKhachHang instance;
  public static Scanner input = new Scanner(System.in);
  private KhachHang[] cs;

  private QuanLyKhachHang() {
    getListCustomer();
  }

  public static QuanLyKhachHang getInstance() {
    if (instance == null) {
      instance = new QuanLyKhachHang();
    }
    return instance;
  }

  public KhachHang[] getListCustomer() {
    String[] result = new String[0];
    try {
      result = Stream.read("Database/KhachHang.txt");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    cs = new KhachHang[result.length];
    for (int i = 0; i < result.length; i++) {
      String[] row = result[i].split(";");
      cs[i] = new KhachHang(row[0], row[1], row[2], row[3], row[4], Integer.parseInt(row[5]), row[6], row[7]);
    }
    return cs;
  }

  @Override
  public void Read() {
    System.out.println("\t\t\t\t\t\t\t\t +----DANH SÁCH KHÁCH HÀNG----+");
    String header = String.format("| %-5s | %-25s | %-4s | %-9s | %-30s | %-25s | %-15s | %-20s |", "ID", "Họ tên",
        "Tuổi", "Giới Tính", "Địa chỉ", "Email", "Số điện thoại", "Loại khách hàng");
    System.out.format(
        "+-------+---------------------------+------+-----------+--------------------------------+---------------------------+-----------------+----------------------+%n");
    System.out.println(header);
    System.out.format(
        "+-------+---------------------------+------+-----------+--------------------------------+---------------------------+-----------------+----------------------+%n");

    getListCustomer();
    for (KhachHang c : cs) {
      if (cs[0].getCustomerId().contains("kh")) {
        String read = String.format("| %-5s | %-25s | %-4s | %-9s | %-30s | %-25s | %-15s | %-20s |",
            c.getCustomerId(), c.getName(), c.getAge(), c.getGender(), c.getAddress(), c.getEmail(),
            c.getSdt(), c.getKindOfCustomer());
        System.out.println(read);
      }
    }
    System.out.format(
        "+-------+---------------------------+------+-----------+--------------------------------+---------------------------+-----------------+----------------------+%n");
    waitConsole();
  }

  @Override
  public void Create() {
    boolean checkIDexist;
    KhachHang khachHang = new KhachHang();
    do {
      checkIDexist = false;
      System.out.println("\t\t\t\t\t\t\t\t ----NHẬP THÔNG TIN KHÁCH HÀNG----");
      System.out.print("Nhập ID khách hàng: ");
      khachHang.setCustomerId(input.nextLine());

      for (KhachHang customer : cs) {
        if (customer != null) {
          if (khachHang.getCustomerId().equals(customer.getCustomerId())) {
            System.out.println("\t\t\t\t\t\t\t\t -MÃ KHÁCH HÀNG BỊ TRÙNG!");
            checkIDexist = true;
            break;

          }
        }
      }
    } while (checkIDexist);
    khachHang.AddThongTin();

    try {
      String input = khachHang.getCustomerId() + ";" + khachHang.getName() + ";" + khachHang.getAge() + ";"
          + khachHang.getGender() + ";" + khachHang.getAddress()
          + ";" + khachHang.getEmail() + ";" + khachHang.getSdt() + ";" + khachHang.getKindOfCustomer();
      Stream.addOneLine("Database/KhachHang.txt", input); // link database here
      System.out.println("Nhập khách hàng thành công");
      waitConsole();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void Update() {
    System.out.print("Nhập ID khách hàng cần chỉnh sửa: ");
    String ID_KhachHang = input.nextLine();
    KhachHang k_hang = null;

    for (KhachHang KhachHang : cs) {
      if (KhachHang.getCustomerId().equals(ID_KhachHang)) {
        k_hang = KhachHang;
        break;
      }
    }

    if (k_hang == null) {
      System.out.println("ID khách hàng không tồn tại!");
      return;
    }

    System.out.println("Thông tin khách hàng: ");
    System.out.println("\t\t\t\t\t\t\t\t +----DANH SÁCH KHÁCH HÀNG----+");
    String header = String.format("| %-5s | %-25s | %-4s | %-9s | %-30s | %-25s | %-15s | %-20s |", "ID", "Họ tên",
        "Tuổi", "Giới Tính", "Địa chỉ", "Email", "Số điện thoại", "Loại khách hàng");
    System.out.format(
        "+-------+---------------------------+------+-----------+--------------------------------+---------------------------+-----------------+----------------------+%n");
    System.out.println(header);
    System.out.format(
        "+-------+---------------------------+------+-----------+--------------------------------+---------------------------+-----------------+----------------------+%n");
    String row = String.format("| %-5s | %-25s | %-4s | %-9s | %-30s | %-25s | %-15s | %-20s |",
        k_hang.getCustomerId(), k_hang.getName(), k_hang.getAge(), k_hang.getGender(),
        k_hang.getAddress(), k_hang.getEmail(), k_hang.getSdt(), k_hang.getKindOfCustomer());
    System.out.println(row);
    System.out.format(
        "+-------+---------------------------+------+-----------+--------------------------------+---------------------------+-----------------+----------------------+%n");

    String[] data = new String[cs.length];

    for (int i = 0; i < cs.length; i++) {
      if (ID_KhachHang.equals(cs[i].getCustomerId())) {
        System.out.println("Nhập thông tin khách hàng:");
        k_hang.AddThongTin();

        cs[i].setName(k_hang.getName());
        cs[i].setGender(k_hang.getGender());
        cs[i].setAddress(k_hang.getAddress());
        cs[i].setEmail(k_hang.getEmail());
        cs[i].setAge(k_hang.getAge());
        cs[i].setSdt(k_hang.getSdt());
        cs[i].setKindOfCustomer(k_hang.getKindOfCustomer());
      }
      data[i] = cs[i].getCustomerId() + ";" + cs[i].getName() + ";" + cs[i].getAge() + ";" + cs[i].getGender()
          + ";" + cs[i].getAddress() + ";" + cs[i].getEmail() + ";" + cs[i].getSdt() + ";"
          + cs[i].getKindOfCustomer();
    }
    try {
      Stream.addAll("Database/KhachHang.txt", data); // link database here
      System.out.println("Sửa thông tin khách hàng thành công");
      waitConsole();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void Delete() {
    System.out.print("Nhập ID khách hàng cần xóa: ");
    String ID_KhachHang = input.nextLine();
    KhachHang k_hang = null;

    for (KhachHang KhachHang : cs) {
      if (KhachHang.getCustomerId().equals(ID_KhachHang)) {
        k_hang = KhachHang;
        break;
      }
    }

    if (k_hang == null) {
      System.out.println("ID khách hàng không tồn tại. Xin vui lòng nhập lại!");
      return;
    }

    for (int i = 0; i < cs.length; i++) {
      if (ID_KhachHang.equals(cs[i].getCustomerId())) {
        cs = deleteCustomer(cs, i);
        break;
      }
    }
    String[] data = new String[cs.length];
    for (int i = 0; i < cs.length; i++) {
      data[i] = cs[i].getCustomerId() + ";" + cs[i].getName() + ";" + cs[i].getAge() + ";" + cs[i].getGender()
          + ";" + cs[i].getAddress() + ";" + cs[i].getEmail() + ";" + cs[i].getSdt() + ";"
          + cs[i].getKindOfCustomer();
    }
    try {
      Stream.addAll("Database/KhachHang.txt", data); // link database here
      System.out.println("Xóa khách hàng thành công");
      waitConsole();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public KhachHang[] deleteCustomer(KhachHang[] cs, int index) {
    KhachHang[] newCs = new KhachHang[cs.length - 1];
    for (int i = 0, j = 0; i < cs.length; i++) {
      if (i != index) {
        newCs[j++] = cs[i];
      }
    }
    return newCs;
  }

  public KhachHang[] addCustomer(KhachHang[] cs, KhachHang customer) {
    cs = Arrays.copyOf(cs, cs.length + 1);
    cs[cs.length - 1] = customer;
    return cs;
  }

  @Override
  public void searchByCategory() {
    KhachHang[] result = new KhachHang[0];
    System.out.println("Nhập mục lục cần tìm kiếm: ");
    System.out.println("1.Mã khách hàng");
    System.out.println("2.Tên khách hàng");
    System.out.println("3.Địa chỉ khách hàng");
    System.out.println("4.Số điện thoại khách hàng");
    System.out.print("Nhập lựa chọn: ");
    int choose = input.nextInt();

    switch (choose) {
      case 1 -> {
        System.out.print("Nhập ID khách hàng: ");
        String Customer_ID = input.nextLine();
        for (int i = 0; i < cs.length; i++) {
          if (Customer_ID.equals(cs[i].getCustomerId())) {
            result = addCustomer(result, cs[i]);
          }
        }
      }
      case 2 -> {
        input.nextLine();
        System.out.print("Nhập tên khách hàng: ");
        String nameCustomer = input.nextLine();
        for (KhachHang KhachHang : cs) {
          if (KhachHang.getName().toLowerCase().contains(nameCustomer.toLowerCase())) {
            result = addCustomer(result, KhachHang);
          }
        }
      }
      case 3 -> {
        input.nextLine();
        System.out.print("Nhập địa chỉ của khách hàng: ");
        String address = input.nextLine();
        for (KhachHang KhachHang : cs) {
          if (KhachHang.getAddress().toLowerCase().contains(address.toLowerCase())) {
            result = addCustomer(result, KhachHang);
          }
        }
      }
      case 4 -> {
        input.nextLine();
        System.out.print("Nhập số điện thoại của khách hàng: ");
        String phoneNumber = input.nextLine();
        for (KhachHang KhachHang : cs) {
          if (KhachHang.getSdt().toLowerCase().contains(phoneNumber.toLowerCase())) {
            result = addCustomer(result, KhachHang);
          }
        }
      }
    }

    System.out.println("Danh sách khách hàng tìm được:");
    String header = String.format("| %-5s | %-25s | %-4s | %-9s | %-30s | %-25s | %-15s | %-20s |", "ID", "Họ tên",
        "Tuổi", "Giới Tính", "Địa chỉ", "Email", "Số điện thoại", "Loại khách hàng");
    System.out.format(
        "+-------+---------------------------+------+-----------+--------------------------------+---------------------------+-----------------+----------------------+%n");
    System.out.println(header);
    System.out.format(
        "+-------+---------------------------+------+-----------+--------------------------------+---------------------------+-----------------+----------------------+%n");
    for (KhachHang customer : result) {
      String read = String.format("| %-5s | %-25s | %-4s | %-9s | %-30s | %-25s | %-15s | %-20s |",
          customer.getCustomerId(), customer.getName(), customer.getAge(), customer.getGender(),
          customer.getAddress(), customer.getEmail(), customer.getSdt(), customer.getKindOfCustomer());
      System.out.println(read);
    }
    System.out.format(
        "+-------+---------------------------+------+-----------+--------------------------------+---------------------------+-----------------+----------------------+%n");
    waitConsole();
  }

  public void waitConsole() {
    System.out.println("Ấn Enter để tiếp tục");
    input.nextLine();
  }
}