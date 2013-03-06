package Stages;

import java.util.ArrayList;

import Map.MapCellCoordinate;

/*
 * Ahmet Emre Unal
 * Burak Tutanlar
 * Celebi Murat
 */

public class Stage4 {
	private int basicTanks = 10;
	private int fastTanks = 6;
	private int armorTanks = 2;
	private int powerTanks = 2;
	private ArrayList<Character> tanks = new ArrayList<Character>();
	private MapCellCoordinate spawnPoint0;
	private MapCellCoordinate spawnPoint1;
	private char[][] stage4 = { 
            {'n', 'n', 'b', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'b', 'n', 'n'} ,
			{'n', 'n', 'b', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'b', 'n', 'n'} ,
			{'n', 'w', 'w', 'w', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'w', 'w', 'w', 'n'} ,
			{'n', 'w', 'b', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'b', 'w', 'n'} ,
			{'b', 'b', 'n', 'b', 'n', 'n', 'n', 'b', 'n', 'n', 'n', 'b', 'n', 'b', 'b'} ,
			{'n', 'n', 'n', 'n', 'b', 'n', 'b', 'n', 'b', 'n', 'b', 'n', 'n', 'n', 'n'} ,
			{'n', 'n', 'n', 'n', 'n', 'b', 'n', 'n', 'n', 'b', 'n', 'n', 'n', 'n', 'n'} ,
			{'n', 'n', 'n', 'n', 'n', 'n', 'b', 's', 'b', 'n', 'n', 'n', 'n', 'n', 'n'} ,
			{'n', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'n'} ,
			{'n', 'b', 't', 't', 't', 'b', 't', 't', 't', 'b', 't', 't', 't', 'b', 'n'} ,
			{'n', 'b', 't', 't', 's', 'n', 'n', 'n', 'n', 'n', 's', 't', 't', 'b', 'n'} ,
			{'n', 'b', 't', 's', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 's', 't', 'b', 'n'} ,
			{'n', 'b', 's', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 's', 'b', 'n'} ,
			{'n', 'b', 'n', 'n', 'n', 'n', 'b', 'b', 'b', 'n', 'n', 'n', 'n', 'b', 'n'} ,
			{'b', 'n', 'n', 'n', 'n', 'n', 'b', 'k', 'b', 'n', 'n', 'n', 'n', 'n', 'b'}};

	public Stage4() {
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

		spawnPoint0 = new MapCellCoordinate(3, 5);
		spawnPoint1 = new MapCellCoordinate(13, 5);
	}

	public char[][] getStage() {
		return stage4;
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
