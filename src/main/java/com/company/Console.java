package com.company;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 *  Class Console
 *  Represents main IO class, rendering engine of the game
 *
 *  This class is a part of (Ad)venture project
 *
 *@author     Matěj Štágl
 *@version    1.0
 *@created    summer 2019
 */
public class Console {
    private static Scanner scan = new Scanner(System.in);
    private static boolean cursorOn = true;

    public static final int TW_SLOW = 150;
    public static final int TW_MEDIUM = 100;
    public static final int TW_FAST = 50;
    public static final int WINDOW_WIDTH = 120;
    public static final int WINDOW_HEIGHT = 30;
    public static boolean TW_ENABLED = true;
    public static boolean CL_ENABLED = true;

    public static void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void writeLine() {System.out.println();}

    public static void writeLine(String line) {
        System.out.println(line);
    }

    public static void writeLine(String line, Colors.colors color) {
        String c = "";

        if (color == Colors.colors.black) {
            c = Colors.c_black();
        }
        if (color == Colors.colors.blue) {
            c = Colors.c_blue();
        }
        if (color == Colors.colors.cyan) {
            c = Colors.c_cyan();
        }
        if (color == Colors.colors.green) {
            c = Colors.c_green();
        }
        if (color == Colors.colors.purple) {
            c = Colors.c_purple();
        }
        if (color == Colors.colors.red) {
            c = Colors.c_red();
        }
        if (color == Colors.colors.white) {
            c = Colors.c_white();
        }
        if (color == Colors.colors.yellow) {
            c = Colors.c_yellow();
        }

        System.out.println(c + line + Colors.c_reset());
    }

    public static void writeLineBBWrap(String line, int typewriterDelay) throws InterruptedException {

        boolean parsingTag = false;
        String currentTag = "";
        int lineWidth;
        boolean correct = false;

        while (!correct) {
            correct = true;
            lineWidth = 0;
            for (int i = 0; i < line.length(); i++) {
                char ch = line.charAt(i);

                if (ch == '[') {
                    parsingTag = true;
                    currentTag = "";
                }
                else if (ch == ']') {
                    parsingTag = false;

                    if (currentTag.equals("br")) {
                        lineWidth = 0;
                    }
                }
                else if (parsingTag) {
                    currentTag += String.valueOf(ch);
                }

                if (!parsingTag) {
                    lineWidth++;

                    if (ch == ' ') {
                        if (lineWidth >= WINDOW_WIDTH - 1) {
                            int back = 1;

                            while (line.charAt(i - back) != ' ') {
                                back++;
                            }

                            line = Utils.removeCharInPosition(line, i - back);
                            line = Utils.insertAt(line, i - back, "[br]");

                            correct = false;
                            break;
                        }
                    }
                }

            }
        }

        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);

