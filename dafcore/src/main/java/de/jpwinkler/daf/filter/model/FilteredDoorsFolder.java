/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.jpwinkler.daf.filter.model;

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

import de.jpwinkler.daf.model.DoorsFolder;
import de.jpwinkler.daf.model.DoorsTreeNode;
import java.util.function.Predicate;

/**
 *
 * @author fwiesweg
 */
class FilteredDoorsFolder extends FilteredDoorsTreeNode<DoorsFolder> implements DoorsFolder {

    FilteredDoorsFolder(DoorsFolder self, Predicate<DoorsTreeNode> filter) {
        super(self, filter);
    }

    @Override
    public boolean isProject() {
        return self.isProject();
    }

    @Override
    public void setProject(boolean value) {
        self.setProject(value);
    }

}
