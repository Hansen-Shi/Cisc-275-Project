package RunOffTestPkg;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Game_Pkg.RunOffGameModel;

class RunOffModelTest {
	RunOffGameModel model = new RunOffGameModel(null, null);
	@Test
	void test() {
		fail("Not yet implemented");
	}
	
	void updateTest() {
		model.updateLocation(5, 10);
		assertEquals(5, model.getX());
		assertEquals(10, model.getY());
	}

}
