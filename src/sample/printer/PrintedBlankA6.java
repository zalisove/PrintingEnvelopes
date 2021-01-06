package sample.printer;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import sample.User;

import java.io.IOException;

public class PrintedBlankA6 extends Pane {

    public PrintedBlankA6(User user) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("blank_A6.fxml"));
        Parent parent = loader.load();
        PrinterBlankController controller = loader.getController();
        controller.make(user);

        this.getChildren().add(parent);
    }
}
