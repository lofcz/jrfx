package com.company;
import java.io.IOException;

/**
 *  Interface iLevel
 *  Defines standard methods usable on BaseLevel instances
 *
 *  This interface is a part of (Ad)venture project
 *
 *@author     Matěj Štágl
 *@version    1.0
 *@created    summer 2019
 */
public interface iLevel {
    void observe() throws InterruptedException, IOException;
    void take() throws InterruptedException, IOException;
    void entrance() throws InterruptedException, IOException;
}
