package ku.cs.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ku.cs.models.*;
import ku.cs.services.*;

import java.io.IOException;
import java.util.ArrayList;

public class ManagePrawnController {
    @FXML private Button codeOneButton;
    @FXML private Button codeTwoButton;
    @FXML private Button codeThreeButton;
    @FXML private Button normalStatusButton;
    @FXML private Button sickStatusButton;
    @FXML private Button problemStatusButton;
    @FXML private TextArea textNote;
    @FXML private Label farmingLabel;
    @FXML private Label warningLabel;
    @FXML private TextField dateTextField;
    @FXML private ListView<String> farmingListView;
    @FXML private TableView<Farming> farmingTableView;

    private String noteType;
    private String statusType;
    private ObservableList<Farming> ObservableList;
    private FarmingList farmingList;
    private DataSource<FarmingList> farmingListDataSource;
    private ManagePrawnList managePrawnList;
    private DataSource<ManagePrawnList> managePrawnListDataSource;


    @FXML
    public void initialize() {
        farmingListDataSource = new FarmingDataSource();
        farmingList = farmingListDataSource.readData();
        managePrawnListDataSource = new ManagePrawnDataSource();
        managePrawnList = managePrawnListDataSource.readData();

        showProductData();
        clearSelectedProduct();
        farmingTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                warningLabel.setText("");
                showSelectedFarming(newValue);
            }
        });
    }

    private void showProductData() {
        farmingTableView.getItems().clear();
        farmingTableView.getColumns().clear();
        ObservableList = FXCollections.observableArrayList(farmingList.getStaffFarming());
        farmingTableView.setItems(ObservableList);
        ///แสดงแถวแนวตรง
        ArrayList<StringConfiguration> configs = new ArrayList<>();
        configs.add(new StringConfiguration("title:pondID", "field:pondID"));
//        configs.add(new StringConfiguration("title:หมายเลข", "field:pondID"));


        for (StringConfiguration conf: configs) {
            TableColumn col = new TableColumn(conf.get("title"));
            col.setCellValueFactory(new PropertyValueFactory<>(conf.get("field")));
            farmingTableView.getColumns().add(col);
        }
    }
    public void  showSelectedFarming(Farming farming){
        farmingLabel.setText(farming.getPondID());
        if (farming.getFarmingStatus().equals("ปกติ")){
            statusType = "ปกติ";
            this.normalStatusButton.setStyle("-fx-background-color: #FF8C00;");
            this.sickStatusButton.setStyle("-fx-background-color: #FFD700;");
            this.problemStatusButton.setStyle("-fx-background-color: #FFD700;");
        }else if(farming.getFarmingStatus().equals("ป่วย")){
            statusType = "ป่วย";
            this.sickStatusButton.setStyle("-fx-background-color: #FF8C00;");
            this.normalStatusButton.setStyle("-fx-background-color: #FFD700;");
            this.problemStatusButton.setStyle("-fx-background-color: #FFD700;");
        }else{
            statusType = "เกิดปัญหา";
            this.problemStatusButton.setStyle("-fx-background-color: #FF8C00;");
            this.normalStatusButton.setStyle("-fx-background-color: #FFD700;");
            this.sickStatusButton.setStyle("-fx-background-color: #FFD700;");
        }
    }

    public Farming selectedFarming(){
        Farming selectedFarmingString = farmingTableView.getSelectionModel().selectedItemProperty().get();
//        System.out.println(selectedFarmingString);
//        Farming farming = farmingList.getFarmingById(selectedFarmingString);
        return selectedFarmingString;
    }

    private void clearSelectedProduct() {
        farmingLabel.setText("");
        warningLabel.setText("");
    }

    private boolean checkFarmingDate(String inputDate, String selectedFarmingDate){
        if (farmingList.validateJavaDate(inputDate)){
            if (farmingList.checkDateInManagePrawn(inputDate, selectedFarmingDate)){
                return true;
            }
            else {
                warningLabel.setText("ไม่สามารถบันทึกวันที่ก่อนลงบ่อได้");
                return false;
            }
        }
        else {
            warningLabel.setText("โปรดใส่วันที่รูปแบบนี้ yyyy-mm-dd");
            System.out.println("วันที่ผิด");
            return false;
        }
    }

    private boolean checkManagePrawnDate(String inputDate, String farmingId){
        if (farmingList.validateJavaDate(inputDate)){
            if (managePrawnList.checkDateInManagePrawn(inputDate, farmingId)){
                return true;
            }
            else {
                warningLabel.setText("ไม่สามารถบันทึกวันที่ในอดีตได้");
                return false;
            }
        }
        else {
            warningLabel.setText("โปรดใส่วันที่รูปแบบนี้ yyyy-mm-dd");
            System.out.println("วันที่ผิด");
            return false;
        }
    }

    @FXML
    private void pressSave() {
        try {
            int manageID = managePrawnList.count()+1;
            String manageIDString = "TK"+ manageID;
            String manageType = noteType;
            String manageNote = textNote.getText();
            String inputDate = dateTextField.getText();
            String farmingId = selectedFarming().getFarmingID();
            String selectedFarmingDate = selectedFarming().getGetDate();

            if (checkFarmingDate(inputDate, selectedFarmingDate)){
                if (managePrawnList.latestManagePrawn(farmingId) == null){
                    ManagePrawn managePrawn = new ManagePrawn(manageIDString, manageType, manageNote, inputDate, farmingId);
                    managePrawn.insertToSql();
                    managePrawnList.addManagePrawn(managePrawn);
                    Farming farming = farmingList.getFarmingById(farmingId);
                    farming.setFarmingStatus(statusType);
                    farming.updateToSql();
//                farmingList.addFarming(farming);
                    warningLabel.setText("บันทึกเสร็จสิ้น");
                    System.out.println("บันทึกแล้ว");
                }
                else{
                    if (checkManagePrawnDate(inputDate, farmingId)){
                        ManagePrawn managePrawn = new ManagePrawn(manageIDString, manageType, manageNote, inputDate, farmingId);
                        managePrawn.insertToSql();
                        managePrawnList.addManagePrawn(managePrawn);
                        Farming farming = farmingList.getFarmingById(farmingId);
                        farming.setFarmingStatus(statusType);
                        farming.updateToSql();
//                farmingList.addFarming(farming);
                        warningLabel.setText("บันทึกเสร็จสิ้น");
                        System.out.println("บันทึกแล้ว");
                    }
                }


            }
        }catch (Exception e) {
            warningLabel.setText("กรุณากรอกข้อมูลให้ครบถ้วน");
            System.err.println("ใส่ข้อมูลผิดพลาด");
        }
    }

    @FXML
    private void pressGoBack() {
        System.out.println("กลับไปหน้าก่อนหน้า");
    }

    @FXML
    private void pressOnCodeOneButton() {
        this.noteType = "วัดน้ำหนัก";
        showStatusCode(noteType);
        this.codeOneButton.setStyle("-fx-background-color: #FF8C00;");
        this.codeTwoButton.setStyle("-fx-background-color: #FFD700;");
        this.codeThreeButton.setStyle("-fx-background-color: #FFD700;");
    }

    @FXML
    private void pressOnCodeTwoButton() {
        this.noteType = "ให้ยา";
        showStatusCode(noteType);
        this.codeTwoButton.setStyle("-fx-background-color: #FF8C00;");
        this.codeOneButton.setStyle("-fx-background-color: #FFD700;");
        this.codeThreeButton.setStyle("-fx-background-color: #FFD700;");
    }

    @FXML
    private void pressOnCodeThreeButton() {
        this.noteType = "บันทึกปัญหา";
        showStatusCode(noteType);
        this.codeThreeButton.setStyle("-fx-background-color: #FF8C00;");
        this.codeOneButton.setStyle("-fx-background-color: #FFD700;");
        this.codeTwoButton.setStyle("-fx-background-color: #FFD700;");
    }

    @FXML
    private void normalStatusButton() {
        this.statusType = "ปกติ";
        showStatusCode(statusType);
        this.normalStatusButton.setStyle("-fx-background-color: #FF8C00;");
        this.sickStatusButton.setStyle("-fx-background-color: #FFD700;");
        this.problemStatusButton.setStyle("-fx-background-color: #FFD700;");
    }

    @FXML
    private void sickStatusButton() {
        this.statusType = "ป่วย";
        showStatusCode(statusType);
        this.sickStatusButton.setStyle("-fx-background-color: #FF8C00;");
        this.normalStatusButton.setStyle("-fx-background-color: #FFD700;");
        this.problemStatusButton.setStyle("-fx-background-color: #FFD700;");
    }

    @FXML
    private void problemStatusButton() {
        this.statusType = "เกิดปัญหา";
        showStatusCode(statusType);
        this.problemStatusButton.setStyle("-fx-background-color: #FF8C00;");
        this.normalStatusButton.setStyle("-fx-background-color: #FFD700;");
        this.sickStatusButton.setStyle("-fx-background-color: #FFD700;");
    }

    private void showStatusCode(String statusCode) {
        System.out.println("Status Code is " + statusCode);
    }


    @FXML
    public void BackButton(ActionEvent actionEvent){
        try {
            com.github.saacsos.FXRouter.goTo("staffHome");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า home ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }

    }
}