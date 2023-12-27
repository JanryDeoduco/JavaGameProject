package miniprojtemplate;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class IchigoSlash extends Sprite {
	private final int BULLET_SPEED = 10;
	public final static Image BULLET_IMAGE = new Image("images/ICHIGO-BULLET.png",IchigoSlash.BULLET_WIDTH,IchigoSlash.BULLET_WIDTH,false,false);
	public final static int BULLET_WIDTH = 200;
	private boolean alive;
	
	
	public IchigoSlash(int x, int y){
		super(x,y);
		this.alive = true;
		this.loadImage(IchigoSlash.BULLET_IMAGE);
	}


	//method that will move/change the x position of the bullet
	public void move(){
		this.x -= BULLET_SPEED;
	}


}