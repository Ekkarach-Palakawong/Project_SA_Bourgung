package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import ku.cs.services.PrawnDataSource;
import ku.cs.services.VendorOrderDataSource;

import java.io.IOException;
import java.util.ArrayList;

public class StaffHomeController {


    private ArrayList<String> getItem;
    private ArrayList<String> passItem;

    @FXML
    public void initialize() {
        getItem = (ArrayList<String>) com.github.saacsos.FXRouter.getData();
        String userID  = getItem.get(0);
        System.out.println("userID is " + userID);


        passItem = new ArrayList<>();
    }

    private void setPassItem(String location) throws IOException {
        String userID  = getItem.get(0);
        passItem.add(userID);

        com.github.saacsos.FXRouter.goTo(location,passItem);
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

    @FXML
    public void staffGetShrimpButton(ActionEvent actionEvent){
        try {
            setPassItem("staffGetShrimp");
            com.github.saacsos.FXRouter.goTo("staffGetShrimp");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า main ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด main");
        }
    }

    @FXML
    public void staffPrepareButton(ActionEvent actionEvent){
        try {
            setPassItem("staffPrepareBorgung");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า main ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด main");
        }
    }

    @FXML
    public void staffShrimpFarmingButton(ActionEvent actionEvent){
        try {
            com.github.saacsos.FXRouter.goTo("staffShrimpFarming");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า main ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด main");
        }
    }

    @FXML
    public void staffQCButton(ActionEvent actionEvent){
        try {
            setPassItem("staffQC");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า main ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด main");
        }
    }

    @FXML
    public void staffDeliveryButton(ActionEvent actionEvent){
        try {
            com.github.saacsos.FXRouter.goTo("staffDelivery");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า main ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด main");
        }
    }
}
