package Tanks;

import java.util.Random;

import Blocks.BaseBlock;
import Blocks.BrickBlock;
import Blocks.SteelBlock;
import Blocks.TreeBlock;
import Blocks.WaterBlock;
import Main.Main;
import Map.Map;
import Map.MapCellCoordinate;
import PowerUp.PowerUp;
import Sounds.TankFiredSound;
import Stages.Stage;
import acm.graphics.GImage;
import acm.graphics.GObject;
import acm.program.GraphicsProgram;

/*
 * Ahmet Emre Unal
 * Burak Tutanlar
 * Celebi Murat
 */

public abstract class Tank extends GImage implements Runnable {
	protected int direction;
	private int speed;
	private int hp;

	protected boolean threadIsRunning = true;

	protected boolean isDestroyable = true;
	protected boolean canDestroySteel = false;
	protected boolean tankIsAllowedToMove = true;
	protected boolean canOneShot = false;

	protected Map battlefield;
	protected GraphicsProgram gp;
	protected Stage stage;
	protected static int blockSize = Map.BLOCK_SIZE;

	protected Random randomGenerator = new Random();

	protected TankBullet bullet;
	protected boolean bulletExists = false;

	protected boolean movingNorth = false;
	protected boolean movingEast = false;
	protected boolean movingSouth = false;
	protected boolean movingWest = false;

	public static final int NORTH = 1;
	public static final int WEST = 2;
	public static final int SOUTH = 3;
	public static final int EAST = 4;

	protected String NORTH_IMG;
	protected String WEST_IMG;
	protected String SOUTH_IMG;
	protected String EAST_IMG;

	protected int NORTH_DIM_W;
	protected int NORTH_DIM_H;
	protected int WEST_DIM_W;
	protected int WEST_DIM_H;
	protected int SOUTH_DIM_W;
	protected int SOUTH_DIM_H;
	protected int EAST_DIM_W;
	protected int EAST_DIM_H;
	
	protected TankSpawnAnimation animation;

	public Tank(int direction, int speed, int hp, Map battlefield,
			String imageLocation) throws InterruptedException {
		super(imageLocation);
		this.direction = direction;
		this.speed = speed;
		this.hp = hp;
		this.battlefield = battlefield;
		this.gp = Map.getGraphicsProgram();
		this.stage = battlefield.getStage();
		sendToBack();
	}

	protected void initializeDimensions() {
		GImage img;
		img = new GImage(NORTH_IMG);
		NORTH_DIM_W = (int) img.getWidth();
		NORTH_DIM_H = (int) img.getHeight();

		img = new GImage(WEST_IMG);
		WEST_DIM_W = (int) img.getWidth();
		WEST_DIM_H = (int) img.getHeight();

		img = new GImage(SOUTH_IMG);
		SOUTH_DIM_W = (int) img.getWidth();
		SOUTH_DIM_H = (int) img.getHeight();

		img = new GImage(EAST_IMG);
		EAST_DIM_W = (int) img.getWidth();
		EAST_DIM_H = (int) img.getHeight();
	}

	public void fire() {
		if (!BulletExists()) {
			bulletExists = true;
			int x = (int) this.getX();
			int y = (int) this.getY();
			if (direction == NORTH) {
				x += (this.getWidth() / 2) - 1;
				y -= 3;
			} else if (direction == WEST) {
				x -= 3;
				y += (this.getHeight() / 2) - 1;
			} else if (direction == SOUTH) {
				x += (this.getWidth() / 2) - 1;
				y += this.getHeight() + 1;
			} else if (direction == EAST) {
				x += this.getWidth() + 1;
				y += (this.getHeight() / 2) - 1;
			}
			if (this instanceof Player1Tank || this instanceof Player2Tank) {
				new TankFiredSound(gp);
			}
			this.bullet = new TankBullet(x, y, direction, battlefield, this);
		}
	}

