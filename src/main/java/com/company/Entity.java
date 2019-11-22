package com.company;

/**
 *  Class Entity
 *  Base class for all in-game classes, represents "object"
 *
 *  This class is a part of (Ad)venture project
 *
 *@author     Matěj Štágl
 *@version    1.0
 *@created    summer 2019
 */
public class Entity {
    int Id;

    public Entity() {
        Id = Utils.AssignId();
    }
}
