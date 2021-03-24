package TempTestFolder;

import NanoGigaCleaner.Circle;
import NanoGigaCleaner.NanoGiga5000;
import org.junit.Test;
import utils.Vector2D;

import static org.junit.Assert.*;

public class NanoGigaBotTests {

    @Test
    public void collisionTestCrashLessThanOne(){
        Circle circle = new Circle(new Vector2D(11.0, 0.0));
        NanoGiga5000 nanoGiga5000 = new NanoGiga5000(new Vector2D(10.5, 0.0), new Vector2D(1.0, 0.0));
        assertTrue(nanoGiga5000.collision(circle));
    }

    @Test
    public void collisionTestCrashOne(){
        Circle circle = new Circle(new Vector2D(11.0, 0.0));
        NanoGiga5000 nanoGiga5000 = new NanoGiga5000(new Vector2D(10.0, 0.0), new Vector2D(1.0, 0.0));
        assertTrue(nanoGiga5000.collision(circle));
    }

    @Test
    public void collisionTestDontCrashMoreThanOne(){
        Circle circle = new Circle(new Vector2D(11.0, 0.0));
        NanoGiga5000 nanoGiga5000 = new NanoGiga5000(new Vector2D(0.0, 0.0), new Vector2D(1.0, 0.0));
        assertFalse(nanoGiga5000.collision(circle));
    }

    @Test
    public void collisionTestCrashAngled(){
        Circle circle = new Circle(new Vector2D(11.0, 0.0));
        NanoGiga5000 nanoGiga5000 = new NanoGiga5000(new Vector2D(11.0 - 0.70710678118, 0.0 + 0.70710678118), new Vector2D(0.70710678118, -0.70710678118));
        assertTrue(nanoGiga5000.collision(circle));
    }

    @Test
    public void collisionTestDontCrashAngled(){
        Circle circle = new Circle(new Vector2D(11.0, 0.0));
        NanoGiga5000 nanoGiga5000 = new NanoGiga5000(new Vector2D(10.0 - 0.70710678118, 0.0 + 0.70710678118), new Vector2D(0.70710678118, -0.70710678118));
        assertFalse(nanoGiga5000.collision(circle));
    }

    @Test
    public void timeTestOne(){
        Circle circle = new Circle(new Vector2D(11.0, 0.0));
        NanoGiga5000 nanoGiga5000 = new NanoGiga5000(new Vector2D(0.0, 0.0), new Vector2D(1.0, 0.0));
        assertEquals(nanoGiga5000.whenCollide(circle), 11, 0.0);
    }
}
