package ku.cs.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class ManagePrawn {
    private String id;
    private String type;
    private String note;
    private String date;
    private String farmingID;

    private Connection connection;
    private PreparedStatement pst;
    private String databaseName = "borgung";


    public ManagePrawn(String id, String type, String note, String date, String farmingID) {
        this.id = id;
        this.type = type;
        this.note = note;
        this.date = date;
        this.farmingID = farmingID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFarmingID() {
        return farmingID;
    }

    public void setFarmingID(String farmingID) {
        this.farmingID = farmingID;
    }

    public boolean checkPrawnId(String id){
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
            pst = connection.prepareStatement("Insert into manage_prawn(D_ID,D_TYPE,D_NOTE,D_DATE,F_ID)values(?,?,?,?,?)");
            pst.setString(1, this.id);
            pst.setString(2, this.type);
            pst.setString(3, this.note);
            pst.setString(4, this.date);
            pst.setString(5, this.farmingID);

            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
