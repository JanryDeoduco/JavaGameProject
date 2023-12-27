package miniprojtemplate;

import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;


import javafx.event.EventHandler;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent; // Import corrected

public class Luffy extends Sprite {
	public static final int MAX_SPEED = 5;
	public final static Image LUFFY_IMAGE = new Image("images/LUFFY-TRANSFORM.gif", Luffy.LUFFY_WIDTH+100, Luffy.LUFFY_WIDTH+100, false, false);
	public final static Image LUFFY_ENTRY = new Image("images/luffy-entry.gif", Luffy.LUFFY_WIDTH+30, Luffy.LUFFY_WIDTH+30, false, false);
	public final static Image LUFFY_RUN = new Image("images/LUFFY-TRANSFORMED-FLOAT.gif",Luffy.LUFFY_WIDTH + 120,Luffy.LUFFY_WIDTH+120,false,false);
	public final static Image LUFFY_ATTACK = new Image("images/LUFFY-ATTACK.gif",Luffy.LUFFY_WIDTH + 200,Luffy.LUFFY_WIDTH+100	,false,false);
	public final static Image LUFFY_PUNCH = new Image("images/PUNCH.gif",Luffy.LUFFY_WIDTH+75,Luffy.LUFFY_WIDTH+150,false,false);
	public final static Image LUFFY_AIR = new Image("images/AIR.gif",Luffy.LUFFY_WIDTH+75,Luffy.LUFFY_WIDTH+150,false,false);
	public final static int LUFFY_WIDTH=200;
	public static final int MAX_HP = 3;

	private boolean alive;
	public int hp;
	private boolean punch;
	//attribute that will determine if a fish will initially move to the right
	private boolean moveRight;
	private boolean moveUp;
	private boolean dash;
	private int speed;
	private boolean initialMove;
	private boolean pause; 	
	private boolean pause2;
	private boolean shootingState;
	private int iter;
	private int iter2;
	private int iter3;
	private boolean run;
	private int shootIter;
	private int lastXBeforePunch;
	private int lastYBeforePunch;
	private ArrayList<LuffyPunch> slash;
	private ImageView fishImageView; // Create an ImageView for the fish image

	//public ImageView fishImageView = new ImageView();
    //public static ImageViewSprite FISH_IMAGE = new ImageViewSprite(fishImageView, new Image("images/ichigoSheet.png"), 6, 1, 6, 200, 200, 60);

	Luffy(int x, int y){
		super(x,y);
		this.lastXBeforePunch = x;
	    this.lastYBeforePunch = y;
		this.punch=false;
		this.alive = true;
		this.run = false;
		this.loadImage(Luffy.LUFFY_IMAGE);
		this.hp = MAX_HP;
		Random random = new Random();
		this.speed = 1;
		this.initialMove = true;
		this.pause = false;
		this.pause2= false;
		this.shootingState = false;
		this.iter = 0;
		this.iter2 = 0;
		this.iter3 = 0;
		this.shootIter = 0;
		this.dash=false;
		// Randomly set moveRight's initial value
	    this.moveRight = false;
	    this.slash = new ArrayList<LuffyPunch>();


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

	//method that changes the x position of ichigo
	
	
	void move(){ //makes a move using an interval for shooting and dashing
		
		if (this.initialMove = true){
			if (x >1700){
				x -= speed;
				this.loadImage(Luffy.LUFFY_ENTRY);
			}
			else{
				this.initialMove = false;
				this.pause = true;
			}
		}
		
		if (this.pause == true){
		    this.y = this.y-0;
		    if (this.iter < 130){
		        this.iter++;
		        this.loadImage(Luffy.LUFFY_IMAGE);
		    }
		    else{
		        this.pause = false;
		        this.run = true;
		    }
		}

		
		if (this.run == true) {
			this.loadImage(Luffy.LUFFY_ATTACK);
			
			
		}
		

	      
	    
		
		
		if (this.initialMove == false && this.pause == false){
			
			
			this.speed = 5;
			this.shootingState = true;
			if(iter<300) {
				
			}else {
				if(iter2<70) {
					this.dash=true;
					this.moveUp=false;
					this.shootingState=false;
					this.run=false;
					this.loadImage(LUFFY_AIR);
					Random random = new Random();
					int randomValue = 20 + random.nextInt(21);
					if(this.x-randomValue >=0) {
						this.x=this.x-randomValue;
						this.loadImage(LUFFY_PUNCH);
					}
				}else {
					this.dash=false;
					this.moveUp=true;
					this.shootingState=true;
					this.run=true;
					this.iter2=0;
					this.iter=0;
					this.x=1600;
				}iter2++;
			}iter++;
			
			if (moveUp){
				if (y < GameStage.WINDOW_HEIGHT - LUFFY_WIDTH) {
		           
		            y += speed;

		        }
				else {
		           
		            moveUp = false;
		        }
			}

			else {
		        if (y > 0) {
		            
		            y -= speed;

		        }
		        else {
		           
		            moveUp = true;
		        }
		    }
		}
		
		
		
		
		
		if (this.shootingState == true){
			if (this.shootIter < 65){
				this.shootIter++;
				
			}
			else{
				
				shoot();
				this.shootIter = 0;
			}
		}
		
		





	}

	//getter
	public boolean isAlive() {
		return this.alive;
	}



	void die() {
		this.alive = false;
	}

	//method that will get the bullets 'shot' by Luffy
	public ArrayList<LuffyPunch> getBullets(){
		return this.slash;
	}

	public void shoot(){ //add luffy bullet to aerrat
		//compute for the x and y initial position of the bullet
		int x = (int) (this.x - this.width);
		int y = (int) (this.y + this.height/2);

		LuffyPunch slash = new LuffyPunch(x, y);

		//append to arraylist
		this.slash.add(slash);
    }
	


}
