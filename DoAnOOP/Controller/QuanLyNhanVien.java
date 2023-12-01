package Controller;

import Entity.Employee;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class QuanLyNhanVien extends Employee implements ControllerInterface {
    public static Scanner input = new Scanner(System.in);
    public Employee[] nv;

    public QuanLyNhanVien() throws FileNotFoundException {
        super();
        getListEmployee();
    }

    public Employee[] getListEmployee() throws FileNotFoundException {
        String[] result = Stream.read("Database/NhanVien.txt"); // link database here
        nv = new Employee[result.length];
        for (int i = 0; i < result.length; i++) {
            String[] row = result[i].split(";");
            nv[i] = new Employee(row[0], row[1], Integer.parseInt(row[2]), row[3], row[4], row[5], row[6], row[7], row[8]);
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
        String header = String.format("| %-5s | %-25s | %-4s | %-9s | %-30s | %-25s | %-15s | %-20s | %-9s | ", "ID", "Họ tên", "Tuổi", "Giới Tính", "Địa chỉ", "Email", "Số điện thoại", "Chức vụ", "Ca trực");
        System.out.format("+-------+---------------------------+------+-----------+--------------------------------+---------------------------+-----------------+----------------------+----------+%n");
        System.out.println(header);
        System.out.format("+-------+---------------------------+------+-----------+--------------------------------+---------------------------+-----------------+----------------------+----------+%n");

        try {
            getListEmployee();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        for (Employee emPloyee : nv) {
            if (nv[0].getID_Worker().indexOf("nv") >= 0) {
                String read = String.format("| %-5s | %-25s | %-4s | %-9s | %-30s | %-25s | %-15s | %-20s | %-9s |", emPloyee.getID_Worker(), emPloyee.getName(), emPloyee.getAge(),
                        emPloyee.getGender(), emPloyee.getAddress(), emPloyee.getEmail(), emPloyee.getSDT(),
                        emPloyee.getRole(), emPloyee.getShift());
                System.out.println(read);
            }
        }
        System.out.format("+-------+---------------------------+------+-----------+--------------------------------+---------------------------+-----------------+----------------------+----------+%n");
        waitConsole();
    }

    @Override
    public void Create() {
        boolean checkIDexist;
        do {
            checkIDexist = false;
            System.out.println("\t\t\t\t\t\t\t\t +----NHẬP THÔNG TIN NHÂN VIÊN----+");
            System.out.print("Nhập ID nhân viên:");
            setID_Worker(input.nextLine());

            for (Employee employee : nv) {
                if (employee != null) {
                    if (getID_Worker().equals(employee.getID_Worker())) {
                        System.out.println("\t\t\t\t\t\t\t\t -MÃ NHÂN VIÊN BỊ TRÙNG!");
                        checkIDexist = true;
                        break;
                    }
                }
            }
        }while (checkIDexist);
            super.AddThongTin();
            input.nextLine();
            System.out.print("Nhập chức vụ của nhân viên:");
            setRole(input.nextLine());

            System.out.print("Nhập ca làm việc của nhân viên: ");
            setShift(input.nextLine());

            try {
                String input = getID_Worker() + ";" + getName() + ";" + getAge() + ";" + getGender() + ";" + getAddress() + ";" + getEmail() + ";" + getSDT() + ";" + getRole() + ";" + getShift();
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
            Employee employee = null;

            for (Employee emPloyee : nv) {
                if (emPloyee.getID_Worker().equals(ID_Employee)) {
                    employee = emPloyee;
                    break;
                }
            }

            if (employee == null) {
                System.out.println("\t\t\t\t\t\t\t\t -MÃ NHÂN VIÊN KHÔNG TỒN TẠI!");
                return;
            }

            System.out.println("\t\t\t\t\t\t\t\t +----THÔNG TIN NHÂN VIÊN TRƯỚC KHI ĐƯỢC CHỈNH SỬA----+");
            String header = String.format("| %-5s | %-25s | %-4s | %-9s | %-30s | %-25s | %-15s | %-20s | %-9s |", "ID", "Họ tên", "Tuổi", "Giới Tính", "Địa chỉ", "Email", "Số điện thoại", "Chức vụ", "Ca trực");
            System.out.format("+-------+---------------------------+------+-----------+--------------------------------+---------------------------+-----------------+----------------------+----------+%n");
            System.out.println(header);
            System.out.format("+-------+---------------------------+------+-----------+--------------------------------+---------------------------+-----------------+----------------------+----------+%n");
            String row = String.format("| %-5s | %-25s | %-4s | %-9s | %-30s | %-25s | %-15s | %-20s | %-9s |", employee.getID_Worker(), employee.getName(), employee.getAge(), employee.getGender(),
                    employee.getAddress(), employee.getEmail(), employee.getSDT(), employee.getRole(), employee.getShift());
            System.out.println(row);

            String[] data = new String[nv.length];

            for (int i = 0; i < nv.length; i++) {
                if (ID_Employee.equals(nv[i].getID_Worker())) {
                    System.out.println("Nhập thông tin nhân viên:");
                    super.AddThongTin();

                    input.nextLine();
                    System.out.print("Nhập chức vụ của nhân viên: ");
                    setRole(input.nextLine());

                    System.out.print("Nhập ca làm việc của nhân viên: ");
                    setShift(input.nextLine());

                    nv[i].setName(getName());
                    nv[i].setGender(getGender());
                    nv[i].setAddress(getAddress());
                    nv[i].setEmail(getEmail());
                    nv[i].setAge(getAge());
                    nv[i].setSDT(getSDT());
                    nv[i].setShift(getShift());
                }
                data[i] = nv[i].getID_Worker() + ";" + nv[i].getName() + ";" + nv[i].getAge() + ";" + nv[i].getGender() + ";" + nv[i].getAddress() + ";" + nv[i].getEmail() + ";" + nv[i].getSDT() + ";" + nv[i].getRole() + ";" + nv[i].getShift();
            }
            try {
                Stream.addAll("Database/NhanVien.txt", data);  // link database here
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
            Employee n_vien = null;

            for (Employee emPloyee : nv) {
                if (emPloyee.getID_Worker().equals(ID_Nhanvien)) {
                    n_vien = emPloyee;
                    break;
                }
            }

            if (n_vien == null) {
                System.out.println("\t\t\t\t\t\t\t\t -MÃ NHÂN VIÊN KHÔNG TỒN TẠI!");
                return;
            }

            for (int i = 0; i < nv.length; i++) {
                if (ID_Nhanvien.equals(nv[i].getID_Worker())) {
                    nv = deleteEmployee(nv, i);
                    break;
                }
            }

            String[] data = new String[nv.length];
            for (int i = 0; i < nv.length; i++) {
                data[i] = nv[i].getID_Worker() + ";" + nv[i].getName() + ";" + nv[i].getAge() + ";" + nv[i].getGender() + ";" + nv[i].getAddress() + ";" + nv[i].getEmail() + ";" + nv[i].getSDT() + ";" + nv[i].getRole() + ";" + nv[i].getShift();
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

    public Employee[] deleteEmployee(Employee[] nv, int index) {
        Employee[] newCs = new Employee[nv.length - 1];
        for (int i = 0, j = 0; i < nv.length; i++) {
            if (i != index) {
                newCs[j++] = nv[i];
            }
        }
        return newCs;
    }

    public Employee[] addEmployee(Employee[] nv, Employee emPloyee) {
        nv = Arrays.copyOf(nv, nv.length + 1);
        nv[nv.length - 1] = emPloyee;
        return nv;
    }

    @Override
    public void Search_byCategory() {
        Employee[] result = new Employee[0];
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
        }
        else {
            switch (choose) {
                case 1 -> {
                    input.nextLine();
                    System.out.print("Nhập mã nhân viên: ");
                    String ID_Worker = input.nextLine();
                    for (Employee emPloyee : nv) {
                        if (ID_Worker.contains(emPloyee.getID_Worker())) {
                            result = addEmployee(result, emPloyee);
                        }
                    }
                }
                case 2 -> {
                    input.nextLine();
                    System.out.print("Nhập họ tên nhân viên: ");
                    String nameWorker = input.nextLine();
                    for (Employee emPloyee : nv) {
                        if (emPloyee.getName().toLowerCase().contains(nameWorker.toLowerCase())) {
                            result = addEmployee(result, emPloyee);
                        }
                    }
                }
                case 3 -> {
                    input.nextLine();
                    System.out.print("Nhập địa chỉ của nhân viên: ");
                    String address = input.nextLine();
                    for (Employee emPloyee : nv) {
                        if (emPloyee.getAddress().toLowerCase().contains(address.toLowerCase())) {
                            result = addEmployee(result, emPloyee);
                        }
                    }
                }
                case 4 -> {
                    input.nextLine();
                    System.out.print("Nhập số điện thoại của nhân viên: ");
                    String phoneNumber = input.nextLine();
                    for (Employee emPloyee : nv) {
                        if (emPloyee.getSDT().toLowerCase().contains(phoneNumber.toLowerCase())) {
                            result = addEmployee(result, emPloyee);
                        }
                    }
                }
                case 5 -> {
                    input.nextLine();
                    System.out.print("Nhập chức vụ của nhân viên: ");
                    String Role = input.nextLine();
                    for (Employee emPloyee : nv) {
                        if (emPloyee.getRole().toLowerCase().contains(Role.toLowerCase())) {
                            result = addEmployee(result, emPloyee);
                        }
                    }
                }
                case 6 -> {
                    input.nextLine();
                    System.out.print("Nhập ca trực của nhân viên: ");
                    String Shift = input.nextLine();
                    for (Employee emPloyee : nv) {
                        if (emPloyee.getShift().toLowerCase().contains(Shift.toLowerCase())) {
                            result = addEmployee(result, emPloyee);
                        }
                    }
                }
            }
            System.out.println("\t\t\t\t\t\t\t\t +----THÔNG TIN NHÂN VIÊN TÌM ĐƯỢC----+");
            String header = String.format("| %-5s | %-25s | %-4s | %-9s | %-30s | %-25s | %-15s | %-20s | %-9s |", "ID", "Họ tên", "Tuổi", "Giới Tính", "Địa chỉ", "Email", "Số điện thoại", "Chức vụ", "Ca trực");
            System.out.format("+-------+---------------------------+------+-----------+--------------------------------+---------------------------+-----------------+----------------------+----------+%n");
            System.out.println(header);
            System.out.format("+-------+---------------------------+------+-----------+--------------------------------+---------------------------+-----------------+----------------------+----------+%n");

            for (Employee DSNV : result) {
                String row = String.format("| %-5s | %-25s | %-4s | %-9s | %-30s | %-25s | %-15s | %-20s | %-9s |", DSNV.getName(), DSNV.getAge(), DSNV.getGender(),
                        DSNV.getAddress(), DSNV.getEmail(), DSNV.getID_Worker(), DSNV.getRole(), DSNV.getShift(), DSNV.getSDT());
                System.out.println(row);
                waitConsole();
            }
        }
    }

    public void Output() {
        System.out.println("\t\t\t\t\t\t\t\t +----DANH SÁCH NHÂN VIÊN----+");
        String header = String.format("| %-5s | %-25s | %-4s | %-9s | %-30s | %-25s | %-15s | %-20s | %-9s |", "ID", "Họ tên", "Tuổi", "Giới Tính", "Địa chỉ", "Email", "Số điện thoại", "Chức vụ", "Ca trực");
        System.out.format("+-------+---------------------------+------+-----------+--------------------------------+---------------------------+-----------------+----------------------+----------+%n");
        System.out.println(header);
        for (Employee DSNV : nv) {
            String row = String.format("| %-5s | %-25s | %-4s | %-9s | %-30s | %-25s | %-15s | %-20s | %-9s |", DSNV.getName(), DSNV.getAge(), DSNV.getGender(),
                    DSNV.getAddress(), DSNV.getEmail(), DSNV.getID_Worker(), DSNV.getRole(), DSNV.getShift(), DSNV.getSDT());
            System.out.println(row);
        }
    }
}
