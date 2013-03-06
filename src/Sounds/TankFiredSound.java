package Sounds;

import javax.swing.JApplet;

/*
 * Ahmet Emre Unal
 * Burak Tutanlar
 * Celebi Murat
 */

public class TankFiredSound extends Sound {
	
	public TankFiredSound (JApplet applet) {
		super(applet.getAudioClip(applet.getDocumentBase(),"Sound_Resources/tank_fire.wav"));
		play();
	}
}
