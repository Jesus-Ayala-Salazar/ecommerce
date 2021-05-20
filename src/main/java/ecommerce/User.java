package ecommerce;

import javax.xml.bind.annotation.XmlRootElement;

//You will need to create a Java Object. Jersey uses these to contruct requests and responses.

public class User {
    
    private int userId;
    private String username;
    private String password;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int id) {
        userId = id;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String x) {
        this.username = x;
    }
    
    public String getPassword() {
    return password;
    }

    public void setPassword(String x) {
        password = x;
    }
}