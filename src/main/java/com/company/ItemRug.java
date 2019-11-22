package com.company;

/**
 *  Class ItemRug
 *  Represents the rug item
 *
 *  This class is a part of (Ad)venture project
 *
 *@author     Matěj Štágl
 *@version    1.0
 *@created    summer 2019
 */
public class ItemRug extends Item {
    public ItemRug(String name, String description) {
        super(name, description);
        setImage("dirtyJug.txt");
    }
}
