package de.jpwinkler.daf.bridge.model;

/*-
 * #%L
 * dafcore
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
import de.jpwinkler.daf.bridge.DXLScript;
import de.jpwinkler.daf.bridge.DoorsApplication;
import de.jpwinkler.daf.db.BackgroundTaskExecutor;
import de.jpwinkler.daf.db.DatabaseFactory;
import de.jpwinkler.daf.db.ModuleCSV;
import de.jpwinkler.daf.model.DoorsAttributes;
import de.jpwinkler.daf.model.DoorsFolder;
import de.jpwinkler.daf.model.DoorsModule;
import de.jpwinkler.daf.model.DoorsObject;
import de.jpwinkler.daf.model.DoorsTableRow;
import de.jpwinkler.daf.model.DoorsTreeNode;
import de.jpwinkler.daf.model.RuntimeExecutionException;
import de.jpwinkler.daf.model.UnresolvedLink;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

class DoorsModuleRefImpl extends DoorsTreeNodeRefImpl implements DoorsModule {

    private final AtomicReference<CompletableFuture<List<DoorsTreeNode>>> children = new AtomicReference<>();
    private final AtomicReference<CompletableFuture<Map<String, String>>> moduleAttributes = new AtomicReference<>();
    private final AtomicReference<List<String>> objectAttributes = new AtomicReference<>();

    private static final Logger LOG = Logger.getLogger(DoorsModuleRefImpl.class.getName());

    public DoorsModuleRefImpl(final DoorsApplication DoorsApplication, final DoorsTreeNode parent, final String name) {
        super(DoorsApplication, parent, name);
    }

    @Override
    public Map<String, String> getAttributes() {
        try {
            return getAttributesAsync(BackgroundTaskExecutor.SYNCHRONOUS).get();
        } catch (InterruptedException ex) {
            throw new RuntimeException();
        } catch (Throwable ex) {
            throw new RuntimeExecutionException(ex);
        }
    }

    @Override
    public CompletableFuture<Map<String, String>> getAttributesAsync(BackgroundTaskExecutor executor) {
        return executor.runBackgroundTask("Load attributes", this.moduleAttributes, i -> {
            String result = doorsApplication.runScript(builder -> {
                builder.addPreamble(DXLScript.fromResource("lib/utils.dxl"));
                builder.addPreamble(DXLScript.fromResource("lib/export_mmd.dxl"));
                builder = builder.beginScope();
                builder.addScript(DXLScript.fromResource("get_module_attributes.dxl"));
                builder.setVariable("url", null);
                builder.setVariable("name", this.getDoorsPath());
                builder = builder.endScope();
            });

            try (ByteArrayInputStream bis = new ByteArrayInputStream(result.getBytes(ModuleCSV.CHARSET))) {
                Map<String, String> attributes = ModuleCSV.readMetaData(doorsApplication.getDatabaseFactory(), bis);
                attributes.put(DoorsAttributes.MODULE_VIEW.getKey(), this.doorsApplication.getDatabaseView());
                return attributes;
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    @Override
    public CompletableFuture<List<DoorsTreeNode>> getChildrenAsync(BackgroundTaskExecutor executor) {
        return executor.runBackgroundTask("Load module", this.children, i -> {
            try {
                Path tempFile = Files.createTempFile(null, null);

                doorsApplication.runScript(builder -> {
                    builder.addPreamble(DXLScript.fromResource("lib/utils.dxl"));
                    builder.addPreamble(DXLScript.fromResource("lib/export_csv.dxl"));
                    builder.addPreamble(DXLScript.fromResource("lib/export_mmd.dxl"));

                    builder = builder.beginScope();
                    builder.addScript(DXLScript.fromResource("export_csv_single.dxl"));
                    builder.setVariable("url", null);
                    builder.setVariable("name", this.getDoorsPath());
                    builder.setVariable("view", this.doorsApplication.getDatabaseView());
                    builder.setVariable("file", tempFile.toAbsolutePath().toString());
                    builder = builder.endScope();
                });

                DoorsModule loadedModule = ModuleCSV.readModule(new DatabaseFactory() {
                    @Override
                    public DoorsFolder createFolder(DoorsTreeNode parent, String name, boolean project) {
                        throw new UnsupportedOperationException("Not supported.");
                    }

                    @Override
                    public DoorsModule createModule(DoorsTreeNode parent, String name) {
                        return new DoorsModuleRefImpl(doorsApplication, parent, name) {
                            private final ArrayList<DoorsTreeNode> dumbChildrenHolder = new ArrayList<>();
                            private List<String> dumbObjectAttributeHolder;

                            @Override
                            public List<DoorsTreeNode> getChildren() {
                                return dumbChildrenHolder;
                            }

                            @Override
                            public void setObjectAttributes(List<String> attrs) {
                                this.dumbObjectAttributeHolder = attrs;
                            }

                            @Override
                            public CompletableFuture<List<String>> getObjectAttributesAsync(BackgroundTaskExecutor executor) {
                                return CompletableFuture.completedFuture(this.dumbObjectAttributeHolder);
                            }

                            @Override
                            public List<String> getObjectAttributes() {
                                return dumbObjectAttributeHolder;
                            }

                        };
                    }

                    @Override
                    public DoorsObject createObject(DoorsTreeNode parent, String objectText) {
                        return doorsApplication.getDatabaseFactory().createObject(parent, objectText);
                    }

                    @Override
                    public DoorsTableRow createTableRow(DoorsTreeNode parent) {
                        return doorsApplication.getDatabaseFactory().createTableRow(parent);
                    }

                    @Override
                    public UnresolvedLink createLink(DoorsObject source, String targetModule, String targetObject) {
                        throw new UnsupportedOperationException("Not supported.");
                    }

                }, tempFile.toFile());

                List<DoorsTreeNode> result = loadedModule.getChildren();
                result.forEach(c -> c.setParent(this));

                if (!objectAttributes.compareAndSet(null, loadedModule.getObjectAttributes())) {
                    throw new AssertionError();
                }

                return result;
            } catch (IOException ex) {
                throw new RuntimeException("error exporting module " + this.getDoorsPath(), ex)    ;
            }
        });
    }

    @Override
    public boolean isChildrenLoaded() {
        CompletableFuture<List<DoorsTreeNode>> childrenFuture = children.get();
        return childrenFuture != null && childrenFuture.isDone();
    }

    @Override
    public List<String> getObjectAttributes() {
        try {
            return getObjectAttributesAsync(BackgroundTaskExecutor.SYNCHRONOUS).get();
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        } catch (Throwable ex) {
            throw new RuntimeExecutionException(ex);
        }
    }

    @Override
    public CompletableFuture<List<String>> getObjectAttributesAsync(BackgroundTaskExecutor executor) {
        return getChildrenAsync(executor).thenApply(children -> objectAttributes.get());
    }

    @Override
    public void setObjectAttributes(List<String> attrs) {
    }

    @Override
    public String getView() {
        return DoorsAttributes.MODULE_VIEW.getValue(String.class, this);
    }
}
