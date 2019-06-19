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

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import de.jpwinkler.daf.bridge.model.DoorsBridgeDatabaseFactory;
import de.jpwinkler.daf.db.DatabaseFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

public class DoorsApplication {

    public static final String STANDARD_VIEW = "Standard view";

    private static final Logger LOGGER = Logger.getLogger(DoorsApplication.class.getName());
    private static Throwable loadError;

    static {
        try {
            String lib;
            switch (System.getProperty("os.arch")) {
                case "x86":
                    lib = "jacob/jacob-1.18-x86.dll";
                    break;
                case "amd64":
                    lib = "jacob/jacob-1.18-x64.dll";
                    break;
                default:
                    throw new RuntimeException("No jacob dll for architecture " + System.getProperty("os.arch"));
            }
            File dllFile = File.createTempFile("doorsbridge", ".dll");
            dllFile.deleteOnExit();
            try (InputStream is = DoorsApplication.class.getClassLoader().getResourceAsStream(lib)) {
                FileUtils.copyInputStreamToFile(is, dllFile);
            }
            System.load(dllFile.getAbsolutePath());
        } catch (Throwable t) {
            loadError = t;
            Logger.getLogger(DoorsApplication.class.getName()).log(Level.SEVERE, null, t);
        }

    }

    /**
     * Returns a DOORS application instance for interacting with DOORS.
     * Executing commands using this instance requires a running DOORS session.
     */
    public DoorsApplication() {
        this(null);
    }

    public DoorsApplication(OutputStream outputStream) {
        this(null, null, null, null, outputStream);
    }

    public DoorsApplication(String doorsPath, String doorsServer, String user, String password) {
        this(doorsPath, doorsServer, user, password, null);
    }

    /**
     * Returns a DOORS application instance for interacting with DOORS. Each
     * command is executed separately using the doors '-b' command line switch.
     *
     * @param doorsPath Full path to the DOORS executable. Usually "doors.exe"
     * @param doorsServer Server and port of the DOORS database server in
     * conventional DOORS format (port@server).
     * @param user Username used for authentication.
     * @param password Password used for authentication. This password is stored
     * as a string as long as the returned DOORS application instance exists.
     * @param outputStream If not null, redirects output generated by DXL
     * scripts using 'print' commands to the designated stream.
     *
     * Note: Redirecting the output is indirect and somewhat slow. Executing a
     * DXL script with 'print' commands and immediately exiting the Java program
     * after might result in some messages not getting redirected.
     */
    public DoorsApplication(String doorsPath, String doorsServer, String user, String password, OutputStream outputStream) {
        if (loadError != null) {
            throw new RuntimeException(loadError);
        }
        this.outputStream = outputStream;

        this.doorsPath = doorsPath;
        this.doorsServer = doorsServer;
        this.user = user;
        this.password = password;

        this.silentMode = doorsServer != null;
    }

    // ActiveXComponent seems to be bound to the current thread, thus we store
    // one ActiveXComponent per thread.
    private final AtomicReference<ThreadLocal<ActiveXComponent>> doorsApplication = new AtomicReference<>(
            ThreadLocal.withInitial(() -> new ActiveXComponent("Doors.Application")));

    /**
     * If assigned, output is redirected here.
     */
    private final OutputStream outputStream;

    private final boolean silentMode;

    // Database connection settings for use with silent mode.
    private final String doorsPath;
    private final String doorsServer;
    private final String user;
    private final String password;

    private final DatabaseFactory databaseFactory = new DoorsBridgeDatabaseFactory(this);

    public boolean isSilentMode() {
        return silentMode;
    }

    private void executeDxl(final String dxl) {
        ThreadLocal<ActiveXComponent> doorsApplicationThreadLocal = this.doorsApplication.get();
        try {
            Dispatch.call(doorsApplicationThreadLocal.get(), "runStr", dxl);
        } catch (Throwable t) {
            // restart the component
            doorsApplicationThreadLocal.get().safeRelease();
            doorsApplicationThreadLocal.remove();

            throw new DoorsRuntimeException(t);
        }
    }

