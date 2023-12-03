package controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import models.ChiTietHoaDon;
// import models.HoaDon;
// import models.SanPham;

public class QuanLyChiTietHoaDon extends ChiTietHoaDon implements ControllerInterface {
  private static QuanLyChiTietHoaDon instance;
  private ChiTietHoaDon[] rd;
  private static Scanner input = new Scanner(System.in);

  public static QuanLyChiTietHoaDon getInstance() {
    if (instance == null) {
      instance = new QuanLyChiTietHoaDon();
    }
    return instance;
  }

  private QuanLyChiTietHoaDon() {
    super();
    getListReceiptDetail();
  }

  public ChiTietHoaDon[] getListReceiptDetail() {
    String[] result = new String[0];
    try {
     result= Stream.read("Database/ChiTietHoaDon.txt");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    rd = new ChiTietHoaDon[result.length];
    for (int i = 0; i < result.length; i++) {
      String[] row = result[i].split(";");
      rd[i] = new ChiTietHoaDon(row[0], row[1], Integer.parseInt(row[2]), Integer.parseInt(row[3]));
    }
    return rd;
  }

  @Override
  public void Read() {
    System.out.println("Danh sách chi tiết hóa đơn:");
    String header = String.format("| %-10s | %-13s | %-10s | %-10s | ", "ID Hóa Đơn", "ID Sản Phẩm", "Số lượng", "Giá");
    System.out.format("+------------+---------------+------------+------------+%n");
    System.out.println(header);
    System.out.format("+------------+---------------+------------+------------+%n");

    getListReceiptDetail();
    for(int i = 0; i < rd.length; i++) {
        String read = String.format("| %-10s | %-13s | %-10s | %-10s |", rd[i].getReceiptId(), rd[i].getProductId(), rd[i].getAmount(), rd[i].getPrice());
        System.out.println(read);
    }
    System.out.format("+------------+---------------+------------+------------+%n");
    waitConsole();

}

  @Override
  public void Create() {
    // ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon();
    System.out.println("\t\t\t\t\t\t\t\t +----NHẬP THÔNG TIN CHI TIẾT HÓA ĐƠN----+");

        String HD;
        do {
			System.out.print("Nhập mã hoá đơn:(hd[0-n])--hd1->hd999 :");
			HD = input.nextLine();
			if(HD.isEmpty() || !HD.matches("^hd[0-9]+$") ||HD.length()>5){
				System.out.println("Bạn nhập không đúng định dạng hãy nhập lại.");
			}
		}while (HD.isEmpty() || !HD.matches("^hd[0-9]+$") ||HD.length()>5);
		setReceiptId(HD);


        String SP;
        do {
			System.out.print("Nhập mã sản phẩm:(sp[0-n])--sp1->sp999 :");
			SP = input.nextLine();
			if(SP.isEmpty() || !SP.matches("^sp[0-9]+$") ||SP.length()>5){
				System.out.println("Bạn nhập không đúng định dạng hãy nhập lại.");
			}
		}while (SP.isEmpty() || !SP.matches("^sp[0-9]+$") ||SP.length()>5);
	setProductId(SP);

        System.out.println("enter để tiếp tục");
        String Amount;
		int Amount_int;
		Amount = input.nextLine();
		while (!Amount.matches("^[0-9]+$") ||Amount.length()>4) {
			System.out.print("Nhập số lượng: (<9999) :");
			Amount = input.nextLine();
		}
		Amount_int = Integer.parseInt(Amount);
		setAmount(Amount_int);


        String Price;
		int Price_int;
		Price = input.nextLine();
		while (!Price.matches("^[0-9]+$")||Price.length()>9) {
			System.out.print("Nhập giá: (<=999.999.999) :");
			Price = input.nextLine();
            System.out.println("enter để tiếp tục :");
		}
		Price_int = Integer.parseInt(Price);
		setPrice(Price_int);
    try {
      String input = getReceiptId() + ";" + getProductId() + ";" + getAmount() + ";" + getPrice();
      Stream.addOneLine("Database/ChiTietHoaDon.txt", input);
      System.out.println("Nhập chi tiết hóa đơn thành công");
      waitConsole();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void Update() {
    String HD;
        do {
			System.out.print("Nhập mã hoá đơn:(hd[0-n])--hd1->hd999 :");
			HD = input.nextLine();
			if(HD.isEmpty() || !HD.matches("^hd[0-9]+$") ||HD.length()>5){
				System.out.println("Bạn nhập không đúng định dạng hãy nhập lại.");
			}
		}while (HD.isEmpty() || !HD.matches("^hd[0-9]+$") ||HD.length()>5);
	 String SP;
        do {
			System.out.print("Nhập mã sản phẩm:(sp[0-n])--sp1->sp999 :");
			SP = input.nextLine();
			if(SP.isEmpty() || !SP.matches("^sp[0-9]+$") ||SP.length()>5){
				System.out.println("Bạn nhập không đúng định dạng hãy nhập lại.");
			}
		}while (SP.isEmpty() || !SP.matches("^sp[0-9]+$") ||SP.length()>5);

    ChiTietHoaDon cthd = null;

    for (ChiTietHoaDon receipt : rd) {

      if (HD.equals(receipt.getReceiptId()) && SP.equals(receipt.getProductId())) {
          cthd = receipt;
          break;
      }
  }

    if (cthd == null) {
      System.out.println("\t\t\t\t\t\t\t\t +-----MÃ HÓA ĐƠN HOẶC SẢN PHẨM KHÔNG TỒN TẠI-----+");
      return;
    }

  
    System.out.println("Thông tin hóa đơn cho sản phẩm có id " + cthd.getProductId() + " là:");
    String header = String.format("| %-10s | %-12s | %-10s | %-10s | ", "ID Hóa Đơn", "ID Sản Phẩm", "Số lượng",
        "Giá");
    System.out.format("+------------+--------------+------------+------------+%n");
    System.out.println(header);
    System.out.format("+------------+--------------+------------+------------+%n");
    String row = String.format("| %-10s | %-12s | %-10s | %-10s |", cthd.getReceiptId(), cthd.getProductId(),
        cthd.getAmount(), cthd.getPrice());
    System.out.println(row);
    System.out.format("+------------+--------------+------------+------------+%n");
    System.out.println("Enter để tiếp tục:");
    String[] data = new String[rd.length];
    input.nextLine();
    for (int i = 0; i < rd.length; i++) {
      if (HD.equals(rd[i].getReceiptId()) && SP.equals(rd[i].getProductId())) {
        System.out.println("Nhập lại thông tin chi tiết hóa đơn:");

          String Amount;
		int Amount_int;
		Amount = input.nextLine();
		while (!Amount.matches("^[0-9]+$") ||Amount.length()>4) {
			System.out.print("Nhập số lượng: (<9999) :");
			Amount = input.nextLine();
		}
		Amount_int = Integer.parseInt(Amount);
		setAmount(Amount_int);


        String Price;
		int Price_int;
		Price = input.nextLine();
		while (!Price.matches("^[0-9]+$")||Price.length()>9) {
			System.out.print("Nhập giá: (<=999.999.999) :");
			Price = input.nextLine();
            System.out.println("enter để tiếp tục :");
		}
		Price_int = Integer.parseInt(Price);
		setPrice(Price_int);
        rd[i].setAmount(getAmount());
        rd[i].setPrice(getPrice());

      }
      data[i] = rd[i].getReceiptId() + ";" + rd[i].getProductId() + ";" + rd[i].getAmount() + ";"
          + rd[i].getPrice();

    }
    try {
      Stream.addAll("Database/ChiTietHoaDon.txt", data);
      System.out.println("Sửa thông tin chi tiết hoá đơn thành công");
      // waitConsole();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // TODO: Nếu xóa chi tiết hóa đơn -> Phải xóa hóa đơn, cần fix
  public void Delete(String id) {
    for (int i = 0; i < rd.length; i++) {
      if (id.equals(rd[i].getReceiptId())) {
        rd = deleteReceiptDetail(rd, i);
        i -= 1;
      }
    }

    String[] data = new String[rd.length];
    for (int i = 0; i < rd.length; i++) {
      data[i] = rd[i].getReceiptId() + ";" + rd[i].getProductId() + ";" + rd[i].getAmount() + ";"
          + rd[i].getPrice();
    }
    try {
      Stream.addAll("Database/ChiTietHoaDon.txt", data);
      System.out.println("Xóa chi tiết hoá đơn thành công");
      waitConsole();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public ChiTietHoaDon[] deleteReceiptDetail(ChiTietHoaDon[] rd, int index) {
    ChiTietHoaDon[] newRd = new ChiTietHoaDon[rd.length - 1];
    for (int i = 0, j = 0; i < rd.length; i++) {
      if (i != index) {
        newRd[j++] = rd[i];
      }
    }
    return newRd;
  }

  public ChiTietHoaDon[] addReceiptDetail(ChiTietHoaDon[] rd, ChiTietHoaDon receipt) {
    rd = Arrays.copyOf(rd, rd.length + 1);
    rd[rd.length - 1] = receipt;
    return rd;
  }

  @Override
  public void searchByCategory() {
    ChiTietHoaDon[] result = new ChiTietHoaDon[0];
    System.out.println("Nhập mục lục cần tìm kiếm: ");
    System.out.println("1.Mã hóa đơn");
    System.out.println("2.Mã Sản Phẩm");
    System.out.println("Mời nhập:");
    String choose = input.nextLine();
    switch (choose) {
      case "1" -> {
        String HD;
        do {
			System.out.print("Nhập mã hoá đơn:(hd[0-n])--hd1->hd999 :");
			HD = input.nextLine();
			if(HD.isEmpty() || !HD.matches("^hd[0-9]+$") ||HD.length()>5){
				System.out.println("Bạn nhập không đúng định dạng hãy nhập lại.");
			}
		}while (HD.isEmpty() || !HD.matches("^hd[0-9]+$") ||HD.length()>5);

        for (int i = 0; i < rd.length; i++) {
          if (HD.equals(rd[i].getReceiptId())) {
            result = addReceiptDetail(result, rd[i]);
          }
        }
      }
      case "2" -> {
       String SP;
        do {
			System.out.print("Nhập mã sản phẩm:(sp[0-n])--sp1->sp999 :");
			SP = input.nextLine();
			if(SP.isEmpty() || !SP.matches("^sp[0-9]+$") ||SP.length()>5){
				System.out.println("Bạn nhập không đúng định dạng hãy nhập lại.");
			}
		}while (SP.isEmpty() || !SP.matches("^sp[0-9]+$") ||SP.length()>5);

        for (int i = 0; i < rd.length; i++) {
          if (SP.equals(rd[i].getProductId())) {
            result = addReceiptDetail(result, rd[i]);
          }
        }
      }
    }

    System.out.println("Danh sách chi tiết hóa hàng tìm được:");
    String header = String.format("| %-10s | %-10s | %-10s | %-10s | ", "ID Hóa Đơn", "ID Sản Phẩm", "Số lượng",
        "Giá");
    System.out.format("+------------+-------------+------------+------------+%n");
    System.out.println(header);
    System.out.format("+------------+-------------+------------+------------+%n");
    for (ChiTietHoaDon receipt : result) {
      String read = String.format("| %-10s | %-11s | %-10s | %-10s |", receipt.getReceiptId(),
          receipt.getProductId(), receipt.getAmount(), receipt.getPrice());
      System.out.println(read);
    }
    System.out.format("+------------+-------------+------------+------------+%n");
    waitConsole();
  }

  public void waitConsole() {
    System.out.println("Ấn Enter để tiếp tục");
    input.nextLine();
  }

  @Override
  public void Delete() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'Delete'");
  }
}