package controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

import models.Configuration;

public class QuanLyConfigurations implements ControllerInterface {
  private static QuanLyConfigurations instance;
  private static Scanner input = new Scanner(System.in);
  private Configuration[] cfg;

  private QuanLyConfigurations() {
  }

  public static QuanLyConfigurations getInstance() {
    if (instance == null) {
      instance = new QuanLyConfigurations();
    }
    return instance;
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
      cfg[i] = new Configuration(row[0],row[1], row[2], row[3], row[4], row[5], row[6], row[7], row[8]);
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
    String header = String.format("| %-5s | %-10s | %-15s | %-25s | %-10s | %-10s | %-15s | %-15s | %-25s |", "ID",
        "Màn hình", "Hệ điều hành", "Cấu hình", "RAM", "ROM", "Dung lượng pin", "Camera trước", "Camera Sau");
    System.out.format(
        "+-------+------------+-----------------+---------------------------+------------+------------+-----------------+-----------------+---------------------------+%n");
    System.out.println(header);
    System.out.format(
        "+-------+------------+-----------------+---------------------------+------------+------------+-----------------+-----------------+---------------------------+%n");

    getListConfigurations();
    for (Configuration configuation : cfg) {
      String read = String.format("| %-5s | %-10s | %-15s | %-25s | %-10s | %-10s | %-15s | %-15s | %-25s |",
          configuation.getImeiCode(), configuation.getScreen(), configuation.getOs(), configuation.getChip(),
          configuation.getRam(), configuation.getRom(), configuation.getBattery(),
          configuation.getFrontCamera(), configuation.getRearCamera());
      System.out.println(read);
    }
    System.out.format(
        "+-------+------------+-----------------+---------------------------+------------+------------+-----------------+-----------------+---------------------------+%n");
    waitConsole();
  }

