package unsw.loopmania;

import java.io.File;
import java.util.EnumMap;

import org.javatuples.Pair;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import unsw.loopmania.Items.Item;
import unsw.loopmania.Loaders.ItemLoader;
import unsw.loopmania.Types.ItemType;

public class LoopManiaShopController {

    @FXML
    private GridPane shopSlot;
    
    @FXML
    private Label goldValue;

    @FXML
    private Button resumeButton;

    @FXML
    private AnchorPane anchorPaneRoot;

    /**
     * Image of Items
     */
    private Image swordImage;
    private Image stakeImage;
    private Image staffImage;
    private Image armourImage;
    private Image shieldImage;
    private Image helmetImage;
    private Image healthPotionImage;

    private EnumMap<ItemType, Pair<Integer, Integer>> itemPositions;
    private EnumMap<ItemType, Integer> itemPrices;

    private LoopManiaWorldController parent;
    private LoopManiaWorld world;
    private Stage shopStage;

    private double xOffset;
    private double yOffset;

    public LoopManiaShopController(LoopManiaWorldController parent, LoopManiaWorld world, Stage shopStage) {
        /**
         * Initialize shop items
         */
        swordImage = new Image((new File("src/images/basic_sword.png")).toURI().toString());
        stakeImage = new Image((new File("src/images/stake.png")).toURI().toString());
        staffImage = new Image((new File("src/images/staff.png")).toURI().toString());
        armourImage = new Image((new File("src/images/armour.png")).toURI().toString());
        shieldImage = new Image((new File("src/images/shield.png")).toURI().toString());
        helmetImage = new Image((new File("src/images/helmet.png")).toURI().toString());
        healthPotionImage = new Image((new File("src/images/brilliant_blue_new.png")).toURI().toString());

        itemPositions = new EnumMap<ItemType, Pair<Integer, Integer>>(ItemType.class);
        itemPrices = new EnumMap<ItemType, Integer>(ItemType.class);

        this.parent = parent;
        this.world = world;
        this.shopStage = shopStage;
    }

    @FXML
    public void initialize(){

        setItemPositions();
        setItemPrices();
        setWindowDragBehaviour();

        shopSlot.setHgap(5.0);
        shopSlot.setVgap(5.0);
        goldValue.setText(Integer.toString(world.getCharacter().getGold()));

        ImageView view = null;
        for(Item item : ItemLoader.loadShopItems(itemPositions)){
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
                    break;
                default:
                    return; /* Should never happen */
            }

            Button buyButton = new Button("Buy");
            VBox vbox = new VBox();
            vbox.setAlignment(Pos.CENTER);
            vbox.getChildren().add(view);
            vbox.getChildren().add(new Label("$" + Integer.toString(itemPrices.get(item.getItemType()))));
            vbox.getChildren().add(buyButton);
            setBuyButtonHandler(item.getItemType(), buyButton);
            shopSlot.add(vbox, item.getX(), item.getY());
        }
    }

    
    /**
     * Define a map of item positions on shop
     */
    private void setItemPositions(){
        itemPositions.put(ItemType.SWORD, new Pair<Integer, Integer>(0, 0));
        itemPositions.put(ItemType.STAKE, new Pair<Integer, Integer>(1, 0));
        itemPositions.put(ItemType.STAFF, new Pair<Integer, Integer>(2, 0));
        itemPositions.put(ItemType.ARMOUR, new Pair<Integer, Integer>(0, 1));
        itemPositions.put(ItemType.SHIELD, new Pair<Integer, Integer>(1, 1));
        itemPositions.put(ItemType.HELMET, new Pair<Integer, Integer>(2, 1));
        itemPositions.put(ItemType.HEALTH_POTION, new Pair<Integer, Integer>(1, 2));
    }

    /**
     * Set prices of items
     */
    private void setItemPrices(){
        itemPrices.put(ItemType.SWORD, 10);
        itemPrices.put(ItemType.STAKE, 10);
        itemPrices.put(ItemType.STAFF, 10);
        itemPrices.put(ItemType.ARMOUR, 10);
        itemPrices.put(ItemType.SHIELD, 10);
        itemPrices.put(ItemType.HELMET, 10);
        itemPrices.put(ItemType.HEALTH_POTION, 10);
    }

    /**
     * Set Buy button actions
     */
    private void setBuyButtonHandler(ItemType type, Button buyButton){
        buyButton.setOnAction(new EventHandler<ActionEvent>(){
            //TODO ADD GOLD VALUE
            @Override
            public void handle(ActionEvent event) {
                if(world.getCharacter().getGold() < itemPrices.get(type)){
                    //DOSTH NOT enough money
                    event.consume();
                    return;
                }else if(world.isUnequippedInventoryFull()){
                    //DOSTH
                    event.consume();
                    return;
                }else{
                    world.getCharacter().minusGold(itemPrices.get(type));
                    updateShopGold();
                    Item boughtItem = ItemLoader.loadBoughtItem(type, world.getFirstAvailableSlotForItem().getValue0(), world.getFirstAvailableSlotForItem().getValue1());
                    world.addUnequippedItem(boughtItem);
                    parent.onLoadUnequippedItem(boughtItem);
                }
           }
            
        });
    }

    /**
     * Set the drag behaviour of the shop window
     */
    private void setWindowDragBehaviour(){

        anchorPaneRoot.setOnMousePressed(new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent event) {
                xOffset = shopStage.getX() - event.getScreenX();
                yOffset = shopStage.getY() - event.getScreenY();                
            }
            
        });

        anchorPaneRoot.setOnMouseDragged(new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent event) {
                shopStage.setX(event.getScreenX() + xOffset);
                shopStage.setY(event.getScreenY() + yOffset);
            }
            
        });
    }
    /**
     * Update the shop gold
     */
    private void updateShopGold(){
        goldValue.setText(Integer.toString(world.getCharacter().getGold()));
        shopStage.sizeToScene();
    }

    @FXML
    private void resumeButtonAction(){
        shopStage.close();
        parent.startTimer();
    }
}
