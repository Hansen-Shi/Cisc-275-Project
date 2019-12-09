package RunOffTestPkg;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Game_Pkg.OilBlob;

class OilTest {
	OilBlob oil = new OilBlob(100, 100);
	@Test
	void test() {
		fail("Not yet implemented");
	}
	void updateTest() {
		int x = oil.getX();
		int y = oil.getY();
		int speed = oil.getSpeed();
		oil.updateBlob();
		int newX = oil.getX();
		assertEquals(x + speed, newX);
	}
}
