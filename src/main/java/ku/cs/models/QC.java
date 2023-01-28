package ku.cs.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class QC {
    private String id, time, manageStatus, note, employeeID, farmingID;
    private Connection connection;
    private PreparedStatement pst;
    private String databaseName = "borgung";

    public QC(String id, String time, String manageStatus, String note, String employeeID, String farmingID) {
        this.id = id;
        this.time = time;
        this.manageStatus = manageStatus;
        this.note = note;
        this.employeeID = employeeID;
        this.farmingID = farmingID;
    }

    public String getFarmingID() {
        return farmingID;
    }

    public void setFarmingID(String farmingID) {
        this.farmingID = farmingID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getManageStatus() {
        return manageStatus;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setManageStatus(String manageStatus) {
        this.manageStatus = manageStatus;
    }

//    public String getRequirement() {
//        return requirement;
//    }
//
//    public void setRequirement(String requirement) {
//        this.requirement = requirement;
//    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

//    public String getPondID() {
//        return pondID;
//    }
//
//    public void setPondID(String pondID) {
//        this.pondID = pondID;
//    }

    public boolean checkID(String id) {
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
            pst = connection.prepareStatement("Insert into qc(Q_ID,Q_TIME,Q_STATUS,Q_NOTE,E_ID,F_ID)values(?,?,?,?,?,?)");
            pst.setString(1, this.id);
            pst.setString(2, this.time);
            pst.setString(3, this.manageStatus);
            pst.setString(4, this.note);
            pst.setString(5, this.employeeID);
            pst.setString(6, this.farmingID);
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
