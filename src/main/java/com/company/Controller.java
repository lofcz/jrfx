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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.InputStream;
import java.net.URL;
import java.util.*;

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

        ArrayList<Text> texts = parseBbString(text);

        logId.getChildren().clear();
        logId.getChildren().addAll(texts);
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
