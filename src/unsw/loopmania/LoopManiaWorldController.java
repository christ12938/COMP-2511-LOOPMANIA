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
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
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
import javafx.stage.Stage;
import javafx.util.Duration;
import unsw.loopmania.Cards.*;
import unsw.loopmania.Enemies.Enemy;
import unsw.loopmania.Items.Equipable;
import unsw.loopmania.Items.Item;
import unsw.loopmania.Loaders.ItemLoader;
import unsw.loopmania.Types.OverlappableEntityType;
import unsw.loopmania.Buildings.*;


import java.util.EnumMap;

import java.io.File;
import java.io.IOException;


/**
 * the draggable types.
 * If you add more draggable types, add an enum value here.
 * This is so we can see what type is being dragged.
 */
enum DRAGGABLE_TYPE{

    VAMPIRECASTLE_CARD,
    ZOMBIEPIT_CARD,
    TOWER_CARD,
    VILLAGE_CARD,
    BARRACKS_CARD,
    TRAP_CARD,
    CAMPFIRE_CARD,

    SWORD,
    STAKE,
    STAFF,
    ARMOUR,
    SHIELD,
    HELMET
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


    /**
     * Image of Enemies
     */
    private Image slugImage;
    private Image zombieImage;
    private Image vampireImage;

    /**
     * the image currently being dragged, if there is one, otherwise null.
     * Holding the ImageView being dragged allows us to spawn it again in the drop location if appropriate.
     */
    private ImageView currentlyDraggedImage;
    
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

    /**
     * @param world world object loaded from file
     * @param initialEntities the initial JavaFX nodes (ImageViews) which should be loaded into the GUI
     */
    public LoopManiaWorldController(LoopManiaWorld world, List<ImageView> initialEntities) {
        this.world = world;
        this.world.getCharacter().setObserver(this);
        entityImages = new ArrayList<>(initialEntities);

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

        /* Initialize all item images */
        //TODO: ADD Rare items
        swordImage = new Image((new File("src/images/basic_sword.png")).toURI().toString());
        stakeImage = new Image((new File("src/images/stake.png")).toURI().toString());
        staffImage = new Image((new File("src/images/staff.png")).toURI().toString());
        armourImage = new Image((new File("src/images/armour.png")).toURI().toString());
        shieldImage = new Image((new File("src/images/shield.png")).toURI().toString());
        helmetImage = new Image((new File("src/images/helmet.png")).toURI().toString());
        goldImage = new Image((new File("src/images/gold_pile.png")).toURI().toString());
        healthPotionImage = new Image((new File("src/images/brilliant_blue_new.png")).toURI().toString());

        currentlyDraggedImage = null;
        currentlyDraggedType = null;

        // initialize them all...
        gridPaneSetOnDragDropped = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        anchorPaneRootSetOnDragOver = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        anchorPaneRootSetOnDragDropped = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        gridPaneNodeSetOnDragEntered = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        gridPaneNodeSetOnDragExited = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
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
            ImageView emptySlotView = new ImageView(inventorySlotImage);
            alliedSoldierSlot.add(emptySlotView, x, 0);
        }

        // create the draggable icon
        draggedEntity = new DragIcon();
        draggedEntity.setVisible(false);
        draggedEntity.setOpacity(0.7);
        anchorPaneRoot.getChildren().add(draggedEntity);
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
            world.runTickMoves();
            List<Enemy> defeatedEnemies = world.runBattles();
            for (Enemy e: defeatedEnemies){
                reactToEnemyDefeat(e);
            }
            List<Enemy> newEnemies = world.possiblySpawnEnemies();
            for (Enemy newEnemy: newEnemies){
                onLoad(newEnemy);
            }
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
    private void loadRandomItem(){
        // start by getting first available coordinates
        Item item = world.loadRandomUnenquippedInventoryItem();
        if(item != null) onLoadUnequippedItem(item);
    }

    /**
     * run GUI events after an enemy is defeated, such as spawning items/experience/gold
     * @param enemy defeated enemy for which we should react to the death of
     */
    private void reactToEnemyDefeat(Enemy enemy){
        // react to character defeating an enemy
        // in starter code, spawning extra card/weapon...
        // TODO = provide different benefits to defeating the enemy based on the type of enemy
        loadRandomItem();
        loadRandomCard();
    }

