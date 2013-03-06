package Map;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import Blocks.BaseBlock;
import Blocks.Block;
import Blocks.BrickBlock;
import Blocks.SteelBlock;
import Blocks.TreeBlock;
import Blocks.WaterBlock;
import Main.Main;
import PowerUp.PowerUp;
import PowerUp.GrenadePowerUp;
import PowerUp.HelmetPowerUp;
import PowerUp.ShovelPowerUp;
import PowerUp.StarPowerUp;
import PowerUp.TankUpPowerUp;
import PowerUp.TimerPowerUp;
import Sounds.TankExplosionSound;
import Stages.Stage;
import Tanks.ArmorTank;
import Tanks.BasicTank;
import Tanks.TankBullet;
import Tanks.TankExplosionAnimation;
import Tanks.FastTank;
import Tanks.Player1Tank;
import Tanks.Player2Tank;
import Tanks.PowerTank;
import Tanks.Tank;
import acm.graphics.GObject;
import acm.program.GraphicsProgram;

/*
 * Ahmet Emre Unal
 * Burak Tutanlar
 * Celebi Murat
 */

public class Map {
	public static final int BLOCK_SIZE = 48;
	public static final int MAP_SIZE = 15;
	private Block[][] blocks = new Block[MAP_SIZE][MAP_SIZE];
	private char[][] mapTiles = new char[MAP_SIZE][MAP_SIZE];
	private ArrayList<Tank> tanks;
	private ArrayList<TankBullet> firedBullets = new ArrayList<TankBullet>();
	private ArrayList<Character> tanksWaitingToSpawn;

	private Stage stage;
	private static Main main;
	private static GraphicsProgram graphicsProgram;

	private int numberOfPlayers;
	private int enemyTankCount;

	private Random randomGenerator = new Random();

	private Player1Tank player1;
	private Player2Tank player2;

	private PowerUp currentPowerUp;
	private boolean powerUp1Given = false;
	private boolean powerUp2Given = false;
	private boolean powerUp3Given = false;
	private boolean powerUp4Given = false;

	private javax.swing.Timer enemySpawnTimer;
	private int enemySpawnDelay = 3000;

	public Map(Stage stage, Main main, int players) {
		this.stage = stage;
		tanksWaitingToSpawn = this.stage.getTanks();
		Map.main = main;
		Map.graphicsProgram = (GraphicsProgram) main;
		numberOfPlayers = players;
		enemyTankCount = numberOfPlayers + (5 * numberOfPlayers);
		paintMap();
		tanks = new ArrayList<Tank>();
		loadPlayers();
		setBlocksZCoordinates();
		repaintMap();
		Main.setState(Main.GAME_STATE);
		startEnemySpawns();
		spawnEnemyTank();
	}

	private void setBlocksZCoordinates() {
		for (int i = 0; i < MAP_SIZE; i++) {
			for (int j = 0; j < MAP_SIZE; j++) {
				char block = mapTiles[i][j];
				if (block == 'w') {
					blocks[i][j].sendBackward();
				} else if (block == 'i') {
					blocks[i][j].sendBackward();
				} else if (block == 't') {
					blocks[i][j].sendToFront();
				}
			}
		}
	}

	public void paintMap() {
		mapTiles = stage.getMap();
		try {
			for (int i = 0; i < MAP_SIZE; i++) {
				for (int j = 0; j < MAP_SIZE; j++) {
					char block = mapTiles[i][j];
					if (block == 'w') {
						blocks[i][j] = (new WaterBlock(j * BLOCK_SIZE, i * BLOCK_SIZE, this));
					} else if (block == 't') {
						blocks[i][j] = (new TreeBlock(j * BLOCK_SIZE, i * BLOCK_SIZE, this));
					} else if (block == 'b') {
						blocks[i][j] = (new BrickBlock(j * BLOCK_SIZE, i * BLOCK_SIZE, this));
					} else if (block == 's') {
						blocks[i][j] = (new SteelBlock(j * BLOCK_SIZE, i * BLOCK_SIZE, this));
					} else if (block == 'k') {
						blocks[i][j] = (new BaseBlock(j * BLOCK_SIZE, i * BLOCK_SIZE, this));
					}
				}
			}
		} catch (IOException e) {
			System.out.println("Couldn't find the images!!!");
		} finally {
			System.out.println("Done loading blocks!");
		}
	}

