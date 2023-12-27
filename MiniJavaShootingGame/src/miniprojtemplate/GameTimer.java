package miniprojtemplate;

import java.io.File;
//import java.awt.Color;
//import java.awt.Font;
import java.util.ArrayList;

import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
//import java.io.File;

import java.util.Timer;
import java.util.TimerTask;

import javafx.scene.text.Font;
import javafx.scene.paint.Color;

import javafx.scene.media.AudioClip;

public class GameTimer extends AnimationTimer{
	//music
	
	
	
	
	
	
	
	//Variable Declarations
	private Timer flameTimer; // Declare the Timer variable
	private GraphicsContext gc;
	private Scene theScene;
	private Ship myShip;
	private Explode explode;
	private ArrayList<Fish> fishes;
	private ArrayList<Orb> orbs;
	private ArrayList<Explode> explosions;
	private ArrayList<Transform> transformation;
	private ArrayList<Life> lives;
	private ArrayList<Fire> flames;
	public static final int MAX_NUM_FISHES = 20;
	public static final float infinityValue = Float.POSITIVE_INFINITY; //infinite spawning limit of orbs
	public int killCount;
	private boolean ichigoBoss;
	private boolean gokuBoss;
	private boolean luffyBoss;
	private boolean normal;
	private ArrayList<Ichigo> ichigo;
	private ArrayList<Luffy> luffy;
	private static final long EXPLOSION_DURATION = 1_000_000_000;
	private long explosionStartTime = -1;
	private long transformStartTime = -1;
	private boolean isdead;
	
	private ArrayList<Goku> goku;
	private ArrayList<Kame> waves;
	private ArrayList<Blast> blast;
	private AudioClip bgm;
	
	
	//Construct GameTimer Class	
	GameTimer(GraphicsContext gc, Scene theScene){
		this.normal = true;
		this.ichigoBoss = false;
	    this.gokuBoss = false;
	    this.luffyBoss = false;
	    this.ichigo = new ArrayList<Ichigo>();
	    this.luffy = new ArrayList<Luffy>();
	    this.goku = new ArrayList<Goku>();
	    this.blast = new ArrayList<Blast>();
	    this.waves = new ArrayList<Kame>();
		this.gc = gc;
		this.killCount = 0;
		this.theScene = theScene;
		this.myShip = new Ship("Going merry",100,100);
		//instantiate the ArrayList of Fish
		this.fishes = new ArrayList<Fish>();
		//instantiate the ArrayList of Fish
		this.orbs = new ArrayList<Orb>();
		this.explosions = new ArrayList<Explode>();
		this.transformation = new ArrayList<Transform>();
		this.lives = new ArrayList<Life>();
		this.flames = new ArrayList<Fire>();
		//call the spawnFishes method
		this.spawnFishesTimer();
		this.spawnOrbsTimer();
		this.spawnLives();
		//if (ichigoBoss == true) {
			//this.spawnIchigo();
		//}	
		
		//call method to handle mouse click event
		this.handleKeyPressEvent();
		//this.spawnGoku();
		this.isdead = false;
	}

	@Override
	//override handle from animationTimer
	
