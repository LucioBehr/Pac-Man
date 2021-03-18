package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import entities.Entities;
import entities.Player;
import world.World;

public class Menu {
		// Declaração de variaveis
	
		public String[] options = {"iniciar", "sair"};
		public int curOptions = 0, maxOption = options.length - 1;
		public boolean up, down, enter, pause = false;
		private int framesPacMan = 0, curFrames = 0, maxFrames = 1;
		// Fim da declaração de variaveis
		
		//Logica do menu
		public void tick() {
			if (up) {
			up = false;
			curOptions--;
			if (curOptions < 0) {
				curOptions = maxOption;
			}
			}
			else if (down) {
			down = false;
			curOptions++;
			if (curOptions > maxOption) {
				curOptions = 0;
			}
			}
			else if (enter) {
			enter = false;
			if (options[curOptions] == "iniciar") { 
				Game.curLevel = 1;
				Game.win = false;
				Player.points = 0;
				Player.life = 3;
				World.restartGame("/level" + Game.curLevel + ".png");
				pause = false;
				Game.gameState = "NORMAL";
				pause = false;
			}
			if (options[curOptions] == "continuar") {
				pause = false;
				Game.gameState = "MAP";
				pause = false;
			}
			else if (options[curOptions] == "sair") {
				System.exit(1);
			}
			
		}
		}
		//Fim da logica do menu
		
		//Reenderização do menu
		public void render(Graphics g) {
	
		
			g.setColor(Color.black);
			g.fillRect(0, 0, Game.WX * Game.SCALE, Game.HY * Game.SCALE);
			g.setColor(Color.red);
			g.fillRect(5, 0, 730, 190);
			g.setColor(Color.orange);
			g.fillRect(20, 15 , 700, 160);
			g.setColor(Color.yellow);
			g.drawImage(Entities.logo, 25, 20, 730, 200, null);
			g.setColor(Color.white);
			g.setFont(new Font("arial", Font.BOLD, 36));
				this.framesPacMan++;
				if(this.framesPacMan == 30) {
					this.framesPacMan = 0;
					this.curFrames++;
				}
				if (this.curFrames > this.maxFrames) {
					this.curFrames = 0;
				}
				if (this.curFrames == 0) {
				g.drawImage(Entities.Player1r, 270, 190, 200, 200, null);
				g.drawImage(Entities.EnBlue1, 0, 520, 200, 200, null);
				g.drawImage(Entities.EnOran1, 170, 520, 200, 200, null);
				g.drawImage(Entities.EnPink1, 350, 520, 200, 200, null);
				g.drawImage(Entities.EnRed1, 520, 520, 200, 200, null);
				}
				else {
					g.drawImage(Entities.Player2r, 270, 190, 200, 200, null);
					g.drawImage(Entities.EnBlue2, 0, 520, 200, 200, null);
					g.drawImage(Entities.EnOran2, 170, 520, 200, 200, null);
					g.drawImage(Entities.EnPink2, 350, 520, 200, 200, null);
					g.drawImage(Entities.EnRed2, 520, 520, 200, 200, null);
				}
			
			//g.drawString("Pac-Man", (Game.WX*Game.SCALE), (Game.HY*Game.SCALE));
			g.setColor(Color.YELLOW);
			g.setFont(new Font("arial",Font.BOLD,50));
			if(pause == false) {
				//g.drawImage(Entities.play, 270, 450, 200, 100, null);
				g.drawString("Play", (Game.WX*Game.SCALE) / 2 - 50, 420);
			//g.drawImage(Entities.exit, 270, 550, 160, 60, null);
			}else
				//g.drawImage(Entities.play, 270, 300, 200, 200, null);
				g.drawString("Resume", (Game.WX*Game.SCALE) / 2 - 50, 420);
			g.drawString("Exit", (Game.WX*Game.SCALE) / 2 - 50, 520);
			
			if(options[curOptions] == "iniciar") {
				g.drawString(">", (Game.WX*Game.SCALE) / 2 - 140, 420);
			}else if(options[curOptions] == "sair") {
				g.drawString(">", (Game.WX*Game.SCALE) / 2 - 140, 520);
			}
			
		
		//fim da reenderização do menu
}
}
