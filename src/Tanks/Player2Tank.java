package Tanks;

import Main.Main;
import Map.Map;

/*
 * Ahmet Emre Unal
 * Burak Tutanlar
 * Celebi Murat
 */

public class Player2Tank extends Tank implements Runnable {
	private static final int SPEED = 3;
	private static final int HP = 3;

	public Player2Tank(Map main_MAP) throws Exception {
		super(1, SPEED, HP, main_MAP, "Tank_Resources/player_2_north.png");
		NORTH_IMG = "Tank_Resources/player_2_north.png"; // #1
		WEST_IMG = "Tank_Resources/player_2_west.png"; // #2
		SOUTH_IMG = "Tank_Resources/player_2_south.png"; // #3
		EAST_IMG = "Tank_Resources/player_2_east.png"; // #4
		initializeDimensions();
		setLocation(blockSize * 9 + (blockSize - getWidth()) / 2, blockSize * 14 + (blockSize - getHeight()) / 2);
		animation = new TankSpawnAnimation(this, battlefield);
	}
	
	public void run() {
		while(threadIsRunning) {
			moveOneFrame();
			if(BulletExists()) {
				bullet.moveOneFrame();
			}
			gp.repaint();
			gp.pause(Main.PAUSE_TIME);
		}
	}
}