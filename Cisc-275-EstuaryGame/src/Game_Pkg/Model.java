package Game_Pkg;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Model{
	int canvasWidth;
	int canvasHeight;
	int imageWidth;
	int imageHeight;
	double xloc=0;
	double yloc=0;
	double xIncr=0;
	double yIncr=0;	
	
	
	boolean pauseState = false;
	
	public Model(int canvasWidth, int canvasHeight, int imageWidth, int imageHeight) {
		this.canvasWidth=canvasWidth;
		this.canvasHeight=canvasHeight;
		this.imageWidth=imageWidth;
		this.imageHeight=imageHeight;
	}
	
	public void updateModeAndDirection(double x, double y, boolean paused) {
		xloc = x;
		yloc = y;
		pauseState = paused;
	}
	
	public void updateLocationandDirection() {
		
	}
	
	public double getX() {
		return xloc;
	}
	
	public double getY() {
		return yloc;
	}
}