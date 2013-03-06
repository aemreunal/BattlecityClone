package Tanks;

import Main.Main;
import Map.Map;

/*
 * Ahmet Emre Unal
 * Burak Tutanlar
 * Celebi Murat
 */

/*
 * Enemy 3: Power Tank
 * 
 * This tank has the greatest offensive power. They move at the speed of
 * normal tanks, but their bullets travel much faster, as fast as your
 * tank can when you collect the star power up. It takes one shot to
 * destroy them. It's highly advised not to enter into their line of fire.
 * Although they are generally more dangerous to player, they will cut
 * through bricks much quicker than other tanks which is especially clear
 * in level 34.
 * Each power tank scores 300.
 */

public class PowerTank extends Tank implements Runnable {
	private static final int SPEED = 2;
	private static final int HP = 2;
	private static final double randomTurnChance = 0.03;
	private static final double randomFireChance = 0.01;

	public PowerTank(Map main_MAP) throws Exception {
		super(1, SPEED, HP, main_MAP, "Tank_Resources/enemy_3_north.png");
		NORTH_IMG = "Tank_Resources/enemy_3_north.png"; // #1
		WEST_IMG = "Tank_Resources/enemy_3_west.png"; // #2
		SOUTH_IMG = "Tank_Resources/enemy_3_south.png"; // #3
		EAST_IMG = "Tank_Resources/enemy_3_east.png"; // #4
		initializeDimensions();
		initializeEnemyLocation();
		setOneShot(true);
		animation = new TankSpawnAnimation(this, battlefield);
	}	
	
	public void changeDirection() {
		int direction = randomGenerator.nextInt(5);
		while (direction == 0) {
			direction = randomGenerator.nextInt(5);
		}
		switch (direction) {
		case NORTH:
			turnNorth();
			break;
		case WEST:
			turnWest();
			break;
		case SOUTH:
			turnSouth();
			break;
		case EAST:
			turnEast();
			break;
		}
	}

	public void moveOnePixel() {
		if (direction == NORTH) {
			move(0, -1);
		} else if (direction == EAST) {
			move(1, 0);
		} else if (direction == SOUTH) {
			move(0, 1);
		} else if (direction == WEST) {
			move(-1, 0);
		}
	}

	public void run() {
		setOneShot(true);
		while (threadIsRunning) {
			if (playerInSight() != 0) {
				switch (playerInSight()) {
				case NORTH:
					turnNorth();
					break;
				case WEST:
					turnWest();
					break;
				case SOUTH:
					turnSouth();
					break;
				case EAST:
					turnEast();
					break;
				}
				fire();
				if (tankCanMove() && tankIsAllowedToMove) {
					moveOneFrame();
					if (randomGenerator.nextDouble() <= randomTurnChance) {
						changeDirection();
					}
				}
			} else if (tankIsAllowedToMove && tankCanMove()) {
				moveOneFrame();
				if (randomGenerator.nextDouble() <= randomTurnChance) {
					changeDirection();
				}
			} else if(!tankIsAllowedToMove) {
				if (randomGenerator.nextDouble() <= randomTurnChance) {
					changeDirection();
				}
				if (randomGenerator.nextDouble() <= randomFireChance) {
					fire();
				}
			} else {
				fire();
				if (tankCanMove() && tankIsAllowedToMove) {
					moveOneFrame();
				} else {
					changeDirection();
				}
			}
			if (randomGenerator.nextDouble() <= randomFireChance) {
				fire();
			}
			if (BulletExists()) {
				bullet.moveOneFrame();
			}
			gp.repaint();
			gp.pause(Main.PAUSE_TIME);
		}
	}
}