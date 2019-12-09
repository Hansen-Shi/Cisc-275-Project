package Game_Pkg;

import java.io.Serializable;
import java.util.ArrayList;

import javafx.scene.shape.Rectangle;

public class AnimalPhotoModel implements Serializable{
	
	static ArrayList <Rectangle> blankAnimals;
    
    static boolean complete = false;
	
	/**
	 * Constructor for the AnimalPhotoModel
	 * Creates 6 Rectangles at specific positions and
	 * stores them in an ArrayList
	 * 
	 * @author Hansen Shi
	 */
	public AnimalPhotoModel() {
		
		
		blankAnimals = new ArrayList <Rectangle>();
		
		Rectangle deerSqr = new Rectangle(275, 275, 200, 200);
		Rectangle turtleSqr = new Rectangle(850, 650, 100, 100);
		Rectangle heronSqr = new Rectangle (400, 400, 375, 250);
		Rectangle frogSqr = new Rectangle(300, 540, 75, 75);
		Rectangle otterSqr = new Rectangle(1350, 400, 150, 150);
		Rectangle birdSqr = new Rectangle(550, 100, 110, 110);
		
		blankAnimals.add(deerSqr);
		blankAnimals.add(turtleSqr);
		blankAnimals.add(heronSqr);
		blankAnimals.add(frogSqr);
		blankAnimals.add(otterSqr);
		blankAnimals.add(birdSqr);
	}
	
	public static void updateCompletion(ArrayList <Rectangle> list) {
		if (list.size() == 6) {
			complete = true;
		}
	}
	
	public static ArrayList <Rectangle> getAnimals() {
		return blankAnimals;
	}
	
	public static boolean getComplete () {
		return complete;
	}
}