	public void handle(long currentNanoTime) {
		this.gc.clearRect(0, 0, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);

		this.myShip.move();
		//this.myShip.run();
		flameTimer = new Timer();
	    
		moveBullets();
		moveFishes();
		moveLuffy();
		movePunch();
		
		this.myShip.render(this.gc);
		
		
		renderLuffy();
		renderPunch();
		handleCollisionsLuffy();
		handlePunchCollisions();
		
		
		
		
		renderFishes();
		
		renderBullets();
		renderOrbs();
		renderExplode();
		renderLives();
		renderFlames();
	
		//GOKUUUUUUUUUUU
		moveGoku();
		moveKiBlast();
		moveWave();
		
		
		renderGoku();
		renderWave();
		
		renderkiBlast();
		handleCollisionsGoku();
		//////////////////////////
		renderKillCount();
		handleIchigoStart();
		handleLuffyStart();
		handleGokuStart();
		
		if (explosionStartTime != -1) { //timer for explosion
	        long elapsedTime = currentNanoTime - explosionStartTime;

	        // If the explosion duration has passed, stop rendering the explosion
	        if (elapsedTime >= EXPLOSION_DURATION) {
	            explosionStartTime = -1; // Reset the explosion start time to stop rendering
	            this.explosions.clear(); // Clear the explosions list or handle as needed
	        }
	    }
		
		if (transformStartTime != -1) { //Timer for transformation
	        long elapsedTime = currentNanoTime - transformStartTime;

	        // If the explosion duration has passed, stop rendering the explosion
	        if (elapsedTime >= EXPLOSION_DURATION) {
	            transformStartTime = -1; // Reset the explosion start time to stop rendering
	            this.transformation.clear();
	        }
	    }
		//renderTransform();
		//for Ichigo
		//if (ichigoBoss == true) {
			System.out.println(ichigoBoss);
			renderIchigo();
			renderSlash();
			handleCollisionsIchigo();
			handleSlashCollisions();
			moveIchigo();
			moveSlash();
			
			
			
		//}
		handleCollisions();
		handleWaveCollisions();
		handleShipCollisions();
		handleNarutoOrbCollisionsHelper();
		handleKiBlastCollisions();
		
		
		//handleTransformation();
		narutoTransform();
		handleNarutoDeath();
		handleClearFlames();

		if (!myShip.isAlive()) {
		    // Ship is dead - perform actions to end the game
		    // For example:
			//Platform.exit(); // A method to stop the game loop or perform final actions
		}
		
		handleLuffyNarutoCollision();
		loseLife();
		handleNarutoSOund();
	}
	
	
	
	
	private void handleNarutoSOund() { //handles the main bg music
		if (this.normal == true) {
			//bgm.stop();
			this.normal = false;
			bgm = new AudioClip("file:src/images/narutoSound.mp3");
			bgm.setCycleCount(AudioClip.INDEFINITE);
			bgm.play();
			if (this.ichigoBoss == true || this.luffyBoss == true || this.gokuBoss == true) {
				bgm.stop();
			}
		}
		
//		else if (this.normal == false && this.ichigoBoss == true) {
//			this.ichigoBoss = false;
//			AudioClip bgm = new AudioClip("file:src/images/bleach.mp3"); //play audio
//	        bgm.play();
//	        bgm.setCycleCount(AudioClip.INDEFINITE);
//	        if (ichigo.size() <= 0) {
//	        	bgm.stop();
//	        }
//		}
	}
	
	/////////ALL LUFFY METHODS///////////////////////////////
	private void handleDashCollision() {
		myShip.getDamaged();
	}
	
	private void handleLuffyNarutoCollision() {
		for (Luffy f: this.luffy) {
			if (myShip.collidesWith(f)) {
                handleDashCollision();
                break; // Exit the loop after collision with one slash
            }
		}
	}
	private void handleLuffyStart() { //starts when a certain number of kills reached
		
		if (this.killCount ==6 && this.luffyBoss == false) {
			bgm.stop();
			bgm = new AudioClip("file:src/images/opMusic.mp3"); //play audio
	        bgm.play();
	        bgm.setCycleCount(AudioClip.INDEFINITE);
			this.normal = false;
			clearEnemies();
			this.luffyBoss = true;
			
			spawnLuffy(); // Spawn the Ichigo boss character
			
		}
		
	}
	private void renderLuffy() { ///////////////////////////////////////////////////
		for (Luffy f : this.luffy){
			f.render(this.gc);
		}
	}
	
	private void renderPunch() {///////////////////////////////////////////////////////
	    for (Luffy f : this.luffy) {
	        ArrayList<LuffyPunch> s = f.getBullets();

	        for (int i = 0; i < s.size(); i++) {
	            LuffyPunch punch = s.get(i);
	            punch.render(this.gc);
	        }
	    }
	}
	
	private void spawnLuffy(){ ////////////////////////////////////////////////////
		int x = GameStage.WINDOW_WIDTH + 100;
		int y = 500;
		Luffy newLuffy = new Luffy(x, y);

		//append to arraylist
		this.luffy.add(newLuffy);



	}
	
	private void movePunch() { //////////////////////////////////////////
	    for (Luffy f : this.luffy) {
	        ArrayList<LuffyPunch> s = f.getBullets();
	        
	        for (int i = 0; i < s.size(); i++) {
	            LuffyPunch punch = s.get(i);

	            if (punch.isVisible()) {
	                punch.move();
	                System.out.println("visible");
	            } else {
	                s.remove(i);
	                i--;
	            }
	        }
	    }
	}
	
	private void moveLuffy(){ //////////////////////////////////////////////////////////////
		//Loop through the fishes arraylist
		for(int i = 0; i < this.luffy.size(); i++){
			Luffy f = this.luffy.get(i);
			if (f.isAlive()){
				f.move();
			}
			else{
				f = luffy.remove(i);
				i--;
			}
		}
	}
	
