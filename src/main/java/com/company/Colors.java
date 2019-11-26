package com.company;

import javafx.scene.paint.Color;

/**
 *  Class Colors
 *  Used to render game in 9 colors mode
 *
 *  This class is a part of (Ad)venture project
 *
 *@author     Matěj Štágl
 *@version    1.0
 *@created    summer 2019
 */
public class Colors {

    public enum colors {
        black,
        red,
        green,
        yellow,
        blue,
        purple,
        cyan,
        white
    }

    private static boolean C_ENABLED = true;
    private static final String C_RESET = "\u001B[0m";
    private static final String C_BLACK = "\u001B[30m";
    private static final String C_RED = "\u001B[31m";
    private static final String C_GREEN = "\u001B[32m";
    private static final String C_YELLOW = "\u001B[33m";
    private static final String C_BLUE = "\u001B[34m";
    private static final String C_PURPLE = "\u001B[35m";
    private static final String C_CYAN = "\u001B[36m";
    private static final String C_WHITE = "\u001B[37m";

    public static String c_reset() {
        return C_ENABLED ? C_RESET : "";
    }

    public static String c_black() {
        return C_ENABLED ? C_BLACK : "";
    }

    public static String c_red() {
        return C_ENABLED ? C_RED : "";
    }

    public static String c_green() {
        return C_ENABLED ? C_GREEN : "";
    }

    public static String c_yellow() {
        return C_ENABLED ? C_YELLOW : "";
    }

    public static String c_blue() {
        return C_ENABLED ? C_BLUE : "";
    }

    public static String c_purple() {
        return C_ENABLED ? C_PURPLE : "";
    }

    public static String c_cyan() {
        return C_ENABLED ? C_CYAN : "";
    }

    public static String c_white() {
        return C_ENABLED ? C_WHITE : "";
    }


    public static void ToggleColors() {
        C_ENABLED = !C_ENABLED;
    }

    public static void EnableColors() {
        C_ENABLED = true;
    }

    public static void EnableColors(boolean enable) {
        C_ENABLED = enable;
    }

    public static boolean GetColorsEnabled() {
        return C_ENABLED;
    }

    public static Color Xterm255ToColor(int color) {


        if (color == 0) {
            return Color.rgb(0, 0, 0);
        }

        if (color == 1) {
            return Color.rgb(128, 0, 0);
        }

        if (color == 2) {
            return Color.rgb(0, 128, 0);
        }

        if (color == 3) {
            return Color.rgb(128, 128, 0);
        }

        if (color == 4) {
            return Color.rgb(0, 0, 128);
        }

        if (color == 5) {
            return Color.rgb(128, 0, 128);
        }

        if (color == 6) {
            return Color.rgb(0, 128, 128);
        }

        if (color == 7) {
            return Color.rgb(192, 192, 192);
        }

        if (color == 8) {
            return Color.rgb(128, 128, 128);
        }

        if (color == 9 || color == 196) {
            return Color.rgb(255, 0, 0);
        }

        if (color == 10) {
            return Color.rgb(0, 255, 0);
        }

        if (color == 11) {
            return Color.rgb(255, 255, 0);
        }

        if (color == 12) {
            return Color.rgb(0, 0, 255);
        }

        if (color == 13) {
            return Color.rgb(255, 0, 255);
        }

        if (color == 14) {
            return Color.rgb(0, 255, 255);
        }

        if (color == 15) {
            return Color.rgb(255, 255, 255);
        }

        if (color == 118) {
            return Color.rgb(135, 255, 0);
        }


        return Color.rgb( 0,  0,  0);
    }
}

