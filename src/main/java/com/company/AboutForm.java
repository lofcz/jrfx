package com.company;
import java.io.IOException;

/**
 *  Class AboutForm
 *  Represents "about" choice from the new game menu
 *
 *  This class is a part of (Ad)venture project
 *
 *@author     Matěj Štágl
 *@version    1.0
 *@created    summer 2019
 */
public class AboutForm extends BaseGUI {

    public AboutForm() throws IOException, InterruptedException {
        Console.hideCursor();
        Console.clear();
        Console.writeFigletColor("vse", new int[] {118, 119, 120, 121, 122, 123, 159});
        Console.moveDown(1);
        Console.writeLineBB(Lang.getLangLump("plaintext", "aboutOn"), Console.TW_FAST);
        Console.writeLineBB(Lang.getLangLump("plaintext", "aboutLibs"), Console.TW_FAST);
        Console.moveDown(2);
        Console.writeLineBB(Lang.getLangLump("plaintext", "authorSignature"), Console.TW_FAST);
        Console.moveDown(3);
        Console.writeLine(Lang.getLangLump("plaintext", "backToMenu"), 240);
        int k = RawConsoleInput.read(true);
        Console.clear();
        Console.showCursor();
        Console.writeFigletColor("logo", new int[] {160, 161, 162, 163, 164, 165, 171});
        Console.moveDown(5);
    }
}
