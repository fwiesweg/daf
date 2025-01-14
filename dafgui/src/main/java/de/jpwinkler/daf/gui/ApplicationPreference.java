package de.jpwinkler.daf.gui;

/*-
 * #%L
 * dafgui
 * %%
 * Copyright (C) 2019 TU Berlin ASET
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashSet;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import net.harawata.appdirs.AppDirsFactory;

/**
 *
 * @author fwiesweg
 */
public class ApplicationPreference<T extends Serializable> {

    public static final Path APPLICATION_DATA_PATH = Paths.get(AppDirsFactory.getInstance().getUserConfigDir(Main.class.getPackage().getName(), null, null)).toAbsolutePath();
    public static final Path APPLICATION_PREFERENCES_PATH = APPLICATION_DATA_PATH.resolve("preferences");
    public static final Path APPLICATION_PLUGINS_PATH = APPLICATION_DATA_PATH.resolve("plugins");

    static {
        try {
            Files.createDirectories(APPLICATION_PREFERENCES_PATH);
            Files.createDirectories(APPLICATION_PLUGINS_PATH);

            System.setProperty("java.util.prefs.userRoot", APPLICATION_PREFERENCES_PATH.toString());
        } catch (IOException ex) {
            Logger.getLogger(ApplicationPreference.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected ApplicationPreference(String name, Class<? super T> valueType, T defaultValue) {
        this(name, valueType, defaultValue, value -> {
        });
    }

    protected ApplicationPreference(String name, Class<? super T> valueType, T defaultValue, Consumer<T> validator) {
        this.name = name;
        this.valueType = valueType;
        this.defaultValue = defaultValue;
        this.validator = validator;
        this.prefs = Preferences.userNodeForPackage(this.getClass());
    }

    private final String name;
    private final Class<? super T> valueType;
    private final T defaultValue;
    private final Consumer<T> validator;
    private final HashSet<Consumer<Object>> onChangedHandlers = new HashSet<>();
    private final Preferences prefs;

    public final <T extends Serializable> void store(T object) {
        if (object != null && !valueType.isAssignableFrom(object.getClass())) {
            throw new IllegalArgumentException("Object must be null or a " + valueType.getCanonicalName());
        }
        try (final java.io.ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            java.io.ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(object);
            prefs.put(this.name, Base64.getEncoder().encodeToString(bos.toByteArray()));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        this.onChangedHandlers.forEach(h -> h.accept(object));
    }

    public final T retrieve() {
        String data = prefs.get(this.name, null);
        if (data == null) {
            return (T) defaultValue;
        }
        try (final java.io.ByteArrayInputStream bis = new ByteArrayInputStream(Base64.getDecoder().decode(data))) {
            ObjectInputStream ois = new ObjectInputStream(bis);
            T value = (T) valueType.cast(ois.readObject());
            validator.accept(value);
            return value;
        } catch (IllegalArgumentException | ObjectStreamException | ClassNotFoundException | ClassCastException ex) {
            prefs.remove(this.name);
            return (T) defaultValue;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public final void addOnChangedHandler(Consumer<Object> handler) {
        this.onChangedHandlers.add(handler);
    }

    public final void removeOnChangedHandler(Consumer<Object> handler) {
        this.onChangedHandlers.remove(handler);
    }
}
