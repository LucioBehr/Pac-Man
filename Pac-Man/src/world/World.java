package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import entities.Player;
import world.World;

import entities.Entities;
import entities.Points1;
import entities.Points2;
import graphics.Sprites;
import entities.Enemies;

import main.Game;

public class World {
	
	public static Tile[] tiles;
	public static int[] en;
	public static int WIDTH, HEIGHT;
	public static final int TILE_SIZE = 16;
	//private Random rand;
	
	
	Random rand = new Random();
	
	public World(String path) {
		try {
		BufferedImage map = ImageIO.read(getClass().getResource(path));
		int[] pixels = new int[map.getWidth() * map.getHeight()];
		WIDTH = map.getWidth();
		HEIGHT = map.getHeight();
		tiles = new Tile[map.getWidth() * map.getHeight()];
		map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
		for (int xx= 0; xx < map.getWidth(); xx++) {
			for (int yy=0; yy < map.getHeight(); yy++) {
				int pixelAtual = pixels[xx + (yy * map.getWidth())];
				tiles[xx + (yy * WIDTH)] = new Floor_tile(xx * 16,yy * 16, Tile.TILE_FLOOR);
					if (pixelAtual == 0xFF000000) {
					//chao
					tiles[xx + (yy * WIDTH)] = new Floor_tile(xx * 16,yy * 16, Tile.TILE_FLOOR); 
				
				}
					else if (pixelAtual == 0xFFFFFFFF) {
					//parede
					tiles[xx + (yy * WIDTH)] = new Wall_tile(xx * 16,yy * 16, Tile.TILE_WALL);
				
				}
					else if (pixelAtual == 0xFF0000FF){
					//player
					Game.player.setX(xx*16);
					Game.player.setY(yy*16);
					

				}
					else if (pixelAtual == 0xFFFF0000 || pixelAtual == 0xFFFF329C) {
					//inimigo
					
					BufferedImage[] buf = new BufferedImage[2];

						buf[0] = Game.spritesheet.getSprite(0,16,16,16);
						buf[1] = Game.spritesheet.getSprite(16,16,16,16);
						Enemies en = new Enemies(xx*16,yy*16,16,16,buf);
						Game.entities.add(en);
						Game.enemies.add(en);

				}
					else if(pixelAtual == 0xFFFF0029 || pixelAtual == 0xFFA8329C) {
						BufferedImage[] buf = new BufferedImage[2];
						buf[0] = Game.spritesheet.getSprite(32,16,16,16);
						buf[1] = Game.spritesheet.getSprite(48,16,16,16);
						Enemies en = new Enemies(xx*16,yy*16,16,16,buf);
						Game.entities.add(en);
						Game.enemies.add(en);
				}
					else if(pixelAtual == 0xFFFF0064 || pixelAtual == 0xFFA8009C) {
					BufferedImage[] buf = new BufferedImage[2];
						buf[0] = Game.spritesheet.getSprite(64,16,16,16);
						buf[1] = Game.spritesheet.getSprite(80,16,16,16);
						Enemies en = new Enemies(xx*16,yy*16,16,16,buf);
						Game.entities.add(en);
						Game.enemies.add(en);
				}
					else if(pixelAtual == 0xFFFF009C || pixelAtual == 0xFFA80010) {
					BufferedImage[] buf = new BufferedImage[2];
						buf[0] = Game.spritesheet.getSprite(0,32,16,16);
						buf[1] = Game.spritesheet.getSprite(16,32,16,16);
						Enemies en = new Enemies(xx*16,yy*16,16,16,buf);
						Game.entities.add(en);
						Game.enemies.add(en);
				}
					
					 
					else if(pixelAtual == 0xFF00FF00){
					//ponto1
					Game.entities.add(new Points1(xx*16,yy*16,16,16,Entities.Points1));
				}
					else if (pixelAtual == 0xFFFFFF00) {
					//ponto 2
					Game.entities.add(new Points2(xx*16,yy*16,16,16,Entities.Points2));
					
				}
				}
		}
		
		}
					catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static boolean isFree(int xnext, int ynext) {
		int x1 = xnext/ TILE_SIZE;
		int y1 = ynext/ TILE_SIZE;
		
		int x2 = (xnext + TILE_SIZE - 1)/ TILE_SIZE;
		int y2 = ynext/ TILE_SIZE;
		
		int x3 = xnext/ TILE_SIZE;
		int y3 = (ynext + TILE_SIZE - 1)/ TILE_SIZE;
		
		int x4 = (xnext+ TILE_SIZE - 1)/ TILE_SIZE;
		int y4 = (ynext+ TILE_SIZE - 1)/ TILE_SIZE;
		
		return !((tiles[x1 +(y1*World.WIDTH)] instanceof Wall_tile) ||
				 (tiles[x2 +(y2*World.WIDTH)] instanceof Wall_tile) ||
				 (tiles[x3 +(y3*World.WIDTH)] instanceof Wall_tile) ||
				 (tiles[x4 +(y4*World.WIDTH)] instanceof Wall_tile));
	}
	public static void restartGame(String level){
		Player.power = false;
		Game.entities.clear();
		Game.enemies.clear();
		Game.entities = new ArrayList<Entities>();
		Game.enemies = new ArrayList<Enemies>();
		Game.spritesheet = new Sprites("/spritesheet.png");
		Game.player = new Player(0,0,16,16,Game.spritesheet.getSprite(32, 0,16,16));
		Game.entities.add(Game.player);
		Game.world = new World("/level" + Game.curLevel + ".png");
		return;
	}
	
	public void render (Graphics g) {
		for (int xx = 0; xx < WIDTH; xx++) {
		for (int yy = 0; yy < HEIGHT; yy++) {
			Tile tile = tiles[xx + (yy*WIDTH)];
			tile.render(g);
		}
		}
	}
}