	public void destroyBullet() {
		if (bulletExists) {
			battlefield.removeBullet(bullet);
			this.bullet = null;
			bulletExists = false;
		}
	}

	public int playerInSight() {
		int originalPosX = (int) (this.getX() + (getWidth() / 2));
		int originalPosY = (int) (this.getY() + (getHeight() / 2));
		int locX = originalPosX;
		int locY = originalPosY;
		boolean playerSighted = false;
		// North
		while ((locY >= 0) && !playerSighted) {
			locY--;
			Object object = Map.getElementAtLocation(locX, locY);
			if (object != null) {
				if (object instanceof Player1Tank || object instanceof Player2Tank) {
					return NORTH;
				} else if (object instanceof BrickBlock || object instanceof SteelBlock
						|| object instanceof BaseBlock) {
					break;
				}
			}
		}
		locX = originalPosX;
		locY = originalPosY;
		// West
		while ((locX >= 0) && !playerSighted) {
			locX--;
			Object object = Map.getElementAtLocation(locX, locY);
			if (object != null) {
				if (object instanceof Player1Tank || object instanceof Player2Tank) {
					return WEST;
				} else if (object instanceof BrickBlock || object instanceof SteelBlock
						|| object instanceof BaseBlock) {
					break;
				}
			}
		}
		locX = originalPosX;
		locY = originalPosY;
		// South
		while ((locY <= Main.mapDimension) && !playerSighted) {
			locY++;
			Object object = Map.getElementAtLocation(locX, locY);
			if (object != null) {
				if (object instanceof Player1Tank || object instanceof Player2Tank) {
					return SOUTH;
				} else if (object instanceof BrickBlock || object instanceof SteelBlock
						|| object instanceof BaseBlock) {
					break;
				}
			}
		}
		locX = originalPosX;
		locY = originalPosY;
		// East
		while ((locX <= Main.mapDimension) && !playerSighted) {
			locX++;
			Object object = Map.getElementAtLocation(locX, locY);
			if (object != null) {
				if (object instanceof Player1Tank || object instanceof Player2Tank) {
					return EAST;
				} else if (object instanceof BrickBlock || object instanceof SteelBlock
						|| object instanceof BaseBlock) {
					break;
				}
			}
		}
		return 0;
	}

	// Movement
	public void moveOneFrame() {
		for (int i = 0; i < getSpeed(); i++) {
			if (tankCanMove() && tankIsAllowedToMove) {
				moveOnePixel();
			}
		}
	}

	public void moveOnePixel() {
		if (movingNorth && direction == NORTH) {
			move(0, -1);
		} else if (movingEast && direction == EAST) {
			move(1, 0);
		} else if (movingSouth && direction == SOUTH) {
			move(0, 1);
		} else if (movingWest && direction == WEST) {
			move(-1, 0);
		}
	}

	public int freeSide() {
		if (checkDirection(SOUTH)) {
			return SOUTH;
		} else if (checkDirection(WEST)) {
			return WEST;
		} else if (checkDirection(EAST)) {
			return EAST;
		} else if (checkDirection(NORTH)) {
			return NORTH;
		} else {
			return 0;
		}
	}

