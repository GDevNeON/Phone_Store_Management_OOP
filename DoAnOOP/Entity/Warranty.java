package Entity;

import java.time.LocalDate;

public class Warranty {
    private String Product_ID;
    private LocalDate Product_Date;
    private String Years_Of_Warranty;
    private String Warranty_Method;
    private String Customer_ID;
    private String Customer_Name;
    private String SDT;

    public Warranty() {
        Product_ID = null;
        Product_Date = null;
        Years_Of_Warranty = null;
        Warranty_Method = null;
        Customer_ID = null;
        Customer_Name = null;
        SDT = null;
    }

    public Warranty(String product_ID, LocalDate product_Date, String years_Of_Warranty, String warranty_Method, String Customer_ID, String Customer_Name, String SDT) {
        this.Product_ID = product_ID;
        this.Product_Date = product_Date;
        this.Years_Of_Warranty = years_Of_Warranty;
        this.Warranty_Method = warranty_Method;
        this.Customer_ID = Customer_ID;
        this.Customer_Name = Customer_Name;
        this.SDT = SDT;
    }

    public String getProduct_ID() {
        return Product_ID;
    }

    public void setProduct_ID(String product_ID) {
        this.Product_ID = product_ID;
    }

    public LocalDate getProduct_Date() {
        return Product_Date;
    }

    public void setProduct_Date(LocalDate product_Date) {
        this.Product_Date = product_Date;
    }

    public String getYears_Of_Warranty() {
        return Years_Of_Warranty;
    }

    public void setYears_Of_Warranty(String years_Of_Warranty) {
        this.Years_Of_Warranty = years_Of_Warranty;
    }

    public String getWarranty_Method() {
        return Warranty_Method;
    }

    public void setWarranty_Method(String warranty_Method) {
        this.Warranty_Method = warranty_Method;
    }

    public String getCustomer_ID() {
        return Customer_ID;
    }

    public void setCustomer_ID(String customer_ID) {
        Customer_ID = customer_ID;
    }

    public String getCustomer_Name() {
        return Customer_Name;
    }

    public void setCustomer_Name(String customer_Name) {
        Customer_Name = customer_Name;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String sDT) {
        SDT = sDT;
    }
}
