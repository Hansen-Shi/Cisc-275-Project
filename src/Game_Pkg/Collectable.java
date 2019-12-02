
package Game_Pkg;

import javafx.scene.image.Image;

public class Collectable {
	double x;
	double y;
	int vX;
	Image img = new Image("Game_Sprites/fish_bass_left.png");
	
	public Collectable(){
		this.x = 1000;
		this.y = Math.random() * 720;
		this.vX = 5;
	}
}
