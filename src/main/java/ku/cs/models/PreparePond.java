package ku.cs.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class PreparePond {
    private String prepareID,status,note,employeeID,pondID;
    private Connection connection;
    private PreparedStatement pst;
    private String databaseName = "borgung";

    public PreparePond(String prepareID, String status, String note,  String employeeID, String pondID) {
        this.prepareID = prepareID;
        this.status = status;
        this.note = note;
        this.employeeID = employeeID;
        this.pondID = pondID;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPrepareID() {
        return prepareID;
    }

    public void setPrepareID(String prepareID) {
        this.prepareID = prepareID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getPondID() {
        return pondID;
    }

    public void setPondID(String pondID) {
        this.pondID = pondID;
    }

    public boolean checkId(String id) {
        return this.prepareID.equals(id);
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
            pst = connection.prepareStatement("Insert into prepare_pond(T_ID ,T_STATUS,T_NOTE,E_ID, W_ID)values(?,?,?,?,?)");
            pst.setString(1, this.prepareID);
            pst.setString(2, this.status);
            pst.setString(3, this.note);
            pst.setString(4, this.employeeID);
            pst.setString(5, this.pondID);

            //String id, String status, String detail



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
            pst = connection.prepareStatement("UPDATE prepare_pond SET T_STATUS = ?WHERE T_ID=?");
            pst.setString(1, this.status);
            pst.setString(2, this.prepareID);

            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


}
