package Stages;

import java.util.ArrayList;

import Map.MapCellCoordinate;

/*
 * Ahmet Emre Unal
 * Burak Tutanlar
 * Celebi Murat
 */

public class Stage3 {
	private int basicTanks = 14;
	private int fastTanks = 3;
	private int armorTanks = 2;
	private int powerTanks = 1;
	private ArrayList<Character> tanks = new ArrayList<Character>();
	private MapCellCoordinate spawnPoint0;
	private MapCellCoordinate spawnPoint1;
	private char[][] stage3 = {
			{ 'n', 'n', 'n', 'n', 'n', 'n', 'n', 't', 'n', 'n', 'n', 'n', 'n', 'n', 'n' },
			{ 'b', 'b', 'b', 'n', 'n', 'b', 'b', 't', 'b', 'b', 'n', 'n', 'b', 'b', 'b' },
			{ 'n', 'n', 'n', 'n', 'n', 'n', 't', 't', 't', 'n', 'n', 'n', 'n', 'n', 'n' },
			{ 'n', 'b', 'n', 'b', 'b', 'b', 'w', 'w', 'w', 'b', 'b', 'b', 'n', 'b', 'n' },
			{ 'n', 'b', 'n', 'b', 'n', 'b', 'n', 'b', 'n', 'b', 'n', 'b', 'n', 'b', 'n' },
			{ 'n', 'b', 'n', 'b', 'n', 'b', 'n', 'b', 'n', 'b', 'n', 'b', 'n', 'b', 'n' },
			{ 'n', 'n', 'n', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'n', 'n', 'n' },
			{ 's', 'n', 'b', 'b', 'n', 'b', 'b', 'n', 'b', 'b', 'n', 'b', 'b', 'n', 's' },
			{ 'n', 'n', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'n', 'n' },
			{ 'n', 'n', 'n', 'n', 'n', 'n', 'b', 'b', 'b', 'n', 'n', 'n', 'n', 'n', 'n' },
			{ 'b', 't', 'b', 'n', 'b', 'n', 'b', 'n', 'b', 'n', 'b', 'n', 'b', 't', 'b' },
			{ 'b', 't', 'b', 'n', 'b', 'n', 'n', 'n', 'n', 'n', 'b', 'n', 'b', 't', 'b' },
			{ 'b', 't', 'b', 'n', 'b', 'n', 'b', 'b', 'b', 'n', 'b', 'n', 'b', 't', 'b' },
			{ 'b', 't', 'b', 'n', 'b', 'n', 'b', 'b', 'b', 'n', 'b', 'n', 'b', 't', 'b' },
			{ 'n', 't', 't', 'n', 'n', 'n', 'b', 'k', 'b', 'n', 'n', 'n', 't', 't', 'n' } };

	public Stage3() {
		for(int i = 0; i < basicTanks; i++) {
			tanks.add('b');
		}
		
		for(int i = 0; i < fastTanks; i++) {
			tanks.add('f');
		}
		
		for(int i = 0; i < powerTanks; i++) {
			tanks.add('p');
		}
		
		for(int i = 0; i < armorTanks; i++) {
			tanks.add('a');
		}
		
		spawnPoint0 = new MapCellCoordinate(2, 3);
		spawnPoint1 = new MapCellCoordinate(13, 4);
	}
	
	public char[][] getStage() {
		return stage3;
	}
	
	public ArrayList<Character> getTanks() {
		return tanks;
	}
	
	public MapCellCoordinate getSpawnPoint(int n) {
		if(n == 0) {
			return spawnPoint0;
		} else {
			return spawnPoint1;
		}
	}
}