	private void handleBulletLuffyCollision(Bullet bullet, Luffy luffy) { ///////////////////////////
	    // Perform actions upon collision (e.g., remove fish, update score, etc.)
	    luffy.hp--; // Example method to mark fish as dead
	    System.out.print(luffy.hp);
	}
	
	private void handleLuffyDeath(Bullet bullet, Luffy luffy) { ///////////////////////////////////
	    //a situation when luffy's hp is 0
	    if (luffy.hp <= 0) {
	    	luffy.die();
	    	this.luffyBoss = false;
	    }
	    
	}
	
	private void handlePunchNarutoCollision(Ship myShip, LuffyPunch punch) { //handles collision for luff's punch
	    myShip.getDamaged(); 
	    punch.visible = false;
	    System.out.println(myShip.isAlive());
	}
	
	private void handleCollisionsLuffy() { //process the collision between rasengan and luffy
	    ArrayList<Bullet> bullets = myShip.getBullets();
	    for (int i = 0; i < bullets.size(); i++) {
	        Bullet bullet = bullets.get(i);
	        for (int j = 0; j < luffy.size(); j++) {
	            Luffy f = luffy.get(j);
	            handleLuffyDeath(bullet,f);
	            if (bullet.collidesWith(f)) {
	                // Collision detected between bullet and ichigo
	                handleBulletLuffyCollision(bullet, f);
	                explode = new Explode(f.x, f.y);
	                explosions.add(explode);
	                final Image EXPLODE_IMAGE = new Image("images/Explode.gif",Explode.EXPLODE_WIDTH,Explode.EXPLODE_WIDTH,false,false);
	        	    explode.loadImage(EXPLODE_IMAGE);
	        	    if (explosionStartTime == -1) {
	        	        explosionStartTime = System.nanoTime();
	        	    }
	                bullets.remove(i); // Remove the bullet
	                i--; // Adjust index after removal
	                break; // Exit fish loop after collision
	            }
	        }
	    }
	}
	
	private void handlePunchCollisions() { //collision for luffy's bullet and naruto
		for (Luffy f : this.luffy) {
	        ArrayList<LuffyPunch> s = f.getBullets();
	        
	        for (int i = 0; i < s.size(); i++) {
	            LuffyPunch slash = s.get(i);
	            if (myShip.collidesWith(slash)) {
	                handlePunchNarutoCollision(myShip, slash);
	                break; // Exit the loop after collision with one slash
	            }
	        }
	    }
	}
	
	
	////////////ALL ICHIGO METHODS///////////////////////////////////////
	
	
	private void clearEnemies() { //remove all enemies on screen
		for (Fish f: this.fishes) {
			explode = new Explode(f.x, f.y);
		    final Image EXPLODE_IMAGE = new Image("images/Explode.gif",Explode.EXPLODE_WIDTH,Explode.EXPLODE_WIDTH,false,false);
		    explode.loadImage(EXPLODE_IMAGE);
		    f.die();
		    this.explosions.add(explode);
		    if (explosionStartTime == -1) {
		        explosionStartTime = System.nanoTime();
		    }
		}
		this.killCount++;
	}
	
	private void handleIchigoStart() {//starts when a certain number of kills reached
		
		if (this.killCount == 2 && this.ichigoBoss == false) {
			bgm.stop();
			bgm = new AudioClip("file:src/images/bleach.mp3"); //play audio
	        bgm.play();
	        bgm.setCycleCount(AudioClip.INDEFINITE);
			this.normal = false;
			clearEnemies();
			this.ichigoBoss = true;
			
			spawnIchigo(); // Spawn the Ichigo boss character
			
		}
		
	}
	
	private void handleGokuStart() { //starts when a certain number of kills reached
		
		if (this.killCount == 10 && this.gokuBoss == false) {
			bgm.stop();
			bgm = new AudioClip("file:src/images/goku.mp3"); //play audio
	        bgm.play();
	        bgm.setCycleCount(AudioClip.INDEFINITE);
			clearEnemies();
			this.gokuBoss = true;
			this.normal = false;
			spawnGoku(); // Spawn the Goku boss character
			
		}
		
	}
	
	
	
	
	
	private void renderIchigo() { //render ichigo to canvas
		if (this.ichigoBoss) {
			for (Ichigo i : this.ichigo){
				i.render(this.gc);
			}
		}
	}
	
