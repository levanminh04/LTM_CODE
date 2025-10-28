package UDP;

import java.io.Serializable;

public class Product implements Serializable {
    private static final long serialVersionUID = 20161107;
    private String id;
    private String code;
    private String name;
    private int quantity;

    public Product(String code, String id, int quantity, String name) {
        this.code = code;
        this.id = id;
        this.quantity = quantity;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
