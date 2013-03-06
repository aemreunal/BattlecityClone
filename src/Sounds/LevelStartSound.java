package Sounds;

import javax.swing.JApplet;

/*
 * Ahmet Emre Unal
 * Burak Tutanlar
 * Celebi Murat
 */

public class LevelStartSound extends Sound {

	public LevelStartSound(JApplet applet) {
		super(applet.getAudioClip(applet.getDocumentBase(),
				"Sound_Resources/level_start.wav"));
		play();
	}

}
