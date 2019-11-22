package com.company;

/**
 *  Class MenuOption
 *  Encapsulates items of a ChoiceMenu instance
 *
 *  This class is a part of (Ad)venture project
 *
 *@author     Matěj Štágl
 *@version    1.0
 *@created    summer 2019
 */
public class MenuOption {
    private String text;
    private int color;

    public MenuOption(String text, int color){
        this.text = text;
        this.color = color;
    }

    public MenuOption(String text) {
        this.text = text;
        color = 8;
    }

    public String getText() {
        return text;
    }

    public int getColor() {
        return color;
    }
}
