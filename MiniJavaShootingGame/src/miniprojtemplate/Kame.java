package miniprojtemplate;

import java.util.ArrayList;

import javafx.scene.image.Image;


public class Kame extends Sprite{
	private final static int WAVE_SPEED = 10;
	public static int WAVE_HEIGHT = 500;
	public static int WAVE_WIDTH = 1980;
	public static Image WAVE_IMAGE = new Image("images/wave2.gif",Kame.WAVE_WIDTH,Kame.WAVE_HEIGHT,false,false);
	public Kame(int x, int y) {
		super(x, y);
		this.y += 160;
		this.x += 700;
		this.loadImage(Kame.WAVE_IMAGE);
	}

	//method to move the wave
	public void move(){
		this.x -= WAVE_SPEED;

	}


}

 