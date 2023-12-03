package controllers;

public class Validation {

  // Các hằng số để quy định độ dài tối đa của các trường thông tin
  private static final int MAX_NAME_LENGTH = 50;
  private static final int MAX_ADDRESS_LENGTH = 100;
  private static final int MAX_EMAIL_LENGTH = 100;
  private static final int MAX_PHONE_NUMBER_LENGTH = 10;

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

  // Hàm validation kiểm tra số nguyên cho phần chọn menu
  public static boolean isInteger(String input) {
    try {
      Integer.parseInt(input);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  //Hàm validation cho account id
  public boolean isValidAccountId(String id){
    return id.startsWith("QL") || id.startsWith("ADMIN") || id.startsWith("NV");
  }
  //Hàm validation cho username
  public boolean isValidUsername(String name){
      return name.startsWith("manager") || name.startsWith("Admin") || name.startsWith("employee");
  }
  //Hàm validation cho position
  public boolean isValidPosition(String position){
      return position.equalsIgnoreCase("manager") || position.equalsIgnoreCase("admin") || position.equalsIgnoreCase("employee");
  }
  //Hàm validation cho id_worker
  public boolean isValidIDWorker(String id){
      return id.startsWith("nv");
  } 
  //Hàm validation cho id_phieunhap
  public boolean isValidIDPN(String id){
      return id.startsWith("pn");
  }
  //Hàm validation cho id_chitietphieu
  public boolean isValidIDCTP(String id){
      return id.startsWith("ctpn");
  }
  //Hàm validation cho id_sanpham
  public boolean isValidIDproduct(String id){
      return id.startsWith("sp");
  }
}