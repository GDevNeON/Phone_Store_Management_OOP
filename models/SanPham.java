package models;

import java.util.Scanner;

public class SanPham {
    Scanner input = new Scanner(System.in);
    SanPham[] DSSP;
    private String productId;
    private String typeOfProductId;
    private String name;
    private int amount;
    private int price;
    private int status;

    public SanPham() {
        productId = null;
        typeOfProductId = null;
        name = null;
        amount = 0;
        price = 0;
        status = 0;
    }

    public SanPham(String productId, String typeOfProductId, String name, int amount, int price, int status) {
        this.productId = productId;
        this.typeOfProductId = typeOfProductId;
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.status = status;
    }

    public String getProductId() {
        return this.productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getTypeOfProductId() {
        return this.typeOfProductId;
    }

    public void setTypeOfProductId(String typeOfProductId) {
        this.typeOfProductId = typeOfProductId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return this.amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public SanPham productId(String productId) {
        setProductId(productId);
        return this;
    }

    public SanPham typeOfProductId(String typeOfProductId) {
        setTypeOfProductId(typeOfProductId);
        return this;
    }

    public SanPham name(String name) {
        setName(name);
        return this;
    }

    public SanPham amount(int amount) {
        setAmount(amount);
        return this;
    }

    public SanPham price(int price) {
        setPrice(price);
        return this;
    }

    public SanPham status(int status) {
        setStatus(status);
        return this;
    }

    public void nhapProductId(SanPham[] DSSP) {
        while (true) {
            System.out.println("Nhập ID sản phẩm (sp_): ");
            productId = input.nextLine();
            if (productId.isEmpty() || !productId.matches("^sp[0-9]+$") || productId.length() > 6) {
                System.out.println("Nhập sai định dạng.");
            } else {
                int check = 0;
                for (SanPham sanpham : DSSP) {
                    if (getProductId().equals(sanpham.getProductId())) {
                        check = 1;
                        break;
                    }
                }
                if (check == 1) {
                    System.out.println("\t\t\t\t\t\t\t\t +----MÃ SẢN PHẨM BỊ TRÙNG----+");
                }else {
                    break;
                }
            }
        }
        setProductId(productId);
    }

    public void nhapTypeOfProductId(SanPham[] DSSP) {
        do {
            System.out.println("Nhập loại: ");
            typeOfProductId = input.nextLine();
            if (typeOfProductId.isEmpty()) {
                System.out.println("Nhập sai định dạng.");
            }
            if (typeOfProductId.length() > 10) {
                System.out.println("Nhập không quá 10 kí tự.");
            }
        } while (typeOfProductId.isEmpty() || typeOfProductId.length() > 10);
        setTypeOfProductId(typeOfProductId);
    }

    public void nhapName(SanPham[] DSSP) {
        do {
            System.out.println("Nhập tên sản phẩm: ");
            name = input.nextLine();
            if (name.isEmpty()) {
                System.out.println("Nhập sai định dạng.");
            }
            if (name.length() > 20) {
                System.out.println("Nhập không quá 20 kí tự.");
            }
        } while (name.isEmpty() || name.length() > 20);
        setName(name);
    }

    public void nhapAmount(SanPham[] DSSP) {
        do {
            System.out.println("Nhập số lượng: ");
            while (!input.hasNextInt()) {
                System.out.println("Vui lòng nhập một số nguyên.");
                input.next();
            }
            amount = input.nextInt();
            if (amount < 1 || amount > 999) {
                System.out.println("Số lượng phải là một số nguyên dương và có tối đa 3 chữ số.");
            }
        } while (amount < 1 || amount > 999);
        setAmount(amount);
    }

    public void nhapPrice(SanPham[] DSSP) {
        do {
            System.out.println("Nhập giá tiền: ");  
            while (!input.hasNextInt()) {
                System.out.println("Nhập giá tiền phải từ 1.000.000 đến 5.000.000.");
                input.next();
            }
            price = input.nextInt();
            if (price <= 1000000 || price > 60000000) {
                System.out.println("Nhập giá tiền phải từ 1.000.000 đến 60.000.000");
            }
        } while (price <= 1000000 || price > 60000000);
        setPrice(price);
    }

    public void nhapStatus(SanPham[] DSSP) {
        do {
            System.out.println("Nhập trạng thái (0 hoặc 1): ");
            while (!input.hasNextInt()) {
                System.out.println("Vui lòng nhập một số nguyên.");
                input.next();
            }
            status = input.nextInt();
            if (status != 0 && status != 1) {
                System.out.println("Trạng thái phải là 0 hoặc 1.");
            }
        } while (status != 0 && status != 1);
        setStatus(status);
    }
}
