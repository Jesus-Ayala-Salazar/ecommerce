package ecommerce;


import javax.xml.bind.annotation.XmlRootElement;

//You will need to create a Java Object. Jersey uses these to contruct requests and responses.

public class Product {
    
    private int productId;
    private String description;
    private double price;
    private String name;
    private String Url;

    public int getId() {
        return productId;
    }

    public void setId(int x) {
        this.productId = x;
    }
    
    public String getDescription() {
    return this.description;
    }

    public void setDescription(String s) {
        this.description = s;
    }
    
    public double getPrice() {
    return this.price;
    }

    public void setPrice(double x) {
        this.price = x;
    }
    
    public String getName() {
    return name;
    }

    public void setName(String s) {
        this.name = s;
    }
    
    public String getUrl() {
    return Url;
    }

    public void setUrl(String s) {
        this.Url= s;
    }
}