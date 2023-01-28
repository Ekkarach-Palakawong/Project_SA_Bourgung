package ku.cs.services;

import ku.cs.models.QC;
import ku.cs.models.QCList;
import ku.cs.models.VendorOrder;
import ku.cs.models.VendorOrderList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class VendorOrderDataSource implements DataSource<VendorOrderList> {

    private DatabaseConnection databaseConnection;

    public VendorOrderDataSource() {}

    public VendorOrderDataSource(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }


    @Override
    public VendorOrderList readData() {
        VendorOrderList list = new VendorOrderList();

        databaseConnection = new DatabaseConnection();
        Connection connectDB = databaseConnection.getConnection();
        String connectQuery = "SELECT * FROM vender_order";

        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);

            while (queryOutput != null && queryOutput.next()){
                String id = queryOutput.getString("R_ID");
                String amount = queryOutput.getString("R_AMOUNT");
                String sellerName = queryOutput.getString("R_SELLER");
                String status = queryOutput.getString("R_STATUS");
                String orderType = queryOutput.getString("R_ORDER");
                String employeeID = queryOutput.getString("E_ID");



                list.addVendorOrder(
                        new VendorOrder(
                                id,
                                Integer.parseInt(amount),
                                sellerName,
                                status,
                                orderType,
                                employeeID



                        )
                );

            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public void insertData(VendorOrderList vendorOrderList) {

    }

}
