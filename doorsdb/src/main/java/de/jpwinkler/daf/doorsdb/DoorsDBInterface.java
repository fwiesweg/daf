package de.jpwinkler.daf.doorsdb;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import de.jpwinkler.daf.dafcore.csv.ModuleMetaDataParser;
import de.jpwinkler.daf.dafcore.util.DoorsModuleUtil;
import de.jpwinkler.daf.doorsdb.doorsdbmodel.DBFolder;
import de.jpwinkler.daf.doorsdb.doorsdbmodel.DBModule;
import de.jpwinkler.daf.doorsdb.doorsdbmodel.DBTag;
import de.jpwinkler.daf.doorsdb.doorsdbmodel.DBVersion;
import de.jpwinkler.daf.doorsdb.doorsdbmodel.DoorsDB;
import de.jpwinkler.daf.doorsdb.doorsdbmodel.DoorsDBModelFactory;
import de.jpwinkler.daf.doorsdb.doorsdbmodel.DoorsDBModelPackage;
import de.jpwinkler.daf.doorsdb.search.DBSearchExpression;
import de.jpwinkler.daf.doorsdb.util.DBUtils;
import de.jpwinkler.daf.doorsdb.util.DoorsDBVisitor;
import de.jpwinkler.libs.doorsbridge.DoorsApplication;
import de.jpwinkler.libs.doorsbridge.DoorsApplicationFactory;
import de.jpwinkler.libs.doorsbridge.DoorsException;
import de.jpwinkler.libs.doorsbridge.DoorsItemType;
import de.jpwinkler.libs.doorsbridge.ItemRef;
import de.jpwinkler.libs.doorsbridge.ModuleRef;

public class DoorsDBInterface {

    private final DoorsDB db;
    private final Path databaseFile;
    private final Path databaseRoot;

    private final DoorsApplication app = DoorsApplicationFactory.getDoorsApplication();

    public static DoorsDBInterface createDB(final Path databaseFile) throws IOException {
        final DoorsDB db = DoorsDBModelFactory.eINSTANCE.createDoorsDB();
        db.setRoot(DoorsDBModelFactory.eINSTANCE.createDBFolder());
        final DoorsDBInterface doorsDBInterface = new DoorsDBInterface(databaseFile, db);
        doorsDBInterface.saveDB();
        return doorsDBInterface;
    }

    public static DoorsDBInterface openDB(final Path databaseFile) throws IOException {

        DoorsDBModelPackage.eINSTANCE.eClass();

        final Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        reg.getExtensionToFactoryMap().put("doorsdbmodel", new XMIResourceFactoryImpl());

        final ResourceSet resourceSet = new ResourceSetImpl();

        // TODO verify
        final Resource resource = resourceSet.getResource(URI.createFileURI(databaseFile.toString()), true);
        final DoorsDB db = (DoorsDB) resource.getContents().get(0);
        return new DoorsDBInterface(databaseFile, db);
    }

    public static DoorsDBInterface createOrOpenDB(final Path databaseFile) throws FileNotFoundException, IOException {
        if (Files.exists(databaseFile)) {
            return openDB(databaseFile);
        } else {
            return createDB(databaseFile);
        }
    }

    private DoorsDBInterface(final Path databaseFile, final DoorsDB db) throws IOException {
        this.databaseFile = databaseFile.toAbsolutePath();
        databaseRoot = databaseFile.toAbsolutePath().getParent();
        this.db = db;
    }

    public Path getCSVLocation(final DBVersion v) {
        return Paths.get(databaseRoot.toString(), v.getModule().getParent().getFullName(), v.getModule().getName() + "_" + new SimpleDateFormat("yyyyMMdd").format(v.getDate()) + ".csv");
    }

    private Path getMMDLocation(final DBVersion v) {
        return Paths.get(getCSVLocation(v).toString() + ".mmd");
    }

    public DBModule addModule(final String moduleName) throws DoorsException, IOException {
        final ItemRef i = app.getItem(moduleName);

        if (!i.exists()) {
            throw new RuntimeException(moduleName + " does not exist.");
        }

        if (!(i.getType() == DoorsItemType.FORMAL)) {
            throw new RuntimeException(moduleName + " is not a formal module.");
        }

        final DBFolder folder = DBUtils.mkdirs(db, i.getParent());

        DBModule module = folder.getModule(i.getItemName().getName());
        if (module == null) {
            module = DoorsDBModelFactory.eINSTANCE.createDBModule();
            module.setParent(folder);
            module.setName(i.getItemName().getName());
        }
        updateModule(module);
        return module;
    }

    public boolean updateModule(final DBModule module) throws DoorsException, IOException {
        final ItemRef i = app.getItem(module.getFullName());
        final ModuleRef modRef = i.open();

        final Map<String, String> metaData = modRef.getModuleAttributes();
        Date lastChangedOn;
        try {
            lastChangedOn = DoorsModuleUtil.parseDate(metaData.get("Last Modified On"));
        } catch (final ParseException e) {
            throw new RuntimeException(e);
        }

        boolean result = false;

        if (module.getLatestVersion() == null || module.getLatestVersion().getDate().before(lastChangedOn)) {
            final DBVersion version = DoorsDBModelFactory.eINSTANCE.createDBVersion();
            version.setDate(lastChangedOn);
            version.setModule(module);

            Files.createDirectories(getCSVLocation(version).getParent());
            modRef.exportToCSV(getCSVLocation(version).toFile());

            final Map<String, String> attributes = new ModuleMetaDataParser().parseModuleMetaData(getMMDLocation(version).toFile());
            for (final Entry<String, String> e : attributes.entrySet()) {
                version.getAttributes().put(e.getKey(), e.getValue());
            }
            Files.delete(getMMDLocation(version));
            result = true;
            saveDB();
        }
        modRef.close();
        return result;
    }