    /**
     * Load a card regarding its type into the GUI.
     * Particularly, we must connect to the drag detection event handler,
     * and load the image into the cards GridPane.
     * @param card
     */
    private void onLoad(Card card){
        ImageView view = null;
        /* TODO: Enum conversion ??? Possible maintainence problem */
        DRAGGABLE_TYPE draggableType = null;
        switch(card.getCardType()){
            case VAMPIRECASTLE_CARD:
                view = new ImageView(vampireCastleCardImage);
                draggableType = DRAGGABLE_TYPE.VAMPIRECASTLE_CARD;
                break;
            case ZOMBIEPIT_CARD:
                view = new ImageView(zombiePitCardImage);
                draggableType = DRAGGABLE_TYPE.ZOMBIEPIT_CARD;
                break;
            case TOWER_CARD:
                view = new ImageView(towerCardImage);
                draggableType = DRAGGABLE_TYPE.TOWER_CARD;
                break;
            case VILLAGE_CARD:
                view = new ImageView(villageCardImage);
                draggableType = DRAGGABLE_TYPE.VILLAGE_CARD;
                break;
            case BARRACKS_CARD:
                view = new ImageView(barracksCardImage);
                draggableType = DRAGGABLE_TYPE.BARRACKS_CARD;
                break;
            case TRAP_CARD:
                view = new ImageView(trapCardImage);
                draggableType = DRAGGABLE_TYPE.TRAP_CARD;
                break;
            case CAMPFIRE_CARD:
                view = new ImageView(campfireCardImage);
                draggableType = DRAGGABLE_TYPE.CAMPFIRE_CARD;
                break;
            default:
                /* Should never happen */
                return;
        }
        // FROM https://stackoverflow.com/questions/41088095/javafx-drag-and-drop-to-gridpane
        // note target setOnDragOver and setOnDragEntered defined in initialize method
        addDragEventHandlers(view, draggableType, cards, squares);

        addEntity(card, view);
        cards.getChildren().add(view);
    }

    /**
     * load an item into the GUI.
     * Particularly, we must connect to the drag detection event handler,
     * and load the image into the unequippedInventory GridPane.
     * @param item
     */
    private void onLoadUnequippedItem(Item item) {
        ImageView view = null;
        DRAGGABLE_TYPE draggableType = null;
        switch(item.getItemType()){
            case SWORD:
                view = new ImageView(swordImage);
                draggableType = DRAGGABLE_TYPE.SWORD;
                break;
            case STAKE:
                view = new ImageView(stakeImage);
                draggableType = DRAGGABLE_TYPE.STAKE;
                break;
            case STAFF:
                view = new ImageView(staffImage);
                draggableType = DRAGGABLE_TYPE.STAFF;
                break;
            case ARMOUR:
                view = new ImageView(armourImage);
                draggableType = DRAGGABLE_TYPE.ARMOUR;
                break;
            case SHIELD:
                view = new ImageView(shieldImage);
                draggableType = DRAGGABLE_TYPE.SHIELD;
                break;
            case HELMET:
                view = new ImageView(helmetImage);
                draggableType = DRAGGABLE_TYPE.HELMET;
                break;
            case GOLD:
                view = new ImageView(goldImage);
                break;
            case HEALTH_POTION:
                view = new ImageView(healthPotionImage);
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
        DRAGGABLE_TYPE draggableType = null;
        switch(item.getItemType()){
            case SWORD:
                view = new ImageView(swordImage);
                draggableType = DRAGGABLE_TYPE.SWORD;
                break;
            case STAKE:
                view = new ImageView(stakeImage);
                draggableType = DRAGGABLE_TYPE.STAKE;
                break;
            case STAFF:
                view = new ImageView(staffImage);
                draggableType = DRAGGABLE_TYPE.STAFF;
                break;
            case ARMOUR:
                view = new ImageView(armourImage);
                draggableType = DRAGGABLE_TYPE.ARMOUR;
                break;
            case SHIELD:
                view = new ImageView(shieldImage);
                draggableType = DRAGGABLE_TYPE.SHIELD;
                break;
            case HELMET:
                view = new ImageView(helmetImage);
                draggableType = DRAGGABLE_TYPE.HELMET;
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
                break;
            case VAMPIRE:
                view = new ImageView(vampireImage);
                break;
            default:
                return;
        }
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
                view = new ImageView(vampireCastleBuildingImage);
                break;
            case ZOMBIEPIT_BUILDING:
                view = new ImageView(zombiePitBuildingImage);
                break;
            case TOWER_BUILDING:
                view = new ImageView(towerBuildingImage);
                break;
            case VILLAGE_BUILDING:
                view = new ImageView(villageBuildingImage);
                break;
            case BARRACKS_BUILDING:
                view = new ImageView(barracksBuildingImage);
                break;
            case TRAP_BUILDING:
                view = new ImageView(trapBuildingImage);
                break;
            case CAMPFIRE_BUILDING:
                view = new ImageView(campfireBuildingImage);
                break;
            default:
                /* This should never happen */
                break;
        }
        addEntity(building, view);
        squares.getChildren().add(view);
    }

