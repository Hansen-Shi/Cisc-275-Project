package Game_Pkg;

import javafx.scene.image.Image;

public class EnemyFishCollect {
	double x;
	double y;
	int vX;
	Image img = new Image("Game_Sprites/fish_catfish_left_0.png");
	
	public EnemyFishCollect() {
		this.x = 1800;
		this.y = Math.random() * 720;
		this.vX = 4;
		
	}
}
