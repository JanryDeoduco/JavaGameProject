package miniprojtemplate;

import java.util.Random;

import javafx.scene.image.Image;

public class Fish extends Sprite {
	public static final int MAX_FISH_SPEED = 5;
	public final static Image FISH_IMAGE = new Image("images/MARINE-RUN.gif",Fish.FISH_WIDTH,Fish.FISH_WIDTH,false,false);
	
	public final static int FISH_WIDTH=200;
	private boolean alive;
	//attribute that will determine if a fish will initially move to the right
	private boolean moveRight;
	private int speed;


	Fish(int x, int y){
		super(x,y);
		this.alive = true;
		this.loadImage(Fish.FISH_IMAGE);
		// Randomize speed of fish within a certain range
		Random random = new Random();
		this.speed = random.nextInt(MAX_FISH_SPEED) + 1;

		// Randomly set moveRight's initial value
	    this.moveRight = random.nextBoolean();
	}

	//method that changes the x position of the fish
	void move(){
		if (x < 0) {
            // Move the fish to the right if it hasn't reached the right boundary yet
            this.die();
        }

		else{
			x -= speed;
		}

	}

	//getter
	public boolean isAlive() {
		return this.alive;
	}

	void die() {
		this.alive = false;
	}
}