    private void executeDxlSilent(final String dxl) throws IOException {
        final File f = getTempFile();
        FileUtils.write(f, dxl);

        final String[] cmdLine = new String[]{doorsPath, "-b", f.getAbsolutePath(), "-d", doorsServer, "-u", user, "-P", "xxxx"};

        LOGGER.info(String.format("Running DOORS in silent mode. Command line: %s", String.join(" ", cmdLine)));

        cmdLine[cmdLine.length - 1] = password;

        final Process exec = Runtime.getRuntime().exec(cmdLine);
        try {
            exec.waitFor();
        } catch (final InterruptedException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    private File getTempFile() throws IOException {
        final File outFile = File.createTempFile("doors_temp", "");
        outFile.deleteOnExit();
        return outFile;
    }

    public DatabaseFactory getDatabaseFactory() {
        return databaseFactory;
    }

    /**
     * Shows a message dialog in DOORS.
     *
     * @param message The message to be shown.
     */
    public void ack(final String message) {
        runScript(builder -> {
            builder.addScript(DXLScript.fromResource("ack.dxl"));
            builder.setVariable("message", message);
        });
    }

    /**
     * Prints a messaged using the DOORS 'print' command.
     *
     * @param message The message to be printed.
     */
    public void print(final String message) {
        runScript(builder -> {
            builder.addScript(DXLScript.fromResource("print.dxl"));
            builder.setVariable("message", message);
        });
    }

    /**
     * Runs a DXL script.
     *
     * @param script the DXL script to be executed.
     * @throws DoorsRuntimeException If the script fails or executes 'throw()'
     */
    public String runScript(final DXLScript script) {
        return runScript(builder -> builder.addScript(script));
    }

    /**
     * Runs a DXL script.
     *
     * @param prepareScriptBuilder Function receiving a builder to create as
     * script with.
     * @throws DoorsRuntimeException If the script fails or executes 'throw()'
     */
    public String runScript(final Consumer<DoorsScriptBuilder> prepareScriptBuilder) {
        try {
            DoorsScriptBuilder innerScriptBuilder = new DoorsScriptBuilder().beginScope();
            prepareScriptBuilder.accept(innerScriptBuilder);
            return executeSript(innerScriptBuilder.endScope());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private String executeSript(DoorsScriptBuilder scriptBuilder) throws IOException {

        final boolean redirectOutput = outputStream != null;

        final File exceptionFile = getTempFile();
        final File returnFile = getTempFile();

        scriptBuilder.addPreamble(DXLScript.fromResource("pre/exception_handling.dxl"));
        scriptBuilder.setVariable("exceptionFilename", exceptionFile.getAbsolutePath());

        scriptBuilder.addPreamble(DXLScript.fromResource("pre/return.dxl"));
        scriptBuilder.setVariable("returnFilename", returnFile.getAbsolutePath());

        if (redirectOutput) {
            scriptBuilder.addPreamble(DXLScript.fromResource("pre/redirect_output.dxl"));
        }

        FileForwarder t = null;
        if (redirectOutput) {
            scriptBuilder.addScript(DXLScript.fromResource("lib/redirect_output_post.dxl"));

            final File outputFile = getTempFile();
            scriptBuilder.setVariable("outputRedirectFilename", outputFile.getAbsolutePath());
            if (!outputFile.exists()) {
                outputFile.createNewFile();
            }
            t = FileForwarder.create(outputFile, outputStream);
        }

        final String dxl = scriptBuilder.build();
        if (silentMode) {
            executeDxlSilent(dxl);
        } else {
            executeDxl(dxl);
        }

        if (redirectOutput) {
            t.stop();
        }

        if (exceptionFile.exists()) {
            final String message = FileUtils.readFileToString(exceptionFile, Charset.forName("Cp1252"));
            if (!message.isEmpty()) {
                throw new DoorsRuntimeException("DXL script failed: " + message);
            }
        }

        if (returnFile.exists()) {
            return FileUtils.readFileToString(returnFile, Charset.forName("Cp1252"));
        } else {
            return null;
        }
    }

    public final void close() {
        doorsApplication.set(null);
    }

    private static class FileForwarder implements Runnable {

        private final OutputStream out;
        private final File file;
        private boolean run = true;

        public FileForwarder(final File file, final OutputStream out) {
            this.file = file;
            this.out = out;
        }

        @Override
        public void run() {
            try (final RandomAccessFile raf = new RandomAccessFile(file, "r")) {
                long position = raf.length();
                raf.seek(position);

                long length = raf.length();
                while (position < length || run) {

                    while (position < length) {

                        final byte[] b = new byte[1000];
                        final int numRead = raf.read(b);

                        out.write(b, 0, numRead);
                        out.flush();
                        position = raf.getFilePointer();

                    }

                    try {
                        Thread.sleep(100);
                    } catch (final InterruptedException e) {
                    }
                    length = raf.length();

                }
            } catch (final IOException e) {
                Logger.getLogger(FileForwarder.class.getName()).log(Level.SEVERE, e.getMessage(), e);
            }
        }

        public void stop() {
            run = false;
        }

        public static FileForwarder create(final File file, final OutputStream os) {
            final FileForwarder myTailer = new FileForwarder(file, os);

            final Thread t = new Thread(myTailer);
            t.setDaemon(true);
            t.start();

            return myTailer;
        }

    }
}
