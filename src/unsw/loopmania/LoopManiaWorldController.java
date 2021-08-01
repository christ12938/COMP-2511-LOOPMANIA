package unsw.loopmania;

import java.util.ArrayList;
import java.util.List;

import org.codefx.libfx.listener.handle.ListenerHandle;
import org.codefx.libfx.listener.handle.ListenerHandles;
import org.javatuples.Pair;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import unsw.loopmania.Cards.*;
import unsw.loopmania.Enemies.Enemy;
import unsw.loopmania.Items.Item;
import unsw.loopmania.Types.OverlappableEntityType;
import unsw.loopmania.Types.CardType;
import unsw.loopmania.Types.DifficultyType;
import unsw.loopmania.Types.EnemyType;
import unsw.loopmania.Types.ItemType;
import unsw.loopmania.Buildings.*;
import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


import java.util.EnumMap;

import java.io.File;
import java.io.IOException;


/**
 * the draggable types.
 * If you add more draggable types, add an enum value here.
 * This is so we can see what type is being dragged.
 */
enum DRAGGABLE_TYPE{
    CARDS,
    ITEMS
}

/**
 * A JavaFX controller for the world.
 *
 * All event handlers and the timeline in JavaFX run on the JavaFX application thread:
 *     https://examples.javacodegeeks.com/desktop-java/javafx/javafx-concurrency-example/
 *     Note in https://openjfx.io/javadoc/11/javafx.graphics/javafx/application/Application.html under heading "Threading", it specifies animation timelines are run in the application thread.
 * This means that the starter code does not need locks (mutexes) for resources shared between the timeline KeyFrame, and all of the  event handlers (including between different event handlers).
 * This will make the game easier for you to implement. However, if you add time-consuming processes to this, the game may lag or become choppy.
 *
 * If you need to implement time-consuming processes, we recommend:
 *     using Task https://openjfx.io/javadoc/11/javafx.graphics/javafx/concurrent/Task.html by itself or within a Service https://openjfx.io/javadoc/11/javafx.graphics/javafx/concurrent/Service.html
 *
 *     Tasks ensure that any changes to public properties, change notifications for errors or cancellation, event handlers, and states occur on the JavaFX Application thread,
 *         so is a better alternative to using a basic Java Thread: https://docs.oracle.com/javafx/2/threads/jfxpub-threads.htm
 *     The Service class is used for executing/reusing tasks. You can run tasks without Service, however, if you don't need to reuse it.
 *
 * If you implement time-consuming processes in a Task or thread, you may need to implement locks on resources shared with the application thread (i.e. Timeline KeyFrame and drag Event handlers).
 * You can check whether code is running on the JavaFX application thread by running the helper method printThreadingNotes in this class.
 *
 * NOTE: http://tutorials.jenkov.com/javafx/concurrency.html and https://www.developer.com/design/multithreading-in-javafx/#:~:text=JavaFX%20has%20a%20unique%20set,in%20the%20JavaFX%20Application%20Thread.
 *
 * If you need to delay some code but it is not long-running, consider using Platform.runLater https://openjfx.io/javadoc/11/javafx.graphics/javafx/application/Platform.html#runLater(java.lang.Runnable)
 *     This is run on the JavaFX application thread when it has enough time.
 */
public class LoopManiaWorldController {

    /**
     * squares gridpane includes path images, enemies, character, empty grass, buildings
     */
    @FXML
    private GridPane squares;

    /**
     * cards gridpane includes cards and the ground underneath the cards
     */
    @FXML
    private GridPane cards;

    /**
     * anchorPaneRoot is the "background". It is useful since anchorPaneRoot stretches over the entire game world,
     * so we can detect dragging of cards/items over this and accordingly update DragIcon coordinates
     */
    @FXML
    private AnchorPane anchorPaneRoot;

    /**
     * equippedItems gridpane is for equipped items (e.g. swords, shield, axe)
     */
    @FXML
    private GridPane equippedItems;

    @FXML
    private GridPane unequippedInventory;

    /**
     * Imageview of equippable item cells
     */
    @FXML
    private ImageView helmetCell;

    @FXML
    private ImageView weaponCell;

    @FXML
    private ImageView armourCell;

    @FXML
    private ImageView shieldCell;

    @FXML
    private Label difficultyText;

    @FXML
    private Label cycleValue;

    @FXML
    private ProgressBar healthBar;

    @FXML
    private Label goldValue;

    @FXML
    private Label expValue;

    /**
     * Gridpane for allied soldiers
     */
    @FXML
    private GridPane alliedSoldierSlot;

    // all image views including tiles, character, enemies, cards... even though cards in separate gridpane...
    private List<ImageView> entityImages;

    /**
     * when we drag a card/item, the picture for whatever we're dragging is set here and we actually drag this node
     */
    private DragIcon draggedEntity;

    private boolean isPaused;
    private LoopManiaWorld world;

    /**
     * runs the periodic game logic - second-by-second moving of character through maze, as well as enemies, and running of battles
     */
    private Timeline timeline;

    /**
     * Image of the cards
     */
    private Image vampireCastleCardImage;
    private Image zombiePitCardImage;
    private Image towerCardImage;
    private Image villageCardImage;
    private Image barracksCardImage;
    private Image trapCardImage;
    private Image campfireCardImage;

    /**
     * Image of the buildings
     */
    private Image vampireCastleBuildingImage;
    private Image zombiePitBuildingImage;
    private Image towerBuildingImage;
    private Image villageBuildingImage;
    private Image barracksBuildingImage;
    private Image trapBuildingImage;
    private Image campfireBuildingImage;


    /**
     * Image of Items
     */
    private Image swordImage;
    private Image stakeImage;
    private Image staffImage;
    private Image armourImage;
    private Image shieldImage;
    private Image helmetImage;
    private Image goldImage;
    private Image healthPotionImage;
    private Image theOneRingImage;
    private Image andurilImage;
    private Image treeStumpImage;
    private Image doggieCoinImage;


    /**
     * Image of Enemies
     */
    private Image slugImage;
    private Image zombieImage;
    private Image vampireImage;
    private Image doggieImage;
    private Image elanMuskeImage;

    /**
     * Image of allied soldier
     */
    private Image alliedSoldierImage;

    /**
     * the image currently being dragged, if there is one, otherwise null.
     * Holding the ImageView being dragged allows us to spawn it again in the drop location if appropriate.
     */
    private ImageView currentlyDraggedImage;

    /**
     * the image currently being highlighted on the grid
     * added for refining the GUI
     */
    private Node currentlyHighlightedImage;

    /**
     * the target grid pane of the image currently dragged
     * added for refining the GUI
     */
    private GridPane currentlyDraggedTargetGridPane;

    private GridPane currentlyDraggedSourceGridPane;

    /**
     * null if nothing being dragged, or the type of item being dragged
     */
    private DRAGGABLE_TYPE currentlyDraggedType;

    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered when the draggable type is dropped over its appropriate gridpane
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> gridPaneSetOnDragDropped;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered when the draggable type is dragged over the background
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> anchorPaneRootSetOnDragOver;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered when the draggable type is dropped in the background
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> anchorPaneRootSetOnDragDropped;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered when the draggable type is dragged into the boundaries of its appropriate gridpane
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> gridPaneNodeSetOnDragEntered;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered when the draggable type is dragged outside of the boundaries of its appropriate gridpane
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> gridPaneNodeSetOnDragExited;

    /**
     * object handling switching to the main menu
     */
    private MenuSwitcher mainMenuSwitcher;

    /**
     * Stage of application
     */
    private Stage primaryStage;


    private DifficultyType difficulty;

    private volatile boolean isTimelineRunning = false;

    private MediaPlayer backgroundMusicPlayer;
    private MediaPlayer deathMediaPlayer;
    private MediaPlayer shopAudioPlayer;
    private MediaPlayer equippingSwordAudioPlayer;
    private MediaPlayer equippingArmourAudioPlayer;
    private MediaPlayer equippingShieldAudioPlayer;
    private MediaPlayer spawnZombieAudioPlayer;
    private MediaPlayer zombieDeathAudioPlayer;
    private MediaPlayer loadTrapAudioPlayer;
    private MediaPlayer loadVampireSAudioPlayer;
    private MediaPlayer loadZombieSAudioPlayer;
    private MediaPlayer loadViliageAudioPlayer;
    private MediaPlayer loadBarracksAudioPlayer;
    private MediaPlayer loadTowerAudioPlayer;
    private MediaPlayer loadCampfireAudioPlayer;
    private MediaPlayer spawnVampireAudioPlayer;
    private MediaPlayer vampireDeathAudioPlayer;
    private MediaPlayer spawnDoggieAudioPlayer;
    private MediaPlayer doggieDeathAudioPlayer;
    private MediaPlayer spawnElanMuskAudioPlayer;
    private MediaPlayer elanDeathAudioPlayer;
    private MediaPlayer addAlliedSoliderAudioPlayer;

