package ku.cs.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Employee {
    private String id;
    private String name;
    private String dateOfBirth;
    private int sex;
    private String address;
    private String phoneNumber;
    private String startWorkingDate;
    private String password;
    private String role;

    private Connection connection;
    private PreparedStatement pst;
    private String databaseName = "borgung";

    public Employee(String id, String name, String dateOfBirth, int sex, String address, String phoneNumber, String startWorkingDate, String password, String role) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.startWorkingDate = startWorkingDate;
        this.password = password;
        this.role = role;
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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStartWorkingDate() {
        return startWorkingDate;
    }

    public void setStartWorkingDate(String startWorkingDate) {
        this.startWorkingDate = startWorkingDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
            pst = connection.prepareStatement("Insert into employee(E_ID,E_NAME,E_DOB,E_SEX,E_ADDRESS,E_TEL,E_STARTWORK,E_PASSWORD,E_ROLE)values(?,?,?,?,?,?,?,?,?)");
            pst.setString(1, this.id);
            pst.setString(2, this.name);
            pst.setString(3, this.dateOfBirth);
            pst.setString(4, this.sex+"");
            pst.setString(5, this.address);
            pst.setString(6, this.phoneNumber);
            pst.setString(7, this.startWorkingDate);
            pst.setString(8, this.password);
            pst.setString(9, this.role);
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
            pst = connection.prepareStatement("UPDATE employee SET E_NAME = ?,E_DOB = ?,E_SEX= ? ,E_ADDRESS= ? ,E_TEL= ? ,E_STARTWORK= ?,E_PASSWORD= ?,E_ROLE= ? WHERE E_ID=?");
            pst.setString(1, this.id);
            pst.setString(2, this.name);
            pst.setString(3, this.dateOfBirth);
            pst.setString(4, this.sex+"");
            pst.setString(5, this.address);
            pst.setString(6, this.phoneNumber);
            pst.setString(7, this.startWorkingDate);
            pst.setString(8, this.password);
            pst.setString(9, this.role);
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }





}
