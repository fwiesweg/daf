package de.jpwinkler.daf.csveditor.views;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class ViewDefinition implements Serializable {

    public ViewDefinition(String name) {
        this.name = name;
    }

    private String name;
    private final List<ColumnDefinition> columns = new ArrayList<>();
    private boolean displayRemainingColumns;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ColumnDefinition> getColumns() {
        return columns;
    }

    public boolean isDisplayRemainingColumns() {
        return displayRemainingColumns;
    }

    public void setDisplayRemainingColumns(boolean displayRemainingColumns) {
        this.displayRemainingColumns = displayRemainingColumns;
    }

    @Override
    public String toString() {
        return name;
    }

}
