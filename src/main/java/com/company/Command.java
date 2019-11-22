package com.company;
import java.io.IOException;

/**
 *  Class Command
 *  Base class for commands
 *
 *  This class is a part of (Ad)venture project
 *
 *@author     Matěj Štágl
 *@version    1.0
 *@created    summer 2019
 */
public class Command {
    private boolean canBeUsed;
    private Commands.CommandFormat format;
    private String name;
    private String clutch;
    private String unificator;

    public Command(String name, String clutch, Commands.CommandFormat format) {
        this.format = format;
        this.name = name;
        this.clutch = clutch;
        this.canBeUsed = false;
        this.unificator = name;
    }

    public Commands.CommandFormat getFormat() {
        return format;
    }

    public String getName() {
        return name;
    }

    public String getClutch() {
        return clutch;
    }

    public String getUnificator() {return unificator;}

    public void setUnificator(String unificator) {
        this.unificator = unificator;
    }

    public void setCanBeUsed(boolean canBeUsed) {
        this.canBeUsed = canBeUsed;
    }

    public boolean getCanBeUsed() {
        return canBeUsed;
    }

    public void perform(String[] params, String originalCommand) throws InterruptedException, IOException {

    }
}
