package com.company;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
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
    }
}
