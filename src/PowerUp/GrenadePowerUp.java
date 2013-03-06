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

public class GrenadePowerUp extends PowerUp {
	private static String path = "Gift_Resources/Grenade.png";
	
	public GrenadePowerUp(Map battlefield) {
		super(path, battlefield);
	}
	
	public void giveEffects(Tank tank) {
		effectsGiven = true;
		this.tank = tank;
		for(int i = 0; i < tanks.size(); i++){
			int arraySize = tanks.size();
			tank = tanks.get(i);
			if(!(tank instanceof Player1Tank) && !(tank instanceof Player2Tank)) {
				battlefield.destroyTank(tank);
			}
			if(tanks.size() + 1 == arraySize) {
				i--;
			}
		}
		new PowerUpTakenSound(Map.getGraphicsProgram());
	}
}
