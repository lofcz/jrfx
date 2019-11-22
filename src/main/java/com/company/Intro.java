package com.company;
import java.io.IOException;

/**
 *  Class Intro
 *  Chapter 0 of the game, introduces player to the game
 *
 *  This class is a part of (Ad)venture project
 *
 *@author     Matěj Štágl
 *@version    1.0
 *@created    summer 2019
 */
public class Intro {
    public static void play() throws InterruptedException, IOException {
        Console.hideCursor();
        Console.clear();
        Console.writeLineBBWrap(Lang.getLangLump("chapter0", "intro0"), Console.TW_FAST);
        Console.moveDown(2);
        Console.writeLineBBWrap(Lang.getLangLump("chapter0", "intro1"), Console.TW_FAST);
        Console.moveDown(2);
        Console.writeLineBBWrap(Lang.getLangLump("chapter0", "intro2"), Console.TW_FAST);
        Console.moveDown(2);
        Console.writeLineBBWrap(Lang.getLangLump("chapter0", "intro3"), Console.TW_FAST);
        Console.moveDown(2);
        Console.writeLineBBWrap(Lang.getLangLump("chapter0", "intro4"), Console.TW_FAST);
        Console.moveDown(3);
        Console.writeLine(Lang.getLangLump("plaintext", "continue"), 240);
        Console.readKeyDirect();
        Console.showCursor();

        Console.clear();
        Console.showInput();

        GameMap.Initialize();

    }
}
