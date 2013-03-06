package Tanks;

import java.awt.Color;

import Blocks.BaseBlock;
import Blocks.BrickBlock;
import Blocks.SteelBlock;
import Map.Map;
import Sounds.TankHitSound;
import acm.graphics.GObject;
import acm.graphics.GRect;

/*
 * Ahmet Emre Unal
 * Burak Tutanlar
 * Celebi Murat
 */

public class TankBullet extends GRect {
	private int direction;
	private int speed = 11;
	private Map battlefield;
	private Tank tank;

	private static int bulletWidth_N_S = 1;
	private static int bulletHeight_N_S = 7;
	private static int bulletWidth_W_E = 7;
	private static int bulletHeight_W_E = 1;

	private final int NORTH = 1;
	private final int WEST = 2;
	private final int SOUTH = 3;
	private final int EAST = 4;

	public TankBullet(int x, int y, int direction, Map battlefield,
			Tank tank) {
		super(x, y, bulletWidth_N_S, bulletHeight_N_S);
		if (direction == WEST || direction == EAST) {
			this.setSize(bulletWidth_W_E, bulletHeight_W_E);
		}
		this.direction = direction;
		this.setFilled(true);
		this.setColor(Color.WHITE);
		this.battlefield = battlefield;
		this.tank = tank;
		this.battlefield.addBullet(this);
	}

	public void moveOneFrame() {
		for (int i = 0; i < speed; i++) {
			if (direction == NORTH) {
				move(0, -1);
			} else if (direction == EAST) {
				move(1, 0);
			} else if (direction == SOUTH) {
				move(0, 1);
			} else if (direction == WEST) {
				move(-1, 0);
			}
			if (tank.BulletExists()) {
				bulletControl();
			}
		}
	}

	public void bulletControl() {
		if (bulletOutsideMap()) {
			tank.destroyBullet();
		} else {
			double locX = this.getX();
			double locY = this.getY();
			if (direction == NORTH) {
				locY -= 1;
			} else if (direction == EAST) {
				locX += bulletWidth_W_E + 1;
			} else if (direction == SOUTH) {
				locY += bulletHeight_N_S + 1;
			} else if (direction == WEST) {
				locX -= 1;
			}
			GObject object = Map.getElementAtLocation(locX, locY);
			bulletHit(object);
		}
	}

	public void bulletHit(GObject object) {
		if (object != null) {
			boolean oneShots = this.tank.oneShots();
			if (object instanceof Tank) {
				new TankHitSound(Map.getGraphicsProgram());
				this.tank.destroyBullet();
				Tank tank = (Tank) object;
				if (this.tank instanceof BasicTank || this.tank instanceof ArmorTank || this.tank instanceof FastTank || this.tank instanceof PowerTank) {
					if (tank instanceof Player1Tank || tank instanceof Player2Tank) {
						tank.getHit();
					}
				} else {
					if (tank instanceof BasicTank || tank instanceof ArmorTank || tank instanceof FastTank || tank instanceof PowerTank) {
						if (oneShots) {
							tank.getOneShot();
						} else {
							tank.getHit();
						}
					}
				}
			} else if (object instanceof BrickBlock) {
				new TankHitSound(Map.getGraphicsProgram());
				this.tank.destroyBullet();
				BrickBlock brick = (BrickBlock) object;
				if (oneShots) {
					brick.getOneShot();
				} else {
					brick.getHit();
				}
			} else if (object instanceof BaseBlock) {
				new TankHitSound(Map.getGraphicsProgram());
				this.tank.destroyBullet();
				BaseBlock base = (BaseBlock) object;
				base.getHit();
			} else if (object instanceof SteelBlock) {
				new TankHitSound(Map.getGraphicsProgram());
				this.tank.destroyBullet();
				if (tank.destroysSteel()) {
					SteelBlock steel = (SteelBlock) object;
					steel.getHit();
				}
			} else if (object instanceof TankBullet) {
				new TankHitSound(Map.getGraphicsProgram());
				this.tank.destroyBullet();
				TankBullet bullet = (TankBullet) object;
				bullet.getTank().destroyBullet();
			}
		}
	}

	public boolean bulletOutsideMap() {
		double bulletX = this.getX();
		double bulletY = this.getY();
		double bulletWidth = this.getWidth();
		double bulletHeight = this.getHeight();
		double mapWidth = Map.getMapWidth();
		double mapHeight = Map.getMapHeight();
		if (direction == NORTH) {
			return bulletY <= 0;
		} else if (direction == EAST) {
			return (bulletX + (bulletWidth / 2)) >= mapWidth;
		} else if (direction == SOUTH) {
			return (bulletY + (bulletHeight / 2)) >= mapHeight;
		} else if (direction == WEST) {
			return bulletX <= 0;
		} else {
			return true;
		}
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public Tank getTank() {
		return tank;
	}

}