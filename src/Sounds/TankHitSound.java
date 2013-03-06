package Sounds;

import javax.swing.JApplet;

/*
 * Ahmet Emre Unal
 * Burak Tutanlar
 * Celebi Murat
 */

public class TankHitSound extends Sound {

	public TankHitSound(JApplet applet) {
		super(applet.getAudioClip(applet.getDocumentBase(),
				"Sound_Resources/hit.wav"));
		play();
	}
}
