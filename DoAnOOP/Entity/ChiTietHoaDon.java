package Entity;

public class ChiTietHoaDon {
	String ID_Receipt;
	String ID_Product;
	int Amount;
	int Price;

	public ChiTietHoaDon() {
		super();
	}

	public ChiTietHoaDon(String iD_Receipt, String iD_Product, int amount, int price) {
		super();
		this.ID_Receipt = iD_Receipt;
		this.ID_Product = iD_Product;
		this.Amount = amount;
		this.Price = price;
	}

	public String getID_Receipt() {
		return ID_Receipt;
	}

	public void setID_Receipt(String iD_Receipt) {
		this.ID_Receipt = iD_Receipt;
	}

	public String getID_Product() {
		return ID_Product;
	}

	public void setID_Product(String iD_Product) {
		this.ID_Product = iD_Product;
	}

	public int getAmount() {
		return Amount;
	}

	public void setAmount(int amount) {
		this.Amount = amount;
	}

	public int getPrice() {
		return Price;
	}

	public void setPrice(int price) {
		this.Price = price;
	}
}
