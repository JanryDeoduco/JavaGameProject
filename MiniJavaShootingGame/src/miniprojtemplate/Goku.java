package miniprojtemplate;

import javafx.util.Duration;


import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javafx.event.EventHandler;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.event.ActionEvent; // Import corrected

public class Goku extends Sprite {
	public static final int MAX_GOKU_SPEED = 5;
	public final static Image GOKU_INTRO = new Image("images/GOKU-INTRO.gif",Goku.GOKU_WIDTH*4.5,Goku.GOKU_WIDTH*4.5,false,false);
	public final static Image GOKU_IMAGE = new Image("images/GOKU-STEADY.gif",Goku.GOKU_WIDTH,Goku.GOKU_WIDTH,false,false);
	public static Image GOKU_BLAST = new Image("images/GOKU-BLAST.gif",Goku.GOKU_WIDTH*1.5,Goku.GOKU_WIDTH*1.3,false,false);
	
	public final static int GOKU_WIDTH=150;
	public boolean alive;
	public static final int MAX_HP = 3;
	//attribute that will determine if a fish will initially move to the right
	public boolean isdead;
	private boolean moveRight;
	private boolean moveUp;
	private int speed;
	private boolean initialMove;
	private boolean pause;
	private boolean shootingState;
	private int iter;
	private int shootIter;
	private ArrayList<Blast> blast;
	private ArrayList<Kame> wave;
	public int hp;
	public boolean ultiReady;
	private int iter2;
	private int iter3;
	private boolean finished;
	private boolean waveStart;
	
	
	Goku(int x, int y){ //initialize attributes
		super(x,y);
		this.alive = true;
		Random random = new Random();
		this.speed = 1;
		this.initialMove = true;
		this.pause = false;
		this.isdead=false;
		this.shootingState = false;
		this.iter = 0;
		this.shootIter = 0;
		this.iter2 = 0;
		this.iter3 = 0;
		this.finished = false;
		// Randomly set moveRight's initial value
	    this.moveRight = random.nextBoolean();
	    this.blast = new ArrayList<Blast>();
	    this.wave = new ArrayList<Kame>();
	    this.hp = MAX_HP;
	    this.ultiReady = false;
	    this.waveStart = false;

	
	}

	//method for goku moving vertically and do kamehamewave with given interval
		void move() {
		    this.speed = 5;
		    this.shootingState = true;
		    
		    
		    if (this.initialMove = true){
				if (x >1300 ){
					x -= speed;
					this.loadImage(Goku.GOKU_INTRO);
				}
				else{
					this.initialMove = false;
					this.pause = true;
				}
			}

			if (this.pause == true){
				if (this.iter < 200){
					this.iter++;
					//this.loadImage(Goku.GOKU_INTRO);

				}
				else{
					this.pause = false;
					//this.run = true;
				}
			}
			
			if (this.initialMove == false && this.pause == false){
			    if (this.iter < 800) {
			        waveStart = false;
			    	
			        this.loadImage(Goku.GOKU_BLAST);
			        iter2 = 0;
			        this.finished = false;
			        this.iter3 = 0;
			        if (moveUp) {
			            if (y < GameStage.WINDOW_HEIGHT - GOKU_WIDTH*2) {
			                y += speed;
			            } else {
			                moveUp = false;
			            }
			        } else {
			            if (y > 0) {
			                y -= speed;
			            } else {
			                moveUp = true;
			            }
			        }
		
			        if (this.shootIter < 70) {
			            this.shootIter++;
			        } else {
			        	AudioClip blst = new AudioClip("file:src/images/Ki.mp3");
						
						blst.play();
			        	shoot();
			            this.shootIter = 0;
			        }
			        if (this.iter == 799) {
			            startWave(); // Start the wave at iteration 499
			        }
			        this.iter++;
			    } 
		    else {
		    	
		        
		        if (iter2 < 330) { // wait for a second before doing ultimate
		        	if (waveStart == true) {
			        	final Image GOKU_WAVE = new Image("images/GOKU-WAVE.gif",Goku.GOKU_WIDTH*2.8,Goku.GOKU_WIDTH*3,false,false);
			        	this.loadImage(GOKU_WAVE); 
			        	waveStart = false;
			        }
		        	iter2++;
		        } 
		        else {
		            System.out.println("Eto na");  
		            final Image WAVE_EXECUTE = new Image("images/HAA.gif",Goku.GOKU_WIDTH*2.8,Goku.GOKU_WIDTH*3,false,false);
		            this.loadImage(WAVE_EXECUTE);
		            AudioClip kame = new AudioClip("file:src/images/kame.mp3");
					
					kame.play();
		            ultimate(); // After 5 seconds of movement, trigger the ultimate
		            
		            this.finished = true;
		            
		            // Increment iter to prevent immediate re-entry into this block
		            if (iter3 < 30) { // wait for a few seconds before doing ultimate
		            	//this.loadImage(GOKU_IMAGE);
		            	iter3++;
		            } else {
		                // Reset the iteration count for the next movement phase
		                iter = 0;
		                
		            }
		        }
		    }
			}
		}

	void startWave() { ////////////////////////////////////////////
	    waveStart = true; // Set waveStart flag to true to indicate the wave should start
	}




	void ultimate() {
        if (this.finished == false){
			Random random = new Random();
	        int randomNumber = random.nextInt(3) + 1;
	        System.out.println(randomNumber);
	        if (randomNumber == 1) {
	            moveGokuToPosition(30); // Move to upper part
	        } else if (randomNumber == 2) {
	            moveGokuToPosition(200); // Move to mid part
	        } else if (randomNumber == 3) {
	            moveGokuToPosition(500); // Move to lower part
	        }

	        kamehameha();
        }

	}

	private static final double POSITION_CHANGE_SPEED = 0.001;

	private void moveGokuToPosition(int targetY) { ////////////////////////
	    if ((int) this.y != targetY) {
	        if (this.y > targetY) {
	            this.y -= POSITION_CHANGE_SPEED;
	            if (this.y < targetY) {
	                this.y = targetY;
	            }
	        } else {
	            this.y += POSITION_CHANGE_SPEED;
	            if (this.y > targetY) {
	                this.y = targetY;
	            }
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

	//method that will get the bullets 'shot' by Ichigo
	public ArrayList<Blast> getBullets(){ /////////////////////////////
		return this.blast;
	}

	public ArrayList<Kame> getWave(){ ///////////////////////////
		return this.wave;
	}

	public void shoot(){
		//compute for the x and y initial position of the bullet
		int x = (int) (this.x - this.width);
		int y = (int) (this.y + this.height/2);


		Blast b = new Blast(x, y);

		//append to arraylist
		this.blast.add(b);
    }

	public void kamehameha(){
		//compute for the x and y initial position of the bullet
		int x = (int) (this.x - this.width);
		int y = (int) (this.y - (this.height/2));
		

		Kame wave = new Kame(x, y);

		//append to arraylist
		this.wave.add(wave);

    }
}