	private void renderSlash() {// render slash to canvas
	    if (this.ichigoBoss) {
			for (Ichigo f : this.ichigo) {
		        ArrayList<IchigoSlash> s = f.getBullets();
	
		        for (int i = 0; i < s.size(); i++) {
		            IchigoSlash slash = s.get(i);
		            slash.render(this.gc);
		        }
		    }
	    }
	}
	
	
	
	private void spawnIchigo(){ //adding ichigo to array
		if (this.ichigoBoss) {
			int x = GameStage.WINDOW_WIDTH + 100;
			int y = 500;
			AudioClip bgm = new AudioClip("file:src/images/bleach.mp3"); //play audio
	        bgm.play();
	        bgm.setCycleCount(AudioClip.INDEFINITE);
			Ichigo newIchigo = new Ichigo(x, y);
	
			//append to arraylist
			this.ichigo.add(newIchigo);
			//this.ichigoBoss = false;
		}


	}
	
	
	
	
	private void moveSlash() { //adjust the x position of slash png
	    //if (this.ichigoBoss) {
			for (Ichigo f : this.ichigo) {
		        ArrayList<IchigoSlash> s = f.getBullets();
		        
		        for (int i = 0; i < s.size(); i++) {
		            IchigoSlash slash = s.get(i);
	
		            if (slash.isVisible()) {
		                slash.move();
		                System.out.println("visible");
		            } else {
		                s.remove(i);
		                i--;
		            }
		        }
		    }
	   // }
	}
	
	private void moveIchigo(){ //calls the method of ichigo for moving
		//if (this.ichigoBoss) {
			for(int i = 0; i < this.ichigo.size(); i++){
				Ichigo f = this.ichigo.get(i);
				if (f.isAlive()){
					f.move();
				}
				else{
					f = ichigo.remove(i);
					i--;
				}
			}
		//}
	}
	
	
	
	private void handleBulletIchigoCollision(Bullet bullet, Ichigo ichigo) { //collision between bullet and ichigo
	    //if (this.ichigoBoss) {
		    if (ichigo.shootingState == true) {
				ichigo.hp--;
			    explode = new Explode(ichigo.x, ichigo.y);
			    final Image EXPLODE_IMAGE = new Image("images/Explode.gif",Explode.EXPLODE_WIDTH,Explode.EXPLODE_WIDTH,false,false);
			    explode.loadImage(EXPLODE_IMAGE);
			    this.explosions.add(explode);
			    if (explosionStartTime == -1) {
			        explosionStartTime = System.nanoTime();
			    }
			    System.out.print(ichigo.hp);
		    }
	    //}
	}
	
	
	private void handleIchigoDeath(Bullet bullet, Ichigo ichigo) { //handles death of ichigo when hp is 0
	    //if (this.ichigoBoss) {
		    if (ichigo.hp <= 0) {
		    	bgm.stop();
		    	ichigo.die();
		    	this.ichigoBoss = false;
		    	this.normal = true;
		    	
		    }
	    //}
	}

	private void handleSlashNarutoCollision(Ship myShip, IchigoSlash slash) { //collision of slash and naruto
	    //if (this.ichigoBoss) {
			
	    	myShip.getDamaged(); // Example method to mark fish as dead
	    	slash.visible = false;
		    System.out.println(myShip.isAlive());
	    //}
	}

	private void handleCollisionsIchigo() { //collision of bullet and ichigo
		//if (this.ichigoBoss) {
			ArrayList<Bullet> bullets = myShip.getBullets();
		    for (int i = 0; i < bullets.size(); i++) {
		        Bullet bullet = bullets.get(i);
		        for (int j = 0; j < ichigo.size(); j++) {
		            Ichigo f = ichigo.get(j);
		            
		            if (bullet.collidesWith(f)) {
		                // Collision detected between bullet and ichigo
		            	handleIchigoDeath(bullet,f);
		            	handleBulletIchigoCollision(bullet, f);
		                bullets.remove(i); // Remove the bullet
		                i--; // Adjust index after removal
		                break; // Exit fish loop after collision
		            }
		        }
		    }
		//}
	}
	
	
	private void handleSlashCollisions() {  //collision of slash and naruto
		//if (this.ichigoBoss) {
			for (Ichigo f : this.ichigo) {
		        ArrayList<IchigoSlash> s = f.getBullets();
		        
		        for (int i = 0; i < s.size(); i++) {
		            IchigoSlash slash = s.get(i);
		            if (myShip.collidesWith(slash)) {
		                handleSlashNarutoCollision(myShip, slash);
		                break; // Exit the loop after collision with one slash
		            }
		        }
		    }
		//}
	}
	////////////////METHODS for GOKU/////////////////////////////
	//SIMILAR WITH ICHIGO METHODS
	private void renderGoku() {  ////////////////////////////
		if (this.gokuBoss) {
			for (Goku g : this.goku){
				g.render(this.gc);
			}
		}
	}
	
