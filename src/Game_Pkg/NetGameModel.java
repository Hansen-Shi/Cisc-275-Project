package Game_Pkg;

import java.util.ArrayList;

public class NetGameModel implements java.io.Serializable{
	int canvasWidth; //width of the screen
	int canvasHeight; // height of the screen
	int imageWidth; // width of the net image
	int imageHeight; // height of the image
	double xloc=0; //x location of the mouse
	double yloc=0; // y location of the mouse
	int score; // score of the game
	Net net; // net object from the view
	ArrayList<NormalFishCollect> collectables = new ArrayList<NormalFishCollect>(); // arraylist of bass, the normal fish in the game, initialized in the view
	ArrayList<EnemyFishCollect> enemies = new ArrayList<EnemyFishCollect>(); //arraylist of enemy catfish, initialized in the view
	ArrayList<TrashCollect> trashes = new ArrayList<TrashCollect>(); //arraylist of trash objects, initialized in the view
	
	boolean pauseState = false;
	/**
	 * 
	 * @param canvasWidth width of the screen
	 * @param canvasHeight height of the screen
	 * @param imageWidth width of the net image
	 * @param imageHeight height of the image
	 * @param imgs arraylist of bass, the normal fish in the game, initialized in the view
	 * @param enemie arraylist of enemy catfish, initialized in the view
	 * @param trashes arraylist of trash objects, initialized in the view
	 * @param net net object from the view
	 * @param score score of the game
	 */
	public NetGameModel(int canvasWidth, int canvasHeight, int imageWidth, int imageHeight, ArrayList<NormalFishCollect> imgs, ArrayList<EnemyFishCollect> enemie, 
			ArrayList<TrashCollect> trashes,Net net, int score) {
		this.canvasWidth=canvasWidth;
		this.canvasHeight=canvasHeight;
		this.imageWidth=imageWidth;
		this.imageHeight=imageHeight;
		this.collectables = imgs;
		this.net = net;
		this.score = score;
		this.enemies = enemie;
		this.trashes = trashes;
		
	}
	/**
	 * updates net location from the view in the model
	 * @param x x location of the net
	 * @param y y location of the net
	 * @param paused paused state of the game
	 */
	public void updateModeAndDirection(double x, double y, boolean paused) {
		xloc = x;
		yloc = y;
		pauseState = paused;
	}
	/**
	 * checks the loation of all fish and trash objects against the net. If collision is detected, object is
	 * removed and a new one added at the start, score changing according to object type. if not collided,
	 * the object moves according to its velocity
	 */
	public void updateLocationandDirection() {
		for(int i = 0; i < collectables.size(); i++) {
			boolean xCheck = collectables.get(i).x >= net.getX() && collectables.get(i).x <= (net.getX() + 200);
			boolean yCheck = collectables.get(i).y >= net.getY() && collectables.get(i).y <= (net.getY() + 200);
			if(xCheck && yCheck) {
				collectables.remove(i);
				collectables.add(new NormalFishCollect());
				score -= 2;
			}
			else if(collectables.get(i).x < 0) {
				collectables.remove(i);
				collectables.add(new NormalFishCollect());
				score += 2;
			}
			else {
				collectables.get(i).x -= collectables.get(i).vX;
			}
			
		}
		for(int i = 0; i < enemies.size(); i++) {
			boolean xCheck = enemies.get(i).x >= net.getX() && enemies.get(i).x <= (net.getX() + 150);
			boolean yCheck = enemies.get(i).y >= net.getY() && enemies.get(i).y <= (net.getY() + 150);
			if(xCheck && yCheck) {
				enemies.remove(i);
				enemies.add(new EnemyFishCollect());
				score++;
			}
			else if(enemies.get(i).x < 0) {
				enemies.remove(i);
				enemies.add(new EnemyFishCollect());
				score--;
			}
			else {
				
				enemies.get(i).x -= enemies.get(i).vX;
			}
		}
		for(int i = 0; i < trashes.size(); i++) {
			boolean xCheck = trashes.get(i).x >= net.getX() && trashes.get(i).x <= (net.getX() + 150);
			boolean yCheck = trashes.get(i).y >= net.getY() && trashes.get(i).y <= (net.getY() + 150);
			if(xCheck && yCheck) {
				trashes.remove(i);
				trashes.add(new TrashCollect());
				score += 5;
			}
			else if(trashes.get(i).x < 0) {
				trashes.remove(i);
				trashes.add(new TrashCollect());
				score -= 5;
			}
			else {
				
				trashes.get(i).x -= trashes.get(i).vX;
			}
		
	}
			
	}
	
	/**
	 * returns x location of net
	 * @return xLoc
	 */
	public double getX() {
		return xloc;
	}
	/**
	 * returns y location of net
	 * @return yLoc
	 */
	public double getY() {
		return yloc;
	}
}
