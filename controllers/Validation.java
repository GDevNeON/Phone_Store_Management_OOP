package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

  // Các hằng số để quy định độ dài tối đa của các trường thông tin
  private static final int MAX_NAME_LENGTH = 50;
  private static final int MAX_ADDRESS_LENGTH = 100;
  private static final int MAX_EMAIL_LENGTH = 100;
  private static final int MAX_PHONE_NUMBER_LENGTH = 10;
  private static final int MAX_ID_LENGTH = 10;

  // Hàm validation cho tên
  public static boolean isValidName(String name) {
    return name.matches("^[a-zA-Z]+( [a-zA-Z]+)*$") && name.length() <= MAX_NAME_LENGTH;
  }

  // Hàm validation cho tuổi
  public static boolean isValidAge(int age) {
    return age >= 0;
  }

  // Hàm validation cho giới tính
  public static boolean isValidGender(String gender) {
    return gender.equalsIgnoreCase("Nam") || gender.equalsIgnoreCase("Nu");
  }

  // Hàm validation cho địa chỉ
  public static boolean isValidAddress(String address) {
    return !address.isEmpty() && address.length() <= MAX_ADDRESS_LENGTH;
  }

  // Hàm validation cho email
  public static boolean isValidEmail(String email) {
    return email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")
        && email.length() <= MAX_EMAIL_LENGTH;
  }

  // Hàm validation cho số điện thoại
  public static boolean isValidPhoneNumber(String SDT) {
    return SDT.matches("^0\\d{9}$") && SDT.length() <= MAX_PHONE_NUMBER_LENGTH;
  }

  // Hàm validation cho số fax
  public static boolean isValidFax(String Fax) {
    String regex = "^84\\d{8}$";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(Fax);
    return matcher.matches();
  }

  // Hàm validation kiểm tra số nguyên cho phần chọn menu
  public static boolean isInteger(String input) {
    try {
      Integer.parseInt(input);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

<<<<<<< HEAD
  // Hàm kiểm tra ngày (NHỚ THÊM ĐIỀU KIỆN ĐỘ DÀI CHUỖI NGÀY NHẬP = 10)
  public static boolean isValidDate(String inputDate) {
    String dateFormat = "yyyy-MM-dd";
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
    sdf.setLenient(false);
=======
  //Hàm kiểm tra ngày (NHỚ THÊM ĐIỀU KIỆN ĐỘ DÀI CHUỖI NGÀY NHẬP = 10)
  public boolean isValidDate(String inputDate) {
        String dateFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setLenient(false);
>>>>>>> fa8ab1b0c2896094c00c8cf2a20288d6481eb81b

    try {
      Date date = sdf.parse(inputDate);
      // Kiểm tra giá trị của ngày và tháng
      Calendar cal = Calendar.getInstance();
      cal.setTime(date);

      int year = cal.get(Calendar.YEAR);
      int month = cal.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0
      int day = cal.get(Calendar.DAY_OF_MONTH);

      // Kiểm tra giá trị của năm, tháng và ngày
      if (year >= 1000 && year <= 9999 && month >= 1 && month <= 12 && day >= 1
          && day <= cal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
        return true;
      }
    } catch (ParseException e) {
      // Nếu có ngoại lệ ParseException, chuỗi không hợp lệ
      return false;
    }

    return false;
  }

  // Hàm validation cho account id
  public boolean isValidAccountId(String id) {
    return id.startsWith("QL") || id.startsWith("ADMIN") || id.startsWith("NV");
  }

  // Hàm validation cho username
  public boolean isValidUsername(String name) {
    return name.startsWith("manager") || name.startsWith("Admin") || name.startsWith("employee");
  }

  // Hàm validation cho position
  public boolean isValidPosition(String position) {
    return position.equalsIgnoreCase("manager") || position.equalsIgnoreCase("admin")
        || position.equalsIgnoreCase("employee");
  }

  // Hàm validation cho id_worker
  public boolean isValidIDWorker(String id) {
    return id.startsWith("nv") && id.length() <= MAX_ID_LENGTH;
  }

  // Hàm validation cho id_phieunhap
  public boolean isValidIDPN(String id) {
    return id.startsWith("pn") && id.length() <= MAX_ID_LENGTH;
  }

  // Hàm validation cho id_chitietphieu
  public boolean isValidIDCTP(String id) {
    return id.startsWith("ctpn") && id.length() <= MAX_ID_LENGTH;
  }
<<<<<<< HEAD

  // Hàm validation cho id_sanpham
  public static boolean isValidIDproduct(String id) {
    return id.startsWith("sp") && id.length() <= MAX_ID_LENGTH;
  }

  // Hàm validation cho id_khachhang
  public static boolean isValidIDcustomer(String id) {
    return id.startsWith("kh") && id.length() <= MAX_ID_LENGTH;
  }

  // Hàm validation cho id_sanpham
  public static boolean isValidIDmanager(String id) {
    return id.startsWith("ql") && id.length() <= MAX_ID_LENGTH;
  }

  // Hàm validation cho id_thanhtoan
  public static boolean isValidIDpayment(String id) {
    return id.startsWith("tt") && id.length() <= MAX_ID_LENGTH;
  }

  // Hàm validation cho id_hoadon
  public static boolean isValidIDreceipt(String id) {
    return id.startsWith("hd") && id.length() <= MAX_ID_LENGTH;
=======
  //Hàm validation cho id_khachhang
  public boolean isValidIDcustomer(String id){
    return id.startsWith("kh") && id.length() <= MAX_ID_LENGTH;
  }
  //Hàm validation cho id_sanpham
  public boolean isValidIDmanager(String id){
    return id.startsWith("ql") && id.length() <= MAX_ID_LENGTH; 
  }

  //Hàm validation cho id_thanhtoan
  public boolean isValidIDpayment(String id){
    return id.startsWith("tt") && id.length() <= MAX_ID_LENGTH; 
  }

  //Hàm validation cho id_hoadon
  public boolean isValidIDreceipt(String id){
    return id.startsWith("hd") && id.length() <= MAX_ID_LENGTH; 
>>>>>>> fa8ab1b0c2896094c00c8cf2a20288d6481eb81b
  }
}
