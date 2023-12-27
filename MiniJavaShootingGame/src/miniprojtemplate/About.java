package miniprojtemplate;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class About extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Load the initial background image
        Image backgroundImage = new Image("images/SCREE.jpg", 1900, 1000, false, false);

        // Create an HBox layout
        HBox layout = new HBox(10);
        layout.setBackground(new Background(
                new BackgroundImage(backgroundImage,
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.DEFAULT,
                        new BackgroundSize(1900, 1000, false, false, true, false)
                )
        ));

        // Buttons with images
        Button playButton = createButtonWithImage("images/BACK.png");

        double yLevel = 800; // Common Y-level for all buttons
        playButton.setTranslateX(700);
        playButton.setTranslateY(yLevel);

        layout.getChildren().addAll(playButton);
        playButton.setOnAction(event -> {
        	
        	Stage mainStage = new Stage();
            Main mainMenu = new Main  (); // Assuming MainMenu is the class representing your main menu
            mainMenu.start(mainStage); // Navigate back to the main menu
            primaryStage.close();
        });

        // Scene setup
        Scene scene = new Scene(layout, 1900, 1000);
        primaryStage.setTitle("Main Menu");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Change background after MAINBG.gif duration
    }

    private Button createButtonWithImage(String imageName) {
        // Load the image
        Image buttonImage = new Image(imageName);

        // Create an ImageView with the image
        ImageView imageView = new ImageView(buttonImage);

        // Create a button with the ImageView as its graphic
        Button button = new Button();
        button.setGraphic(imageView);
        button.setStyle("-fx-background-color: transparent; -fx-padding: 0;"); // Make button background transparent

        return button;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