	public boolean tankCanMove() {
		double locX = this.getX();
		double locY = this.getY();
		if (direction == NORTH) {
			locY -= 1;
			for (int i = 0; i <= this.getWidth(); i++) {
				GObject object = Map.getElementAtLocation(locX + i, locY);
				if (blockingObject(object) || locY <= 0) {
					return false;
				}
			}
			return true;
		} else if (direction == EAST) {
			locX += this.getWidth() + 1;
			for (int i = 0; i <= this.getHeight(); i++) {
				GObject object = Map.getElementAtLocation(locX, locY + i);
				if (blockingObject(object) || locX >= Map.getMapWidth()) {
					return false;
				}
			}
			return true;
		} else if (direction == SOUTH) {
			locY += this.getHeight() + 1;
			for (int i = 0; i <= this.getWidth(); i++) {
				GObject object = Map.getElementAtLocation(locX + i, locY);
				if (blockingObject(object) || locY >= Map.getMapHeight()) {
					return false;
				}
			}
			return true;
		} else if (direction == WEST) {
			locX -= 1;
			for (int i = 0; i <= this.getHeight(); i++) {
				GObject object = Map.getElementAtLocation(locX, locY + i);
				if (blockingObject(object) || locX <= 0) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}

	public boolean checkDirection(int direction) {
		double locX = this.getX();
		double locY = this.getY();
		if (direction == NORTH) {
			locY -= 1;
			for (int i = 0; i <= this.getWidth(); i++) {
				GObject object = Map.getElementAtLocation(locX + i, locY);
				if (blockingObject(object) || locY <= 0) {
					return false;
				}
			}
			return true;
		} else if (direction == EAST) {
			locX += this.getWidth() + 1;
			for (int i = 0; i <= this.getHeight(); i++) {
				GObject object = Map.getElementAtLocation(locX, locY + i);
				if (blockingObject(object) || locX >= Map.getMapWidth()) {
					return false;
				}
			}
			return true;
		} else if (direction == SOUTH) {
			locY += this.getHeight() + 1;
			for (int i = 0; i <= this.getWidth(); i++) {
				GObject object = Map.getElementAtLocation(locX + i, locY);
				if (blockingObject(object) || locY >= Map.getMapHeight()) {
					return false;
				}
			}
			return true;
		} else if (direction == WEST) {
			locX -= 1;
			for (int i = 0; i <= this.getHeight(); i++) {
				GObject object = Map.getElementAtLocation(locX, locY + i);
				if (blockingObject(object) || locX <= 0) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}

	public Object getBlockingObject() {
		int x = (int) this.getX();
		int y = (int) this.getY();
		if (direction == NORTH) {
			x += (this.getWidth() / 2);
			y -= 2;
		} else if (direction == WEST) {
			x -= 2;
			y += (this.getHeight() / 2);
		} else if (direction == SOUTH) {
			x += (this.getWidth() / 2);
			y += this.getHeight() + 2;
		} else if (direction == EAST) {
			x += this.getWidth() + 2;
			y += (this.getHeight() / 2);
		}
		return Map.getElementAtLocation(x, y);
	}

	public Object getBlockingObjectAt(int checkingDirection) {
		double x = this.getX();
		double y = this.getY();
		double w = this.getWidth();
		double h = this.getHeight();
		GObject object;
		int checkLength = 1;
		if (checkingDirection == NORTH) {
			x += (w / 2);
			y -= 10;
			checkLength = (int) (w / 2);
			for (int i = 0; i <= checkLength; i++) {
				if (this.direction == EAST) {
					object = Map.getElementAtLocation(x - i, y);
				} else {
					object = Map.getElementAtLocation(x + i, y);
				}
				if (object != null) {
					return object;
				}
			}
			return null;
		} else if (checkingDirection == WEST) {
			x -= 10;
			y += (h / 2);
			checkLength = (int) (h / 2);
			for (int i = 0; i <= checkLength; i++) {
				if (this.direction == NORTH) {
					object = Map.getElementAtLocation(x, y + i);
				} else {
					object = Map.getElementAtLocation(x, y - i);
				}
				if (object != null) {
					return object;
				}
			}
			return null;
		} else if (checkingDirection == SOUTH) {
			x += (w / 2);
			y += h + 10;
			checkLength = (int) (w / 2);
			for (int i = 0; i <= checkLength; i++) {
				if (this.direction == EAST) {
					object = Map.getElementAtLocation(x - i, y);
				} else {
					object = Map.getElementAtLocation(x + i, y);
				}
				if (object != null) {
					return object;
				}
			}
			return null;
		} else if (checkingDirection == EAST) {
			x += w + 10;
			y += (h / 2);
			checkLength = (int) (h / 2);
			for (int i = 0; i <= checkLength; i++) {
				if (this.direction == NORTH) {
					object = Map.getElementAtLocation(x, y + i);
				} else {
					object = Map.getElementAtLocation(x, y - i);
				}
				if (object != null) {
					return object;
				}
			}
			return null;
		} else {
			return Map.getElementAtLocation(x, y);
		}
	}

	public boolean blockingObject(Object object) {
		if (object instanceof BrickBlock) {
			return true;
		} else if (object instanceof WaterBlock) {
			return true;
		} else if (object instanceof TreeBlock) {
			return false;
		} else if (object instanceof SteelBlock) {
			return true;
		} else if (object instanceof Tank) {
			return true;
		} else if (object instanceof BaseBlock) {
			return true;
		} else if (object instanceof PowerUp) {
			if (this instanceof Player1Tank || this instanceof Player2Tank) {
				PowerUp gift = (PowerUp) object;
				battlefield.removePowerUp();
				Map.repaintMap();
				gift.giveEffects(this);
			}
			return false;
		} else {
			return false;
		}
	}

	public void turnNorth() {
		movingNorth = true;
		this.setSize(NORTH_DIM_W, NORTH_DIM_H);
		this.setImage(NORTH_IMG);
		direction = NORTH;
		Map.repaintMap();
		Map.pause(1);
	}

	public void turnEast() {
		movingEast = true;
		this.setSize(EAST_DIM_W, EAST_DIM_H);
		this.setImage(EAST_IMG);
		direction = EAST;
		Map.repaintMap();
		Map.pause(1);
	}

	public void turnSouth() {
		movingSouth = true;
		this.setSize(SOUTH_DIM_W, SOUTH_DIM_H);
		this.setImage(SOUTH_IMG);
		direction = SOUTH;
		Map.repaintMap();
		Map.pause(1);
	}

	public void turnWest() {
		movingWest = true;
		this.setSize(WEST_DIM_W, WEST_DIM_H);
		this.setImage(WEST_IMG);
		direction = WEST;
		Map.repaintMap();
		Map.pause(1);
	}

	public void stopNorth() {
		movingNorth = false;
	}

	public void stopEast() {
		movingEast = false;
	}

	public void stopSouth() {
		movingSouth = false;
	}

	public void stopWest() {
		movingWest = false;
	}
	// End of Movement

	public void getHit() {
		if (this.isDestroyable) {
			this.hp--;
			battlefield.getMain().refreshSideMenu();
			if (this.hp <= 0) {
				battlefield.destroyTank(this);
			}
		}
	}

	public void getOneShot() {
		if (this.isDestroyable) {
			battlefield.destroyTank(this);
		}
	}

	protected void initializeEnemyLocation() {
		MapCellCoordinate spawnPoint = stage.getSpawnPoint(randomGenerator.nextInt(2));
		setLocation(spawnPoint.getX(), spawnPoint.getY());
	}

	// Getters & Setters
	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public void setDestroyability(boolean b) {
		this.isDestroyable = b;
	}

	public void setOneShot(boolean b) {
		if (b) {
			canDestroySteel = true;
		} else {
			canDestroySteel = false;
		}
		this.canOneShot = b;
	}

	public boolean oneShots() {
		return canOneShot;
	}

	public void setCanMove(boolean b) {
		this.tankIsAllowedToMove = b;
	}

	public boolean isTankAllowedToMove() {
		return tankIsAllowedToMove;
	}

	public boolean BulletExists() {
		return bulletExists && bullet != null;
	}

	public TankBullet getBullet() {
		return bullet;
	}

	public boolean destroysSteel() {
		return canDestroySteel;
	}

	public int getHp() {
		return hp;
	}

	public void incrementHp() {
		hp++;
		battlefield.getMain().refreshSideMenu();
	}

	public boolean isThreadIsRunning() {
		return threadIsRunning;
	}

	public void setThreadIsRunning(boolean threadIsRunning) {
		this.threadIsRunning = threadIsRunning;
	}
}