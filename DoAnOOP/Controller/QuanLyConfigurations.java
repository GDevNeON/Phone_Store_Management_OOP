package Controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

import Entity.Configuration;

public class QuanLyConfigurations extends Configuration implements ControllerInterface {
	static Scanner input = new Scanner(System.in);
	public Configuration[] cfg;

	public QuanLyConfigurations() {
		super();
	}

	public Configuration[] getListConfigurations() {
		String[] result = new String[0];
		try {
			result = Stream.read("Database/CauHinh.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		cfg = new Configuration[result.length];
		for (int i = 0; i < result.length; i++) {
			String[] row = result[i].split(";");
			cfg[i] = new Configuration(row[0], row[1], row[2], row[3], row[4], row[5], row[6], row[7], row[8]);
		}
		return cfg;
	}

	public void waitConsole() {
		input.nextLine();
		System.out.println("\t\t\t\t\t\t\t\t -Ấn Enter để tiếp tục");
		input.nextLine();
	}

	@Override
	public void Read() {
		System.out.println("\t\t\t\t\t\t\t\t +----DANH SÁCH THÔNG TIN CẤU HÌNH----+");
		String header = String.format("| %-5s | %-10s | %-15s | %-25s | %-10s | %-10s | %-15s | %-15s | %-25s |", "ID", "Màn hình", "Hệ điều hành", "Cấu hình", "RAM", "ROM", "Dung lượng pin", "Camera trước", "Camera Sau");
		System.out.format("+-------+------------+-----------------+---------------------------+------------+------------+-----------------+-----------------+---------------------------+%n");
		System.out.println(header);
		System.out.format("+-------+------------+-----------------+---------------------------+------------+------------+-----------------+-----------------+---------------------------+%n");

		getListConfigurations();
		for (Configuration configuation : cfg) {
			String read = String.format("| %-5s | %-10s | %-15s | %-25s | %-10s | %-10s | %-15s | %-15s | %-25s |",
					configuation.getIMEI_Code(), configuation.getScreen(), configuation.getOS(), configuation.getChip(), configuation.getRam(), configuation.getROM(), configuation.getBattery(), configuation.getFrontCamera(), configuation.getRearCamera());
			System.out.println(read);
		}
		System.out.format("+-------+------------+-----------------+---------------------------+------------+------------+-----------------+-----------------+---------------------------+%n");
		waitConsole();
	}

	// Thêm phần tử vào mảng có giao diện
	@Override
	public void Create() {
		System.out.println("\t\t\t\t\t\t\t\t +----NHẬP THÔNG TIN CẤU HÌNH SẢN PHẨM----+");
		String ID_Code;
		do {
			System.out.print("Nhập Mã sản phẩm: ");
			ID_Code = input.nextLine();
		} while (ID_Code.isEmpty());
		setIMEI_Code(ID_Code);
		int check = 0;
		for (Configuration configuation : cfg) {
			if (getIMEI_Code().equals(configuation.getIMEI_Code())) {
				check = 1;
				break;
			}
		}

		if (check == 1) {
			System.out.println("\t\t\t\t\t\t\t\t -MÃ SẢN PHẨM BỊ TRÙNG!");
			return;
		}
		String screen;
		do {
			System.out.print("Nhập Screen: ");
			screen = input.nextLine();
		} while (screen.isEmpty());
		setScreen(screen);

		String OS;
		do {
			System.out.print("Nhập OS: ");
			OS = input.nextLine();
		} while (OS.isEmpty());
		setOS(OS);

		String CHIP;
		do {
			System.out.print("Nhập CHIP: ");
			CHIP = input.nextLine();
		} while (CHIP.isEmpty());
		setChip(CHIP);

		String RAM;
		do {
			System.out.print("Nhập RAM: ");
			RAM = input.nextLine();
		} while (RAM.isEmpty());
		setRam(RAM);

		String ROM;
		do {
			System.out.print("Nhập ROM: ");
			ROM = input.nextLine();
		} while (ROM.isEmpty());
		setROM(ROM);

		String Battery;
		do {
			System.out.print("Nhập Battery: ");
			Battery = input.nextLine();
		} while (Battery.isEmpty());
		setBattery(Battery);

		String Front_Camera;
		do {
			System.out.print("Nhập Front Camera: ");
			Front_Camera = input.nextLine();
		} while (Front_Camera.isEmpty());
		setFrontCamera(Front_Camera);

		String Rear_Camera;
		do {
			System.out.print("Nhập Rear Camera: ");
			Rear_Camera = input.nextLine();
		} while (Rear_Camera.isEmpty());
		setRearCamera(Rear_Camera);


		try {
			String input = getIMEI_Code() + ";" + getScreen() + ";" + getOS() + ";" + getChip() + ";" + getRam() + ";" + getROM() + ";" + getBattery() + ";" + getFrontCamera() + ";" + getRearCamera();
			Stream.addOneLine("Database/CauHinh.txt", input);
			System.out.println("\t\t\t\t\t\t\t\t +----NHẬP CONFIGURATION THÀNH CÔNG----+");
			waitConsole();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void Update() {
		try {
			System.out.println("\t\t\t\t\t\t\t\t +----CẬP NHẬT LẠI THÔNG TIN CONFIGURATION----+");
			input.nextLine();
			System.out.print("\t\t\t\t\t\t\t\t - Mời bạn nhập mã sản phẩm cần chỉnh sửa: ");
			String IMEIcode = input.nextLine();
			Configuration IMEIcode_cur = null;

			for (Configuration config : cfg) {
				if (config.getIMEI_Code().equals(IMEIcode)) {
					IMEIcode_cur = config;
					break;
				}
			}

			if (IMEIcode_cur == null) {
				System.out.println("\t\t\t\t\t\t\t\t - MÃ SẢN PHẨM KHÔNG TỒN TẠI!");
				return;
			}

			System.out.println("\t\t\t\t\t\t\t\t +----THÔNG TIN CONFIGURATION TRƯỚC KHI CHỈNH SỬA----+");
			String header = String.format("| %-5s | %-10s | %-15s | %-25s | %-10s | %-10s | %-15s | %-15s | %-25s |", "ID", "Màn hình", "Hệ điều hành", "Cấu hình", "RAM", "ROM", "Dung lượng pin", "Camera trước", "Camera Sau");
			System.out.format("+------------------------------------------------------------------------------------------------------------------------------------------------------------+%n");
			System.out.println(header);
			System.out.format("+------------------------------------------------------------------------------------------------------------------------------------------------------------+%n");
			String row = String.format("| %-5s | %-10s | %-15s | %-25s | %-10s | %-10s | %-15s | %-15s | %-25s |",
					IMEIcode_cur.getIMEI_Code(), IMEIcode_cur.getScreen(), IMEIcode_cur.getOS(), IMEIcode_cur.getChip(), IMEIcode_cur.getRam(), IMEIcode_cur.getROM(),
					IMEIcode_cur.getBattery(), IMEIcode_cur.getFrontCamera(), IMEIcode_cur.getRearCamera());
			System.out.println(row);
			System.out.format("+------------------------------------------------------------------------------------------------------------------------------------------------------------+%n");
			String[] data = new String[cfg.length];

			for (int i = 0; i < cfg.length; i++) {
				if (cfg[i].getIMEI_Code().equals(IMEIcode)) {

					String screen;
					do {
						System.out.print("Nhập Screen: ");
						screen = input.nextLine();
					} while (screen.isEmpty());
					setScreen(screen);

					String OS;
					do {
						System.out.print("Nhập OS: ");
						OS = input.nextLine();
					} while (OS.isEmpty());
					setOS(OS);

					String CHIP;
					do {
						System.out.print("Nhập CHIP: ");
						CHIP = input.nextLine();
					} while (CHIP.isEmpty());
					setChip(CHIP);

					String RAM;
					do {
						System.out.print("Nhập RAM: ");
						RAM = input.nextLine();
					} while (RAM.isEmpty());
					setRam(RAM);

					String ROM;
					do {
						System.out.print("Nhập ROM: ");
						ROM = input.nextLine();
					} while (ROM.isEmpty());
					setROM(ROM);

					String Battery;
					do {
						System.out.print("Nhập Battery: ");
						Battery = input.nextLine();
					} while (Battery.isEmpty());
					setBattery(Battery);

					String Front_Camera;
					do {
						System.out.print("Nhập Front Camera: ");
						Front_Camera = input.nextLine();
					} while (Front_Camera.isEmpty());
					setFrontCamera(Front_Camera);

					String Rear_Camera;
					do {
						System.out.print("Nhập Rear Camera: ");
						Rear_Camera = input.nextLine();
					} while (Rear_Camera.isEmpty());
					setRearCamera(Rear_Camera);


					cfg[i].setScreen(getScreen());
					cfg[i].setOS(getOS());
					cfg[i].setChip(getChip());
					cfg[i].setRam(getRam());
					cfg[i].setROM(getROM());
					cfg[i].setBattery(getBattery());
					cfg[i].setFrontCamera(getFrontCamera());
					cfg[i].setRearCamera(getRearCamera());
				}
				data[i] = cfg[i].getIMEI_Code() + ";" + cfg[i].getScreen() + ";" + cfg[i].getOS() + ";" + cfg[i].getChip() + ";"
						+ cfg[i].getRam() + ";" + cfg[i].getRam() + ";" + cfg[i].getBattery() + ";" + cfg[i].getFrontCamera() + ";" + cfg[i].getRearCamera();
			}
			try {
				Stream.addAll("Database/CauHinh.txt", data);
				System.out.println("\t\t\t\t\t\t\t\t+----SỬA THÔNG TIN CONFIGURATION THÀNH CÔNG----+");
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
			System.out.println("\t\t\t\t\t\t\t\t +----XÓA THÔNG TIN CONFUGURATION----+");
			System.out.println("\t\t\t\t\t\t\t\t -Nhập mã sản phẩm cần xóa: ");
			String IMEIcode = input.nextLine();

			Configuration cur_product = null;
			for (Configuration config : cfg) {
				if (config.getIMEI_Code().equals(IMEIcode)) {
					cur_product = config;
					break;
				}
			}
			for (int i = 0; i < cfg.length; i++) {
				if (IMEIcode.equals(cfg[i].getIMEI_Code())) {
					cfg = deleteConfiguration(cfg, i);
					break;
				}
			}

			if (cur_product == null) {
				System.out.println("\t\t\t\t\t\t\t\t - MÃ SẢN PHẨM KHÔNG TỒN TẠI!");
				return;
			}

			for (int i = 0; i < cfg.length; i++) {
				if (IMEIcode.equals(cfg[i].getIMEI_Code())) {
					cfg = deleteConfiguration(cfg, i);
					break;
				}
			}
			String[] data = new String[cfg.length];
			for (int i = 0; i < cfg.length; i++) {
				data[i] = cfg[i].getIMEI_Code() + ";" + cfg[i].getScreen() + ";" + cfg[i].getOS() + ";" + cfg[i].getChip() + ";" + cfg[i].getRam() + ";" + cfg[i].getRam() + ";" + cfg[i].getBattery() + ";" + cfg[i].getFrontCamera() + ";" + cfg[i].getRearCamera();
			}
			try {
				Stream.addAll("Database/CauHinh.txt", data);
				System.out.println("\t\t\t\t\t\t\t\t +----XÓA THÔNG TIN CONFIGURATION THÀNH CÔNG----+");
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

	public Configuration[] deleteConfiguration(Configuration[] cfg, int index) {
		Configuration[] newCs = new Configuration[cfg.length - 1];
		for (int i = 0, j = 0; i < cfg.length; i++) {
			if (i != index) {
				newCs[j++] = cfg[i];
			}
		}
		return newCs;
	}

	public Configuration[] addConfiguration(Configuration[] cfg, Configuration configuation) {
		cfg = Arrays.copyOf(cfg, cfg.length + 1);
		cfg[cfg.length - 1] = configuation;
		return cfg;
	}

	@Override
	public void Search_byCategory() {
		Configuration[] result = new Configuration[0];
		System.out.println("\t\t\t\t\t\t\t\t +--------NHẬP MỤC LỤC CẨN TÌM KIẾM--------+");
		System.out.println("\t\t\t\t\t\t\t\t |               0.Thoát                   |");
		System.out.println("\t\t\t\t\t\t\t\t +-----------------------------------------+");
		System.out.println("\t\t\t\t\t\t\t\t |1.Mã sản phẩm                            |");
		System.out.println("\t\t\t\t\t\t\t\t |2.Màn hình                               |");
		System.out.println("\t\t\t\t\t\t\t\t |3.Hệ điều hành                           |");
		System.out.println("\t\t\t\t\t\t\t\t |4.Cấu hình chip                          |");
		System.out.println("\t\t\t\t\t\t\t\t |5.RAM                                    |");
		System.out.println("\t\t\t\t\t\t\t\t |6.ROM                                    |");
		System.out.println("\t\t\t\t\t\t\t\t |7.Dung lượng pin                         |");
		System.out.println("\t\t\t\t\t\t\t\t |8.Số pixel camera trước                  |");
		System.out.println("\t\t\t\t\t\t\t\t |9.Số pixel camera sau                    |");
		System.out.println("\t\t\t\t\t\t\t\t +-----------------------------------------+");
		System.out.print("\t\t\t\t\t\t\t\t - Mời Bạn Nhập Lựa Chọn: ");
		int choose = input.nextInt();
		if (choose == 0)
			return;
		else {
			switch (choose) {
				case 1 -> {
					input.nextLine();
					System.out.print("Nhập mã sản phẩm: ");
					String ID_cauhinh = input.nextLine();
					for (Configuration configuation : cfg) {
						if (configuation.getIMEI_Code().toLowerCase().contains(ID_cauhinh.toLowerCase())) {
							result = addConfiguration(result, configuation);
						}
					}
				}
				case 2 -> {
					input.nextLine();
					System.out.print("Nhập Screen: ");
					String Screen = input.nextLine();
					for (Configuration configuation : cfg) {
						if (configuation.getScreen().toLowerCase().contains(Screen.toLowerCase())) {
							result = addConfiguration(result, configuation);
						}
					}
				}
				case 3 -> {
					System.out.print("Nhập OS: ");
					input.nextLine();
					String OS = input.nextLine();
					for (Configuration configuation : cfg) {
						if (configuation.getOS().toLowerCase().contains(OS.toLowerCase())) {
							result = addConfiguration(result, configuation);
						}
					}
				}
				case 4 -> {
					input.nextLine();
					System.out.print("Nhập Chip: ");
					String Chip = input.nextLine();
					for (Configuration configuation : cfg) {
						if (configuation.getChip().toLowerCase().contains(Chip.toLowerCase())) {
							result = addConfiguration(result, configuation);
						}
					}
				}
				case 5 -> {
					System.out.print("Nhập Ram: ");
					input.nextLine();
					String Ram = input.nextLine();
					for (Configuration configuation : cfg) {
						if (configuation.getRam().toLowerCase().contains(Ram.toLowerCase())) {
							result = addConfiguration(result, configuation);
						}
					}
				}
				case 6 -> {
					System.out.print("Nhập Rom: ");
					input.nextLine();
					String ROM = input.nextLine();
					for (Configuration configuation : cfg) {
						if (configuation.getROM().toLowerCase().contains(ROM.toLowerCase())) {
							result = addConfiguration(result, configuation);
						}
					}
				}
				case 7 -> {
					System.out.print("Nhập Battery: ");
					input.nextLine();
					String Battery = input.nextLine();
					for (Configuration configuation : cfg) {
						if (configuation.getBattery().toLowerCase().contains(Battery.toLowerCase())) {
							result = addConfiguration(result, configuation);
						}
					}
				}
				case 8 -> {
					System.out.print("Nhập Front Camera: ");
					input.nextLine();
					String FrontCamera = input.nextLine();
					for (Configuration configuation : cfg) {
						if (configuation.getFrontCamera().toLowerCase().contains(FrontCamera.toLowerCase())) {
							result = addConfiguration(result, configuation);
						}
					}
				}
				case 9 -> {
					System.out.print("Nhập Rear Camera: ");
					input.nextLine();
					String RearCamera = input.nextLine();
					for (Configuration configuation : cfg) {
						if (configuation.getRearCamera().toLowerCase().contains(RearCamera.toLowerCase())) {
							result = addConfiguration(result, configuation);
						}
					}
				}
			}
		}
		System.out.println("\t\t\t\t\t\t\t\t +----TẤT CẢ THÔNG TIN ĐÃ TÌM ĐƯỢC----+");
		String header = String.format("| %-5s | %-10s | %-15s | %-25s | %-10s | %-10s | %-15s | %-15s | %-25s |", "ID", "Màn hình", "Hệ điều hành", "Cấu hình", "RAM", "ROM", "Dung lượng pin", "Camera trước", "Camera Sau");
		System.out.format("+-------+------------+-----------------+---------------------------+------------+------------+-----------------+-----------------+---------------------------+%n");
		System.out.println(header);

		for (Configuration configuation : result) {
			String read = String.format("| %-5s | %-10s | %-15s | %-25s | %-10s | %-10s | %-15s | %-15s | %-25s |", configuation.getIMEI_Code(), configuation.getScreen(), configuation.getOS(),
					configuation.getChip(), configuation.getRam(), configuation.getROM(), configuation.getBattery(), configuation.getFrontCamera(), configuation.getRearCamera());
			System.out.println(read);
		}
		waitConsole();
	}
}