    /**
     * @param world world object loaded from file
     * @param initialEntities the initial JavaFX nodes (ImageViews) which should be loaded into the GUI
     */
    public LoopManiaWorldController(LoopManiaWorld world, List<ImageView> initialEntities) {
        this.world = world;
        this.world.setController(this);
        entityImages = new ArrayList<>(initialEntities);

        difficulty = null;

        /* Initialize all card images */
        vampireCastleCardImage = new Image((new File("src/images/vampire_castle_card.png")).toURI().toString());
        zombiePitCardImage = new Image((new File("src/images/zombie_pit_card.png")).toURI().toString());
        towerCardImage = new Image((new File("src/images/tower_card.png")).toURI().toString());
        villageCardImage = new Image((new File("src/images/village_card.png")).toURI().toString());
        barracksCardImage = new Image((new File("src/images/barracks_card.png")).toURI().toString());
        trapCardImage = new Image((new File("src/images/trap_card.png")).toURI().toString());
        campfireCardImage = new Image((new File("src/images/campfire_card.png")).toURI().toString());

        /* Initialize all building images */
        vampireCastleBuildingImage = new Image((new File("src/images/vampire_castle_building_purple_background.png")).toURI().toString());
        zombiePitBuildingImage = new Image((new File("src/images/zombie_pit.png")).toURI().toString());
        towerBuildingImage = new Image((new File("src/images/tower.png")).toURI().toString());
        villageBuildingImage = new Image((new File("src/images/village.png")).toURI().toString());
        barracksBuildingImage = new Image((new File("src/images/barracks.png")).toURI().toString());
        trapBuildingImage = new Image((new File("src/images/trap.png")).toURI().toString());
        campfireBuildingImage = new Image((new File("src/images/campfire.png")).toURI().toString());

        /* Initialize all enemy images */
        slugImage = new Image((new File("src/images/slug.png")).toURI().toString());
        zombieImage = new Image((new File("src/images/zombie.png")).toURI().toString());
        vampireImage = new Image((new File("src/images/vampire.png")).toURI().toString());
        doggieImage = new Image((new File("src/images/doggie.png")).toURI().toString());
        elanMuskeImage = new Image((new File("src/images/ElanMuske.png")).toURI().toString());

        /* Initialize all item images */
        swordImage = new Image((new File("src/images/basic_sword.png")).toURI().toString());
        stakeImage = new Image((new File("src/images/stake.png")).toURI().toString());
        staffImage = new Image((new File("src/images/staff.png")).toURI().toString());
        armourImage = new Image((new File("src/images/armour.png")).toURI().toString());
        shieldImage = new Image((new File("src/images/shield.png")).toURI().toString());
        helmetImage = new Image((new File("src/images/helmet.png")).toURI().toString());
        goldImage = new Image((new File("src/images/gold_pile.png")).toURI().toString());
        healthPotionImage = new Image((new File("src/images/brilliant_blue_new.png")).toURI().toString());
        doggieCoinImage = new Image((new File("src/images/doggiecoin.png")).toURI().toString());
        theOneRingImage = new Image((new File("src/images/the_one_ring.png")).toURI().toString());
        andurilImage = new Image((new File("src/images/anduril_flame_of_the_west.png")).toURI().toString());
        treeStumpImage = new Image((new File("src/images/tree_stump.png")).toURI().toString());

        /* Initialize allied soldier image */
        alliedSoldierImage = new Image((new File("src/images/deep_elf_master_archer.png")).toURI().toString());

        currentlyDraggedImage = null;
        currentlyHighlightedImage = null;
        currentlyDraggedType = null;
        currentlyDraggedTargetGridPane = null;
        currentlyDraggedSourceGridPane = null;

        // initialize them all...
        gridPaneSetOnDragDropped = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        anchorPaneRootSetOnDragOver = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        anchorPaneRootSetOnDragDropped = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        gridPaneNodeSetOnDragEntered = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        gridPaneNodeSetOnDragExited = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);

        String backgroundMusic = new File("src/Music/song1.mp3").toURI().toString();
        String deathSound = new File("src/Music/death.mp3").toURI().toString();
        String shopAudio = new File("src/Music/ShopBell.mp3").toURI().toString();
        String equippingSwordAudio = new File("src/Music/EquippingSword.mp3").toURI().toString();
        String equippingArmourAudio = new File("src/Music/EquippingArmour.mp3").toURI().toString();
        String equippingShieldAudio = new File("src/Music/EquippingShield.mp3").toURI().toString();
        String spawningZombieAudio = new File("src/Music/ZombieSpawn.mp3").toURI().toString();
        String zombieDeathAudio = new File("src/Music/ZombieDeath.mp3").toURI().toString();
        String loadTrapAudio = new File("src/Music/ArmingTrap.mp3").toURI().toString();
        String loadVampireS = new File("src/Music/place_vampS.mp3").toURI().toString();
        String loadZombieS = new File("src/Music/place_zombieS.mp3").toURI().toString();
        String loadVilliageAudio = new File("src/Music/load_villiage.mp3").toURI().toString();
        String loadBarracksAudio = new File("src/Music/barracks.mp3").toURI().toString();
        String loadCampfireAudio = new File("src/Music/campfire.mp3").toURI().toString();
        String loadTowerAudio = new File("src/Music/tower.mp3").toURI().toString();


        String spawnVampireAudio = new File("src/Music/VampireSpawn.mp3").toURI().toString();
        String vampireDeathAudio = new File("src/Music/VampireDeath.mp3").toURI().toString();
        String doggieDeathAudio = new File("src/Music/DoggieDeath.mp3").toURI().toString();
        String spawnDoggieAudio = new File("src/Music/DoggieSpawn.mp3").toURI().toString();
        String spawnElanMuskAudio = new File("src/Music/MuskSpawn.mp3").toURI().toString();
        String elanMuskDeathAudio = new File("src/Music/MuskDeath.mp3").toURI().toString();
        String addAlliedSoldierAudio = new File("src/Music/AlliedSoldier.mp3").toURI().toString();

        shopAudioPlayer = new MediaPlayer(new Media(shopAudio));
        deathMediaPlayer = new MediaPlayer(new Media(deathSound));
        backgroundMusicPlayer = new MediaPlayer(new Media(backgroundMusic));
        equippingSwordAudioPlayer = new MediaPlayer(new Media(equippingSwordAudio));
        equippingArmourAudioPlayer = new MediaPlayer(new Media(equippingArmourAudio));
        equippingShieldAudioPlayer = new MediaPlayer(new Media(equippingShieldAudio));
        spawnZombieAudioPlayer = new MediaPlayer(new Media(spawningZombieAudio));
        zombieDeathAudioPlayer = new MediaPlayer(new Media(zombieDeathAudio));
        loadTrapAudioPlayer = new MediaPlayer(new Media(loadTrapAudio));
        loadVampireSAudioPlayer =  new MediaPlayer(new Media(loadVampireS));
        loadZombieSAudioPlayer =  new MediaPlayer(new Media(loadZombieS));
        loadViliageAudioPlayer =  new MediaPlayer(new Media(loadVilliageAudio));
        loadBarracksAudioPlayer = new MediaPlayer(new Media(loadBarracksAudio));
        loadTowerAudioPlayer = new MediaPlayer(new Media(loadTowerAudio));
        loadCampfireAudioPlayer = new MediaPlayer(new Media(loadCampfireAudio));
        spawnVampireAudioPlayer = new MediaPlayer(new Media(spawnVampireAudio));
        vampireDeathAudioPlayer = new MediaPlayer(new Media(vampireDeathAudio));
        spawnDoggieAudioPlayer = new MediaPlayer(new Media(spawnDoggieAudio));
        doggieDeathAudioPlayer = new MediaPlayer(new Media(doggieDeathAudio));
        spawnElanMuskAudioPlayer = new MediaPlayer(new Media(spawnElanMuskAudio));
        elanDeathAudioPlayer = new MediaPlayer(new Media(elanMuskDeathAudio));
        addAlliedSoliderAudioPlayer = new MediaPlayer(new Media(addAlliedSoldierAudio));