    /**
     * add drag event handlers for dropping into gridpanes, dragging over the background, dropping over the background.
     * These are not attached to invidual items such as swords/cards.
     * @param draggableType the type being dragged - card or item
     * @param sourceGridPane the gridpane being dragged from
     * @param targetGridPane the gridpane the human player should be dragging to (but we of course cannot guarantee they will do so)
     */
    private void buildNonEntityDragHandlers(DRAGGABLE_TYPE draggableType, GridPane sourceGridPane, GridPane targetGridPane){
        // TODO = be more selective about where something can be dropped
        // TODO = OVERLAPPED IMAGE
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
                        if(!isPlacable(currentlyDraggedType, GridPane.getColumnIndex(node), GridPane.getRowIndex(node), sourceGridPane, targetGridPane)){
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
                            switch (draggableType){
                                case VAMPIRECASTLE_CARD:
                                case ZOMBIEPIT_CARD:
                                case TOWER_CARD:
                                case VILLAGE_CARD:
                                case BARRACKS_CARD:
                                case TRAP_CARD:
                                case CAMPFIRE_CARD:
                                    checkAndDestroyOverlappedEntity(x, y, targetGridPane);
                                    Building newBuilding = convertCardToBuildingByCoordinates(nodeX, nodeY, x, y);
                                    if(newBuilding instanceof Spawner){
                                        ((Spawner)newBuilding).addSpawningTile(getAdjacentPathTiles(x, y));
                                    }
                                    onLoad(newBuilding);
                                    break;
                                case SWORD:
                                case STAKE:
                                case STAFF:
                                case ARMOUR:
                                case SHIELD:
                                case HELMET:
                                    // TODO = spawn an item in the new location. The above code for spawning a building will help, it is very similar
                                    // All these cases are equipable
                                    checkAndDestroyOverlappedEntity(x, y, targetGridPane);
                                    if(targetGridPane == unequippedInventory){
                                        Equipable equipableItem = world.getEquippedItemByCoordinates(nodeX, nodeY);
                                        equipableItem = unequip(equipableItem, nodeX, nodeY, x, y);
                                        world.unequipEquippableItem(equipableItem);
                                        onLoadUnequippedItem(equipableItem);
                                    }else if(targetGridPane == equippedItems){
                                        Equipable equipableItem = world.getUnequippedItemByCoordinates(nodeX, nodeY);
                                        equipableItem = equip(equipableItem, nodeX, nodeY, x, y);
                                        world.EquipEquippableItem(equipableItem);
                                        onLoadEquippedItem(equipableItem);

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
    private void removeUnequippedInventoryByCoordinates(int nodeX, int nodeY) {
        world.removeUnequippedInventoryItemByCoordinates(nodeX, nodeY);
    }

    /**
     * Perform unequip actions
     * @param equipableItem item to be unequipped
     * @param nodeX x coordinate position of the item
     * @param nodeY y coordinate position of the item
     * @param x x coordinate of the item to be placed
     * @param y y coordinate of the item to be placed
     * @return the new Equipable item
     */
    private Equipable unequip(Equipable equipableItem, int nodeX, int nodeY, int x, int y){
        world.removeEquippedItem(equipableItem);
        equipableItem = ItemLoader.loadEquipableItem(equipableItem.getItemType(), x, y);
        world.addUnequippedItem(equipableItem);
        return equipableItem;
    }

    /**
     * Perform equip actions
     * @param equipableItem item to be equipped
     * @param nodeX x coordinate position of the item
     * @param nodeY y coordinate position of the item
     * @param x x coordinate of the item to be placed
     * @param y y coordinate of the item to be placed
     * @return the new Equipable item
     */
    private Equipable equip(Equipable equipableItem, int nodeX, int nodeY, int x, int y){
        removeUnequippedInventoryByCoordinates(nodeX, nodeY);
        equipableItem = ItemLoader.loadEquipableItem(equipableItem.getItemType(), x, y);
        world.addEquippedItem(equipableItem);
        return equipableItem;
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
                switch (draggableType){
                    case VAMPIRECASTLE_CARD:
                        draggedEntity.setImage(vampireCastleCardImage);
                        break;
                    case ZOMBIEPIT_CARD:
                        draggedEntity.setImage(zombiePitCardImage);
                        break;
                    case TOWER_CARD:
                        draggedEntity.setImage(towerCardImage);
                        break;
                    case VILLAGE_CARD:
                        draggedEntity.setImage(villageCardImage);
                        break;
                    case BARRACKS_CARD:
                        draggedEntity.setImage(barracksCardImage);
                        break;
                    case TRAP_CARD:
                        draggedEntity.setImage(trapCardImage);
                        break;
                    case CAMPFIRE_CARD:
                        draggedEntity.setImage(campfireCardImage);
                        break;
                    case SWORD:
                        draggedEntity.setImage(swordImage);
                        break;
                    case STAKE:
                        draggedEntity.setImage(stakeImage);
                        break;
                    case STAFF:
                        draggedEntity.setImage(staffImage);
                        break;
                    case ARMOUR:
                        draggedEntity.setImage(armourImage);
                        break;
                    case SHIELD:
                        draggedEntity.setImage(shieldImage);
                        break;
                    case HELMET:
                        draggedEntity.setImage(helmetImage);
                        break;
                    default:
                        break;
                }
                
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
                                    && isPlacable(currentlyDraggedType, GridPane.getColumnIndex(n), GridPane.getRowIndex(n), sourceGridPane, targetGridPane)){
                                    n.setOpacity(0.7);
                                }
                            }
                            event.consume();
                        }
                    });
                    gridPaneNodeSetOnDragExited.put(draggableType, new EventHandler<DragEvent>() {
                        public void handle(DragEvent event) {
                            if (currentlyDraggedType == draggableType){
                                n.setOpacity(1);
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
                if(currentlyDraggedImage != null){
                    currentlyDraggedImage.setVisible(true);
                    draggedEntity.setVisible(false);
                    draggedEntity.setMouseTransparent(false);
                    // remove drag event handlers before setting currently dragged image to null
                    removeDraggableDragEventHandlers(draggableType, targetGridPane);
                    currentlyDraggedImage = null;
                    currentlyDraggedType = null;
                }
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
            n.removeEventHandler(DragEvent.DRAG_ENTERED, gridPaneNodeSetOnDragEntered.get(draggableType));
            n.removeEventHandler(DragEvent.DRAG_EXITED, gridPaneNodeSetOnDragExited.get(draggableType));
        }
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
                startTimer();
            }
            else{
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
     * we added this method to help with debugging so you could check your code is running on the application thread.
     * By running everything on the application thread, you will not need to worry about implementing locks, which is outside the scope of the course.
     * Always writing code running on the application thread will make the project easier, as long as you are not running time-consuming tasks.
     * We recommend only running code on the application thread, by using Timelines when you want to run multiple processes at once.
     * EventHandlers will run on the application thread.
     */
    private void printThreadingNotes(String currentMethodLabel){
        System.out.println("\n###########################################");
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
    private boolean isPlacable(DRAGGABLE_TYPE draggableType, int column, int row, GridPane sourceGridPane, GridPane targetGridPane){
        switch(draggableType){
            case VAMPIRECASTLE_CARD:
            case ZOMBIEPIT_CARD:
            case TOWER_CARD:
                return isAdjacentToPath(column, row);
            case VILLAGE_CARD:
            case BARRACKS_CARD:
            case TRAP_CARD:
                return isOnPath(column, row);
            case CAMPFIRE_CARD:
                return !isOnPath(column, row);
            case SWORD:
            case STAKE:
            case STAFF:
            case ARMOUR:
            case SHIELD:
            case HELMET:
                if(sourceGridPane == equippedItems && targetGridPane == unequippedInventory){
                    return true;
                }else if(sourceGridPane == unequippedInventory && targetGridPane == equippedItems){
                    return isOnCorrectEquippableSlot(draggableType, column, row);
                }else{
                    return false;
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
    private List<Pair<Integer, Integer>> getAdjacentPathTiles(int column, int row){
        List<Pair<Integer, Integer>> result = new ArrayList<Pair<Integer, Integer>>();
        /* Check if the target surroundings has a path nearby */
        Pair<Integer, Integer> up = new Pair<>(column, row - 1);
        Pair<Integer, Integer> right = new Pair<>(column + 1, row);
        Pair<Integer, Integer> down = new Pair<>(column, row + 1);
        Pair<Integer, Integer> left = new Pair<>(column - 1, row);
        if(world.getOrderedPath().contains(up)){
            result.add(up);
        }
        if(world.getOrderedPath().contains(right)){
            result.add(right);
        }
        if(world.getOrderedPath().contains(down)){
            result.add(down);
        }
        if(world.getOrderedPath().contains(left)){
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
    private boolean isOnCorrectEquippableSlot(DRAGGABLE_TYPE draggableType, int column, int row){
        ImageView targetCell = null;
        switch(draggableType){
            case SWORD:
            case STAKE:
            case STAFF:
                targetCell = weaponCell;
                break;
            case ARMOUR:
                targetCell = armourCell;
                break;
            case SHIELD:
                targetCell = shieldCell;
                break;
            case HELMET:
                targetCell = helmetCell;
                break;
            default:
                return false;
        }

        if(column == GridPane.getColumnIndex(targetCell) && row == GridPane.getRowIndex(targetCell)){
            return true;
        }else{
            return false;
        }
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
        world.RemoveOverlappedEntityByCoordinates(x, y, type);
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
        System.err.println("test");
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
     * Set the stage of the application
     * @param primaryStage
     */
    public void setStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }
}
