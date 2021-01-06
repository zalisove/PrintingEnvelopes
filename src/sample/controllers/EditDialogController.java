package sample.controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.print.Printer;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.User;
import javafx.fxml.FXML;
import sample.database.DataBase;
import sample.printer.Printing;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class EditDialogController {

    @FXML
    private ChoiceBox<Printer> printerChoiceBox;

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

    private User user;
    private Stage dialogStage;
    private DataBase dataBase;
    private ObservableList<User> tableData;
    @FXML
    void initialize() {
        ObservableList<String> chose = FXCollections.observableArrayList("Украина", "За рубеж");
        printerChoiceBox.setItems(FXCollections.observableArrayList(new ArrayList<>(Printer.getAllPrinters())));
        printerChoiceBox.setValue(Printer.getDefaultPrinter());
        try {
            dataBase = DataBase.getInstance();
        } catch (SQLException throwables) {
            dialogStage.close();
            throwables.printStackTrace();
        }
        printFormatChoice.setItems(chose);
        printFormatChoice.getSelectionModel().selectedIndexProperty()
                .addListener((observableValue, number, number2) -> {
            if(printFormatChoice.getItems().get((Integer) number2).equals("Украина"))
            countryField.setText(printFormatChoice.getItems().get((Integer) number2));
        });
    }

    public void setTableData(ObservableList<User> tableData){
        this.tableData = tableData;
    }

    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;

    }
    public void setUser(User user){
        this.user = user;
        idLabel.setText("Запись №" + user.getId());
        nameField.setText(user.getName());
        countryField.setText(user.getCountry());
        streetField.setText(user.getStreet());
        phoneField.setText(user.getPhone());
        cityField.setText(user.getCity());
        regionField.setText(user.getRegion());
        districtField.setText(user.getDistrict());
        ZIPCodeField.setText(user.getZIPCode());
        printFormatChoice.setValue(user.getPrintFormat());
        payerArea.setText(user.getPayer());
        leafCountField.setText(String.valueOf(user.getLeafCount()));
        subscriptionStartDatePicker.setValue(user.getSubscriptionStartDate());
        subscriptionFinishDatePicker.setValue(user.getSubscriptionFinishDate());
    }
    @FXML
    private void save(){
        if (check(leafCountField, subscriptionFinishDatePicker, subscriptionStartDatePicker))
            return;
        user.setName(nameField.getText());
        user.setCountry(countryField.getText());
        user.setStreet(streetField.getText());
        user.setPhone(phoneField.getText());
        user.setCity(cityField.getText());
        user.setRegion(regionField.getText());
        user.setDistrict(districtField.getText());
        user.setZIPCode(ZIPCodeField.getText());
        user.setPrintFormat(printFormatChoice.getValue());
        user.setPayer(payerArea.getText());
        user.setLeafCount(Integer.parseInt(leafCountField.getText()));
        user.setSubscriptionStartDate(subscriptionStartDatePicker.getValue());
        user.setSubscriptionFinishDate(subscriptionFinishDatePicker.getValue());

        tableData.set(tableData.indexOf(user),user);
        dataBase.updateUser(user);
        dialogStage.close();
    }
    @FXML
    private void remove(){
        dataBase.removeUser(user);
        tableData.remove(user);
        dialogStage.close();
    }
    @FXML
    private void close(){
        dialogStage.close();
    }
    @FXML
    private void print(){

        if (check(leafCountField, subscriptionFinishDatePicker, subscriptionStartDatePicker)) return;

        LocalDate now = LocalDate.now();
        if(
                now.isBefore(subscriptionFinishDatePicker.getValue())
                && now.isAfter(subscriptionStartDatePicker.getValue())
                || now.equals(subscriptionFinishDatePicker.getValue())
                || now.equals(subscriptionStartDatePicker.getValue())
        ) {
            Printing.printOne(user, printerChoiceBox.getValue());

            user.setName(nameField.getText());
            user.setCountry(countryField.getText());
            user.setStreet(streetField.getText());
            user.setPhone(phoneField.getText());
            user.setCity(cityField.getText());
            user.setRegion(regionField.getText());
            user.setDistrict(districtField.getText());
            user.setZIPCode(ZIPCodeField.getText());
            user.setPrintFormat(printFormatChoice.getValue());
            user.setPayer(payerArea.getText());
            user.setLeafCount(Integer.parseInt(leafCountField.getText()));
            user.setSubscriptionStartDate(subscriptionStartDatePicker.getValue());
            user.setSubscriptionFinishDate(subscriptionFinishDatePicker.getValue());

            tableData.set(tableData.indexOf(user),user);
            dataBase.updateUser(user);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ошибка");
            alert.setContentText("Срок подписки вышел");
            alert.showAndWait();
            return;
        }
        close();
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
