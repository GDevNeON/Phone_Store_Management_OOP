package Entity;

public class Customer extends Person {
    private String Customer_ID;
    private String KindOfCustomer;

    public Customer(String Customer_ID, String name, Integer age, String gender, String address, String email, String SDT, String KindOfCustomer) {
        super(name, age, gender, address, email, SDT);
        this.Customer_ID = Customer_ID;
        this.KindOfCustomer = KindOfCustomer;
    }

    public Customer() {
        super();
    }

    public String getCustomer_ID() {
        return Customer_ID;
    }

    public void setCustomer_ID(String customer_ID) {
        this.Customer_ID = customer_ID;
    }

    public String getKindOfCustomer() {
        return KindOfCustomer;
    }

    public void setKindOfCustomer(String kindOfCustomer) {
        this.KindOfCustomer = kindOfCustomer;
    }
}


