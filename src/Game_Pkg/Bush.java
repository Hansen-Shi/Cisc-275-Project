package Game_Pkg;

public class Bush {
	int hp;
	int height;
	int width;
	int x;
	int y;
	
	public Bush(int height, int width) {
		hp = 3;
		this.height = height;
		this.width = width;
		this.x = (int) Math.random() * 540;
		this.y = (int) Math.random() * 720;
	}
	
	public void updateLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	
}
