package Game_Pkg;

import javafx.application.Application;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.input.*;
import javafx.scene.layout.Region;
import javafx.event.*;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

// NOTE: will not be needed once lab is complete
import java.util.Random;

public class NetGameView {
	int picNum = -1; // index for which picture to use
	int picCount = 4; // number of pics in animation

	// value of the height and width of screen
	int canvasWidth = 1920;
	int canvasHeight = 1080;
	// value of the size of0he image.000013.

	static final int imgWidthOrig = 257;
	static final int imgHeightOrig = 257;

	int imgWidth = 300;
	int imgHeight = 300;

	GraphicsContext gc;
	Alert fishAlert = new Alert(AlertType.INFORMATION);
	
	Alert enemyAlert = new Alert(AlertType.INFORMATION);
	Alert trashAlert = new Alert(AlertType.INFORMATION);
	Alert endAlert = new Alert(AlertType.CONFIRMATION);
	Alert controlAlert = new Alert(AlertType.INFORMATION);
	boolean alert1 = true;
	boolean alert2 = true;
	boolean alert3 = true;
	boolean alert4 = true;
	Image background;

	ArrayList<NormalFishCollect> collectables = new ArrayList<NormalFishCollect>();
	ArrayList<EnemyFishCollect> enemies = new ArrayList<EnemyFishCollect>();
	ArrayList<TrashCollect> trashList = new ArrayList<TrashCollect>();
	// array of wide png images
	Image[] animationSequence;

	// Used to determine the direction to warp the bass image into

	int modeInd = -1;
	// Used to index the animationSequence outter array.

	boolean paused = false;
	double bassX;
	double bassY;

	// variables to determine the location of image
	double x = 0;
	Popup popup = new Popup();
	int score = 0;
	Text t;
	double y = 0;
	Net net;

	static Scene gameThree;

	static Level gameLevel = Level.LEVELTHREE;

