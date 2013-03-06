package Tanks;

import Main.Main;
import Map.Map;
import acm.graphics.GImage;
import acm.program.GraphicsProgram;

/*
 * Ahmet Emre Unal
 * Burak Tutanlar
 * Celebi Murat
 */

public class TankHelmetAnimation extends GImage implements Runnable {
	private Tank tank;
	private GraphicsProgram gp;
	private Locator locator;
	private boolean threadIsRunning = true;

	public TankHelmetAnimation(Tank tank) {
		super("Map_Resources/brick0.png");
		this.tank = tank;
		this.gp = Map.getGraphicsProgram();
		locate();
		setSize(28, 28);
		gp.add(this);
		locator = new Locator();
		new Thread(locator).start();
		new Thread(this).start();
	}

	public void run() {
		while (threadIsRunning) {
			for (int i = 1; i <= 2; i++) {
				setImage("Tank_Resources/helmet" + i + ".png");
				gp.pause(100);
			}
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
		setSize(28, 28);
		setLocation(tankX + (tankW - imageW) / 2, tankY + (tankH - imageH) / 2);
	}
	
	public void stop() {
		threadIsRunning = false;
	}

	class Locator implements Runnable {
		public void run() {
			while (threadIsRunning) {
				locate();
				gp.pause(Main.PAUSE_TIME);
			}
		}
	}
}
