package de.jpwinkler.daf.gui;

import de.jpwinkler.daf.model.DoorsTreeNode;
import java.util.function.Consumer;

public class CommandStack {

    private AbstractCommand lastExecuted = INITIAL_COMMAND;
    private AbstractCommand lastSaved = INITIAL_COMMAND;
    private Consumer<Boolean> onDirty = (a) -> {};

    public void addCommand(final AbstractCommand command) {
        command.previous = lastExecuted;
        this.lastExecuted.next = command;
        this.lastExecuted = command;
        
        onDirty.accept(isDirty());
    }

    public AbstractCommand undo() {
        final AbstractCommand commandToUndo = lastExecuted;
        if (!commandToUndo.canUndo()) {
            return null;
        }

        this.lastExecuted = lastExecuted.previous;
        commandToUndo.undo();
        
        onDirty.accept(isDirty());
        return commandToUndo;
    }

    public AbstractCommand redo() {
        AbstractCommand commandToRedo = lastExecuted.next;
        if (commandToRedo != null) {
            this.lastExecuted = commandToRedo;
            commandToRedo.redo();
        }
        
        onDirty.accept(isDirty());
        return commandToRedo;
    }

    public boolean isDirty() {
        return lastExecuted != lastSaved;
    }

    public void setSavePoint() {
        this.lastSaved = lastExecuted;
        onDirty.accept(isDirty());
    }

    public Consumer<Boolean> getOnDirty() {
        return onDirty;
    }

    public void setOnDirty(Consumer<Boolean> onDirty) {
        this.onDirty = onDirty;
    }

    public static abstract class AbstractCommand<T extends DoorsTreeNode> {

        public AbstractCommand() {
        }

        public boolean isApplicable() {
            return true;
        }

        public abstract String getName();

        public abstract void apply();

        public abstract void redo();

        public abstract void undo();

        public UpdateAction[] getUpdateActions() {
            return new UpdateAction[0];
        }

        public boolean canUndo() {
            return true;
        }

        private AbstractCommand previous;
        private AbstractCommand next;
    }

    public static final AbstractCommand INITIAL_COMMAND = new AbstractCommand() {
        @Override
        public String getName() {
            return null;
        }

        @Override
        public void apply() {
        }

        @Override
        public void redo() {
        }

        @Override
        public void undo() {
        }

        @Override
        public boolean canUndo() {
            return false;
        }
    };
}