package Game_Pkg;

import java.util.ArrayList;

public class NetGameModel {
	int canvasWidth;
	int canvasHeight;
	int imageWidth;
	int imageHeight;
	double xloc=0;
	double yloc=0;
	double xIncr=0;
	double yIncr=0;	
	int score;
	Net net;
	ArrayList<NormalFishCollect> collectables = new ArrayList<NormalFishCollect>();
	ArrayList<EnemyFishCollect> enemies = new ArrayList<EnemyFishCollect>();
	ArrayList<TrashCollect> trashes = new ArrayList<TrashCollect>();
	
	boolean pauseState = false;
	
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
	
	public void updateModeAndDirection(double x, double y, boolean paused) {
		xloc = x;
		yloc = y;
		pauseState = paused;
	}
		
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
	
	public double getX() {
		return xloc;
	}
	
	public double getY() {
		return yloc;
	}
}
