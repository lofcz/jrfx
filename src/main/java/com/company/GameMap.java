package com.company;
import java.io.IOException;
import java.util.ArrayList;

/**
 *  Class GameMap
 *  Core class of the game world
 *
 *  This class is a part of (Ad)venture project
 *
 *@author     Matěj Štágl
 *@version    1.0
 *@created    summer 2019
 */
public class GameMap {

    public static Inventory inventory = new Inventory();

    public enum Levels {
        pub
    }

    static ArrayList<BaseLevel> levels = new ArrayList<>();

    public static void Initialize() throws InterruptedException, IOException {
        LevelPub levelPub = new LevelPub();

        levels.add(levelPub);

        levelPub.setPlayerHere(true);
        levelPub.entrance();
    }

    public static BaseLevel getLevelWherePlayerIs() {
        return levels.stream().filter(x -> x.getPlayerHere()).findFirst().orElse(null);
    }
}
