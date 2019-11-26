package com.company;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.concurrent.Callable;

public class Controller extends Observable implements Initializable  {

    @FXML
    public VBox locationList;
    @FXML
    public VBox itemList;
    @FXML
    public VBox npcList;
    @FXML
    public VBox inventory;
    @FXML
    public Label healthLabel;
    @FXML
    public Label moneyLabel;

    @FXML
    public MenuItem newGameMenuItem;

    @FXML
    public MenuItem endGame;

    @FXML
    public MenuItem helpMenuItem;

    @FXML
    public VBox logger;

    @FXML
    public ComboBox<String> commandsBox;

    public ImageView locationImage;

    @FXML
    public TextFlow logId;
    public AnchorPane pane;
    public VBox locationBox;
    public VBox inventoryBox;

    ArrayList<String> defaultInteractions;

    String interactionMode = "invoke";
    String cutsceneName = "";
    boolean skipSelection = false;

    BaseLevel currentLevel = null;

    Inventory inv = null;


    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {

        commandsBox.setDisable(true);
        inv = new Inventory();

        commandsBox.valueProperty().addListener((ov, t, t1) -> {

            if (skipSelection) {
                skipSelection = false;
            }
            else {
                if (commandsBox.getValue() != null) {
                    handleInteraction(t1);
                }
            }
        });

        helpMenuItem.setOnAction(x -> {
            renderHelp();

          /*  Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Nápověda");
            alert.setHeaderText("Nápověda ke hře (Ad)ventura");
            alert.setContentText("I have a great message for you!");

            alert.showAndWait();*/
        });

        lines = new LinkedList<DialogLine>();
        defaultInteractions = new ArrayList<>();
        defaultInteractions.add("Interakce");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/scene.fxml"));
    }

    public void handleInteraction(String text) {

        interactionsClear();

        if (interactionMode == "cutscene") {
            if (!pushCutsene()) {
                interactionMode = "invoke";

                setChanged();
                notifyObservers("cutsceneEnded;" + cutsceneName);
            }
        }
    }

    public void movePlayerToLocation(BaseLevel level) {
        currentLevel = level;
        displayCurrentLocation();
    }

    public void displayCurrentLocation() {
        setLevelImage(currentLevel.getLevelImage());
        propagateExitLocations(currentLevel);
        setLogText(currentLevel.getEntranceText());
    }

    public void propagateExitLocations(BaseLevel level) {
        ArrayList<BaseLevel> exits = level.exitLevels;

        ArrayList<HBox> locations = new ArrayList<>();

        for (BaseLevel exit : exits) {

            HBox hb = new HBox();
            ImageView imgView = new ImageView();
            Image img = exit.getLevelImage();
            Label lbl = new Label();

            imgView.setImage(img);
            imgView.setFitHeight(40);
            imgView.setFitWidth(40);

            lbl.setText(exit.getName());
            lbl.setPrefWidth(120);

            hb.setSpacing(10);
            hb.getChildren().setAll(imgView, lbl);
            hb.getStyleClass().add("basicBox");


            hb.setOnMouseClicked(event -> {
                movePlayerToLocation(exit);
            });

            locations.add(hb);
        }

        locationBox.getChildren().setAll(locations);
    }


    public void setLevelImage(Image img) {
        locationImage.setImage(img);
    }

    public InputStream loadResource(String path) {
        return getClass().getClassLoader().getResourceAsStream("\\" + path);
    }

    private Queue<DialogLine> lines;

    public void setLogCutsene(ArrayList<DialogLine> l, String cutsceneName) {
        lines.addAll(l);
        this.cutsceneName = cutsceneName;
    }

    public void interactionsClear() {

        skipSelection = true;
        commandsBox.getItems().setAll(defaultInteractions);

        // #SO fixed
        Platform.runLater(() -> {
            commandsBox.setValue(null);
        });
    }

    public void startCutscene() {

            interactionMode = "cutscene";
            pushCutsene();
    }

    boolean pushCutsene() {
        int s = lines.size();

        if (lines.size() > 0) {
            DialogLine l = lines.poll();
            setLogText(l.line);
            ArrayList<String> o = new ArrayList<String>();
            o.add(l.continueLine);

            setInteractionOptions(o);
            enableInteractionOptions();
        }

        return s > 0;
    }

    public void setInteractionOptions(List<String> options) {
        commandsBox.getSelectionModel().clearSelection();
        commandsBox.getItems().setAll(options);
    }

    public void pickItem(Item item) {
        inv.addItem(item);
        propagateItems();
    }

    public void propagateItems() {
        ArrayList<Item> items = inv.getItems();
        ArrayList<HBox> locations = new ArrayList<>();

        for (Item item : items) {

            HBox hb = new HBox();
            ImageView imgView = new ImageView();
            Image img = item.getItemImage();
            Label lbl = new Label();

            imgView.setImage(img);
            imgView.setFitHeight(40);
            imgView.setFitWidth(40);

            lbl.setText(item.getName());
            lbl.setPrefWidth(120);

            hb.setSpacing(10);
            hb.getChildren().setAll(imgView, lbl);
            hb.getStyleClass().add("basicBox");
            hb.setOnMouseClicked(event -> {

            });

            locations.add(hb);
        }

        inventoryBox.getChildren().setAll(locations);
    }

    public void enableInteractionOptions() {
        commandsBox.setDisable(false);
    }

    public void disableInteractionOptions() {
        commandsBox.setDisable(true);
    }

    public Image loadImage(String path) {
        return new Image(loadResource(path));
    }

    public void setLogText(String text) {

        ArrayList<Text> texts = parseBbString(text);

        logId.getChildren().clear();
        logId.getChildren().addAll(texts);
    }

    private void renderHelp() {
        WebView browser = new WebView();
        WebEngine webEngine = browser.getEngine();
        File f = new File(getClass().getClassLoader().getResource("napoveda.html").getFile());
        webEngine.load(f.toURI().toString());
        this.logger.getChildren().clear();
        this.logger.getChildren().add(browser);
    }

    private ArrayList<Text> parseBbString(String line) {

        ArrayList<Text> tokens = new ArrayList<>();
        Text currentToken = new Text();

        boolean parsingTag = false;
        String currentTag = "";
        String currentText = "";

        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);

            if (ch == '[') {
                parsingTag = true;

                if (i < line.length() + 1) {
                    if (line.charAt(i + 1) != '/') {
                        if (currentText != "") {
                            currentToken = new Text();
                            currentToken.setText(currentText);
                            tokens.add(currentToken);
                        }
                    }
                }
            }
            else if (ch == ']') {
                parsingTag = false;

                if (!currentTag.equals("/")) {
                    currentToken = new Text();
                    currentToken.setFill(Colors.Xterm255ToColor(Integer.parseInt(currentTag)));
                }
                else {
                    currentToken.setText(currentText);
                    tokens.add(currentToken);
                }

                currentTag = "";
                currentText = "";
            }
            else if (!parsingTag) {
                currentText += String.valueOf(ch);
            }
            else {
                currentTag += String.valueOf(ch);
            }
        }

        if (currentText != "") {
            currentToken = new Text();
            currentToken.setText(currentText);
            tokens.add(currentToken);
        }

       return tokens;
    }
}
