package de.jpwinkler.daf.model;

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

public class FindObjectVisitor extends DoorsTreeNodeVisitor<DoorsObject> {

    private final String objectIdentifier;

    private DoorsObject object;

    public FindObjectVisitor(final String objectIdentifier) {
        super(DoorsObject.class);
        this.objectIdentifier = objectIdentifier;
    }

    @Override
    public boolean visitPreTraverse(final DoorsObject object) {
        if (object.getObjectIdentifier() != null && object.getObjectIdentifier().equals(objectIdentifier)) {
            this.object = object;
            return false;
        } else {
            return true;
        }
    }

    public DoorsObject getObject() {
        return object;
    }
}
