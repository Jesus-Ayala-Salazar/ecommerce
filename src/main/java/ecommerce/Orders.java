package ecommerce;


import javax.xml.bind.annotation.XmlRootElement;

//You will need to create a Java Object. Jersey uses these to contruct requests and responses.

public class Orders {
    
    private int orderId;
    private int productId;
    private int customerId;
    private int quantity;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int x) {
        this.orderId = x;
    }
    
    public int getProductId() {
    return productId;
    }

    public void setProductId(int x) {
        this.productId = x;
    }
    
    public int getCustomerId() {
    return customerId;
    }

    public void setCustomerId(int x) {
        this.customerId = x;
    }
    
    public int getQuantity() {
    return customerId;
    }

    public void setQuantity(int x) {
        this.quantity = x;
    }
}