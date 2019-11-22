package com.company;
import java.io.IOException;

/**
 *  Class CommandUse
 *  Represents "use" command
 *
 *  This class is a part of (Ad)venture project
 *
 *@author     Matěj Štágl
 *@version    1.0
 *@created    summer 2019
 */
public class CommandUse extends Command {
    public CommandUse(String name, String clutch, Commands.CommandFormat format) {
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

            }
        }

        Console.readKeyDirect();
    }
}
