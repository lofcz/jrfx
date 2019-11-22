package com.company;
import java.io.IOException;

/**
 *  Class CommandTake
 *  Represents "take" command
 *
 *  This class is a part of (Ad)venture project
 *
 *@author     Matěj Štágl
 *@version    1.0
 *@created    summer 2019
 */
public class CommandTake extends Command {

    public CommandTake(String name, String clutch, Commands.CommandFormat format) {
        super(name, clutch, format);
    }

    @Override
    public void perform(String[] params, String originalCommand) throws InterruptedException, IOException {

        BaseLevel levelWithPlayer = GameMap.getLevelWherePlayerIs();
        String toTake = params[0];

        if (levelWithPlayer == null) {
            // error player must be somewhere
        }
        else {
            if (levelWithPlayer instanceof LevelPub) {
                LevelPub levelUnboxed = (LevelPub)levelWithPlayer;

                if (toTake.equals("dirtyJug")) {
                    if (levelUnboxed.itemsContain("dirtyJug")) {

                        levelUnboxed.itemRemove("dirtyJug");
                        levelUnboxed.setState(1);

                        Console.hideCursor();
                        Console.clear();
                        Console.writeLineBBWrap(Lang.getLangLump("items", "cleanJugObtain"), Console.TW_FAST);
                        Console.moveDown(3);
                        Console.writeLine(Lang.getLangLump("plaintext", "continue"), 240);
                        Console.readKeyDirect();
                        Console.showCursor();

                        levelUnboxed.afterJugCleaned();

                    } else {
                        Console.hideCursor();
                        Console.clear();
                        Console.writeLineBBWrap(Lang.getLangLump("items", "cleanJugObtainedAlready"), Console.TW_FAST);
                        Console.moveDown(3);
                        Console.writeLine(Lang.getLangLump("plaintext", "continue"), 240);
                        Console.readKeyDirect();
                        Console.showCursor();

                        levelUnboxed.afterJugCleaned();
                    }
                }
                else {
                   Commands.throwCommandError(toTake, 5);
                }
            }
        }

        Console.readKeyDirect();
    }
}
