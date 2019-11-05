package bassPkg;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Model{
	int canvasWidth;
	int canvasHeight;
	int imageWidth;
	int imageHeight;
	int xloc=0;
	int yloc=0;
	double xIncr=8;
	double yIncr=2;	
	Direction rection = Direction.EAST;
	BassMode mood = BassMode.DEFAULT;
	boolean pauseState = false;
	
	public Model(int canvasWidth, int canvasHeight, int imageWidth, int imageHeight) {
		this.canvasWidth=canvasWidth;
		this.canvasHeight=canvasHeight;
		this.imageWidth=imageWidth;
		this.imageHeight=imageHeight;
	}
	
	public void updateModeAndDirection(Direction direction, BassMode bassMode, boolean paused) {
		rection = direction;
		mood = bassMode;
		pauseState = paused;
	}
	
	public void updateLocationandDirection() {
		if (mood == BassMode.DEFAULT) {
			xIncr=8;
			yIncr=2;
		}
		if (mood == BassMode.CONFUSE) {
			xIncr=4;
			yIncr=1;
		}
		if (mood == BassMode.ATTAC) {
			xIncr=16;
			yIncr=4;
		}
		if (rection == Direction.NORTH) {
			xIncr=0;
			yIncr=-yIncr;
		}
		if (rection == Direction.NORTHEAST) {
			yIncr=-yIncr;
		}
		if (rection == Direction.EAST) {
			yIncr=0;
		}
		if (rection == Direction.SOUTH) {
			xIncr=0;
		}
		if (rection == Direction.SOUTHWEST) {
			xIncr=-xIncr;
		}
		if (rection == Direction.WEST) {
			xIncr=-xIncr;
			yIncr=0;
		}
		if (rection == Direction.NORTHWEST) {
			xIncr=-xIncr;
			yIncr=-yIncr;
		}
		if (pauseState == true) {
			xIncr = 0;
			yIncr = 0;
			rection = Direction.EAST;
		}
		xloc += xIncr;
		yloc += yIncr;
		getDirection();
	}
	
	public int getX() {
		return xloc;
	}
	
	public int getY() {
		return yloc;
	}
	
	public Direction getDirection() {
		return rection;
	}
}