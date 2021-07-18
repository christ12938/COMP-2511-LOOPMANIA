package unsw.loopmania;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class PopUpMessageController {

    @FXML
    private Label messageText;

    @FXML
    private Button quitButton;

    private String message;
    private Color color;
    String buttonText;

    private MenuSwitcher quitSwitcher;
    Stage stage;

    public PopUpMessageController(Stage stage, String message, Color color, String buttonText){
        this.message = message;
        this.color = color;
        this.stage = stage;
        this.buttonText = buttonText;
    }

    @FXML
    public void initialize(){
        messageText.setText(message);
        messageText.setTextFill(color);
        messageText.setWrapText(true);
        messageText.setTextAlignment(TextAlignment.CENTER);
        quitButton.setText(buttonText);
    }

    public void setQuitSwitcher(MenuSwitcher quitSwitcher){
        this.quitSwitcher = quitSwitcher;
    }

    @FXML
    public void quitAction(){
        quitSwitcher.switchMenu();
    }

    public Stage getStage(){
        return stage;
    }

}
