package sample.controllers;

import java.sql.SQLException;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.User;
import sample.database.DataBase;

public class AddDialogController {

    @FXML
    private TextField leafCountField;

    @FXML
    private TextField cityField;

    @FXML
    private TextField districtField;

    @FXML
    private DatePicker subscriptionFinishDatePicker;

    @FXML
    private TextField nameField;

    @FXML
    private TextField countryField;

    @FXML
    private ChoiceBox<String> printFormatChoice;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField regionField;

    @FXML
    private TextField streetField;

    @FXML
    private TextArea payerArea;

    @FXML
    private TextField ZIPCodeField;

    @FXML
    private Label idLabel;

    @FXML
    private DatePicker subscriptionStartDatePicker;
    private int id;
    private Stage dialogStage;
    private DataBase dataBase;
    private ObservableList<User> tableData;


    @FXML
    void initialize() {
        ObservableList<String> chose = FXCollections.observableArrayList("Украина", "За рубеж");
        try {
            dataBase = DataBase.getInstance();
            id = dataBase.getMaxId();
        } catch (SQLException throwables) {
            dialogStage.close();
            throwables.printStackTrace();
        }
        printFormatChoice.setItems(chose);
        leafCountField.setText("1");
        subscriptionFinishDatePicker.setValue(LocalDate.now());
        subscriptionStartDatePicker.setValue(LocalDate.now());
        printFormatChoice.getSelectionModel().selectedIndexProperty()
                .addListener((observableValue, number, number2) -> {
                    if(printFormatChoice.getItems().get((Integer) number2).equals("Украина"))
                        countryField.setText(printFormatChoice.getItems().get((Integer) number2));
                });
        idLabel.setText("Запись № "+(id+1));
    }
    @FXML
    void close() {
        dialogStage.close();
    }

    public void setTableData(ObservableList<User> tableData){
        this.tableData = tableData;
    }

    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
    }
    @FXML
    private void add(){
        if (check(leafCountField, subscriptionFinishDatePicker, subscriptionStartDatePicker)) return;
        User user = new User(
                id+1,
                nameField.getText(),
                countryField.getText(),
                streetField.getText(),
                phoneField.getText(),
                cityField.getText(),
                regionField.getText(),
                districtField.getText(),
                ZIPCodeField.getText(),
                printFormatChoice.getValue(),
                Integer.parseInt(leafCountField.getText()),
                payerArea.getText(),
                subscriptionStartDatePicker.getValue(),
                subscriptionFinishDatePicker.getValue()
        );

        tableData.add(user);
        dataBase.addUser(user);
        dialogStage.close();
    }

    boolean check(TextField leafCountField, DatePicker subscriptionFinishDatePicker, DatePicker subscriptionStartDatePicker) {
        if(leafCountField.getText().trim().isEmpty()) leafCountField.setText("1");
        if(subscriptionFinishDatePicker.getValue() == null) subscriptionFinishDatePicker.setValue(LocalDate.now());
        if(subscriptionStartDatePicker.getValue() == null) subscriptionStartDatePicker.setValue(LocalDate.now());
        if(subscriptionFinishDatePicker.getValue().isBefore(subscriptionStartDatePicker.getValue())){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ошибка");
            alert.setContentText("Дата начала больше даты окончания");
            alert.showAndWait();
            return true;
        }
        return false;
    }
}



