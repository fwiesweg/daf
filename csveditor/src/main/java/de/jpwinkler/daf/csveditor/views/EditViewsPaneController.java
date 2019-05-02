package de.jpwinkler.daf.csveditor.views;

import de.jpwinkler.daf.csveditor.MainFX;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Window;

public class EditViewsPaneController {

    public static Dialog<List<ViewModel>> asDialog(Window owner, List<ViewModel> initialValue) {
        try {
            var dialog = new Dialog();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(owner);
            dialog.setResizable(true);

            final FXMLLoader loader = new FXMLLoader(MainFX.class.getResource("EditViewsPane.fxml"));
            dialog.getDialogPane().setContent(loader.load());
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

            final EditViewsPaneController editViewsController = loader.getController();
            editViewsController.initialize(initialValue);
            dialog.setResultConverter(bt -> bt == ButtonType.OK ? editViewsController.viewModelsListView.getItems() : null);
            return dialog;
        } catch (final IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @FXML
    private ListView<ViewModel> viewModelsListView;

    @FXML
    private ListView<ColumnDefinition> viewColumnsListView;

    @FXML
    private TextField viewTitleTextField;

    @FXML
    public void initialize() {
    }

    public void initialize(List<ViewModel> initialValue) {
        initialValue.forEach(c -> viewModelsListView.getItems().add(c));

        viewTitleTextField.textProperty().addListener((ObservableValue<? extends String> obs, String oldValue, String newValue) -> {
            if (viewModelsListView.getSelectionModel().getSelectedItem() != null) {
                viewModelsListView.getSelectionModel().getSelectedItem().setName(newValue);
            }
        });

        viewModelsListView.getSelectionModel().getSelectedItems().addListener((ListChangeListener.Change<? extends ViewModel> event) -> {
            ViewModel vm = event.getList().stream().findAny().orElse(null);
            viewColumnsListView.getItems().clear();
            if (vm != null) {
                viewColumnsListView.getItems().addAll(vm.getColumns());
                viewTitleTextField.setText(vm.getName());
                viewTitleTextField.setDisable(false);
            } else {
                viewTitleTextField.setText("");
                viewTitleTextField.setDisable(true);
            }
        });
    }

    @FXML
    public void topClicked() {
        final int selectedIndex = viewColumnsListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex > 0) {
            final ColumnDefinition cd = viewColumnsListView.getItems().remove(selectedIndex);
            viewColumnsListView.getItems().add(0, cd);
            viewColumnsListView.getSelectionModel().select(cd);
        }
    }

    @FXML
    public void upClicked() {
        final int selectedIndex = viewColumnsListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex > 0) {
            final ColumnDefinition cd = viewColumnsListView.getItems().remove(selectedIndex);
            viewColumnsListView.getItems().add(selectedIndex - 1, cd);
            viewColumnsListView.getSelectionModel().select(cd);
        }
    }

    @FXML
    public void downClicked() {
        final int selectedIndex = viewColumnsListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex < viewColumnsListView.getItems().size() - 1) {
            final ColumnDefinition cd = viewColumnsListView.getItems().remove(selectedIndex);
            viewColumnsListView.getItems().add(selectedIndex + 1, cd);
            viewColumnsListView.getSelectionModel().select(cd);
        }
    }

    @FXML
    public void botClicked() {
        final int selectedIndex = viewColumnsListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex < viewColumnsListView.getItems().size() - 1) {
            final ColumnDefinition cd = viewColumnsListView.getItems().remove(selectedIndex);
            viewColumnsListView.getItems().add(cd);
            viewColumnsListView.getSelectionModel().select(cd);
        }
    }

    @FXML
    public void addViewModelClicked() {
        ViewModel vm = new ViewModel("New view model");
        this.viewModelsListView.getItems().add(vm);
        this.viewModelsListView.getSelectionModel().select(vm);
    }

    @FXML
    public void deleteViewModelClicked() {
        ViewModel vm = this.viewModelsListView.getSelectionModel().getSelectedItem();
        if (vm != null) {
            this.viewModelsListView.getItems().remove(vm);
        }
    }
}
