package miniprojtemplate;

import java.util.Random;

import javafx.scene.image.Image;

public class Orb extends Sprite{
	public final static Image ORB_IMAGE = new Image("images/orb.gif",Orb.ORB_WIDTH,Orb.ORB_WIDTH,false,false);
	public final static int ORB_WIDTH = 50;
	private boolean alive;



	Orb(int x, int y) {
		super(x,y);
		this.loadImage(Orb.ORB_IMAGE);
	}

	void die(){
		this.alive = false; //the orb will disappear
	}
}
