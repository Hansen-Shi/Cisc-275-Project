package Game_Pkg;

public enum Animal {

    NONE("Underwater.png"), DEER("deer.jpg"), TURTLE("turtle.png");
	
	private String name = null;
	
	private Animal(String s){
		name = s;
	}

	public String getName() {
		return name;
	}
}
