package Entity;

public class SanPham {
    private String ID_Product;
    private String ID_TypeOfProduct;
    private String Name;
    private int Amount;
    private int Amount_remaining;
    private int Price;
    private int Status;

    public SanPham() {
        ID_Product = null;
        ID_TypeOfProduct = null;
        Name = null;
        Amount = 0;
        Amount_remaining = 0;
        Price = 0;
        Status = 0;
    }

    public SanPham(String iD_Product, String iD_TypeOfProduct, String name, int amount, int amount_remaining, int price,
                   int status) {
        this.ID_Product = iD_Product;
        this.ID_TypeOfProduct = iD_TypeOfProduct;
        this.Name = name;
        this.Amount = amount;
        this.Amount_remaining = amount_remaining;
        this.Price = price;
        this.Status = status;
    }

    public String getID_Product() {
        return ID_Product;
    }

    public void setID_Product(String iD_Product) {
        this.ID_Product = iD_Product;
    }

    public String getID_TypeOfProduct() {
        return ID_TypeOfProduct;
    }

    public void setID_TypeOfProduct(String iD_TypeOfProduct) {
        this.ID_TypeOfProduct = iD_TypeOfProduct;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        this.Amount = amount;
    }

    public int getAmount_remaining() {
        return Amount_remaining;
    }

    public void setAmount_remaining(int amount_remaining) {
        this.Amount_remaining = amount_remaining;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        this.Status = status;
    }

}