	public void givePowerUp() {
		if (currentPowerUp == null) {
			try {
				int randomPowerUp = randomGenerator.nextInt(6);
				if (randomPowerUp == 0) {
					currentPowerUp = new GrenadePowerUp(this);
				} else if (randomPowerUp == 1) {
					currentPowerUp = new HelmetPowerUp(this);
				} else if (randomPowerUp == 2) {
					currentPowerUp = new ShovelPowerUp(this);
				} else if (randomPowerUp == 3) {
					currentPowerUp = new StarPowerUp(this);
				} else if (randomPowerUp == 4) {
					currentPowerUp = new TankUpPowerUp(this);
				} else if (randomPowerUp == 5) {
					currentPowerUp = new TimerPowerUp(this);
				}
			} catch (Exception e) {
				System.out.println("Couldn't load Power Up!");
				e.printStackTrace();
			}
		} else {
			currentPowerUp.removeEffects(currentPowerUp.getTank());
			removePowerUp();
		}
	}

	public void checkForGivingPowerUp() {
		int count = 0;
		if (tanks.contains(player1)) {
			count++;
		}
		if (tanks.contains(player2)) {
			count++;
		}
		int numberOfEnemies = tanksWaitingToSpawn.size() + tanks.size() - count;
		if (!powerUp1Given && numberOfEnemies == 17) {
			givePowerUp();
			powerUp1Given = true;
		} else if (!powerUp2Given && numberOfEnemies == 12) {
			givePowerUp();
			powerUp2Given = true;
		} else if (!powerUp3Given && numberOfEnemies == 8) {
			givePowerUp();
			powerUp3Given = true;
		} else if (!powerUp4Given && numberOfEnemies == 4) {
			givePowerUp();
			powerUp4Given = true;
		}
	}

