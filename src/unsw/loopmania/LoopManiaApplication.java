package unsw.loopmania;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * the main application
 * run main method from this class
 */
public class LoopManiaApplication extends Application {
    // TODO = possibly add other menus?

    /**
     * the controller for the game. Stored as a field so can terminate it when click exit button
     */
    private LoopManiaWorldController mainController;
    
    private boolean hasStarted = false;

    @Override
    public void start(Stage primaryStage) throws IOException {
        // set title on top of window bar
        primaryStage.setTitle("Loop Mania");

        // prevent human player resizing game window (since otherwise would see white space)
        // alternatively, you could allow rescaling of the game (you'd have to program resizing of the JavaFX nodes)
        primaryStage.setResizable(false);

        // load the main game
        LoopManiaWorldControllerLoader loopManiaLoader = new LoopManiaWorldControllerLoader("world_with_twists_and_turns.json");
        mainController = loopManiaLoader.loadController();
        mainController.setStage(primaryStage);
        FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("LoopManiaView.fxml"));
        gameLoader.setController(mainController);
        Parent gameRoot = gameLoader.load();

        // load the main menu
        MainMenuController mainMenuController = new MainMenuController();
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("MainMenuView.fxml"));
        menuLoader.setController(mainMenuController);
        Parent mainMenuRoot = menuLoader.load();

        // load the difficulty menu
        DifficultyMenuController difficultyMenuController = new DifficultyMenuController();
        FXMLLoader difficultyMenuLoader = new FXMLLoader(getClass().getResource("DifficultyMenuView.fxml"));
        difficultyMenuLoader.setController(difficultyMenuController);
        Parent difficultyMenuRoot = difficultyMenuLoader.load();

        // create new scene with the main menu (so we start with the main menu)
        Scene scene = new Scene(mainMenuRoot);
        
        // set functions which are activated when button click to switch menu is pressed
        // e.g. from main menu to start the game, or from the game to return to main menu
        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
        });
        mainController.setMainMenuSwitcher(() -> {
            mainMenuController.setButtonToResume();
            switchToRoot(scene, mainMenuRoot, primaryStage);
        });
        mainMenuController.setGameSwitcher(() -> {
            if(hasStarted){
                switchToRoot(scene, gameRoot, primaryStage);
                mainController.startTimer();
            }else{
                switchToRoot(scene, difficultyMenuRoot, primaryStage);
            }
        });
        mainMenuController.setQuitSwitcher(() ->{
            primaryStage.close();
        });
        difficultyMenuController.setBackSwitcher(()->{
            switchToRoot(scene, mainMenuRoot, primaryStage);
        });
        difficultyMenuController.setConfirmSwitcher(()->{
            switchToRoot(scene, gameRoot, primaryStage);
            mainController.setDifficulty(difficultyMenuController.getSelectedDifficulty());
            mainController.startTimer();
            hasStarted = true;
        });
        


        // deploy the main onto the stage
        gameRoot.requestFocus();
        primaryStage.setScene(scene);
        primaryStage.show();

        //Hide and show again to make it center
        primaryStage.hide();
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((screenBounds.getWidth() - primaryStage.getWidth()) / 2); 
        primaryStage.setY((screenBounds.getHeight() - primaryStage.getHeight()) / 2);
        primaryStage.show();

    }

    @Override
    public void stop(){
        // wrap up activities when exit program
        if(mainController.isTimelineRunning()) mainController.terminate();
    }

    /**
     * switch to a different Root
     */
    private void switchToRoot(Scene scene, Parent root, Stage stage){
        /**
         * First get the values from previous stage
         */
        double xPos = stage.getX();
        double yPos = stage.getY();
        double stageWidth = stage.getWidth();
        double stageHeight = stage.getHeight();

        scene.setRoot(root);
        root.requestFocus();
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();

        /**
         * Hide and show to center window
         */
        stage.hide();
        double centerXPosition = xPos + stageWidth/2d;
        double centerYPosition = yPos + stageHeight/2d;
        stage.setX(centerXPosition - stage.getWidth()/2d);
        stage.setY(centerYPosition - stage.getHeight()/2d);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
