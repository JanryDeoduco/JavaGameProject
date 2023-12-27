package miniprojtemplate;

import javafx.scene.image.Image;

public class Life extends Sprite {
	final static int MAX_LIFE = 10;
	public final static Image LIFE_IMAGE = new Image("images/heart.png",Life.LIFE_WIDTH,Life.LIFE_WIDTH,false,false);
	public final static int LIFE_WIDTH=50;
	public Life(int x, int y) {
		super(x, y);
		this.loadImage(Life.LIFE_IMAGE);
	}

	void die(){ //triggers when 1 hp is lost
		this.visible = false;
	}
}
