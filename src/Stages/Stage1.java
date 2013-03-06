package Stages;

import java.util.ArrayList;

import Map.MapCellCoordinate;

/*
 * Ahmet Emre Unal
 * Burak Tutanlar
 * Celebi Murat
 */

public class Stage1 {
	private int basicTanks = 20;
	private int fastTanks = 0;
	private int armorTanks = 0;
	private int powerTanks = 0;
	private ArrayList<Character> tanks = new ArrayList<Character>();
	private MapCellCoordinate spawnPoint0;
	private MapCellCoordinate spawnPoint1;
	private char[][] stage1 = { 
			{ 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n' },
			{ 'n', 'b', 'n', 'b', 'n', 'b', 'n', 'b', 'n', 'b', 'n', 'b', 'n', 'b', 'n' },
			{ 'n', 'b', 'n', 'b', 'n', 'b', 'n', 'b', 'n', 'b', 'n', 'b', 'n', 'b', 'n' },
			{ 'n', 'b', 'n', 'b', 'n', 'b', 's', 'b', 's', 'b', 'n', 'b', 'n', 'b', 'n' },
			{ 'n', 'b', 'n', 'b', 'n', 'b', 'n', 'b', 'n', 'b', 'n', 'b', 'n', 'b', 'n' },
			{ 'n', 'b', 'n', 'b', 'n', 'b', 'n', 'b', 'n', 'b', 'n', 'b', 'n', 'b', 'n' },
			{ 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n' },
			{ 's', 'n', 'b', 'b', 'n', 'b', 'b', 'n', 'b', 'b', 'n', 'b', 'b', 'n', 's' },
			{ 'n', 'n', 'b', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'b', 'n', 'n' },
			{ 'n', 'n', 'n', 'n', 'n', 'n', 'b', 'b', 'b', 'n', 'n', 'n', 'n', 'n', 'n' },
			{ 'b', 'n', 'b', 'n', 'b', 'n', 'b', 'n', 'b', 'n', 'b', 'n', 'b', 'n', 'b' },
			{ 'b', 'n', 'b', 'n', 'b', 'n', 'n', 'n', 'n', 'n', 'b', 'n', 'b', 'n', 'b' },
			{ 'b', 'n', 'b', 'n', 'b', 'n', 'b', 'b', 'b', 'n', 'b', 'n', 'b', 'n', 'b' },
			{ 'b', 'n', 'b', 'n', 'b', 'n', 'b', 'b', 'b', 'n', 'b', 'n', 'b', 'n', 'b' },
			{ 'n', 'n', 'n', 'n', 'n', 'n', 'b', 'k', 'b', 'n', 'n', 'n', 'n', 'n', 'n' }};

	public Stage1() {
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
		spawnPoint1 = new MapCellCoordinate(13, 3);
	}

	public char[][] getStage() {
		return stage1;
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
