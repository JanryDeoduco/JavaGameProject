package miniprojtemplate;
import javafx.scene.image.Image;

public class Fire extends Sprite{
	final static int MAX_FIRE = 5;
	public final static Image FIRE_IMAGE = new Image("images/fire.gif",Fire.FIRE_WIDTH,Fire.FIRE_WIDTH,false,false);
	public final static Image INITIAL_IMAGE = new Image("images/initialFire.gif",Fire.FIRE_WIDTH,Fire.FIRE_WIDTH,false,false);
	public final static int FIRE_WIDTH=150;
	public Fire(int x, int y) {
		super(x, y);
		this.loadImage(Fire.INITIAL_IMAGE);
	}

	void die(){ //triggers when 1 hp is lost
		this.visible = false;
	}
}
