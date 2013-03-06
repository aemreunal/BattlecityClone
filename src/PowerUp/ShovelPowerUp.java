package PowerUp;

import java.io.IOException;

import Blocks.BrickBlock;
import Blocks.SteelBlock;
import Map.Map;
import Sounds.PowerUpTakenSound;
import Tanks.Tank;

/*
 * Ahmet Emre Unal
 * Burak Tutanlar
 * Celebi Murat
 */

public class ShovelPowerUp extends PowerUp {
	private static String path = "Gift_Resources/Shovel.png";

	public ShovelPowerUp(Map battlefield) {
		super(path, battlefield);
	}

	public void giveEffects(Tank tank) {
		effectsGiven = true;
		this.tank = tank;
		// base -> 14 down, 7 right
		try {
			brickToSteel(14, 6);
			brickToSteel(14, 8);
			brickToSteel(13, 6);
			brickToSteel(13, 7);
			brickToSteel(13, 8);
		} catch (IOException e) {
			System.out.println("Couldn't create steels for helmet power-up!");
			e.printStackTrace();
		}
		timer.start();
		new PowerUpTakenSound(Map.getGraphicsProgram());
	}

	public void removeEffects(Tank tank) {
		if (effectsGiven) {
			try {
				steelToBrick(14, 6);
				steelToBrick(14, 8);
				steelToBrick(13, 6);
				steelToBrick(13, 7);
				steelToBrick(13, 8);
			} catch (IOException e) {
				System.out.println("Couldn't create bricks for helmet power-up!");
				e.printStackTrace();
			}
			timer.stop();
		}	
	}

	public void brickToSteel(int x, int y) throws IOException {
		if (map[x][y] != 'n') {
			battlefield.removeBlock(blocks[x][y]);
		}
		blocks[x][y] = new SteelBlock(y * blockSize, x * blockSize, battlefield);
		map[x][y] = 's';
	}

	public void steelToBrick(int x, int y) throws IOException {
		if (map[x][y] != 'n') {
			battlefield.removeBlock(blocks[x][y]);
		}
		blocks[x][y] = new BrickBlock(y * blockSize, x * blockSize, battlefield);
		map[x][y] = 'b';
	}
}
