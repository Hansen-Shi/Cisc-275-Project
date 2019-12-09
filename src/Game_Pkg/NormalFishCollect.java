
package Game_Pkg;

import javafx.scene.image.Image;



public class NormalFishCollect implements java.io.Serializable{
	public double x;
	public double y;
	int vX;
	Image img = new Image("Game_Sprites/fish_bass_left.png");
	
	public NormalFishCollect(){
		this.x = 1800;
		this.y = Math.random() * 720;
		this.vX = 8;
	}
}
