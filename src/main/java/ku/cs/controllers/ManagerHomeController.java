package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class ManagerHomeController {

    @FXML
    public void toPrepareBorgung(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("managerPrepareBorgung");
        } catch (IOException ex) {
            System.err.println(ex.toString());
            System.err.println("ไม่สามารถเข้าหน้า managerPrepareBorgung");
        }
    }

    @FXML
    public void toCheckStock(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("managerSellOrder");
        } catch (IOException ex) {
            System.err.println(ex.toString());
            System.err.println("ไม่สามารถเข้าหน้า managerCheckStock");
        }
    }

    @FXML
    public void toOrderShrimp(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("managerOrderShrimp");
        } catch (IOException ex) {
            System.err.println(ex.toString());
            System.err.println("ไม่สามารถเข้าหน้า managerOrderShrimp");
        }
    }

    @FXML
    public void logoutBtn(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("login");
        } catch (IOException ex) {
            System.err.println(ex.toString());
            System.err.println("ไม่สามารถเข้าหน้า login");
        }
    }




}