	private void renderkiBlast() { ////////////////////////////
	    for (Goku g : this.goku) {
	        ArrayList<Blast> s = g.getBullets();

	        for (int i = 0; i < s.size(); i++) {
	            Blast b = s.get(i);
	            b.render(this.gc);
	        }
	    }
	}
	
	private void renderWave() {  ///////////////////////
		for (Goku g : this.goku) {
	        ArrayList<Kame> w = g.getWave();

	        for (int i = 0; i < w.size(); i++) {
	            Kame wave = w.get(i);
	            wave.render(this.gc);
	        }
	    }
	}
	
	private void moveWave(){ /////////////////////////////////////////
		for (Goku g : this.goku) {
	        ArrayList<Kame> w = g.getWave();

	        for (int i = 0; i < w.size(); i++) {
	            Kame wave = w.get(i);

	            if (wave.isVisible()) {
	                moveWaveTimer(wave);
	            } else {
	            	wave.setVisible(false);
	            	w.remove(i);
	                i--;
	            }
	        }
	    }
	}
	
	private void moveWaveTimer(Kame wave) { ////////////////////////
	    Timer timer = new Timer();

	    TimerTask task = new TimerTask() {
	        @Override
	        public void run() {
	            // Check if wave's x is greater than 0
	            if (wave.x > -500) {
	                wave.move();
	            } else {
	                // If x is not greater than 0, cancel the timer
	                timer.cancel();

	                // Create a new timer to set isVisible to false after 1 second
	                Timer falseVisibilityTimer = new Timer();
	                falseVisibilityTimer.schedule(new TimerTask() {
	                    @Override
	                    public void run() {
	                        wave.setVisible(false);
	                    }
	                }, 2000); // Schedule the task to run after 1 second (1000 milliseconds)
	            }
	        }
	    };

	    // Schedule the task to run every 1 millisecond
	    timer.scheduleAtFixedRate(task, 0, 1);
	}
	
	private void spawnGoku(){ /////////////////////////////
		if (this.gokuBoss) {
			int x = GameStage.WINDOW_WIDTH - 200;
			int y = 500;
	
			Goku newGoku = new Goku(x, y);
	
			//append to arraylist
			this.goku.add(newGoku);
		}


	}
	
	private void spawnWave(){ //adds a big wave gif to array
		int x = 100;
		int y = 500;

		Kame newWave = new Kame(x, y);

		//append to arraylist
		this.waves.add(newWave);



	}
	
	private void moveKiBlast() { ///////////////////////
	    for (Goku g : this.goku) {
	        ArrayList<Blast> s = g.getBullets();

	        for (int i = 0; i < s.size(); i++) {
	            Blast b = s.get(i);

	            if (b.isVisible()) {
	                b.move();

	            } else {
	                s.remove(i);
	                i--;
	            }
	        }
	    }
	}
	
	//method that will move goku
	private void moveGoku(){ ////////////////////////
		for(int i = 0; i < this.goku.size(); i++){
			Goku g = this.goku.get(i);
			if (g.isAlive()){
					//f.startWave();
					g.move();




			}
			else{
				g = goku.remove(i);
				i--;
			}
		}
	}
	
	
	private void handleBulletGokuCollision(Bullet bullet, Goku goku) { ///////////////
	    if (goku.hp <=0 && myShip.isdead == false){
	    	
	    	Stage stage = (Stage) theScene.getWindow();
	    	stage.close();
	    	Stage mainStage = new Stage();
            Victory  mainMenu = new Victory  ();
            mainMenu.start(mainStage);
	        myShip.isdead = true;
	        myShip.currentLife++;
	        myShip.die();
	        bgm.stop();
            
            
	    }
	    else{
	    	goku.hp--;
	    	
  	    }
	}
	
	private void handleBlastNarutoCollision(Ship myShip, Blast blast) { ////////////////////////////////
	    // Perform actions upon collision (e.g., remove fish, update score, etc.)
	    myShip.getDamaged();; // Example method to mark naruto as dead
	    System.out.println(myShip.isAlive());
	}
	
	private void handleWaveNarutoCollision(Ship myShip, Kame wave) { //1 hit delete
	    // Perform actions upon collision (e.g., remove fish, update score, etc.)
	    myShip.getDamaged();; // Example method to mark naruto as dead
	    System.out.println(myShip.isAlive());
	}
	
