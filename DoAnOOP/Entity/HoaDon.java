package Entity;


public class HoaDon {
	    private String ID_Receipt;
	    private int Price;
	    private String DatePurchase;
	    private String PurchaseMethod;
	    private String ID_Customer;
		private String ID_Employee;
		public HoaDon(String iD_receipt, int price, String datePurchase, String purchaseMethod, String iD_Customer, String ID_Employee) {
			super();
			this.ID_Receipt = iD_receipt;
			this.Price = price;
			this.DatePurchase = datePurchase;
			this.PurchaseMethod = purchaseMethod;
			this.ID_Employee = ID_Employee;
			this.ID_Customer = iD_Customer;
		}

    public HoaDon() {

    }

    public String getID_Receipt() {
			return ID_Receipt;
		}
		public void setID_Receipt(String iD_Receipt) {
			this.ID_Receipt = iD_Receipt;
		}
		public int getPrice() {
			return Price;
		}
		public void setPrice(int price) {
			this.Price = price;
		}
		public String getDatePurchase() {
			return DatePurchase;
		}
		public void setDatePurchase(String datePurchase) {
			this.DatePurchase = datePurchase;
		}
		public String getPurchaseMethod() {
			return PurchaseMethod;
		}
		public void setPurchaseMethod(String purchaseMethod) {
			this.PurchaseMethod = purchaseMethod;
		}
		public String getID_Employee() {
			return ID_Employee;
		}
		public void setID_Employee(String ID_Employee) {
			this.ID_Employee = ID_Employee;
		}
		public String getID_Customer() {
			return ID_Customer;
		}
		public void setID_Customer(String iD_Customer) {
			this.ID_Customer = iD_Customer;
		}
}