    public List<DBModule> updateAllModules() {
        final List<DBModule> updatedModules = new ArrayList<>();
        final Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, -1);
        final Date cutoff = c.getTime();

        db.accept(new DoorsDBVisitor() {

            @Override
            public void visit(final DBModule module) {
                if (module.hasTag("sys:keepversion") || (module.getLatestVersion() != null && module.getLatestVersion().getDate().before(cutoff))) {
                    System.out.println("skipping " + module.getFullName());
                    return;
                }
                try {
                    System.out.print("updating " + module.getFullName() + "... ");
                    if (updateModule(module)) {
                        System.out.println("added new version.");
                    } else {
                        System.out.println("up to date.");
                    }
                } catch (DoorsException | IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            @Override
            public void visit(final DBFolder folder) {
            }
        });
        return updatedModules;
    }

    public void saveDB() throws IOException {
        final Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        reg.getExtensionToFactoryMap().put("doorsdbmodel", new XMIResourceFactoryImpl());
        final ResourceSet resourceSet = new ResourceSetImpl();
        final Resource resource = resourceSet.createResource(URI.createFileURI(databaseFile.toString()));
        resource.getContents().add(db);
        resource.save(new HashMap<>());
    }

    public DoorsDB getDB() {
        return db;
    }

    public void addTag(final DBModule module, final String tag) {
        DBTag tagObject = db.getTag(tag);
        if (tagObject == null) {
            tagObject = DoorsDBModelFactory.eINSTANCE.createDBTag();
            tagObject.setName(tag);
            db.getTags().add(tagObject);
        }
        if (!module.getTags().contains(tagObject)) {
            module.getTags().add(tagObject);
        }
    }

    public List<DBModule> getModulesWithTag(final String tag) {
        final DBTag tagObject = db.getTag(tag);
        if (tagObject != null) {
            return tagObject.getModules();
        } else {
            return Collections.emptyList();
        }
    }

    public void removeTag(final DBModule module, final String tag) {
        final DBTag tagObject = db.getTag(tag);
        module.getTags().remove(tagObject);
    }

    public List<String> getTags(final DBModule value) {
        return value.getTags().stream().map(t -> t.getName()).collect(Collectors.toList());
    }

    public void removeModule(final DBModule module) {
        module.setParent(null);
        module.getTags().clear();
        module.getVersions().forEach(v -> {
            try {
                Files.deleteIfExists(getCSVLocation(v));
                Files.deleteIfExists(getMMDLocation(v));
            } catch (final IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        module.getVersions().clear();
    }

    public void removeFolder(final DBFolder folder) {
        final List<DBModule> markedForDeletion = new ArrayList<>();
        folder.accept(new DoorsDBVisitor() {

            @Override
            public void visit(final DBModule module) {
                markedForDeletion.add(module);
            }

            @Override
            public void visit(final DBFolder folder) {
            }
        });
        markedForDeletion.forEach(m -> removeModule(m));
        folder.setParent(null);
    }

    public List<DBModule> getAllModules() {
        final List<DBModule> result = new ArrayList<>();
        db.getRoot().accept(new DoorsDBVisitor() {

            @Override
            public void visit(final DBModule module) {
                result.add(module);
            }

            @Override
            public void visit(final DBFolder folder) {
            }
        });
        return result;
    }

    public List<DBModule> findModules(final DBSearchExpression e) {
        final List<DBModule> result = new ArrayList<>();
        db.getRoot().accept(new DoorsDBVisitor() {

            @Override
            public void visit(final DBModule module) {
                if (e.matches(module)) {
                    result.add(module);
                }
            }

            @Override
            public void visit(final DBFolder folder) {
            }
        });
        return result;
    }

    public DBFolder getFolder(final String path) {
        final List<String> pathSegments = Arrays.asList(path.split("/")).stream().filter(s -> !s.isEmpty()).collect(Collectors.toList());
        DBFolder current = getDB().getRoot();
        for (final String segment : pathSegments.subList(0, pathSegments.size())) {
            current = current.getFolder(segment);
            if (current == null) {
                return null;
            }
        }
        return current;
    }

    public DBModule getModule(final String path) {
        final List<String> pathSegments = Arrays.asList(path.split("/")).stream().filter(s -> !s.isEmpty()).collect(Collectors.toList());
        DBFolder current = getDB().getRoot();
        for (final String segment : pathSegments.subList(0, pathSegments.size() - 1)) {
            current = current.getFolder(segment);
            if (current == null) {
                return null;
            }
        }
        return current.getModule(pathSegments.get(pathSegments.size() - 1));
    }

    public static DoorsDBInterface getDefaultDatabase() throws FileNotFoundException, IOException {
        final Path path = Paths.get(System.getenv("LOCALAPPDATA"), "DoorsDB");
        if (!Files.exists(path)) {
            Files.createDirectory(path);
        }
        return createOrOpenDB(path.resolve("db.doorsdbmodel"));
    }

}