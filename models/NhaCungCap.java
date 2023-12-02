package models;

public class NhaCungCap {
  private String MaNCC;
  private String TenNCC;
  private String DiaChi;
  private String SDT;
  private String fax;

  public NhaCungCap() {
  }

  public NhaCungCap(String maNCC, String tenNCC, String diaChi, String sDT, String fax) {
    this.MaNCC = maNCC;
    this.TenNCC = tenNCC;
    this.DiaChi = diaChi;
    this.SDT = sDT;
    this.fax = fax;
  }

  public String getMaNCC() {
    return MaNCC;
  }

  public void setMaNCC(String maNCC) {
    this.MaNCC = maNCC;
  }

  public String getTenNCC() {
    return TenNCC;
  }

  public void setTenNCC(String tenNCC) {
    this.TenNCC = tenNCC;
  }

  public String getDiaChi() {
    return DiaChi;
  }

  public void setDiaChi(String diaChi) {
    this.DiaChi = diaChi;
  }

  public String getSDT() {
    return SDT;
  }

  public void setSDT(String sDT) {
    this.SDT = sDT;
  }

  public String getFax() {
    return fax;
  }

  public void setFax(String fax) {
    this.fax = fax;
  }
}