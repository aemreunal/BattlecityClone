package Stages;

import java.util.ArrayList;

import Map.MapCellCoordinate;

/*
 * Ahmet Emre Unal
 * Burak Tutanlar
 * Celebi Murat
 */

public class Stage5 {
	private int basicTanks = 8;
	private int fastTanks = 7;
	private int armorTanks = 3;
	private int powerTanks = 2;
	private ArrayList<Character> tanks = new ArrayList<Character>();
	private MapCellCoordinate spawnPoint0;
	private MapCellCoordinate spawnPoint1;
	private char[][] stage5 = { 
			{'n', 'n', 'n', 'n', 'n', 'n', 's', 'n', 'b', 'n', 'n', 'n', 'n', 'n', 'n'} ,
			{'n', 'n', 'w', 'w', 'w', 'w', 's', 'n', 'b', 'n', 'n', 'n', 'n', 'n', 'n'} ,
			{'n', 'b', 'n', 'n', 'n', 'n', 's', 'n', 'b', 's', 'n', 'b', 't', 't', 't'} ,
			{'n', 'b', 'n', 'n', 'n', 'n', 'b', 'n', 'b', 'b', 'b', 'b', 't', 'b', 'n'} ,
			{'n', 'b', 'n', 't', 't', 'b', 'b', 'w', 'n', 'n', 'n', 'b', 't', 'b', 'n'} ,
			{'b', 'b', 'n', 'n', 'n', 'n', 'n', 'n', 's', 's', 'b', 'b', 't', 'b', 'n'} ,
			{'b', 'b', 'n', 'n', 'b', 't', 'b', 't', 'b', 'n', 'n', 'b', 't', 'b', 'n'} ,
			{'n', 'n', 'n', 'b', 'b', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'w', 'w'} ,
			{'n', 'n', 'b', 'b', 'b', 'n', 'b', 'n', 'b', 'n', 'n', 'w', 't', 't', 't'} ,
			{'b', 'w', 'w', 'w', 'b', 'n', 'b', 'b', 'b', 'n', 'n', 'w', 'b', 'b', 't'} ,
			{'t', 's', 'b', 'n', 'b', 'n', 'b', 'b', 'b', 'n', 'n', 'w', 'b', 'b', 't'} ,
			{'t', 's', 'b', 'n', 'n', 'n', 'b', 'b', 'b', 'n', 'n', 's', 'b', 'b', 't'} ,
			{'t', 'b', 'b', 'b', 'b', 'n', 'n', 'n', 'n', 'n', 'n', 's', 'b', 'b', 't'} ,
			{'t', 'b', 'b', 'b', 'b', 'n', 'b', 'b', 'b', 'n', 'n', 's', 'b', 'b', 't'} ,
			{'t', 'b', 'b', 'b', 'b', 'n', 'b', 'k', 'b', 'n', 'n', 'b', 't', 't', 'n'}};

	public Stage5() {
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
		spawnPoint0 = new MapCellCoordinate(3, 3);
		spawnPoint1 = new MapCellCoordinate(13, 2);
	}

	public char[][] getStage() {
		return stage5;
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
