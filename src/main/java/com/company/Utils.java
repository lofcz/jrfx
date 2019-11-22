package com.company;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 *  Class Utils
 *  Storage class of various utilities, dealing with files, OS specific functions and String helpers
 *
 *  This class is a part of (Ad)venture project
 *
 *@author     Matěj Štágl
 *@version    1.0
 *@created    summer 2019
 */
public class Utils {
    private static int IdSeed = 0;
    private static final boolean ON_UNIX = false;
    public static int LEVEL_COMPLETED_CONSTANT = 100;

    public static int AssignId() {
        IdSeed++;
        return IdSeed;
    }

    public static void HookWindows() throws IOException, InterruptedException  {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    }

    // Compatibility before Java11 Files.readString
    public static String readStream(InputStream is) {
        StringBuilder sb = new StringBuilder(512);
        try {
            Reader r = new InputStreamReader(is, "UTF-8");
            int c = 0;
            while ((c = r.read()) != -1) {
                sb.append((char) c);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

    public static String readFile(File f) throws IOException {

        InputStream is = new FileInputStream(f);
        StringBuilder sb = new StringBuilder(512);
        try {
            Reader r = new InputStreamReader(is, "UTF-8");
            int c = 0;
            while ((c = r.read()) != -1) {
                sb.append((char) c);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

    public static String[] readFileLines(String file) throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader(file));
        List<String> lines = new ArrayList<String>();

        String line;
        while((line = bf.readLine()) != null) {
            lines.add(line);
        }
        bf.close();

        return lines.toArray(new String[]{});
    }

    public static String[] readFileLines(InputStream file) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(file));
        List<String> lines = new ArrayList<String>();

        String line;
        while((line = bf.readLine()) != null) {
            lines.add(line);
        }
        bf.close();

        return lines.toArray(new String[]{});
    }

    public static String insertAt(final String target, final int position, final String insert) {
        final int targetLen = target.length();
        if (position < 0 || position > targetLen) {
            throw new IllegalArgumentException("position=" + position);
        }
        if (insert.isEmpty()) {
            return target;
        }
        if (position == 0) {
            return insert.concat(target);
        } else if (position == targetLen) {
            return target.concat(insert);
        }
        final int insertLen = insert.length();
        final char[] buffer = new char[targetLen + insertLen];
        target.getChars(0, position, buffer, 0);
        insert.getChars(0, insertLen, buffer, position);
        target.getChars(position, targetLen, buffer, position + insertLen);
        return new String(buffer);
    }

    public static String changeCharInPosition(int position, char ch, String str){
        char[] charArray = str.toCharArray();
        charArray[position] = ch;
        return new String(charArray);
    }

    public static String removeCharInPosition(String str, int index) {
        return str.substring(0, index) + str.substring(index + 1);
    }
}
