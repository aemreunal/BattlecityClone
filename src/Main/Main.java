package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import Map.Map;
import Sounds.LevelStartSound;
import Stages.Stage;
import Tanks.Tank;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GLine;
import acm.program.GraphicsProgram;

/*
 * Ahmet Emre Unal
 * Burak Tutanlar
 * Celebi Murat
 */

@SuppressWarnings("serial")
public class Main extends GraphicsProgram {
	public static final int blockSize = 48;
	public static final int menuSpaceWidth = 130;
	public static final int mapDimension = 720;
	public static final int PAUSE_TIME = 30;

	public static final int MENU_STATE = 0;
	public static final int GAME_STATE = 1;
	public static final int PAUSE_STATE = 2;
	private static int STATE = MENU_STATE;
	
	private Map battlefield;
	private Stage stage;
	private int stageNumber;
	private int numberOfPlayers;

	private GLabel player1Hp;
	private GLabel player2Hp;
	private GLabel enemyCount;

	public void init() {
		setSize(mapDimension + menuSpaceWidth, mapDimension + blockSize);
		addKeyListeners();
		setBackground(Color.BLACK);
		initializeMenu();
		setBoundaries();
		setBottomText();
		setWindowPosition();
	}

	public void setBottomText() {
		GLabel label = new GLabel("CS 102, Spring 2012, Term Project    |"
				+ "    Ahmet Emre †nal - ‚elebi Murat - Burak Tutanlar"
				+ "    |    P1: WASD + Space  | ÊP2: Directional + M");
		label.setLocation(5, mapDimension + 18);
		label.setColor(Color.WHITE);
		add(label);
	}

