package ku.cs.services;



import ku.cs.models.PreparePond;
import ku.cs.models.PreparePondList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class PreparePondDataSource implements DataSource<PreparePondList> {

    private DatabaseConnection databaseConnection;

    public PreparePondDataSource() {}

    public PreparePondDataSource(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }


    @Override
    public PreparePondList readData() {
        PreparePondList list = new PreparePondList();

        databaseConnection = new DatabaseConnection();
        Connection connectDB = databaseConnection.getConnection();
        String connectQuery = "SELECT * FROM prepare_pond";

        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);

            while (queryOutput != null && queryOutput.next()){
                String prepareID = queryOutput.getString("T_ID");
                String status = queryOutput.getString("T_STATUS");
                String note = queryOutput.getString("T_NOTE");
                String employeeID = queryOutput.getString("E_ID");
                String pondID = queryOutput.getString("W_ID");

                list.addPreparePond(
                        new PreparePond(
                                prepareID,
                                status,
                                note,
                                employeeID,
                                pondID

                        )
                );

            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public void insertData(PreparePondList preparePondList) {

    }

}

