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

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private VBox locationList;
    @FXML
    private VBox itemList;
    @FXML
    private VBox npcList;
    @FXML
    private VBox inventory;
    @FXML
    private Label healthLabel;
    @FXML
    private Label moneyLabel;

    @FXML
    private MenuItem newGameMenuItem;

    @FXML
    private MenuItem endGame;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private VBox logger;

    @FXML
    private ComboBox<String> commandsBox;

    public ImageView locationImage;

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {

        commandsBox.getItems().setAll("Apple", "Orange", "Pear");
        commandsBox.setDisable(true);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/scene.fxml"));

        Image itemImage = new Image(getClass().getClassLoader().getResourceAsStream("\\" + "pub1.jpg"));
        locationImage.setImage(itemImage);
        //Image img = new Image(getClass().getResourceAsStream("\\pub1.jpg"));

    }
}
