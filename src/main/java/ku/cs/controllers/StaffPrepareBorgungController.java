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

public class StaffPrepareBorgungController {

    @FXML private TableView<PreparePond> preparePondTableView;
    @FXML private ListView<String> preparePondListView;
    @FXML private Label pondIdLabel;
    @FXML private Label pondStatusLabel;
    @FXML private Label preparePondStatusLabel;

    private ObservableList<PreparePond> ObservableList;
    private PondList pondList;
    private Pond pond;
    private DataSource<PondList> pondListDataSource;
    private PreparePondList preparePondList;
    private DataSource<PreparePondList> preparePondListDataSource;
    private PreparePond preparePond;

    private ArrayList<String> getItem;

    @FXML
    public void initialize() {
        getItem = (ArrayList<String>) com.github.saacsos.FXRouter.getData();
        String userID  = getItem.get(0);
        pondListDataSource = new PondDataSource();
        pondList = pondListDataSource.readData();
        preparePondListDataSource = new PreparePondDataSource();
        preparePondList = preparePondListDataSource.readData();

        showProductData();
        clearSelectedProduct();
        preparePondTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showSelectedPond(newValue);
                selectedPreparePond();
            }
        });
    }

    @FXML
    public void enterInProgressButton(ActionEvent actionEvent){
        try {
            pond.setStatus("กำลังดำเนินการ");
            pond.updateToSql();
            preparePond.setStatus("กำลังดำเนินการ");
            preparePond.updateToSql();
            showPond();
            System.out.println(pond.getStatus());
        }catch (Exception e) {
            System.err.println("ใส่ข้อมูลผิดพลาด");
        }
    }

    @FXML
    public void enterFinishProgressButton(ActionEvent actionEvent){
        try {
            pond.setStatus("เตรียมบ่อเสร็จสิ้น");
            pond.updateToSql();
            preparePond.setStatus("ดำเนินการเสร็จสิ้น");
            preparePond.updateToSql();
            showPond();
            System.out.println(pond.getStatus());
        }catch (Exception e) {
            System.err.println("ใส่ข้อมูลผิดพลาด");
        }
    }

    private void showProductData() {
        preparePondTableView.getItems().clear();
        preparePondTableView.getColumns().clear();
        String userID  = getItem.get(0);
        ObservableList = FXCollections.observableArrayList(preparePondList.getStaffPreparePond(userID));
        preparePondTableView.setItems(ObservableList);
        ///แสดงแถวแนวตรง
        ArrayList<StringConfiguration> configs = new ArrayList<>();
        configs.add(new StringConfiguration("title:pondID", "field:pondID"));
        configs.add(new StringConfiguration("title:status", "field:status"));


        for (StringConfiguration conf: configs) {
            TableColumn col = new TableColumn(conf.get("title"));
            col.setCellValueFactory(new PropertyValueFactory<>(conf.get("field")));
            preparePondTableView.getColumns().add(col);
        }
    }

    private void selectedPreparePond(){
        PreparePond selectedPreparePondString = preparePondTableView.getSelectionModel().selectedItemProperty().get();
        String preparePondString = selectedPreparePondString.getPrepareID();
        String pondString = selectedPreparePondString.getPondID();
        System.out.println(pondString);
        pond = pondList.getPondById(pondString);
        preparePond = preparePondList.getPreparePondById(preparePondString);
    }

    private void showSelectedPond(PreparePond preparePond) {
        pondIdLabel.setText(preparePond.getPondID());
        String pondStatus = pondList.getPondById(preparePond.getPondID()).getStatus();
        pondStatusLabel.setText(pondStatus);
        preparePondStatusLabel.setText(preparePond.getNote());
    }

    private void showPond(){
        pondStatusLabel.setText(pond.getStatus());
    }

    private void clearSelectedProduct() {
        pondIdLabel.setText("");
        pondStatusLabel.setText("");
        preparePondStatusLabel.setText("");
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
