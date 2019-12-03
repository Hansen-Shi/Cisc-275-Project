package Game_Pkg;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;

public class Controller extends Application {

	private Model model;
	private MapView viewmap;
	private RunOffGameView runOffGameView;
	private RunOffGameModel runOffGameModel;
	private AnimalPhotoView animalPhotoView;
	private AnimalPhotoModel animalPhotoModel;
	private NetGameView netGameView;
	private NetGameModel netModel;
	private Level level = Level.MAP;
	private static Stage theStage;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage theStage) {
		viewmap = new MapView();
		
		model = new Model(viewmap.getWidth(), viewmap.getHeight(), viewmap.getImageWidth(), viewmap.getImageHeight());
		runOffGameView = new RunOffGameView();
		runOffGameModel = new RunOffGameModel(1080,720, runOffGameView.blobs, runOffGameView.bushs);
		animalPhotoView = new AnimalPhotoView();
		animalPhotoModel = new AnimalPhotoModel(1080, 720);
		netGameView = new NetGameView();
		netModel = new NetGameModel(1080, 720, 300, 300, netGameView.collectables, netGameView.enemies, netGameView.trashList, netGameView.net,
				netGameView.score);

		new AnimationTimer() {
			public void handle(long currentNanoTime) {
				switch (level) {

				case MAP:
					theStage.setScene(MapView.getScene());
					theStage.setMaximized(true);
					level = MapView.getLevel();
					break;

				case LEVELONE:
					theStage.setScene(RunOffGameView.getScene());
					
					runOffGameModel.updateLocation(runOffGameView.getPlayerX(), runOffGameView.getPlayerY());
					runOffGameView.update(runOffGameView.getPlayerX(), runOffGameView.getPlayerY(), runOffGameModel.getScore(), runOffGameModel.blobs, runOffGameModel.bushs);
					if(runOffGameModel.getScore() >= 20 ^ runOffGameModel.getScore() <= -10) {
						level = Level.MAP;
					}
					break;

				case LEVELTWO:
					theStage.setScene(AnimalPhotoView.getScene());
					theStage.setMaximized(true);
					level = AnimalPhotoView.getLevel();
					animalPhotoModel.updateLocation(animalPhotoView.getX(), animalPhotoView.getY());
					animalPhotoView.update(animalPhotoView.getX(), animalPhotoView.getY());
					break;

				case LEVELTHREE:
					theStage.setScene(NetGameView.getScene());
					theStage.setMaximized(true);
					netGameView.update(netModel.getX(), netModel.getY(), netModel.collectables, netModel.enemies, netModel.trashes,
							netModel.score);
					netModel.updateModeAndDirection(netGameView.net.getX(), netGameView.getY(), false);
					netModel.updateLocationandDirection();
					level = netGameView.getLevel();
					break;
				}
				// increment the x and y coordinates, alter direction if necessary
				model.updateModeAndDirection(viewmap.getX(), viewmap.getY());
				model.updateLocationandDirection();
				// input the x coordinates, y coordinates, and direction picture
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

	public static Stage getTheStage() {
		return theStage;
	}
}