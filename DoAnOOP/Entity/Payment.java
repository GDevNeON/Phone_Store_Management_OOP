package Entity;

import java.time.LocalDate;
import java.util.Scanner;


public class Payment {

    public int n;
    public String Payment_ID;
    public String Customer_ID;
    public String ID_Receipt;
    public String Amount;
    public LocalDate Payment_Date;
    public String Payment_Method;
    public String Status;
    Scanner sc = new Scanner(System.in);
    Payment[] payment;

    /* CONSTRUCTOR ************************************************************************************* */

    public Payment() {
        Payment_ID = null;
        Customer_ID = null;
        ID_Receipt = null;
        Amount = null;
        Payment_Date = null;
        Payment_Method = null;
        Status = null;
    }

    public Payment(String payment_ID, String customer_ID, String ID_Receipt, String amount, LocalDate payment_Date,
                   String payment_Method, String status) {
        this.Payment_ID = payment_ID;
        this.Customer_ID = customer_ID;
        this.ID_Receipt = ID_Receipt;
        this.Amount = amount;
        this.Payment_Date = payment_Date;
        this.Payment_Method = payment_Method;
        this.Status = status;
    }

    /* GETTER ****************************************************************************************** */

    public String getPayment_ID() {
        return Payment_ID;
    }

    public void setPayment_ID(String payment_ID) {
        this.Payment_ID = payment_ID;
    }

    public String getCustomer_ID() {
        return Customer_ID;
    }

    public void setCustomer_ID(String customer_ID) {
        this.Customer_ID = customer_ID;
    }

    public String getID_Receipt() {
        return ID_Receipt;
    }

    public void setID_Receipt(String ID_Receipt) {
        this.ID_Receipt = ID_Receipt;
    }

    public String getAmount() {
        return Amount;
    }

    /* SETTER ****************************************************************************************** */

    public void setAmount(String amount) {
        this.Amount = amount;
    }

    public LocalDate getPayment_Date() {
        return Payment_Date;
    }

    public void setPayment_Date(LocalDate payment_Date) {
        this.Payment_Date = payment_Date;
    }

    public String getPayment_Method() {
        return Payment_Method;
    }

    public void setPayment_Method(String payment_Method) {
        this.Payment_Method = payment_Method;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        this.Status = status;
    }
}
