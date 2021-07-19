package unsw.loopmania;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import unsw.loopmania.Types.DifficultyType;

public class DifficultyMenuController {

    @FXML
    private RadioButton standardModeButton;

    @FXML
    private RadioButton survivalModeButton;

    @FXML
    private RadioButton beserkerModeButton;

    @FXML
    private Label modeDescription;

    private ToggleGroup group = new ToggleGroup();

    /**
     * Should put in a string text file
     */
    private final String standardModeDescription = "Standard mode has no distinguishing effects.";
    private final String survivalModeDescription = "In survival mode, you can only purchase 1 health potion each time you shop at the Hero's Castle.";
    private final String beserkerModeDescription = "In berserker mode, you cannot purchase more than 1 piece of protective gear (protective gear includes armour, helmets, and shields) each time you shops at the Hero's Castle.";


    private MenuSwitcher backSwitcher;

    private MenuSwitcher confirmSwitcher;

    public DifficultyMenuController(){
    }

    @FXML
    public void initialize() {

        standardModeButton.setToggleGroup(group);
        survivalModeButton.setToggleGroup(group);
        beserkerModeButton.setToggleGroup(group);
        //Default = standard mode
        setToggleAction();
        standardModeButton.setSelected(true);

    }

    public void setBackSwitcher(MenuSwitcher backSwitcher){
        this.backSwitcher = backSwitcher;
    }

    @FXML
    private void switchToMainMenu() {
        backSwitcher.switchMenu();
    }

    public void setConfirmSwitcher(MenuSwitcher confirmSwitcher){
        this.confirmSwitcher = confirmSwitcher;
    }

    @FXML
    private void switchToGame(){
        confirmSwitcher.switchMenu();
    }

    public DifficultyType getSelectedDifficulty(){
        RadioButton selectedButton = ((RadioButton)group.getSelectedToggle());
        switch(selectedButton.getId()){
            case "standardModeButton":
                return DifficultyType.STANDARD;
            case "survivalModeButton":
                return DifficultyType.SURVIVAL;
            case "beserkerModeButton":
                return DifficultyType.BESERKER;
            default:
                return null;
        }
    }

    private void setToggleAction(){
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){

            @Override
            public void changed(ObservableValue<? extends Toggle> arg0, Toggle arg1, Toggle arg2) {
                if(group.getSelectedToggle() != null){
                    RadioButton selectedButton = (RadioButton)group.getSelectedToggle();
                    switch(selectedButton.getId()){
                        case "standardModeButton":
                            modeDescription.setText(standardModeDescription);
                            break;
                        case "survivalModeButton":
                            modeDescription.setText(survivalModeDescription);
                            break;
                        case "beserkerModeButton":
                            modeDescription.setText(beserkerModeDescription);
                            break;
                        default:
                            return;
                    }
                    modeDescription.setWrapText(true);
                    modeDescription.setTextAlignment(TextAlignment.CENTER);
                }               
            }
            
        });
    }
}
