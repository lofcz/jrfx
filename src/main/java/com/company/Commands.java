package com.company;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *  Class CommandTake
 *  Handles input, parses commands and arguments
 *
 *  This class is a part of (Ad)venture project
 *
 *@author     Matěj Štágl
 *@version    1.0
 *@created    summer 2019
 */
public class Commands {

    public enum CommandFormat {
        noParams,
        oneAfter,
        use
    }

    private static String COMMAND_TAKE_UNIFICATOR = Lang.getLangLump("commands", "take");

    static CommandObserve commandObserve = null;
    static CommandTake commandTake = null;
    static ArrayList<Command> commands = new ArrayList<Command>();

    /**
     * Starts the system, filling commands ArrayList with new command instances
     *
     */
    public static void initialize() {
        commandObserve = new CommandObserve(Lang.getLangLump("commands", "observe"), "", CommandFormat.noParams);
        commandObserve.setCanBeUsed(true);

        commandTake = new CommandTake(Lang.getLangLump("commands", "take"), "", CommandFormat.oneAfter);
        commandTake.setCanBeUsed(true);

        commands.add(commandObserve);
        commands.add(commandTake);
    }

    /**
     * Checks whether input starts with any known command
     *
     *@param command Input command
     *@return Match candidate found
     */
    public static boolean existingCommand(String command) {

        for (Command cmd : commands) {
            if (cmd.getFormat() != CommandFormat.use) {
                if (command.startsWith(cmd.getName())) {
                    return true;
                }
            }
            else {
                if (command.startsWith(cmd.getName()) && command.contains(cmd.getClutch())) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Returns predicted command match
     *
     *@param command Input command
     *@return Match candidate
     */
    public static Command predictedCommand(String command) {

        for (Command cmd : commands) {
            if (cmd.getFormat() != CommandFormat.use) {
                if (command.startsWith(cmd.getName())) {
                    return cmd;
                }
            }
            else {
                if (command.startsWith(cmd.getName()) && command.contains(cmd.getClutch())) {
                    return cmd;
                }
            }
        }

        return null;
    }

    /**
     * Sanitizes item names
     *
     *@param command Input command
     *@return Sanitized command
     */
    private static String SanitizeItems(String command) {
        command = command.replace("oslizlý korbel", "dirtyJug");
        return command;
    }

    public static void throwCommandError(String command, int modeIndex) throws InterruptedException, IOException {
        Console.hideCursor();
        Console.writeLine();

        String[] errorLumpMapping = {"unknownCommand", "lockedCommand", "tooManyArguments", "internalError", "tooFewArguments", "itemNotPresent"};

        String error = Lang.getLangLump("errors", errorLumpMapping[modeIndex]);
        error = error.replace("{0}", command);
        Console.writeLine(error, 196);

        Console.writeLine(Lang.getLangLump("plaintext", "continue"), 8);

        Console.readKeyDirect();

        Console.clearCurrentLine();
        Console.moveUp(1);
        Console.clearCurrentLine();
        Console.moveUp(1);
        Console.clearCurrentLine();
        Console.moveUp(1);
        Console.clearCurrentLine();
        Console.moveUp(1);
        Console.clearCurrentLine();

        Console.showCursor();

        requestCommand();
    }

    public static void requestCommand() throws InterruptedException, IOException {
        Console.showInput();
        Console.showCursor();
        Console.write("> ", 8);
        String command = Console.readLine();

        if (command.equals(":q!")) {
            System.exit(0);
        }

        // Is there an appropriate command defined?
        if (!existingCommand(command)) {
            throwCommandError(command, 0);
        }
        else {

            Command cmd = predictedCommand(command);

            if (!cmd.getCanBeUsed()) {
                // Can the command be used?
                throwCommandError(command, 1);
            }
            else {
                // Sanitize item names
                command = SanitizeItems(command);

                // Get parameters
                String[] params = commandGetParams(command, cmd);

                if (cmd.getUnificator() == COMMAND_TAKE_UNIFICATOR) {

                    // Are there any unexpected parameters
                    if (params.length > 1) {
                        throwCommandError(command, 2);
                    }
                    else if (params.length == 0) {
                        throwCommandError(command, 4);
                    }
                    else {
                        // Looking good
                        Command retrievedCmd = commands.stream().filter(x -> x.getName().equals(cmd.getName())).findFirst().orElse(null);

                        if (retrievedCmd == null) {
                            throwCommandError(command, 3);
                        }
                        else {
                            retrievedCmd.perform(params, command);
                        }

                    }
                }
                else {

                    // Are there any unexpected parameters
                    if (params.length > 0) {

                        for (int i = 0; i < params.length; i++) {
                            Console.writeLine(params[i], 65);
                        }
                        throwCommandError(command, 2);
                    }
                    else {
                        // Looking good
                        Command retrievedCmd = commands.stream().filter(x -> x.getName().equals(cmd.getName())).findFirst().orElse(null);

                        if (retrievedCmd == null) {
                            throwCommandError(command, 3);
                        }
                        else {
                            retrievedCmd.perform(params, command);
                        }

                    }
                }
            }


        }
    }

    private static String[] removeEmptyElements(String[] array) {
            return Arrays.stream(array).filter(x -> !x.equals("")).toArray(String[]::new);
    }

    private static String[] commandGetParams(String command, Command knownCore) {
        command = command.replaceAll("\\s{2,}", " ").trim(); // Remove double and trailing spaces
        String cleaned = command.replace(knownCore.getName() + " ", "");
        cleaned = cleaned.replace(knownCore.getName(), "");
        return removeEmptyElements(cleaned.split(" "));
    }
}
