package com.company;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.InputStream;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class Controller implements Initializable {

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

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {

        commandsBox.getItems().setAll("Apple", "Orange", "Pear");
        commandsBox.setDisable(true);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/scene.fxml"));
    }

    public void setLevelImage(Image img) {
        locationImage.setImage(img);
    }

    public InputStream loadResource(String path) {
        return getClass().getClassLoader().getResourceAsStream("\\" + path);
    }

    public Image loadImage(String path) {
        return new Image(loadResource(path));
    }

    public void setLogText(String text) {

        Text t = new Text(text);
        t.setUnderline(true);
        logId.getChildren().clear();
        logId.getChildren().addAll(t);
       // logId.setText(text);
    }
}
