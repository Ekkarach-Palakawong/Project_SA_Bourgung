package ku.cs.services;

import ku.cs.models.Employee;
import ku.cs.models.EmployeeList;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class EmployeeDataSource implements DataSource<EmployeeList> {

    private DatabaseConnection databaseConnection;

    public EmployeeDataSource() {}

    public EmployeeDataSource(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }


    @Override
    public EmployeeList readData() {

        EmployeeList list = new EmployeeList();

        databaseConnection = new DatabaseConnection();

        Connection connectDB = databaseConnection.getConnection();
        String connectQuery = "SELECT * FROM employee";

        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);

            while (queryOutput != null && queryOutput.next()){

                String id = queryOutput.getString("E_ID");
                String name = queryOutput.getString("E_NAME");
                String dateOfBirth = queryOutput.getString("E_DOB");
                String sex = queryOutput.getString("E_SEX");
                String address = queryOutput.getString("E_ADDRESS");
                String phoneNumber = queryOutput.getString("E_TEL");
                String startWorkingDate = queryOutput.getString("E_STARTWORK");
                String password = queryOutput.getString("E_PASSWORD");
                String role = queryOutput.getString("E_ROLE");

                list.addEmployee(
                        new Employee(
                                id,
                                name,
                                dateOfBirth,
                                Integer.parseInt(sex),
                                address,
                                phoneNumber,
                                startWorkingDate,
                                password,
                                role
                        )
                );

            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public void insertData(EmployeeList employeeList) {

    }

}