        deathMediaPlayer.setVolume(0.03);
        backgroundMusicPlayer.setVolume(0.03);
        shopAudioPlayer.setVolume(0.03);
        equippingSwordAudioPlayer.setVolume(0.03);
        equippingArmourAudioPlayer.setVolume(0.2);
        equippingShieldAudioPlayer.setVolume(0.03);
        spawnZombieAudioPlayer.setVolume(0.03);
        zombieDeathAudioPlayer.setVolume(0.03);
        loadTrapAudioPlayer.setVolume(0.03);
        loadVampireSAudioPlayer.setVolume(0.06);
        loadZombieSAudioPlayer.setVolume(0.03);
        loadViliageAudioPlayer.setVolume(0.06);
        loadBarracksAudioPlayer.setVolume(0.06);
        loadTowerAudioPlayer.setVolume(0.06);
        loadCampfireAudioPlayer.setVolume(0.06);
        vampireDeathAudioPlayer.setVolume(0.03);
        spawnVampireAudioPlayer.setVolume(0.03);
        spawnDoggieAudioPlayer.setVolume(0.03);
        doggieDeathAudioPlayer.setVolume(0.03);
        spawnElanMuskAudioPlayer.setVolume(0.03);
        elanDeathAudioPlayer.setVolume(0.03);
        addAlliedSoliderAudioPlayer.setVolume(0.03);

