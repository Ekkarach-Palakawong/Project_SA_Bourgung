package ku.cs.services;


import ku.cs.models.Pond;
import ku.cs.models.PondList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class PondDataSource implements DataSource<PondList> {

    private DatabaseConnection databaseConnection;

    public PondDataSource() {}

    public PondDataSource(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }


    @Override
    public PondList readData() {
        PondList list = new PondList();

        databaseConnection = new DatabaseConnection();
        Connection connectDB = databaseConnection.getConnection();
        String connectQuery = "SELECT * FROM pond";

        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);

            while (queryOutput != null && queryOutput.next()){
                String id = queryOutput.getString("W_ID");
                String status = queryOutput.getString("W_STATUS");
                String detail = queryOutput.getString("W_DETAILS");


                list.addPond(
                        new Pond(
                                id,
                                status,
                                detail

                        )
                );

            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public void insertData(PondList pondList) {

    }

}
