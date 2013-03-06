package Map;

/*
 * Ahmet Emre Unal
 * Burak Tutanlar
 * Celebi Murat
 */

public class MapCellCoordinate {
	private int x, y;
	public static final int blockSize = 48;

	public MapCellCoordinate(int x, int y) {
		this.x = ((x - 1) * blockSize) + 2;
		this.y = ((y - 1) * blockSize) + 2;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
