package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import ku.cs.models.*;
import ku.cs.services.DataSource;
import ku.cs.services.DatabaseConnection;
import ku.cs.services.EmployeeDataSource;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class LoginController {

    @FXML
    TextField usernameField;
    @FXML
    PasswordField passwordField;

    private EmployeeList employeeList;
    private DataSource<EmployeeList> employeeListDataSource;

    private ArrayList<String> passItem;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";

    @FXML private Label warningUsernameLabel;
    @FXML private Label warningLabel;

    @FXML
    public void initialize() {
        passItem = new ArrayList<>();
        employeeListDataSource = new EmployeeDataSource();
        employeeList = employeeListDataSource.readData();
    }

    @FXML
    public void loginButton(ActionEvent event) {

        if (employeeList.isCorrectPair(usernameField.getText(), passwordField.getText() )) {

            try {
                System.out.println(ANSI_GREEN +  "Log in finished !" + ANSI_RESET);

                if (employeeList.getEmployeeById(usernameField.getText()).getRole().equals("ผู้จัดการ")){
                    setPassItem("managerHome"); // will do later
                }else{
                    setPassItem("staffHome");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        else if (usernameField.getText().equals("") && passwordField.getText().equals("")){
            warningLabel.setText("Please Enter Username and Password");
            warningLabel.setTextFill(Color.web("#FFA500", 1));
            warningUsernameLabel.setText("");
        }
        else if (usernameField.getText().equals("")){
            warningUsernameLabel.setText("Please Enter Username");
            warningUsernameLabel.setTextFill(Color.web("#FFA500", 1));
            warningLabel.setText("");



        }
        else if (passwordField.getText().equals("")){
            warningLabel.setText("Please Enter Password");
            warningLabel.setTextFill(Color.web("#FFA500", 1));
            warningUsernameLabel.setText("");

        }


        else {
            warningLabel.setText("username หรือ password ไม่ถูกต้อง");
            warningLabel.setTextFill(Color.web("#ff0000", 1));
            warningUsernameLabel.setText("");
            System.err.println("username หรือ password ไม่ถูกต้อง");
            System.err.println("โปรดตรวจสอบ username และ password ");
        }
    }

    private void setPassItem(String location) throws IOException {
        String userID = usernameField.getText();
        passItem.add(userID);
        com.github.saacsos.FXRouter.goTo(location,passItem);
    }

    @FXML
    public void backBtn(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("managerOrderShrimp");
        } catch (IOException ex) {
            System.err.println(ex.toString());
            System.err.println("ไม่สามารถเข้าหน้า managerOrderShrimp");
        }
    }









}
