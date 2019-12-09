package Game_Pkg;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.input.*;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.Serializable;

public class AnimalPhotoView implements Serializable{
	
	int picNum = 0; // index for which picture to use
    

	//Effectively adds Fullscreen mode
    //The Canvas will fill any computer's screen as it's based off native resolution
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	static int canvasWidth = (int) screenSize.getWidth();
	static int canvasHeight = (int) screenSize.getHeight();
	
	//Size of the Camera imgs
	static final int imgWidthOrig = 620;
	static final int imgHeightOrig = 728;

	int imgWidth = 300;
	int imgHeight = 300;
	
    static GraphicsContext gc;

    Image background;
    Image camera;
    Image noCamera;
	
    ArrayList <Rectangle> rectangleAnimals;
    ArrayList <Rectangle> removedAnimals;
    ArrayList <Image> Animals;
    ArrayList <ImageView> animalViews;
    
    static boolean levelComplete = false;
    
    
    boolean takePic = false;

    
	
    double X;
	double Y;
	
	double x = 0;
	double y = 0;
	
	static Animal species = Animal.NONE;
	
	static Scene gameTwo;
	
	static Level gameLevel = Level.LEVELTWO;
	
	
	
	
	/**
	 * Constructor for the AnimalPhotoView
	 * Creates a root Group
	 * Creates 6 animals consisting of Rectangles and ImagePatterns and adds them to the root
	 * Creates a Home button in the top left corner and adds it to the root
	 * Creates a Cavnas and adds it to the root
	 * Creates 6 Alerts
	 * and Creates event handlers for each of the 6 animals
	 * 
	 * @author Hansen Shi
	 * @param list an ArrayList of Rectangles 
	 */
	public AnimalPhotoView(ArrayList <Rectangle> list) {
		
		rectangleAnimals = list;
		
		removedAnimals = new ArrayList <Rectangle>();
		
		
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
        
        Rectangle deer = createAnimal(rectangleAnimals.get(0), deerPat);
        Rectangle turtle = createAnimal(rectangleAnimals.get(1), turtlePat);
        Rectangle heron = createAnimal(rectangleAnimals.get(2), heronPat);
        Rectangle frog = createAnimal(rectangleAnimals.get(3), frogPat);
        Rectangle otter = createAnimal(rectangleAnimals.get(4), otterPat);
        Rectangle bird = createAnimal(rectangleAnimals.get(5), birdPat);
        
        
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
        
        otter.setOnMouseEntered(e->{
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
        
        root.addEventFilter(MouseEvent.MOUSE_MOVED, e-> {
            X = e.getSceneX() - 25;
            Y = e.getSceneY() - 50;
        });
        
        root.addEventFilter(MouseEvent.MOUSE_CLICKED, e->{
        	if(takePic == true && species == Animal.DEER) {
        		createAlerts(deerAlert, "They're surprisingly good swimmers",
        				"A White Tailed Deer!\n" + "It's a buck at that, you can tell cause he's got antlers", animalViews.get(0));
        		deerAlert.showAndWait();
        		root.getChildren().remove(deer);
        		removedAnimals.add(deer);
        	}
        	
        	else if (takePic == true && species == Animal.TURTLE) {
        		createAlerts(turtleAlert, "Their bright pastron doesn't quite match their shy nature "
        		, "A Red Bellied Turtle!\n" + "Not to be confused with its cousin the painted turtle...", animalViews.get(1));
        		turtleAlert.showAndWait();
        		root.getChildren().remove(turtle);
        		removedAnimals.add(turtle);
        	}
        	else if (takePic == true && species == Animal.HERON) {
        		createAlerts(heronAlert, "They're huge with a 6 foot wingspan, wowzers",
        				"A Great Blue Heron!\n" + "These birds stand perfectly still in the water waiting to strike", animalViews.get(2));
        		heronAlert.showAndWait();
        		root.getChildren().remove(heron);
        		removedAnimals.add(heron);
        	}
        	else if (takePic == true && species == Animal.FROG) {
        		createAlerts(frogAlert, "Males can make a cow-like moo sound", 
        				"A Bullfrog!\n" + "They got a camo coat on", animalViews.get(3));
        		frogAlert.showAndWait();
        		root.getChildren().remove(frog);
        		removedAnimals.add(frog);
        	}
        	else if (takePic == true && species == Animal.OTTER) {
        		createAlerts(otterAlert, "Don't spook them, they scream real loud when frightened", 
        				"A North American River Otter!\n" + "These little fellas need a lot of seafood everyday", animalViews.get(4));
        		otterAlert.showAndWait();
        		root.getChildren().remove(otter);
        		removedAnimals.add(otter);
        	}
        	else if (takePic == true && species == Animal.BIRD) {
        		createAlerts(birdAlert, "These birds love to visit the Delaware Bay during migration season", 
        				"A Red Knot!\n" + "These little guys fly real far every Spring and Fall", animalViews.get(5));
        		birdAlert.showAndWait();
        		root.getChildren().remove(bird);
        		removedAnimals.add(bird);
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
	
	/**
	 * Creates a rectangle with a set ImagePattern
	 * 
	 * @author Hansen Shi
	 * @param r a Rectangle object
	 * @param img an ImagePattern object
	 * @return a Rectangle Object
	 */
	private Rectangle createAnimal(Rectangle r, ImagePattern img) {
		Rectangle rect = new Rectangle(r.getX(), r.getY(), r.getWidth(), r.getHeight());
		rect.setFill(img);
		return rect;
	}
	
	/**
	 * Creates a custom Alert with a new icon img and text content
	 * 
	 * @author Hansen Shi
	 * @param a an Alert
	 * @param content a String 
	 * @param header a String
	 * @param img an ImageView
	 * @return none
	 */
	private void createAlerts(Alert a, String content, String header,ImageView img) {
		a.setAlertType(AlertType.INFORMATION);
		a.setContentText(content);
		a.setGraphic(img);
		a.setHeaderText(header);
	}
	
	/**
	 * Resizes all the ImageViews in an ArrayList 
	 * 
	 * @author Hansen Shi
	 * @param list an ArrayList of ImageViews
	 */
	private void setImageView(ArrayList <ImageView> list) {
		for (int i = 0; i<list.size(); i++) {
			list.get(i).setFitHeight(200);
			list.get(i).setFitWidth(200);
		}
	}
	
	/**
	 * Loads the background img as well as the two camera related images
	 * 
	 * @author Hansen Shi
	 * @param none
	 */
	private void importImages() {
		// Eclipse will look for <path/to/project>/bin/<relative path specified>
        String img_file_base = "Game_Sprites/";
        
        String ext = ".jpg";	  	
        
        // Loads background and the two camera related images
        background = createImage(img_file_base + "Forest" + ext);
        camera = createImage(img_file_base + "Camera.png");
        noCamera = createImage(img_file_base + "NoCamera.png");
        }
	
    /**
     * Creates an image based on an image file located at the directory equal to the string parameter
     * 
     * @author Hansen Shi
     * @param image_file a string 
     * @return an image
     */
    private Image createImage(String image_file) {
        Image img = new Image(image_file);
        return img;   	
    }

	/**
	 * Attaches image to mouse cursor
	 * Updates the mouse cursor image if the player is hovering an animal
	 * Changes the level back to the Map upon completion
	 * 
	 * @author Hansen Shi
	 * @param e a double equal to the x position of the mouse
	 * @param f a double equal to the y poisition of the mouse
	 * @param b a boolean 
	 */
	public void update(double e, double f, boolean b) {
		x = e;
		y = f;
		levelComplete = b;
		
		Image pics;
		
		if (takePic == false) {
			pics = noCamera;
		}
		else {
			pics = camera;
		}
		
		if (levelComplete == true ) {
			gameLevel = Level.MAP;
		}
		

        // Clear the canvas
        gc.clearRect(0, 0, canvasWidth, canvasHeight);

        // draw background
        gc.drawImage(background, 0, 0, canvasWidth, canvasHeight);
        
        Rectangle2D croppedPortion = new Rectangle2D(imgWidthOrig*picNum, 0, imgWidthOrig, imgHeightOrig);
        
        ImageView imageView = new ImageView(pics);
        imageView.setViewport(croppedPortion);
        imageView.setFitWidth(imgWidthOrig);
        imageView.setFitHeight(imgHeightOrig);
        imageView.setSmooth(true);
        
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        Image croppedImage = imageView.snapshot(params, null);

        transformAndDraw(gc, croppedImage, x, y);
        }
	
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
		gameLevel = Level.LEVELTWO;
		return g;
	}
	
	public double getX() {
		return X;
	}
	public double getY() {
		return Y;
	}
	
	public static Scene getScene() {
		return gameTwo;
	}
	
	public ArrayList <Rectangle> getRemovedAnimals(){
		return removedAnimals;
	}
   
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
