package de.jpwinkler.daf.gui;

import de.codecentric.centerdevice.javafxsvg.SvgImageLoaderFactory;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainFX extends Application {

    @Override
    public void start(final Stage primaryStage) throws Exception {
        SvgImageLoaderFactory.install();

        final ApplicationPaneController applicationPaneController = new ApplicationPaneController();
        final Scene applicationScene = new Scene(applicationPaneController.getNode());

        primaryStage.setOnCloseRequest((WindowEvent event) -> {
            applicationPaneController.closeRequest();

            if (!applicationPaneController.canClose()) {
                event.consume();
            }
        });
        primaryStage.setScene(applicationScene);
        primaryStage.setMaximized(true);
        primaryStage.setTitle("Doors Access Framework – GUI");
        primaryStage.show();
    }
}
