package RunOffTestPkg;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import Game_Pkg.Bush;

class BushTest {

	@Test
	void test() {
		fail("Not yet implemented");
	}

	@Test
	void updateTest() {
		ArrayList<Bush> bushs = new ArrayList<>();
		for (int i = 0; i < 1000; i++) {
			bushs.add(new Bush(i, i));
		}
		for(Bush b : bushs) {
			b.updateLocation(5, 10);
			assertEquals(5, b.getX());
			assertEquals(10, b.getY());
		}
	}

}
