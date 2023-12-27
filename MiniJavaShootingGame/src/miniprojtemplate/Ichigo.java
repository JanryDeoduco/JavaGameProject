package miniprojtemplate;

import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;


import javafx.event.EventHandler;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.event.ActionEvent; // Import corrected

public class Ichigo extends Sprite {
	public static final int MAX_SPEED = 5;
	public final static Image ICHIGO_IMAGE = new Image("images/edited.gif",Ichigo.ICHIGO_WIDTH,Ichigo.ICHIGO_WIDTH,false,false);
	public final static Image ICHIGO_RUN = new Image("images/ICHIGO-RUN.gif",Ichigo.ICHIGO_WIDTH + 50,Ichigo.ICHIGO_WIDTH-20,false,false);
	public final static Image ICHIGO_ATTACK = new Image("images/ICHIGO-ATTACK.gif",Ichigo.ICHIGO_WIDTH + 100,Ichigo.ICHIGO_WIDTH + 80,false,false);
	public final static int ICHIGO_WIDTH=150;
	public static final int MAX_HP = 3;

	private boolean alive;
	public int hp;

	private boolean moveRight;
	private boolean moveUp;
	private int speed;
	private boolean initialMove;
	private boolean pause;
	public boolean shootingState;
	private int iter;
	private boolean run;
	private int shootIter;
	private ArrayList<IchigoSlash> slash;
	private AudioClip slsh;
	private ImageView fishImageView; // Create an ImageView for the fish image
	public boolean attackMode;

	//public ImageView fishImageView = new ImageView();
    //public static ImageViewSprite FISH_IMAGE = new ImageViewSprite(fishImageView, new Image("images/ichigoSheet.png"), 6, 1, 6, 200, 200, 60);

	Ichigo(int x, int y){
		super(x,y);
		this.alive = true;
		this.run = false;
		this.loadImage(Ichigo.ICHIGO_IMAGE);
		this.hp = MAX_HP;
		Random random = new Random();
		this.speed = 1;
		this.initialMove = true;
		this.pause = false;
		this.shootingState = false;
		this.iter = 0;
		this.shootIter = 0;
		// Randomly set moveRight's initial value
	    this.moveRight = random.nextBoolean();
	    this.slash = new ArrayList<IchigoSlash>();
	    this.attackMode = false;


	 /* Add a delay before moving vertically
	    Timeline timeline = new Timeline(new KeyFrame(
	            Duration.seconds(2), // Delay duration (2 seconds in this example)
	            new EventHandler<ActionEvent>() {
	                @Override
	                public void handle(ActionEvent event) {
	                    initialMove = false; // After 2 seconds, set initialMove to false
	                }
	            }));
	    timeline.play(); // Start the delay timer*/
	}

	//walks first and then move vertically while slashing at the same time
	void move(){
		if (this.initialMove = true){
			this.attackMode = false;
			if (x >1600){
				x -= speed;
				this.loadImage(Ichigo.ICHIGO_RUN);
			}
			else{
				this.initialMove = false;
				this.pause = true;
			}
		}

		if (this.pause == true){
			if (this.iter < 200){
				this.iter++;
				this.loadImage(Ichigo.ICHIGO_IMAGE);

			}
			else{
				this.pause = false;
				this.run = true;
			}
		}
		this.attackMode = true;
		if (this.run == true) {
			this.loadImage(Ichigo.ICHIGO_ATTACK);
			
		}

		if (this.initialMove == false && this.pause == false){
			this.speed = 5;
			this.shootingState = true;
			if (moveUp){
				if (y < GameStage.WINDOW_HEIGHT - ICHIGO_WIDTH) {
		            // Move the fish to the right if it hasn't reached the right boundary yet
		            y += speed;

		        }
				else {
		            // Change direction if the fish reaches the right boundary
		            moveUp = false;
		        }
			}

			else {
		        if (y > 0) {
		            // Move the fish to the left if it hasn't reached the left boundary yet
		            y -= speed;

		        }
		        else {
		            // Change direction if the fish reaches the left boundary
		            moveUp = true;
		        }
		    }
		}

		if (this.shootingState == true){
			if (this.shootIter < 75){
				this.shootIter++;
				
			}
			else{
				slsh = new AudioClip("file:src/images/slash.mp3");
				
				slsh.play();
				shoot();
				this.shootIter = 0;
			}
		}





	}

	//getter
	public boolean isAlive() {
		return this.alive;
	}



	void die() { //die
		this.alive = false;
	}

	//method that will get the bullets 'shot' by Ichigo
	public ArrayList<IchigoSlash> getBullets(){
		return this.slash;
	}

	public void shoot(){
		//compute for the x and y initial position of the bullet
		int x = (int) (this.x - this.width);
		int y = (int) (this.y + this.height/2);


		IchigoSlash slash = new IchigoSlash(x, y);

		//append to arraylist
		this.slash.add(slash);
    }
}
