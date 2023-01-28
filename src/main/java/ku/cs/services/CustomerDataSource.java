package ku.cs.services;

import ku.cs.models.Customer;
import ku.cs.models.CustomerList;
import ku.cs.models.ManagePrawn;
import ku.cs.models.ManagePrawnList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class CustomerDataSource implements DataSource<CustomerList>{

    private DatabaseConnection databaseConnection;

    public CustomerDataSource() {}

    public CustomerDataSource(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }
    @Override
    public CustomerList readData() {
        CustomerList list = new CustomerList();

        databaseConnection = new DatabaseConnection();

        Connection connectDB = databaseConnection.getConnection();

        String connectQuery = "SELECT * FROM customer";

        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);

            while (queryOutput != null && queryOutput.next()){
                String id = queryOutput.getString("C_ID");
                String name = queryOutput.getString("C_NAME");
                String phoneNumber = queryOutput.getString("C_TEL");
                String address = queryOutput.getString("C_ADDRESS");


                list.addCustomer(
                        new Customer(
                                id,
                                name,
                                phoneNumber,
                                address

                        )
                );

            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public void insertData(CustomerList customerList) {

    }


}
