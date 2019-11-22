package com.company;
import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 *  Class BaseLevel
 *  Defines shared attributes and methods game levels can use
 *
 *  This class is a part of (Ad)venture project
 *
 *@author     Matěj Štágl
 *@version    1.0
 *@created    summer 2019
 */
public class BaseLevel {
    private int state;
    private boolean playerHere;

    public int getState() {
        return state;
    }
    public void setState(int state) {
        this.state = state;
    }

    public ArrayList<Item> itemsInRoom = new ArrayList<>();

    public BaseLevel() {
        playerHere = false;
    }

    public boolean getPlayerHere() {
        return  playerHere;
    }

    public void setPlayerHere(boolean playerHere) {
        this.playerHere = playerHere;
    }

    public ArrayList<BaseLevel> exitLevels = new ArrayList<>();

    public Image levelImage;

    public void addExitLevel(BaseLevel level) {
        exitLevels.add(level);
    }

    public ArrayList<BaseLevel> getExitLevels() {
        return exitLevels;
    }

    public void setLevelImage(Image img) {
        levelImage = img;
    }

    public Image getLevelImage() {
        return levelImage;
    }

    /**
     * Returns whether an item is present in the inventory
     *
     *@param name Name of the item to search for
     *@return Is the item present?
     */
    public boolean itemsContain(String name) {
        return itemsInRoom.stream().filter(x -> x.getName().equals(name)).findFirst().orElse(null) != null;
    }

    /**
     * Returns an item from the inventory
     *
     *@param name Name of the item to search for
     *@return Item or null in case no such item is present
     */
    public Item itemGet(String name) {
        return itemsInRoom.stream().filter(x -> x.getName().equals(name)).findFirst().orElse(null);
    }

    /**
     * Removes specified item from the inventory
     *
     *@param item Item to remove
     */
    public void itemRemove(Item item) {
        itemsInRoom.remove(item);
    }


    /**
     * Removes specified item from the inventory, based on string selector
     *
     *@param item Item to remove
     */
    public void itemRemove(String item) {
        Item itm = itemsInRoom.stream().filter(x -> x.getName().equals(item)).findFirst().orElse(null);

        if (itm != null) {
            itemRemove(itm);
        }
    }
}
