package de.jpwinkler.daf.documenttagging.maxent.preprocessing;

import de.jpwinkler.daf.dafcore.model.csv.DoorsObject;

public interface Preprocessor {

    void apply(DoorsObject object);

}