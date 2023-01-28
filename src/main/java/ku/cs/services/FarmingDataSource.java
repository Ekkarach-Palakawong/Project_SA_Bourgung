package ku.cs.services;

import ku.cs.models.Farming;
import ku.cs.models.FarmingList;
import ku.cs.models.ManagePrawn;
import ku.cs.models.ManagePrawnList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class FarmingDataSource implements DataSource<FarmingList> {

    private DatabaseConnection databaseConnection;

    public FarmingDataSource() {}

    public FarmingDataSource(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }


    @Override
    public FarmingList readData() {
        FarmingList list = new FarmingList();

        databaseConnection = new DatabaseConnection();
        Connection connectDB = databaseConnection.getConnection();
        String connectQuery = "SELECT * FROM farming";

        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);

            while (queryOutput != null && queryOutput.next()){
                String FarmingID = queryOutput.getString("F_ID");
                String pondID = queryOutput.getString("W_ID");
                String prawnID = queryOutput.getString("P_ID");
                String round = queryOutput.getString("F_ROUND");
                String prawnAmount = queryOutput.getString("F_AMOUNT");
                String getDate = queryOutput.getString("F_GETDATE");
                String sellDate = queryOutput.getString("F_SELLDATE");
                String FarmingStatus = queryOutput.getString("F_STATUS");
                String vendorOrderID = queryOutput.getString("R_ID");

                list.addFarming(
                        new Farming(
                                FarmingID,
                                pondID,
                                Integer.parseInt(round),
                                Integer.parseInt(prawnAmount),
                                prawnID,
                                getDate,
                                sellDate,
                                FarmingStatus,
                                vendorOrderID
                        )
                );

            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public void insertData(FarmingList farmingList) {

    }

}
