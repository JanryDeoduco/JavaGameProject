package miniprojtemplate;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Explode extends Sprite {
	private final int BULLET_SPEED = 10;
	//public final static Image EXPLODE_IMAGE = new Image("images/Explode.gif",Explode.EXPLODE_WIDTH,Explode.EXPLODE_WIDTH,false,false);
	public final static int EXPLODE_WIDTH = 100;
	public Explode(int xPos, int yPos) {
		super(xPos, yPos);
		//this.loadImage(EXPLODE_IMAGE);
	}
	


}