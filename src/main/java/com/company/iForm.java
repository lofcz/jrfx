package com.company;
import java.io.IOException;

/**
 *  Interface iForm
 *  Defines standard methods usable on in-game forms
 *
 *  This interface is a part of (Ad)venture project
 *
 *@author     Matěj Štágl
 *@version    1.0
 *@created    summer 2019
 */
public interface iForm {
    void Render() throws IOException, InterruptedException;
    void StartRender() throws IOException, InterruptedException;
}
