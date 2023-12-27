package miniprojtemplate;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;


import javafx.scene.image.Image;

import java.util.TimerTask;


public class Ship extends Sprite{
	private String name;
	private int strength;
	private boolean alive;
	public int awakeningLevel;
	public boolean isAwakened;
	private Timer awakenedTimer;
	private ArrayList<Bullet> bullets;
	private ArrayList<Fire> flames;
	public final static Image SHIP_IMAGE = new Image("images/NARUTO.gif",Ship.SHIP_WIDTH,Ship.SHIP_WIDTH,false,false);
	public final static Image AWAKENED_MODE = new Image("images/SIX-PATHS.gif",Ship.SHIP_WIDTH,Ship.SHIP_WIDTH,false,false);
	public final static Image NARUTO_RUN = new Image("images/NARUTO-RUN.gif",Ship.SHIP_WIDTH,Ship.SHIP_WIDTH,false,false);
	final static int SHIP_WIDTH = 150;
	public final static int MAX_LIFE = 10;
	public int currentLife;
	public boolean isDamaged;
	public boolean transformationEnded;
	public boolean running;
	public GameTimer gt;
	private boolean transformed;
	public boolean isdead;
	
    private static long lastFireTime = 0;
    private static final long FIRE_RATE = 300;
	
	public Ship(String name, int x, int y){
		super(x,y);
		this.isdead = false;
		this.name = name;
		Random r = new Random();
		this.strength = r.nextInt(151)+100;
		this.alive = true;
		this.bullets = new ArrayList<Bullet>();
		this.loadImage(Ship.NARUTO_RUN);
		this.awakeningLevel = 0;
		this.isAwakened = false;
		this.currentLife = MAX_LIFE;
		this.isDamaged = false;
		this.transformationEnded = false;
		this.running = false;
		this.transformed = false;
		
	}

	public boolean isAlive(){ 
		if(this.alive) return true;
		return false;
	}
	public String getName(){
		return this.name;
	}

	public void getDamaged(){ //subtracts naruto's hp
		this.currentLife--;
		this.isDamaged = true;
	}

	public void die(){ //die
    	this.alive = false;
    }

	public void transform() { //changes the image of naruto when transforming
	    if (awakeningLevel == 5) {
	        final Image TRANSFORM_IMAGE = new Image("images/TRANSFORM.gif", Ship.SHIP_WIDTH*1.3, Ship.SHIP_WIDTH*1.3, false, false);
	        this.loadImage(TRANSFORM_IMAGE);

	        // Wait for 1 second before changing to AWAKENED_MODE
	        Timer timer = new Timer();

	        TimerTask task = new TimerTask() {
	            @Override
	            public void run() {
	                // Load AWAKENED_MODE image after 1 second delay
	                loadImage(AWAKENED_MODE);
	                isAwakened = true;
	                timer.cancel(); // Cancel the timer after the image change
	            }
	        };

	        // Schedule the task to run after 1 second (1000 milliseconds)
	        timer.schedule(task, 1000);
	    } else {
	        this.loadImage(NARUTO_RUN);
	        this.isAwakened = false;
	    }
	}

	
	


	void awakenedLimit() { //handles the limit of naruto's awakening
		if (this.awakeningLevel == 5){
			this.awakeningLevel = 0;
	        Timer timer = new Timer();

	        TimerTask task = new TimerTask() {
	            @Override
	            public void run() {
	                // Your task logic goes here
	                System.out.println("Task executed after delay!");
	                transformationEnded = true; // Set the transformation end flag to true
	                loadImage(NARUTO_RUN);
	        	    isAwakened = false;
	        	    awakeningLevel = 0; // Reset the awakening level to 0
	                timer.cancel();
	            }
	        };

	        // Schedule the task to run after 10 seconds (10000 milliseconds)
	        timer.schedule(task, 10000);
		}

	}





	//method that will get the bullets 'shot' by the ship
	public ArrayList<Bullet> getBullets(){
		return this.bullets;
	}

	//method called if spacebar is pressed
	public void shoot(){
		long currentTime = System.currentTimeMillis();
        if (currentTime - lastFireTime >= FIRE_RATE) {
            int x = (int) (this.x + this.width + 20);
            int y = (int) (this.y + this.height / 4);
            Bullet newBullet = new Bullet(x, y+10);
            if (isAwakened == true) {
                newBullet.loadImage(Bullet.ENHANCED_BULLET_IMAGE);
            }
            this.bullets.add(newBullet);
            lastFireTime = currentTime;
		}

		//append to arraylist
    }

	//method called if up/down/left/right arrow key is pressed.
	public void move() {
		if (this.x + this.dx >= 0 && this.x + this.width + this.dx <= GameStage.WINDOW_WIDTH) {
	        this.x += this.dx;
	    }

	    if (this.y + this.dy >= 0 && this.y + this.height + this.dy <= GameStage.WINDOW_HEIGHT) {
	        this.y += this.dy;
	    }

	}



}
