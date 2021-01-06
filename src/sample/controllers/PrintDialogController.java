package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.print.*;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.User;
import sample.database.DataBase;
import sample.printer.Printing;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PrintDialogController {


    @FXML
    private ChoiceBox<String> choseBox;

    @FXML
    private ChoiceBox<Printer> choseBoxPrinter;

    @FXML
    private TextField toField;

    @FXML
    private TextField fromField;

    private Stage dialogStage;
    private DataBase dataBase;
    private LocalDate now;
    @FXML
    void initialize() {
        ObservableList<String> chose = FXCollections.observableArrayList("Украина", "За рубеж");
        choseBoxPrinter.setItems(FXCollections.observableArrayList(new ArrayList<>(Printer.getAllPrinters())));
        choseBox.setItems(chose);
        choseBox.setValue("Украина");
        choseBoxPrinter.setValue(Printer.getDefaultPrinter());
        now = LocalDate.now();
        try {
            dataBase = DataBase.getInstance();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    private void printSomething(){
        String to = toField.getText();
        if(to.equals("MAX"))to = String.valueOf(dataBase.getMaxId());
        List<User> filterUser =dataBase.getAllCondition("printFormat = '"
                + choseBox.getValue() +
                "' AND id >= " + fromField.getText() +
                " AND id <= " + to)
                .stream()
                .filter(user -> now.isBefore(user.getSubscriptionFinishDate()) && now.isAfter(user.getSubscriptionStartDate())
                        || now.equals(user.getSubscriptionFinishDate())|| now.equals(user.getSubscriptionStartDate()))
                .collect(Collectors.toList());
        Printing.printMore(filterUser,choseBoxPrinter.getValue());
        StringBuilder ms = new StringBuilder();
        for (User u:filterUser) {
            ms.append(u).append("\n");
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Люди которих розпичатали");
        alert.setHeaderText("Люди которих розпичатали");
        VBox dialogPaneContent = new VBox();
        TextArea textArea = new TextArea();
        textArea.setText(ms.toString());
        dialogPaneContent.getChildren().add(textArea);
        alert.getDialogPane().setContent(dialogPaneContent);
        alert.showAndWait();
        cancel();
    }
    @FXML
    private void cancel(){
        dialogStage.close();
    }
    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
    }
}
