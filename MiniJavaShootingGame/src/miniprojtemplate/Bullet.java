package miniprojtemplate;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Bullet extends Sprite {
	private final int BULLET_SPEED = 10;
	public final static Image BULLET_IMAGE = new Image("images/rasengan.gif",Bullet.BULLET_WIDTH,Bullet.BULLET_WIDTH,false,false);
	public final static Image ENHANCED_BULLET_IMAGE = new Image("images/rasengan.gif",Bullet.ENHANCED_BULLET_WIDTH,Bullet.ENHANCED_BULLET_WIDTH,false,false);
	public final static int BULLET_WIDTH = 50;
	public final static int ENHANCED_BULLET_WIDTH = 200;
	private boolean alive;
	public Bullet(int x, int y){
		super(x,y);
		this.loadImage(Bullet.BULLET_IMAGE);
		this.alive = true;
	}
	//method that will move/change the x position of the bullet
	public void move(){
		if (this.x > GameStage.WINDOW_WIDTH) {

            this.alive = false;
            this.visible = false;
        }

		else{
			this.x += BULLET_SPEED;
		}
	}


}