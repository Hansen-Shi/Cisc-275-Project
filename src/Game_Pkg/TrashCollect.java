package Game_Pkg;

import javafx.scene.image.Image;

public class TrashCollect {
	double x;
	double y;
	int vX;
	Image img = new Image("Game_Sprites/trashbag.png");
	
	public TrashCollect() {
		this.x = 1800;
		this.y = Math.random() * 720;
		this.vX = 6;
		
	}
}
