package Sounds;

import javax.swing.JApplet;

/*
 * Ahmet Emre Unal
 * Burak Tutanlar
 * Celebi Murat
 */

public class BrickDestroyedSound extends Sound{
	
	public BrickDestroyedSound(JApplet applet) {
		super(applet.getAudioClip(applet.getDocumentBase(),"Sound_Resources/brick_destroy.wav"));
		play();
	}

}
