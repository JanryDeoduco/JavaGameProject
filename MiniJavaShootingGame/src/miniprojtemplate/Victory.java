package miniprojtemplate;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Victory extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Load the background image
        Image backgroundImage = new Image("images/BG.gif");

        // Create a background image
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false)
        );

        // Create a background with the image
        Background backgroundImg = new Background(background);

        // Create a layout with the background image
        Pane layout = new Pane();
        layout.setBackground(backgroundImg);

        // Create an ImageView for the GIF
        Image gifImage = new Image("images/VICTORY.gif");
        ImageView gifView = new ImageView(gifImage);
        gifView.setLayoutX(470); // Adjust X position of the GIF
        gifView.setLayoutY(100); // Adjust Y position of the GIF

        // Create a button with the BACK.png image
        Button backButton = createButtonWithImage("images/BACK.png");
        backButton.setLayoutX(880); // Adjust the X position of the button
        backButton.setLayoutY(550); // Adjust the Y position of the button
        backButton.setOnAction(event -> {
        	 primaryStage.close();
        	Stage mainStage = new Stage();
            Main mainMenu = new Main  (); // Assuming MainMenu is the class representing your main menu
            mainMenu.start(mainStage); // Navigate back to the main menu
           
        });


        layout.getChildren().addAll(gifView, backButton);

        // Create a scene with the layout
        Scene scene = new Scene(layout, 1900, 1000);

        primaryStage.setTitle("Main Over");
        primaryStage.setScene(scene);
        primaryStage.show();
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
