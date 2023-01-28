package ku.cs.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class Pond {
    private String id;
    private String status;
    private String detail;
    private Connection connection;
    private PreparedStatement pst;
    private String databaseName = "borgung";

    public Pond(String id, String status, String detail) {
        this.id = id;
        this.status = status;
        this.detail = detail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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
            pst = connection.prepareStatement("Insert into pond(W_NO ,W_STATUS,W_DETAILS)values(?,?,?)");
            pst.setString(1, this.id);
            pst.setString(2, this.status);

            pst.setString(3, this.detail);

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
            pst = connection.prepareStatement("UPDATE pond SET W_STATUS = ?,W_DETAILS = ? WHERE W_ID=?");
            pst.setString(1, this.status);
            pst.setString(2, this.detail);
            pst.setString(3, this.id);

            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
