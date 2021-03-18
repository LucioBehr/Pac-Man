package entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import entities.Entities;


import main.Game;

public class Entities {

	
	//pegando as sprites referentes a cada entidade do jogo
	public static BufferedImage Player1r = Game.spritesheet.getSprite(0, 0,16,16);
	public static BufferedImage Player2r = Game.spritesheet.getSprite(16, 0,16,16);
	public static BufferedImage Player1l = Game.spritesheet.getSprite(32, 0,16,16);
	public static BufferedImage Player2l = Game.spritesheet.getSprite(48, 0,16,16);
	public static BufferedImage Player1u = Game.spritesheet.getSprite(64, 0,16,16);
	public static BufferedImage Player2u = Game.spritesheet.getSprite(80, 0,16,16);
	public static BufferedImage Player1d = Game.spritesheet.getSprite(96, 0,16,16);
	public static BufferedImage Player2d = Game.spritesheet.getSprite(112, 0,16,16);
	public static BufferedImage EnRed1 = Game.spritesheet.getSprite(0, 16,16,16);
	public static BufferedImage EnRed2 = Game.spritesheet.getSprite(16, 16,16,16);
	public static BufferedImage EnOran1 = Game.spritesheet.getSprite(32, 16,16,16);
	public static BufferedImage EnOran2 = Game.spritesheet.getSprite(48, 16,16,16);
	public static BufferedImage EnPink1 = Game.spritesheet.getSprite(64, 16,16,16);
	public static BufferedImage EnPink2 = Game.spritesheet.getSprite(80, 16,16,16);
	public static BufferedImage EnBlue1 = Game.spritesheet.getSprite(0, 32,16,16);
	public static BufferedImage EnBlue2 = Game.spritesheet.getSprite(16, 32,16,16);
	public static BufferedImage EnDam1 = Game.spritesheet.getSprite(32, 32,16,16);
	public static BufferedImage enDam2 = Game.spritesheet.getSprite(48, 32,16,16);
	//public static BufferedImage Cherry = Game.spritesheet.getSprite(16, 48,16,16);
	public static BufferedImage Points1 = Game.spritesheet.getSprite(0, 48,16,16);
	public static BufferedImage Points2 = Game.spritesheet.getSprite(16, 48,16,16);
	public static BufferedImage pcDie1 = Game.spritesheet.getSprite(112, 112,16,16);
	public static BufferedImage pcDie2 = Game.spritesheet.getSprite(112, 96,16,16);
	public static BufferedImage pcDie3 = Game.spritesheet.getSprite(112, 80,16,16);
	public static BufferedImage logo = Game.spritesheet.getSprite(0, 80,127,32);
	public static BufferedImage play = Game.spritesheet.getSprite(96, 32,32,16);
	public static BufferedImage exit = Game.spritesheet.getSprite(96, 48,32,16);
	public static BufferedImage menu = Game.spritesheet.getSprite(90, 64,38,16);
	//

	 
	// importando x, y, altura e largura e as mascaras
	protected double x, y;
	protected int wx, hy;
	
	private BufferedImage sprite;
	public int maskx, masky, mwx, mhy;
	//
	public Entities(int x,int y,int wx,int hy,BufferedImage sprite){
		this.x = x;
		this.y = y;
		this.wx = wx;
		this.hy = hy;
		this.sprite = sprite;
		
		this.maskx = 0;
		this.masky = 0;
		this.mwx = wx;
		this.mhy = hy;
	}
	// Declarando os gets e sets
	public void setMask (int maskx, int masky, int mwx, int mhy){
		this.maskx = maskx;
		this.masky = masky;
		this.mwx = mwx;
		this.mhy = mhy;
	}
		
	
	public void setX(int newX) {
		this.x = newX;
	}
	
	public void setY(int newY) {
		this.y = newY;
	}
	
	public int getX() {
		return (int)this.x;
	}
	
	public int getY() {
		return (int)this.y;
	}
	
	public int getWidth() {
		return this.wx;
	}
	
	public int getHeight() {
		return this.hy;
	}
	public static boolean isColidding(Entities e1,Entities e2){
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskx,e1.getY()+e1.masky,e1.mwx,e1.mhy);
		Rectangle e2Mask = new Rectangle(e2.getX() + e2.maskx,e2.getY()+e2.masky,e2.mwx,e2.mhy);
		if (e1Mask.intersects(e2Mask)) {
			return true;
		}
		return false;
	}
	
	public void tick() {
		
	}
	
	public static boolean isColliding(Entities e1, Entities e2) {
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskx, e1.getY()+e1.masky, e1.mwx, e1.mhy);
		Rectangle e2Mask = new Rectangle(e2.getX() + e2.maskx, e2.getY()+e1.masky, e2.mwx, e2.mhy);
		return e1Mask.intersects(e2Mask);
		
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite,this.getX(),this.getY(),null);
		//g.setColor(Color.red);
		//g.fillRect(this.getX() + maskx,this.getY() + masky,mwx,mhy);
	}
	
}
