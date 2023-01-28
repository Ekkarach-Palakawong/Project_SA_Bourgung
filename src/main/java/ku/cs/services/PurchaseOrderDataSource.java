package ku.cs.services;

import ku.cs.models.PreparePond;
import ku.cs.models.PreparePondList;
import ku.cs.models.PurchaseOrder;
import ku.cs.models.PurchaseOrderList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class PurchaseOrderDataSource implements DataSource<PurchaseOrderList> {

    private DatabaseConnection databaseConnection;

    public PurchaseOrderDataSource() {}

    public PurchaseOrderDataSource(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }


    @Override
    public PurchaseOrderList readData() {
        PurchaseOrderList list = new PurchaseOrderList();

        databaseConnection = new DatabaseConnection();
        Connection connectDB = databaseConnection.getConnection();
        String connectQuery = "SELECT * FROM purchase_order";

        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);

            while (queryOutput != null && queryOutput.next()){
                String id = queryOutput.getString("O_ID");
                String purchaseType = queryOutput.getString("O_PURCHASE_TYPE");
                String price = queryOutput.getString("O_PRICE");
                String status = queryOutput.getString("O_STATUS");
                String customerID = queryOutput.getString("C_ID");
                String farmingID = queryOutput.getString("F_ID");



                list.addPurchaseOrder(
                        new PurchaseOrder(
                                id,
                                purchaseType,
                                Integer.parseInt(price),
                                status,
                                customerID,
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
    public void insertData(PurchaseOrderList purchaseOrderList) {

    }

}
