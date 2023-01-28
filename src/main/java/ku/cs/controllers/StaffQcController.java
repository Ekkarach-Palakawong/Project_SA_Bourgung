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

public class StaffQcController {
    @FXML private Button passedButton;
    @FXML private Button failedButton;
    @FXML private TextField failedReason;
    @FXML private TextField qcTimeTextField;
    @FXML private Label employeeIdLabel;
    @FXML private Label pondIdLabel;
    @FXML private Label measureWeightLabel;
    @FXML private Label manageStatusLabel;
    @FXML private ListView<String> farmingListView;
    @FXML private Label warningLabel;
    @FXML private TableView<Farming> farmingTableView;

    private ObservableList<Farming> ObservableList;
    private FarmingList farmingList;
    private DataSource<FarmingList> farmingListDataSource;
    private ManagePrawnList managePrawnList;
    private DataSource<ManagePrawnList> managePrawnListDataSource;
    private QCList qcList;
    private DataSource<QCList> qcListDataSource;

    private String qcStatus;
    private ArrayList<String> getItem;

    @FXML
    public void initialize() {
        getItem = (ArrayList<String>) com.github.saacsos.FXRouter.getData();
        String userID  = getItem.get(0);

        farmingListDataSource = new FarmingDataSource();
        farmingList = farmingListDataSource.readData();
        managePrawnListDataSource = new ManagePrawnDataSource();
        managePrawnList = managePrawnListDataSource.readData();
        qcListDataSource = new QCDataSource();
        qcList = qcListDataSource.readData();

        showProductData();
        clearSelectedProduct();
        farmingTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
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
        configs.add(new StringConfiguration("title:รอบ", "field:round"));


        for (StringConfiguration conf: configs) {
            TableColumn col = new TableColumn(conf.get("title"));
            col.setCellValueFactory(new PropertyValueFactory<>(conf.get("field")));
            farmingTableView.getColumns().add(col);
        }
    }


    public Farming selectedFarming(){
        Farming selectedFarmingString = farmingTableView.getSelectionModel().selectedItemProperty().get();
//        System.out.println(selectedFarmingString);
//        Farming farming = farmingList.getFarmingById(selectedFarmingString);
        return selectedFarmingString;
    }

    public void  showSelectedFarming(Farming farming){
        pondIdLabel.setText(farming.getPondID());
        manageStatusLabel.setText(farming.getFarmingStatus());

        ManagePrawn managePrawn = managePrawnList.latestManagePrawn(farming.getFarmingID());
//        System.out.println("this is: " + managePrawn);
        if (managePrawnList.latestManagePrawn(farming.getFarmingID()) == null){
            measureWeightLabel.setText("ไม่มีการวัดน้ำหนัก");
        }else {
            measureWeightLabel.setText(managePrawn.getNote());
        }

    }

    private void clearSelectedProduct() {
        pondIdLabel.setText("");
        manageStatusLabel.setText("");
        measureWeightLabel.setText("");
        warningLabel.setText("");
        qcStatus = "";
    }

    @FXML
    private void clickOnFailedButton() {
        qcStatus = "ไม่ผ่าน";
        this.failedReason.setDisable(false);
        this.failedButton.setStyle("-fx-background-color: #FF8C00;");
        this.passedButton.setStyle("-fx-background-color: #FFD700;");

    }
    @FXML
    private void clickOnPassedButton() {
        qcStatus = "ผ่าน";
        this.failedReason.setDisable(true);
        this.passedButton.setStyle("-fx-background-color: #FF8C00;");
        this.failedButton.setStyle("-fx-background-color: #FFD700;");
        this.failedReason.clear();
    }

    private boolean checkFarmingDate(String inputDate, String selectedFarmingDate){
        if (farmingList.validateJavaDate(inputDate)){
            if (farmingList.checkDateInQc(inputDate, selectedFarmingDate)){
                System.out.println("work");
                return true;
            }
            else {
                warningLabel.setText("ไม่สามารถบันทึกวันที่ก่อนลงบ่อได้");
                System.out.println("not work");
                return false;
            }
        }
        else {
            warningLabel.setText("โปรดใส่วันที่รูปแบบนี้ yyyy-mm-dd");
            System.out.println("วันที่ผิด");
            return false;
        }
    }

    private boolean checkManagePrawnDate(String inputDate, String selectedFarmingDate){
        if (farmingList.validateJavaDate(inputDate)){
            if (managePrawnList.checkDateInQC(inputDate, selectedFarmingDate)){
                System.out.println("work");
                return true;
            }
            else {
                warningLabel.setText("ไม่สามารถบันทึกวันที่ก่อนการดูแลกุ้งปัจจุบันได้");
                System.out.println("not work");
                return false;
            }
        }
        else {
            warningLabel.setText("โปรดใส่วันที่รูปแบบนี้ yyyy-mm-dd");
            System.out.println("วันที่ผิด");
            return false;
        }
    }