	public void setWindowPosition() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int w = getSize().width;
		int h = getSize().height;
		int x = (dim.width - w) / 2;
		int y = ((dim.height - h) / 2) - 100;
		getParent().getParent().setLocation(x, y);
	}

	public void keyPressed(KeyEvent e) {
		if (STATE == GAME_STATE) {
			battlefield.keyPressed(e);
		}
	}

	public void keyReleased(KeyEvent e) {
		if (STATE == GAME_STATE) {
			battlefield.keyReleased(e);
		}
	}

	public void initializeMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");
		JMenu stageMenu = new JMenu("New game...");

		StageButton(1, stageMenu);
		StageButton(2, stageMenu);
		StageButton(3, stageMenu);
		StageButton(4, stageMenu);
		StageButton(5, stageMenu);

		JMenuItem resetButton = new JMenuItem("Reset");
		resetButton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
				ActionEvent.ALT_MASK));
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				reset();
			}
		});

		JMenuItem exitButton = new JMenuItem("Quit");
		exitButton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
				ActionEvent.ALT_MASK));
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});

		menu.add(stageMenu);
		menu.add(resetButton);
		menu.add(exitButton);
		menuBar.add(menu);
		setJMenuBar(menuBar);
	}

	public void initializeSideMenu() {
		GLabel player1Label = new GLabel("Player 1 HP ");
		player1Label.setFont("Arial-14");
		player1Label.setColor(Color.white);
		player1Label.setLocation(730, 50);

		player1Hp = new GLabel("" + battlefield.getPlayer1().getHp());
		player1Hp.setColor(Color.WHITE);
		player1Hp.setLocation(755, 90);
		player1Hp.setFont("Arial-40");

		add(player1Label);
		add(player1Hp);

		if (numberOfPlayers == 2) {
			GLabel player2Label = new GLabel("Player 2 HP ");
			player2Label.setFont("Arial-14");
			player2Label.setColor(Color.white);
			player2Label.setLocation(730, 140);

			player2Hp = new GLabel("" + battlefield.getPlayer2().getHp());
			player2Hp.setColor(Color.WHITE);
			player2Hp.setLocation(755, 180);
			player2Hp.setFont("Arial-40");

			add(player2Label);
			add(player2Hp);
		}

		GLabel enemyTankLabel = new GLabel("Enemy tanks left ");
		enemyTankLabel.setFont("Arial-14");
		enemyTankLabel.setColor(Color.WHITE);
		enemyTankLabel.setLocation(730, 230);

		enemyCount = new GLabel(""
				+ (battlefield.getTanksWaitingToSpawn().size()
						+ battlefield.getTanks().size() - numberOfPlayers));
		enemyCount.setColor(Color.WHITE);
		enemyCount.setLocation(755, 270);
		enemyCount.setFont("Arial-40");

		add(enemyTankLabel);
		add(enemyCount);
	}

	public void refreshSideMenu() {
		int count = 0;
		if (battlefield.getTanks().contains(battlefield.getPlayer1())) {
			player1Hp.setLabel("" + battlefield.getPlayer1().getHp());
			count++;
		}
		if (battlefield.getTanks().contains(battlefield.getPlayer2())) {
			player2Hp.setLabel("" + battlefield.getPlayer2().getHp());
			count++;
		}
		int enemyNumber = battlefield.getTanksWaitingToSpawn().size()
				+ battlefield.getTanks().size() - count;
		enemyCount.setLabel("" + enemyNumber);
		repaint();
	}

	public void StageButton(final int stageNumber, JMenu menu) {
		JMenu nthStageMenu = new JMenu("Stage " + stageNumber);

		JMenuItem stageButton1 = new JMenuItem("Single-player");
		stageButton1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_0
				+ stageNumber, ActionEvent.ALT_MASK));
		stageButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				newGame(stageNumber, 1);
			}
		});
		nthStageMenu.add(stageButton1);

		JMenuItem stageButton2 = new JMenuItem("Multiplayer");
		stageButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				newGame(stageNumber, 2);
			}
		});
		nthStageMenu.add(stageButton2);

		menu.add(nthStageMenu);
	}

	public void setBoundaries() {
		GLine line1 = new GLine(mapDimension, 0, mapDimension, mapDimension);
		line1.setColor(Color.WHITE);
		add(line1);
		GLine line2 = new GLine(0, mapDimension, mapDimension, mapDimension);
		line2.setColor(Color.WHITE);
		add(line2);
	}

	public void newGame(int stageNumber, int numberOfPlayers) {
		if (STATE == GAME_STATE) {
			reset();
		}
		try {
			this.numberOfPlayers = numberOfPlayers;
			this.stageNumber = stageNumber;
			stage = new Stage(stageNumber);
			battlefield = new Map(stage, this, numberOfPlayers);
			initializeSideMenu();
			STATE = GAME_STATE;
			new LevelStartSound(Map.getGraphicsProgram());
		} catch (Exception e) {
			System.out.println("Couldn't start the game!");
			System.out.println();
			System.out.println();
			e.printStackTrace();
		}
	}

	public void gameOver() {
		GLabel label = new GLabel("Game Over!");
		label.setColor(Color.WHITE);
		label.setFont("Arial-64");
		label.setLocation((Map.getMapWidth() - label.getWidth()) / 2,
				(Map.getMapHeight() - label.getHeight()) / 2);
		add(label);
		for (Tank tank : battlefield.getTanks()) {
			tank.setThreadIsRunning(false);
		}
		battlefield.getEnemySpawnTimer().stop();
		GImage gameOver = new GImage("Map_Resources/gameover.png");
		gameOver.setLocation(
				(Map.getMapWidth() - gameOver.getWidth()) / 2,
				label.getY() + 10);
		add(gameOver);
		pause(3000);
	}

	public void gameWon() {
		GLabel label = new GLabel("You win!");
		label.setColor(Color.WHITE);
		label.setFont("Arial-64");
		label.setLocation((Map.getMapWidth() - label.getWidth()) / 2,
				(Map.getMapHeight() - label.getHeight()) / 2);
		add(label);
		GImage gameWin = new GImage("Map_Resources/gamewin.jpg");
		gameWin.setLocation((Map.getMapWidth() - gameWin.getWidth()) / 2,
				label.getY() + 10);
		add(gameWin);
		pause(3000);
		if (stageNumber != 4) {
			newGame(stageNumber + 1, numberOfPlayers);
		}
	}
	
	public void reset() {
		battlefield.stopEnemySpawnTimer();
		if (STATE != MENU_STATE) {
			ArrayList<Tank> tanks = battlefield.getTanks();
			for (int i = 0; i < tanks.size(); i++) {
				int originalArraySize = tanks.size();
				battlefield.removeTank(tanks.get(i));
				if (tanks.size() < originalArraySize) {
					i--;
				}
			}
		}
		STATE = MENU_STATE;
		this.removeAll();
		repaint();
		battlefield = null;
		stage = null;
		setBoundaries();
		setBottomText();
	}

	public static void setState(int state) {
		Main.STATE = state;
	}

	public static int getState() {
		return STATE;
	}

	public void windowClosing(WindowEvent we) {
		System.exit(0);
	}
}
