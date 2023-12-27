package miniprojtemplate;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Blast extends Sprite {
	private final int BULLET_SPEED = 10;
	public final static Image BULLET_IMAGE = new Image("images/KI-BLAST.gif",Blast.BULLET_WIDTH,Blast.BULLET_WIDTH,false,false);
	public final static int BULLET_WIDTH = 100;

	public Blast(int x, int y){
		super(x,y);
		this.loadImage(Blast.BULLET_IMAGE);
	}
	
	
	//method that will move/change the x position of the bullet
	public void move(){
		this.x -= BULLET_SPEED;
	}


}