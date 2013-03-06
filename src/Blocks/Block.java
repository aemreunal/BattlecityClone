package Blocks;

import Map.Map;
import Sounds.BrickDestroyedSound;
import acm.graphics.GImage;

/*
 * Ahmet Emre Unal
 * Burak Tutanlar
 * Celebi Murat
 */

public class Block extends GImage {
	protected Map battlefield;
	protected int hp = 99999999;

	public Block(double x, double y, String path, Map battlefield) {
		super(path, x, y);
		this.battlefield = battlefield;
		this.battlefield.addBlock(this);
	}

	public void getHit() {
		System.out.println("Trololololo!");
	}

	public void checkBlock(Block block) {
		if (block instanceof BrickBlock) {
			BrickBlock brick = (BrickBlock) block;
			if (hp > 0) {
				brick.setImage("Map_Resources/brick" + hp + ".png");
				Map.repaintMap();
			} else {
				battlefield.removeBlock(this);
				new BrickDestroyedSound(Map.getGraphicsProgram());
			}
		} else if (block instanceof SteelBlock) {
			SteelBlock steel = (SteelBlock) block;
			if (hp > 0) {
				steel.setImage("Map_Resources/steel" + hp + ".png");
			} else {
				battlefield.removeBlock(this);
				new BrickDestroyedSound(Map.getGraphicsProgram());
			}
		}
	}

}
