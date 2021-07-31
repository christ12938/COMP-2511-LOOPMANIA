package unsw.loopmania;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class HelpMenuController {

    private Stage helpStage;
    private LoopManiaWorldController parent;

    public HelpMenuController(Stage helpStage, LoopManiaWorldController parent){
        this.helpStage = helpStage;
        this.parent = parent;
    }

    @FXML
    private void backButtonAction(){
        helpStage.close();
        parent.startTimer();
    }

}