	public void startEnemySpawns() {
		enemySpawnTimer = new javax.swing.Timer(enemySpawnDelay,
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						spawnEnemyTank();
						main.refreshSideMenu();
					}
				});
		enemySpawnTimer.start();
	}

	@SuppressWarnings("unused")
	public void spawnEnemyTank() {
		if (tanks.size() < enemyTankCount && tanksWaitingToSpawn.size() > 0
				&& Main.getState() == Main.GAME_STATE) {
			int i = randomGenerator.nextInt(tanksWaitingToSpawn.size());
			char tankToSpawn = tanksWaitingToSpawn.get(i);
			tanksWaitingToSpawn.remove(i);
			switch (tankToSpawn) {
			case 'b':
				try {
					BasicTank basicTank = new BasicTank(this);
				} catch (Exception e) {
					System.out.println("Cannot load Basic Tank!");
					e.printStackTrace();
				}
				break;
			case 'f':
				try {
					FastTank fastTank = new FastTank(this);
				} catch (Exception e) {
					System.out.println("Cannot load Fast Tank!");
					e.printStackTrace();
				}
				break;
			case 'a':
				try {
					ArmorTank armorTank = new ArmorTank(this);
				} catch (Exception e) {
					System.out.println("Cannot load Armor Tank!");
					e.printStackTrace();
				}
				break;
			case 'p':
				try {
					PowerTank powerTank = new PowerTank(this);
				} catch (Exception e) {
					System.out.println("Cannot load Power Tank!");
					e.printStackTrace();
				}
				break;
			}
		}
	}

	public void loadPlayers() {
		try {
			player1 = new Player1Tank(this);
		} catch (Exception e) {
			System.out.println("Cannot load Player 1 Tank!");
			e.printStackTrace();
		}

		if (numberOfPlayers == 2) {
			try {
				player2 = new Player2Tank(this);
			} catch (Exception e) {
				System.out.println("Cannot load Player 2 Tank!");
				e.printStackTrace();
			}
		}
	}

	public void addBlock(Block blockToAdd) {
		addObject(blockToAdd);
	}

	public void removeBlock(Block blockToRemove) {
		int x = (int) (blockToRemove.getX() + (blockToRemove.getWidth() / 2));
		int y = (int) (blockToRemove.getY() + (blockToRemove.getHeight() / 2));
		int arrayX = x / BLOCK_SIZE;
		int arrayY = y / BLOCK_SIZE;
		graphicsProgram.remove(blockToRemove);
		blocks[arrayY][arrayX] = null;
		mapTiles[arrayY][arrayX] = 'n';
	}

	public void addBullet(TankBullet bulletToAdd) {
		firedBullets.add(bulletToAdd);
		addObject(bulletToAdd);
		repaintMap();
		pause(1);
	}

	public void removeBullet(TankBullet bulletToAdd) {
		firedBullets.remove(bulletToAdd);
		graphicsProgram.remove(bulletToAdd);
		repaintMap();
		pause(1);
	}

	public void addTank(Tank tankToAdd) {
		tanks.add(tankToAdd);
		addObject(tankToAdd);
		repaintMap();
		pause(1);
	}

	public void destroyTank(Tank tankToRemove) {
		removeTank(tankToRemove);
		main.refreshSideMenu();
		new TankExplosionSound(graphicsProgram);
		new TankExplosionAnimation(tankToRemove);
		repaintMap();
		pause(1);
		checkWinCondition(tankToRemove);
		checkForGivingPowerUp();
	}
	
	public void removeTank(Tank tankToRemove) {
		tankToRemove.setThreadIsRunning(false);
		if (tankToRemove.BulletExists()) {
			removeBullet(tankToRemove.getBullet());
		}
		tanks.remove(tankToRemove);
		graphicsProgram.remove(tankToRemove);
		repaintMap();
		pause(1);
	}

	public void checkWinCondition(Tank tank) {
		if (numberOfPlayers == 1) {
			if (tank instanceof Player1Tank) {
				main.gameOver();
			}

			if (tanks.size() == 1 && tanksWaitingToSpawn.size() == 0) {
				main.gameWon();
			}
		} else {
			if (tank instanceof Player1Tank && !(tanks.contains(player2))) {
				main.gameOver();
			} else if (tank instanceof Player2Tank
					&& !(tanks.contains(player1))) {
				main.gameOver();
			}

			if (tanksWaitingToSpawn.size() == 0) {
				if (isGameWon()) {
					main.gameWon();
				}
			}
		}
	}

	public boolean isGameWon() {
		if (tanks.contains(player1) && !(tanks.contains(player2))) {
			return tanks.size() == 1;
		} else if (tanks.contains(player2) && !(tanks.contains(player1))) {
			return tanks.size() == 1;
		} else if (tanks.contains(player1) && tanks.contains(player1)) {
			return tanks.size() == 2;
		} else {
			return false;
		}
	}

	public void removePowerUp() {
		removeObject(currentPowerUp);
		currentPowerUp = null;
		repaintMap();
		pause(1);
	}

	public void keyPressed(KeyEvent e) {
		// Player 1
		if (e.getKeyCode() == KeyEvent.VK_W) {
			player1.turnNorth();
		} else if (e.getKeyCode() == KeyEvent.VK_D) {
			player1.turnEast();
		} else if (e.getKeyCode() == KeyEvent.VK_S) {
			player1.turnSouth();
		} else if (e.getKeyCode() == KeyEvent.VK_A) {
			player1.turnWest();
		}

		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			player1.fire();
		}

		// Player 2
		if (numberOfPlayers == 2) {
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				player2.turnNorth();
			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				player2.turnEast();
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				player2.turnSouth();
			} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				player2.turnWest();
			}

			if (e.getKeyCode() == KeyEvent.VK_M) {
				player2.fire();
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_G) {
			givePowerUp();
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_W) {
			player1.stopNorth();
		} else if (e.getKeyCode() == KeyEvent.VK_D) {
			player1.stopEast();
		} else if (e.getKeyCode() == KeyEvent.VK_S) {
			player1.stopSouth();
		} else if (e.getKeyCode() == KeyEvent.VK_A) {
			player1.stopWest();
		}

		if (numberOfPlayers == 2) {
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				player2.stopNorth();
			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				player2.stopEast();
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				player2.stopSouth();
			} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				player2.stopWest();
			}
		}
	}

	public void stopEnemySpawnTimer() {
		enemySpawnTimer.stop();
	}

	public int getBlockSize() {
		return BLOCK_SIZE;
	}

	public int getMapSize() {
		return MAP_SIZE;
	}

	public Stage getStage() {
		return stage;
	}

	public ArrayList<Tank> getTanks() {
		return tanks;
	}

	public Block[][] getBlocks() {
		return blocks;
	}

	public char[][] getMap() {
		return mapTiles;
	}

	public static GObject getElementAtLocation(double locX, double locY) {
		return graphicsProgram.getElementAt(locX, locY);
	}

	public static int getMapWidth() {
		return Main.mapDimension;
	}

	public static int getMapHeight() {
		return Main.mapDimension;
	}

	public Main getMain() {
		return main;
	}

	public javax.swing.Timer getEnemySpawnTimer() {
		return enemySpawnTimer;
	}

	public static void repaintMap() {
		graphicsProgram.repaint();
	}

	public static void gameOver() {
		main.gameOver();
	}

	public static void addObject(GObject object) {
		graphicsProgram.add(object);
		repaintMap();
	}

	public static void pause(int pauseTime) {
		graphicsProgram.pause(pauseTime);
		repaintMap();
	}

	public static void removeObject(GObject object) {
		graphicsProgram.remove(object);
		repaintMap();
	}

	public static GraphicsProgram getGraphicsProgram() {
		return graphicsProgram;
	}

	public Player1Tank getPlayer1() {
		return player1;
	}

	public Player2Tank getPlayer2() {
		return player2;
	}

	public ArrayList<Character> getTanksWaitingToSpawn() {
		return tanksWaitingToSpawn;
	}
}