package Tanks;

import Main.Main;
import Map.Map;

/*
 * Ahmet Emre Unal
 * Burak Tutanlar
 * Celebi Murat
 */

/*
 * Enemy 4: Armor Tank
 * 
 * This tank has the most defensive power. They can move and shoot at normal
 * speeds. However, it takes four shots to destroy them. They start out green
 * and gradually become gray until the fourth shot destroys them. It's highly
 * recommended not to destroy tanks with head-on approach before you've
 * collected 2nd star powerup. Each armor tank scores 400.
 */

public class ArmorTank extends Tank implements Runnable {
	private static final int SPEED = 2;
	private static final int HP = 3;
	private static final double randomTurnChance = 0.03;
	private static final double randomFireChance = 0.01;

	public ArmorTank(Map main_MAP) throws Exception {
		super(1, SPEED, HP, main_MAP, "Tank_Resources/enemy_4_north.png");
		NORTH_IMG = "Tank_Resources/enemy_4_north.png"; // #1
		WEST_IMG = "Tank_Resources/enemy_4_west.png"; // #2
		SOUTH_IMG = "Tank_Resources/enemy_4_south.png"; // #3
		EAST_IMG = "Tank_Resources/enemy_4_east.png"; // #4
		initializeDimensions();
		initializeEnemyLocation();
		turnSouth();
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
		while (threadIsRunning) {
			int playerDirection = playerInSight();
			if (playerDirection != 0) {
				switch (playerDirection) {
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
