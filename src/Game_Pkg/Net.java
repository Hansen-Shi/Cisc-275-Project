package Game_Pkg;

import javafx.scene.image.Image;

public class Net implements java.io.Serializable{
	double x;
	double y;
	Image netImg = new Image("Game_Sprites/Net.png");
	
	public Net(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	

}