	// View constructor initialize the starting position for the image
	// Called in controller
	public NetGameView() {
		net = new Net(0, 0);
		t = new Text("" + score);
		Font font = Font.font("Verdana", FontWeight.EXTRA_BOLD, 50);
		t.setFont(font);
		Button mapButton = new Button("Return");
		Button tutorial = new Button("Show Tutorial");
		Group root = new Group();
		gameThree = new Scene(root);
		fishAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
		enemyAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
		trashAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
		controlAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
		fishAlert.setGraphic(new ImageView(new Image("Game_Sprites/fish_bass_left.png")));
		enemyAlert.setGraphic(new ImageView(new Image("Game_Sprites/fish_catfish_left_0.png")));
		trashAlert.setGraphic(new ImageView(new Image("Game_Sprites/trashbag.png")));
		
		Popup popup = new Popup();
		popup.setX(canvasWidth / 2);
		popup.setY(canvasHeight / 2);
		Label label = new Label("TGood Job, you win!"); 
		popup.getContent().add(label);
		
		popup.hide();
		
		root.addEventFilter(MouseEvent.MOUSE_MOVED, e -> {
			// System.out.println(e.getSceneX() + " " + e.getSceneY());
			net.setX(e.getSceneX() - 140);
			net.setY(e.getSceneY() - 140);

		});

		Canvas canvas = new Canvas(canvasWidth, canvasHeight);
		root.getChildren().add(canvas);

		mapButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				gameLevel = Level.MAP;

			}
		});
		tutorial.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				controlAlert.showAndWait();
				alert4 = false;
				fishAlert.showAndWait();
				alert1 = false;
				enemyAlert.showAndWait();
				alert2 = false;
				trashAlert.showAndWait();
				alert3 = false;
				score = 0;
			}
		});
		controlAlert.initOwner(gameThree.getWindow());
		
		fishAlert.setContentText("Avoid removing Bass from their natural habitat through irresponsible fishing. Picking up these will lose you points.");
		enemyAlert.setContentText("Catfish are a known invasive species in the estuary, remove them by using the net! Picking up these will gain you points.");
		trashAlert.setContentText("Trash and other pollutants litter the esturary, do your part and help clean it up! Picking up these will gain you points.");
		controlAlert.setContentText("Remove fish and trash by moving the net over the item you want to pick up");
		
		root.getChildren().add(mapButton);
		root.getChildren().add(tutorial);
		tutorial.setTranslateX(50);
		tutorial.setTranslateY(50);
		mapButton.setTranslateX(50);
		mapButton.setTranslateY(80);
		mapButton.setVisible(true);

		gc = canvas.getGraphicsContext2D();

		for (int i = 0; i < 5; i++) {
			collectables.add(new NormalFishCollect());
		}
		for (int i = 0; i < 5; i++) {
			enemies.add(new EnemyFishCollect());
		}
		for (int i = 0; i < 5; i++) {
			trashList.add(new TrashCollect());
		}

		importImages();
		root.getChildren().add(t);
		t.setTranslateX(20);
		t.setTranslateY(40);

	}

	public double getX() {
		return bassX;
	}

	public double getY() {
		return bassY;
	}

	public boolean getPauseState() {
		return paused;
	}

	// Method used to import the images into the 2D image array
	private void importImages() {

		// Create array of the images. Each image pixel map contains
		// multiple images of the animate at different time steps

		// Eclipse will look for <path/to/project>/bin/<relative path specified>
		String img_file_base = "Game_Sprites/";
		String ext = ".png";

		// Load background
		background = createImage(img_file_base + "Underwater" + ext);
	}

	// Read image from file and return
	private Image createImage(String image_file) {
		Image img = new Image(image_file);
		return img;
	}

	// method used to repaint on the image and called in controller
	public void update(double e, double f, ArrayList<NormalFishCollect> imgs, ArrayList<EnemyFishCollect> enemie,
			ArrayList<TrashCollect> trashes, int score) {
		if(alert1 || alert2 || alert3 || alert4) {
			gc.clearRect(0, 0, canvasWidth, canvasHeight);

			// draw background
			gc.drawImage(background, 0, 0, canvasWidth, canvasHeight);

		}
		else if(score > 200) {
			t.setText("Good job, you won!");
			t.setY(canvasHeight / 2);
			t.setX(canvasWidth / 2);
			
			try {
				
				Thread.sleep(3000);
				gameLevel = Level.MAP;
				
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
		}
		else {
			x = e;
			y = f;
			int point = score;
			
			
			y = net.getY();
			Image pics = createImage("Game_Sprites/net.png");
			picNum = 0;
			t.setText("" + point);
			// Clear the canvas
			gc.clearRect(0, 0, canvasWidth, canvasHeight);

			// draw background
			gc.drawImage(background, 0, 0, canvasWidth, canvasHeight);

			// First, crop the wide png into image so we can rotate and flip it!!
			//
			// Define the Rectangle to crop by (x,y,widht,height)
			Rectangle2D croppedPortion = new Rectangle2D(imgWidthOrig * picNum, 0, imgWidthOrig, imgHeightOrig);
			// Define an ImageView with the wide png image 'pics'
			ImageView imageView = new ImageView(pics);
			imageView.setViewport(croppedPortion);
			imageView.setFitWidth(imgWidthOrig);
			imageView.setFitHeight(imgHeightOrig);
			imageView.setSmooth(true);
			// Crop!
			SnapshotParameters params = new SnapshotParameters();
			params.setFill(Color.TRANSPARENT);
			Image croppedImage = imageView.snapshot(params, null);
			updateFish(imgs);
			updateTrash(trashes);
			drawArray(collectables, gc);
			drawEnemies(enemies, gc);
			drawTrash(trashes, gc);

			// Now rotate and flip it based on direction, then draw to canvas
			transformAndDraw(gc, croppedImage, x, y);
		}
		

	}

	// getter methods to get the frame dimensions and image dimensions
	public int getWidth() {
		return canvasWidth;
	}

	public int getHeight() {
		return canvasHeight;
	}

	public int getImageWidth() {
		return imgWidth;
	}

	public int getImageHeight() {
		return imgHeight;
	}

	public static Scene getScene() {
		return gameThree;
	}

	// If the bass is facing to the WEST, we must flip it, then rotate accordingly
	// for NORTH/SOUTH
	// Then draw to gc
	private void transformAndDraw(GraphicsContext gc, Image image, double x, double y) {
		// clockwise rotation angle
		// Why clockwise? I don't know. It should probably be counter-clockwise
		double theta = 0.0;
		boolean isFlipped = false;

		// Setting x scale to -1 will flip it horizontally
		if (isFlipped) {
			ImageView iv = new ImageView(image);
			iv.setScaleX(-1.0);
			SnapshotParameters params = new SnapshotParameters();
			params.setFill(Color.TRANSPARENT);
			image = iv.snapshot(params, null);
		}

		// Rotate the CANVAS and NOT the Image, because rotating the image
		// will crop part of the bass's tail for certain angles

		gc.save(); // saves the current state on stack, including the current transform

		// set canvas rotation about the point (x+imageWidth/2, y+imageWidth/2) by angle
		// theta
		Rotate r = new Rotate(theta, x + imgWidth / 2, y + imgWidth / 2);
		// Transform gc by the rotation
		gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());

		// draw the subimage from the original png to animate the bass's motion
		// The arguments for drawImage are:
		//
		// drawImage(sourceImage, sX, sY, sWidth, sHeight, dX, dY, dWidth, dHeight)
		//
		// This means that a rectangle of size (sWidth, sHeight) will be CROPPED from
		// sourceImage
		// at location (sX, sY), RESIZED to (dWidth, dHeight), and drawn to the
		// parent of the GraphicsContext at location (dX, dY).
		gc.drawImage(image, 0, 0, imgWidthOrig, imgHeightOrig, x, y, imgWidth, imgHeight);

		// back to original state (before rotation)
	}

	private void drawArray(ArrayList<NormalFishCollect> images, GraphicsContext gc) {
		gc.save();
		for (int i = 0; i < images.size(); i++) {
			gc.drawImage(images.get(i).img, images.get(i).x, images.get(i).y);

		}
		gc.restore();

	}

	private void drawEnemies(ArrayList<EnemyFishCollect> images, GraphicsContext gc) {
		gc.save();
		for (int i = 0; i < images.size(); i++) {
			gc.drawImage(images.get(i).img, images.get(i).x, images.get(i).y);

		}
		gc.restore();

	}

	private void drawTrash(ArrayList<TrashCollect> images, GraphicsContext gc) {
		gc.save();
		for (int i = 0; i < images.size(); i++) {
			gc.drawImage(images.get(i).img, images.get(i).x, images.get(i).y);

		}
		gc.restore();

	}

	public void updateFish(ArrayList<NormalFishCollect> fishes) {
		for (int i = 0; i < collectables.size(); i++) {
			collectables.get(i).x = fishes.get(i).x;

		}
	}

	public void updateEnemies(ArrayList<EnemyFishCollect> fishes) {
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).x = fishes.get(i).x;

		}
	}

	public void updateTrash(ArrayList<TrashCollect> fishes) {
		for (int i = 0; i < trashList.size(); i++) {
			trashList.get(i).x = fishes.get(i).x;

		}
	}

	public static Level getLevel() {
		Level g = gameLevel;
		gameLevel = Level.LEVELTHREE;
		return g;
	}

}
