package com.company;
import java.io.IOException;

/**
 *  Class LevelPub
 *  First level of the game
 *
 *  This class is a part of (Ad)venture project
 *
 *@author     Matěj Štágl
 *@version    1.0
 *@created    summer 2019
 */
public class LevelPub extends BaseLevel implements iLevel {

    public LevelPub() {
        super();
        setState(0);

        Item dirtyJug = new Item("dirtyJug", "dirtyJugDescription");
        itemsInRoom.add(dirtyJug);
    }

    @Override
    public void observe() {
        if (getState() == 0) {
            Console.writeLine("Stav levelu je 0");
        }

        if (getState() == 1) {
            Console.writeLine("Stav levelu je 1");
        }
    }

    @Override
    public void take() throws InterruptedException, IOException {
        if (itemsContain("dirtyJug")) {

            Item itemToTake = itemGet("dirtyJug");
            GameMap.inventory.addItem(itemToTake);
            itemRemove(itemToTake);
            itemToTake.renderCollected();
        }
    }

    @Override
    public void entrance() throws InterruptedException, IOException {
        Console.hideCursor();
        Console.clear();
        Console.writeLineBBWrap(Lang.getLangLump("chapter1", "intro0"), Console.TW_FAST);
        Console.moveDown(2);
        Console.writeLineBBWrap(Lang.getLangLump("chapter1", "intro1"), Console.TW_FAST);
        Console.moveDown(2);
        Console.writeLineBBWrap(Lang.getLangLump("chapter1", "intro2"), Console.TW_FAST);
        Console.moveDown(2);
        Console.writeLineBBWrap(Lang.getLangLump("chapter1", "intro3"), Console.TW_FAST);
        Console.moveDown(3);
        Console.writeLine(Lang.getLangLump("plaintext", "inputExpected"), 240);
        Console.showInput();

        Commands.requestCommand();
    }

    public void afterJugCleaned() throws InterruptedException, IOException  {
        Console.hideCursor();
        Console.clear();
        Console.writeLineBBWrap(Lang.getLangLump("chapter1", "observe1"), Console.TW_FAST);
        Console.showInput();

        Commands.requestCommand();
    }

    public boolean canGiveBeer() {
        return GameMap.inventory.hasItem("beer");
    }

    public void giveBeer() {
        GameMap.inventory.removeItem("beer");
        setState(Utils.LEVEL_COMPLETED_CONSTANT);
    }
}
