package miniprojtemplate;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Transform extends Sprite {
	private final int BULLET_SPEED = 10;
	//public final static Image EXPLODE_IMAGE = new Image("images/Explode.gif",Explode.EXPLODE_WIDTH,Explode.EXPLODE_WIDTH,false,false);
	public final static int TRANSFORM_WIDTH = 150;
	public Transform(int xPos, int yPos) {
		super(xPos, yPos);
		//this.loadImage(EXPLODE_IMAGE);
	}
	


}