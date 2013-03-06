package Tanks;

import Blocks.BaseBlock;
import Blocks.BrickBlock;
import Blocks.TreeBlock;
import Main.Main;
import Map.Map;

/*
 * Ahmet Emre Unal
 * Burak Tutanlar
 * Celebi Murat
 */

/*
 * Enemy 2: Fast Tank
 * 
 * This is the fastest enemy tank. They can move at a substantially faster rate
 * than your tank can travel. Their bullets, however, travel at normal speed. It
 * takes one shot to destroy them. These are generally more dangerous to
 * headquarters than a player and should be dispatched quickly. Each fast tank
 * scores 200.
 */

public class FastTank extends Tank implements Runnable {
	private static final int SPEED = 4;
	private static final int HP = 1;
	private static final double randomTurnChance = 0.03;
	private static final double randomFireChance = 0.01;
	private static final double seeingPlayerChance = 0.7;
	private static final double otherBrickFireChance = 0.7;
	private static final double southBrickFireChance = 0.5;
	private static final double turningSouthChance = 0.1;

	public FastTank(Map main_MAP) throws Exception {
		super(1, SPEED, HP, main_MAP, "Tank_Resources/enemy_2_north.png");
		NORTH_IMG = "Tank_Resources/enemy_2_north.png"; // #1
		WEST_IMG = "Tank_Resources/enemy_2_west.png"; // #2
		SOUTH_IMG = "Tank_Resources/enemy_2_south.png"; // #3
		EAST_IMG = "Tank_Resources/enemy_2_east.png"; // #4
		initializeDimensions();
		initializeEnemyLocation();
		turnSouth();
		animation = new TankSpawnAnimation(this, battlefield);
	}

	public void randomDirection(int directionGiven) {
		int direction;
		do {
			direction = randomGenerator.nextInt(5);
		} while (direction == 0 && direction != directionGiven);
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

	public void changeDirection(int direction) {
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
	
	public void randomDirection() {
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

	public void shootToPlayer() {
		changeDirection(playerInSight());
		fire();
	}

	public boolean canMoveToSouth() {
		return ((getBlockingObjectAt(SOUTH) instanceof BrickBlock)
				|| (getBlockingObjectAt(SOUTH) == null) || (getBlockingObjectAt(SOUTH) instanceof TreeBlock))
				&& getY() <= Main.mapDimension - Main.blockSize - 10;
	}

	public void run() {
		while (threadIsRunning) {
			if (playerInSight() != 0 && randomGenerator.nextDouble() < seeingPlayerChance) {
				shootToPlayer();
				moveOneFrame();
			} else if (tankIsAllowedToMove && tankCanMove()) {
				moveOneFrame();
				if (canMoveToSouth() && randomGenerator.nextDouble() < turningSouthChance) {
					turnSouth();
				}
			} else if (!tankIsAllowedToMove) {
				if (randomGenerator.nextDouble() <= randomTurnChance) {
					randomDirection();
				}
				if (randomGenerator.nextDouble() <= randomFireChance) {
					fire();
				}
			} else {
				Object object = getBlockingObject();
				int freeSide = freeSide();
				if (freeSide != 0 && freeSide != NORTH) {

					if (object instanceof BaseBlock) {
						fire();
					} else if (object instanceof BrickBlock) {
						if (direction != SOUTH) {
							if (randomGenerator.nextDouble() < otherBrickFireChance) {
								fire();
							} else if (!checkDirection(SOUTH)) {
								randomDirection(SOUTH);
							} else {
								turnSouth();
							}
						} else {
							if (randomGenerator.nextDouble() < southBrickFireChance) {
								fire();
							} else {
								randomDirection(direction);
								moveOneFrame();
							}
						}
					} else {
						randomDirection(direction);
						moveOneFrame();
					}
				} else if (checkDirection((direction + 1) % 4 + 1)
						&& randomGenerator.nextDouble() < randomFireChance) {
					changeDirection((direction + 1) % 4 + 1);
					fire();
					moveOneFrame();
				} else if (checkDirection((direction + 3) % 4 + 1)
						&& randomGenerator.nextDouble() < randomFireChance) {
					changeDirection((direction + 3) % 4 + 1);
					fire();
					moveOneFrame();

				} else if (getY() < Main.mapDimension - Main.blockSize) {
					int x = (int) getX() + 1;
					int mapDim = Main.mapDimension;
					if (mapDim / x <= 2 && direction != WEST) {
						turnWest();
					} else if (mapDim / x > 2 && direction != EAST) {
						turnEast();
					} else if ((mapDim / x <= 2 && direction == WEST)
							|| (mapDim / x > 2 && direction == EAST)) {
						turnSouth();
					} else {
						randomDirection(direction);
					}
				} else {
					int x = (int) getX();
					int mapDim = Main.mapDimension;
					if (mapDim / x >= 2) {
						if (randomGenerator.nextDouble() <= randomTurnChance) {
							turnWest();
						} else {
							turnEast();
						}
					} else {
						if (randomGenerator.nextDouble() <= randomTurnChance) {
							turnEast();
						} else {
							turnWest();
						}
					}
				}
			}
			if (BulletExists()) {
				bullet.moveOneFrame();
			}
			gp.repaint();
			gp.pause(Main.PAUSE_TIME);
		}
	}
}