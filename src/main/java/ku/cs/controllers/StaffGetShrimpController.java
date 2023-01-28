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
import ku.cs.models.Prawn;
import ku.cs.models.PrawnList;
import ku.cs.models.VendorOrder;
import ku.cs.models.VendorOrderList;
import ku.cs.services.DataSource;
import ku.cs.services.PrawnDataSource;
import ku.cs.services.StringConfiguration;
import ku.cs.services.VendorOrderDataSource;

import java.io.IOException;
import java.util.ArrayList;

public class StaffGetShrimpController {

    @FXML private ListView<String> addVendorListView;
    @FXML private TableView<VendorOrder> addVendorTableView;
    @FXML private Label idLabel;
    @FXML private Label amountLabel;
    @FXML private Label SellerIdLabel;
    @FXML private Label StatusLabel;
    @FXML private Label ShrimpNameLabel;

    private ObservableList<VendorOrder> ObservableList;
    private VendorOrderList vendorOrderList;
    private PrawnList prawnList;
    private DataSource<VendorOrderList> dataSource;
    private DataSource<PrawnList> prawnListDataSource;

    private ArrayList<String> getItem;
    private ArrayList<String> passItem;

    @FXML
    public void initialize() {

        getItem = (ArrayList<String>) com.github.saacsos.FXRouter.getData();
        String userID  = getItem.get(0);

        dataSource = new VendorOrderDataSource();
        vendorOrderList = dataSource.readData();
        prawnListDataSource = new PrawnDataSource();
        prawnList = prawnListDataSource.readData();
        passItem = new ArrayList<>();


        showProductData();
        clearSelectedProduct();
        addVendorTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showSelectedVendor(newValue);
            }
        });
    }

    private void showProductData() {
        addVendorTableView.getItems().clear();
        addVendorTableView.getColumns().clear();
        ObservableList = FXCollections.observableArrayList(vendorOrderList.getStaffVendorOrder());
        addVendorTableView.setItems(ObservableList);
        ///แสดงแถวแนวตรง
        ArrayList<StringConfiguration> configs = new ArrayList<>();
        configs.add(new StringConfiguration("title:ID", "field:id"));
        configs.add(new StringConfiguration("title:ผู้ขาย", "field:sellerName"));


        for (StringConfiguration conf: configs) {
            TableColumn col = new TableColumn(conf.get("title"));
            col.setCellValueFactory(new PropertyValueFactory<>(conf.get("field")));
            addVendorTableView.getColumns().add(col);
        }
    }


    private void handleSelectedListView() {
        addVendorListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observableValue,
                                        String oldValue, String newValue) {
                        VendorOrder selectedVendorOrder = vendorOrderList.getVendorOrderById(newValue);
                        System.out.println(selectedVendorOrder + " is selected");
                        showSelectedVendor(selectedVendorOrder);
                        selectedVendorOrder();
                    }
                });
    }

    private VendorOrder selectedVendorOrder(){
        VendorOrder selectedVendorOrderString = addVendorTableView.getSelectionModel().selectedItemProperty().get();
//        System.out.println(selectedVendorOrderString);
//        VendorOrder vendorOrder = vendorOrderList.getVendorOrderById(selectedVendorOrderString);
        return selectedVendorOrderString;
    }

    @FXML
    private void handleSetWaitStatus(ActionEvent actionEvent){
        String addStatusString = "รอดำเนินการ";
        selectedVendorOrder().setStatus(addStatusString);
        showSelectedVendor(selectedVendorOrder());
        System.out.println(addStatusString);

    }

    @FXML
    private void handleSetFinishStatus(ActionEvent actionEvent){
        String addStatusString = "เสร็จสิ้น";
        selectedVendorOrder().setStatus(addStatusString);
        showSelectedVendor(selectedVendorOrder());
        System.out.println(addStatusString);

    }

    private void clearSelectedProduct() {
        idLabel.setText("");
        amountLabel.setText("");
        SellerIdLabel.setText("");
        StatusLabel.setText("");
        ShrimpNameLabel.setText("");


    }

    private void showSelectedVendor(VendorOrder vendorOrder) {
        idLabel.setText(vendorOrder.getId());
        amountLabel.setText(String.valueOf(vendorOrder.getAmount()));
        SellerIdLabel.setText(vendorOrder.getSellerName());
        StatusLabel.setText(vendorOrder.getStatus());
        String prawnName = prawnList.getPrawnById(vendorOrder.getOrderType()).getSpecies();
        ShrimpNameLabel.setText(prawnName);
    }

    private void setPassItem(String location) throws IOException {

        String R_ID = selectedVendorOrder().getId();
        String userID  = getItem.get(0);
        passItem.add(R_ID);
        passItem.add(userID);

        com.github.saacsos.FXRouter.goTo(location,passItem);
    }

    @FXML
    public void addShrimpButton(ActionEvent actionEvent) throws IOException {
        setPassItem("staffAddShrimp");
        try {
            com.github.saacsos.FXRouter.goTo("staffAddShrimp");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า staffAddShrimp ไม่ได้");
        }

    }

    @FXML
    public void Button(ActionEvent actionEvent){

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
