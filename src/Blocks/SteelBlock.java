package Blocks;

import java.io.IOException;

import Map.Map;

/*
 * Ahmet Emre Unal
 * Burak Tutanlar
 * Celebi Murat
 */

public class SteelBlock extends Block {
	private static String originalPath = "Map_Resources/steel2.png";

	public SteelBlock(double x, double y, Map battlefield) throws IOException {
		super(x, y, originalPath, battlefield);
		hp = 2;
	}

	public void getHit() {
		hp--;
		checkBlock(this);
	}
}
