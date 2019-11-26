package game1;

import com.company.*;
import javafx.scene.image.Image;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class GameData implements Observer {

    Controller game;
    BaseLevel square = new BaseLevel();
    BaseLevel mtn = new BaseLevel();
    LevelPub pub;

    public GameData(Controller controller) {
        pub = new LevelPub();
        game = controller;
        game.addObserver(this);
    }

    public void start() {
        Image defaultImage = game.loadImage("pub1.jpg");

        pub.setEntranceText("Přišel jsi do hospody");
        pub.setName("Hospoda");
        pub.setLevelImage(defaultImage);
        pub.addExitLevel(square);

        square.setName("Náměstí");
        square.setEntranceText("Dostal jsi se na náměstí");
        square.setLevelImage(game.loadImage("city.gif"));
        square.addExitLevel(pub);
        square.addExitLevel(mtn);

        mtn.setName("Hory");
        mtn.setEntranceText("Vystoupal jsi do hor");
        mtn.setLevelImage(game.loadImage("mtn.gif"));
        mtn.addExitLevel(square);

        Lang.setController(game);
        Lang.ReadLumps("langCS_CZ.json");

        game.setLevelImage(defaultImage);

        ArrayList<DialogLine> intro = new ArrayList<>();

        DialogLine d = new DialogLine();
        d.line = Lang.getLangLump("chapter0", "intro0");
        d.continueLine = Lang.getLangLump("responses", "continue");
        intro.add(d);

        d = new DialogLine();
        d.line = Lang.getLangLump("chapter0", "intro1");
        d.continueLine = Lang.getLangLump("responses", "continue");
        intro.add(d);

        d = new DialogLine();
        d.line = Lang.getLangLump("chapter0", "intro2");
        d.continueLine = Lang.getLangLump("responses", "continue");
        intro.add(d);

        d = new DialogLine();
        d.line = Lang.getLangLump("chapter0", "intro3");
        d.continueLine = Lang.getLangLump("responses", "continue");
        intro.add(d);

        d = new DialogLine();
        d.line = Lang.getLangLump("chapter0", "intro4");
        d.continueLine = Lang.getLangLump("responses", "continue");
        intro.add(d);

        game.setLogCutsene(intro, "intro");
        game.startCutscene();
    }

    @Override
    public void update(Observable o, Object arg) {
        String[] info = arg.toString().split(";");

        if (info.length >= 1) {
            String event = info[0];
            String index = info[1];

            if (event.equals("cutsceneEnded")) {

                if (index.equals("intro")) {
                    game.disableInteractionOptions();
                    game.movePlayerToLocation(square);

                    Item sack = new Item("Vak", "Vak naditým jakýmsi nepořádkem");
                    sack.setItemImage(game.loadImage("sack.png"));

                    Item mug = new Item("Džbán", "Prázdný džbán od piva");
                    mug.setItemImage(game.loadImage("mug.png"));

                    Item key = new Item("Klíč", "Klíč od hospody");
                    key.setItemImage(game.loadImage("key.png"));

                    game.pickItem(key);
                    game.pickItem(sack);
                    game.pickItem(mug);
                }
            }
        }


    }
}
