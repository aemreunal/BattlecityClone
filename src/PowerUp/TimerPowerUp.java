package PowerUp;

import Map.Map;
import Sounds.PowerUpTakenSound;
import Tanks.Player1Tank;
import Tanks.Player2Tank;
import Tanks.Tank;

/*
 * Ahmet Emre Unal
 * Burak Tutanlar
 * Celebi Murat
 */

public class TimerPowerUp extends PowerUp {
	private static String path = "Gift_Resources/Timer.png";

	public TimerPowerUp(Map battlefield) {
		super(path, battlefield);
	}

	public void giveEffects(Tank tank) {
		effectsGiven = true;
		this.tank = tank;
		for (int i = 0; i < tanks.size(); i++) {
			tank = tanks.get(i);
			if (!(tank instanceof Player1Tank) && !(tank instanceof Player2Tank)) {
				tank.setCanMove(false);
			}
		}
		timer.start();
		battlefield.getEnemySpawnTimer().stop();
		new PowerUpTakenSound(Map.getGraphicsProgram());
	}

	public void removeEffects(Tank tank) {
		if (effectsGiven) {
			for (int i = 0; i < tanks.size(); i++) {
				tank = tanks.get(i);
				if (!(tank instanceof Player1Tank) && !(tank instanceof Player2Tank)) {
					tank.setCanMove(true);
				}
			}
			timer.stop();
			battlefield.getEnemySpawnTimer().start();
		}
	}
}
