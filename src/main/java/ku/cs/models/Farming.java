package ku.cs.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Farming {

    private String farmingID;
    private String pondID;
    private int round;
    private int prawnAmount;
    private String prawnID;
    private String getDate;
    private String sellDate;
    private String farmingStatus;
    private String vendorOrderID;

    private Connection connection;
    private PreparedStatement pst;
    private String databaseName = "borgung";


    public Farming(String farmingID ,String pondID, int round, int prawnAmount, String prawnID, String getDate, String sellDate,String FarmingStatus, String vendorOrderID) {

        this.farmingID = farmingID;
        this.pondID = pondID;
        this.round = round;
        this.prawnAmount = prawnAmount;
        this.prawnID = prawnID;
        this.getDate = getDate;
        this.sellDate = sellDate;
        this.farmingStatus = FarmingStatus;
        this.vendorOrderID = vendorOrderID;

    }

    public String getFarmingID() {
        return farmingID;
    }

    public void setFarmingID(String farmingID) {
        this.farmingID = farmingID;
    }

    public String getPondID() {
        return pondID;
    }

    public void setPondID(String pondID) {
        this.pondID = pondID;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getPrawnAmount() {
        return prawnAmount;
    }

    public void setPrawnAmount(int prawnAmount) {
        this.prawnAmount = prawnAmount;
    }

    public String getPrawnID() {
        return prawnID;
    }

    public void setPrawnID(String prawnID) {
        this.prawnID = prawnID;
    }

    public String getGetDate() {
        return getDate;
    }

    public void setGetDate(String getDate) {
        this.getDate = getDate;
    }

    public String getSellDate() {
        return sellDate;
    }

    public void setSellDate(String sellDate) {
        this.sellDate = sellDate;
    }



    public String getVendorOrderID() {
        return vendorOrderID;
    }

    public void setVendorOrderID(String vendorOrderID) {
        this.vendorOrderID = vendorOrderID;
    }

    public boolean checkId(String id) {
        return this.farmingID.equals(id);
    }

//    public boolean checkRound(int round) {
//        if (round)
//    }

    public String getFarmingStatus() {
        return farmingStatus;
    }

    public void setFarmingStatus(String farmingStatus) {
        this.farmingStatus = farmingStatus;
    }

    final static String DATE_FORMAT = "dd-MM-yyyy";

    public void insertToSql() {
        try {
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (Exception e) {
                System.out.println(e);
            }
            String url = "jdbc:mysql://localhost:3306/" + databaseName;
            connection = DriverManager.getConnection(url , "root","");
            pst = connection.prepareStatement("Insert into farming(F_ID,W_ID,P_ID,F_ROUND,F_AMOUNT,F_GETDATE,F_SELLDATE,F_STATUS,R_ID)values(?,?,?,?,?,?,?,?,?)");
            pst.setString(1, this.farmingID);
            pst.setString(2, this.pondID);
            pst.setString(3, this.prawnID);
            pst.setString(4, Integer.toString(this.round));
            pst.setString(5, Integer.toString(this.prawnAmount));
            pst.setString(6, this.getDate);
            pst.setString(7, this.sellDate);
            pst.setString(8, this.farmingStatus);
            pst.setString(9, this.vendorOrderID);
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
            pst = connection.prepareStatement("UPDATE farming SET F_SELLDATE = ?,F_STATUS = ? WHERE F_ID=?");
            pst.setString(1, this.sellDate);
            pst.setString(2, this.farmingStatus);
            pst.setString(3, this.farmingID);
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
