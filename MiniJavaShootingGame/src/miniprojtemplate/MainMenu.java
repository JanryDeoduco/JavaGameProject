  package miniprojtemplate;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainMenu extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Load the background image
        Image backgroundImage = new Image("file:mainbg.png", true);

        // Create BackgroundImage
        BackgroundImage background = new BackgroundImage(backgroundImage,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));

        // VBox layout with the background image
        VBox layout = new VBox(10);
        layout.setBackground(new Background(background));

        Label titleLabel = new Label("SHONEN SHOOTOUT");
        Button playButton = new Button("PLAY");
        Button aboutButton = new Button("ABOUT");
        Button tutorialButton = new Button("TUTORIAL");

        playButton.setOnAction(event -> {
            Main mainApp = new Main();
            mainApp.start(primaryStage); // This will need to call the appropriate method in Main
        });

        // Add all controls to the VBox layout
        layout.getChildren().addAll(titleLabel, playButton, aboutButton, tutorialButton);

        Scene scene = new Scene(layout, 1920, 1080);
        primaryStage.setTitle("Main Menu");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