  // Thêm phần tử vào mảng có giao diện
  @Override
  public void Create() {
    Configuration cfgModel = new Configuration();
    System.out.println("\t\t\t\t\t\t\t\t +----NHẬP THÔNG TIN CẤU HÌNH SẢN PHẨM----+");
    String ID_Code;
			do {
				System.out.print("Nhập Mã sản phẩm:(cfg[0-n])-MAX(cfg99)");
				ID_Code = input.nextLine();
				if(ID_Code.isEmpty() || !ID_Code.matches("^cfg[0-9]+$") ||ID_Code.length()>5){
					System.out.println("Bạn nhập không đúng định dạng hãy nhập lại.");
				}

			} while (ID_Code.isEmpty() || !ID_Code.matches("^cfg[0-9]+$") ||ID_Code.length()>5);

    cfgModel.setImeiCode(ID_Code);
    int check = 0;
    for (Configuration configuation : cfg) {
      if (cfgModel.getImeiCode().equals(configuation.getImeiCode())) {
        check = 1;
        break;
      }
    }

    if (check == 1) {
      System.out.println("\t\t\t\t\t\t\t\t -MÃ SẢN PHẨM BỊ TRÙNG!");
      return;
    }
    System.out.println("Nhập thông số bắt buộc phải có khoảng cách theo định dạng đã ví dụ");
		
		String screen;
		do {
			System.out.print("Nhập kích thước màn hình: (99.99) inch -Không vượt quá 11 kí tự  :");	
			screen = input.nextLine();
			if(screen.isEmpty()|| !screen.matches("^[0-9]+\\.[0-9]{2}\\s(inch)$") ||screen.length()>11){
				System.out.println("Bạn nhập không đúng định dạng hãy nhập lại.");
			}
		} while (screen.isEmpty()|| !screen.matches("^[0-9]+\\.[0-9]{2}\\s(inch)$") ||screen.length()>11);
		cfgModel.setScreen(screen);

		String OS;
		do {
			System.out.print("Nhập Hệ Điều Hành:(IOS,Android,...)-Không vượt quá 15 kí tự  : ");
			OS = input.nextLine();
			if(OS.isEmpty()|| OS.length()>15){
				System.out.println("Bạn nhập không đúng định dạng hãy nhập lại.");
			}
		} while (OS.isEmpty()|| OS.length()>15);
		cfgModel.setOs(OS);

		String CHIP;
		do {
			System.out.print("Nhập CHIP:(Snapdragon 675 8 nhân,....)-Không vượt quá 20 kí tự  :");
			CHIP = input.nextLine();
			if(CHIP.isEmpty()|| CHIP.length()>21){
				System.out.println("Bạn nhập không đúng định dạng hãy nhập lại.");
			}
		} while (CHIP.isEmpty()|| CHIP.length()>21);
		cfgModel.setChip(CHIP);

		String RAM;
		do {
			System.out.print("Nhập RAM:(3,5,8,...,99) GB -Không vượt quá 5 kí tự  :");
			RAM = input.nextLine();
			if(RAM.isEmpty()||!RAM.matches("^[0-9]+\\s(GB)$")|| RAM.length()>5){
				System.out.println("Bạn nhập không đúng định dạng hãy nhập lại.");
			}
		} while (RAM.isEmpty()|| !RAM.matches("^[0-9]+\\s(GB)$")|| RAM.length()>5);
		cfgModel.setRam(RAM);

		String ROM;
		do {
			System.out.print("Nhập ROM:(16,32,64,128,...,9999) GB -Không vượt quá 7 kí tự  :");
			ROM = input.nextLine();
			if(ROM.isEmpty()|| !ROM.matches("^[0-9]+\\s(GB)$")|| ROM.length()>7){
				System.out.println("Bạn nhập không đúng định dạng hãy nhập lại.");
			}
		} while (ROM.isEmpty()|| !ROM.matches("^[0-9]+\\s(GB)$")||ROM.length()>7);
		cfgModel.rom(ROM);

		String Battery;
		do {
			System.out.print("Nhập Dung Lượng Pin Của Điện Thoại:(4000,5000,....) mAh -Không vượt quá 9 kí tự  :");
			Battery = input.nextLine();
			if(Battery.isEmpty()|| !Battery.matches("^[0-9]+\\s(mAh)$") || Battery.length()>9){
				System.out.println("Bạn nhập không đúng định dạng hãy nhập lại.");
			}
		} while (Battery.isEmpty()|| !Battery.matches("^[0-9]+\\s(mAh)$")||Battery.length()>9);
		cfgModel.setBattery(Battery);

		String Front_Camera;
		do {
			System.out.print("Nhập Thông Số Camera trước:(16 MP,32 MP,......) -Không vượt quá 6 kí tự :");
			Front_Camera = input.nextLine();
			if(Front_Camera.isEmpty()|| Front_Camera.length()>10){
				System.out.println("Bạn nhập không đúng định dạng hãy nhập lại.");
			}
		} while (Front_Camera.isEmpty()|| Front_Camera.length()>10);
		cfgModel.setFrontCamera(Front_Camera);

		String Rear_Camera;
		do {
			System.out.print("Nhập Thông Số Camera sau:(Chính 48 MP & Phụ 8 MP1,......) -Không vượt quá 25 kí tự  :");
			Rear_Camera = input.nextLine();
			if(Rear_Camera.isEmpty()|| Rear_Camera.length()>25){
				System.out.println("Bạn nhập không đúng định dạng hãy nhập lại.");
			}
		} while (Rear_Camera.isEmpty()|| Rear_Camera.length()>25);
		cfgModel.setRearCamera(Rear_Camera);


    try {
      String input = cfgModel.getImeiCode() + ";" + cfgModel.getScreen() + ";" + cfgModel.getOs() + ";"
          + cfgModel.getChip() + ";" + cfgModel.getRam() + ";"
          + cfgModel.getRom() + ";" + cfgModel.getBattery() + ";" + cfgModel.getFrontCamera() + ";"
          + cfgModel.getRearCamera();
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
      String IMEIcode;
			do {
				System.out.print(" Mời bạn nhập mã sản phẩm cần chỉnh sửa:(cfg[0-n])");
				IMEIcode = input.nextLine();
				if(IMEIcode.isEmpty() || !IMEIcode.matches("^cfg[0-9]+$") ||IMEIcode.length()>6){
					System.out.println("Bạn nhập không đúng định dạng hãy nhập lại.");
				}

			} while (IMEIcode.isEmpty() || !IMEIcode.matches("^cfg[0-9]+$") ||IMEIcode.length()>6);

      Configuration IMEIcode_cur = null;


      for (Configuration config : cfg) {
        if (config.getImeiCode().equals(IMEIcode)) {
          IMEIcode_cur = config;
          break;
        }
      }

      if (IMEIcode_cur == null) {
        System.out.println("\t\t\t\t\t\t\t\t - MÃ SẢN PHẨM KHÔNG TỒN TẠI!");
        return;
      }

      System.out.println("\t\t\t\t\t\t\t\t +----THÔNG TIN CONFIGURATION TRƯỚC KHI CHỈNH SỬA----+");
      String header = String.format("| %-5s | %-10s | %-15s | %-25s | %-10s | %-10s | %-15s | %-15s | %-25s |",
          "ID", "Màn hình", "Hệ điều hành", "Cấu hình", "RAM", "ROM", "Dung lượng pin", "Camera trước",
          "Camera Sau");
      System.out.format(
          "+------------------------------------------------------------------------------------------------------------------------------------------------------------+%n");
      System.out.println(header);
      System.out.format(
          "+------------------------------------------------------------------------------------------------------------------------------------------------------------+%n");
      String row = String.format("| %-5s | %-10s | %-15s | %-25s | %-10s | %-10s | %-15s | %-15s | %-25s |",
          IMEIcode_cur.getImeiCode(), IMEIcode_cur.getScreen(), IMEIcode_cur.getOs(), IMEIcode_cur.getChip(),
          IMEIcode_cur.getRam(), IMEIcode_cur.getRom(),
          IMEIcode_cur.getBattery(), IMEIcode_cur.getFrontCamera(), IMEIcode_cur.getRearCamera());
      System.out.println(row);
      System.out.format(
          "+------------------------------------------------------------------------------------------------------------------------------------------------------------+%n");
      String[] data = new String[cfg.length];

      for (int i = 0; i < cfg.length; i++) {
        if (cfg[i].getImeiCode().equals(IMEIcode)) {

          String screen;
					do {
						System.out.print("Nhập kích thước màn hình: (99.99) inch -Không vượt quá 11 kí tự  :");	
						screen = input.nextLine();
						if(screen.isEmpty()|| !screen.matches("^[0-9]+\\.[0-9]{2}\\s(inch)$") ||screen.length()>11){
							System.out.println("Bạn nhập không đúng định dạng hãy nhập lại.");
						}
					} while (screen.isEmpty()|| !screen.matches("^[0-9]+\\.[0-9]{2}\\s(inch)$") ||screen.length()>11);
					IMEIcode_cur.setScreen(screen);

					String OS;
					do {
						System.out.print("Nhập Hệ Điều Hành:(IOS,Android,...)-Không vượt quá 15 kí tự  : ");
						OS = input.nextLine();
						if(OS.isEmpty()|| OS.length()>15){
							System.out.println("Bạn nhập không đúng định dạng hãy nhập lại.");
						}
					} while (OS.isEmpty()|| OS.length()>15);
					IMEIcode_cur.setOs(OS);

					String CHIP;
					do {
						System.out.print("Nhập CHIP:(Snapdragon 675 8 nhân,....)-Không vượt quá 20 kí tự  :");
						CHIP = input.nextLine();
						if(CHIP.isEmpty()|| CHIP.length()>21){
							System.out.println("Bạn nhập không đúng định dạng hãy nhập lại.");
						}
					} while (CHIP.isEmpty()|| CHIP.length()>21);
					IMEIcode_cur.setChip(CHIP);

					String RAM;
					do {
						System.out.print("Nhập RAM:(3,5,8,...,99) GB -Không vượt quá 5 kí tự  :");
						RAM = input.nextLine();
						if(RAM.isEmpty()||!RAM.matches("^[0-9]+\\s(GB)$")|| RAM.length()>5){
							System.out.println("Bạn nhập không đúng định dạng hãy nhập lại.");
						}
					} while (RAM.isEmpty()|| !RAM.matches("^[0-9]+\\s(GB)$")|| RAM.length()>5);
					IMEIcode_cur.setRam(RAM);

					String ROM;
					do {
						System.out.print("Nhập ROM:(16,32,64,128,...,9999) GB -Không vượt quá 7 kí tự  :");
						ROM = input.nextLine();
						if(ROM.isEmpty()|| !ROM.matches("^[0-9]+\\s(GB)$")|| ROM.length()>8){
							System.out.println("Bạn nhập không đúng định dạng hãy nhập lại.");
						}
					} while (ROM.isEmpty()|| !ROM.matches("^[0-9]+\\s(GB)$")||ROM.length()>8);
					IMEIcode_cur.rom(ROM);

					String Battery;
					do {
						System.out.print("Nhập Dung Lượng Pin Của Điện Thoại:(4000,5000,....10000) mAh -Không vượt quá 9 kí tự  :");
						Battery = input.nextLine();
						if(Battery.isEmpty()|| !Battery.matches("^[0-9]+\\s(mAh)$") || Battery.length()>9){
							System.out.println("Bạn nhập không đúng định dạng hãy nhập lại.");
						}
					} while (Battery.isEmpty()|| !Battery.matches("^[0-9]+\\s(mAh)$")||Battery.length()>9);
					IMEIcode_cur.setBattery(Battery);

					String Front_Camera;
					do {
						System.out.print("Nhập Thông Số Camera trước:(16 MP,32 MP,......) -Không vượt quá 6 kí tự :");
						Front_Camera = input.nextLine();
						if(Front_Camera.isEmpty()|| Front_Camera.length()>6){
							System.out.println("Bạn nhập không đúng định dạng hãy nhập lại.");
						}
					} while (Front_Camera.isEmpty()|| Front_Camera.length()>6);
					IMEIcode_cur.setFrontCamera(Front_Camera);

					String Rear_Camera;
					do {
						System.out.print("Nhập Thông Số Camera sau:(Chính 48 MP & Phụ 8 MP1,......) -Không vượt quá 25 kí tự  :");
						Rear_Camera = input.nextLine();
						if(Rear_Camera.isEmpty()|| Rear_Camera.length()>25){
							System.out.println("Bạn nhập không đúng định dạng hãy nhập lại.");
						}
					} while (Rear_Camera.isEmpty()|| Rear_Camera.length()>25);
					IMEIcode_cur.setRearCamera(Rear_Camera);

          cfg[i].setScreen(IMEIcode_cur.getScreen());
          cfg[i].setOs(IMEIcode_cur.getOs());
          cfg[i].setChip(IMEIcode_cur.getChip());
          cfg[i].setRam(IMEIcode_cur.getRam());
          cfg[i].setRom(IMEIcode_cur.getRom());
          cfg[i].setBattery(IMEIcode_cur.getBattery());
          cfg[i].setFrontCamera(IMEIcode_cur.getFrontCamera());
          cfg[i].setRearCamera(IMEIcode_cur.getRearCamera());
        }
        data[i] = cfg[i].getImeiCode() + ";" + cfg[i].getScreen() + ";" + cfg[i].getOs() + ";"
            + cfg[i].getChip() + ";"
            + cfg[i].getRam() + ";" + cfg[i].getRam() + ";" + cfg[i].getBattery() + ";"
            + cfg[i].getFrontCamera() + ";" + cfg[i].getRearCamera();
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
      String IMEIcode;
			do {
				System.out.print("Nhập mã sản phẩm cần xóa:(cfg[0-n])");
				IMEIcode = input.nextLine();
				if(IMEIcode.isEmpty() || !IMEIcode.matches("^cfg[0-9]+$") ||IMEIcode.length()>6){
					System.out.println("Bạn nhập không đúng định dạng hãy nhập lại.");
				}

			} while (IMEIcode.isEmpty() || !IMEIcode.matches("^cfg[0-9]+$") ||IMEIcode.length()>6);


      Configuration cur_product = null;
      for (Configuration config : cfg) {
        if (config.getImeiCode().equals(IMEIcode)) {
          cur_product = config;
          break;
        }
      }
      for (int i = 0; i < cfg.length; i++) {
        if (IMEIcode.equals(cfg[i].getImeiCode())) {
          cfg = deleteConfiguration(cfg, i);
          break;
        }
      }

      if (cur_product == null) {
        System.out.println("\t\t\t\t\t\t\t\t - MÃ SẢN PHẨM KHÔNG TỒN TẠI!");
        return;
      }

      for (int i = 0; i < cfg.length; i++) {
        if (IMEIcode.equals(cfg[i].getImeiCode())) {
          cfg = deleteConfiguration(cfg, i);
          break;
        }
      }
      String[] data = new String[cfg.length];
      for (int i = 0; i < cfg.length; i++) {
        data[i] = cfg[i].getImeiCode() + ";" + cfg[i].getScreen() + ";" + cfg[i].getOs() + ";"
            + cfg[i].getChip() + ";" + cfg[i].getRam() + ";" + cfg[i].getRam() + ";" + cfg[i].getBattery()
            + ";" + cfg[i].getFrontCamera() + ";" + cfg[i].getRearCamera();
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
  public void searchByCategory() {
    Configuration[] result = new Configuration[0];
		System.out.println("\t\t\t\t\t\t\t\t +--------NHẬP MỤC LỤC CẨN TÌM KIẾM--------+");
		System.out.println("\t\t\t\t\t\t\t\t |            (Nhập số khác).Thoát         |");
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
		String choose = input.nextLine();
		if (choose == "0")
			return;
		else {
			switch (choose) {
				case "1" -> {
					System.out.print("Nhập mã sản phẩm: ");
					String ID_cauhinh = input.nextLine();
					for (Configuration configuation : cfg) {
						if (configuation.getImeiCode().toLowerCase().contains(ID_cauhinh.toLowerCase())) {
							result = addConfiguration(result, configuation);
						}
					}
				}
				case "2" -> {
					System.out.print("Nhập Screen: ");
					String Screen = input.nextLine();
					for (Configuration configuation : cfg) {
						if (configuation.getScreen().toLowerCase().contains(Screen.toLowerCase())) {
							result = addConfiguration(result, configuation);
						}
					}
				}
				case "3" -> {
					System.out.print("Nhập OS: ");
					String OS = input.nextLine();
					for (Configuration configuation : cfg) {
						if (configuation.getOs().toLowerCase().contains(OS.toLowerCase())) {
							result = addConfiguration(result, configuation);
						}
					}
				}
				case "4" -> {
					System.out.print("Nhập Chip: ");
					String Chip = input.nextLine();
					for (Configuration configuation : cfg) {
						if (configuation.getChip().toLowerCase().contains(Chip.toLowerCase())) {
							result = addConfiguration(result, configuation);
						}
					}
				}
				case "5" -> {
					System.out.print("Nhập Ram: ");
					String Ram = input.nextLine();
					for (Configuration configuation : cfg) {
						if (configuation.getRam().toLowerCase().contains(Ram.toLowerCase())) {
							result = addConfiguration(result, configuation);
						}
					}
				}
				case "6" -> {
					System.out.print("Nhập Rom: ");
					String ROM = input.nextLine();
					for (Configuration configuation : cfg) {
						if (configuation.getRom().toLowerCase().contains(ROM.toLowerCase())) {
							result = addConfiguration(result, configuation);
						}
					}
				}
				case "7" -> {
					System.out.print("Nhập Battery: ");
					String Battery = input.nextLine();
					for (Configuration configuation : cfg) {
						if (configuation.getBattery().toLowerCase().contains(Battery.toLowerCase())) {
							result = addConfiguration(result, configuation);
						}
					}
				}
				case "8" -> {
					System.out.print("Nhập Front Camera: ");
					String FrontCamera = input.nextLine();
					for (Configuration configuation : cfg) {
						if (configuation.getFrontCamera().toLowerCase().contains(FrontCamera.toLowerCase())) {
							result = addConfiguration(result, configuation);
						}
					}
				}
				case "9" -> {
					System.out.print("Nhập Rear Camera: ");
					String RearCamera = input.nextLine();
					for (Configuration configuation : cfg) {
						if (configuation.getRearCamera().toLowerCase().contains(RearCamera.toLowerCase())) {
							result = addConfiguration(result, configuation);
						}
					}
				}
				default -> {
					return;
				}
			}
		}
    System.out.println("\t\t\t\t\t\t\t\t +----TẤT CẢ THÔNG TIN ĐÃ TÌM ĐƯỢC----+");
    String header = String.format("| %-5s | %-10s | %-15s | %-25s | %-10s | %-10s | %-15s | %-15s | %-25s |", "ID",
        "Màn hình", "Hệ điều hành", "Cấu hình", "RAM", "ROM", "Dung lượng pin", "Camera trước", "Camera Sau");
    System.out.format(
        "+-------+------------+-----------------+---------------------------+------------+------------+-----------------+-----------------+---------------------------+%n");
    System.out.println(header);
 System.out.format(
        "+-------+------------+-----------------+---------------------------+------------+------------+-----------------+-----------------+---------------------------+%n");

    for (Configuration configuation : result) {
      String read = String.format("| %-5s | %-10s | %-15s | %-25s | %-10s | %-10s | %-15s | %-15s | %-25s |",
          configuation.getImeiCode(), configuation.getScreen(), configuation.getOs(),
          configuation.getChip(), configuation.getRam(), configuation.getRom(), configuation.getBattery(),
          configuation.getFrontCamera(), configuation.getRearCamera());
      System.out.println(read);
    }
	System.out.format(
        "+-------+------------+-----------------+---------------------------+------------+------------+-----------------+-----------------+---------------------------+%n");

    waitConsole();
  }
}