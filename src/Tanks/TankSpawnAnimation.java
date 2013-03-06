package Tanks;

import Map.Map;
import acm.graphics.GImage;
import acm.program.GraphicsProgram;

/*
 * Ahmet Emre Unal
 * Burak Tutanlar
 * Celebi Murat
 */

public class TankSpawnAnimation extends GImage implements Runnable {
	private Tank tank;
	private GraphicsProgram gp;
	private Map battlefield;

	public TankSpawnAnimation(Tank tank, Map battlefield) {
		super("Map_Resources/brick0.png");
		this.tank = tank;
		this.gp = Map.getGraphicsProgram();
		this.battlefield = battlefield;
		locate();
		setSize(28, 28);
		gp.add(this);
		locate();
		new Thread(this).start();
	}

	public void run() {
		for (int i = 1; i <= 4; i++) {
			setImage("Tank_Resources/spawn" + i + ".png");
			gp.pause(100);
		}

		for (int i = 4; i > 0; i--) {
			setImage("Tank_Resources/spawn" + i + ".png");
			gp.pause(100);
		}

		for (int i = 1; i <= 4; i++) {
			setImage("Tank_Resources/spawn" + i + ".png");
			gp.pause(100);
		}
		gp.remove(this);
		this.battlefield.addTank(tank);
		new Thread(tank).start();
		tank.sendToBack();
		gp.repaint();
	}
	
	public Thread getCurrentThread() {
		return Thread.currentThread();
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
}
