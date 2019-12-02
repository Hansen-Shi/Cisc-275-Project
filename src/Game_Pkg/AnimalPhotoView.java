package Game_Pkg;

import javafx.event.ActionEvent;
import javafx.application.Application;
import javafx.event.EventHandler;
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
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
import javafx.scene.input.*;
import javafx.event.EventHandler;
import javafx.event.*;

import javafx.scene.paint.Color;
import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class AnimalPhotoView {
	int picNum = -1; // index for which picture to use
    int picCount = 4; // number of pics in animation 

	// value of the height and width of screen
	static int canvasWidth = 1080;
	static int canvasHeight = 720;
	// value of the size of0he image.000013.

	static final int imgWidthOrig = 620;
	static final int imgHeightOrig = 728;

	int imgWidth = 300;
	int imgHeight = 300;
	
    static GraphicsContext gc;

    Image background;
    Image camera;
    Image noCamera;
	// array of wide png images
    Image[] animationSequence;
    
    static Image Deer;
    static Image Turtle;
    
    boolean takePic = false;
    static boolean picTakenDeer = false;
    static boolean picTakenTurtle = false;

    boolean paused = false;
	double bassX;
	double bassY;
	//variables to determine the location of image
	double x = 0;
	double y = 0;
	
	static Animal species = Animal.NONE;
	
	static Scene gameTwo;
	
	static Level gameLevel = Level.LEVELTWO;
	
	//static Level currentLvl = Level.MAP;
	
	
	//View constructor initialize the starting position for the image
	//Called in controller
	public AnimalPhotoView() {
        
        Group root = new Group();
        gameTwo = new Scene(root);
        
        Button home = new Button("Home");
        
        Deer = createImage("Game_Sprites/Deer.png");
        Turtle = createImage("Game_Sprites/turtle.png");
        ImagePattern pat = new ImagePattern(Deer);
        ImagePattern pat2 = new ImagePattern(Turtle);
        
        Rectangle deer = new Rectangle(250, 200, 100, 100);
        deer.setFill(pat);
        Rectangle turtle = new Rectangle(400, 350, 100, 100);
        turtle.setFill(pat2);
        
        home.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent e) {
        		gameLevel = Level.MAP;
        	}
        });
        
        deer.setOnMouseEntered(e ->{
        	takePic = true;
        	species = Animal.DEER;
        	System.out.println(takePic + ", " + species);
        });
        
        deer.setOnMouseExited(e ->{
        	takePic = false;
        	species = Animal.NONE;
        	System.out.println(takePic + ", " + species);
        });
        
        turtle.setOnMouseEntered(e ->{
        	takePic = true;
        	species = Animal.TURTLE;
        	System.out.println(takePic + ", " + species);
        });
        
        turtle.setOnMouseExited(e ->{
        	takePic = false;
        	species = Animal.NONE;
        	System.out.println(takePic + ", " + species);
        });
        
        root.addEventFilter(MouseEvent.MOUSE_MOVED, e -> {
        	//System.out.println(e.getSceneX() + " " + e.getSceneY());
            bassX = e.getSceneX() - 25;
            bassY = e.getSceneY() - 50;
        });
        
        root.addEventFilter(MouseEvent.MOUSE_CLICKED, e->{
        	if(takePic == true && species == Animal.DEER) {
        		root.getChildren().removeAll(deer, turtle);
        		System.out.println("I swear im working");
        		picTakenDeer = true;
        		}
        	else if (takePic == true && species == Animal.TURTLE) {
        		root.getChildren().removeAll(deer, turtle);
        		System.out.println("Still working I promise");
        		picTakenTurtle = true;
        	}
        });
        
        Canvas canvas = new Canvas(canvasWidth, canvasHeight);
        root.getChildren().add(canvas);
        root.getChildren().add(deer);
        root.getChildren().add(turtle);
        root.getChildren().add(home);
        
        gc = canvas.getGraphicsContext2D();
        
        
		importImages();
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
	
	public static Scene getScene() {
		return gameTwo;
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
        background = createImage(img_file_base + "Forest" + ext);
        camera = createImage(img_file_base + "Camera.png");
        noCamera = createImage(img_file_base + "NoCamera.png");
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
		
		Image pics;
		
		if (takePic == false) {
			pics = noCamera;
			picNum = 0;
		}
		else {
			pics = camera;
			picNum = 0;
		}
		

        // Clear the canvas
        gc.clearRect(0, 0, canvasWidth, canvasHeight);

        // draw background
        gc.drawImage(background, 0, 0, canvasWidth, canvasHeight);
        
        Rectangle2D croppedPortion = new Rectangle2D(imgWidthOrig*picNum, 0, imgWidthOrig, imgHeightOrig);
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

        // Now rotate and flip it based on direction, then draw to canvas
        transformAndDraw(gc, croppedImage, x, y);
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
	
	public static void getPhotoTurtle() {
		
		gc.clearRect(0, 0, canvasWidth, canvasHeight);
		gc.drawImage(Turtle, -725, -325);
		}
	public static void getPhotoDeer() {
		
		gc.clearRect(0, 0, canvasWidth, canvasHeight);
		gc.drawImage(Deer, -125, -100);
	}
	
	public static boolean pictureTakenDeer() {
		return picTakenDeer;
	}
	
	public static boolean pictureTakenTurtle() {
		return picTakenTurtle;
	}
	
	public static Level getLevel() {
		Level g = gameLevel;
		gameLevel = Level.LEVELTWO;
		return g;
	}

    // If the bass is facing to the WEST, we must flip it, then rotate accordingly
    // for NORTH/SOUTH
    // Then draw to gc
    private void transformAndDraw(GraphicsContext gc, Image image, 
            double x, double y) {
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

        // set canvas rotation about the point (x+imageWidth/2, y+imageWidth/2) by angle theta
        Rotate r = new Rotate(theta, x+imgWidth/2, y+imgWidth/2);
        // Transform gc by the rotation
        gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());

        // draw the subimage from the original png to animate the bass's motion
        // The arguments for drawImage are:
        // 
        // drawImage(sourceImage, sX, sY, sWidth, sHeight, dX, dY, dWidth, dHeight)
        //
        // This means that a rectangle of size (sWidth, sHeight) will be CROPPED from sourceImage
        // at location (sX, sY), RESIZED to (dWidth, dHeight), and drawn to the 
        // parent of the GraphicsContext at location (dX, dY).
        gc.drawImage(image, 0, 0, imgWidthOrig, imgHeightOrig,
                            x, y, imgWidth, imgHeight);
                        
        gc.restore(); // back to original state (before rotation)
    }
}
