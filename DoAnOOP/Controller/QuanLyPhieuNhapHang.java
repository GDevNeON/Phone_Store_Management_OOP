package Controller;

import Entity.PhieuNhapHang;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class QuanLyPhieuNhapHang extends PhieuNhapHang implements ControllerInterface {
	public PhieuNhapHang[] dsp;
	public QuanLyChiTietPhieuNhapHang chiTiet;
	static Scanner sc = new Scanner(System.in);

	public QuanLyPhieuNhapHang() throws FileNotFoundException {
		super();
		getListPhieuNhapHang();
	}

	public PhieuNhapHang[] getListPhieuNhapHang() {
		String[] result = new String[0];
		try {
			result = Stream.read("Database/PhieuNhap.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		dsp = new PhieuNhapHang[result.length];
		for (int i = 0; i < result.length; i++) {
			String[] row = result[i].split(";");
			dsp[i] = new PhieuNhapHang(row[0], row[1], row[2], LocalDate.parse(row[3]),
					Integer.parseInt(row[4]));
		}
		return dsp;
	}

	public void waitConsole() {
		System.out.println("\t\t\t\t\t\t\t\t +-----ẤN ENTER ĐỂ TIẾP TỤC-----+");
		sc.nextLine();
	}

	@Override
	public void Read() {
		try {
			chiTiet = new QuanLyChiTietPhieuNhapHang();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		System.out.println("\t\t\t\t\t\t\t\t +-----DANH SÁCH PHIẾU NHẬP HÀNG-----+");
		String header = String.format("| %-14s | %-13s | %-30s | %-10s | %-20s |", "ID Phiếu nhập", "ID Nhân viên",
				"Nhà cung cấp", "Ngày nhập", "Tổng tiền");
		System.out.format(
				"+----------------+---------------+--------------------------------+------------+----------------------+%n");
		System.out.println(header);
		System.out.format(
				"+----------------+---------------+--------------------------------+------------+----------------------+%n");

		getListPhieuNhapHang();

		for (PhieuNhapHang PhieuNhapHang : dsp) {
			String read = String.format("| %-14s | %-13s | %-30s | %-10s | %-20s |", PhieuNhapHang.getID_PhieuNhap(),
					PhieuNhapHang.getID_Worker(), PhieuNhapHang.getName_Supplier(),
					PhieuNhapHang.getNgayNhap(), PhieuNhapHang.getPrice());
			System.out.println(read);
		}
		System.out.format(
				"+----------------+---------------+--------------------------------+------------+----------------------+%n");

		System.out.println("Xem thêm chi tiết phiếu nhập ?");
		System.out.println("1.Có ");
		System.out.println("2.Không ");
		System.out.print("Mời nhập: ");
		int choose1 = sc.nextInt();
		if (choose1 == 1) {
			chiTiet.Search_byCategory();
		}
		waitConsole();
	}

	@Override
	public void Create() {
		System.out.println("\t\t\t\t\t\t\t\t +-----NHẬP THÊM PHIẾU NHẬP-----+");
		System.out.print("Nhập mã phiếu nhập: ");
		setID_PhieuNhap(sc.nextLine());

		int check = 0;
		for (PhieuNhapHang PhieuNhapHang : dsp) {
			if (getID_PhieuNhap().equals(PhieuNhapHang.getID_PhieuNhap())) {
				check = 1;
				break;
			}
		}

		if (check == 1) {
			System.out.println("\t\t\t\t\t\t\t\t +-----MÃ PHIẾU NHẬP BỊ TRÙNG-----+");
			return;
		}

		System.out.print("Nhập ID nhân viên: ");
		setID_Worker(sc.nextLine());

		System.out.print("Nhập tên nhà cung cấp: ");
		setName_Supplier(sc.nextLine());

		LocalDate date = java.time.LocalDate.now();
		String formattedDate = date.format(DateTimeFormatter.ofPattern("d/MM/uuuu"));
		setNgayNhap(LocalDate.parse(formattedDate, DateTimeFormatter.ofPattern("d/MM/uuuu")));

		System.out.print("Nhập tổng tiền: ");
		setPrice(sc.nextInt());

		try {
			String pnhString = getID_PhieuNhap() + ";" + getID_Worker() + ";" + getName_Supplier() + ";" + getNgayNhap()
					+ ";" + getPrice();
			Stream.addOneLine("Database/PhieuNhap.txt", pnhString);
			System.out.println("\t\t\t\t\t\t\t\t +-----NHẬP PHIẾU HÀNG THÀNH CÔNG-----+");
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println();
		System.out.println("Thêm chi tiết cho phiếu nhập này:");
		System.out.println("1.Có ");
		System.out.println("2.Không");
		System.out.print("Mời nhập: ");

		int choose1 = sc.nextInt();
		if (choose1 == 1) {
			boolean check1 = true;
			do {
				chiTiet.Create();
				System.out.println("Thêm tiếp chi tiết phiếu nhập:");
				System.out.println("1.Có ");
				System.out.println("2.Không");
				System.out.print("Mời nhập: ");
				int choose2 = sc.nextInt();
				if (choose2 != 1)
					check1 = false;
			} while (check1);
		}
		getListPhieuNhapHang();
		waitConsole();
	}

	@Override
	public void Update() {
		try {
			System.out.println("\t\t\t\t\t\t\t\t +-----CHỈNH SỬA THÔNG TIN PHIẾU NHẬP-----+");
			System.out.print("-Mời nhập ID phiếu nhập cần chỉnh sửa: ");
			String ID_PhieuNhapHang = sc.nextLine();
			PhieuNhapHang pnh = null;

			for (PhieuNhapHang PhieuNhapHang : dsp) {
				if (PhieuNhapHang.getID_PhieuNhap().equals(ID_PhieuNhapHang)) {
					pnh = PhieuNhapHang;
					break;
				}
			}

			if (pnh == null) {
				System.out.println("\t\t\t\t\t\t\t\t +-----MÃ PHIẾU NHẬP KHÔNG TỒN TẠI-----+");
				return;
			}

			System.out.println("\t\t\t\t\t\t\t\t +-----THÔNG TIN PHIẾU NHẬP TRƯỚC KHI CHỈNH SỬA-----+");
			String header = String.format("| %-14s | %-13s | %-30s | %-10s | %-20s |", "ID Phiếu nhập", "ID Nhân viên",
					"Nhà cung cấp", "Ngày nhập", "Tổng tiền");
			System.out.format(
					"+----------------+---------------+--------------------------------+------------+----------------------+%n");
			System.out.println(header);
			System.out.format(
					"+----------------+---------------+--------------------------------+------------+----------------------+%n");
			String row = String.format("| %-14s | %-13s | %-30s | %-10s | %-20s |", pnh.getID_PhieuNhap(), pnh.getID_Worker(),
					pnh.getName_Supplier(), pnh.getNgayNhap(), pnh.getPrice());
			System.out.println(row);
			System.out.format(
					"+----------------+---------------+--------------------------------+------------+----------------------+%n");

			String[] data = new String[dsp.length];

			System.out.println("\t\t\t\t\t\t\t\t +-----SỬA THÔNG TIN PHIẾU NHẬP-----+");
			System.out.println("\t\t\t\t\t\t\t\t |0.Thoát                           |");
			System.out.println("\t\t\t\t\t\t\t\t +----------------------------------+");
			System.out.println("\t\t\t\t\t\t\t\t |1.Sửa ID phiếu nhập               |");
			System.out.println("\t\t\t\t\t\t\t\t |2.Sửa ID nhân viên nhập           |");
			System.out.println("\t\t\t\t\t\t\t\t |3.Sửa tên nhà cung cấp            |");
			System.out.println("\t\t\t\t\t\t\t\t |4.Sửa tổng tiền phiếu nhập        |");
			System.out.println("\t\t\t\t\t\t\t\t |5.Sửa tất cả thông tin            |");
			System.out.println("\t\t\t\t\t\t\t\t +----------------------------------+");
			System.out.print("\t\t\t\t\t\t\t\t Mời bạn nhập lựa chọn: ");
			int choose = sc.nextInt();
			sc.nextLine();
			if (choose == 0)
				return;
			else {
				switch (choose) {
					case 1 -> {
						for (int i = 0; i < dsp.length; i++) {
							if (dsp[i].getID_PhieuNhap().equals(ID_PhieuNhapHang)) {
								System.out.print("Nhập ID phiếu nhập hàng: ");
								setID_PhieuNhap(sc.nextLine());
								int check = 0;
								for (PhieuNhapHang phieuNhap : dsp) {
									if (getID_PhieuNhap().equals(phieuNhap.getID_PhieuNhap())) {
										check = 1;
										break;
									}
								}
								if (check == 1) {
									System.out.println("\t\t\t\t\t\t\t\t MÃ PHIẾU NHẬP BỊ TRÙNG!");
									return;
								}
								dsp[i].setID_PhieuNhap(getID_PhieuNhap());
							}
							data[i] = dsp[i].getID_PhieuNhap() + ";" + dsp[i].getID_Worker() + ";" + dsp[i].getName_Supplier() + ";" + dsp[i].getNgayNhap() + ";" + dsp[i].getPrice();
						}
						try {
							Stream.addAll("Database/PhieuNhap.txt", data);
							System.out.println("\t\t\t\t\t\t\t\t +-----SỬA THÔNG TIN PHIẾU NHẬP THÀNH CÔNG-----+");
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					case 2 -> {
						for (int i = 0; i < dsp.length; i++) {
							if (dsp[i].getID_PhieuNhap().equals(ID_PhieuNhapHang)) {
								System.out.print("Nhập ID nhân viên: ");
								setID_Worker(sc.nextLine());
								dsp[i].setID_Worker(getID_Worker());
							}
							data[i] = dsp[i].getID_PhieuNhap() + ";" + dsp[i].getID_Worker() + ";" + dsp[i].getName_Supplier() + ";" + dsp[i].getNgayNhap() + ";" + dsp[i].getPrice();
						}
						try {
							Stream.addAll("Database/PhieuNhap.txt", data);
							System.out.println("\t\t\t\t\t\t\t\t +-----SỬA THÔNG TIN PHIẾU NHẬP THÀNH CÔNG-----+");
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					case 3 -> {
						for (int i = 0; i < dsp.length; i++) {
							if (dsp[i].getID_PhieuNhap().equals(ID_PhieuNhapHang)) {
								System.out.print("Nhập tên nhà cung cấp: ");
								setName_Supplier(sc.nextLine());
								dsp[i].setName_Supplier(getName_Supplier());
							}
							data[i] = dsp[i].getID_PhieuNhap() + ";" + dsp[i].getID_Worker() + ";" + dsp[i].getName_Supplier() + ";" + dsp[i].getNgayNhap() + ";" + dsp[i].getPrice();
						}
						try {
							Stream.addAll("Database/PhieuNhap.txt", data);
							System.out.println("\t\t\t\t\t\t\t\t +-----SỬA THÔNG TIN PHIẾU NHẬP THÀNH CÔNG-----+");
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					case 4 -> {
						for (int i = 0; i < dsp.length; i++) {
							if (dsp[i].getID_PhieuNhap().equals(ID_PhieuNhapHang)) {
								System.out.print("Nhập tổng tiền: ");
								setPrice(sc.nextInt());
							}
							data[i] = dsp[i].getID_PhieuNhap() + ";" + dsp[i].getID_Worker() + ";" + dsp[i].getName_Supplier() + ";" + dsp[i].getNgayNhap() + ";" + dsp[i].getPrice();
						}
						try {
							Stream.addAll("Database/PhieuNhap.txt", data);
							System.out.println("\t\t\t\t\t\t\t\t +-----SỬA THÔNG TIN PHIẾU NHẬP THÀNH CÔNG-----+");
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					case 5 -> {
						for (int i = 0; i < dsp.length; i++) {
							if (dsp[i].getID_PhieuNhap().equals(ID_PhieuNhapHang)) {
								System.out.print("Nhập ID phiếu nhập hàng: ");
								setID_PhieuNhap(sc.nextLine());
								int check = 0;
								for (PhieuNhapHang phieuNhap : dsp) {
									if (getID_PhieuNhap().equals(phieuNhap.getID_PhieuNhap())) {
										check = 1;
										break;
									}
								}
								if (check == 1) {
									System.out.println("\t\t\t\t\t\t\t\t MÃ PHIẾU NHẬP BỊ TRÙNG!");
									return;
								}

								System.out.print("Nhập ID nhân viên: ");
								setID_Worker(sc.nextLine());

								System.out.print("Nhập tên nhà cung cấp: ");
								setName_Supplier(sc.nextLine());

								System.out.print("Nhập tổng tiền: ");
								setPrice(sc.nextInt());

								dsp[i].setID_PhieuNhap(getID_PhieuNhap());
								dsp[i].setID_Worker(getID_Worker());
								dsp[i].setName_Supplier(getName_Supplier());
								dsp[i].setPrice(getPrice());
							}
							data[i] = dsp[i].getID_PhieuNhap() + ";" + dsp[i].getID_Worker() + ";" + dsp[i].getName_Supplier() + ";"
									+ dsp[i].getNgayNhap() + ";" + dsp[i].getPrice();
						}

						try {
							Stream.addAll("Database/PhieuNhap.txt", data);
							System.out.println("\t\t\t\t\t\t\t\t +-----SỬA THÔNG TIN PHIẾU NHẬP THÀNH CÔNG-----+");
						} catch (IOException e) {
							e.printStackTrace();
						}
					}

					default -> {
						System.out.println("\t\t\t\t\t\t\t\t +-----LỰA CHỌN KHÔNG HỢP LỆ-----+");
					}
				}
			}


		} catch (InputMismatchException ei) {
			System.out.println("\t\t\t\t\t\t\t\t +------GIÁ TRỊ KHÔNG HỢP LỆ. VUI LÒNG NHẬP LẠI-----+");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.println("Bạn có muốn sửa chi tiết phiếu nhập cho phiếu nhập này không");
		System.out.println("1.Có ");
		System.out.println("2.Không");
		System.out.print("Mời nhập: ");
		int choose1 = sc.nextInt();
		if (choose1 == 1) {
			boolean check1 = true;
			do {
				chiTiet.Update();
				System.out.println("Sửa chi tiết phiếu nhập:");
				System.out.println("1.Có");
				System.out.println("2.Không");
				System.out.print("Mời nhập: ");
				int choose2 = sc.nextInt();
				if (choose2 != 1)
					check1 = false;
			} while (check1);
		}

		waitConsole();
	}

	@Override
	public void Delete() {
		try {
			System.out.println("\t\t\t\t\t\t\t\t +-----XÓA PHIẾU NHẬP-----+");
			System.out.print("Nhập ID phiếu nhập cần xóa: ");
			String ID_PhieuNhapHang = sc.nextLine();

			PhieuNhapHang pnh = null;
			for (PhieuNhapHang PhieuNhapHang : dsp) {
				if (PhieuNhapHang.getID_PhieuNhap().equals(ID_PhieuNhapHang)) {
					pnh = PhieuNhapHang;
					break;
				}
			}
			if (pnh == null) {
				System.out.println("\t\t\t\t\t\t\t\t +-----MÃ PHIẾU NHẬP KHÔNG TỒN TẠI-----+");
				return;
			}

			for (int i = 0; i < dsp.length; i++) {
				if (ID_PhieuNhapHang.equals(dsp[i].getID_PhieuNhap())) {
					dsp = deletePhieuNhapHang(dsp, i);
					break;
				}
			}

			String[] data = new String[dsp.length];
			for (int i = 0; i < dsp.length; i++) {
				data[i] = dsp[i].getID_PhieuNhap() + ";" + dsp[i].getID_Worker() + ";" + dsp[i].getName_Supplier() + ";"
						+ dsp[i].getNgayNhap() + ";" + dsp[i].getPrice();
			}
			try {
				Stream.addAll("Database/PhieuNhap.txt", data);
				System.out.println("\t\t\t\t\t\t\t\t +-----XÓA PHIẾU HÀNG THÀNH CÔNG-----+");
				waitConsole();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (InputMismatchException ei) {
			System.out.println("\t\t\t\t\t\t\t\t +-----GIÁ TRỊ KHÔNG HỢP LỆ. VUI LÒNG NHẬP LẠI-----+");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public PhieuNhapHang[] deletePhieuNhapHang(PhieuNhapHang[] pnh, int index) {
		PhieuNhapHang[] newRe = new PhieuNhapHang[pnh.length - 1];
		for (int i = 0, j = 0; i < pnh.length; i++) {
			if (i != index) {
				newRe[j++] = pnh[i];
			}
		}
		return newRe;
	}

	public PhieuNhapHang[] addPhieuNhapHang(PhieuNhapHang[] pnh, PhieuNhapHang PhieuNhapHang) {
		pnh = Arrays.copyOf(pnh, pnh.length + 1);
		pnh[pnh.length - 1] = PhieuNhapHang;
		return pnh;
	}

	@Override
	public void Search_byCategory() {
		PhieuNhapHang[] result = new PhieuNhapHang[0];
		System.out.println("\t\t\t\t\t\t\t\t +--------NHẬP MỤC LỤC CẨN TÌM KIẾM--------+");
		System.out.println("\t\t\t\t\t\t\t\t |0.Thoát                                  |");
		System.out.println("\t\t\t\t\t\t\t\t +-----------------------------------------+");
		System.out.println("\t\t\t\t\t\t\t\t |1.Tiền nhập hàng                         |");
		System.out.println("\t\t\t\t\t\t\t\t |2.ID Nhân viên                           |");
		System.out.println("\t\t\t\t\t\t\t\t |3.Tên nhà cung cấp                       |");
		System.out.println("\t\t\t\t\t\t\t\t |4.ID Phiếu nhập hàng                     |");
		System.out.println("\t\t\t\t\t\t\t\t |5.Ngày nhập hàng                         |");
		System.out.println("\t\t\t\t\t\t\t\t +-----------------------------------------+");
		System.out.print("\t\t\t\t\t\t\t\t - Mời Bạn Nhập Lựa Chọn: ");
		int choose = sc.nextInt();
		if (choose == 0)
			return;
		else {
			switch (choose) {
				case 1 -> {
					sc.nextLine();
					System.out.print("Nhập tiền nhập hàng: ");
					Integer price = sc.nextInt();
					for (PhieuNhapHang pnh : dsp) {
						if (price.equals(pnh.getPrice())) {
							result = addPhieuNhapHang(result, pnh);
						}
					}
				}
				case 2 -> {
					sc.nextLine();
					System.out.print("Nhập mã nhân viên nhập hàng: ");
					String id = sc.nextLine();
					for (PhieuNhapHang PhieuNhapHang : dsp) {
						if (id.equals(PhieuNhapHang.getID_Worker())) {
							result = addPhieuNhapHang(result, PhieuNhapHang);
						}
					}
				}
				case 3 -> {
					sc.nextLine();
					System.out.print("Nhập tên nhà cung cấp: ");
					String name = sc.nextLine();
					for (PhieuNhapHang PhieuNhapHang : dsp) {
						if (PhieuNhapHang.getName_Supplier().toLowerCase().contains(name.toLowerCase())) {
							result = addPhieuNhapHang(result, PhieuNhapHang);
						}
					}
				}
				case 4 -> {
					System.out.print("Nhập ID phiếu nhập hàng: ");
					String id = sc.nextLine();
					for (PhieuNhapHang PhieuNhapHang : dsp) {
						if (id.equals(PhieuNhapHang.getID_PhieuNhap())) {
							result = addPhieuNhapHang(result, PhieuNhapHang);
						}
					}
				}
				case 5 -> {
					sc.nextLine();
					System.out.print("Nhập ngày bắt đầu: ");
					String day1 = sc.nextLine();
					System.out.print("Ngày kết thúc: ");
					String day2 = sc.nextLine();
					DateTimeFormatter sdf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
					try {
						LocalDate start = LocalDate.parse(day1, sdf);
						LocalDate end = LocalDate.parse(day2, sdf);
						for (PhieuNhapHang pnh : dsp) {
							LocalDate day = LocalDate.parse(sdf.format(pnh.getNgayNhap()), sdf);

							if (day.isEqual(start) || day.isAfter(start) && day.isBefore(end)) {
								result = addPhieuNhapHang(result, pnh);
							}
						}
					} catch (DateTimeParseException e) {
						e.printStackTrace();
					}
				}
			}
		}

		System.out.println("Danh sách phiếu nhập tìm được:");
		String header = String.format("| %-5s | %-20s | %-25s | %-15s | %-20s |", "ID", "Nhân viên lập đơn", "Tên nhà cung cấp", "Giá nhập", "Ngày nhập");
		System.out.format(
				"+-------+----------------------+---------------------------+-----------------+----------------------+%n");
		System.out.println(header);
		System.out.format(
				"+-------+----------------------+---------------------------+-----------------+----------------------+%n");
		for (PhieuNhapHang pnh : result) {
			String read = String.format("| %-5s | %-20s | %-25s | %-15s | %-20s |", pnh.getID_PhieuNhap(), pnh.getID_Worker(), pnh.getName_Supplier(), pnh.getPrice(), pnh.getNgayNhap());
			System.out.println(read);
		}
		System.out.format(
				"+-------+----------------------+---------------------------+-----------------+----------------------+%n");
		waitConsole();
	}
}