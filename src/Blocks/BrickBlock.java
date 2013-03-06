package Blocks;

import java.io.IOException;

import Map.Map;

/*
 * Ahmet Emre Unal
 * Burak Tutanlar
 * Celebi Murat
 */

public class BrickBlock extends Block {
	private static String originalPath = "Map_Resources/brick3.png";

	public BrickBlock(double x, double y, Map battlefield) throws IOException {
		super(x, y, originalPath, battlefield);
		hp = 3;
	}

	public void getHit() {
		hp--;
		checkBlock(this);
	}

	public void getOneShot() {
		hp = -5;
		checkBlock(this);
	}
}
