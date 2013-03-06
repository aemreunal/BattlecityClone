package PowerUp;

import Map.Map;
import Sounds.PowerUpTakenSound;
import Tanks.Tank;

/*
 * Ahmet Emre Unal
 * Burak Tutanlar
 * Celebi Murat
 */

public class StarPowerUp extends PowerUp {
	private static String path = "Gift_Resources/Star.png";

	public StarPowerUp(Map battlefield) {
		super(path, battlefield);
	}

	public void giveEffects(Tank tank) {
		effectsGiven = true;
		this.tank = tank;
		tank.setOneShot(true);
		timer.start();
		new PowerUpTakenSound(Map.getGraphicsProgram());
	}

	public void removeEffects(Tank tank) {
		if(effectsGiven) {
			tank.setOneShot(false);
			timer.stop();
		}
	}

}
