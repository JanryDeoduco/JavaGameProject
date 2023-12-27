package miniprojtemplate;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.nio.file.Paths;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class GameStage {
    public static final int WINDOW_HEIGHT = 1000;
    public static final int WINDOW_WIDTH = 1900;
    private Scene scene;
    private Stage stage;
    private Stage gameover;
    private Group root;
    private Canvas canvas;
    private GraphicsContext gc;
    private GameTimer gametimer;

    private Text timerText; // UI element to display the timer
    private Timeline timeline; // Timeline for countdown
    private MediaPlayer mediaPlayer;
    
    // The class constructor
    public GameStage() {
        this.root = new Group();
        this.scene = new Scene(root, GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT, Color.CADETBLUE);
        this.canvas = new Canvas(GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
        this.gc = canvas.getGraphicsContext2D();

        // Load the image for the background
        Image backgroundImage = new Image("images/BG.gif", GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT, false, false);
        
        
        // Create an ImageView with the background image
        ImageView backgroundView = new ImageView(backgroundImage);
        this.root.setScaleX(0.8); // Set a scale factor for X-axis
        this.root.setScaleY(0.8); // Set a scale factor for Y-axis
        backgroundView.setFitWidth(2020);
        backgroundView.setFitHeight(1380);
        backgroundView.setTranslateX(-20); // Move 100 pixels to the right
        backgroundView.setTranslateY(-100); // Move 50 pixels down
        

        // Add the background ImageView to the root
        this.root.getChildren().add(backgroundView);

        // Instantiate an animation timer
        this.gametimer = new GameTimer(this.gc, this.scene);

        // Initialize the timer UI
        timerText = new Text("00:00");
        timerText.setX(50); // Position the timerText on your scene
        timerText.setY(50);
        timerText.setFill(Color.WHITE); // Set the color of the timer text
        timerText.setStyle("-fx-font: 50 arial;"); // Set the style of the timer text

        // Add the timerText to the scene
        this.root.getChildren().add(timerText);

        // Add the canvas on top of the background image
        this.root.getChildren().add(canvas);

        // Start the countdown timer
        startCountdownTimer();
        
        //music();
        
        
    }

    private void startCountdownTimer() {
        int[] time = new int[]{0}; // Starting time in seconds

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            time[0]++;
            int minutes = time[0] / 60;
            int seconds = time[0] % 60;
            timerText.setText(String.format("%02d:%02d", minutes, seconds));
        }));
        
        timeline.setCycleCount(Timeline.INDEFINITE); // Make it repeat indefinitely
        timeline.play();
    }

    
    
    public void music() {
        try {
            String s = "C:/Users/Janry/Downloads/Copy of project/src/images/opMusic.mp3";
            Media h = new Media(Paths.get(s).toUri().toString());
            mediaPlayer = new MediaPlayer(h);
            mediaPlayer.play();
        } catch (Exception e) {
            System.err.println("Error playing music: " + e.getMessage());
        }
    }
    
   
    
    
    public void endGame() {
    	
    	// Create a new stage for MainOver
        Stage about = new Stage();

        // Create a new instance of MainOver
        MainOver mainOver = new MainOver();
        stage.close();
        // Pass the gameover stage to MainOver
        mainOver.start(gameover);

     // Close the current game stage
       

        // Start the MainOver stage

        System.out.println("Game Over");
    }
    
    public void about() {
    	
    	// Create a new stage for about
        Stage abt = new Stage();

        // Create a new instance of About
        About about = new About();

        // Pass the gameover stage to MainOver
        about.start(abt);

     // Close the current game stage
//        stage.close();

        System.out.println("About");
    }
    
    public void tutorial() {
    	
    	// Create a new stage for about
        Stage tutor = new Stage();

        // Create a new instance of About
        Instruction instruct = new Instruction();

        // Pass the gameover stage to MainOver
        instruct.start(tutor);

//     // Close the current game stage
//        stage.close();

        // Start the MainOver stage

        System.out.println("About");
    }

    
    

    // Method to add the stage elements
    public void setStage(Stage stage) {
        this.stage = stage;

        // Set stage elements here
        this.stage.setTitle("SHONEN SHOOTOUT");
        this.stage.setScene(this.scene);
        
        // Invoke the start method of the animation timer
        
        this.gametimer.start();
//        String filePath = "file:///C:/Users/Janry/Downloads/Copy%20of%20project/src/miniprojtemplate/images/opMusic.mp3";
//        AudioClip bgm = new AudioClip(filePath);
        
        this.stage.show();
        
        
        
    }

}
