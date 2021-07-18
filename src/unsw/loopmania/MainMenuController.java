package unsw.loopmania;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * controller for the main menu.
 * TODO = you could extend this, for example with a settings menu, or a menu to load particular maps.
 */
public class MainMenuController {
    /**
     * facilitates switching to main game
     */
    private MenuSwitcher gameSwitcher;

    private MenuSwitcher quitSwitcher;

    @FXML
    private Button startGameButton;

    public void setGameSwitcher(MenuSwitcher gameSwitcher){
        this.gameSwitcher = gameSwitcher;
    }

    public void setQuitSwitcher(MenuSwitcher quitSwitcher){
        this.quitSwitcher = quitSwitcher;
    }

    /**
     * facilitates switching to main game upon button click
     * @throws IOException
     */
    @FXML
    private void switchToGame() throws IOException {
        gameSwitcher.switchMenu();
    }

    @FXML
    private void quitGame() throws IOException {
        quitSwitcher.switchMenu();
    }

    public void setButtonToResume(){
        startGameButton.setText("Resume");
    }
}
