package de.jpwinkler.daf.filter.objects;

import de.jpwinkler.daf.model.DoorsObject;

public class AttributeMissingFilter extends DoorsObjectFilter {

    private final String attribute;

    public AttributeMissingFilter(final String attribute) {
        super();
        this.attribute = attribute;
    }

    @Override
    public boolean checkObject(final DoorsObject object) {
        final String s = object.getAttributes().get(attribute);
        return s == null || s.isEmpty();
    }

}