package Stages;

import java.util.ArrayList;

import Map.MapCellCoordinate;

/*
 * Ahmet Emre Unal
 * Burak Tutanlar
 * Celebi Murat
 */

public class Stage2 {
	private int basicTanks = 17;
	private int fastTanks = 1;
	private int armorTanks = 1;
	private int powerTanks = 1;
	private ArrayList<Character> tanks = new ArrayList<Character>();
	private MapCellCoordinate spawnPoint0;
	private MapCellCoordinate spawnPoint1;
	private char[][] stage2 = {
			{ 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n' },
			{ 'n', 'b', 'n', 'n', 'n', 'b', 'n', 'b', 'n', 'b', 'n', 'n', 'n', 'b', 'n' },
			{ 'w', 'w', 'w', 'b', 'w', 'w', 't', 't', 't', 'w', 'w', 'b', 'w', 'w', 'w' },
			{ 'n', 'b', 'w', 'b', 'w', 'b', 's', 'b', 's', 'b', 'w', 'b', 'w', 'b', 'n' },
			{ 'n', 'b', 'n', 'b', 'w', 'b', 'n', 'b', 'n', 'b', 'w', 'b', 'n', 'b', 'n' },
			{ 'n', 'b', 'n', 'b', 'n', 'b', 'n', 'b', 'n', 'b', 'n', 'b', 'n', 'b', 'n' },
			{ 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n' },
			{ 's', 'n', 'b', 'b', 'n', 'b', 'b', 'n', 'b', 'b', 'n', 'b', 'b', 'n', 's' },
			{ 'n', 'n', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'n', 'n' },
			{ 'n', 'n', 'n', 'n', 'n', 'n', 'b', 'b', 'b', 'n', 'n', 'n', 'n', 'n', 'n' },
			{ 'b', 't', 'b', 'n', 'b', 'n', 'b', 'n', 'b', 'n', 'b', 'n', 'b', 't', 'b' },
			{ 'b', 't', 'b', 'n', 'b', 'n', 'n', 'n', 'n', 'n', 'b', 'n', 'b', 't', 'b' },
			{ 'b', 't', 'b', 'n', 'b', 'n', 'b', 'b', 'b', 'n', 'b', 'n', 'b', 't', 'b' },
			{ 'b', 't', 'b', 'n', 'b', 'n', 'b', 'b', 'b', 'n', 'b', 'n', 'b', 't', 'b' },
			{ 'n', 't', 't', 'n', 'n', 'n', 'b', 'k', 'b', 'n', 'n', 'n', 't', 't', 'n' } };

	public Stage2() {
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
		
		spawnPoint0 = new MapCellCoordinate(3, 2);
		spawnPoint1 = new MapCellCoordinate(13, 5);
	}
	
	public char[][] getStage() {
		return stage2;
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
