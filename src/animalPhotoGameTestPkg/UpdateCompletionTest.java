package animalPhotoGameTestPkg;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.Assert.*;

import Game_Pkg.AnimalPhotoModel;
import javafx.scene.shape.Rectangle;

class AnimalPhotoModelTest {

	@Test
	public void testUpdateCompletion() {
		
		Rectangle one = new Rectangle(50,50);
		Rectangle two = new Rectangle(50,50);
		Rectangle three = new Rectangle(50,50);
		Rectangle four = new Rectangle(50,50);
		Rectangle five = new Rectangle(50,50);
		Rectangle six = new Rectangle(50,50);
		
		
		ArrayList <Rectangle> emptyList = new ArrayList <Rectangle>();
		ArrayList <Rectangle> notFullList = new ArrayList <Rectangle>();
		ArrayList <Rectangle> FullList = new ArrayList <Rectangle>();
		
		notFullList.add(one);
		
		FullList.add(one);
		FullList.add(two);
		FullList.add(three);
		FullList.add(four);
		FullList.add(five);
		FullList.add(six);
		
		AnimalPhotoModel.updateCompletion(emptyList);
		assertEquals(AnimalPhotoModel.getComplete(), false);
		
		AnimalPhotoModel.updateCompletion(notFullList);
		assertEquals(AnimalPhotoModel.getComplete(), false);
		
		AnimalPhotoModel.updateCompletion(FullList);
		assertEquals(AnimalPhotoModel.getComplete(), true);
		}

}
