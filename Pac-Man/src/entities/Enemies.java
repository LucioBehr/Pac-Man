package entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;



import main.Game;
import world.World;

	
public class Enemies extends Entities{
	private double speed = 0.3;
	Random rand = new Random();
	public static Player player;
	private int frames = 0,maxFrames = 20,index = 0,maxIndex = 1;
	
	private BufferedImage[] sprites;
	public Enemies(int x, int y, int wx, int hy, BufferedImage[] sprite) {
		super(x, y, wx, hy, null);
		// TODO Auto-generated constructor stub
		sprites = new BufferedImage[2];
		this.sprites[0] = sprite[0];
		this.sprites[1] = sprite[1];
		
		
	}

		
	
	public void destroy() {
		Game.entities.remove(this);
		return;
	}
	
	public void collisionPP() {
		for(int i =0; i < Game.entities.size(); i++) {
			Entities e = Game.entities.get(i);
			if (e instanceof Player) {
				if (Entities.isColidding(this, e)) {
					destroy();
					Player.points+= 100;
					return;
				}
			}
		}
		
	}
	public void tick() {
		if(Player.power == true) {
		collisionPP();
		}
		
		
		if (this.isColiddingWithPlayer() == false) {
			if(Player.power == true) {
				if (Game.player.getX() > this.getX() && World.isFree(this.getX() - 1, this.getY()) && !isColidding((int)(x-speed), this.getY())) {
					this.x-=speed;
				}
				else if (Game.player.getX() < this.getX() && World.isFree(this.getX() + 1, this.getY()) && !isColidding((int)(x+speed), this.getY())) {
					this.x+=speed;
				}
				else if (Game.player.getY() > this.getY() && World.isFree(this.getX() , this.getY()- 1) && !isColidding(this.getX(), (int)(y-speed))) {
					this.y-=speed;
				}
				else if (Game.player.getY() < this.getY() && World.isFree(this.getX() , this.getY() + 1)&& !isColidding(this.getX(), (int)(y+speed))) {
					this.y+=speed;
			}
	
			}
			else {
				//System.out.println("a");
				if (Game.player.getX() > this.getX() && World.isFree(this.getX() + 1, this.getY()) && !isColidding((int)(x+speed), this.getY())) {
					this.x+=speed;
				}
				else if (Game.player.getX() < this.getX() && World.isFree(this.getX() - 1, this.getY()) && !isColidding((int)(x-speed), this.getY())) {
					this.x-=speed;
				}
				else if (Game.player.getY() > this.getY() && World.isFree(this.getX() , this.getY()+ 1) && !isColidding(this.getX(), (int)(y+speed))) {
					this.y+=speed;
				}
				else if (Game.player.getY() < this.getY() && World.isFree(this.getX() , this.getY() - 1)&& !isColidding(this.getX(), (int)(y-speed))) {
					this.y-=speed;
				}
			}
		}
		else {
			if (Player.power == true) {
				
			}else {
			Player.life-=1;
			World.restartGame("level1.png");
			}
		}

		frames++;
		if(frames == maxFrames) {
			frames = 0;
			index++;
			if(index > maxIndex)
				index = 0;
		}
	}
	public boolean isColiddingWithPlayer() {
		Rectangle enemyCurrent = new Rectangle(this.getX(), this.getY(), World.TILE_SIZE, World.TILE_SIZE);
		Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(), 16, 16);
		return enemyCurrent.intersects(player);
	}
	public boolean isColidding(int xnext, int ynext) {
		Rectangle enemyCurrent = new Rectangle(xnext, ynext, World.TILE_SIZE, World.TILE_SIZE);
		for (int i = 0; i < Game.enemies.size(); i++) {
			Enemies e = Game.enemies.get(i);
			if (e==this) {
				continue;
			}
				Rectangle targetEnemy = new Rectangle(e.getX(), e.getY(), World.TILE_SIZE, World.TILE_SIZE);
			if(enemyCurrent.intersects(targetEnemy)) {
				return true;
			}
			}
		
		
		return false;

	}
	public void render(Graphics g) {
		BufferedImage[] dam_en = new BufferedImage[2];
		if(Player.wp <=60) {
		dam_en[0] = Game.spritesheet.getSprite(32,32,16,16);
		}
		else {
		dam_en[0] = Game.spritesheet.getSprite(64,32,16,16);
		}
		dam_en[1] = Game.spritesheet.getSprite(48,32,16,16);

		if(Player.power == true) {
			g.drawImage(dam_en[index], this.getX(),this.getY(),null);
		}
		else {
		g.drawImage(sprites[index], this.getX(),this.getY(),null);
		}
	}

}
