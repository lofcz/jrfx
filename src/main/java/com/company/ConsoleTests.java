package com.company;

/**
 *  Class ConsoleTests
 *  Tests used to deal with console
 *
 *  This class is a part of (Ad)venture project
 *
 *@author     Matěj Štágl
 *@version    1.0
 *@created    summer 2019
 */
public class ConsoleTests {

    public static void testColors() {
        for (int i = 0; i < 255; i++) {
            Console.writeLine("Colorful terminal", i);
        }
    }
}
