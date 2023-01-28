package ku.cs.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class VendorOrder {

    String id,sellerName,employeeID,status,orderType;
    int amount;
    private Connection connection;
    private PreparedStatement pst;
    private String databaseName = "borgung";

    public VendorOrder(String id, int amount, String sellerName, String status,String orderType, String employeeID) {
        this.id = id;
        this.amount = amount;
        this.sellerName = sellerName;
        this.employeeID = employeeID;
        this.orderType = orderType;
        this.status = status;

    }



    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;

    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean checkId(String id) {
        return this.id.equals(id);
    }

    public void insertToSql() {
        try {
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (Exception e) {
                System.out.println(e);
            }
            String url = "jdbc:mysql://localhost:3306/" + databaseName;
            connection = DriverManager.getConnection(url , "root","");
            pst = connection.prepareStatement("Insert into vender_order(R_ID,R_AMOUNT,R_SELLER,R_STATUS,R_ORDER,E_ID)values(?,?,?,?,?,?)");
            pst.setString(1, this.id);
            pst.setString(2, this.amount+"");
            pst.setString(3, this.sellerName);
            pst.setString(4, this.status);
            pst.setString(5, this.orderType);
            pst.setString(6, this.employeeID);

            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void insertToSql2() {
        try {
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (Exception e) {
                System.out.println(e);
            }
            String url = "jdbc:mysql://localhost:3306/" + databaseName;
            connection = DriverManager.getConnection(url , "root","");
            pst = connection.prepareStatement("Insert into vender_order(R_AMOUNT,R_SELLER,R_ORDER,E_ID)values(?,?,?,?)");

            pst.setString(1, this.amount+"");
            pst.setString(2, this.sellerName);

            pst.setString(3, this.orderType);
            pst.setString(4, this.employeeID);

            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    //String id, int amount, String sellerName, String status,String orderType, String employeeID



    public void updateToSql() {
        try {
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (Exception e) {
                System.out.println(e);
            }
            String url = "jdbc:mysql://localhost:3306/" + databaseName;
            connection = DriverManager.getConnection(url , "root","");
            pst = connection.prepareStatement("UPDATE vender_order SET R_STATUS= ? WHERE R_ID=?");
            pst.setString(1, this.status);
            pst.setString(2, this.id);
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
