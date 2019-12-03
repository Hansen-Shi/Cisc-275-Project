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

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class AnimalPhotoView {
	int picNum = -1; // index for which picture to use
    int picCount = 4; // number of pics in animation 

	// value of the height and width of screen
	static int canvasWidth = 1280;
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
    ArrayList <Image> Animals;
    ArrayList <ImageView> animalViews;
    
    
    boolean takePic = false;

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
		
		Animals = new ArrayList <Image>();
		Animals.add(createImage("Game_Sprites/genericDeer.png"));
		Animals.add(createImage("Game_Sprites/genericTurtle.png"));
		Animals.add(createImage("Game_Sprites/genericHeron.png"));
		Animals.add(createImage("Game_Sprites/genericFrog.png"));
		Animals.add(createImage("Game_Sprites/genericOtter.png"));
		Animals.add(createImage("Game_Sprites/genericBird.png"));
		
		animalViews = new ArrayList <ImageView>();
		animalViews.add(new ImageView("Game_Sprites/deer.png"));
		animalViews.add(new ImageView("Game_Sprites/turtle.png"));
		animalViews.add(new ImageView("Game_Sprites/heron.png"));
		animalViews.add(new ImageView("Game_Sprites/frog.png"));
		animalViews.add(new ImageView("Game_Sprites/otter.png"));
		animalViews.add(new ImageView("Game_Sprites/genericBird.png"));
		
		setImageView(animalViews);
        
        Group root = new Group();
        gameTwo = new Scene(root);
        
        Button home = new Button("Home");
        Alert turtleAlert = new Alert(AlertType.NONE);
        Alert deerAlert = new Alert(AlertType.NONE);
        Alert heronAlert = new Alert(AlertType.NONE);
        Alert frogAlert = new Alert(AlertType.NONE);
        Alert otterAlert = new Alert(AlertType.NONE);
        Alert birdAlert = new Alert(AlertType.NONE);
        
         
        ImagePattern deerPat = new ImagePattern(Animals.get(0));
        ImagePattern turtlePat = new ImagePattern(Animals.get(1));
        ImagePattern heronPat = new ImagePattern(Animals.get(2));
        ImagePattern frogPat = new ImagePattern(Animals.get(3));
        ImagePattern otterPat = new ImagePattern(Animals.get(4));
        ImagePattern birdPat = new ImagePattern(Animals.get(5));
        
        Rectangle deer = new Rectangle(250, 200, 100, 100);
        deer.setFill(deerPat);
        Rectangle turtle = new Rectangle(400, 400, 80, 80);
        turtle.setFill(turtlePat);
        Rectangle heron = new Rectangle (600, 250, 200, 150);
        heron.setFill(heronPat);
        Rectangle frog = new Rectangle(175, 350, 75, 75);
        frog.setFill(frogPat);
        Rectangle otter = new Rectangle(900, 250, 100, 100);
        otter.setFill(otterPat);
        Rectangle bird = new Rectangle(550, 100, 100, 100);
        bird.setFill(birdPat);
        
        home.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent e) {
        		gameLevel = Level.MAP;
        	}
        });
        
        deer.setOnMouseEntered(e ->{
        	takePic = true;
        	species = Animal.DEER;
        });
        
        deer.setOnMouseExited(e ->{
        	takePic = false;
        	species = Animal.NONE;
        });
        
        turtle.setOnMouseEntered(e ->{
        	takePic = true;
        	species = Animal.TURTLE;
        });
        
        turtle.setOnMouseExited(e ->{
        	takePic = false;
        	species = Animal.NONE;
        });
        
        heron.setOnMouseEntered(e ->{
        	takePic = true;
        	species = Animal.HERON;
        });
        
        heron.setOnMouseExited(e ->{
        	takePic = false;
        	species = Animal.NONE;
        });
        
        frog.setOnMouseEntered(e ->{
        	takePic = true;
        	species = Animal.FROG;
        });
        
        frog.setOnMouseExited(e ->{
        	takePic = false;
        	species = Animal.NONE;
        });
        
        otter.setOnMouseEntered(e ->{
        	takePic = true;
        	species = Animal.OTTER;
        });
        
        otter.setOnMouseExited(e ->{
        	takePic = false;
        	species = Animal.NONE;
        });
        
        bird.setOnMouseEntered(e ->{
        	takePic = true;
        	species = Animal.BIRD;
        });
        
        bird.setOnMouseExited(e ->{
        	takePic = false;
        	species = Animal.NONE;
        });
        
        root.addEventFilter(MouseEvent.MOUSE_MOVED, e -> {
            bassX = e.getSceneX() - 25;
            bassY = e.getSceneY() - 50;
        });
        
        root.addEventFilter(MouseEvent.MOUSE_CLICKED, e->{
        	if(takePic == true && species == Animal.DEER) {
        		root.getChildren().removeAll(deer);
        		createAlerts(deerAlert, "They're surprisingly good swimmers",
        				"A White Tailed Deer!\n" + "It's a buck at that, you can tell cause he's got antlers", animalViews.get(0));
        		deerAlert.showAndWait();
        	}
        	
        	else if (takePic == true && species == Animal.TURTLE) {
        		root.getChildren().remove(turtle);
        		createAlerts(turtleAlert, "Their bright pastron doesn't quite match their shy nature "
        		, "A Red Bellied Turtle!\n" + "Not to be confused with its cousin the painted turtle...", animalViews.get(1));
        		turtleAlert.showAndWait();
        	}
        	else if (takePic == true && species == Animal.HERON) {
        		root.getChildren().remove(heron);
        		createAlerts(heronAlert, "They're huge with a 6 foot wingspan, wowzers",
        				"A Great Blue Heron!\n" + "These birds stand perfectly still in the water waiting to strike", animalViews.get(2));
        		heronAlert.showAndWait();
        	}
        	else if (takePic == true && species == Animal.FROG) {
        		root.getChildren().remove(frog);
        		createAlerts(frogAlert, "Males can make a cow-like moo sound", 
        				"A Bullfrog!\n" + "They got a camo coat on", animalViews.get(3));
        		frogAlert.showAndWait();
        	}
        	else if (takePic == true && species == Animal.OTTER) {
        		root.getChildren().remove(otter);
        		createAlerts(otterAlert, "Don't spook them, they scream real loud when frightened", 
        				"A North American River Otter!\n" + "These little fellas need a lot of seafood everyday", animalViews.get(4));
        		otterAlert.showAndWait();
        	}
        	else if (takePic == true && species == Animal.BIRD) {
        		root.getChildren().remove(bird);
        		createAlerts(birdAlert, "These birds love to visit the Delaware Bay during migration season", 
        				"A Red Knot!\n" + "These little guys fly real far every Spring and Fall", animalViews.get(5));
        		birdAlert.showAndWait();
        	}
        });
        
        Canvas canvas = new Canvas(canvasWidth, canvasHeight);
        root.getChildren().add(canvas);
        root.getChildren().add(deer);
        root.getChildren().add(turtle);
        root.getChildren().add(heron);
        root.getChildren().add(frog);
        root.getChildren().add(otter);
        root.getChildren().add(bird);
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
	
	private void createAlerts(Alert a, String content, String header,ImageView img) {
		a.setAlertType(AlertType.INFORMATION);
		a.setContentText(content);
		a.setGraphic(img);
		a.setHeaderText(header);
	}
	
	private void setImageView(ArrayList <ImageView> list) {
		for (int i = 0; i<list.size(); i++) {
			list.get(i).setFitHeight(200);
			list.get(i).setFitWidth(200);
		}
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
