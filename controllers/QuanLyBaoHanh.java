package controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

import models.KhachHang;
import models.SanPham;
import models.BaoHanh;

public class QuanLyBaoHanh implements ControllerInterface {
  private static QuanLyBaoHanh instance;
  private Scanner sc = new Scanner(System.in);
  private BaoHanh[] warranty;

  public static QuanLyBaoHanh getInstance() {
    if (instance == null) {
      instance = new QuanLyBaoHanh();
    }
    return instance;
  }

  private QuanLyBaoHanh() {
    getListWarranties();
  }

  // Lấy danh sách bảo hành từ file
  public BaoHanh[] getListWarranties() {
    String[] result = new String[0];
    try {
      result = Stream.read("Database/BaoHanh.txt");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    warranty = new BaoHanh[result.length];
    for (int i = 0; i < result.length; i++) {
      String[] row = result[i].split(";");
      warranty[i] = new BaoHanh(row[0], LocalDate.parse(row[1]), row[2], row[3], row[4], row[5], row[6]);
    }
    return warranty;
  }

  @Override
  public void Read() {
    System.out.println("\t\t\t\t\t\t\t\t +----DANH SÁCH BẢO HÀNH----+");
    OutputHeader();
    getListWarranties();

    for (BaoHanh w : warranty) {
      String read = String.format("| %-11s | %-25s | %-8s | %-20s | %-13s | %-25s | %-12s |",
          w.getProductId(),
          w.getProductDate(),
          w.getYearsOfWarranty(),
          w.getWarrantyMethod(),
          w.getCustomerId(),
          w.getCustomerName(),
          w.getSdt());
      System.out.println(read);
    }
    System.out.format(
        "+-------------+---------------------------+----------+----------------------+---------------+---------------------------+--------------+%n");
    waitConsole();
  }

  @Override
  public void Create() {
    sc.nextLine();
    System.out.println("\t\t\t\t\t\t\t\t +----NHẬP THÔNG TIN BẢO HÀNH----+");
    BaoHanh warrantyModel = new BaoHanh();
    String test;

    System.out.println("Nhập ID Sản phẩm (sp_): ");
		while (true) {
			test = sc.nextLine();
			if (test.isBlank() || (!Validation.isValidIDproduct(test))) {   //nếu như xâu test rỗng hoặc chứa toàn khoảng trắng, NHẬP LẠI ĐEEEEEEEE!!!!
					System.out.println("ID Sản phẩm không hợp lệ. Nhập lại: ");
			} else {
				break;
			}
		}
    warrantyModel.setProductId(test);
    int check = 0;
    SanPham[] spList = QuanLySanPham.getInstance().getListSanPham();
    for (SanPham sp : spList) {
      if (warrantyModel.getProductId().equals(sp.getProductId())) {
        check = 1;
        break;
      }
    }
    if (check == 0) {
      System.out.println("\t\t\t\t\t\t\t\t +----MÃ SẢN PHẨM KHÔNG TỒN TẠI. VUI LÒNG KIỂM TRA LẠI----+");
      return;
    }

    System.out.println("Nhập Ngày sản xuất (yyyy-MM-dd): ");
    while (true) {
        test = sc.nextLine();
        if (test.isBlank() || test.length() != 10) {
            System.out.println("Ngày không hợp lệ. Nhập lại: ");
        } else {
            if (Validation.isValidDate(test)) {
                warrantyModel.setProductDate(LocalDate.parse(test));
                break;
            } else {
                System.out.println("Ngày không hợp lệ. Nhập lại: ");
            }
        }
    }

    System.out.println("Nhập số năm bảo hành (1 -> 5): ");
    while (true) {
        test = sc.nextLine();
        if (!Validation.isInteger(test) || test.isBlank()) {   //nếu như xâu test rỗng hoặc chứa toàn khoảng trắng, NHẬP LẠI ĐEEEEEEEE!!!!
            System.out.println("Phải nhập số nguyên!. Nhập lại: ");
        } else {
            if (Integer.parseInt(test) < 1 || Integer.parseInt(test) > 5) {
                System.out.println("Số năm bảo hành không hợp lệ");
            } else {
                warrantyModel.setYearsOfWarranty(test);
                break;
            }
        }
    }

    System.out.println("Nhập Phương thức bảo hành (tối đa 20 kí tự): ");
    System.out.println("Các Phương thức bảo hành gồm: chinh hang, mo rong, tu nha ban le, tu ben thu ba");
		String verify[] = {"chinh hang", "mo rong", "tu nha ban le", "tu ben thu ba"};
    while (true) {
        test = sc.nextLine();
        if (test.isBlank() || test.length() > 20) {   //nếu như xâu test rỗng hoặc chứa toàn khoảng trắng, NHẬP LẠI ĐEEEEEEEE!!!!
            System.out.println("Phương thức bảo hành không hợp lệ. Nhập lại: ");
        } else {
            boolean flag = false;
              for (String v : verify) {
                if (test.equals(v)) {
                  flag = true;
                  break;
                }
                flag = false;
              }
            if (flag) {
              warrantyModel.setWarrantyMethod(test);
							break;
            }
            else {
              System.out.println("Phương thức bảo hành không hợp lệ. Nhập lại: ");
            }
        }
    }

		System.out.println("Nhập ID Khách hàng (kh_): ");
		sc.nextLine();
		while (true) {
			test = sc.nextLine();
			if (test.isBlank() || !Validation.isValidIDcustomer(test)) {   //nếu như xâu test rỗng hoặc chứa toàn khoảng trắng, NHẬP LẠI ĐEEEEEEEE!!!!
					System.out.println("ID Khách hàng không hợp lệ. Nhập lại: ");
			} else {
				break;
			}
		}
    warrantyModel.setCustomerId(test);
    KhachHang[] customers = QuanLyKhachHang.getInstance().getListCustomer();

    for (KhachHang customer : customers) {
      if (customer.getCustomerId().equalsIgnoreCase(warrantyModel.getCustomerId())) {
        warrantyModel.setCustomerName(customer.getName());
        warrantyModel.setSdt(customer.getSdt());
        break;
      }
    }

    updateList(0, warranty);
    getListWarranties();
  }

  @Override
  public void Update() {
    try {
      sc.nextLine();
      System.out.println("\t\t\t\t\t\t\t\t +----CẬP NHẬT THÔNG TIN BẢO HÀNH----+");
      System.out.println("Nhập ID của sản phẩm cần sửa: ");
      String ID_Product;
      BaoHanh id = null;

			while (true) {
				ID_Product = sc.nextLine();
				if (ID_Product.isBlank()) {   //nếu như xâu test rỗng hoặc chứa toàn khoảng trắng, NHẬP LẠI ĐEEEEEEEE!!!!
						System.out.println("Mã sản phẩm không được để trống. Nhập lại: ");
				} else {
						break;
				}
			}

      for (BaoHanh warrantyModel : warranty) {
        if (warrantyModel.getProductId().equals(ID_Product)) {
          id = warrantyModel;
          break;
        }
      }

      if (id == null) {
        System.out.println("\t\t\t\t\t\t\t\t +-----MÃ SẢN PHẨM KHÔNG TỒN TẠI-----+");
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

      System.out.println("\t\t\t\t\t\t\t\t +----THÔNG TIN BẢO HÀNH TRƯỚC KHI CHỈNH SỬA----+");
      OutputHeader();
      String row = String.format("| %-11s | %-25s | %-8s | %-20s | %-13s | %-25s | %-12s |",
          id.getProductId(),
          id.getProductDate(),
          id.getYearsOfWarranty(),
          id.getWarrantyMethod(),
          id.getCustomerId(),
          id.getCustomerName(),
          id.getSdt());
      System.out.println(row);
      System.out.format(
          "+-------------+---------------------------+----------+----------------------+---------------+---------------------------+--------------+%n");
      if (choose == 1) {
        System.out.println("\t\t\t\t\t\t\t\t +--------NHẬP MỤC LỤC CẨN SỬA-------------+");
        System.out.println("\t\t\t\t\t\t\t\t |0. Thoát                                 |");
        System.out.println("\t\t\t\t\t\t\t\t +-----------------------------------------+");
        System.out.println("\t\t\t\t\t\t\t\t |1. ID sản phẩm                           |");
        System.out.println("\t\t\t\t\t\t\t\t |2. Ngày sản xuất                         |");
        System.out.println("\t\t\t\t\t\t\t\t |3. Năm bảo hành                          |");
        System.out.println("\t\t\t\t\t\t\t\t |4. Phương thức bảo hành                  |");
        System.out.println("\t\t\t\t\t\t\t\t |5. ID Khách hàng                         |");
        System.out.println("\t\t\t\t\t\t\t\t +-----------------------------------------+");
        System.out.print("\t\t\t\t\t\t\t\t - Mời Bạn Nhập Lựa Chọn: ");
        int index = sc.nextInt();
        while (true) {
          if (index < 0 || index > 5) {
            System.out.print("Nhập lại: ");
            index = sc.nextInt();
          } else {
            break;
          }
        }

        String[] data = new String[warranty.length];

        for (int i = 0; i < warranty.length; i++) {
          if (warranty[i].getProductId().equals(ID_Product)) {
            System.out.println("Nhập thông tin bảo hành:");
            switch (index) {
              case 0 -> {
                return;
              }

              case 1 -> {
								String test;
								System.out.println("Nhập ID Sản phẩm (sp_): ");
								sc.nextLine();
								while (true) {
									test = sc.nextLine();
									if (test.isBlank() || !Validation.isValidIDproduct(test)) {   //nếu như xâu test rỗng hoặc chứa toàn khoảng trắng, NHẬP LẠI ĐEEEEEEEE!!!!
											System.out.println("ID Sản phẩm không hợp lệ. Nhập lại: ");
									} else {
										break;
									}
								}

                id.setProductId(sc.nextLine());
                int check = 0;
                SanPham[] spList = QuanLySanPham.getInstance().getListSanPham();
                for (SanPham sp : spList) {
                  if (id.getProductId().equals(sp.getProductId())) {
                    check = 1;
                    break;
                  }
                }
                if (check == 0) {
                  System.out.println("\t\t\t\t\t\t\t\t +----MÃ SẢN PHẨM KHÔNG TỒN TẠI----+");
                  return;
                }
                warranty[i].setProductId(id.getProductId());
                break;
              }
                
              case 2 -> {
                String test;
                System.out.println("Nhập Ngày sản xuất (yyyy-MM-dd): ");
                sc.nextLine();
                while (true) {
                    test = sc.nextLine();
                    if (test.isBlank() || test.length() != 10) {
                        System.out.println("Ngày không hợp lệ. Nhập lại: ");
                    } else {
                        if (Validation.isValidDate(test)) {
                            id.setProductDate(LocalDate.parse(test));
                            break;
                        } else {
                            System.out.println("Ngày không hợp lệ. Nhập lại: ");
                        }
                    }
                }
                warranty[i].setProductDate(id.getProductDate());
                break;
              }
                
              case 3 -> {
								String test;
                System.out.println("Nhập số năm bảo hành (1 -> 5): ");
								while (true) {
										test = sc.nextLine();
										if (!Validation.isInteger(test) || test.isBlank()) {   //nếu như xâu test rỗng hoặc chứa toàn khoảng trắng, NHẬP LẠI ĐEEEEEEEE!!!!
												System.out.println("Phải nhập số nguyên!. Nhập lại: ");
										} else {
												if (Integer.parseInt(test) < 1 || Integer.parseInt(test) > 5) {
														System.out.println("Số năm bảo hành không hợp lệ");
												} else {
														id.setYearsOfWarranty(test);
														break;
												}
										}
								}
                warranty[i].setYearsOfWarranty(id.getYearsOfWarranty());
                break;
              }
                
              case 4 -> {
								String test;
                sc.nextLine();
								System.out.println("Nhập Phương thức bảo hành (tối đa 20 kí tự): ");
								System.out.println("Các Phương thức bảo hành gồm: chinh hang, mo rong, tu nha ban le, tu ben thu ba");
								String verify[] = {"chinh hang", "mo rong", "tu nha ban le", "tu ben thu ba"};
								while (true) {
										test = sc.nextLine();
										if (test.isBlank() || test.length() > 20) {   //nếu như xâu test rỗng hoặc chứa toàn khoảng trắng, NHẬP LẠI ĐEEEEEEEE!!!!
												System.out.println("Phương thức bảo hành không hợp lệ. Nhập lại: ");
										} else {
												boolean flag = false;
													for (String v : verify) {
														if (test.equals(v)) {
															flag = true;
															break;
														}
														flag = false;
													}
												if (flag) {
													id.setWarrantyMethod(test);
													break;
												}
												else {
													System.out.println("Phương thức bảo hành không hợp lệ. Nhập lại: ");
												}
										}
								}       
                warranty[i].setWarrantyMethod(id.getWarrantyMethod());
                break;
              }
                
              case 5 -> {
								String test;

                boolean foundCustomer = false;
                do {
                  System.out.println("Nhập ID Khách hàng: ");
									sc.nextLine();
									while (true) {
										test = sc.nextLine();
										if (test.isBlank() || !Validation.isValidIDcustomer(test)) {   //nếu như xâu test rỗng hoặc chứa toàn khoảng trắng, NHẬP LẠI ĐEEEEEEEE!!!!
												System.out.println("ID Khách hàng không hợp lệ. Nhập lại: ");
										} else {
											break;
										}
									}

                  id.setCustomerId(test);
                  KhachHang[] customers = QuanLyKhachHang.getInstance().getListCustomer();
                  for (KhachHang customer : customers) {
                    if (customer.getCustomerId().equals(id.getCustomerId())) {
                      foundCustomer = true;
                      warranty[i].setCustomerId(id.getCustomerId());
                      break;
                    }
                  }
                } while (foundCustomer == false);
                break;
              }
            }
          }

          data[i] = warranty[i].getProductId() + ";" +
              warranty[i].getProductDate() + ";" +
              warranty[i].getYearsOfWarranty() + ";" +
              warranty[i].getWarrantyMethod() + ";" +
              warranty[i].getCustomerId() + ";" +
              warranty[i].getCustomerName() + ";" +
              warranty[i].getSdt();
        }
        try {
          Stream.addAll("Database/BaoHanh.txt", data);
          System.out.println("\t\t\t\t\t\t\t\t+----SỬA THÔNG TIN BẢO HÀNH THÀNH CÔNG----+");
          waitConsole();
        } catch (IOException e) {
          e.printStackTrace();
        }
      } else {
        // Sửa toàn bộ dòng
        String[] data = new String[warranty.length];

        for (int i = 0; i < warranty.length; i++) {
          if (warranty[i].getProductId().equals(ID_Product)) {
            System.out.println("Nhập thông tin bảo hành:");
            	{
								String test;
                System.out.println("Nhập ID Sản phẩm (sp_): ");
								sc.nextLine();
								while (true) {
									test = sc.nextLine();
									if (test.isBlank() || (!Validation.isValidIDproduct(test))) {   //nếu như xâu test rỗng hoặc chứa toàn khoảng trắng, NHẬP LẠI ĐEEEEEEEE!!!!
											System.out.println("ID Sản phẩm không hợp lệ. Nhập lại: ");
									} else {
										break;
									}
								}
        
                id.setProductId(sc.nextLine());
                int check = 0;
                SanPham[] spList = QuanLySanPham.getInstance().getListSanPham();
                for (SanPham sp : spList) {
                  if (id.getProductId().equals(sp.getProductId())) {
                    check = 1;
                    break;
                  }
                }
                if (check == 0) {
                  System.out.println("\t\t\t\t\t\t\t\t +----MÃ SẢN PHẨM KHÔNG TỒN TẠI----+");
                  return;
                }
                warranty[i].setProductId(id.getProductId());
              }
                
              {
                String test;
                System.out.println("Nhập Ngày sản xuất (yyyy-MM-dd): ");
                sc.nextLine();
                while (true) {
                    test = sc.nextLine();
                    if (test.isBlank() || test.length() != 10) {
                        System.out.println("Ngày không hợp lệ. Nhập lại: ");
                    } else {
                        if (Validation.isValidDate(test)) {
                            id.setProductDate(LocalDate.parse(test));
                            break;
                        } else {
                            System.out.println("Ngày không hợp lệ. Nhập lại: ");
                        }
                    }
                }
                warranty[i].setProductDate(id.getProductDate());
              }
                
              {
                String test;
                System.out.println("Nhập số năm bảo hành (1 -> 5): ");
								while (true) {
										test = sc.nextLine();
										if (!Validation.isInteger(test) || test.isBlank()) {   //nếu như xâu test rỗng hoặc chứa toàn khoảng trắng, NHẬP LẠI ĐEEEEEEEE!!!!
												System.out.println("Phải nhập số nguyên!. Nhập lại: ");
										} else {
												if (Integer.parseInt(test) < 1 || Integer.parseInt(test) > 5) {
														System.out.println("Số năm bảo hành không hợp lệ");
												} else {
														id.setYearsOfWarranty(test);
														break;
												}
										}
								}
                warranty[i].setYearsOfWarranty(id.getYearsOfWarranty());
              }
                
              {
								String test;
                sc.nextLine();
								System.out.println("Nhập Phương thức bảo hành (tối đa 20 kí tự): ");
								System.out.println("Các Phương thức bảo hành gồm: chinh hang, mo rong, tu nha ban le, tu ben thu ba");
								String verify[] = {"chinh hang", "mo rong", "tu nha ban le", "tu ben thu ba"};
								while (true) {
										test = sc.nextLine();
										if (test.isBlank() || test.length() > 20) {   //nếu như xâu test rỗng hoặc chứa toàn khoảng trắng, NHẬP LẠI ĐEEEEEEEE!!!!
												System.out.println("Phương thức bảo hành không hợp lệ. Nhập lại: ");
										} else {
												boolean flag = false;
													for (String v : verify) {
														if (test.equals(v)) {
															flag = true;
															break;
														}
														flag = false;
													}
												if (flag) {
													id.setWarrantyMethod(test);
													break;
												}
												else {
													System.out.println("Phương thức bảo hành không hợp lệ. Nhập lại: ");
												}
										}
								}       
                warranty[i].setWarrantyMethod(id.getWarrantyMethod());
              }
                
              {
                System.out.println("Nhập ID Khách hàng: ");
                boolean foundCustomer = false;
                do {
                  String test;
									System.out.println("Nhập ID Khách hàng: ");
									sc.nextLine();
									while (true) {
										test = sc.nextLine();
										if (test.isBlank() || !Validation.isValidIDcustomer(test)) {   //nếu như xâu test rỗng hoặc chứa toàn khoảng trắng, NHẬP LẠI ĐEEEEEEEE!!!!
												System.out.println("ID Khách hàng không hợp lệ. Nhập lại: ");
										} else {
											break;
										}
									}

                  id.setCustomerId(test);
                  KhachHang[] customers = QuanLyKhachHang.getInstance().getListCustomer();
                  for (KhachHang customer : customers) {
                    if (customer.getCustomerId().equals(id.getCustomerId())) {
                      foundCustomer = true;
                      warranty[i].setCustomerId(id.getCustomerId());
                      break;
                    }
                  }
                } while (foundCustomer == false);
              }

            warranty[i].setProductDate(id.getProductDate());
            warranty[i].setYearsOfWarranty(id.getYearsOfWarranty());
            warranty[i].setWarrantyMethod(id.getWarrantyMethod());
            warranty[i].setCustomerId(id.getCustomerId());
            warranty[i].setCustomerName(id.getCustomerName());
            warranty[i].setSdt(id.getSdt());
          }
          data[i] = warranty[i].getProductId() + ";" +
              warranty[i].getProductDate() + ";" +
              warranty[i].getYearsOfWarranty() + ";" +
              warranty[i].getWarrantyMethod() + ";" +
              warranty[i].getCustomerId() + ";" +
              warranty[i].getCustomerName() + ";" +
              warranty[i].getSdt();
        }
        try {
          Stream.addAll("Database/BaoHanh.txt", data);
          System.out.println("\t\t\t\t\t\t\t\t+----SỬA THÔNG TIN BẢO HÀNH THÀNH CÔNG----+");
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
      sc.nextLine();
      System.out.println("\t\t\t\t\t\t\t\t +----XÓA THÔNG TIN BẢO HÀNH----+");
      System.out.print("Nhập ID sản phẩm cần xóa: ");
      String ID_Product;

			while (true) {
				ID_Product = sc.nextLine();
				if (ID_Product.isBlank()) {   //nếu như xâu test rỗng hoặc chứa toàn khoảng trắng, NHẬP LẠI ĐEEEEEEEE!!!!
						System.out.println("Mã sản phẩm không được để trống. Nhập lại: ");
				} else {
						break;
				}
			}

      BaoHanh product = null;
      for (BaoHanh w : warranty) {
        if (w.getProductId().equals(ID_Product)) {
          product = w;
          break;
        }
      }

      if (product == null) {
        System.out.println("\t\t\t\t\t\t\t\t +-----MÃ BẢO HÀNH KHÔNG TỒN TẠI-----+");
        return;
      }

      for (int i = 0; i < warranty.length; i++) {
        if (ID_Product.equals(warranty[i].getProductId())) {
          warranty = deleteWarranty(warranty, i);
          break;
        }
      }

      // Add lại nguyên danh sách đã xóa dòng dữ liệu
      updateList(1, warranty);

    } catch (InputMismatchException ei) {
      System.out.println("\t\t\t\t\t\t\t\t GIÁ TRỊ KHÔNG HỢP LỆ. VUI LÒNG NHẬP LẠI!");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  // Xóa phần tử khỏi mảng
  public BaoHanh[] deleteWarranty(BaoHanh[] warranty, int index) {
    BaoHanh[] newCs = new BaoHanh[warranty.length - 1];
    for (int i = 0, j = 0; i < warranty.length; i++) {
      if (i != index) {
        newCs[j++] = warranty[i];
      }
    }
    return newCs;
  }

  // Thêm phần tử vào mảng
  public BaoHanh[] addWarranty(BaoHanh[] warranty, BaoHanh sanpham) {
    warranty = Arrays.copyOf(warranty, warranty.length + 1);
    warranty[warranty.length - 1] = sanpham;
    return warranty;
  }

  @Override
  public void searchByCategory() {
    try {
      sc.nextLine();
      String find = null;
      System.out.println("\t\t\t\t\t\t\t\t +--------NHẬP MỤC LỤC CẨN TÌM-------------+");
      System.out.println("\t\t\t\t\t\t\t\t |0. Thoát                                 |");
      System.out.println("\t\t\t\t\t\t\t\t +-----------------------------------------+");
      System.out.println("\t\t\t\t\t\t\t\t |1. ID sản phẩm                           |");
      System.out.println("\t\t\t\t\t\t\t\t |2. Ngày sản xuất                         |");
      System.out.println("\t\t\t\t\t\t\t\t |3. Số năm bảo hành                       |");
      System.out.println("\t\t\t\t\t\t\t\t |4. Phương thức bảo hành                  |");
      System.out.println("\t\t\t\t\t\t\t\t |5. ID Khách hàng                         |");
      System.out.println("\t\t\t\t\t\t\t\t +-----------------------------------------+");
      System.out.print("\t\t\t\t\t\t\t\t - Mời Bạn Nhập Lựa Chọn: ");

      int index = sc.nextInt();

      while (true) {
        if (index < 0 || index > 5) {
          System.out.print("Nhập lại: ");
          index = sc.nextInt();
        } else {
          break;
        }
      }

      System.out.print("Nhập nội dung cần tìm: ");

      switch (index) {
          case 1 -> {
            sc.nextLine();
            while (true) {
              find = sc.nextLine();
              if (find.isBlank() || !Validation.isValidIDproduct(find)) {   //nếu như xâu find rỗng hoặc chứa toàn khoảng trắng, NHẬP LẠI ĐEEEEEEEE!!!!
                  System.out.println("ID Sản phẩm không hợp lệ. Nhập lại: ");
              } else {
                break;
              }
            }
            break;
          }
            
          case 2 -> {
            sc.nextLine();
            while (true) {
                find = sc.nextLine();
                if (find.isBlank() || find.length() != 10) {
                    System.out.println("Ngày không hợp lệ. Nhập lại: ");
                } else {
                    if (Validation.isValidDate(find)) {
                        break;
                    } else {
                        System.out.println("Ngày không hợp lệ. Nhập lại: ");
                    }
                }
            }
            break;
          }
            
          case 3 -> {
            sc.nextLine();
            while (true) {
                find = sc.nextLine();
                if (!Validation.isInteger(find) || find.isBlank()) {   //nếu như xâu find rỗng hoặc chứa toàn khoảng trắng, NHẬP LẠI ĐEEEEEEEE!!!!
                    System.out.println("Phải nhập số nguyên!. Nhập lại: ");
                } else {
                    if (Integer.parseInt(find) < 1 || Integer.parseInt(find) > 5) {
                        System.out.println("Số năm bảo hành không hợp lệ");
                    } else {
                        break;
                    }
                }
            }
            break;
          }
            
          case 4 -> {
            sc.nextLine();
            System.out.println("Các Phương thức bảo hành gồm: chinh hang, mo rong, tu nha ban le, tu ben thu ba");
            String verify[] = {"chinh hang", "mo rong", "tu nha ban le", "tu ben thu ba"};
            while (true) {
                find = sc.nextLine();
                if (find.isBlank() || find.length() > 20) {   //nếu như xâu find rỗng hoặc chứa toàn khoảng trắng, NHẬP LẠI ĐEEEEEEEE!!!!
                    System.out.println("Phương thức bảo hành không hợp lệ. Nhập lại: ");
                } else {
                    boolean flag = false;
                      for (String v : verify) {
                        if (find.equals(v)) {
                          flag = true;
                          break;
                        }
                        flag = false;
                      }
                    if (flag) {
                      break;
                    }
                    else {
                      System.out.println("Phương thức bảo hành không hợp lệ. Nhập lại: ");
                    }
                }
            }       
            break;
          }
            
          case 5 -> {
            sc.nextLine();
            while (true) {
              find = sc.nextLine();
              if (find.isBlank() || !Validation.isValidIDcustomer(find)) {   //nếu như xâu find rỗng hoặc chứa toàn khoảng trắng, NHẬP LẠI ĐEEEEEEEE!!!!
                  System.out.println("ID Khách hàng không hợp lệ. Nhập lại: ");
              } else {
                break;
              }
            }
            break;
          }
      }

      System.out.println("\t\t\t\t\t\t\t\t +----DANH SÁCH BẢO HÀNH----+");
      OutputHeader();

      for (int i = 0; i < warranty.length; i++) {
        switch (index) {
          case 0:
            return;
          case 1:
            if (warranty[i].getProductId().contains(find))
              OutputData(i);
            break;
          case 2:
            if (warranty[i].getProductDate().equals(LocalDate.parse(find)))
              OutputData(i);
            break;
          case 3:
            if (warranty[i].getYearsOfWarranty().equals(find))
              OutputData(i);
            break;
          case 4:
            if (warranty[i].getWarrantyMethod().contains(find))
              OutputData(i);
            break;
          case 5:
            if (warranty[i].getCustomerId().equals(find))
              OutputData(i);
            break;
        }
      }
      System.out.format(
          "+-------------+---------------------------+----------+----------------------+---------------+---------------------------+--------------+%n");
    } catch (InputMismatchException e) {
      System.out.println("\t\t\t\t\t\t\t\t GIÁ TRỊ KHÔNG HỢP LỆ. VUI LÒNG NHẬP LẠI!");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public void OutputData(int i) {
    String row = String.format("| %-11s | %-25s | %-8s | %-20s | %-13s | %-25s | %-12s |",
        warranty[i].getProductId(),
        warranty[i].getProductDate(),
        warranty[i].getYearsOfWarranty(),
        warranty[i].getWarrantyMethod(),
        warranty[i].getCustomerId(),
        warranty[i].getCustomerName(),
        warranty[i].getSdt());
    System.out.println(row);
  }

  public void OutputHeader() {
    String header = String.format("| %-11s | %-25s | %-8s | %-20s | %-13s | %-25s | %-12s |",
          "ID sản phẩm",
          "Ngày bảo hành",
          "Số lượng",
          "Phương thức bảo hành",
          "ID khách hàng",
          "Tên khách hàng",
          "SĐT");
      System.out.format(
          "+-------------+---------------------------+----------+----------------------+---------------+---------------------------+--------------+%n");
      System.out.println(header);
      System.out.format(
          "+-------------+---------------------------+----------+----------------------+---------------+---------------------------+--------------+%n");
  }

  public String[] stringToInputInFile(BaoHanh[] warranty) {
    String[] data = new String[warranty.length];

    for (int i = 0; i < warranty.length; i++) {
      data[i] = warranty[i].getProductId() + ";" +
          warranty[i].getProductDate() + ";" +
          warranty[i].getYearsOfWarranty() + ";" +
          warranty[i].getWarrantyMethod() + ";" +
          warranty[i].getCustomerId() + ";" +
          warranty[i].getCustomerName() + ";" +
          warranty[i].getSdt();
    }

    return data;
  }

  public void updateList(int select, BaoHanh[] warranty) {
    switch (select) {
      case 0:
        try {
          String[] data = stringToInputInFile(warranty);
          Stream.addAll("Database/BaoHanh.txt", data);
          System.out.println("\t\t\t\t\t\t\t\t +----NHẬP THÔNG TIN BẢO HÀNH THÀNH CÔNG----+");
          waitConsole();
        } catch (IOException e) {
          e.printStackTrace();
        }
        break;

      case 1:
        try {
          String[] data = stringToInputInFile(warranty);
          Stream.addAll("Database/BaoHanh.txt", data);
          System.out.println("\t\t\t\t\t\t\t\t +----SỬA THÔNG TIN BẢO HÀNH THÀNH CÔNG----+");
          waitConsole();
        } catch (IOException e) {
          e.printStackTrace();
        }
        break;
    }
  }

  public void waitConsole() {
    System.out.println("\t\t\t\t\t\t\t\t - Ấn Enter để tiếp tục");
    sc.nextLine();
  }
}