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
        ///???????????????????????????????????????
        ArrayList<StringConfiguration> configs = new ArrayList<>();
        configs.add(new StringConfiguration("title:pondID", "field:pondID"));
//        configs.add(new StringConfiguration("title:?????????????????????", "field:pondID"));


        for (StringConfiguration conf: configs) {
            TableColumn col = new TableColumn(conf.get("title"));
            col.setCellValueFactory(new PropertyValueFactory<>(conf.get("field")));
            farmingTableView.getColumns().add(col);
        }
    }
    public void  showSelectedFarming(Farming farming){
        farmingLabel.setText(farming.getPondID());
        if (farming.getFarmingStatus().equals("????????????")){
            statusType = "????????????";
            this.normalStatusButton.setStyle("-fx-background-color: #FF8C00;");
            this.sickStatusButton.setStyle("-fx-background-color: #FFD700;");
            this.problemStatusButton.setStyle("-fx-background-color: #FFD700;");
        }else if(farming.getFarmingStatus().equals("????????????")){
            statusType = "????????????";
            this.sickStatusButton.setStyle("-fx-background-color: #FF8C00;");
            this.normalStatusButton.setStyle("-fx-background-color: #FFD700;");
            this.problemStatusButton.setStyle("-fx-background-color: #FFD700;");
        }else{
            statusType = "???????????????????????????";
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
                warningLabel.setText("???????????????????????????????????????????????????????????????????????????????????????????????????");
                return false;
            }
        }
        else {
            warningLabel.setText("?????????????????????????????????????????????????????????????????? yyyy-mm-dd");
            System.out.println("???????????????????????????");
            return false;
        }
    }

    private boolean checkManagePrawnDate(String inputDate, String farmingId){
        if (farmingList.validateJavaDate(inputDate)){
            if (managePrawnList.checkDateInManagePrawn(inputDate, farmingId)){
                return true;
            }
            else {
                warningLabel.setText("??????????????????????????????????????????????????????????????????????????????????????????");
                return false;
            }
        }
        else {
            warningLabel.setText("?????????????????????????????????????????????????????????????????? yyyy-mm-dd");
            System.out.println("???????????????????????????");
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
                    warningLabel.setText("?????????????????????????????????????????????");
                    System.out.println("??????????????????????????????");
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
                        warningLabel.setText("?????????????????????????????????????????????");
                        System.out.println("??????????????????????????????");
                    }
                }


            }
        }catch (Exception e) {
            warningLabel.setText("???????????????????????????????????????????????????????????????????????????");
            System.err.println("????????????????????????????????????????????????");
        }
    }

    @FXML
    private void pressGoBack() {
        System.out.println("??????????????????????????????????????????????????????");
    }

    @FXML
    private void pressOnCodeOneButton() {
        this.noteType = "??????????????????????????????";
        showStatusCode(noteType);
        this.codeOneButton.setStyle("-fx-background-color: #FF8C00;");
        this.codeTwoButton.setStyle("-fx-background-color: #FFD700;");
        this.codeThreeButton.setStyle("-fx-background-color: #FFD700;");
    }

    @FXML
    private void pressOnCodeTwoButton() {
        this.noteType = "???????????????";
        showStatusCode(noteType);
        this.codeTwoButton.setStyle("-fx-background-color: #FF8C00;");
        this.codeOneButton.setStyle("-fx-background-color: #FFD700;");
        this.codeThreeButton.setStyle("-fx-background-color: #FFD700;");
    }

    @FXML
    private void pressOnCodeThreeButton() {
        this.noteType = "?????????????????????????????????";
        showStatusCode(noteType);
        this.codeThreeButton.setStyle("-fx-background-color: #FF8C00;");
        this.codeOneButton.setStyle("-fx-background-color: #FFD700;");
        this.codeTwoButton.setStyle("-fx-background-color: #FFD700;");
    }

    @FXML
    private void normalStatusButton() {
        this.statusType = "????????????";
        showStatusCode(statusType);
        this.normalStatusButton.setStyle("-fx-background-color: #FF8C00;");
        this.sickStatusButton.setStyle("-fx-background-color: #FFD700;");
        this.problemStatusButton.setStyle("-fx-background-color: #FFD700;");
    }

    @FXML
    private void sickStatusButton() {
        this.statusType = "????????????";
        showStatusCode(statusType);
        this.sickStatusButton.setStyle("-fx-background-color: #FF8C00;");
        this.normalStatusButton.setStyle("-fx-background-color: #FFD700;");
        this.problemStatusButton.setStyle("-fx-background-color: #FFD700;");
    }

    @FXML
    private void problemStatusButton() {
        this.statusType = "???????????????????????????";
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
            System.err.println("??????????????????????????? home ??????????????????");
            System.err.println("?????????????????????????????????????????????????????? route");
        }

    }
}