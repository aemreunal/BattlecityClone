package Stages;

import java.util.ArrayList;

import Map.MapCellCoordinate;

/*
 * Ahmet Emre Unal
 * Burak Tutanlar
 * Celebi Murat
 */

public class Stage {
	private char[][] map;
	private ArrayList<Character> stageTanks;
	private MapCellCoordinate spawnPoint0;
	private MapCellCoordinate spawnPoint1;
	
	public Stage(int n) {
		switch (n) {
		case 1:
			Stage1 stage1 = new Stage1();
			map = stage1.getStage();
			stageTanks = stage1.getTanks();
			spawnPoint0 = stage1.getSpawnPoint(0);
			spawnPoint1 = stage1.getSpawnPoint(1);
			break;
		case 2:
			Stage2 stage2 = new Stage2();
			map = stage2.getStage();
			stageTanks = stage2.getTanks();
			spawnPoint0 = stage2.getSpawnPoint(0);
			spawnPoint1 = stage2.getSpawnPoint(1);
			break;
		case 3:
			Stage3 stage3 = new Stage3();
			map = stage3.getStage();
			stageTanks = stage3.getTanks();
			spawnPoint0 = stage3.getSpawnPoint(0);
			spawnPoint1 = stage3.getSpawnPoint(1);
			break;
		case 4:
			Stage4 stage4 = new Stage4();
			map = stage4.getStage();
			stageTanks = stage4.getTanks();
			spawnPoint0 = stage4.getSpawnPoint(0);
			spawnPoint1 = stage4.getSpawnPoint(1);
			break;
		case 5:
			Stage5 stage5 = new Stage5();
			map = stage5.getStage();
			stageTanks = stage5.getTanks();
			spawnPoint0 = stage5.getSpawnPoint(0);
			spawnPoint1 = stage5.getSpawnPoint(1);
			break;
		}

	}

	public char[][] getMap() {
		return map;
	}
	
	public ArrayList<Character> getTanks() {
		return stageTanks;
	}
	
	public MapCellCoordinate getSpawnPoint(int n) {
		if(n == 0) {
			return spawnPoint0;
		} else {
			return spawnPoint1;
		}
	}
}
