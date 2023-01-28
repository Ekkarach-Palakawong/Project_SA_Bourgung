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

public class StaffAddShrimpController {

    @FXML private ListView<String> addPondListView;
    @FXML private TableView<Pond> pondTableView;
    @FXML private Label pondIdLabel;
    @FXML private Label pondStatusLabel;
    @FXML private Label shrimpQuantityLabel;
    @FXML private Label shrimpIdLabel;
    @FXML private Label vendorOrderLabel;
    @FXML private Label warningLabel;
    @FXML private TextField dateTextField;
    @FXML private TextField roundTextField;

    private ObservableList<Pond> pondObservableList;
    private PondList pondList;
    private VendorOrder vendorOrder;
    private VendorOrderList vendorOrderList;
    private DataSource<VendorOrderList> vendorOrderListDataSource;
    private DataSource<PondList> dataSource;
    private DataSource<PrawnList> prawnListDataSource;
    private PrawnList prawnList;
    private DataSource<FarmingList> farmingListDataSource;
    private FarmingList farmingList;
    private Farming farming;

    private ArrayList<String> getItem;

    @FXML
    public void initialize() {

        getItem = (ArrayList<String>) com.github.saacsos.FXRouter.getData();
        String R_ID  = getItem.get(0);
        String userID = getItem.get(1);

        dataSource = new PondDataSource();
        pondList = dataSource.readData();
        vendorOrderListDataSource = new VendorOrderDataSource();
        vendorOrderList = vendorOrderListDataSource.readData();
        vendorOrder = vendorOrderList.getVendorOrderById(R_ID);
        farmingListDataSource = new FarmingDataSource();
        farmingList = farmingListDataSource.readData();

        prawnListDataSource = new PrawnDataSource();
        prawnList = prawnListDataSource.readData();
        Prawn prawn =prawnList.getPrawnById("1");
        clearSelectedProduct();
        showProductData();
        showVendorOrder();
        pondTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showSelectedPond(newValue);
            }
        });
    }

    private boolean handleAddFarming(String roundString, String date){
        int round = Integer.parseInt(roundString);
        if (farmingList.addFarmingInputCheck(roundString)){
            if (farmingList.validateJavaDate(date)){
                return true;
            }
            else {
                warningLabel.setText("โปรดใส่วันที่รูปแบบนี้ yyyy-mm-dd");
                System.out.println("วันที่ผิด");
                return false;
            }
        }
        else {
            warningLabel.setText("กรอกรอบไม่ถูกต้อง");
            System.out.println("รอบผิด");
            return false;
        }
    }

    private boolean handleAddRound(int round){
        if (round > 4 || round < 1){
            warningLabel.setText("โปรดใส่รอบในช่วง1ถึง4");
            return false;
        }
        else {
            return true;
        }
    }

    public boolean isStringInt(String round)
    {
        try
        {
            Integer.parseInt(round);
            return true;
        } catch (NumberFormatException ex)
        {
            warningLabel.setText("โปรดใส่จำนวนเป็นเลขจำนวนเต็ม");
            return false;
        }
    }

    private void showProductData() {
        pondTableView.getItems().clear();
        pondTableView.getColumns().clear();
        pondObservableList = FXCollections.observableArrayList(pondList.getStaffPond());
        pondTableView.setItems(pondObservableList);
        ///แสดงแถวแนวตรง
        ArrayList<StringConfiguration> configs = new ArrayList<>();
        configs.add(new StringConfiguration("title:ID", "field:id"));
        configs.add(new StringConfiguration("title:สถานะบ่อ", "field:status"));


        for (StringConfiguration conf: configs) {
            TableColumn col = new TableColumn(conf.get("title"));
            col.setCellValueFactory(new PropertyValueFactory<>(conf.get("field")));
            pondTableView.getColumns().add(col);
        }
    }

    @FXML
    public void enterButton(ActionEvent actionEvent){
        try {
            int farmingID = farmingList.count()+1;
            String farmingIDString = "F"+ farmingID;
            String pondID = selectedPond().getId();
            String roundString = roundTextField.getText();
            int round = Integer.parseInt(roundString);
            int amount = vendorOrder.getAmount();
            System.out.println(roundString);
            String prawnId = vendorOrder.getOrderType();
            String dateString = dateTextField.getText();
            String vendorId = vendorOrder.getId();

            if (handleAddFarming(roundString,dateString)){
                if (handleAddRound(round)){
                    farming = new Farming(farmingIDString, pondID, round, amount, prawnId, dateString,null, "ปกติ", vendorId);
                    handleAddFarming(roundString,dateString);
                    farming.insertToSql();
                    vendorOrder.setStatus("ดำเนินการเสร็จสิ้น");
                    String userID = getItem.get(1);
                    vendorOrder.setEmployeeID(userID);
                    System.out.println("vendor status change");
                    warningLabel.setText("ดำเนินการเสร็จสิ้น");
                    vendorOrder.updateToSql();

                    Pond pond = pondList.getPondById(pondIdLabel.getText());
                    pond.setStatus("เลี้ยงกุ้ง");
                    pond.updateToSql();
                    staffHome();
                }
            }
        }catch (Exception e) {
//
            System.err.println("ใส่ข้อมูลผิดพลาด");
            warningLabel.setText("ใส่ข้อมูลผิดพลาด");
        }

    }

//    @FXML
//    public void vendorUpdateTest(ActionEvent actionEvent){
//        System.out.println("vendor test");
//        vendorOrder.setStatus("ดำเนินการเสร็จสิ้น");
//        System.out.println(vendorOrder.getId() + vendorOrder.getStatus());
//        vendorOrder.updateToSql();
//    }

    private Pond selectedPond(){
        Pond selectedPondString = pondTableView.getSelectionModel().selectedItemProperty().get();
//        Pond pond = pondList.getPondById(selectedPondString);
        return selectedPondString;
    }

    private void clearSelectedProduct() {
        pondIdLabel.setText("");
        pondStatusLabel.setText("");
//        shrimpQuantityLabel.setText("");
//        shrimpIdLabel.setText("");
//        vendorOrderLabel.setText("");
        warningLabel.setText("");
    }

    private void showSelectedPond(Pond pond) {
        pondIdLabel.setText(pond.getId());
        pondStatusLabel.setText(pond.getStatus());
    }

    private void showVendorOrder(){
        shrimpQuantityLabel.setText(String.valueOf(vendorOrder.getAmount()));
        String prawnName = prawnList.getPrawnById(vendorOrder.getOrderType()).getSpecies();
        shrimpIdLabel.setText(prawnName);
        vendorOrderLabel.setText(vendorOrder.getId());
    }

    @FXML
    public void BackButton(ActionEvent actionEvent){
        try {
            com.github.saacsos.FXRouter.goTo("staffGetShrimp");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า home ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }

    }

    public void staffHome(){
        try {
            com.github.saacsos.FXRouter.goTo("staffHome");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า home ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

}
