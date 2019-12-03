package Game_Pkg;

import java.util.Random;

public class OilBlob {
	// width and length are representitve of how big the oilBlob is, speed is how fast if moves
	double width;
	double height;
	int x;
	int y;
	int speed;
	
	// constructor(s)
	public OilBlob(double width, double height) {
		this.width = width;
		this.height = height;
		this.x = (int) RunOffGameView.getScene().getWidth();
		this.y = (int) (Math.random() * (720 - height));
		this.speed = (int) (Math.random() * 20) % 10;
	}
	
	public void updateBlob() {
		this.x -= speed;
	}
	
	public double getWidth() {
		return width;
	}
	
	public double getHeight() {
		return height;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getSpeed() {
		return speed;
	}

}
