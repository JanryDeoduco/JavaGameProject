package miniprojtemplate;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Load the initial background image
//    	AudioClip bgm = new AudioClip("file:src/images/INTRO.mp3");
//        bgm.play();
        Image initialBackgroundImage = new Image("images/MAINBG.gif",1900,1000,false,false);
        BackgroundImage initialBackground = new BackgroundImage(initialBackgroundImage,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));

        HBox layout = new HBox(10); // Changed VBox to HBox
        layout.setBackground(new Background(initialBackground));

        // Buttons with images
        Button playButton = createButtonWithImage("images/START.PNG"); 
        Button aboutButton = createButtonWithImage("images/ABOUT.PNG");
        Button tutorialButton = createButtonWithImage("images/INSTRUCTIONS.PNG");

        // Set positions for buttons using layout parameters
        double yLevel = 800; // Common Y-level for all buttons

        playButton.setTranslateX(700);
        playButton.setTranslateY(yLevel);

        aboutButton.setTranslateX(500);
        aboutButton.setTranslateY(yLevel);

        tutorialButton.setTranslateX(900);
        tutorialButton.setTranslateY(yLevel);

        // Initially, hide the buttons
        playButton.setVisible(false);
        aboutButton.setVisible(false);
        tutorialButton.setVisible(false);

        layout.getChildren().addAll(aboutButton, playButton, tutorialButton);

        // Scene setup
        Scene scene = new Scene(layout, 1900, 1000);
        primaryStage.setTitle("Main Menu");
         primaryStage.setScene(scene);
         primaryStage.show();
         
        // Change background after MAINBG.gif duration
        Duration gifDuration = Duration.seconds(13); // Duration of the gif
        Timeline timeline = new Timeline(
                new KeyFrame(gifDuration, e -> {

                    
                    Image newBackgroundImage = new Image("images/BGPNG.png",1900,1000,false ,false);
                    
                    BackgroundImage newBackground = new BackgroundImage(newBackgroundImage,
                            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                            BackgroundPosition.DEFAULT,
                            new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
                    layout.setBackground(new Background(newBackground));

                    // Show the buttons after the GIF animation
                    playButton.setVisible(true);
                    aboutButton.setVisible(true);
                    tutorialButton.setVisible(true);
                    
                    // Set actions for the buttons after they're visible
                    playButton.setOnAction(event -> {
                    	GameStage theGameStage = new GameStage();
                		theGameStage.setStage(primaryStage);; // This will need to call the appropriate method in Main
                		
                    });

                    aboutButton.setOnAction(event -> {
                        // Define the action when the about button is clicked
                        // Replace this with your intended behavior
                    	Stage currentStage = (Stage) aboutButton.getScene().getWindow();
                        currentStage.close();
                        
                    	GameStage theGameStage = new GameStage();
                		theGameStage.about();
                       
                        System.out.println("About button clicked");
                        
                    });

                    tutorialButton.setOnAction(event -> {
                        // Define the action when the tutorial button is clicked
                        // Replace this with your intended behavior
                    	
                    	Stage currentStage = (Stage) tutorialButton.getScene().getWindow();	
                    	GameStage theGameStage = new GameStage();
                    	currentStage.close();
              
                		theGameStage.tutorial();
                		
                        System.out.println("Tutorial button clicked");
                    });
                })
        );
        timeline.setCycleCount(1); // Change background once
        timeline.play();
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

//package miniprojtemplate;
//
//import javafx.application.Application;
//import javafx.stage.Stage;
//
//public class Main extends Application {
//
//	public static void main(String[] args) {
//		launch(args);
//	}
//
//	public void start(Stage stage){
//		GameStage theGameStage = new GameStage();
//		theGameStage.setStage(stage);
//	}
//
//}

