package controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

import models.TaiKhoan;

public class QuanLyTaiKhoan implements ControllerInterface {
  private static QuanLyTaiKhoan instance;
  private static Scanner sc = new Scanner(System.in);
  private TaiKhoan[] dsAccount;
  private Validation kiemTra = new Validation();

  public static QuanLyTaiKhoan getInstance() {
    if (instance == null) {
      instance = new QuanLyTaiKhoan();
    }
    return instance;
  }

  private QuanLyTaiKhoan() {
    getListAccount();
  }

  public TaiKhoan[] getListAccount() {
    String[] result = new String[0];
    try {
      result = Stream.read("Database/TaiKhoan.txt");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    dsAccount = new TaiKhoan[result.length];
    for (int i = 0; i < result.length; i++) {
      String[] row = result[i].split(";");
      dsAccount[i] = new TaiKhoan(row[0], row[1], row[2], row[3]);
    }
    return dsAccount;
  }

  public void waitConsole() {
    System.out.println("\t\t\t\t\t\t\t\t -Ấn Enter để tiếp tục");
    sc.nextLine();
  }

  @Override
  public void Read() {
    System.out.println("\t\t\t\t\t\t\t\t +----DANH SÁCH TÀI KHOẢN----+");
    String header = String.format("| %-8s | %-30s | %-10s | %-25s |", "ID", "Tên", "Mật khẩu", "Chức vụ");
    System.out.format("+----------+--------------------------------+------------+---------------------------+%n");
    System.out.println(header);
    System.out.format("+----------+--------------------------------+------------+---------------------------+%n");

    getListAccount();

    for (TaiKhoan ac : dsAccount) {
      String read = String.format("| %-8s | %-30s | %-10s | %-25s |",
          ac.getAccountId(), ac.getUsername(), ac.getPassword(), ac.getPosition());
      System.out.println(read);
    }
    System.out.format("+----------+--------------------------------+------------+---------------------------+%n");
    waitConsole();
  }

  @Override
  public void Create() {
    System.out.println("\t\t\t\t\t\t\t\t +----NHẬP THÔNG TIN TÀI KHOẢN----+");
    TaiKhoan taiKhoan = new TaiKhoan();
    System.out.print("Nhập ID tài khoản: ");
    String ID_Account = sc.nextLine();
    while (ID_Account.isEmpty() || !kiemTra.isValidAccountId(ID_Account) ){
        System.out.print("Không đúng định dạng! Vui lòng nhập lại ID tài khoản: ");
        ID_Account = sc.nextLine();
        
    }
    taiKhoan.setAccountId(ID_Account);
    int check = 0;
    for (TaiKhoan ac : dsAccount) {
      if (taiKhoan.getAccountId().equals(ac.getAccountId())) {
        check = 1;
        break;
      }
    }

    if (check == 1) {
      System.out.println("\t\t\t\t\t\t\t\t MÃ TÀI KHOẢN BỊ TRÙNG!");
      return;
    }

    System.out.print("Nhập tên tài khoản: ");
    String name;
    name = sc.nextLine();
    while (name.isEmpty() || !kiemTra.isValidUsername(name)) {
        System.out.print("Không đúng định dạng! Vui lòng nhập lại tên tài khoản: ");
        name = sc.nextLine();
    }
    taiKhoan.setUsername(name);

    System.out.print("Nhập mật khẩu tài khoản: ");
    String password;
    password = sc.nextLine();
    while (password.isEmpty()){
        System.out.print("Không được để trống! Vui lòng nhập mật khẩu: ");
        password = sc.nextLine();
    }
    taiKhoan.setPassword(password);

    System.out.print("Nhập chức vụ: ");
    String position;
    position = sc.nextLine();
    while (position.isEmpty() || !kiemTra.isValidPosition(position)) {
        System.out.print("Không đúng định dạng! Vui lòng nhập lại chức vụ:");
        position = sc.nextLine();
    }
    taiKhoan.setPosition(position);

    try {
      String sc = taiKhoan.getAccountId() + ";" + taiKhoan.getUsername() + ";" + taiKhoan.getPassword() + ";"
          + taiKhoan.getPosition();
      Stream.addOneLine("Database/TaiKhoan.txt", sc);
      System.out.print("\t\t\t\t\t\t\t\t +----NHẬP TÀI KHOẢN THÀNH CÔNG----+\n");
      waitConsole();
    } catch (IOException e) {
      e.printStackTrace();
    }
    getListAccount();
  }

  public void Update() {
    try {
      System.out.println("\t\t\t\t\t\t\t\t +----CẬP NHẬT LẠI THÔNG TIN TÀI KHOẢN----+");
      System.out.print("- Mời bạn nhập mã tài khoản chỉnh sửa: ");
      String account_id = sc.nextLine();
      while (account_id.isEmpty() || !kiemTra.isValidAccountId(account_id)) {
          System.out.print("Không đúng định dạng! Vui lòng nhập lại ID tài khoản: ");
          account_id = sc.nextLine();
      }
      TaiKhoan act = null;

      for (TaiKhoan ac : dsAccount) {
        if (ac.getAccountId().equals(account_id)) {
          act = ac;
          break;
        }
      }

      if (act == null) {
        System.out.println("\t\t\t\t\t\t\t\t MÃ TÀI KHOẢN KHÔNG TỒN TẠI!");
        return;
      }

      System.out.println("\t\t\t\t\t\t\t\t +----THÔNG TIN TÀI KHOẢN TRƯỚC KHI CHỈNH SỬA----+");
      String header = String.format("| %-8s | %-30s | %-10s | %-25s |", "ID", "Tên", "Mật khẩu", "Chức vụ");
      System.out
          .format("+----------+--------------------------------+------------+---------------------------+%n");
      System.out.println(header);
      System.out
          .format("+----------+--------------------------------+------------+---------------------------+%n");

      String row = String.format("| %-8s | %-30s | %-10s | %-25s |", act.getAccountId(), act.getUsername(),
          act.getPassword(), act.getPosition());
      System.out.println(row);
      System.out
          .format("+----------+--------------------------------+------------+---------------------------+%n");

      String[] data = new String[dsAccount.length];

      System.out.println("\t\t\t\t\t\t\t\t +-----SỬA THÔNG TIN TÀI KHOẢN-----+");
      System.out.println("\t\t\t\t\t\t\t\t |0.Thoát                          |");
      System.out.println("\t\t\t\t\t\t\t\t +---------------------------------+");
      System.out.println("\t\t\t\t\t\t\t\t |1.Sửa ID tài khoản               |");
      System.out.println("\t\t\t\t\t\t\t\t |2.Sửa tên tài khoản              |");
      System.out.println("\t\t\t\t\t\t\t\t |3.Sửa mật khẩu tài khoản         |");
      System.out.println("\t\t\t\t\t\t\t\t |4.Sửa chức vụ tài khoản          |");
      System.out.println("\t\t\t\t\t\t\t\t |5.Sửa tất cả thông tin           |");
      System.out.println("\t\t\t\t\t\t\t\t +---------------------------------+");
      System.out.print("\t\t\t\t\t\t\t\t Mời bạn nhập lựa chọn: ");
      String choose = sc.nextLine();
        switch (choose) {
          case "0" -> {
            System.out.println("\t\t\t\t\t\t\t\t +----THOÁT CHƯƠNG TRÌNH THÀNH CÔNG!----+");
            choose = "0";
          }
          case "1" -> {
            for (int i = 0; i < dsAccount.length; i++) {
              if (dsAccount[i].getAccountId().equals(account_id)) {
                System.out.print("Nhập ID tài khoản: ");
                String ID_Account;
                ID_Account = sc.nextLine();
                while (ID_Account.isEmpty() || !kiemTra.isValidAccountId(ID_Account) ){
                    System.out.print("Không đúng định dạng! Vui lòng nhập lại ID tài khoản: ");
                    ID_Account = sc.nextLine();
                    
                }
                act.setAccountId(ID_Account);
                int check = 0;
                for (TaiKhoan ac : dsAccount) {
                  if (act.getAccountId().equals(ac.getAccountId())) {
                    check = 1;
                    break;
                  }
                }
                if (check == 1) {
                  System.out.println("\t\t\t\t\t\t\t\t MÃ TÀI KHOẢN BỊ TRÙNG!");
                  return;
                }
                dsAccount[i].setAccountId(act.getAccountId());
              }
              data[i] = dsAccount[i].getAccountId() + ";" + dsAccount[i].getUsername() + ";"
                  + dsAccount[i].getPassword() + ";" + dsAccount[i].getPosition();
            }
            try {
              Stream.addAll("Database/TaiKhoan.txt", data);
              System.out.println("\t\t\t\t\t\t\t\t +----SỬA THÔNG TIN TÀI KHOẢN THÀNH CÔNG----+");
              waitConsole();
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
          case "2" -> {
            for (int i = 0; i < dsAccount.length; i++) {
              if (dsAccount[i].getAccountId().equals(account_id)) {
                System.out.print("Nhập tên tài khoản: ");
                String name;
                name = sc.nextLine();
                while (name.isEmpty() || !kiemTra.isValidUsername(name)) {
                    System.out.print("Không đúng định dạng! Vui lòng nhập lại tên tài khoản: ");
                    name = sc.nextLine();
                }
                act.setUsername(name);
                dsAccount[i].setUsername(act.getUsername());
              }
              data[i] = dsAccount[i].getAccountId() + ";" + dsAccount[i].getUsername() + ";"
                  + dsAccount[i].getPassword() + ";" + dsAccount[i].getPosition();
            }
            try {
              Stream.addAll("Database/TaiKhoan.txt", data);
              System.out.println("\t\t\t\t\t\t\t\t +----SỬA THÔNG TIN TÀI KHOẢN THÀNH CÔNG----+");
              waitConsole();
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
          case "3" -> {
            for (int i = 0; i < dsAccount.length; i++) {
              if (dsAccount[i].getAccountId().equals(account_id)) {
                System.out.print("Nhập mật khẩu tài khoản: ");
                String password;
                password = sc.nextLine();
                while (password.isEmpty()){
                    System.out.print("Không được để trống! Vui lòng nhập mật khẩu: ");
                    password = sc.nextLine();
                }
                act.setPassword(password);

                dsAccount[i].setPassword(act.getPassword());
              }
              data[i] = dsAccount[i].getAccountId() + ";" + dsAccount[i].getUsername() + ";"
                  + dsAccount[i].getPassword() + ";" + dsAccount[i].getPosition();
            }
            try {
              Stream.addAll("Database/TaiKhoan.txt", data);
              System.out.println("\t\t\t\t\t\t\t\t +----SỬA THÔNG TIN TÀI KHOẢN THÀNH CÔNG----+");
              waitConsole();
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
          case "4" -> {
            for (int i = 0; i < dsAccount.length; i++) {
              if (dsAccount[i].getAccountId().equals(account_id)) {
                System.out.print("Nhập chức vụ: ");
                String position;
                position = sc.nextLine();
                while (position.isEmpty() || !kiemTra.isValidPosition(position)) {
                    System.out.print("Không đúng định dạng! Vui lòng nhập lại chức vụ:");
                    position = sc.nextLine();
                }
                act.setPosition(position);
                dsAccount[i].setPosition(act.getPosition());
              }
              data[i] = dsAccount[i].getAccountId() + ";" + dsAccount[i].getUsername() + ";"
                  + dsAccount[i].getPassword() + ";" + dsAccount[i].getPosition();
            }
            try {
              Stream.addAll("Database/TaiKhoan.txt", data);
              System.out.println("\t\t\t\t\t\t\t\t +----SỬA THÔNG TIN TÀI KHOẢN THÀNH CÔNG----+");
              waitConsole();
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
          case "5" -> {
            for (int i = 0; i < dsAccount.length; i++) {
              if (dsAccount[i].getAccountId().equals(account_id)) {
                System.out.print("Nhập ID tài khoản: ");
                String ID_Account;
                ID_Account = sc.nextLine();
                while (ID_Account.isEmpty() || !kiemTra.isValidAccountId(ID_Account) ){
                    System.out.print("Không đúng định dạng! Vui lòng nhập lại ID tài khoản: ");
                    ID_Account = sc.nextLine();
                    
                }
                act.setAccountId(ID_Account);
                int check = 0;
                for (TaiKhoan ac : dsAccount) {
                  if (act.getAccountId().equals(ac.getAccountId())) {
                    check = 1;
                    break;
                  }
                }

                if (check == 1) {
                  System.out.println("\t\t\t\t\t\t\t\t MÃ TÀI KHOẢN BỊ TRÙNG!");
                  return;
                }

                System.out.print("Nhập tên tài khoản: ");
                String name;
                name = sc.nextLine();
                while (name.isEmpty() || !kiemTra.isValidUsername(name)) {
                    System.out.print("Không đúng định dạng! Vui lòng nhập lại tên tài khoản: ");
                    name = sc.nextLine();
                }
                act.setUsername(sc.nextLine());

                System.out.print("Nhập mật khẩu tài khoản: ");
                String password;
                password = sc.nextLine();
                while (password.isEmpty()){
                    System.out.print("Không được để trống! Vui lòng nhập mật khẩu: ");
                    password = sc.nextLine();
                }
                act.setPassword(password);

                System.out.print("Nhập chức vụ: ");
                String position;
                position = sc.nextLine();
                while (position.isEmpty() || !kiemTra.isValidPosition(position)) {
                    System.out.print("Không đúng định dạng! Vui lòng nhập lại chức vụ:");
                    position = sc.nextLine();
                }
                act.setPosition(position);

                dsAccount[i].setAccountId(act.getAccountId());
                dsAccount[i].setUsername(act.getUsername());
                dsAccount[i].setPassword(act.getPassword());
                dsAccount[i].setPosition(act.getPosition());
              }
              data[i] = dsAccount[i].getAccountId() + ";" + dsAccount[i].getUsername() + ";"
                  + dsAccount[i].getPassword() + ";" + dsAccount[i].getPosition();
            }
            try {
              Stream.addAll("Database/TaiKhoan.txt", data);
              System.out.println("\t\t\t\t\t\t\t\t +----SỬA THÔNG TIN TÀI KHOẢN THÀNH CÔNG----+");
              waitConsole();
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
          default -> {
            System.out.println("\t\t\t\t\t\t\t\t +-----LỰA CHỌN KHÔNG HỢP LỆ-----+");
          }
        }
    } catch (InputMismatchException ei) {
      System.out.println("\t\t\t\t\t\t\t\t GIÁ TRỊ KHÔNG HỢP LỆ. VUI LÒNG NHẬP LẠI!");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public void Delete() {

    try {
      System.out.println("\t\t\t\t\t\t\t\t +----XÓA THÔNG TIN TÀI KHOẢN----+");
      System.out.print("Nhập mã tài khoản cần xóa: ");
      String account_id = sc.nextLine();
      while (account_id.isEmpty() || !kiemTra.isValidAccountId(account_id) ){
        System.out.print("Không đúng định dạng! Vui lòng nhập lại ID tài khoản: ");
        account_id = sc.nextLine();
        
      }

      TaiKhoan delete_ac = null;
      for (TaiKhoan ac : dsAccount) {
        if (ac.getAccountId().equals(account_id)) {
          delete_ac = ac;
          break;
        }
      }

      if (delete_ac == null) {
        System.out.println("\t\t\t\t\t\t\t\t  MÃ TÀI KHOẢN KHÔNG TỒN TẠI!");
        return;
      }

      for (int i = 0; i < dsAccount.length; i++) {
        if (account_id.equals(dsAccount[i].getAccountId())) {
          dsAccount = deleteAccount(dsAccount, i);
          break;
        }
      }
      String[] data = new String[dsAccount.length];
      for (int i = 0; i < dsAccount.length; i++) {
        data[i] = dsAccount[i].getAccountId() + ";" + dsAccount[i].getUsername() + ";"
            + dsAccount[i].getPassword() + ";" + dsAccount[i].getPosition();
      }

      try {
        Stream.addAll("Database/TaiKhoan.txt", data);
        System.out.println("\t\t\t\t\t\t\t\t +----XÓA THÔNG TIN TÀI KHOẢN THÀNH CÔNG----+");
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

  public TaiKhoan[] deleteAccount(TaiKhoan[] dsAccount, int index) {
    TaiKhoan[] newCs = new TaiKhoan[dsAccount.length - 1];
    for (int i = 0, j = 0; i < dsAccount.length; i++) {
      if (i != index) {
        newCs[j++] = dsAccount[i];
      }
    }
    return newCs;
  }

  public TaiKhoan[] addAccount(TaiKhoan[] dsAccount, TaiKhoan ac) {
    dsAccount = Arrays.copyOf(dsAccount, dsAccount.length + 1);
    dsAccount[dsAccount.length - 1] = ac;
    return dsAccount;
  }

  @Override
  public void searchByCategory() {
    TaiKhoan[] result = new TaiKhoan[0];
    System.out.println("\t\t\t\t\t\t\t\t +--------NHẬP MỤC LỤC CẨN TÌM KIẾM--------+");
    System.out.println("\t\t\t\t\t\t\t\t |0.Thoát                                  |");
    System.out.println("\t\t\t\t\t\t\t\t +-----------------------------------------+");
    System.out.println("\t\t\t\t\t\t\t\t |1.ID tài khoản                           |");
    System.out.println("\t\t\t\t\t\t\t\t |2.Tên tài khoản                          |");
    System.out.println("\t\t\t\t\t\t\t\t |3.Chức vụ                                |");
    System.out.println("\t\t\t\t\t\t\t\t +-----------------------------------------+");
    System.out.print("\t\t\t\t\t\t\t\t - Mời Bạn Nhập Lựa Chọn: ");
    String choose = sc.nextLine();
      switch (choose) {
        case "0" -> {
          System.out.println("\t\t\t\t\t\t\t\t +----THOÁT CHƯƠNG TRÌNH THÀNH CÔNG----+");
          break;
        }
        case "1" -> {
          System.out.print("Nhập ID tài khoản: ");
          String account_id = sc.nextLine();
          for (TaiKhoan ac : dsAccount) {
            if (ac.getAccountId().toLowerCase().contains(account_id.toLowerCase())) {
              result = addAccount(result, ac);
            }
          }
        }
        case "2" -> {
          System.out.print("Nhập tên tài khoản: ");
          String name = sc.nextLine();
          for (TaiKhoan ac : dsAccount) {
            if (ac.getUsername().toLowerCase().contains(name.toLowerCase())) {
              result = addAccount(result, ac);
            }
          }
        }
        case "3" -> {
          System.out.print("Nhập chức vụ: ");
          String position = sc.nextLine();
          for (TaiKhoan ac : dsAccount) {
            if (ac.getPosition().toLowerCase().contains(position.toLowerCase())) {
              result = addAccount(result, ac);
            }
          }
        }
        default -> {
          System.out.println("\t\t\t\t\t\t\t\t LỰA CHỌN KHÔNG HỢP LỆ");
        }
    }
    String header = String.format("| %-8s | %-30s | %-10s | %-25s |", "ID", "Tên", "Mật khẩu", "Chức vụ");
    System.out.format("+----------+--------------------------------+------------+---------------------------+%n");
    System.out.println(header);
    System.out.format("+----------+--------------------------------+------------+---------------------------+%n");

    for (TaiKhoan ac : result) {
      String read = String.format("| %-8s | %-30s | %-10s | %-25s |",
          ac.getAccountId(), ac.getUsername(), ac.getPassword(), ac.getPosition());
      System.out.println(read);
    }
    System.out.format("+----------+--------------------------------+------------+---------------------------+%n");
    waitConsole();
  }
}