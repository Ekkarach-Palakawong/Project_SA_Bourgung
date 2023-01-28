package ku.cs.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ku.cs.models.*;
import ku.cs.services.*;

import java.io.IOException;
import java.util.ArrayList;

public class StaffDeliveryController {
    @FXML private ListView<String> purchaseOrderListView;
    @FXML TableView<PurchaseOrder> staffDeliveryTableView;
    @FXML private Label prawnLabel;
    @FXML private Label amountLabel;
    @FXML private Label purchaseTypeLabel;
    @FXML private Label priceLabel;
    @FXML private Label customerLabel;
    @FXML private Label phoneLabel;
    @FXML private Label locationLabel;
    @FXML private Label warningLabel;

    private ObservableList<PurchaseOrder> ObservableList;
    private PurchaseOrderList purchaseOrderList;
    private DataSource<PurchaseOrderList> purchaseOrderListDataSource;
    private CustomerList customerList;
    private DataSource<CustomerList> customerListDataSource;
    private FarmingList farmingList;
    private DataSource<FarmingList> farmingListDataSource;
    private DataSource<PrawnList> prawnDataSource;
    private PrawnList prawnList;

    @FXML
    public void initialize() {
        purchaseOrderListDataSource = new PurchaseOrderDataSource();
        purchaseOrderList = purchaseOrderListDataSource.readData();
        customerListDataSource = new CustomerDataSource();
        customerList = customerListDataSource.readData();
        farmingListDataSource = new FarmingDataSource();
        farmingList = farmingListDataSource.readData();
        prawnDataSource = new PrawnDataSource();
        prawnList = prawnDataSource.readData();

        showProductData();
        clearSelectedProduct();
        staffDeliveryTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showSelectedPurchaseOrder(newValue);
            }
        });
    }
    private void showProductData() {
        staffDeliveryTableView.getItems().clear();
        staffDeliveryTableView.getColumns().clear();
        ObservableList = FXCollections.observableArrayList(purchaseOrderList.getStaffPurchaseOrder());
        staffDeliveryTableView.setItems(ObservableList);
        ///แสดงแถวแนวตรง
        ArrayList<StringConfiguration> configs = new ArrayList<>();
        configs.add(new StringConfiguration("title:ID", "field:id"));
        configs.add(new StringConfiguration("title:รหัสลูกค้า", "field:customerID"));


        for (StringConfiguration conf: configs) {
            TableColumn col = new TableColumn(conf.get("title"));
            col.setCellValueFactory(new PropertyValueFactory<>(conf.get("field")));
            staffDeliveryTableView.getColumns().add(col);
        }
    }



    private PurchaseOrder selectedPurchaseOrder(){
        PurchaseOrder selectedPurchaseOrderString = staffDeliveryTableView.getSelectionModel().selectedItemProperty().get();
//        System.out.println(selectedPurchaseOrderString);
//        PurchaseOrder purchaseOrder = purchaseOrderList.getPurchaseOrderById(selectedPurchaseOrderString);
        return selectedPurchaseOrderString;
    }

    private void showSelectedPurchaseOrder(PurchaseOrder purchaseOrder) {
        Farming farming =farmingList.getFarmingById(purchaseOrder.getFarmingID());
        Prawn prawn = prawnList.getPrawnById(farming.getPrawnID());

        prawnLabel.setText(prawn.getSpecies());
        amountLabel.setText(Integer.toString(farming.getPrawnAmount()));
        purchaseTypeLabel.setText(purchaseOrder.getPurchaseType());
        priceLabel.setText(Integer.toString(purchaseOrder.getPrice()));
        Customer customer = customerList.getCustomerById(purchaseOrder.getCustomerID());
        customerLabel.setText(customer.getName());
        phoneLabel.setText(customer.getPhoneNumber());
        locationLabel.setText(customer.getAddress());
    }

    private void clearSelectedProduct() {
        prawnLabel.setText("");
        amountLabel.setText("");
        purchaseTypeLabel.setText("");
        priceLabel.setText("");
        customerLabel.setText("");
        phoneLabel.setText("");
        locationLabel.setText("");
        warningLabel.setText("");
    }

    @FXML
    private void enterButton(ActionEvent actionEvent){
        try{
            selectedPurchaseOrder().setStatus("ส่งของเสร็จสิิ้น");
            selectedPurchaseOrder().updateToSql();
            warningLabel.setText("ส่งของเสร็จสิิ้น");
            System.out.println("กดติดแล้ว");
        }catch (Exception e) {
            warningLabel.setText("เลือกรายการใบสั่งซื้อ");
            System.err.println("ใส่ข้อมูลผิดพลาด");
        }

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
