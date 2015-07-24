package de.jpwinkler.daf.dafcore.csv;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import de.jpwinkler.daf.dafcore.model.csv.DoorsModule;

public abstract class ModuleWriter extends OutputStreamWriter {

    public ModuleWriter(final OutputStream out, final Charset cs) {
        super(out, cs);
    }

    public ModuleWriter(final OutputStream out, final CharsetEncoder enc) {
        super(out, enc);
    }

    public ModuleWriter(final OutputStream out, final String charsetName) throws UnsupportedEncodingException {
        super(out, charsetName);
    }

    public ModuleWriter(final OutputStream out) {
        super(out);
    }

    public abstract void writeModule(DoorsModule module);

}