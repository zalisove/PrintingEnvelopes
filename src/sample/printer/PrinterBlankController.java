package sample.printer;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import sample.User;

public class PrinterBlankController {

    @FXML
    private Label recipientLabel;

    @FXML
    private Label senderLabel;

    public void make(User user){
        senderLabel.setText(
                "газета \"Наши дни\"\n"+
                        "а/я396\n" +
                        "г.Николаев\n" +
                        "54001/Украина"
        );
        StringBuilder buffer = new StringBuilder();
        if(!user.getName().isEmpty())buffer.append(user.getName()).append("\n");
        if(!user.getStreet().isEmpty())buffer.append(user.getStreet()).append("\n");
        if(!user.getDistrict().isEmpty())buffer.append(user.getDistrict()).append(" р-н\n");
        if(!user.getCity().isEmpty())buffer.append(user.getCity()).append("\n");
        if(!user.getRegion().isEmpty())buffer.append(user.getRegion()).append(" обл.\n");
        if(!user.getZIPCode().isEmpty())buffer.append(user.getZIPCode()).append("   ");
        if(!user.getCountry().isEmpty())buffer.append(user.getCountry());
        recipientLabel.setText(buffer.toString());
    }
}
