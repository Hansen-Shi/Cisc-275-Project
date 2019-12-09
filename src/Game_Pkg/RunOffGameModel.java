package Game_Pkg;

import java.io.Serializable;
import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public class RunOffGameModel implements Serializable {
	int playerX = 0;
	int playerY = 0;
	int score;
	ArrayList<OilBlob> blobs = new ArrayList<OilBlob>();
	ArrayList<Rectangle> bushs = new ArrayList<Rectangle>();
	
	/**
	 * constructor for the game model
	 * @param blobs the arraylist of blobs needed
	 * @param bushs the arraylist of bushes needed
	 */
	public RunOffGameModel(ArrayList<OilBlob> blobs, ArrayList<Rectangle> bushs) {
		this.blobs = blobs;
		this.bushs = bushs;
	}
	
	/**
	 * updates and redraws the canvas. Also handles if collision between items happen
	 * @param x the x location of the player's cursor
	 * @param y the y location of the player's cursor
	 */
	public void updateLocation(int x, int y) {
		playerX = x;
		playerY = y;

		for (OilBlob o : blobs) {
			o.updateBlob();
		}

		for (Rectangle b : bushs) {
			for (OilBlob o : blobs) {
				Boolean checkX = o.getX() + 50 >= b.getX() && o.getX() <= (b.getX() + 100);
				Boolean checkY = o.getY() + 100 >=  b.getY() && o.getY() <= (b.getY() + 200);
				if (checkX && checkY) {
					blobs.remove(o);
					blobs.add(new OilBlob(100, 100));
					score++;
				} else if (o.getX() < 0) {
					blobs.remove(o);
					blobs.add(new OilBlob(100, 100));
					score--;
				} else {
				
				}
			}
		}
	}

	public double getX() {
		return playerX;
	}

	public double getY() {
		return playerY;
	}

	public int getScore() {
		return score;
	}

}
