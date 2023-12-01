package Entity;

public class Discount {
    public String Discount_ID;
    public String KindOfCustomer;
    public String Product_name;
    public String Discount_rate;
    public String Status;
    public String StartDate;
    public String EndDate;


    public Discount(String discount_ID, String kindOfCustomer, String product_name, String discount_rate, String status,
            String startDate, String endDate) {
        this.Discount_ID = discount_ID;
        this.KindOfCustomer = kindOfCustomer;
        this.Product_name = product_name;
        this.Discount_rate = discount_rate;
        this.Status = status;
        this.StartDate = startDate;
        this.EndDate = endDate;
    }

    public Discount() {

    }

    public String getDiscount_ID() {
        return Discount_ID;
    }

    public void setDiscount_ID(String discount_ID) {
        this.Discount_ID = discount_ID;
    }

    public String getKindOfCustomer() {
        return KindOfCustomer;
    }

    public void setKindOfCustomer(String kindOfCustomer) {
        this.KindOfCustomer = kindOfCustomer;
    }

    public String getProduct_name() {
        return Product_name;
    }

    public void setProduct_name(String product_name) {
        this.Product_name = product_name;
    }

    public String getDiscount_rate() {
        return Discount_rate;
    }

    public void setDiscount_rate(String discount_rate) {
        this.Discount_rate = discount_rate;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        this.Status = status;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        this.StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        this.EndDate = endDate;
    }
}