package Tanks;

import Blocks.BaseBlock;
import Main.Main;
import Map.Map;
import acm.graphics.GImage;
import acm.program.GraphicsProgram;

/*
 * Ahmet Emre Unal
 * Burak Tutanlar
 * Celebi Murat
 */

public class TankExplosionAnimation extends GImage implements Runnable {
	private Tank tank;
	private GraphicsProgram gp;

	public TankExplosionAnimation(Tank tank) {
		super("Map_Resources/brick0.png");
		this.tank = tank;
		this.gp = Map.getGraphicsProgram();
		locate();
		gp.add(this);
		new Thread(this).start();
	}
	
	public TankExplosionAnimation(BaseBlock base) {
		super("Map_Resources/brick0.png");
		this.gp = Map.getGraphicsProgram();
		setLocation((Main.mapDimension - Main.blockSize) / 2, Main.mapDimension - Main.blockSize);
		gp.add(this);
		new Thread(this).start();
	}

	public void run() {
		for (int i = 1; i <= 3; i++) {
			setImage("Tank_Resources/explosion" + i + ".png");
			gp.repaint();
			gp.pause(200);
		}
		gp.remove(this);
		gp.repaint();
	}

	public void locate() {
		sendToFront();
		gp.repaint();
		int tankX = (int) tank.getX();
		int tankY = (int) tank.getY();
		int tankW = (int) tank.getWidth();
		int tankH = (int) tank.getHeight();
		int imageW = (int) this.getWidth();
		int imageH = (int) this.getHeight();
		setLocation(tankX + (tankW - imageW) / 2, tankY + (tankH - imageH) / 2);
	}
}
