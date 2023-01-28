package ku.cs.services;


import ku.cs.models.QC;
import ku.cs.models.QCList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class QCDataSource implements DataSource<QCList> {

    private DatabaseConnection databaseConnection;

    public QCDataSource() {}

    public QCDataSource(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }


    @Override
    public QCList readData() {
        QCList list = new QCList();

        databaseConnection = new DatabaseConnection();
        Connection connectDB = databaseConnection.getConnection();
        String connectQuery = "SELECT * FROM qc";

        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);

            while (queryOutput != null && queryOutput.next()){
                String id = queryOutput.getString("Q_ID");
                String time = queryOutput.getString("Q_TIME");
                String manageStatus = queryOutput.getString("Q_STATUS");
                String note = queryOutput.getString("Q_NOTE");
                String employeeID = queryOutput.getString("E_ID");
                String farmingID = queryOutput.getString("F_ID");

                list.addQC(
                        new QC(
                                id,
                                time,
                                manageStatus,
                                note,
                                employeeID,
                                farmingID

                        )
                );

            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public void insertData(QCList qcList) {

    }

}
