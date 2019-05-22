package de.jpwinkler.daf.gui.modules.commands;

import de.jpwinkler.daf.gui.CommandStack.AbstractCommand;
import de.jpwinkler.daf.gui.UpdateAction;
import de.jpwinkler.daf.gui.modules.ModulePaneController.ModuleUpdateAction;
import de.jpwinkler.daf.model.DoorsFactory;
import de.jpwinkler.daf.model.DoorsObject;
import de.jpwinkler.daf.model.DoorsTreeNode;

public class NewObjectBelowCommand extends AbstractCommand {

    private final DoorsTreeNode parent;
    private DoorsObject newObject;

    public NewObjectBelowCommand(final DoorsTreeNode parent) {
        this.parent = parent;
    }

    @Override
    public String getName() {
        return "New Object Below";
    }

    @Override
    public void apply() {
        newObject = DoorsFactory.eINSTANCE.createDoorsObject();
        newObject.setObjectText("New object");
        newObject.setObjectHeading("");
        newObject.setObjectLevel(((parent instanceof DoorsObject) ? ((DoorsObject) parent).getObjectLevel() : 0) + 1);
        redo();
    }

    @Override
    public void undo() {
        parent.getChildren().remove(newObject);
    }

    @Override
    public void redo() {
        parent.getChildren().add(newObject);
    }

    @Override
    public UpdateAction[] getUpdateActions() {
        return new UpdateAction[]{ModuleUpdateAction.UPDATE_CONTENT_VIEW, ModuleUpdateAction.UPDATE_OUTLINE_VIEW};
    }
}
