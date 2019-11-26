package com.company;
import javafx.scene.image.Image;

import java.io.IOException;

/**
 *  Class Item
 *  Base class for in-game items
 *
 *  This class is a part of (Ad)venture project
 *
 *@author     Matěj Štágl
 *@version    1.0
 *@created    summer 2019
 */
public class Item extends Entity {
    private String name;
    private String description;
    private String image;
    private String descriptionLoc;
    private boolean solid;
    private Image itemImage;

    public Item(String name, String description) {
        super();
        this.name = name;
        this.description = description;
        this.solid = false;
    }

    public String getNameLoc() {
        return Lang.getLangLump("items", getName());
    }

    public String getDescriptionLoc() {
        return Lang.getLangLump("items", getName() + "Description");
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public Image getItemImage() {
        return itemImage;
    }

    public void setItemImage(Image itemImage) {
        this.itemImage = itemImage;
    }

    public void setSolid(boolean solid) {
        this.solid = solid;
    }

    public boolean getSolid() {
        return solid;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void renderCollected() throws InterruptedException, IOException {
        Console.hideCursor();
        Console.clear();
        Console.writeLine("Získal jsi nový předmět!", 80);

        Console.showInput();
    }
}
