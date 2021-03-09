package gr.teohaik;

import java.io.File;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private File selectedFile;

    @Override
    public void start(Stage stage) {
        var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();
        var label = new Label();

        FileChooser fileChooser = new FileChooser();

        Button button = new Button("Select Folder");
        Button buttonAnalyze = new Button("Analyze");
        buttonAnalyze.setOnAction(new AnalyzeEventHandler());
        button.setOnAction(e->{
            selectedFile = fileChooser.showOpenDialog(stage);
            if(selectedFile != null) {
                label.setText("Selected File: " + selectedFile.getAbsolutePath());
            }
        });

        FlowPane flowpane = new FlowPane();

        flowpane.getChildren().add(button);
        flowpane.getChildren().add(label);
        flowpane.getChildren().add(buttonAnalyze);

        var scene = new Scene(flowpane, 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    class AnalyzeEventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            if(selectedFile == null){
                selectedFile =
                        new File(
                                "test-pom.xml");
            }
            PomParser.parse(selectedFile);
        }
    }

}