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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.input.*;
import javafx.event.EventHandler;
import javafx.event.*;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

// NOTE: will not be needed once lab is complete
import java.util.Random;

public class NetGameView {
	int picNum = -1; // index for which picture to use
    int picCount = 4; // number of pics in animation 

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
    
    ArrayList<Collectable> collectables = new ArrayList<Collectable>();
	// array of wide png images
    Image[] animationSequence;

	// Used to determine the direction to warp the bass image into
	
    int modeInd = -1;
    // Used to index the animationSequence outter array. 
    
    boolean paused = false;
	double bassX;
	double bassY;
	
	//variables to determine the location of image
	double x = 0;
	int score = 0;
	Text t;
	double y = 0;
	Net net;
	
	static Scene gameThree;
	//View constructor initialize the starting position for the image
	//Called in controller
	public NetGameView() {
		net = new Net(0,0);
		t = new Text("" + score);
		Font font = Font.font("Verdana", FontWeight.EXTRA_BOLD, 50);
		t.setFont(font);
		
        Group root = new Group();
        gameThree = new Scene(root);
        
        
        
        
        root.addEventFilter(MouseEvent.MOUSE_MOVED, e -> {
        	//System.out.println(e.getSceneX() + " " + e.getSceneY());
            net.setX( e.getSceneX() - 140);
            net.setY(e.getSceneY() - 140);

        });
    
        
        

        Canvas canvas = new Canvas(canvasWidth, canvasHeight);
        root.getChildren().add(canvas);
       
        gc = canvas.getGraphicsContext2D();
        
        for(int i = 0; i < 10; i++) {
        	collectables.add(new Collectable());
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
	
	//Method used to import the images into the 2D image array
	private void importImages() {
		
		//Create array of the images. Each image pixel map contains
        // multiple images of the animate at different time steps
        
        // Eclipse will look for <path/to/project>/bin/<relative path specified>
        String img_file_base = "Game_Sprites/";
        String ext = ".jpg";

       
        
        // Load background
        background = createImage(img_file_base + "Ocean" + ext);
	}
	
    //Read image from file and return
    private Image createImage(String image_file) {
        Image img = new Image(image_file);
        return img;   	
    }

	//method used to repaint on the image and called in controller
	public void update(double e, double f, ArrayList<Collectable> imgs, int score) {
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
        Rectangle2D croppedPortion = new Rectangle2D(imgWidthOrig*picNum, 0, 
                                                 imgWidthOrig, imgHeightOrig);
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
        drawArray(collectables, gc);
        
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
	
	public static Scene getScene() {
		return gameThree;
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
                        
        // back to original state (before rotation)
    }
    
    private void drawArray(ArrayList<Collectable> images, GraphicsContext gc) {
    	gc.save();
    	for(int i = 0; i < images.size(); i++) {
    		gc.drawImage(images.get(i).img, images.get(i).x, images.get(i).y);
    		
    	}
    	gc.restore();
    	
    }
    public void updateFish(ArrayList<Collectable> fishes) {
    	for(int i = 0; i < collectables.size(); i++) {
    		collectables.get(i).x = fishes.get(i).x;
    		
    	}
    }

}

