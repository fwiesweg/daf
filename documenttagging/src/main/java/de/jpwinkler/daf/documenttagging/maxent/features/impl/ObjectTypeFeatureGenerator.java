package de.jpwinkler.daf.documenttagging.maxent.features.impl;

import de.jpwinkler.daf.dafcore.model.csv.DoorsObject;
import de.jpwinkler.daf.documenttagging.maxent.features.FeatureGenerator;
import de.jpwinkler.daf.documenttagging.maxent.features.StringFeature;

public class ObjectTypeFeatureGenerator extends FeatureGenerator {

    @Override
    protected void processObject(final DoorsObject o) {
        if (o.getAttributes().containsKey("Object Type")) {
            emitFeature(new StringFeature("objectType", o.getAttributes().get("Object Type")));
        }
    }

}