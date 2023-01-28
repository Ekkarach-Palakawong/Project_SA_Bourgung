package ku.cs.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class PurchaseOrder {

    private String id,purchaseType,status, customerID, farmingID;
    private int price;
    private Connection connection;
    private PreparedStatement pst;
    private String databaseName = "borgung";

    public PurchaseOrder(String id, String purchaseType, int price , String status, String customerID, String farmingID) {
        this.id = id;
        this.purchaseType = purchaseType;
        this.status = status;
        this.customerID = customerID;
        this.price = price;
        this.farmingID = farmingID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public int getPrice() {
        return price;
    }

    public String getFarmingID() {
        return farmingID;
    }

    public void setFarmingID(String farmingID) {
        this.farmingID = farmingID;
    }

    public void setPrice(int price) {
        this.price = price;
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
            pst = connection.prepareStatement("Insert into purchase_order(O_ID,O_PURCHASE_TYPE,O_PRICE,O_STATUS,C_ID,F_ID )values(?,?,?,?,?,?)");
            pst.setString(1, this.id);
            pst.setString(2, this.purchaseType);
            pst.setString(3, Integer.toString(this.price));
            pst.setString(4, this.status);
            pst.setString(5, this.customerID);
            pst.setString(6, this.farmingID);

            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void updateToSql() {
        try {
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (Exception e) {
                System.out.println(e);
            }
            String url = "jdbc:mysql://localhost:3306/" + databaseName;
            connection = DriverManager.getConnection(url , "root","");
            pst = connection.prepareStatement("UPDATE purchase_order SET O_STATUS = ? WHERE O_ID=?");
            pst.setString(1, this.status);
            pst.setString(2, this.id);


            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
