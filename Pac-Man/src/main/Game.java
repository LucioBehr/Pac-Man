package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;


import entities.Enemies;
import entities.Entities;
import entities.Player;
import main.Game;
import world.World;
import graphics.Sprites;
import graphics.UI;

public class Game extends Canvas implements Runnable,KeyListener,MouseListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Declaração de variaveis
	private boolean showMessageGameOver = true;
	public static boolean win;
	private int framesGameOver = 0;
	public static final int WX = 370, HY = 365, SCALE = 2;
	public static int curLevel = 1;
	private int maxLevel =3 ;
	public static int level_map;
	private static BufferedImage image;
	public static Sprites spritesheet;
	public UI ui;
	public Menu menu;
	public static World world;
	public static String gameState = "MENU";
	public static JFrame frame;

	private Thread thread;
	private boolean isRunning = true;
	
	public static Enemies enemie;
	public static List<Entities> entities;
	public static List<Enemies> enemies;
	public static Player player;
	
	public Game() {
		
		addKeyListener(this);
		setPreferredSize(new Dimension(WX*SCALE, HY*SCALE));
		initFrame();
		ui = new UI();
		image = new BufferedImage(WX, HY, BufferedImage.TYPE_INT_RGB);
		entities = new ArrayList<Entities>();
		enemies = new ArrayList<Enemies>();
		spritesheet = new Sprites("/SpriteSheet.png");
		player = new Player(0, 0, 16, 16,spritesheet.getSprite(0, 0, 16, 16));
		entities.add(player);
		world = new World("/level" + curLevel + ".png");
		menu = new Menu();
		
	}
	public void initFrame() {
		frame = new JFrame("Pac-Man");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	public static void main(String args[]){
		Game game = new Game();
		game.start();
	}
	
	public synchronized void start(){
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	public synchronized void stop(){
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void tick() {
		if(Game.entities.size() == 1) {
			if (curLevel < maxLevel) {
			curLevel+=1;
			World.restartGame("/level" + curLevel + ".png");
			}
			else if (curLevel == maxLevel) {
				curLevel+=1;
			}
			else if (curLevel > maxLevel) {
				curLevel = 1;
				win = true;
				gameState = "GAME_OVER";
				Sounds.power.stop();
				Sounds.eat1.stop();
				Sounds.eat2.stop();
				Sounds.die.stop();
				
				}
		
		
		}
if(gameState == "NORMAL") {
	for (int i=0; i< entities.size(); i++) {
		Entities e = entities.get(i);
		e.tick();
	}
		}
else if(gameState == "MENU") {
			
			menu.tick();
		}
else if (gameState == "GAME_OVER") {
	this.framesGameOver++;
	if(this.framesGameOver == 30) {
		this.framesGameOver = 0;
		if(this.showMessageGameOver)
			this.showMessageGameOver = false;
			else
				this.showMessageGameOver = true;
}
	}
		
		
		
	}
	
	
	
	public void render () {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(0,0,0));
		g.fillRect(0, 0, WX, HY);
		
		world.render(g);
		for (int i=0; i< entities.size(); i++) {
			Entities e = entities.get(i);
			e.render(g);
		}
		ui.render(g);
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WX*SCALE, HY*SCALE, null);
		if(gameState == "GAME_OVER") {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0,0,0));
			g2.fillRect(0, 0, Game.WX* Game.SCALE, Game.HY* Game.SCALE);
			g.setFont(new Font("arial",Font.BOLD,80));
			g.setColor(Color.yellow);
			if(win == false) {
			g.drawString("Game Over", 150, 200);
			}else {
			g.drawString("You win", 150, 200);
			//win = false;
			}
			g.setFont(new Font("arial",Font.BOLD,32));
			g.drawString("Menu", 300, 400);
			g.drawString("Pontos: " + Player.points, 300, 300 );
			if(showMessageGameOver)
				g.drawString(">",270, 402);
		}else if(gameState == "MENU") {
			menu.render(g);
		} else if (gameState == "NORMAL") {
			if (curLevel == 1) {
				g.drawImage(Entities.logo, 220, 80, 300, 100, null);
				g.setColor(Color.YELLOW);
				g.setFont(new Font("arial",Font.BOLD,32));
				g.drawString("Bem vindo ao meu clone do", 100, 520);
				g.drawImage(Entities.logo, 530, 492, 100, 40, null);
				g.setFont(new Font("arial",Font.BOLD,20));
				g.drawString("Use: ", 100, 550);
				g.drawString("[W] Para se mover para Cima", 100, 580);
				g.drawString("[A] Para se mover para Esquerda", 100, 610);
				g.drawString("[S] Para se mover para Baixo", 100, 640);
				g.drawString("[D] Para se mover para Direita", 100, 670);
				g.drawString("Capture Todos os pontos para avançar para a próxima fase", 100, 700);
		}
			else if (curLevel == 2) {
				g.drawImage(Entities.logo, 220, 80, 300, 100, null);
				g.setColor(Color.YELLOW);
				g.setFont(new Font("arial",Font.BOLD,20));
				g.drawString("Ganhe poder ao pegar pontos brancos maiores", 100, 500);
				g.drawString("Quando seu poder estiver ativo, toque em um inimigo para derrota-lo", 40, 600);
				g.drawString("Colete os pontos e derrote os inimigos para avançar", 70, 700);
			}
			else if (curLevel == 3) {
				g.drawImage(Entities.logo, 220, 80, 300, 100, null);
			}
			
		}
		bs.show();
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_D) {
			if (!World.isFree(player.getX() + 1, player.getY())) {
				
			}
			else {
				player.right = true;
				player.down = false;
				player.up = false;
				player.left = false;
			}
		}
		
		else if (e.getKeyCode() == KeyEvent.VK_A) {
			
			if (!World.isFree(player.getX() - 1, player.getY())) {
				
			}
			else {
				player.right = false;
				player.down = false;
				player.up = false;
				player.left = true;
			}
		}
		else if (e.getKeyCode() == KeyEvent.VK_W) {
			if(gameState == "MENU") {
				menu.up = true;
			}
			if (!World.isFree(player.getX(), player.getY() - 1)) {
				
			}
			else {
				player.right = false;
				player.down = false;
				player.up = true;
				player.left = false;
			}
		}
		else if (e.getKeyCode() == KeyEvent.VK_S) {
			if(gameState == "MENU") {
				menu.down = true;
			}
			if (!World.isFree(player.getX(), player.getY() + 1)) {
				
			}
			else {
				player.right = false;
				player.down = true;
				player.up = false;
				player.left = false;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			//this.restartGame = true;
			if(gameState == "MENU") {
				menu.enter = true;
			}
			else if (gameState == "GAME_OVER") {
				gameState = "MENU";
				World.restartGame("/level3.png");
			}
			else if (gameState == "GAME_OVER" || win == true) {
				
				gameState = "MENU";
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			if(gameState == "NORMAL") {
				gameState = "MENU";
				menu.pause = true;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_1){
			Game.level_map = 1;
		}
		if(e.getKeyCode() == KeyEvent.VK_2){
			Game.level_map = 2;
		}
		if(e.getKeyCode() == KeyEvent.VK_3){
			Game.level_map = 3;
		}
		}
	
	
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void run() {
		requestFocus();
		// TODO Auto-generated method stub
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		requestFocus();
		while(isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
			}
			if (System.currentTimeMillis() - timer >= 1000) {
				System.out.println("FPS: " + frames);
				frames = 0;
				timer+=1000;
				
			}
		}
		
	}
}
