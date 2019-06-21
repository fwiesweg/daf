package de.jpwinkler.daf.filter.objects;

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

import de.jpwinkler.daf.model.DoorsObject;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class AttributeFilter extends DoorsObjectFilter {

    private final String attribute;

    private final String filter;

    private final boolean exactMatch;

    private final boolean regexp;

    private final Pattern pattern;

    public AttributeFilter(final String attribute, final String filter, final boolean exactMatch, final boolean regexp) {
        super();
        this.attribute = attribute;
        this.filter = filter;
        this.exactMatch = exactMatch;
        this.regexp = regexp;
        try {
            pattern = Pattern.compile(filter, Pattern.CASE_INSENSITIVE);
        } catch (final PatternSyntaxException e) {
            throw new RuntimeException()
                    ;
        }
    }

    @Override
    public boolean checkObject(final DoorsObject object) {
        final String s = object.getAttributes().get(attribute);
        if (s != null) {
            if (regexp && pattern != null) {
                final Matcher matcher = pattern.matcher(s);
                return exactMatch ? matcher.matches() : matcher.find();
            } else if (!regexp) {
                return exactMatch ? s.equalsIgnoreCase(filter) : s.toLowerCase().contains(filter.toLowerCase());
            }
        }
        return false;
    }

}
