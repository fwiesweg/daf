/*
 * doorsbridge - A library for Java to Doors interaction.
 * Copyright (C) 2016 Jonas Winkler
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.jpwinkler.daf.bridge;

import java.io.File;
import java.io.OutputStream;

public interface DoorsApplication {

    /**
     * Enter batch mode. When batch mode is active, commands are not sent to
     * DOORS and are stored in a buffer instead and executed as a single command
     * when batch mode ends. The order of commands remains the same.
     *
     * Executing multiple commands in batch mode is especially useful when DOORS
     * is started in silent mode (no gui).
     */
    void beginBatchMode();

    /**
     * Exit batch mode and execute all stored commands.
     *
     * @throws DoorsRuntimeException
     *             If the script executes the 'throw()' function.
     */
    void endBatchMode();

    /**
     * Shows a message dialog in DOORS.
     *
     * @param message
     *            The message to be shown.
     */
    void ack(String message);

    /**
     * Prints a messaged using the DOORS 'print' command.
     *
     * @param message
     *            The message to be printed.
     */
    void print(String message);

    /**
     * Opens a module for reading.
     *
     * @param url
     *            The URL of the module to be opened.
     * @return An interface used for interacting with the module.
     * @throws DoorsRuntimeException
     *             If the module does not exist.
     */
    ModuleRef openModule(DoorsURL url);

    /**
     * Opens a module for reading.
     *
     * @param name
     *            The path of the module.
     * @return An interface used for interacting with the module.
     * @throws DoorsRuntimeException
     *             If the module does not exist.
     */
    ModuleRef openModule(String name);

    ItemRef getRoot();

    ItemRef getItem(String path);

    /**
     * Runs a DXL script stored in a file.
     *
     * @param scriptFile
     *            The file containing the DXL code to be executed.
     * @throws DoorsRuntimeException
     *             If the script executes the 'throw()' function.
     */
    void runScript(File scriptFile);

    /**
     * Runs a DXL script.
     *
     * @param dxlCode
     *            the DXL script to be executed.
     * @throws DoorsRuntimeException
     *             If the script executes the 'throw()' function.
     */
    void runScript(String dxlCode);

    /**
     * Checks whether DOORS is running or not by testing if the DOORS Database
     * Browser Window exists and if DOORS is ready to execute DXL code.
     *
     * @return true if DOORS is running.
     */
    boolean isDoorsRunning();

    /**
     * Redirects output generated by DXL scripts using 'print' commands to the
     * designated stream.
     *
     * Note: Capturing the output is indirect and somewhat slow. Executing a DXL
     * script with 'print' commands and immediately exiting the Java program
     * after might result in some messages not getting redirected.
     *
     * @param stream
     *            The stream to witch output shall be redirected.
     */
    void redirectOutput(OutputStream stream);
}