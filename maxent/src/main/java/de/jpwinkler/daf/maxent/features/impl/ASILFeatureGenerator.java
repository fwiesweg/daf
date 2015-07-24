package de.jpwinkler.daf.maxent.features.impl;

import de.jpwinkler.daf.dafcore.model.csv.DoorsObject;
import de.jpwinkler.daf.maxent.features.FeatureGenerator;
import de.jpwinkler.daf.maxent.features.StringFeature;

public class ASILFeatureGenerator extends FeatureGenerator {

    @Override
    protected void processObject(final DoorsObject o) {
        if (o.getAttributes().containsKey("ASIL") && !o.getAttributes().get("ASIL").equals("-")) {
            emitFeature(new StringFeature("asil", o.getAttributes().get("ASIL")));
        }
    }

}
