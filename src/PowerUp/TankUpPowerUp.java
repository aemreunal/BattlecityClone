package PowerUp;

import Map.Map;
import Sounds.TankUpSound;
import Tanks.Tank;

/*
 * Ahmet Emre Unal
 * Burak Tutanlar
 * Celebi Murat
 */

public class TankUpPowerUp extends PowerUp {
	private static String path = "Gift_Resources/Tank.png";

	public TankUpPowerUp(Map battlefield) {
		super(path, battlefield);
	}
	
	public void giveEffects(Tank tank) {
		effectsGiven = true;
		this.tank = tank;
		tank.incrementHp();
		new TankUpSound(Map.getGraphicsProgram());
	}
}