    private boolean checkQCDate(String inputDate, String farmingId){
        if (farmingList.validateJavaDate(inputDate)){
            if (qcList.checkDateInQC(inputDate, farmingId)){
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
    private void clickFinishedButton() {

        if (qcStatus == ""){
            warningLabel.setText("กรุณาเลือกสถานะ qc");
            System.out.println("กรุณากรอกสาเหตุที่ไม่ผ่าน1");
        }
        else if (qcStatus == "ไม่ผ่าน") {
            if(qcTimeTextField.getText().isEmpty() || failedReason.getText().isEmpty()){
                warningLabel.setText("กรุณากรอกสาเหตุที่ไม่ผ่าน");
                System.out.println("กรุณากรอกสาเหตุที่ไม่ผ่าน2");
            }else {

                int qcID = qcList.count()+1;
                String qcIDString =  "QC"+ qcID;
                String inputDate = qcTimeTextField.getText();
                String note = failedReason.getText();
                String farmingId = selectedFarming().getFarmingID();
                String userID  = getItem.get(0);
                String selectedFarmingDate = selectedFarming().getGetDate();

                if (checkFarmingDate(inputDate, selectedFarmingDate)){

                    if (managePrawnList.latestManagePrawn(farmingId) == null){
                        if (qcList.latestQC(farmingId) == null) {
                            QC qc = new QC(qcIDString, inputDate, qcStatus, note, userID, farmingId);
                            qc.insertToSql();
                            qcList.addQC(qc);
                            warningLabel.setText("ดำเนินการเสร็จสิ้น !");
                        }
                        else {
                            if (checkQCDate(inputDate, farmingId)){
                                QC qc = new QC(qcIDString, inputDate, qcStatus, note, userID, farmingId);
                                qc.insertToSql();
                                qcList.addQC(qc);
                                warningLabel.setText("ดำเนินการเสร็จสิ้น !");
                            }
                        }
                    }
                    else {
                        if (checkManagePrawnDate(inputDate, farmingId)){
                            if (qcList.latestQC(farmingId) == null) {
                                QC qc = new QC(qcIDString, inputDate, qcStatus, note, userID, farmingId);
                                qc.insertToSql();
                                qcList.addQC(qc);
                                warningLabel.setText("ดำเนินการเสร็จสิ้น !");
                            }
                            else {
                                if (checkQCDate(inputDate, farmingId)){
                                    QC qc = new QC(qcIDString, inputDate, qcStatus, note, userID, farmingId);
                                    qc.insertToSql();
                                    qcList.addQC(qc);
                                    warningLabel.setText("ดำเนินการเสร็จสิ้น !");
                                }
                            }
                        }
                    }


                }
            }
        } else if (qcStatus == "ผ่าน"){
            if (qcTimeTextField.getText().isEmpty()){
                warningLabel.setText("กรุณากรอกข้อมูลให้ครบ");
                System.out.println("กรุณากรอกสาเหตุที่ไม่ผ่าน3");
            }else {
                int qcID = qcList.count()+1;
                String qcIDString =  "QC"+ qcID;
                String inputDate = qcTimeTextField.getText();
                String note = failedReason.getText();
                String farmingId = selectedFarming().getFarmingID();
                String userID  = getItem.get(0);
                String selectedFarmingDate = selectedFarming().getGetDate();

                if (checkFarmingDate(inputDate, selectedFarmingDate)){

                    if (managePrawnList.latestManagePrawn(farmingId) == null){
                        if (qcList.latestQC(farmingId) == null) {
                            QC qc = new QC(qcIDString, inputDate, qcStatus, note, userID, farmingId);
                            qc.insertToSql();
                            qcList.addQC(qc);
                            warningLabel.setText("ดำเนินการเสร็จสิ้น !");
                        }
                        else {
                            if (checkQCDate(inputDate, farmingId)){
                                QC qc = new QC(qcIDString, inputDate, qcStatus, note, userID, farmingId);
                                qc.insertToSql();
                                qcList.addQC(qc);
                                warningLabel.setText("ดำเนินการเสร็จสิ้น !");
                            }
                        }
                    }
                    else {
                        if (checkManagePrawnDate(inputDate, farmingId)){
                            if (qcList.latestQC(farmingId) == null) {
                                QC qc = new QC(qcIDString, inputDate, qcStatus, note, userID, farmingId);
                                qc.insertToSql();
                                qcList.addQC(qc);
                                warningLabel.setText("ดำเนินการเสร็จสิ้น !");
                            }
                            else {
                                if (checkQCDate(inputDate, farmingId)){
                                    QC qc = new QC(qcIDString, inputDate, qcStatus, note, userID, farmingId);
                                    qc.insertToSql();
                                    qcList.addQC(qc);
                                    warningLabel.setText("ดำเนินการเสร็จสิ้น !");
                                }
                            }
                        }
                    }


                }
            }
        }
    }


    @FXML
    private void clickBackButton() {
        System.out.println("กลับไปหน้าก่อนหน้า");
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
