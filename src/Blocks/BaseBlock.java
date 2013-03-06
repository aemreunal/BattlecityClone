package Blocks;

import java.io.IOException;

import Map.Map;
import Sounds.BaseExplosionSound;
import Tanks.TankExplosionAnimation;

/*
 * Ahmet Emre Unal
 * Burak Tutanlar
 * Celebi Murat
 */

public class BaseBlock extends Block {
	private static String originalPath = "Map_Resources/base.png";

	public BaseBlock(double x, double y, Map battlefield) throws IOException {
		super(x, y, originalPath, battlefield);
	}

	public void getHit() {
		new TankExplosionAnimation(this);
		new BaseExplosionSound(Map.getGraphicsProgram());
		battlefield.removeBlock(this);
		Map.gameOver();
	}
}