	private void handleCollisionsGoku() { ////////////////////////////////
	    ArrayList<Bullet> bullets = myShip.getBullets();
	    for (int i = 0; i < bullets.size(); i++) {
	        Bullet bullet = bullets.get(i);
	        for (int j = 0; j < goku.size(); j++) {
	            Goku g = goku.get(j);
	            if (bullet.collidesWith(g)) {
	                // Collision detected between bullet and goku
	                handleBulletGokuCollision(bullet, g);
	                explode = new Explode(g.x, g.y);
	                explosions.add(explode);
	                final Image EXPLODE_IMAGE = new Image("images/Explode.gif",Explode.EXPLODE_WIDTH,Explode.EXPLODE_WIDTH,false,false);
	        	    explode.loadImage(EXPLODE_IMAGE);
	        	    if (explosionStartTime == -1) {
	        	        explosionStartTime = System.nanoTime();
	        	    }
	                bullets.remove(i); // Remove the bullet
	                i--; // Adjust index after removal
	                break; // Exit fish loop after collision
	            }
	        }
	    }
	}
	
	private void handleKiBlastCollisions() { //////////////////////////////////
	    for (Goku g : this.goku) {
	        ArrayList<Blast> s = g.getBullets();

	        for (int i = 0; i < s.size(); i++) {
	            Blast b = s.get(i);
	            if (myShip.collidesWith(b)) {
	            	b.visible = false; 
	                handleBlastNarutoCollision(myShip, b);
	                break; // Exit the loop after collision with one blast
	            }
	        }
	    }
	}
	
	private void handleWaveCollisions() { //////////////////////////////////
	    for (Goku g : this.goku) {
	        ArrayList<Kame> k = g.getWave();

	        for (int i = 0; i < k.size(); i++) {
	            Kame w = k.get(i);
	            if (myShip.collidesWith(w)) {
	                handleWaveNarutoCollision(myShip, w);
	                break; // Exit the loop after collision with one blast
	            }
	        }
	    }
	}
		
	
	
	
	
	//////////////////NORMAL//////////////////////////////////
	
	private void renderKillCount() { //prints the current killcount of the player
	    String killCountText = "" + killCount; 
	    gc.setFont(Font.font("Arial", 100)); // Set the font family and size
	    gc.setFill(Color.WHITE);
	    gc.fillText(killCountText, 950, 100); 
	}
	
	//method that will render/draw the fishes to the canvas
	private void renderFishes() { 
		for (Fish f : this.fishes){
			f.render(this.gc);
		}
	}

	//method that will render/draw the bullets to the canvas
	private void renderBullets() {
		ArrayList<Bullet> bList = this.myShip.getBullets();

		//Loop through the bullet list and check whether a bullet is still visible.
		for(int i = 0; i < bList.size(); i++){
			Bullet b = bList.get(i);
			b.render(this.gc);
		}
	}

	//render orbs to canvas
	private void renderOrbs() {
		for (Orb o: this.orbs){
			o.render(this.gc);
		}
	}
	
	private void renderExplode() { //render explode animation
		for (Explode e : this.explosions){
			e.render(this.gc);
		}
	}
	
	private void renderTransform() { //renderTransform animation
		for (Transform e : this.transformation){
			e.render(this.gc);
		}
	}
	//render hearts to canvas
	private void renderLives() { //render hearts
		for (Life l: this.lives){
			l.render(this.gc);
		}
	}

	//render hearts to canvas
	private void renderFlames() {
		for (Fire f: this.flames){
			f.render(this.gc);
		}
	}



	private void narutoTransform() {
		if (myShip.awakeningLevel == 5){
			Transform transform = new Transform(myShip.x, myShip.y);
			this.transformation.add(transform);
			final Image TRANSFORM_IMAGE = new Image("images/TRANSFORM.gif",Transform.TRANSFORM_WIDTH,Transform.TRANSFORM_WIDTH,false,false);
		    transform.loadImage(TRANSFORM_IMAGE);
		    if (transformStartTime == -1) {
		    	transformStartTime = System.nanoTime();
		    }
		    
	    	myShip.transform();
	    	myShip.awakenedLimit();
			for (Fire f: this.flames){
				f.loadImage(Fire.FIRE_IMAGE);
			}
		}
			
		
	}

