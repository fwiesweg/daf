/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.jpwinkler.daf.gui.extensions;

import java.util.Collections;
import java.util.List;
import javafx.scene.control.Menu;

/**
 *
 * @author fwiesweg
 */
public interface MenuExtension extends ApplicationPartExtension {
    
    default List<Menu> getMenus() {
        return Collections.emptyList();
    }
    
}
