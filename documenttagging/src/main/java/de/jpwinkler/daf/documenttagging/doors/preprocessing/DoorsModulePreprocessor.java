package de.jpwinkler.daf.documenttagging.doors.preprocessing;

import java.util.ArrayList;
import java.util.List;

import de.jpwinkler.daf.dafcore.csv.DoorsTreeNodeVisitor;
import de.jpwinkler.daf.dafcore.model.csv.DoorsModule;
import de.jpwinkler.daf.dafcore.model.csv.DoorsObject;

public class DoorsModulePreprocessor {

    private final List<DoorsObjectPreprocessor> objectPreprocessors = new ArrayList<>();

    public void addObjectPreprocessor(final DoorsObjectPreprocessor objectPreprocessor) {
        objectPreprocessors.add(objectPreprocessor);
    }

    public static DoorsModulePreprocessor getDefaultPreprocessor() {
        final DoorsModulePreprocessor doorsModulePreprocessor = new DoorsModulePreprocessor();

        doorsModulePreprocessor.addObjectPreprocessor(new StopwordRemovalPreprocessor());

        return doorsModulePreprocessor;
    }

    public void preprocessModule(final DoorsModule module) {
        module.accept(new DoorsTreeNodeVisitor() {
            @Override
            public boolean visitPreTraverse(final DoorsObject object) {
                for (final DoorsObjectPreprocessor objectPreprocessor : objectPreprocessors) {
                    objectPreprocessor.preprocessObject(object);
                }
                return true;
            }
        });
    }

}