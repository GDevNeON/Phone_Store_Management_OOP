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
}