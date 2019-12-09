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

public class NetGameView implements java.io.Serializable{
	int picNum = -1; // index for which picture to use
	// value of the height and width of screen
	int canvasWidth = 1920;
	int canvasHeight = 1080;
	// value of the size of the net image
	static final int imgWidthOrig = 257;
	static final int imgHeightOrig = 257;
	int imgWidth = 300;
	int imgHeight = 300;

	//Alerts used as tutorial and booleans to ensure they are seen before the game starts
	Alert fishAlert = new Alert(AlertType.INFORMATION);
	Alert enemyAlert = new Alert(AlertType.INFORMATION);
	Alert trashAlert = new Alert(AlertType.INFORMATION);
	Alert endAlert = new Alert(AlertType.CONFIRMATION);
	Alert controlAlert = new Alert(AlertType.INFORMATION);
	boolean alert1 = true;
	boolean alert2 = true;
	boolean alert3 = true;
	boolean alert4 = true;
	
	Image background; //ocean background image
	public ArrayList<NormalFishCollect> collectables = new ArrayList<NormalFishCollect>(); //normal fish arraylist, meant to be avoided in game
	public ArrayList<EnemyFishCollect> enemies = new ArrayList<EnemyFishCollect>(); //enemy fish arraylist, meant to be collected in game
	public ArrayList<TrashCollect> trashList = new ArrayList<TrashCollect>(); //trash arraylist, meant to be collected in game


	boolean paused = false;
	double bassX; //net x location to be returned
	double bassY;//net y location to be returned

	// variables to determine the location of image
	double x = 0;
	double y = 0;
	
	int waittime = 3000;
	
	public Net net; //net object to be passed between classes

	public int score = 0; //score of the game
	Text t; //text used to display score
	static Scene gameThree; //definining which scene this is
	
	GraphicsContext gc; //used to draw images

	static Level gameLevel = Level.LEVELTHREE; //sets game level

	// View constructor initialize the starting position for the image
	// Called in controller
	/**
	 * sets all permanent fixtures onto the scene, as well as sets up event handelers for the mouse and interactable objects.
	 * It also sets up the array of collectable objects.
	 * 
	 */
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
	/**
	 * returns net x location to be passed to model
	 * @return bassX
	 */
	public double getX() {
		return bassX;
	}
	/**
	 * returns net y location to be passed to model
	 * @return bassY
	 */
	public double getY() {
		return bassY;
	}

	public boolean getPauseState() {
		return paused;
	}

	// Method used to import the images into the 2D image array
	/**
	 * sets background image as underwater.png
	 */
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
	/**
	 * used to set images based on their url
	 * @param image_file url location of the image file within the project
	 * @return img, the image object associated with the input url
	 */
	private Image createImage(String image_file) {
		Image img = new Image(image_file);
		return img;
	}

	// method used to repaint on the image and called in controller
	/**
	 * takes in updated arrays and image locations from model to be redrawn from controller
	 * This method also makes it so no objects besides the background until the tutorial is seen, or after the game ends
	 * @param e x location of the net
	 * @param f y location of the net
	 * @param imgs normal fish to be drawn, as updated from the array
	 * @param enemie enemy fish to be drawn, as updated from the array
	 * @param trashes trash objects to be drawn, as updated from the array
	 * @param score updated score of the game
	 */
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
				
				Thread.sleep(waittime);
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
			drawArray(collectables, gc);
			drawEnemies(enemies, gc);
			drawTrash(trashes, gc);

			// Now rotate and flip it based on direction, then draw to canvas
			transformAndDraw(gc, croppedImage, x, y);
		}
		

	}

	// getter methods to get the frame dimensions and image dimensions
	/**
	 * returns width of the canvas drawn, as set in the parameters of the view class
	 * @return canvasWidth
	 */
	public int getWidth() {
		return canvasWidth;
	}
	/**
	 * returns height of the canvas drawn, as set in the parameters of the view class
	 * @return canvasHeight
	 */
	public int getHeight() {
		return canvasHeight;
	}
	/**
	 * returns width of net image to be drawn
	 * @return imgWidth
	 */
	public int getImageWidth() {
		return imgWidth;
	}
	/**
	 * returns height of net image to be drawn
	 * @return imgHeight
	 */
	public int getImageHeight() {
		return imgHeight;
	}
	/**
	 * returns which scene this should be, which is gameThree
	 * @return gameThree
	 */
	public static Scene getScene() {
		return gameThree;
	}

	// If the bass is facing to the WEST, we must flip it, then rotate accordingly
	// for NORTH/SOUTH
	// Then draw to gc
	/**
	 * draws the net image onto the graphics context using x and y
	 * @param gc GraphicsContext
	 * @param image the image to be drawn
	 * @param x x location to draw the image at
	 * @param y y location to draw image at
	 */
	private void transformAndDraw(GraphicsContext gc, Image image, double x, double y) {
		
		gc.save(); // saves the current state on stack, including the current transform

		//draws image at the specified locations x,y with given heights and widths of image
		gc.drawImage(image, 0, 0, imgWidthOrig, imgHeightOrig, x, y, imgWidth, imgHeight);

		
	}
	/**
	 * draws the array of normal fishes onto the graphics context
	 * @param images arraylist of fish objects to be drawn
	 * @param gc GraphicsContext
	 */
	private void drawArray(ArrayList<NormalFishCollect> images, GraphicsContext gc) {
		gc.save();
		for (int i = 0; i < images.size(); i++) {
			gc.drawImage(images.get(i).img, images.get(i).x, images.get(i).y);

		}
		gc.restore();

	}
	/**
	 * draws the array of enemy fishes onto the graphics context
	 * @param images arraylist of fish objects to be drawn
	 * @param gc GraphicsContext
	 */
	private void drawEnemies(ArrayList<EnemyFishCollect> images, GraphicsContext gc) {
		gc.save();
		for (int i = 0; i < images.size(); i++) {
			gc.drawImage(images.get(i).img, images.get(i).x, images.get(i).y);

		}
		gc.restore();

	}
	/**
	 * draws the array of trash  onto the graphics context
	 * @param images arraylist of trash objects to be drawn
	 * @param gc GraphicsContext
	 */
	private void drawTrash(ArrayList<TrashCollect> images, GraphicsContext gc) {
		gc.save();
		for (int i = 0; i < images.size(); i++) {
			gc.drawImage(images.get(i).img, images.get(i).x, images.get(i).y);

		}
		gc.restore();

	}

	/**
	 * returns the game level we are in
	 * @return g game level we are in
	 */
	public static Level getLevel() {
		Level g = gameLevel;
		gameLevel = Level.LEVELTHREE;
		return g;
	}
	

}
