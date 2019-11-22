package com.company;

/**
 *  Class ItemBarrel
 *  Represents the barrel item
 *
 *  This class is a part of (Ad)venture project
 *
 *@author     Matěj Štágl
 *@version    1.0
 *@created    summer 2019
 */
public class ItemBarrel extends Item {
    public ItemBarrel(String name, String description) {
        super(name, description);
        setSolid(true);
    }

}