	private void spawnFishesTimer(){
		Timer timer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                //spawn orbs every 10 seconds
            	if (ichigoBoss == false && luffyBoss == false) {
            	spawnFishes();
            	}
            	
            }
        };

        // Schedule the task to run every 1000 milliseconds
        timer.scheduleAtFixedRate(task, 0, 500);
	}
	
	
	//method that will spawn/instantiate three fishes at a random x,y location
	private void spawnFishes(){
		Random r = new Random();

		int x = r.nextInt(GameStage.WINDOW_WIDTH) + GameStage.WINDOW_WIDTH;
		int y = r.nextInt(GameStage.WINDOW_HEIGHT);
		Fish newFish = new Fish(x, y);

		//append to arraylist
		this.fishes.add(newFish);


	}
	//method in showing the hearts
	private void spawnLives(){

		int x = 10;
		int y = 900;

		for (int i=0; i<myShip.currentLife; i++){
			//instantiate new life
			Life newLife = new Life(x, y);
			//append to arraylist
			this.lives.add(newLife);
			x += 100;
		}



	}


	//method in showing the hearts
	private void spawnFlames(){

		int x = 1300;
		int y = 850;

		if (myShip.awakeningLevel <= 5){
			for (int i=0; i<myShip.awakeningLevel; i++){
				//instantiate new life
				Fire newFire = new Fire(x, y);
				//append to arraylist
				this.flames.add(newFire);
				x += 100;
			}
		}



	}

	void handleClearFlames(){
		if (myShip.transformationEnded == true){
			for (int i=0; i<flames.size(); i++){
				this.flames.get(i).die();

			}
			this.flames.clear();
			myShip.transformationEnded = false;
		}
	}

	private void loseLife() {
		if (myShip.isDamaged == true && myShip.isAlive()){
			int totalLife = 0;
			for (int i=0; i<myShip.currentLife; i++){
				totalLife++;
			}
			this.lives.remove(lives.get(totalLife));
			myShip.isDamaged = false;
		}

	}



	private void spawnOrb(){
		Random r = new Random();
		int x = r.nextInt(GameStage.WINDOW_WIDTH);
		int y = r.nextInt(GameStage.WINDOW_HEIGHT);
		/*
		 *TODO: Add a new object Fish to the fishes arraylist
		 */
		Orb newOrb = new Orb(x, y);

		//append to arraylist
		this.orbs.add(newOrb);

	}



	private void spawnOrbsTimer(){
	    Timer timer = new Timer();

	    TimerTask task = new TimerTask() {
	        @Override
	        public void run() {
	            // spawn orbs every 10 seconds
	            spawnOrb();
	        }
	    };

	    // Schedule the task to run every 10 seconds (10000 milliseconds)
	    timer.scheduleAtFixedRate(task, 0, 5000);
	}














	//method that will move the bullets shot by a ship
	private void moveBullets(){
		//create a local arraylist of Bullets for the bullets 'shot' by the ship
		ArrayList<Bullet> bList = this.myShip.getBullets();

		//Loop through the bullet list and check whether a bullet is still visible.
		for(int i = 0; i < bList.size(); i++){
			Bullet b = bList.get(i);
			/*
			 * TODO:  If a bullet is visible, move the bullet, else, remove the bullet from the bullet array list.
			 */
			if (b.isVisible() == true){
				b.move();
			}
			else{
				b = bList.remove(i);
				i--;
			}
		}
	}

	//method that will move the fishes
	private void moveFishes(){
		//Loop through the fishes arraylist
		for(int i = 0; i < this.fishes.size(); i++){
			Fish f = this.fishes.get(i);
			/*
			 * TODO:  *If a fish is alive, move the fish. Else, remove the fish from the fishes arraylist.
			 */
			if (f.isAlive()){
				f.move();
			}
			else{
				f = fishes.remove(i);
				i--;
			}
		}
	}

	private void handleNarutoDeath() {
	    if (myShip.currentLife <= 0 && !myShip.isdead) {

	        myShip.isdead = true;
	    	Stage stage = (Stage) theScene.getWindow();
	    	stage.close();
	    	Stage mainStage = new Stage();
            MainOver mainMenu = new MainOver  ();
            mainMenu.start(mainStage);
	        myShip.currentLife++;
	        myShip.die();
	        bgm.stop();
	    }
	}

 

	private void handleBulletFishCollision(Bullet bullet, Fish fish) {
	    // Example method to mark fish as dead
	     // Mark the fish as dead first
	    explode = new Explode(fish.x, fish.y);
	    final Image EXPLODE_IMAGE = new Image("images/Explode.gif",Explode.EXPLODE_WIDTH,Explode.EXPLODE_WIDTH,false,false);
	    explode.loadImage(EXPLODE_IMAGE);
	    fish.die();
	    this.explosions.add(explode);
	    if (explosionStartTime == -1) {
	        explosionStartTime = System.nanoTime();
	    }
	    this.killCount++;
	    System.out.println(killCount);
	}


	private void handleShipFishCollision(Ship myShip, Fish fish) {
	    // Perform actions upon collision (e.g., remove fish, update score, etc.)
	    myShip.getDamaged(); // Example method to mark fish as dead
	    fish.die();
	    System.out.println(myShip.isAlive());
	}

	private void handleNarutoOrbCollision(Ship myShip, Orb orb) {
		//Perform actions upon collision (e.g., remove fish, update score, etc.)
		AudioClip bgm = new AudioClip("file:src/images/orb.mp3");
        bgm.play();
		orb.setVisible(false);
	    
	    //increase the awakening level by 1
	    myShip.awakeningLevel++;
	    this.spawnFlames();

	}

	private void handleCollisions() {
	    ArrayList<Bullet> bullets = myShip.getBullets();
	    for (int i = 0; i < bullets.size(); i++) {
	        Bullet bullet = bullets.get(i);
	        for (int j = 0; j < fishes.size(); j++) {
	            Fish fish = fishes.get(j);
	            if (bullet.collidesWith(fish) && myShip.isAwakened == true) {
	                // Collision detected between bullet and fish
	                handleBulletFishCollision(bullet, fish);
	                break; // Exit fish loop after collision
	            }
	            else if (bullet.collidesWith(fish)) {
	                // Collision detected between bullet and fish
	                handleBulletFishCollision(bullet, fish);
	                bullets.remove(i); // Remove the bullet
	                i--; // Adjust index after removal
	                break; // Exit fish loop after collision
	            }
	        }
	    }
	}

	private void handleShipCollisions() {
	    for (Fish fish : fishes) {
	        if (myShip.collidesWith(fish)) {
	            // Collision detected between ship and fish
	            handleShipFishCollision(myShip, fish);
	            break; // Exit fish loop after collision
	        }
	    }


	}

	private void handleNarutoOrbCollisionsHelper(){
		if (myShip.isAwakened == false){
			for (int j = 0; j < orbs.size(); j++) {
	            Orb orb = orbs.get(j);
	            if (myShip.collidesWith(orb)) {
	                // Collision detected between bullet and fish
	            	handleNarutoOrbCollision(myShip, orb);

	                orbs.remove(j); // Remove the bullet
	                j--; // Adjust index after removal
	                break; // Exit fish loop after collision

	            }
	        }
		}
	}
	//method that will listen and handle the key press events
	private void handleKeyPressEvent() {
		this.theScene.setOnKeyPressed(new EventHandler<KeyEvent>(){
			public void handle(KeyEvent e){
            	KeyCode code = e.getCode();
                moveMyShip(code);
			}
		});

		this.theScene.setOnKeyReleased(new EventHandler<KeyEvent>(){
		            public void handle(KeyEvent e){
		            	KeyCode code = e.getCode();
		                stopMyShip(code);
		            }
		        });
    }
	private void handleTransformation() {
        if (myShip.awakeningLevel >= 5 && !myShip.isAwakened) {
            myShip.transform();
            myShip.isAwakened = true;
        }
    }




	//method that will move the ship depending on the key pressed
	private void moveMyShip(KeyCode ke) {
		if(ke==KeyCode.UP) {
			this.myShip.setDY(-10);
			
		}

		else if(ke==KeyCode.LEFT) {
			this.myShip.setDX(-10);
			
		}

		else if(ke==KeyCode.DOWN) {
			this.myShip.setDY(10);
			
		}

		else if(ke==KeyCode.RIGHT) {
			this.myShip.setDX(10);
		}	
		
		else if (ke==KeyCode.CANCEL){
			this.myShip.running = false;
		}

		if(ke==KeyCode.SPACE) {
			//for (Ichigo i: ichigo) {
//				if (i.attackMode == true && this.ichigoBoss == true) {
//					this.myShip.shoot();
//				}
				//if (this.ichigoBoss == false) {
			AudioClip rasengan = new AudioClip("file:src/images/rasengan.mp3");
	        rasengan.play();		
			this.myShip.shoot();
				//}
			//}
			
			
		}


		//System.out.println(ke+" key pressed.");
   	}

	//method that will stop the ship's movement; set the ship's DX and DY to 0
	private void stopMyShip(KeyCode ke){
		this.myShip.setDX(0);
		this.myShip.setDY(0);
	}

}
