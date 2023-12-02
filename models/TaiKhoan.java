package models;

public class TaiKhoan {
  private String accountId;
  private String username;
  private String password;
  private String position;

  public TaiKhoan() {
  }

  public TaiKhoan(String accountId, String username, String password, String position) {
    this.accountId = accountId;
    this.username = username;
    this.password = password;
    this.position = position;
  }

  public String getAccountId() {
    return this.accountId;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPosition() {
    return this.position;
  }

  public void setPosition(String position) {
    this.position = position;
  }

  public TaiKhoan accountId(String accountId) {
    setAccountId(accountId);
    return this;
  }

  public TaiKhoan username(String username) {
    setUsername(username);
    return this;
  }

  public TaiKhoan password(String password) {
    setPassword(password);
    return this;
  }

  public TaiKhoan position(String position) {
    setPosition(position);
    return this;
  }
}
