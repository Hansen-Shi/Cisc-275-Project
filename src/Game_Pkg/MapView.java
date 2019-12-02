package Game_Pkg;

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
import javafx.scene.transform.Rotate;
import javafx.scene.input.*;
import javafx.event.EventHandler;
import javafx.event.*;
import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

// NOTE: will not be needed once lab is complete
import java.util.Random;



public class MapView {
	int picNum = -1; // index for which picture to use
    //int picCount = 4; // number of pics in animation 

	// value of the height and width of screen
	int canvasWidth = 1080;
	int canvasHeight = 720;
	// value of the size of0he image.000013.

	static final int imgWidthOrig = 257;
	static final int imgHeightOrig = 257;

	int imgWidth = 300;
	int imgHeight = 300;
	
    GraphicsContext gc;

    Image background;
	// array of wide png images
    Image[] animationSequence;

    boolean paused = false;
	double bassX;
	double bassY;
	//variables to determine the location of image
	double x = 0;
	double y = 0;
	
	//Mode mode;
	
	boolean clicked = false;
	
	static Scene mapScreen;
	static Level gameLevel = Level.MAP;
	
	//static Level currentLvl = Level.MAP;
	
	
	//View constructor initialize the starting position for the image
	//Called in controller
	public MapView() {
        
		Button gameOne = new Button("Game 1");
        Button gameTwo = new Button("Game 2");
        Button gameThree = new Button("Game 3");
        
        
        Group root = new Group();
        mapScreen = new Scene(root);
        
        
        
        /*root.addEventFilter(MouseEvent.MOUSE_MOVED, e -> {
        	System.out.println(e.getSceneX() + " " + e.getSceneY());
            bassX = e.getSceneX() - 140;
            bassY = e.getSceneY() - 140;
        });*/
        
        

        Canvas canvas = new Canvas(canvasWidth, canvasHeight);
        root.getChildren().add(canvas);
       
        gc = canvas.getGraphicsContext2D();
        
        
		importImages();
		
		gameThree.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent e) {
				gameLevel = Level.LEVELTHREE;
				
			}
		});
		
		gameTwo.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent e) {
				gameLevel = Level.LEVELTWO;
			}
		});
		
		gameOne.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				gameLevel = Level.LEVELONE;
			}
		});
		
		root.getChildren().add(gameOne);
        
        gameOne.setTranslateX(418);
        gameOne.setTranslateY(277);
        
        root.getChildren().add(gameTwo);
        
        gameTwo.setTranslateX(158);
        gameTwo.setTranslateY(222);
        
        root.getChildren().add(gameThree);
        gameThree.setTranslateX(782);
        gameThree.setTranslateY(264);
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
	
	
	
	
	
	
	//Method used to import the images into the 2D image array
	private void importImages() {
		
		//Create array of the images. Each image pixel map contains
        // multiple images of the animate at different time steps
        
        // Eclipse will look for <path/to/project>/bin/<relative path specified>
        String img_file_base = "Game_Sprites/";
        
        String ext = ".jpg";	  	

        // Now we have the wide pngs for each mode stored in animationSequence
        
        // Load background
        background = createImage(img_file_base + "Map" + ext);
	}
	
    //Read image from file and return
    private Image createImage(String image_file) {
        Image img = new Image(image_file);
        return img;   	
    }

	//method used to repaint on the image and called in controller
	public void update(double e, double f) {
		x = e;
		y = f;

        // Clear the canvas
        gc.clearRect(0, 0, canvasWidth, canvasHeight);

        // draw background
        gc.drawImage(background, 0, 0, canvasWidth, canvasHeight);

        
	}
	
	//getter methods to get the frame dimensions and image dimensions
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
	
	public static Level getLevel() {
		Level g = gameLevel;
		gameLevel = Level.MAP;
		return g;
	}
	
	public static Scene getScene() {
		return mapScreen;
	}
}
