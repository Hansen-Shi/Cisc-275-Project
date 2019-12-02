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
	ArrayList<Collectable> collectables = new ArrayList<Collectable>();
	
	
	boolean pauseState = false;
	
	public NetGameModel(int canvasWidth, int canvasHeight, int imageWidth, int imageHeight, ArrayList<Collectable> imgs, Net net, int score) {
		this.canvasWidth=canvasWidth;
		this.canvasHeight=canvasHeight;
		this.imageWidth=imageWidth;
		this.imageHeight=imageHeight;
		this.collectables = imgs;
		this.net = net;
		this.score = score;
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
				collectables.add(new Collectable());
				score++;
			}
			else if(collectables.get(i).x < 0) {
				collectables.remove(i);
				collectables.add(new Collectable());
				score--;
			}
			else {
				collectables.get(i).x -= collectables.get(i).vX;
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
