package de.jpwinkler.daf.documenttagging.doors.maxent;

import de.jpwinkler.daf.dafcore.model.csv.DoorsObject;

public class WordPredicateGenerator extends PredicateGenerator {

    private String[] tokenizer(final String s) {
        return s.replaceAll("(?![äöüßa-z0-9 ])(.)", " ").split(" ");
    }

    @Override
    protected void runGenerator(final DoorsObject object) {
        for (final String word : tokenizer(object.getText())) {
            if (!word.isEmpty()) {
                emitPredicate(word);
            }
        }
    }

}