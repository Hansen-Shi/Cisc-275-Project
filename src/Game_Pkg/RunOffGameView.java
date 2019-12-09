package Game_Pkg;

import javafx.scene.Group;
import javafx.application.Application;
import javafx.event.EventHandler;
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
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.event.EventHandler;
import javafx.event.*;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class RunOffGameView implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	int picNum = -1; // index for which picture to use
	// int picCount = 4; // number of pics in animation

	// value of the height and width of screen
	static Dimension screenSizze = Toolkit.getDefaultToolkit().getScreenSize();
	static int canvasWidth = (int) screenSizze.getWidth();
	static int canvasHeight = (int) screenSizze.getHeight();
	// value of the size of0he image.000013.

	static final int imgWidthOrig = 100;
	static final int imgHeightOrig = 100;

	int imgWidth = 100;
	int imgHeight = 100;

	int sBushSize = 150;
	int lBushSize = 200;
	int score = 0;
	static Image bush;
	GraphicsContext gc;
	Text t;
	Rectangle bush1;
	Rectangle bush2;
	Rectangle bush3;
	Image background;
	// array of wide png images
	Image[] animationSequence;
	ArrayList<OilBlob> blobs = new ArrayList<OilBlob>();
	ArrayList<Rectangle> bushs = new ArrayList<Rectangle>();

	boolean paused = false;
	int playerX;
	int playerY;

	// variables to determine the location of image

	Random rand = new Random();

	static Scene gameOne;

	/**
	 * constructor
	 */
	public RunOffGameView() {
		Button bushHeal = new Button("Restore bushes");
		Button tutorial = new Button("Instructions"); 

		bush = createImage("Game_Sprites/Bush.png");
		ImagePattern bushPat = new ImagePattern(bush);
		
		bush1 = new Rectangle(100, 200, 100, 200);
		bush2 = new Rectangle(200, 400, 100, 200);
		bush3 = new Rectangle(100, 600, 100, 200);
		bush1.setFill(bushPat);
		bush2.setFill(bushPat);
		bush3.setFill(bushPat);
		bushs.add(bush1);
		bushs.add(bush2);
		bushs.add(bush3);
		
		bush1.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
			bush1.setX(e.getSceneX());
			bush1.setY(e.getSceneY());
		});
		
		bush2.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
			bush2.setX(e.getSceneX());
			bush2.setY(e.getSceneY());
		});
		
		bush3.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
			bush3.setX(e.getSceneX());
			bush3.setY(e.getSceneY());
		});
		
		bushHeal.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			
		});
		
		tutorial.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("Collect the oil run off!");
			alert.setTitle("Instructions");
			alert.setContentText("Move the bushes by clicking and dragging them to new areas to block oil run off."
								+ "Bushes can only absorb so much oil so be sure to restor them by pushing the restore bush"
								+ "button.");
			alert.showAndWait();
		});
		Group root = new Group();
		gameOne = new Scene(root);
		t = new Text("" + score);
		Font font = Font.font("Verdana", FontWeight.EXTRA_BOLD, 50);
		t.setFont(font);
		
		root.addEventFilter(MouseEvent.MOUSE_DRAGGED, e -> {
			playerX = (int) (e.getSceneX() - 75);
			playerY = (int) (e.getSceneY() - 50);
		});
		
		
	
		for (int i = 0; i <= 3; i++) {
			blobs.add(new OilBlob(imgWidth, imgHeight));
		}
		Canvas canvas = new Canvas(canvasWidth, canvasHeight);
		
		BorderPane bPane = new BorderPane();
		bPane.setLeft(bushHeal);
		bPane.setTop(tutorial);
		bPane.setCenter(canvas);
		root.getChildren().add(canvas);
		root.getChildren().add(bPane);
		root.getChildren().add(bush1);
		root.getChildren().add(bush2);
		root.getChildren().add(bush3);
		gc = canvas.getGraphicsContext2D();
		importImages();
		root.getChildren().add(t);
		t.setTranslateX(20);
		t.setTranslateY(100);
	}

	public boolean getPauseState() {
		return paused;
	}

	/** Method used to import the images into the 2D image array
	 * 
	 */
	private void importImages() {

		// Create array of the images. Each image pixel map contains
		// multiple images of the animate at different time steps

		// Eclipse will look for <path/to/project>/bin/<relative path specified>
		String img_file_base = "Game_Sprites/";

		String ext = ".jpg";

		// Now we have the wide pngs for each mode stored in animationSequence

		// Load background
		background = createImage(img_file_base + "Runoff" + ext);
	}

	/**
	 * uses a string to find the image needed
	 * @param image_file the location of the image
	 * @return the image
	 */
	private Image createImage(String image_file) {
		Image img = new Image(image_file);
		return img;
	}

	/**
	 * method used to repaint on the image and called in controller
	 * @param e the x location of the player's cursor
	 * @param f the y location of the player's cursor
	 * @param score the score of the game
	 * @param blobs the arraylist of oil blob images
	 * @param bushs the arraylist of the bush images
	 */
	public void update(int e, int f, int score, ArrayList<OilBlob> blobs, ArrayList<Rectangle> bushs) {
		playerX = e;
		playerY = f;
		this.score = score;
		t.setText("" + getScore());

		Image bush = createImage("Game_Sprites/Bush.png");
		picNum = 0;
		Image oil = createImage("Game_Sprites/Oil.png");

		// Clear the canvas
		gc.clearRect(0, 0, canvasWidth, canvasHeight);

		// draw background
		gc.drawImage(background, 0, 0, canvasWidth, canvasHeight);

		Rectangle2D croppedPortion = new Rectangle2D(imgWidthOrig * picNum, 0, imgWidthOrig, imgHeightOrig);
		// Define an ImageView with the wide png image 'bush'
		SnapshotParameters params = new SnapshotParameters();
		params.setFill(Color.TRANSPARENT);
		
		this.bushs = bushs;

		ImageView oilView = new ImageView(oil);
		oilView.setViewport(croppedPortion);
		oilView.setFitHeight(imgHeight);
		oilView.setFitWidth(imgWidth);
		oilView.setSmooth(true);
		Image croppedOil = oilView.snapshot(params, null);

		// Now rotate and flip it based on direction, then draw to canvas
		this.blobs = blobs;
		for (OilBlob o : blobs) {
			transformAndDraw(gc, croppedOil, o.getX(), o.getY());
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

	public int getPlayerX() {
		return playerX;
	}

	public int getPlayerY() {
		return playerY;
	}
	
	public int getScore() {
		return score;
	}

	public static Scene getScene() {
		return gameOne;
	}
	

	// If the bass is facing to the WEST, we must flip it, then rotate accordingly
	// for NORTH/SOUTH
	// Then draw to gc
	/**
	 *  If the bass is facing to the WEST, we must flip it, then rotate accordingly
	 *  for NORTH/SOUTH
	 *  Then draw to gc
	 * @param gc the graphic's context being used
	 * @param image the image being modified
	 * @param x the x location of the image
	 * @param y the y location of the image
	 */
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

		gc.drawImage(image, 0, 0, imgWidthOrig, imgHeightOrig, x, y, imgWidth, imgHeight);

		gc.restore(); // back to original state (before rotation)
	}
}
