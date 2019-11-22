package com.company;
import java.io.IOException;

/**
 *  Class ChoiceMenu
 *  Used to render a choice menu
 *
 *  This class is a part of (Ad)venture project
 *
 *@author     Matěj Štágl
 *@version    1.0
 *@created    summer 2019
 */
public class ChoiceMenu extends BaseGUI implements iForm {

    private MenuOption[] options;
    private int selected;
    private boolean first;
    private boolean alive;
    private String unificator;

    @Override
    public void Render() throws IOException, InterruptedException {

        for (int i = 0; i < options.length; i++) {

            String prefix = i == selected ? "> " : "";
            int color = options[i].getColor();

            if (i == selected) {
                color = 226;
            }

            Console.writeLine(prefix + options[i].getText(), color);
        }

        int k = RawConsoleInput.read(true);

        if (k == 'w') {
            selected--;
        }

        if (k == 's') {
            selected++;
        }

        if (k == 'q') {
            System.exit(0);
        }

        if (k == 13) {
            ConfirmChoice();
        }

        for (int i = 0; i < options.length; i++) {
            Console.moveUp(1);
            Console.clearCurrentLine();
        }

        if (first){
            first = false;
            Console.moveUp(1);
            Console.clearCurrentLine();
        }

        if (selected < 0) {
            selected = options.length - 1;
        }

        if (selected > options.length - 1) {
            selected = 0;
        }

        if (alive)
        {
            Render();
        }
    }

    @Override
    public void StartRender() throws IOException, InterruptedException {

        Console.hideCursor();

        if (unificator == "MainMenu" && first) {
            Console.writeFigletColor("logo", new int[] {160, 161, 162, 163, 164, 165, 171});
            Console.moveDown(1);
        }

        if (first) {
            Console.writeLine(Lang.getLangLump("plaintext", "choiceMenuTooltip"), 111);
        }
        Render();
    }

    public ChoiceMenu(MenuOption[] options, String unificator) {
        this.options = options;
        this.unificator = unificator;
        this.alive = true;
        this.first = true;
    }

    /**
     * Executes actions after selection has been confirmed
     *
     */
    private void ConfirmChoice() throws IOException, InterruptedException {
        alive = false;
        Console.showCursor();

        if (unificator == "MainMenu") {
            if (selected == 0) {
                // new game
                Intro.play();
            }
            else if (selected == 1) {
                // settings
            }
            else if (selected == 2) {
                // about
                AboutForm about = new AboutForm();
            }
            else if (selected == 3) {
                // end
                System.exit(0);
            }
        }
    }
}
