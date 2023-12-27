package miniprojtemplate;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class LuffyPunch extends Sprite {
	private final int BULLET_SPEED = 10;
	public final static Image BULLET_IMAGE = new Image("images/LUFFY-BULLET.gif",LuffyPunch.BULLET_WIDTH,LuffyPunch.BULLET_WIDTH,false,false);
	public final static int BULLET_WIDTH = 200;

	public LuffyPunch(int x, int y){
		super(x,y);
		this.loadImage(LuffyPunch.BULLET_IMAGE);
	}


	//method that will move/change the x position of the bullet
	public void move(){
		this.x -= BULLET_SPEED;
	}


}