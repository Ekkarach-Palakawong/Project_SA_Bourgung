package ku.cs.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ku.cs.models.*;
import ku.cs.services.*;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.ArrayList;

public class ManagerPrepareBorgungController {

    @FXML
    private ListView<String> addEmployeeListView;
    @FXML
    private ListView<String> addPondListView;
    @FXML
    private Label employeeLabel;
    @FXML
    private Label pondIdLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private Label finishLB;
    @FXML
    private TextArea infoTextArea;


    private ObservableList<Employee> ObservableList;
    private ObservableList<Pond> ObservableList2;
    private PondList pondList;
    private EmployeeList employeeList;
    private DataSource<PondList> dataSource;
    private DataSource<EmployeeList> employeeDataSource;
    private PreparePondList preparePondList;
    private DataSource<PreparePondList> preparePondListDataSource;

    @FXML private TableView<Employee> employeeTableView;
    @FXML private TableView<Pond> pondTableView;

    @FXML
    public void initialize() {

        dataSource = new PondDataSource();
        pondList = dataSource.readData();

        employeeDataSource = new EmployeeDataSource();
        employeeList = employeeDataSource.readData();

        preparePondListDataSource = new PreparePondDataSource();
        preparePondList = preparePondListDataSource.readData();

//        showEmployeeListView();
//        clearSelectedRow();
//        handleSelectedEmployeeListView();

//        showPondListView();
//        handleSelectedPondListView();

        showEmployeeData();
        showPondData();
        clearSelectedRow();

        employeeTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                finishLB.setText("");
                showSelectedEmployee(newValue);
            }
        });
        pondTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                finishLB.setText("");
                showSelectedPond(newValue);
            }
        });

    }

    private void showEmployeeData() {
        employeeTableView.getItems().clear();
        employeeTableView.getColumns().clear();
        ObservableList = FXCollections.observableArrayList(employeeList.getManagerEmployee());
        employeeTableView.setItems(ObservableList);
        ///แสดงแถวแนวตรง
        ArrayList<StringConfiguration> configs = new ArrayList<>();
        configs.add(new StringConfiguration("title:ชื่อพนักงาน", "field:name"));
//        configs.add(new StringConfiguration("title:หมายเลข", "field:pondID"));


        for (StringConfiguration conf: configs) {
            TableColumn col = new TableColumn(conf.get("title"));
            col.setCellValueFactory(new PropertyValueFactory<>(conf.get("field")));
            employeeTableView.getColumns().add(col);
        }
    }
    private void showPondData() {
        pondTableView.getItems().clear();
        pondTableView.getColumns().clear();
//        ArrayList<Pond> pondsWithManageCheck = new ArrayList<>();
//        for (Pond pond : pondList.getManagerPond()){
//            if (){
//            }
//        }
        ObservableList2 = FXCollections.observableArrayList(pondList.getManagerPond());
        pondTableView.setItems(ObservableList2);
        ///แสดงแถวแนวตรง
        ArrayList<StringConfiguration> configs = new ArrayList<>();
        configs.add(new StringConfiguration("title:หมายเลขบ่อ", "field:id"));
//        configs.add(new StringConfiguration("title:หมายเลข", "field:pondID"));


        for (StringConfiguration conf: configs) {
            TableColumn col = new TableColumn(conf.get("title"));
            col.setCellValueFactory(new PropertyValueFactory<>(conf.get("field")));
            pondTableView.getColumns().add(col);
        }
    }

    @FXML
    private void clickFinishedButton() {
        try {
            int preparePondID = preparePondList.count()+1;
            String preparePondIDString = "TB"+preparePondID;
            String status = "รอดำเนินการ";
            String note = infoTextArea.getText();
            String employeeID = selectedEmployee().getId();
            String pondID = selectedPond().getId();

            PreparePond preparePond = new PreparePond(preparePondIDString,status,note,employeeID,pondID );
            preparePond.insertToSql();
            preparePondList.addPreparePond(preparePond);

            selectedPond().setStatus("รอดำเนินการ");
            selectedPond().updateToSql();

            finishLB.setText("อัพเดทข้อมูลสำเร็จ");
        }catch (Exception e){
            finishLB.setText("ใส่ข้อมูลผิดพลาด");
        }


    }

//    private void showEmployeeListView() {
//        ListView<String> listView = new ListView<>();
//
//        ObservableList = FXCollections.observableArrayList();
//        ArrayList<Employee> tempVendorList = new ArrayList<Employee>();
//        for (int i = employeeList.count()-1; i>=0; i--){
//            Employee employee = employeeList.getEmployeeNumber(i);
//            tempVendorList.add(employee);
//            ObservableList.add(employee.getId());
//
//        }
//        addEmployeeListView.setItems(ObservableList);
//    }

//    private void showPondListView() {
//        ListView<String> listView = new ListView<>();
//
//        ObservableList = FXCollections.observableArrayList();
//        ArrayList<Pond> tempVendorList = new ArrayList<Pond>();
//        for (int i = pondList.count()-1; i>=0; i--){
//            Pond pond = pondList.getPondNumber(i);
//            if (pond.getStatus().equals("ขายแล้ว")){
//                tempVendorList.add(pond);
//                ObservableList.add(pond.getId());
//            }
//            else if (pond.getStatus().equals("เกิดปัญหา")){
//                tempVendorList.add(pond);
//                ObservableList.add(pond.getId());
//            }
//        }
//        addPondListView.setItems(ObservableList);
//    }



    private void handleSelectedEmployeeListView() {
        addEmployeeListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observableValue,
                                        String oldValue, String newValue) {
                        Employee selectedEmployee = employeeList.getEmployeeById(newValue);
                        System.out.println(selectedEmployee + " is selected");
                        showSelectedEmployee(selectedEmployee);
                        selectedEmployee();
                    }
                });
    }

    private void handleSelectedPondListView() {
        addPondListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observableValue,
                                        String oldValue, String newValue) {
                        Pond selectedPond = pondList.getPondById(newValue);
                        System.out.println(selectedPond + " is selected");
                        showSelectedPond(selectedPond);
                        selectedPond();
                    }
                });
    }

    private Employee selectedEmployee(){
        Employee selectedEmployeeString = employeeTableView.getSelectionModel().selectedItemProperty().get();
        //System.out.println(selectedVendorOrderString);
//        Employee employee = employeeList.getEmployeeById(selectedEmployeeString);
        return selectedEmployeeString;
    }

    private Pond selectedPond(){
        Pond selectedPondString = pondTableView.getSelectionModel().selectedItemProperty().get();
        //System.out.println(selectedVendorOrderString);
//        Pond pond = pondList.getPondById(selectedPondString);
        return selectedPondString;
    }

    private void clearSelectedRow() {
        employeeLabel.setText("");
        pondIdLabel.setText("");
        finishLB.setText("");
    }

    private void showSelectedEmployee(Employee employee) {
        employeeLabel.setText(employee.getName());

    }

    private void showSelectedPond(Pond pond) {
        pondIdLabel.setText(pond.getId());
//        statusLabel.setText(pond.getStatus()+"");

    }

    @FXML
    public void backBtn(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("managerHome");
        } catch (IOException ex) {
            System.err.println(ex.toString());
            System.err.println("ไม่สามารถเข้าหน้า managerHome");
        }
    }
}
