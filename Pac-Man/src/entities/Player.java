package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import main.Sounds;
import world.World;

public class Player extends Entities{
	
	public boolean up, down, left, right;

	public static boolean power = false;
	public static double speed;
	public int right_dir =0, left_dir=1,up_dir = 2, down_dir =3, dir = right_dir;
	public static int maxLife = 3;
	public static int points = 0;
	public static int life = 3;
	public static Enemies enemy;
	private int frames = 0, maxFrames=4, index = 0, maxIndex=1;

	private static int a = 0;
	public static int wp = 0;
	public boolean moved = false;
	private BufferedImage[] rightPacMan;
	private BufferedImage[] leftPacMan;
	private BufferedImage[] upPacMan;
	private BufferedImage[] downPacMan;
	
	public Player(int x, int y, int wx, int hy, BufferedImage sprite) {
		super(x, y, wx, hy, sprite);
		// TODO Auto-generated constructor stub
		rightPacMan = new BufferedImage[2];
		leftPacMan = new BufferedImage[2];
		upPacMan = new BufferedImage[2];
		downPacMan = new BufferedImage[2];
		for(int i =0; i < 2; i++) {
			rightPacMan[i] = Game.spritesheet.getSprite(0 + (i*16), 0, 16, 16);
		}
		for(int i =0; i < 2; i++) {
			leftPacMan[i] = Game.spritesheet.getSprite(32 + (i*16), 0, 16, 16);
		}
		for(int i =0; i < 2; i++) {
			upPacMan[i] = Game.spritesheet.getSprite(64 + (i*16), 0, 16, 16);
		}
		for(int i =0; i < 2; i++) {
			downPacMan[i] = Game.spritesheet.getSprite(96 + (i*16), 0, 16, 16);
		}
	}
	
	public void tick() {
		
		if (life == 0) {
			Sounds.die.play();
			Game.gameState = "GAME_OVER";
		}
		if (right && World.isFree((int)(x+speed), this.getY())) {
			moved = true; 
			dir = right_dir;
			up = false;
			down = false;
			left = false;
			x+=speed;
		}
		if (left && World.isFree((int)(x-speed), this.getY())) {
			moved = true; 
			dir = left_dir;
			up = false;
			down = false;
			right = false;
			x-=speed;
		}
		if (up && World.isFree(this.getX(),(int)(y-speed))) {
			moved = true; 
			dir = up_dir;
			right = false;
			down = false;
			left = false;
			y-=speed;
		}
		if (down && World.isFree(this.getX(),(int)(y+speed))) {
			moved = true; 
			dir = down_dir;
			up = false;
			right = false;
			left = false;
			y+=speed;
		}
		if(moved) {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if (index > maxIndex) {
					index = 0;
					if (power == true) {
						wp++;
					}
					else {
						wp = 0;
					}
				}
			}
		}
		if (moved = false) {
			//index = index;
		}
		power();
		this.checkColisionP1();
		this.checkColisionP2();
}
	public void checkColisionP1() {
		
		for (int i = 0; i < Game.entities.size(); i++) {
		Entities atual = Game.entities.get(i);
		if (atual instanceof Points1) {
			if (Entities.isColidding(this, atual)) {
			a++;
			if (a > 1) {
				a = 0;
			}
			if (a == 0) {
				Sounds.eat1.play();
			}
			else {
				Sounds.eat2.play();
			}
			points+=10;
			Game.entities.remove(atual);
			}
		}
		}
	}
	public static boolean power() {

		if (power ==true) {
			speed = 0.7;
			Sounds.power.loop();
			if (wp == 70) {
				power = false;
			}
		}
		else {
			speed = 0.5;
			Sounds.power.stop();
		}
		return false;
	}

public void checkColisionP2() {
	
	for (int i = 0; i < Game.entities.size(); i++) {
	Entities atual = Game.entities.get(i);
	if (atual instanceof Points2) {
		if (Entities.isColidding(this, atual) && power == false) {
		wp = 0;
		power = true;
		Game.entities.remove(atual);
		} else if (Entities.isColidding(this, atual) && power == true) {
			wp = 0;
			Game.entities.remove(atual);
		}
	}
	}
	}

	public void render(Graphics g) {

		if (dir == right_dir) {
		g.drawImage(rightPacMan[index], this.getX(), this.getY(), null);
		} else if(dir == left_dir) {
		g.drawImage(leftPacMan[index], this.getX(), this.getY(), null);
		}else if (dir == up_dir) {
		g.drawImage(upPacMan[index], this.getX(), this.getY(), null);
		} else if (dir == down_dir) {
		g.drawImage(downPacMan[index], this.getX(), this.getY(), null);
		}
		}

	

}
