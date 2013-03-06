package Blocks;

import java.io.IOException;

import Map.Map;

/*
 * Ahmet Emre Unal
 * Burak Tutanlar
 * Celebi Murat
 */

public class WaterBlock extends Block {

	private static String originalPath = "Map_Resources/water.png";

	public WaterBlock(double x, double y, Map battlefield) throws IOException {
		super(x, y, originalPath, battlefield);
	}
}
