package PowerUp;

import Map.Map;
import Sounds.PowerUpTakenSound;
import Tanks.TankHelmetAnimation;
import Tanks.Tank;

/*
 * Ahmet Emre Unal
 * Burak Tutanlar
 * Celebi Murat
 */

public class HelmetPowerUp extends PowerUp {
	private static String path = "Gift_Resources/Helmet.png";
	private TankHelmetAnimation animation;

	public HelmetPowerUp(Map field) {
		super(path, field);
	}

	public void giveEffects(Tank tank) {
		effectsGiven = true;
		this.tank = tank;
		tank.setDestroyability(false);
		timer.start();
		new PowerUpTakenSound(Map.getGraphicsProgram());
		animation = new TankHelmetAnimation(tank);
	}

	public void removeEffects(Tank tank) {
		if (effectsGiven) {
			tank.setDestroyability(true);
			timer.stop();
			animation.stop();
		}
	}
}
