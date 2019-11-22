package com.company;
import java.io.IOException;

/**
 *  Class CommandObserve
 *  Represents "observe" command
 *
 *  This class is a part of (Ad)venture project
 *
 *@author     Matěj Štágl
 *@version    1.0
 *@created    summer 2019
 */
public class CommandObserve extends Command {

    public CommandObserve(String name, String clutch, Commands.CommandFormat format) {
        super(name, clutch, format);
    }

    /**
     * Performs "observe" action
     *
     *@param params Input arguments of the command
     *@param originalCommand params Input arguments of the command
     */
    @Override
    public void perform(String[] params, String originalCommand) throws InterruptedException, IOException {

        BaseLevel levelWithPlayer = GameMap.getLevelWherePlayerIs();

        if (levelWithPlayer == null) {
            // error player must be somewhere
        }
        else {
            if (levelWithPlayer instanceof LevelPub) {
                LevelPub levelUnboxed = (LevelPub)levelWithPlayer;

                if (levelUnboxed.getState() == 0) {
                    Console.hideCursor();
                    Console.clear();
                    Console.writeLineBBWrap(Lang.getLangLump("chapter1", "observe0"), Console.TW_FAST);
                    Console.showInput();
                    Console.showCursor();

                    Commands.requestCommand();
                }

                if (levelUnboxed.getState() == 1) {
                    Console.hideCursor();
                    Console.clear();
                    Console.writeLineBBWrap(Lang.getLangLump("chapter1", "observe1"), Console.TW_FAST);
                    Console.showInput();
                    Console.showCursor();

                    Commands.requestCommand();
                }
            }
        }
    }
}