            if (ch == '[') {
                parsingTag = true;
            }
            else if (ch == ']') {
                parsingTag = false;

                if (currentTag.equals("br") || currentTag.equals("/br") || currentTag.equals("brbr")) {
                    System.out.println();
                }
                else if (!currentTag.equals("/")) {
                    currentTag = currentTag.replace("/", "");
                    currentTag = currentTag.replace("br", "");
                    System.out.print("\u001B[38;5;" + currentTag + "m");
                }
                else {
                    System.out.print("\u001B[38;5;7m");
                }

                currentTag = "";
            }
            else if (!parsingTag) {
                System.out.print(ch);

                if (TW_ENABLED) {
                    Thread.sleep(typewriterDelay);
                }
            }
            else {
                currentTag += Character.toString(ch);
            }
        }

        Console.writeLine();
    }

    public static void writeLineBB(String line, int typewriterDelay) throws InterruptedException {

        boolean parsingTag = false;
        String currentTag = "";

        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);

            if (ch == '[') {
                parsingTag = true;
            }
            else if (ch == ']') {
                parsingTag = false;

                if (currentTag.equals("br") || currentTag.equals("/br") || currentTag.equals("brbr")) {
                    System.out.println();
                }
                else if (!currentTag.equals("/")) {
                    currentTag = currentTag.replace("/", "");
                    currentTag = currentTag.replace("br", "");
                    System.out.print("\u001B[38;5;" + currentTag + "m");
                }
                else {
                    System.out.print("\u001B[38;5;7m");
                }

                currentTag = "";
            }
            else if (!parsingTag) {
                System.out.print(ch);

                if (TW_ENABLED) {
                    Thread.sleep(typewriterDelay);
                }
            }
            else {
                currentTag += Character.toString(ch);
            }
        }

        Console.writeLine();
    }

    public static void writeLineBB(String line) throws InterruptedException {

        boolean parsingTag = false;
        String currentTag = "";
        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);

            if (ch == '[') {
                parsingTag = true;
            }
            else if (ch == ']') {
                parsingTag = false;

                if (!currentTag.equals("/")) {
                    System.out.print("\u001B[38;5;" + currentTag + "m");
                }
                else {
                    System.out.print("\u001B[38;5;7m");
                }

                currentTag = "";
            }
            else if (!parsingTag) {
                System.out.print(ch);
            }
            else {
                currentTag += String.valueOf(ch);
            }
        }

        Console.writeLine();
    }

    public static void writeLine(String line, int xcolor) {
        System.out.println("\u001B[38;5;" + xcolor + "m" + line + Colors.c_reset());
    }

    public static void write(String line, int xcolor) {
        System.out.print("\u001B[38;5;" + xcolor + "m" + line + Colors.c_reset());
    }

    public static void setPosition(int x, int y) {
        System.out.print("\u001b[" + y + ";" + x + "H");
    }

    public static void setTitle(String title) {
        System.out.print("\033]2;" + title + "\007");
    }

    public static void writeImage(String image) throws IOException {
        File f = new File(image);
        String[] allText = Utils.readFileLines(image);

        for(String line : allText) {
            writeLine(line);
        }


    }

    // Expects 7 colors
    public static void writeFigletColor(String image, int[] colors) throws IOException {
        File f = new File(image);
        ClassLoader classLoader = Lang.class.getClassLoader();
        InputStream sss = classLoader.getResourceAsStream("com/company/ascii/" + image);

        String[] allText = Utils.readFileLines(sss);

        int i = 0;
        for(String line : allText) {
            writeLine(line, colors[i]);
            i++;
        }
    }

    public static void writeFigletColor(String image, int color) throws IOException {
        File f = new File(image);
        String[] allText = Utils.readFileLines(image);

        for(String line : allText) {
            writeLine(line, color);
        }
    }

    public static void writeFiglet(String image) throws IOException {
        File f = new File(image);
        String[] allText = Utils.readFileLines(image);

        for(String line : allText) {
            writeLine(line);
        }
    }

    public static void setTerminalSize(int width, int height) {
        Console.writeLine("\u001B[8;" + height + ";" + width + "t");
    }

    public static void setTerminalPosition(int x, int y) {
        Console.writeLine("\u001B[3;0;0t'");
    }

    public static String readLine() {
        return scan.nextLine();
    }

    public static char readKey() {
        return scan.next().charAt(0);
    }

    public static char readKeyDirect() throws IOException {
        return (char)RawConsoleInput.read(true);
    }

    public static void clearCurrentLine() {
        System.out.print("\u001b[2K");
    }

    public static Point getPosition() {
        return new Point();
    }

    public static void moveUp(int n)  {
        System.out.print("\u001b[" + n + "F");
    }

    public static void moveDown(int n)  {
        System.out.print("\u001b[" + n + "E");
    }

    public static void hideCursor() {
        System.out.print("\u001b[?25l");
        cursorOn = false;
    }

    public static void showCursor() {
        System.out.print("\u001b[?25h");
        cursorOn = true;
    }

    public static boolean getCursorVisible() {
        return  cursorOn;
    }

    public static void toggleCursor() {
        if (cursorOn) {
            hideCursor();
        }
        else {
            showCursor();
        }
    }

    public static void showInput() throws IOException {
        RawConsoleInput.resetConsoleMode();
    }

    public static class CommandUse {
    }
}
