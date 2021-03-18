package graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import entities.Entities;
import entities.Player;

public class UI {
	public void render(Graphics g) {
		g.fillRect(0, 0, 48, 16);
		for(int i = 1; i <= Player.life; i++) {
		
		g.drawImage(Entities.Player1r, (i * 16) - 16, 0, null);
		
		}
		g.setColor(Color.white);
		g.setFont(new Font("arial",Font.PLAIN,12));
		g.drawString("Pontos: " + Player.points , 270, 14);
		/*
		g.setColor(Color.red);
		g.fillRect(8,4,70,8);
		g.setColor(Color.green);
		g.fillRect(8,4,(int)((Game.player.life/Game.player.maxLife)*70),8);
		
		g.drawString((int)Game.player.life+"/"+(int)Game.player.maxLife,30,11);
		*/
	}
}
