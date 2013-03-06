package Sounds;

import javax.swing.JApplet;

/*
 * Ahmet Emre Unal
 * Burak Tutanlar
 * Celebi Murat
 */

public class TankUpSound extends Sound {

	public TankUpSound(JApplet applet) {
		super(applet.getAudioClip(applet.getDocumentBase(),
				"Sound_Resources/tankup.wav"));
		play();
	}
}
