package Sounds;

import javax.swing.JApplet;

/*
 * Ahmet Emre Unal
 * Burak Tutanlar
 * Celebi Murat
 */

public class BaseExplosionSound extends Sound {

	public BaseExplosionSound(JApplet applet) {
		super(applet.getAudioClip(applet.getDocumentBase(),
				"Sound_Resources/explosion_base.wav"));
		play();
	}
}