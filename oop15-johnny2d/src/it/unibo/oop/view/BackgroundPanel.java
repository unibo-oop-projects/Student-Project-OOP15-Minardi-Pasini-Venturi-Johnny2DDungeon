package it.unibo.oop.view;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.swing.JPanel;

public class BackgroundPanel extends JPanel {

	private static final long serialVersionUID = -5152861659918746222L;
	private Image background;
	private final ImageLoader loader;
	
	public BackgroundPanel(String name) {
		this.loader = new ImageLoader();
		try {
			this.background = this.loader.load(name);
		} catch(IOException e) {
		System.out.println("Background not found");
		}
	}
	
	protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.background, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}