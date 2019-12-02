package Game_Pkg;

public class RunOffGameModel {
	int canvasWidth;
	int canvasHeight;
	int imageWidth = 150;
	int imageHeight = 150;
	double xLoc = 0;
	double yLoc = 0;
	
	
	public RunOffGameModel(int canvasWidth, int canvasHeight) {
		this.canvasWidth = canvasWidth;
		this.canvasHeight = canvasHeight;
	}
	
	
	public void updateLocation(double x, double y) {
		xLoc = x;
		yLoc = y;
	}
	
	public double getX( ) {
		return xLoc;
	}
	public double getY() {
		return yLoc;
	}
}