        backgroundMusicPlayer.play();
        backgroundMusicPlayer.setCycleCount(100);

    }

    @FXML
    public void initialize() {
        // TODO = load more images/entities during initialization

        Image pathTilesImage = new Image((new File("src/images/32x32GrassAndDirtPath.png")).toURI().toString());
        Image inventorySlotImage = new Image((new File("src/images/empty_slot.png")).toURI().toString());
        Rectangle2D imagePart = new Rectangle2D(0, 0, 32, 32);
        Image cardSlotImage = new Image((new File("src/images/card_slot.png")).toURI().toString());
        healthBar.setStyle("-fx-accent: green;");

        // Add the ground first so it is below all other entities (inculding all the twists and turns)
        for (int x = 0; x < world.getWidth(); x++) {
            for (int y = 0; y < world.getHeight(); y++) {
                ImageView groundView = new ImageView(pathTilesImage);
                groundView.setViewport(imagePart);
                squares.add(groundView, x, y);
            }
        }

        // load entities loaded from the file in the loader into the squares gridpane
        for (ImageView entity : entityImages){
            squares.getChildren().add(entity);
        }

        // add the ground underneath the cards
        for (int x=0; x<world.getWidth(); x++){
            ImageView cardSlotView = new ImageView(cardSlotImage);
            cards.add(cardSlotView, x, 0);
        }

        // add the empty slot images for the unequipped inventory
        for (int x=0; x<LoopManiaWorld.unequippedInventoryWidth; x++){
            for (int y=0; y<LoopManiaWorld.unequippedInventoryHeight; y++){
                ImageView emptySlotView = new ImageView(inventorySlotImage);
                unequippedInventory.add(emptySlotView, x, y);
            }
        }

        // add the empty slot images for the allied soldier slot
        for (int x=0; x<LoopManiaWorld.alliedSoldierNumber; x++){
            ImageView emptySlotView = new ImageView(cardSlotImage);
            alliedSoldierSlot.add(emptySlotView, x, 0);
        }

        // create the draggable icon
        draggedEntity = new DragIcon();
        draggedEntity.setVisible(false);
        draggedEntity.setOpacity(0.7);
        anchorPaneRoot.getChildren().add(draggedEntity);

        anchorPaneRoot.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent e) {
                anchorPaneRoot.requestFocus();
            }
        });
    }

    /**
     * create and run the timer
     */
    public void startTimer(){
        // TODO = handle more aspects of the behaviour required by the specification
        System.out.println("starting timer");
        isPaused = false;
        // trigger adding code to process main game logic to queue. JavaFX will target framerate of 0.3 seconds
        timeline = new Timeline(new KeyFrame(Duration.seconds(0.3), event -> {

            isTimelineRunning = true;

            /**
             * Progress: 1. Move character and enemies
             *           2. Check if character is on a spawned item and add it to the inventory
             *           3. Spawn items on tiles
             *           4. Spawn enemies
             *           5. Apply Traps to enemies
             *           6. Apply static (permanant) Building Buffs to character
             *           7. Run Battles if character is not on hero castle
             *           P.S. Equipped items buff are in real time, so wont be included in the timeline
             */

            world.runTickMoves();

            Item newItem = world.checkAndAddSpawnedItem();
            if(newItem != null) onLoadUnequippedItem(newItem);

            List<Item> newSpawnedItems = world.possiblySpawnItems();
            for (Item item : newSpawnedItems){
                onLoadSpawnedItem(item);
            }

            List<Enemy> newEnemies = world.possiblySpawnEnemies();
            for (Enemy newEnemy: newEnemies){
                onLoad(newEnemy);
            }

            world.applyTrapsToEnemies();
            world.applyStaticBuildingBuffsToCharacter();

            if(world.characterIsOnHeroCastle()){
                if(!world.hasHumanPlayerWon()) {
                    world.nextCycle();
                    world.updateDoggieCoin();
                    openShop();
                }
            }else{
                List<Enemy> defeatedEnemies = world.runBattles();
                if (world.getCharacterCurrentHp() <= 0) {
                    backgroundMusicPlayer.pause();
                    deathMediaPlayer.play();
                }
                for (Enemy e: defeatedEnemies){
                    reactToEnemyDefeat(e);
                }
            }

            setEnemyImagesToFront();
            setCharacterImageToFront();

            printThreadingNotes("HANDLED TIMER");
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * pause the execution of the game loop
     * the human player can still drag and drop items during the game pause
     */
    public void pause(){
        isPaused = true;
        System.out.println("pausing");
        timeline.stop();
    }

    public void terminate(){
        pause();
    }

    /**
     * pair the entity an view so that the view copies the movements of the entity.
     * add view to list of entity images
     * @param entity backend entity to be paired with view
     * @param view frontend imageview to be paired with backend entity
     */
    private void addEntity(Entity entity, ImageView view) {
        trackPosition(entity, view);
        entityImages.add(view);
    }

    /**
     * load a random card from the world, and pair it with an image in the GUI
     */
    private void loadRandomCard(){
        onLoad(world.loadRandomCard());
    }

    /**
     * load a random item from the world, and pair it with an image in the GUI
     */
    public void loadRandomItem(){
        // start by getting first available coordinates
        Item item = world.loadRandomUnenquippedInventoryItem();
        if(item != null) onLoadUnequippedItem(item);
    }

    /**
     * Load doggie coin from the world, and pair it with an image in the GUI
     */
    public void loadDoggieCoin(){
        onLoadUnequippedItem(world.loadDoggieCoinItem());
    }

    /**
     * run GUI events after an enemy is defeated, such as spawning items/experience/gold
     * @param enemy defeated enemy for which we should react to the death of
     */
    private void reactToEnemyDefeat(Enemy enemy){
        // react to character defeating an enemy
        // in starter code, spawning extra card/weapon...
        // TODO = provide different benefits to defeating the enemy based on the type of enemy

        switch(enemy.getEnemyType()){
            case SLUG:
                break;
            case ZOMBIE:
                zombieDeathAudioPlayer.play();
                zombieDeathAudioPlayer.seek(Duration.ZERO);
                break;
            case VAMPIRE:
                vampireDeathAudioPlayer.play();
                vampireDeathAudioPlayer.seek(Duration.ZERO);
                break;
            case DOGGIE:
                doggieDeathAudioPlayer.play();
                doggieDeathAudioPlayer.seek(Duration.ZERO);
                break;
            case ELAN_MUSKE:
                elanDeathAudioPlayer.play();
                elanDeathAudioPlayer.seek(Duration.ZERO);
                break;
            default:
                return;
        }
        loadRandomItem();
        loadRandomCard();
        world.addExperienceReward(enemy.getEnemyType());
    }

    /**
     * Load the Spawned Item on path tiles
     * @param item
     */
    private void onLoadSpawnedItem(Item item){
        ImageView view = null;
        switch(item.getItemType()){
            case GOLD:
                view = new ImageView(goldImage);
                break;
            case HEALTH_POTION:
                view = new ImageView(healthPotionImage);
                break;
            default:
                return; /* Should never happen */
        }
        addEntity(item, view);
        squares.getChildren().add(view);
    }

    /**
     * Load a card regarding its type into the GUI.
     * Particularly, we must connect to the drag detection event handler,
     * and load the image into the cards GridPane.
     * @param card
     */
    private void onLoad(Card card){
        ImageView view = null;
        switch(card.getCardType()){
            case VAMPIRECASTLE_CARD:
                view = new ImageView(vampireCastleCardImage);
                break;
            case ZOMBIEPIT_CARD:
                view = new ImageView(zombiePitCardImage);
                break;
            case TOWER_CARD:
                view = new ImageView(towerCardImage);
                break;
            case VILLAGE_CARD:
                view = new ImageView(villageCardImage);
                break;
            case BARRACKS_CARD:
                view = new ImageView(barracksCardImage);
                break;
            case TRAP_CARD:
                view = new ImageView(trapCardImage);
                break;
            case CAMPFIRE_CARD:
                view = new ImageView(campfireCardImage);
                break;
            default:
                /* Should never happen */
                return;
        }
        // FROM https://stackoverflow.com/questions/41088095/javafx-drag-and-drop-to-gridpane
        // note target setOnDragOver and setOnDragEntered defined in initialize method
        addDragEventHandlers(view, DRAGGABLE_TYPE.CARDS, cards, squares);

        addEntity(card, view);
        cards.getChildren().add(view);
    }

    /**
     * load an item into the GUI.
     * Particularly, we must connect to the drag detection event handler,
     * and load the image into the unequippedInventory GridPane.
     * @param item
     */
    public void onLoadUnequippedItem(Item item) {
        ImageView view = null;
        DRAGGABLE_TYPE draggableType = DRAGGABLE_TYPE.ITEMS;
        ItemType subType = item.getItemSubType();
        switch(item.getItemType()){
            case SWORD:
                view = new ImageView(swordImage);
                break;
            case STAKE:
                view = new ImageView(stakeImage);
                break;
            case STAFF:
                view = new ImageView(staffImage);
                break;
            case ARMOUR:
                view = new ImageView(armourImage);
                break;
            case SHIELD:
                view = new ImageView(shieldImage);
                break;
            case HELMET:
                view = new ImageView(helmetImage);
                break;
            case HEALTH_POTION:
                view = new ImageView(healthPotionImage);
                draggableType = null;
                break;
            case DOGGIECOIN:
                view = new ImageView(doggieCoinImage);
                draggableType = null;
                break;
            case THE_ONE_RING:
                view = new ImageView(theOneRingImage);
                if(subType == null) draggableType = null;
                break;
            case ANDURIL:
                view = new ImageView(andurilImage);
                break;
            case TREE_STUMP:
                view = new ImageView(treeStumpImage);
                break;
            default:
                return;
        }
        if(!(draggableType == null)){
            addDragEventHandlers(view, draggableType, unequippedInventory, equippedItems);
        }
        addEntity(item, view);
        unequippedInventory.add(view, item.getX(), item.getY());
    }

    /**
     * load an equipped item into the GUI.
     * Particularly, we must connect to the drag detection event handler,
     * and load the image into the equippedItem GridPane.
     * @param item
     */
    private void onLoadEquippedItem(Item item) {
        ImageView view = null;
        DRAGGABLE_TYPE draggableType = DRAGGABLE_TYPE.ITEMS;
        switch(item.getItemType()){
            case SWORD:
                view = new ImageView(swordImage);
                equippingSwordAudioPlayer.play();
                equippingSwordAudioPlayer.seek(Duration.ZERO);
                break;
            case STAKE:
                view = new ImageView(stakeImage);
                equippingSwordAudioPlayer.play();
                equippingSwordAudioPlayer.seek(Duration.ZERO);
                break;
            case STAFF:
                view = new ImageView(staffImage);
                equippingSwordAudioPlayer.play();
                equippingSwordAudioPlayer.seek(Duration.ZERO);
                break;
            case ARMOUR:
                view = new ImageView(armourImage);
                equippingArmourAudioPlayer.play();
                equippingArmourAudioPlayer.seek(Duration.ZERO);
                break;
            case SHIELD:
                view = new ImageView(shieldImage);
                equippingShieldAudioPlayer.play();
                equippingShieldAudioPlayer.seek(Duration.ZERO);
                break;
            case HELMET:
                view = new ImageView(helmetImage);
                equippingArmourAudioPlayer.play();
                equippingArmourAudioPlayer.seek(Duration.ZERO);
                break;
            case THE_ONE_RING:
                view = new ImageView(theOneRingImage);
                break;
            case ANDURIL:
                view = new ImageView(andurilImage);
                equippingSwordAudioPlayer.play();
                equippingSwordAudioPlayer.seek(Duration.ZERO);
                break;
            case TREE_STUMP:
                view = new ImageView(treeStumpImage);
                equippingShieldAudioPlayer.play();
                equippingShieldAudioPlayer.seek(Duration.ZERO);
                break;
            default:
                return;
        }
        addDragEventHandlers(view, draggableType, equippedItems, unequippedInventory);
        addEntity(item, view);
        equippedItems.add(view, item.getX(), item.getY());
    }

    /**
     * load an enemy into the GUI
     * @param enemy
     */
    private void onLoad(Enemy enemy) {
        ImageView view = null;
        switch(enemy.getEnemyType()){
            case SLUG:
                view = new ImageView(slugImage);
                break;
            case ZOMBIE:
                view = new ImageView(zombieImage);
                spawnZombieAudioPlayer.play();
                spawnZombieAudioPlayer.seek(Duration.ZERO);
                break;
            case VAMPIRE:
                view = new ImageView(vampireImage);
                spawnVampireAudioPlayer.play();
                spawnVampireAudioPlayer.seek(Duration.ZERO);
                break;
            case DOGGIE:
                view = new ImageView(doggieImage);
                spawnDoggieAudioPlayer.play();
                spawnDoggieAudioPlayer.seek(Duration.ZERO);
                break;
            case ELAN_MUSKE:
                view = new ImageView(elanMuskeImage);
                spawnElanMuskAudioPlayer.play();
                spawnElanMuskAudioPlayer.seek(Duration.ZERO);
                break;
            default:
                return;
        }
        view.setId("enemy");
        addEntity(enemy, view);
        squares.add(view, enemy.getX(), enemy.getY());
    }

    /**
     * load a building into the GUI
     * @param building
     */
    private void onLoad(Building building){
        ImageView view = null;
        switch(building.getBuildingType()){
            case VAMPIRECASTLE_BUILDING:
                loadVampireSAudioPlayer.play();
                loadVampireSAudioPlayer.seek(Duration.ZERO);
                view = new ImageView(vampireCastleBuildingImage);
                break;
            case ZOMBIEPIT_BUILDING:
                loadZombieSAudioPlayer.play();
                loadZombieSAudioPlayer.seek(Duration.ZERO);
                view = new ImageView(zombiePitBuildingImage);
                break;
            case TOWER_BUILDING:
                loadTowerAudioPlayer.play();
                loadTowerAudioPlayer.seek(Duration.ZERO);
                view = new ImageView(towerBuildingImage);
                break;
            case VILLAGE_BUILDING:
                loadViliageAudioPlayer.play();
                loadViliageAudioPlayer.seek(Duration.ZERO);
                view = new ImageView(villageBuildingImage);
                break;
            case BARRACKS_BUILDING:
                loadBarracksAudioPlayer.play();
                loadBarracksAudioPlayer.seek(Duration.ZERO);
                view = new ImageView(barracksBuildingImage);
                break;
            case TRAP_BUILDING:
                loadTrapAudioPlayer.play();
                loadTrapAudioPlayer.seek(Duration.ZERO);
                view = new ImageView(trapBuildingImage);
                break;
            case CAMPFIRE_BUILDING:
                loadCampfireAudioPlayer.play();
                loadCampfireAudioPlayer.seek(Duration.ZERO);
                view = new ImageView(campfireBuildingImage);
                break;
            default:
                /* This should never happen */
                break;
        }
        addEntity(building, view);
        squares.getChildren().add(view);
    }

    private void onLoad(AlliedSoldier alliedSoldier){
        ImageView view = new ImageView(alliedSoldierImage);
        trackAlliedSoldierSlotPosition(alliedSoldier, view);
        entityImages.add(view); //Redundant???
        alliedSoldierSlot.add(view, alliedSoldier.getSlotPosition().get(), 0);
        addAlliedSoliderAudioPlayer.play();
        addAlliedSoliderAudioPlayer.seek(Duration.ZERO);
    }

    /**
     * add drag event handlers for dropping into gridpanes, dragging over the background, dropping over the background.
     * These are not attached to invidual items such as swords/cards.
     * @param draggableType the type being dragged - card or item
     * @param sourceGridPane the gridpane being dragged from
     * @param targetGridPane the gridpane the human player should be dragging to (but we of course cannot guarantee they will do so)
     */
    private void buildNonEntityDragHandlers(DRAGGABLE_TYPE draggableType, GridPane sourceGridPane, GridPane targetGridPane){
        // for example, in the specification, villages can only be dropped on path, whilst vampire castles cannot go on the path

        gridPaneSetOnDragDropped.put(draggableType, new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                // TODO = for being more selective about where something can be dropped, consider applying additional if-statement logic
                /*
                 *you might want to design the application so dropping at an invalid location drops at the most recent valid location hovered over,
                 * or simply allow the card/item to return to its slot (the latter is easier, as you won't have to store the last valid drop location!)
                 */
                if (currentlyDraggedType == draggableType){
                    // problem = event is drop completed is false when should be true...
                    // https://bugs.openjdk.java.net/browse/JDK-8117019
                    // putting drop completed at start not making complete on VLAB...

                    //Data dropped
                    //If there is an image on the dragboard, read it and use it
                    Dragboard db = event.getDragboard();
                    Node node = event.getPickResult().getIntersectedNode();
                    if(node != targetGridPane && db.hasImage()){
                        if(!isPlacable(currentlyDraggedType, GridPane.getColumnIndex(node), GridPane.getRowIndex(node), sourceGridPane)){
                            currentlyDraggedImage.setVisible(true);
                            printThreadingNotes("DRAG DROPPED ON GRIDPANE CANCELLED");
                        }else{
                            Integer cIndex = GridPane.getColumnIndex(node);
                            Integer rIndex = GridPane.getRowIndex(node);
                            int x = cIndex == null ? 0 : cIndex;
                            int y = rIndex == null ? 0 : rIndex;
                            //Places at 0,0 - will need to take coordinates once that is implemented
                            //ImageView image = new ImageView(db.getImage());

                            int nodeX = GridPane.getColumnIndex(currentlyDraggedImage);
                            int nodeY = GridPane.getRowIndex(currentlyDraggedImage);

                            /**
                             * At this point everything dropped is valid and should destroy
                             * any overlapped entities
                             * Single Threaded? if no add a pause
                             */
                            checkAndDestroyOverlappedEntity(x, y, targetGridPane);

                            switch (draggableType){
                                case CARDS:
                                    Building newBuilding = convertCardToBuildingByCoordinates(nodeX, nodeY, x, y);
                                    if(newBuilding.getBuildingType().isSpawnable()){
                                        ((Spawner)newBuilding).addSpawningTile(getSpawnablePathTiles(x, y));
                                    }
                                    onLoad(newBuilding);
                                    break;
                                case ITEMS:
                                    if(sourceGridPane == unequippedInventory){
                                        onLoadEquippedItem(equip(nodeX, nodeY, x, y));
                                    }else{
                                        onLoadUnequippedItem(unequip(nodeX, nodeY, x, y));
                                    }
                                    break;
                                default:
                                    break;
                            }
                            printThreadingNotes("DRAG DROPPED ON GRIDPANE HANDLED");
                        }
                        draggedEntity.setVisible(false);
                        draggedEntity.setMouseTransparent(false);
                        // remove drag event handlers before setting currently dragged image to null
                        removeDraggableDragEventHandlers(draggableType, targetGridPane);
                        currentlyDraggedImage = null;
                        currentlyDraggedType = null;
                        currentlyDraggedTargetGridPane = null;
                        currentlyDraggedSourceGridPane = null;
                    }
                }
                event.setDropCompleted(true);
                // consuming prevents the propagation of the event to the anchorPaneRoot (as a sub-node of anchorPaneRoot, GridPane is prioritized)
                // https://openjfx.io/javadoc/11/javafx.base/javafx/event/Event.html#consume()
                // to understand this in full detail, ask your tutor or read https://docs.oracle.com/javase/8/javafx/events-tutorial/processing.htm
                event.consume();
            }
        });

        // this doesn't fire when we drag over GridPane because in the event handler for dragging over GridPanes, we consume the event
        anchorPaneRootSetOnDragOver.put(draggableType, new EventHandler<DragEvent>(){
            // https://github.com/joelgraff/java_fx_node_link_demo/blob/master/Draggable_Node/DraggableNodeDemo/src/application/RootLayout.java#L110
            @Override
            public void handle(DragEvent event) {
                if (currentlyDraggedType == draggableType){
                    if(event.getGestureSource() != anchorPaneRoot && event.getDragboard().hasImage()){
                        event.acceptTransferModes(TransferMode.MOVE);
                    }
                }
                if (currentlyDraggedType != null){
                    draggedEntity.relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));
                }
                event.consume();
            }
        });

        // this doesn't fire when we drop over GridPane because in the event handler for dropping over GridPanes, we consume the event
        anchorPaneRootSetOnDragDropped.put(draggableType, new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                if (currentlyDraggedType == draggableType){
                    //Data dropped
                    //If there is an image on the dragboard, read it and use it
                    Dragboard db = event.getDragboard();
                    Node node = event.getPickResult().getIntersectedNode();
                    if(node != anchorPaneRoot && db.hasImage()){
                        //Places at 0,0 - will need to take coordinates once that is implemented
                        currentlyDraggedImage.setVisible(true);
                        draggedEntity.setVisible(false);
                        draggedEntity.setMouseTransparent(false);
                        // remove drag event handlers before setting currently dragged image to null
                        removeDraggableDragEventHandlers(draggableType, targetGridPane);

                        currentlyDraggedImage = null;
                        currentlyDraggedType = null;
                        currentlyDraggedTargetGridPane = null;
                        currentlyDraggedSourceGridPane = null;
                    }
                }
                //let the source know whether the image was successfully transferred and used
                event.setDropCompleted(true);
                event.consume();
            }
        });
    }

    /**
     * remove the card from the world, and spawn and return a building instead where the card was dropped
     * @param cardNodeX the x coordinate of the card which was dragged, from 0 to width-1
     * @param cardNodeY the y coordinate of the card which was dragged (in starter code this is 0 as only 1 row of cards)
     * @param buildingNodeX the x coordinate of the drop location for the card, where the building will spawn, from 0 to width-1
     * @param buildingNodeY the y coordinate of the drop location for the card, where the building will spawn, from 0 to height-1
     * @return building entity returned from the world
     */
    private Building convertCardToBuildingByCoordinates(int cardNodeX, int cardNodeY, int buildingNodeX, int buildingNodeY) {
        return world.convertCardToBuildingByCoordinates(cardNodeX, cardNodeY, buildingNodeX, buildingNodeY);
    }

    /**
     * remove an item from the unequipped inventory by its x and y coordinates in the unequipped inventory gridpane
     * @param nodeX x coordinate from 0 to unequippedInventoryWidth-1
     * @param nodeY y coordinate from 0 to unequippedInventoryHeight-1
     */
    public void removeUnequippedInventoryByCoordinates(int nodeX, int nodeY) {
        world.removeUnequippedInventoryItemByCoordinates(nodeX, nodeY);
    }

    /**
     * Perform unequip action
     * @param item item to be unequipped
     * @param x x pos
     * @param y y pos
     * @return the new unequipped item
     */
    private Item unequip(int nodeX, int nodeY, int x, int y){
        return world.unequipItem(nodeX, nodeY, x, y);
    }

    /**
     * Equip Item
     * @param item Item to be equipped
     * @param nodeX original node x pos
     * @param nodeY original node y pos
     * @param x new x pos
     * @param y new y pos
     * @return
     */
    private Item equip(int nodeX, int nodeY, int x, int y){
        return world.equipItem(nodeX, nodeY, x, y);
    }

    /**
     * add drag event handlers to an ImageView
     * @param view the view to attach drag event handlers to
     * @param draggableType the type of item being dragged - card or item
     * @param sourceGridPane the relevant gridpane from which the entity would be dragged
     * @param targetGridPane the relevant gridpane to which the entity would be dragged to
     */
    private void addDragEventHandlers(ImageView view, DRAGGABLE_TYPE draggableType, GridPane sourceGridPane, GridPane targetGridPane){
        view.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                currentlyDraggedImage = view; // set image currently being dragged, so squares setOnDragEntered can detect it...
                currentlyDraggedType = draggableType;
                currentlyDraggedTargetGridPane = targetGridPane;
                anchorPaneRoot.requestFocus();
                //Drag was detected, start drap-and-drop gesture
                //Allow any transfer node
                Dragboard db = view.startDragAndDrop(TransferMode.MOVE);

                //Put ImageView on dragboard
                ClipboardContent cbContent = new ClipboardContent();
                cbContent.putImage(view.getImage());
                db.setContent(cbContent);
                view.setVisible(false);

                buildNonEntityDragHandlers(draggableType, sourceGridPane, targetGridPane);

                draggedEntity.relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));
                draggedEntity.setImage(view.getImage());
                draggedEntity.setVisible(true);
                draggedEntity.setMouseTransparent(true);
                draggedEntity.toFront();

                // IMPORTANT!!!
                // to be able to remove event handlers, need to use addEventHandler
                // https://stackoverflow.com/a/67283792
                targetGridPane.addEventHandler(DragEvent.DRAG_DROPPED, gridPaneSetOnDragDropped.get(draggableType));
                anchorPaneRoot.addEventHandler(DragEvent.DRAG_OVER, anchorPaneRootSetOnDragOver.get(draggableType));
                anchorPaneRoot.addEventHandler(DragEvent.DRAG_DROPPED, anchorPaneRootSetOnDragDropped.get(draggableType));

                for (Node n: targetGridPane.getChildren()){
                    // events for entering and exiting are attached to squares children because that impacts opacity change
                    // these do not affect visibility of original image...
                    // https://stackoverflow.com/questions/41088095/javafx-drag-and-drop-to-gridpane
                    gridPaneNodeSetOnDragEntered.put(draggableType, new EventHandler<DragEvent>() {
                        // TODO = be more selective about whether highlighting changes - if it cannot be dropped in the location, the location shouldn't be highlighted!
                        public void handle(DragEvent event) {
                            if (currentlyDraggedType == draggableType){
                            //The drag-and-drop gesture entered the target
                            //show the user that it is an actual gesture target
                                if(event.getGestureSource() != n && event.getDragboard().hasImage()
                                    && isPlacable(currentlyDraggedType, GridPane.getColumnIndex(n), GridPane.getRowIndex(n), sourceGridPane)){
                                    n.setOpacity(0.7);
                                    currentlyHighlightedImage = n;
                                }
                            }
                            event.consume();
                        }
                    });
                    gridPaneNodeSetOnDragExited.put(draggableType, new EventHandler<DragEvent>() {
                        public void handle(DragEvent event) {
                            if (currentlyDraggedType == draggableType){
                                n.setOpacity(1);
                                currentlyHighlightedImage = null;
                            }

                            event.consume();
                        }
                    });
                    n.addEventHandler(DragEvent.DRAG_ENTERED, gridPaneNodeSetOnDragEntered.get(draggableType));
                    n.addEventHandler(DragEvent.DRAG_EXITED, gridPaneNodeSetOnDragExited.get(draggableType));


                }
                event.consume();
            }

        });

        view.setOnMouseReleased(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                /**
                 * currentlyDraggedImage == null means when the view is released
                 * all the other handlers didnt fire
                 * meaning out of bounds dropping
                 * perform action when it is null else just consume the event as it is valid
                 */
                cleanseDragInput();
                event.consume();
            }

        });

    }

    /**
     * remove drag event handlers so that we don't process redundant events
     * this is particularly important for slower machines such as over VLAB.
     * @param draggableType either cards, or items in unequipped inventory
     * @param targetGridPane the gridpane to remove the drag event handlers from
     */
    private void removeDraggableDragEventHandlers(DRAGGABLE_TYPE draggableType, GridPane targetGridPane){
        // remove event handlers from nodes in children squares, from anchorPaneRoot, and squares
        targetGridPane.removeEventHandler(DragEvent.DRAG_DROPPED, gridPaneSetOnDragDropped.get(draggableType));

        anchorPaneRoot.removeEventHandler(DragEvent.DRAG_OVER, anchorPaneRootSetOnDragOver.get(draggableType));
        anchorPaneRoot.removeEventHandler(DragEvent.DRAG_DROPPED, anchorPaneRootSetOnDragDropped.get(draggableType));

        for (Node n: targetGridPane.getChildren()){
            if(n == currentlyHighlightedImage){
                n.setOpacity(1);
                currentlyHighlightedImage = null;
            }
            n.removeEventHandler(DragEvent.DRAG_ENTERED, gridPaneNodeSetOnDragEntered.get(draggableType));
            n.removeEventHandler(DragEvent.DRAG_EXITED, gridPaneNodeSetOnDragExited.get(draggableType));
        }
        gridPaneSetOnDragDropped.clear();
        anchorPaneRootSetOnDragOver.clear();
        anchorPaneRootSetOnDragDropped.clear();
        gridPaneNodeSetOnDragEntered.clear();
        gridPaneNodeSetOnDragExited.clear();
    }

    /**
     * handle the pressing of keyboard keys.
     * Specifically, we should pause when pressing SPACE
     * @param event some keyboard key press
     */
    @FXML
    public void handleKeyPress(KeyEvent event) {
        // TODO = handle additional key presses, e.g. for consuming a health potion
        switch (event.getCode()) {
        case SPACE:
            if (isPaused){
                backgroundMusicPlayer.play();
                startTimer();
            }
            else{
                backgroundMusicPlayer.pause();
                pause();
            }
            break;
        case H:
            this.world.useHealthPotion();
            break;

        default:
            break;
        }
    }

    public void setMainMenuSwitcher(MenuSwitcher mainMenuSwitcher){
        // TODO = possibly set other menu switchers
        this.mainMenuSwitcher = mainMenuSwitcher;
    }

    /**
     * this method is triggered when click button to go to main menu in FXML
     * @throws IOException
     */
    @FXML
    private void switchToMainMenu() throws IOException {
        // TODO = possibly set other menu switchers
        pause();
        mainMenuSwitcher.switchMenu();
    }

    /**
     * Set a node in a GridPane to have its position track the position of an
     * entity in the world.
     *
     * By connecting the model with the view in this way, the model requires no
     * knowledge of the view and changes to the position of entities in the
     * model will automatically be reflected in the view.
     *
     * note that this is put in the controller rather than the loader because we need to track positions of spawned entities such as enemy
     * or items which might need to be removed should be tracked here
     *
     * NOTE teardown functions setup here also remove nodes from their GridPane. So it is vital this is handled in this Controller class
     * @param entity
     * @param node
     */
    private void trackPosition(Entity entity, Node node) {
        // TODO = tweak this slightly to remove items from the equipped inventory?
        GridPane.setColumnIndex(node, entity.getX());
        GridPane.setRowIndex(node, entity.getY());

        ChangeListener<Number> xListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setColumnIndex(node, newValue.intValue());
            }
        };
        ChangeListener<Number> yListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setRowIndex(node, newValue.intValue());
            }
        };

        // if need to remove items from the equipped inventory, add code to remove from equipped inventory gridpane in the .onDetach part
        ListenerHandle handleX = ListenerHandles.createFor(entity.x(), node)
                                               .onAttach((o, l) -> o.addListener(xListener))
                                               .onDetach((o, l) -> {
                                                    o.removeListener(xListener);
                                                    entityImages.remove(node);
                                                    squares.getChildren().remove(node);
                                                    cards.getChildren().remove(node);
                                                    equippedItems.getChildren().remove(node);
                                                    unequippedInventory.getChildren().remove(node);
                                                })
                                               .buildAttached();
        ListenerHandle handleY = ListenerHandles.createFor(entity.y(), node)
                                               .onAttach((o, l) -> o.addListener(yListener))
                                               .onDetach((o, l) -> {
                                                   o.removeListener(yListener);
                                                   entityImages.remove(node);
                                                   squares.getChildren().remove(node);
                                                   cards.getChildren().remove(node);
                                                   equippedItems.getChildren().remove(node);
                                                   unequippedInventory.getChildren().remove(node);
                                                })
                                               .buildAttached();
        handleX.attach();
        handleY.attach();

        // this means that if we change boolean property in an entity tracked from here, position will stop being tracked
        // this wont work on character/path entities loaded from loader classes
        entity.shouldExist().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> obervable, Boolean oldValue, Boolean newValue) {
                handleX.detach();
                handleY.detach();
            }
        });
    }

    /**
     * Track the allied soldier in the slot position
     * @param alliedSoldier
     * @param node
     */
    public void trackAlliedSoldierSlotPosition(AlliedSoldier alliedSoldier, Node node){

        ChangeListener<Number> listener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setColumnIndex(node, newValue.intValue());
            }
        };

        ListenerHandle handle = ListenerHandles.createFor(alliedSoldier.getSlotPosition(), node)
                                               .onAttach((o, l) -> o.addListener(listener))
                                               .onDetach((o, l) -> {
                                                    o.removeListener(listener);
                                                    entityImages.remove(node);
                                                    alliedSoldierSlot.getChildren().remove(node);
                                                })
                                               .buildAttached();

        handle.attach();

        alliedSoldier.shouldExist().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> obervable, Boolean oldValue, Boolean newValue) {
                handle.detach();
            }
        });
    }

    /**
     * we added this method to help with debugging so you could check your code is running on the application thread.
     * By running everything on the application thread, you will not need to worry about implementing locks, which is outside the scope of the course.
     * Always writing code running on the application thread will make the project easier, as long as you are not running time-consuming tasks.
     * We recommend only running code on the application thread, by using Timelines when you want to run multiple processes at once.
     * EventHandlers will run on the application thread.
     */
    private void printThreadingNotes(String currentMethodLabel){
        System.out.println("\n###########################################");
        System.out.println("Current Health = " + Double.toString(world.getCharacterCurrentHp()));
        System.out.println("current method = "+currentMethodLabel);
        System.out.println("In application thread? = "+Platform.isFxApplicationThread());
        System.out.println("Current system time = "+java.time.LocalDateTime.now().toString().replace('T', ' '));
    }


    /**
     * Check if the the dragged entity can be placed on the node
     * @param draggableType Entity type
     * @param column column of the node
     * @param row row of the node
     * @return
     */
    private boolean isPlacable(DRAGGABLE_TYPE draggableType, int column, int row, GridPane sourceGridPane){
        switch(draggableType){
            case CARDS:
                CardType cardType = world.getCardTypeByCoordinates(GridPane.getColumnIndex(currentlyDraggedImage), GridPane.getRowIndex(currentlyDraggedImage));
                switch(cardType){
                    case VAMPIRECASTLE_CARD:
                    case ZOMBIEPIT_CARD:
                        return canSpawnEnemies(column, row);
                    case TOWER_CARD:
                        return isAdjacentToPath(column, row);
                    case VILLAGE_CARD:
                    case BARRACKS_CARD:
                    case TRAP_CARD:
                        /* If is on hero castle return false */
                        if(world.isOnHeroCastle(column, row)) return false;
                        return isOnPath(column, row);
                    case CAMPFIRE_CARD:
                        return !isOnPath(column, row);
                    default:
                        return false;
                }
            case ITEMS:
                if(currentlyDraggedTargetGridPane == equippedItems){
                    return isOnCorrectEquipableSlot(column, row);
                }else if(sourceGridPane == equippedItems){
                    return true;
                }
            default:
                return false;
        }
    }

    /**
     * Determine if the target node is adjacent to the path
     * @param column column of the node
     * @param row row of the node
     * @return
     */
    private boolean isAdjacentToPath(int column, int row){
        /* If target is on path then it cannot be placed */
        if(isOnPath(column, row)) return false;
        /* Check if the target surroundings has a path nearby */
        Pair<Integer, Integer> up = new Pair<>(column, row - 1);
        Pair<Integer, Integer> right = new Pair<>(column + 1, row);
        Pair<Integer, Integer> down = new Pair<>(column, row + 1);
        Pair<Integer, Integer> left = new Pair<>(column - 1, row);
        if(world.getOrderedPath().contains(up) || world.getOrderedPath().contains(right)
            || world.getOrderedPath().contains(down) || world.getOrderedPath().contains(left)){
                return true;
        }
        return false;
    }

    private boolean canSpawnEnemies(int column, int row){

        if(!isAdjacentToPath(column, row)) return false;

        Pair<Integer, Integer> up = new Pair<>(column, row - 1);
        Pair<Integer, Integer> right = new Pair<>(column + 1, row);
        Pair<Integer, Integer> down = new Pair<>(column, row + 1);
        Pair<Integer, Integer> left = new Pair<>(column - 1, row);

        int count = 0;

        if(world.getOrderedPath().contains(up)){
            count++;
        }
        if(world.getOrderedPath().contains(right)){
            count++;
        }
        if(world.getOrderedPath().contains(down)){
            count++;
        }
        if(world.getOrderedPath().contains(left)){
            count++;
        }
        if((up.getValue0() == LoopManiaWorld.herosCastle.getX() && up.getValue1() == LoopManiaWorld.herosCastle.getY())
            || (right.getValue0() == LoopManiaWorld.herosCastle.getX() && right.getValue1() == LoopManiaWorld.herosCastle.getY())
            || (down.getValue0() == LoopManiaWorld.herosCastle.getX() && down.getValue1() == LoopManiaWorld.herosCastle.getY())
            || (left.getValue0() == LoopManiaWorld.herosCastle.getX() && left.getValue1() == LoopManiaWorld.herosCastle.getY())){
                if(count == 1) return false;
            }

        return true;
    }

    /**
     * Determine if the target node is on the path
     * @param column column of the node
     * @param row row of the node
     * @return
     */
    private boolean isOnPath(int column, int row){
        /* If target is on path then return true */
        Pair<Integer, Integer> target = new Pair<>(column, row);
        if(world.getOrderedPath().contains(target)) return true;
        return false;
    }

    /**
     * Get all adjacent path tiles around the node
     * @param column column of the node
     * @param row row of the node
     * @return
     */
    private List<Pair<Integer, Integer>> getSpawnablePathTiles(int column, int row){
        List<Pair<Integer, Integer>> result = new ArrayList<Pair<Integer, Integer>>();
        /* Check if the target surroundings has a path nearby */
        Pair<Integer, Integer> up = new Pair<>(column, row - 1);
        Pair<Integer, Integer> right = new Pair<>(column + 1, row);
        Pair<Integer, Integer> down = new Pair<>(column, row + 1);
        Pair<Integer, Integer> left = new Pair<>(column - 1, row);
        if(world.getOrderedPath().contains(up)
            && !(up.getValue0() == LoopManiaWorld.herosCastle.getX() && up.getValue1() == LoopManiaWorld.herosCastle.getY())){
            result.add(up);
        }
        if(world.getOrderedPath().contains(right)
            && !(right.getValue0() == LoopManiaWorld.herosCastle.getX() && right.getValue1() == LoopManiaWorld.herosCastle.getY())){
            result.add(right);
        }
        if(world.getOrderedPath().contains(down)
            && !(down.getValue0() == LoopManiaWorld.herosCastle.getX() && down.getValue1() == LoopManiaWorld.herosCastle.getY())){
            result.add(down);
        }
        if(world.getOrderedPath().contains(left)
            && !(left.getValue0() == LoopManiaWorld.herosCastle.getX() && left.getValue1() == LoopManiaWorld.herosCastle.getY())){
            result.add(left);
        }
        return result;
    }

    /**
     * Check if the node is on correct equippable slot
     * @param draggableType type of dragged node
     * @param column column of the node
     * @param row row of the node
     * @return
     */
    private boolean isOnCorrectEquipableSlot(int column, int row){
        ImageView targetCell = null;
        ItemType type = world.getUnequippedItemTypeByCoordinates(GridPane.getColumnIndex(currentlyDraggedImage), GridPane.getRowIndex(currentlyDraggedImage));
        ItemType subType = world.getUnequippedItemSubTypeByCoordinates(GridPane.getColumnIndex(currentlyDraggedImage), GridPane.getRowIndex(currentlyDraggedImage));
        switch(type){
            case SWORD:
            case STAKE:
            case STAFF:
            case ANDURIL:
                targetCell = weaponCell;
                break;
            case ARMOUR:
                targetCell = armourCell;
                break;
            case SHIELD:
            case TREE_STUMP:
                targetCell = shieldCell;
                break;
            case HELMET:
                targetCell = helmetCell;
                break;
            default:
                break;
        }

        if(targetCell != null && column == GridPane.getColumnIndex(targetCell) && row == GridPane.getRowIndex(targetCell)){
            return true;
        }

        if(subType != null){
            switch(subType){
                case ANDURIL:
                    targetCell = weaponCell;
                    break;
                case TREE_STUMP:
                    targetCell = shieldCell;
                    break;
                default:
                    break;
            }

            if(targetCell != null && column == GridPane.getColumnIndex(targetCell) && row == GridPane.getRowIndex(targetCell)){
                return true;
            }
        }

        return false;
    }

    /**
     * Set character image to the front
     */
    private void setCharacterImageToFront(){
        for(ImageView view : entityImages){
            if(view.getId() != null && view.getId().equals("character")) {
                view.toFront();
                return;
            }
        }
    }

    /**
     * Set enemies images to the front
     */
    private void setEnemyImagesToFront(){
        for(ImageView view : entityImages){
            if(view.getId() != null && view.getId().equals("enemy")) {
                view.toFront();
            }
        }
    }

    /**
     * Destroy the overlapped entity when placed on gridpane
     * @param x x coordinate
     * @param y y coordinate
     * @param targetGridPane the targetGridPane
     */
    private void checkAndDestroyOverlappedEntity(int x, int y, GridPane targetGridPane){
        OverlappableEntityType type = null;
        if(targetGridPane == squares){
            type = OverlappableEntityType.BUILDING;
        }else if(targetGridPane == equippedItems){
            type = OverlappableEntityType.EQUIPPED_ITEM;
        }else if(targetGridPane == unequippedInventory){
            type = OverlappableEntityType.UNEQUIPPED_INVENTORY_ITEM;
        }else{
            return;
        }
        world.removeOverlappedEntityByCoordinates(x, y, type);
    }

    /**
     * Signal from observable about updating cycle (Observer pattern)
     */
    public void updateCycle(){
        cycleValue.setText(Integer.toString(world.getCycle()));
        primaryStage.sizeToScene();
    }

    /**
     * Signal from observable about updating gold (Observer pattern)
     */
    public void updateGold(){
        goldValue.setText(Integer.toString(world.getCharacter().getGold()));
        primaryStage.sizeToScene();
    }

    /**
     * Signal from observable about updating experience (Observer pattern)
     */
    public void updateExperience(){
        expValue.setText(Integer.toString(world.getCharacter().getExperience()));
        primaryStage.sizeToScene();
    }

    /**
     * Signal from observable about updating experience (Observer pattern)
     */
    public void updateHealth(double currentHealth, double maxHealth){
        if(currentHealth/maxHealth <= 0.3){
            healthBar.setStyle("-fx-accent: red;");
        }else if(currentHealth/maxHealth <= 0.6){
            healthBar.setStyle("-fx-accent: yellow;");
        }else{
            healthBar.setStyle("-fx-accent: green;");
        }
        healthBar.setProgress(currentHealth/maxHealth);
        primaryStage.sizeToScene();
    }

    /**
     * Front end code for adding allied soldier
     * @return
     */
    public AlliedSoldier addAlliedSoldier(){
        AlliedSoldier alliedSoldier = world.addAlliedSoldier();
        if(alliedSoldier == null) return null;
        onLoad(alliedSoldier);
        return alliedSoldier;
    }

    /**
     * Font end code for removing allied soldier
     */
    public void removeAlliedSoldier(){
        List<AlliedSoldier> alliedSoldiers =  world.getCharacter().getAlliedSoldiers();
        shiftAlliedSoldiersFromXCoordinate(alliedSoldiers);
    }

    /**
     * Shift Allied soldiers to fit the slot
     * similar to shift cards down function
     * @param alliedSoldiers
     * @param x
     */
    private void shiftAlliedSoldiersFromXCoordinate(List<AlliedSoldier> alliedSoldiers){
        for(int i = 0; i < alliedSoldiers.size(); i++){
            alliedSoldiers.get(i).setSlotPosition(i);
        }
    }

    /**
     * Set the stage of the application
     * @param primaryStage
     */
    public void setStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    /**
     * Open the shop
     */
    private void openShop(){
        /* Pause the game first */
        if(!isPaused) pause();
        shopAudioPlayer.play();
        shopAudioPlayer.seek(Duration.ZERO);
        /**
         * Cleanse left over dragging event
         */
        cleanseDragInput();

        try {
            Stage shopStage = new Stage();
            FXMLLoader shopLoader = new FXMLLoader(getClass().getResource("LoopManiaShop.fxml"));
            shopLoader.setController(new LoopManiaShopController(this, world, shopStage));
            shopStage.setScene(new Scene(shopLoader.load()));
            shopStage.initStyle(StageStyle.UNDECORATED);
            shopStage.setResizable(false);

            /**
             * Set Stage modality and owner to block all controls to owner
             */
            shopStage.initModality(Modality.WINDOW_MODAL);
            shopStage.initOwner(primaryStage);

            shopStage.show();

            /**
             * Hide and show to center window
             */
            shopStage.hide();
            double centerXPosition = primaryStage.getX() + primaryStage.getWidth()/2d;
            double centerYPosition = primaryStage.getY() + primaryStage.getHeight()/2d;
            shopStage.setX(centerXPosition - shopStage.getWidth()/2d);
            shopStage.setY(centerYPosition - shopStage.getHeight()/2d);
            shopStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displayVictoryMessage(){
        if(world.hasHumanPlayerLost()) return;
        if(!isPaused) pause();
        PopUpMessageController popUpMessageController = openPopUpMessageWindow(primaryStage, "You Won!", Color.GREEN, "Quit Game");
        popUpMessageController.setQuitSwitcher(() ->{
            popUpMessageController.getStage().close();
            primaryStage.close();
        });
    }

    public void displayDefeatMessage(){
        if(world.hasHumanPlayerWon()) return;
        if(!isPaused) pause();
        PopUpMessageController popUpMessageController = openPopUpMessageWindow(primaryStage, "You Lost!", Color.RED, "Quit Game");
        popUpMessageController.setQuitSwitcher(() ->{
            popUpMessageController.getStage().close();
            primaryStage.close();
        });
    }

    public void cleanseDragInput(){
        /**
         * Cleanse left over dragging event
         */
        if(currentlyDraggedImage != null){
            currentlyDraggedImage.setVisible(true);
            draggedEntity.setVisible(false);
            draggedEntity.setMouseTransparent(false);
            // remove drag event handlers before setting currently dragged image to null
            removeDraggableDragEventHandlers(currentlyDraggedType, currentlyDraggedTargetGridPane);
            currentlyDraggedImage = null;
            currentlyDraggedType = null;
            currentlyDraggedTargetGridPane = null;
            currentlyDraggedSourceGridPane = null;
        }
    }

    public void setDifficulty(DifficultyType difficulty){
        this.difficulty = difficulty;
        switch(difficulty){
            case STANDARD:
                difficultyText.setText("Standard Mode");
                difficultyText.setTextFill(Color.BLUEVIOLET);
                break;
            case SURVIVAL:
                difficultyText.setText("Survival Mode");
                difficultyText.setTextFill(Color.GREEN);
                break;
            case BESERKER:
                difficultyText.setText("Beserker Mode");
                difficultyText.setTextFill(Color.RED);
                break;
            case CONFUSING:
                difficultyText.setText("Confusing Mode");
                difficultyText.setTextFill(Color.PINK);
                break;
        }
        primaryStage.sizeToScene();
    }

    public DifficultyType getDifficulty(){
        return difficulty;
    }

    public boolean isTimelineRunning(){
        return isTimelineRunning;
    }

    /**
     * Open a message box
     * @param parentStage
     * @param message
     * @param color
     * @return
     */
    public PopUpMessageController openPopUpMessageWindow(Stage parentStage, String message, Color color, String buttonText){

        /* Pause the game first */
        if(!isPaused) pause();

        cleanseDragInput();

        try {
            Stage popUpMessageStage = new Stage();
            FXMLLoader popUpMessageLoader = new FXMLLoader(getClass().getResource("PopUpMessageView.fxml"));
            PopUpMessageController popUpMessageController = new PopUpMessageController(popUpMessageStage, message, color, buttonText);
            popUpMessageLoader.setController(popUpMessageController);
            popUpMessageStage.setScene(new Scene(popUpMessageLoader.load()));
            popUpMessageStage.initStyle(StageStyle.UNDECORATED);
            popUpMessageStage.setResizable(false);

            /**
             * Set Stage modality and owner to block all controls to owner
             */
            popUpMessageStage.initModality(Modality.WINDOW_MODAL);
            popUpMessageStage.initOwner(parentStage);

            popUpMessageStage.show();

            /**
             * hide and show again to center the stage
             */
            popUpMessageStage.hide();
            double centerXPosition = parentStage.getX() + parentStage.getWidth()/2d;
            double centerYPosition = parentStage.getY() + parentStage.getHeight()/2d;
            popUpMessageStage.setX(centerXPosition - popUpMessageStage.getWidth()/2d);
            popUpMessageStage.setY(centerYPosition - popUpMessageStage.getHeight()/2d);
            popUpMessageStage.show();

            return popUpMessageController;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @FXML
    private void openHelpMenu(){

        /* Pause the game first */
        if(!isPaused) pause();

        /**
         * Cleanse left over dragging event
         */
        cleanseDragInput();

        try {
            Stage helpStage = new Stage();
            FXMLLoader helpLoader = new FXMLLoader(getClass().getResource("HelpMenu.fxml"));
            HelpMenuController helpMenuController = new HelpMenuController(helpStage, this);
            helpLoader.setController(helpMenuController);
            helpStage.setScene(new Scene(helpLoader.load()));
            helpStage.setResizable(false);

            /**
             * Set Stage modality and owner to block all controls to owner
             */
            helpStage.initModality(Modality.WINDOW_MODAL);
            helpStage.initOwner(primaryStage);

            helpStage.show();

            /**
             * Hide and show to center window
             */
            helpStage.hide();
            double centerXPosition = primaryStage.getX() + primaryStage.getWidth()/2d;
            double centerYPosition = primaryStage.getY() + primaryStage.getHeight()/2d;
            helpStage.setX(centerXPosition - helpStage.getWidth()/2d);
            helpStage.setY(centerYPosition - helpStage.getHeight()/2d);
            helpStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
