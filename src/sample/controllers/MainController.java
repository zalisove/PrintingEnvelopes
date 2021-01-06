package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import sample.Main;
import sample.User;
import sample.database.DataBase;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class MainController {

    private ObservableList<User> usersData = FXCollections.observableArrayList();
    @FXML
    private TableView<User> table;

    @FXML
    private Label activeUsersLabel;

    @FXML
    private TableColumn<User, String> country;

    @FXML
    private TableColumn<User, String> name;

    @FXML
    private TableColumn<User, Integer> id;

    @FXML
    private TableColumn<User, String> street;

    @FXML
    private TableColumn<User, String> city;

    @FXML
    private TableColumn<User, String> ZIPCode;

    @FXML
    private TableColumn<User, String> region;


    @FXML
    private TextField searchText;

    private Stage primaryStage;

    private DataBase dataBase;

    @FXML
    void initialize() throws SQLException {

        dataBase = DataBase.getInstance();
        usersData.addAll(dataBase.getAllUser());
        activeUsersUpdate();
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        country.setCellValueFactory(new PropertyValueFactory<>("country"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        ZIPCode.setCellValueFactory(new PropertyValueFactory<>("ZIPCode"));
        city.setCellValueFactory(new PropertyValueFactory<>("city"));
        region.setCellValueFactory(new PropertyValueFactory<>("region"));
        street.setCellValueFactory(new PropertyValueFactory<>("street"));
        table.setRowFactory(txt -> {
            TableRow<User> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    User rowData = row.getItem();
                    showUserEditDialog(rowData);
                }
            });
            return row ;
        });
        table.setItems(usersData);
    }

    private void activeUsersUpdate() {
        LocalDate now = LocalDate.now();
        activeUsersLabel.setText("Активних : " + usersData.stream()
                .filter(e -> now.isBefore(e.getSubscriptionFinishDate())
                        && now.isAfter(e.getSubscriptionStartDate())
                        || now.equals(e.getSubscriptionFinishDate())
                        || now.equals(e.getSubscriptionStartDate()))
                .count());
    }

    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }


    public void showUserEditDialog(User user){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/edit_dialog.fxml"));
            Parent page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Редактирование");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setResizable(false);
            dialogStage.setScene(scene);
            EditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setUser(user);
            controller.setTableData(usersData);
            dialogStage.showAndWait();
            activeUsersUpdate();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void showUserAddDialog(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/add_dialog.fxml"));
            Parent page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Добавление");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.setResizable(false);
            AddDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setTableData(usersData);
            dialogStage.showAndWait();
            activeUsersUpdate();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void showPrintDialog(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/print_dialog.fxml"));
            Parent page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Печать");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.setResizable(false);
            PrintDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void getSearchToId(){
        usersData.setAll(dataBase.getSearchToId(searchText.getText()));
    }
    @FXML
    public void getSearchToName(){
        usersData.setAll(dataBase.getSearchToName(searchText.getText()));
    }
    @FXML
    public void getSearchToCity(){
        usersData.setAll(dataBase.getSearchToCity(searchText.getText()));
    }
    @FXML
    public void getSearchToCountry(){
        usersData.setAll(dataBase.getSearchToCountry(searchText.getText()));
    }
    @FXML
    public void getSearchToZIPCode(){
        usersData.setAll(dataBase.getSearchToZIPCode(searchText.getText()));
    }
    @FXML
    public void getSearchToStreet(){
        usersData.setAll(dataBase.getSearchToStreet(searchText.getText()));
    }

}
