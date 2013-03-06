package Sounds;

import javax.swing.JApplet;

/*
 * Ahmet Emre Unal
 * Burak Tutanlar
 * Celebi Murat
 */

public class PowerUpTakenSound extends Sound {

	public PowerUpTakenSound(JApplet applet) {
		super(applet.getAudioClip(applet.getDocumentBase(),
				"Sound_Resources/gift_take.wav"));
		play();
	}

}
