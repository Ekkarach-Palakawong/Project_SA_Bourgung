package ku.cs.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Customer {
    private String id;
    private String name;
    private String phoneNumber;
    private String address;
    private Connection connection;
    private PreparedStatement pst;
    private String databaseName = "borgung";

    public Customer(String id, String name, String phoneNumber, String address) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean checkId(String id) {
        return this.id.equals(id);
    }

    public boolean checkName(String name) {
        return this.name.equals(name);
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
            pst = connection.prepareStatement("Insert into customer(C_ID,C_NAME,C_TEL,C_ADDRESS)values(?,?,?,?)");
            pst.setString(1, this.id);
            pst.setString(2, this.name);
            pst.setString(3, this.phoneNumber);
            pst.setString(4, this.address);


            //String id, String name, String phoneNumber, String address

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
            pst = connection.prepareStatement("UPDATE customer SET C_TEL = ?,C_ADDRESS = ? WHERE C_ID=?");
            pst.setString(1, this.phoneNumber);
            pst.setString(2, this.address);
            pst.setString(3, this.id);

            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


}
