package ku.cs.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

public class ManagerCheckStockController {

    //still dont know

    @FXML
    private ListView<String> farmingListView;
    @FXML
    private Label pondLabel;
    @FXML
    private Label prawnLabel;
    @FXML
    private Label weightLabel;
    @FXML
    private Label amountLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private TextField priceTextField;
    @FXML
    private TextField sellDateTextField;

    @FXML private Label warningLabel;


    @FXML private TableView<Farming> farmingTableView1;

    private ObservableList<Farming> ObservableList;

    private FarmingList farmingList;
    private FarmingList farmingCheckQC;
    private DataSource<FarmingList> farmingListDataSource;
    private ManagePrawnList managePrawnList;
    private DataSource<ManagePrawnList> managePrawnListDataSource;
    private PrawnList prawnList;
    private DataSource<PrawnList> prawnListDataSource;
    private QCList qcList;
    private DataSource<QCList> qcListDataSource;

    private ArrayList<String> getItem;
    private ArrayList<String> passItem;

    @FXML
    public void initialize() {

        getItem = (ArrayList<String>) com.github.saacsos.FXRouter.getData();
        String name  = getItem.get(0);
        String phone  = getItem.get(1);
        String address  = getItem.get(2);

        passItem = new ArrayList<>();

        farmingListDataSource = new FarmingDataSource();
        farmingList = farmingListDataSource.readData();
        managePrawnListDataSource = new ManagePrawnDataSource();
        managePrawnList = managePrawnListDataSource.readData();
        prawnListDataSource = new PrawnDataSource();
        prawnList = prawnListDataSource.readData();
        qcListDataSource = new QCDataSource();
        qcList = qcListDataSource.readData();

        farmingCheckQC = new FarmingList();

        clearSelectedProduct();
        showProductData();
        farmingTableView1.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showSelectedFarming(newValue);
            }
        });
    }

    private void qcCheck(){
        for (Farming farming : farmingList.getManagerFarming()) {
            QC latestQC = qcList.latestQC(farming.getFarmingID());
//            System.out.println(farming.getFarmingID() +" is " + qcList.latestQC(farming.getFarmingID()));
            if (latestQC != null){
                if (latestQC.getManageStatus().equals("ผ่าน")) {
                    farmingCheckQC.addFarming(farming);
                    System.out.println("this is "+farming.getFarmingID());

                }
            }
        }

    }

    private void showProductData() {
        farmingTableView1.getItems().clear();
        farmingTableView1.getColumns().clear();
        qcCheck();
        ObservableList = FXCollections.observableArrayList(farmingCheckQC.getFarmings());
        farmingTableView1.setItems(ObservableList);
        ///แสดงแถวแนวตรง
        ArrayList<StringConfiguration> configs = new ArrayList<>();
        configs.add(new StringConfiguration("title:pondID", "field:pondID"));
        configs.add(new StringConfiguration("title:prawnID", "field:prawnID"));


        for (StringConfiguration conf: configs) {
            TableColumn col = new TableColumn(conf.get("title"));
            col.setCellValueFactory(new PropertyValueFactory<>(conf.get("field")));
            farmingTableView1.getColumns().add(col);
        }
    }

    public Farming selectedFarming(){
        Farming selectedFarmingString = farmingTableView1.getSelectionModel().selectedItemProperty().get();
//        System.out.println(selectedFarmingString);
//        Farming farming = farmingList.getFarmingById(selectedFarmingString);
        return selectedFarmingString;
    }

    public void  showSelectedFarming(Farming farming){
        pondLabel.setText(farming.getPondID());
        String prawn = prawnList.getPrawnById(farming.getPrawnID()).getSpecies();
        prawnLabel.setText(prawn);

        String weight = managePrawnList.latestManagePrawn(farming.getFarmingID()).getNote();
        weightLabel.setText(weight);
        amountLabel.setText(Integer.toString(farming.getPrawnAmount()));
        dateLabel.setText(farming.getGetDate());
    }

    private void clearSelectedProduct() {
        pondLabel.setText("");
        prawnLabel.setText("");
        weightLabel.setText("");
        amountLabel.setText("");
        dateLabel.setText("");
        warningLabel.setText("");
    }

    private boolean checkInput(){

        try{
            int amount = Integer.parseInt(priceTextField.getText());
            if (amount <= 0){
                warningLabel.setText("โปรดใส่ราคาให้ถูกต้อง");
                return false;
            }
            else {
                return true;
            }
        }
        catch (Exception e) {
            warningLabel.setText("โปรดใส่ราคาให้ถูกต้อง");
            return false;
        }
    }

    private boolean checkQcDate(){
        String sellDate = sellDateTextField.getText();
        String farmingID = selectedFarming().getFarmingID();
        if (qcList.checkDateInCheckStock(sellDate, farmingID)){
            return true;
        }
        else {
            warningLabel.setText("ไม่สามารถบันทึกวันที่ก่อนวันqcได้");
            return false;
        }
    }

    @FXML
    private void enterButton(ActionEvent actionEvent){
        if (priceTextField.getText().isEmpty()) {
            warningLabel.setText("กรุณากรอกราคา");
            System.out.println("กรุณากรอกราคา");
        }
        else if(selectedFarming() == null){
            warningLabel.setText("กรุณาเลือกบ่อ");
        }
        else if (sellDateTextField.getText().isEmpty()) {
            warningLabel.setText("กรุณาใส่วันที่");
            System.out.println("กรุณากรอกราคา");
        }
        else if (!farmingList.validateJavaDate(sellDateTextField.getText())){
            warningLabel.setText("ใส่วันที่ไม่ถูกต้อง");
        }
        else {
            if (checkInput()){
                if (checkQcDate()){
                    try {
                        setPassItem("managerCreatePurchaseOrder");
                    } catch (IOException e) {
                        System.err.println(e.toString());
                        System.err.println("ไม่สามารถเข้าหน้า managerCreatePurchaseOrder");
                    }
                }

            }

        }
    }

    private void setPassItem(String location) throws IOException {

        String name  = getItem.get(0);
        String phone  = getItem.get(1);
        String address  = getItem.get(2);
        String purchaseType = getItem.get(3);

        String price = priceTextField.getText();
        String farmingID = selectedFarming().getFarmingID();

        String sellDate = sellDateTextField.getText();

        passItem.add(name);
        passItem.add(phone);
        passItem.add(address);
        passItem.add(purchaseType);
        passItem.add(farmingID);
        passItem.add(price);
        passItem.add(sellDate);

        com.github.saacsos.FXRouter.goTo(location,passItem);
    }

    @FXML
    public void backBtn(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("managerSellOrder");
        } catch (IOException ex) {
            System.err.println(ex.toString());
            System.err.println("ไม่สามารถเข้าหน้า managerHome");
        }
    }
}
