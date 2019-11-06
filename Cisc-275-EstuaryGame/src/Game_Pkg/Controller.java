package Game_Pkg;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;

public class Controller extends Application{
	//data fields hold Model and viewmap
	private Model model;
	private MapView viewmap;
	
	public static void main(String[] args) {
		launch(args);		
	}
	
	@Override
	public void start(Stage theStage) {
		viewmap = new MapView(theStage);
		model = new Model(viewmap.getWidth(), viewmap.getHeight(), viewmap.getImageWidth(), viewmap.getImageHeight());
		new AnimationTimer() {
				public void handle(long currentNanoTime) {
				//increment the x and y coordinates, alter direction if necessary 
				model.updateModeAndDirection(viewmap.getX(),viewmap.getY(), viewmap.getPauseState());
				model.updateLocationandDirection();
				//input the x coordinates, y coordinates, and direction picture
				viewmap.update(model.getX(), model.getY());
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();
		theStage.show();
	}
}