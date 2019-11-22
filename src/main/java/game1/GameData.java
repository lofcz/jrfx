package game1;

import com.company.BaseLevel;
import com.company.Controller;
import javafx.scene.image.Image;

public class GameData {

    Controller game;

    public GameData(Controller controller) {
        LevelPub pub = new LevelPub();
        game = controller;
    }

    public void start() {

        Image defaultImage = game.loadImage("pub1.jpg");
        game.setLevelImage(defaultImage);
        game.setLogText("<b>Lorem</b> ipsum dolor sit amet, consectetuer adipiscing elit. Nunc tincidunt ante vitae massa. Phasellus enim erat, vestibulum vel, aliquam a, posuere eu, velit. Nam sed tellus id magna elementum tincidunt. Nullam at arcu a est sollicitudin euismod. In convallis. Nullam at arcu a est sollicitudin euismod. Integer pellentesque quam vel velit. In laoreet, magna id viverra tincidunt, sem odio bibendum justo, vel imperdiet sapien wisi sed libero. Etiam dui sem, fermentum vitae, sagittis id, malesuada in, quam. Mauris suscipit, ligula sit amet pharetra semper, nibh ante cursus purus, vel sagittis velit mauris vel metus. Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Nunc tincidunt ante vitae massa. Phasellus enim erat, vestibulum vel, aliquam a, posuere eu, velit. Nam sed tellus id magna elementum tincidunt. Nullam at arcu a est sollicitudin euismod. In convallis. Nullam at arcu a est sollicitudin euismod. Integer pellentesque quam vel velit. In laoreet, magna id viverra tincidunt, sem odio bibendum justo, vel imperdiet sapien wisi sed libero. Etiam dui sem, fermentum vitae, sagittis id, malesuada in, quam. Mauris suscipit, ligula sit amet pharetra semper, nibh ante cursus purus, vel sagittis velit mauris vel metus.");
    }
}
