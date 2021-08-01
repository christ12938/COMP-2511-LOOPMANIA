package unsw.loopmania;

import java.io.File;
import java.util.EnumMap;

import org.javatuples.Pair;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import unsw.loopmania.Items.Item;
import unsw.loopmania.Loaders.ItemLoader;
import unsw.loopmania.Loaders.ShopLoader;
import unsw.loopmania.Types.ItemType;

public class LoopManiaShopController {

    @FXML
    private GridPane shopBuySlot;

    @FXML
    private GridPane shopSellSlot;

    @FXML
    private GridPane inventorySlot;
    
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
    private Image doggieCoinImage;
    private Image theOneRingImage;
    private Image andurilImage;
    private Image treeStumpImage;

    private EnumMap<ItemType, Pair<Integer, Integer>> itemBuyPositions;
    private EnumMap<ItemType, Pair<Integer, Integer>> itemSellPositions;

    private LoopManiaWorldController parent;
    private LoopManiaWorld world;
    private Stage shopStage;

    private double xOffset;
    private double yOffset;

    private LoopManiaShop shop;

    private MediaPlayer sellItemAudioPlayer;
    private MediaPlayer buyItemAudioPlayer;

    public LoopManiaShopController(LoopManiaWorldController parent, LoopManiaWorld world, Stage shopStage) {

        shop = new LoopManiaShop(world, parent, this);

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
        doggieCoinImage = new Image((new File("src/images/doggiecoin.png")).toURI().toString());
        theOneRingImage = new Image((new File("src/images/the_one_ring.png")).toURI().toString());
        andurilImage = new Image((new File("src/images/anduril_flame_of_the_west.png")).toURI().toString());
        treeStumpImage = new Image((new File("src/images/tree_stump.png")).toURI().toString());

        itemBuyPositions = ShopLoader.loadItemBuyPositions();
        itemSellPositions = ShopLoader.loadItemSellPositions();

        String buyAudio = new File("src/Music/BuyItem.mp3").toURI().toString();
        String sellAudio = new File("src/Music/SellItem.mp3").toURI().toString();

        buyItemAudioPlayer = new MediaPlayer(new Media(buyAudio));
        sellItemAudioPlayer = new MediaPlayer(new Media(sellAudio));

        buyItemAudioPlayer.setVolume(0.15);
        sellItemAudioPlayer.setVolume(0.03);

        this.parent = parent;
        this.world = world;
        this.shopStage = shopStage;
    }

    @FXML
    public void initialize(){

        setWindowDragBehaviour();

        shopBuySlot.setHgap(5.0);
        shopBuySlot.setVgap(5.0);
        shopSellSlot.setHgap(5.0);
        shopSellSlot.setVgap(5.0);
        goldValue.setText(Integer.toString(world.getCharacter().getGold()));

        onLoadShopBuyItems();
        onLoadInventoryItems();
        onLoadShopSellItems();

        /* Found on stack overflow. Request focus later */
        Platform.runLater(() -> anchorPaneRoot.requestFocus());
    }

    /**
     * Set Buy button actions
     */
    private void setBuyButtonHandler(ItemType type, Button buyButton){
        buyButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                String returnedMessage = shop.buyItem(type);
                Color color = null;
                if(returnedMessage.equals(shop.getBuySuccessMessage())){
                    color = Color.GREEN;
                    buyItemAudioPlayer.play();
                    buyItemAudioPlayer.seek(Duration.ZERO);
                }else{
                    color = Color.RED;
                }
                PopUpMessageController popUpMessageController = parent.openPopUpMessageWindow(shopStage, returnedMessage, color, "Back");
                popUpMessageController.setQuitSwitcher(()->{
                    popUpMessageController.getStage().close();
                });
           }     
        });
    }

    /**
     * Set Buy button actions
     */
    private void setSellButtonHandler(ItemType type, Button sellButton){
        sellButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                String returnedMessage = shop.sellItem(type);
                Color color = null;
                if(returnedMessage.equals(shop.getSellSuccessMessage())){
                    sellItemAudioPlayer.play();
                    sellItemAudioPlayer.seek(Duration.ZERO);
                    color = Color.GREEN;
                }else{
                    color = Color.RED;
                }
                PopUpMessageController popUpMessageController = parent.openPopUpMessageWindow(shopStage, returnedMessage, color, "Back");
                popUpMessageController.setQuitSwitcher(()->{
                    popUpMessageController.getStage().close();
                });
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

    public void addInventoryItem(Item item){
        ImageView view = getImageViewByType(item);
        inventorySlot.add(view, item.getX(), item.getY());
        item.shouldExist().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> obervable, Boolean oldValue, Boolean newValue) {
                if(oldValue == true && newValue == false){
                    inventorySlot.getChildren().remove(view);
                }
            }
        });
    }

    /**
     * Update the shop gold
     */
    public void updateShopGold(){
        goldValue.setText(Integer.toString(world.getCharacter().getGold()));
        shopStage.sizeToScene();
    }

    @FXML
    private void resumeButtonAction(){
        shopStage.close();
        parent.startTimer();
    }

    private void onLoadShopBuyItems(){
        for(Item item : ItemLoader.loadShopBuyItems(itemBuyPositions)){
            ImageView view = getImageViewByType(item);
            Button buyButton = new Button("Buy");
            VBox vbox = new VBox();
            vbox.setAlignment(Pos.CENTER);
            vbox.getChildren().add(view);
            vbox.getChildren().add(new Label("$" + shop.getItemBuyPrice(item.getItemType())));
            vbox.getChildren().add(buyButton);
            setBuyButtonHandler(item.getItemType(), buyButton);
            shopBuySlot.add(vbox, item.getX(), item.getY());
        }
    }

    private void onLoadShopSellItems(){
        for(Item item : ItemLoader.loadShopSellItems(itemSellPositions)){
            ImageView view = getImageViewByType(item);
            Button sellButton = new Button("Sell");
            VBox vbox = new VBox();
            vbox.setAlignment(Pos.CENTER);
            vbox.getChildren().add(view);
            vbox.getChildren().add(new Label("$" + shop.getItemSellPrice(item.getItemType())));
            vbox.getChildren().add(sellButton);
            setSellButtonHandler(item.getItemType(), sellButton);
            shopSellSlot.add(vbox, item.getX(), item.getY());
        }
    }

    private void onLoadInventoryItems(){
        // add the empty slot images for the unequipped inventory
        Image inventorySlotImage = new Image((new File("src/images/empty_slot.png")).toURI().toString());
        for (int x=0; x<LoopManiaWorld.unequippedInventoryWidth; x++){
            for (int y=0; y<LoopManiaWorld.unequippedInventoryHeight; y++){
                ImageView emptySlotView = new ImageView(inventorySlotImage);
                inventorySlot.add(emptySlotView, x, y);
            }
        }

        for(Item item : world.getUnequippedInventoryItems()){
            addInventoryItem(item);
        }
    }

    private ImageView getImageViewByType(Item item){
        ImageView view = null;
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
            case DOGGIECOIN:
                view = new ImageView(doggieCoinImage);
                break;
            case THE_ONE_RING:
                view = new ImageView(theOneRingImage);
                break;
            case ANDURIL:
                view = new ImageView(andurilImage);
                break;
            case TREE_STUMP:
                view = new ImageView(treeStumpImage);
                break;
            default:
                view = null; /* Should never happen */
        }
        return view;
    }
}

