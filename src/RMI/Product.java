package RMI;

import java.io.Serializable;

public class Product implements Serializable {
    private static final long serialVersionUID = 20151107L;
    String id;
    String code;
    double importPrice;
    double exportPrice;

    public Product() {
    }

    public Product(String id, String code, double importPrice, double exportPrice) {
        this.id = id;
        this.code = code;
        this.importPrice = importPrice;
        this.exportPrice = exportPrice;
    }

    public String getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public double getImportPrice() {
        return importPrice;
    }

    public double getExportPrice() {
        return exportPrice;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setImportPrice(double importPrice) {
        this.importPrice = importPrice;
    }

    public void setExportPrice(double exportPrice) {
        this.exportPrice = exportPrice;
    }


}
