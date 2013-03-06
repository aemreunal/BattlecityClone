package PowerUp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Timer;

import Blocks.Block;
import Main.Main;
import Map.Map;
import Sounds.PowerUpAppearedSound;
import Tanks.Tank;
import acm.graphics.GImage;

/*
 * Ahmet Emre Unal
 * Burak Tutanlar
 * Celebi Murat
 */

public abstract class PowerUp extends GImage {
	protected Random rand = new Random();
	
	protected Map battlefield;
	protected char[][] map;
	protected Block[][] blocks;
	protected static int blockSize = Main.blockSize;
	protected ArrayList<Tank> tanks;
	protected Timer timer;
	protected boolean effectsGiven;
	
	protected Tank tank;
	protected String path;

	public PowerUp(String path, Map battlefield) {
		super(path, 1000, 1000);
		this.path = path;
		this.battlefield = battlefield;
		this.map = battlefield.getMap();
		this.tanks = battlefield.getTanks();
		this.blocks = battlefield.getBlocks();
		setGiftLocation();
		timer = new Timer(10000, new GiftEffectsRemover());
		Map.addObject(this);
	}

	public void setGiftLocation() {
		int i;
		int j;
		do {
			i = rand.nextInt(battlefield.getMapSize());
			j = rand.nextInt(battlefield.getMapSize());
		} while(map[i][j] != 'n');
		int yPos = blockSize * i + (blockSize - (int) this.getWidth()) / 2;
		int xPos = blockSize * j + (blockSize - (int) this.getHeight()) / 2;
		this.setLocation(xPos, yPos);
		new PowerUpAppearedSound(Map.getGraphicsProgram());
	}

	public abstract void giveEffects(Tank tank);
	
	public void removeEffects(Tank tank) {
		System.out.println("Removed effects :(");
	}

	public Tank getTank() {
		return tank;
	}
	
	class GiftEffectsRemover implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			removeEffects(tank);
		}		
	}
}
