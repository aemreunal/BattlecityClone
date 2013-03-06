package Sounds;

import java.applet.AudioClip;

/*
 * Ahmet Emre Unal
 * Burak Tutanlar
 * Celebi Murat
 */

public class Sound {
	private AudioClip clip;
	
	public Sound(AudioClip clip){
		this.clip = clip;
	}	
	
	public void play(){
		clip.play();
	}
	
	public void loop(){
		clip.loop();
	}
	
	public void stop() {
		clip.stop();
	}
}