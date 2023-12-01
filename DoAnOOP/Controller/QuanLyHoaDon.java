package Controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

import Entity.HoaDon;

public class QuanLyHoaDon extends HoaDon implements ControllerInterface{
	static Scanner input = new Scanner(System.in);
	public HoaDon[] re;
	public QuanLyChiTietHoaDon rdm = null;

	public QuanLyHoaDon() throws FileNotFoundException {
		super();
		getListHoaDon();
	}

	public HoaDon[] getListHoaDon() {
		String[] result = new String[0];
		try {
			result = Stream.read("Database/HoaDon.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		re = new HoaDon[result.length];
		for (int i = 0; i < result.length; i++) {
			String[] row = result[i].split(";");
			re[i] = new HoaDon(row[0], Integer.parseInt(row[1]), row[2], row[3], row[4], row[5]);
		}
		return re;
	}

	@Override
	public void Read() {
		try {
			rdm = new QuanLyChiTietHoaDon();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		System.out.println("Danh sách hóa đơn:");
		String header = String.format("| %-5s | %-15s | %-20s | %-25s | %-15s | %-20s |", "ID", "Giá", "Ngày mua", "Phương thức thanh toán", "Id khách hàng", "Nhân viên lập đơn");
		System.out.format("+-------+-----------------+----------------------+---------------------------+-----------------+----------------------+%n");
		System.out.println(header);
		System.out.format("+-------+-----------------+----------------------+---------------------------+-----------------+----------------------+%n");
		getListHoaDon();
		for (HoaDon HoaDon : re) {
			String read = String.format("| %-5s | %-15s | %-20s | %-25s | %-15s | %-20s |", HoaDon.getID_Receipt(), HoaDon.getPrice(), HoaDon.getDatePurchase(), HoaDon.getPurchaseMethod(), HoaDon.getID_Customer(), HoaDon.getID_Employee());
			System.out.println(read);
		}
		System.out.format("+-------+-----------------+----------------------+---------------------------+-----------------+----------------------+%n");

		//Xem chi tiết hóa đơn
		System.out.println("Xem thêm chi tiết hóa đơn ?");
		System.out.println("1.Có (Nhập số bất kì để thoát)");
		System.out.print("Mời nhập:");
		int choose1 = input.nextInt();
		if (choose1 == 1) {
			rdm.Search_byCategory();
		}
		waitConsole();
	}

	@Override
	public void Create() {
		System.out.print("Nhập mã hóa đơn: ");
		setID_Receipt(input.nextLine());

		int check = 0;
		for (HoaDon HoaDon : re) {
			if (getID_Receipt().equals(HoaDon.getID_Receipt())) {
				check = 1;
				break;
			}
		}

		if (check == 1) {
			System.out.println("id của hóa đơn bị trùng");
			return;
		}

		System.out.print("Nhập giá: ");
		setPrice(input.nextInt());

		input.nextLine();
		System.out.print("Nhập mã khách hàng: ");
		setID_Customer(input.nextLine());

		System.out.print("Nhập mã nhân viên: ");
		setID_Employee(input.nextLine());

		System.out.print("Nhập phương pháp mua hàng: ");
		setPurchaseMethod(input.nextLine());

		LocalDate date = java.time.LocalDate.now();
		setDatePurchase(date.format(DateTimeFormatter.ofPattern("d/MM/uuuu")));


		try {
			String inputString = getID_Receipt() + ";" + getPrice() + ";" + getDatePurchase() + ";" + getPurchaseMethod() + ";" + getID_Customer() + ";" + getID_Employee();
			Stream.addOneLine("Database/HoaDon.txt", inputString);
			System.out.println("Nhập hóa đơn thành công");
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println();
		System.out.println("Thêm chi tiết hóa đơn cho hóa đơn này:");
		System.out.println("1.Có (Nhập số khác để thoát)");
		System.out.print("Mời nhập:");

		int choose1 = input.nextInt();
		if (choose1 == 1) {
			boolean check1 = true;
			do {
				rdm.Create();
				System.out.println("Thêm tiếp chi tiết hóa đơn:");
				System.out.println("1.Có (Nhập số khác để thoát)");
				System.out.print("Mời nhập:");
				int choose2 = input.nextInt();
				if (choose2 != 1)
					check1 = false;
			}
			while (check1);
		}
		waitConsole();
	}


	@Override
	public void Update() {
		System.out.print("Nhập ID hóa đơn cần chỉnh sửa: ");
		String ID_HoaDon = input.nextLine();
		HoaDon hdon = null;

		for (HoaDon HoaDon : re) {
			if (ID_HoaDon.equals(HoaDon.getID_Receipt())) {
				hdon = HoaDon;
				break;
			}
		}

		if (hdon == null) {
			System.out.println("ID hóa đơn không tồn tại. Xin vui lòng nhập lại!");
			return;
		}

		System.out.println("Thông tin cũ của hóa đơn:");
		String header = String.format("| %-5s | %-15s | %-20s | %-25s | %-15s | %-20s |", "ID", "Giá", "Ngày mua", "Phương thức thanh toán", "Id khách hàng", "Nhân viên lập đơn");
		System.out.format("+-------+-----------------+----------------------+---------------------------+-----------------+----------------------+%n");
		System.out.println(header);
		System.out.format("+-------+-----------------+----------------------+---------------------------+-----------------+----------------------+%n");
		String row = String.format("| %-5s | %-15s | %-20s | %-25s | %-15s | %-20s |", hdon.getID_Receipt(), hdon.getPrice(), hdon.getDatePurchase()
				, hdon.getPurchaseMethod(), hdon.getID_Customer(), hdon.getID_Employee());
		System.out.println(row);
		System.out.format("+-------+-----------------+----------------------+---------------------------+-----------------+----------------------+%n");

		String[] data = new String[re.length];

		for (int i = 0; i < re.length; i++) {
			if (ID_HoaDon.equals(re[i].getID_Receipt())) {
				System.out.println("Sửa thông tin hóa đơn:");

				System.out.print("Nhập giá: ");
				setPrice(input.nextInt());

				input.nextLine();
				System.out.print("Nhập mã khách hàng: ");
				setID_Customer(input.nextLine());

				System.out.print("Nhập mã nhân viên: ");
				setID_Employee(input.nextLine());

				System.out.print("Nhập phương pháp mua hàng: ");
				setPurchaseMethod(input.nextLine());

				re[i].setPrice(getPrice());
				re[i].setID_Customer(getID_Customer());
				re[i].setID_Employee(getID_Employee());
				re[i].setPurchaseMethod(getPurchaseMethod());
			}
			data[i] = re[i].getID_Receipt() + ";" + re[i].getPrice() + ";" + re[i].getDatePurchase() + ";" + re[i].getPurchaseMethod() + ";" + re[i].getID_Customer() + ";" + re[i].getID_Employee();
		}

		// Ghi file mảng đã cập nhật
		try {
			Stream.addAll("Database/HoaDon.txt", data);
			System.out.println("Sửa thông tin hoá đơn thành công");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void Delete() {
		System.out.print("Nhập ID hóa đơn cần xóa:asadds ");
		String ID_HoaDon = input.nextLine();
		HoaDon hdon = null;

		for (HoaDon HoaDon : re) {
			if (ID_HoaDon.equals(HoaDon.getID_Receipt())) {
				hdon = HoaDon;
				break;
			}
		}

		if (hdon == null) {
			System.out.println("ID hóa đơn không tồn tại. Xin vui lòng nhập lại!");
			return;
		}

		String[] data = new String[re.length];

		for (int i = 0; i < re.length; i++) {
			if (ID_HoaDon.equals(re[i].getID_Receipt())) {
				System.out.print("Nhập giá: ");
				setPrice(0);

				setID_Customer("null");

				setID_Employee("null");

				setPurchaseMethod("null");

				re[i].setPrice(getPrice());
				re[i].setID_Customer(getID_Customer());
				re[i].setID_Employee(getID_Employee());
				re[i].setPurchaseMethod(getPurchaseMethod());
			}
			data[i] = re[i].getID_Receipt() + ";" + re[i].getPrice() + ";" + re[i].getDatePurchase() + ";" + re[i].getPurchaseMethod() + ";" + re[i].getID_Customer() + ";" + re[i].getID_Employee();
		}

		// Ghi file mảng đã cập nhật
		try {
			Stream.addAll("Database/HoaDon.txt", data);
			System.out.println("Sửa thông tin hoá đơn thành công");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public HoaDon[] addHoaDon(HoaDon[] re, HoaDon HoaDon) {
		re = Arrays.copyOf(re, re.length + 1);
		re[re.length - 1] = HoaDon;
		return re;
	}

	@Override
	public void Search_byCategory() {
		HoaDon[] result = new HoaDon[0];
		System.out.println("Nhập mục lục cần tìm kiếm: ");
		System.out.println("1.Mã hóa đơn");
		System.out.println("2.Mã nhân viên lập hóa đơn");
		System.out.println("3.Mã khách hàng");
		System.out.println("4.Khoảng ngày mua hàng");
		System.out.println("5.Tổng tiền của 1 hóa đơn");
		int choose = input.nextInt();
		switch (choose) {
			case 1 -> {
				input.nextLine();
				System.out.print("Nhập ID hóa đơn: ");
				String id = input.nextLine();
				for (HoaDon HoaDon : re) {
					if (id.equals(HoaDon.getID_Receipt())) {
						result = addHoaDon(result, HoaDon);
					}
				}
			}
			case 2 -> {
				input.nextLine();
				System.out.print("Nhập mã nhân viên lập hóa đơn: ");
				String id = input.nextLine();
				for (HoaDon HoaDon : re) {
					if (id.equals(HoaDon.getID_Employee())) {
						result = addHoaDon(result, HoaDon);
					}
				}
			}
			case 3 -> {
				input.nextLine();
				System.out.print("Nhập mã khách hàng của hóa đơn: ");
				String id = input.nextLine();
				for (HoaDon HoaDon : re) {
					if (id.equals(HoaDon.getID_Customer())) {
						result = addHoaDon(result, HoaDon);
					}
				}
			}
			case 4 -> {
				input.nextLine();
				System.out.print("Nhập ngày bắt đầu: ");
				String day1 = input.nextLine();
				System.out.print("Ngày két thúc:");
				String day2 = input.nextLine();
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				try {
					Date start = sdf.parse(day1);
					Date end = sdf.parse(day2);
					for (HoaDon HoaDon : re) {
						Date day = sdf.parse(HoaDon.getDatePurchase());
						if (day.after(start) && day.before(end)) {
							result = addHoaDon(result, HoaDon);
						}
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			case 5 -> {
				input.nextLine();
				System.out.print("Từ giá: ");
				int begin = input.nextInt();
				System.out.print("Đến giá:");
				int end = input.nextInt();
				for (HoaDon HoaDon : re) {
					if (HoaDon.getPrice() <= end && HoaDon.getPrice() >= begin) {
						result = addHoaDon(result, HoaDon);
					}
				}
			}
		}

		if (result.length == 0) {
			System.out.println("Không có hóa đơn cần tìm");
		} else {
			System.out.println("Danh sách hóa đơn tìm được:");
			String header = String.format("| %-5s | %-15s | %-20s | %-25s | %-15s | %-20s |", "ID", "Giá", "Ngày mua", "Phương thức thanh toán", "Id khách hàng", "Nhân viên lập đơn");
			System.out.format("+-------+-----------------+----------------------+---------------------------+-----------------+----------------------+%n");
			System.out.println(header);
			System.out.format("+-------+-----------------+----------------------+---------------------------+-----------------+----------------------+%n");
			for (HoaDon recepit : result) {
				String read = String.format("| %-5s | %-15s | %-20s | %-25s | %-15s | %-20s |", recepit.getID_Receipt(), recepit.getPrice(), recepit.getDatePurchase(), recepit.getPurchaseMethod(), recepit.getID_Customer(), recepit.getID_Employee());
				System.out.println(read);
			}
			System.out.format("+-------+-----------------+----------------------+---------------------------+-----------------+----------------------+%n");

			waitConsole();
		}
	}

	public void waitConsole() {
		System.out.println("Ấn Enter để tiếp tục");
		input.nextLine();
	}
}
