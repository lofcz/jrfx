package com.company;
import java.io.IOException;

import game1.GameData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *  Class Main
 *  Entry point of the game
 *
 *  This class is a part of (Ad)venture project
 *
 *@author     Matěj Štágl
 *@version    1.0
 *@created    summer 2019
 */
public class Main extends Application {

    static boolean alive = true;

    public static void main(String[] args) throws IOException, InterruptedException {


        if (args.length > 0)
        {
            if (args[0] == "nogui") {
                // Prepare the game window
                // ----------------------------
                //  Utils.HookWindows();
                 Console.setTerminalSize(120, 30);
                 Console.setTitle("(Ad)ventura");
                 Lang.ReadLumps("langCS_CZ.json");

                // Rendering engine settings
                // ----------------------------
                  Console.TW_ENABLED = false;
                Console.CL_ENABLED = true;


                // Start the game
                // ----------------------------
                MenuOption[] options = new MenuOption[] {new MenuOption(Lang.getLangLump("mainMenu", "newGame")), new MenuOption(Lang.getLangLump("mainMenu", "options")), new MenuOption(Lang.getLangLump("mainMenu", "about")), new MenuOption(Lang.getLangLump("mainMenu", "end"))};
                 ChoiceMenu mainMenu = new ChoiceMenu(options, "MainMenu");
                 Commands.initialize();


                // Game loop
                // ----------------------------
                  while(alive) {
                      mainMenu.StartRender();
                 }
            }
        }
        else {
            launch(args);
        }




    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/scene.fxml"));
        Parent rootComponent = loader.load();

        Scene scene = new Scene(rootComponent);
        primaryStage.setScene(scene);

        Controller controller = loader.getController();

        primaryStage.setTitle("stam14 - 1. semestrální práce 4IT115");
        primaryStage.setResizable(false);

        String css = getClass().getResource("/styles.css").toExternalForm();
        scene.getStylesheets().clear();
        scene.getStylesheets().add(css);

//        InputStream iconStream = getClass().getResourceAsStream("/ikona.png");
//        Image icon = new Image(iconStream);
        //primaryStage.getIcons().add(icon);

        GameData gd = new GameData(controller);
        gd.start();

        primaryStage.show();
    }
}
