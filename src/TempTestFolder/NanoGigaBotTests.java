package TempTestFolder;

import NanoGigaCleaner.Circle;
import NanoGigaCleaner.ISofaClubObject;
import NanoGigaCleaner.NanoGiga5000;
import org.junit.Test;
import utils.Pair;
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
        assertEquals(nanoGiga5000.whenCollide(circle).getA(), 10.0, 0.0);
    }

    @Test
    public void timeTestExpectedCrashPosition(){
        Circle circle = new Circle(new Vector2D(11.0, 0.0));
        NanoGiga5000 nanoGiga5000 = new NanoGiga5000(new Vector2D(0.0, 0.0), new Vector2D(1.0, 0.0));
        assertEquals(nanoGiga5000.whenCollide(circle).getB().x(), 10.0, 0.0);
    }

    @Test
    public void timeTestExpectedCrashPositionNegativeValues(){
        Circle circle = new Circle(new Vector2D(0.0, 0.0));
        NanoGiga5000 nanoGiga5000 = new NanoGiga5000(new Vector2D(11.0, 0.0), new Vector2D(-1.0, 0.0));
        assertEquals(nanoGiga5000.whenCollide(circle).getB().x(), 1.0, 0.0);
    }

    @Test
    public void changeAngle90Test(){
        NanoGiga5000 nanoGiga5000 = new NanoGiga5000(new Vector2D(0.0, 0.0), new Vector2D(1.0, 0.0));
        nanoGiga5000.changeDir(Math.PI/2.0);
        assertEquals(nanoGiga5000.getVectorVelocity().y(), 1, 0.00001);
        assertEquals(nanoGiga5000.getVectorVelocity().x(), 0, 0.00001);
    }

    @Test
    public void testDoAction(){
        ISofaClubObject[] circles = setUpSimpleCircles();
        NanoGiga5000 nanoGiga5000 = new NanoGiga5000(new Vector2D(2, 2), new Vector2D(0, 1), circles);
        Pair<Double, Vector2D> p = nanoGiga5000.doRandomAction();
        assertEquals(p.getA(), 7.0, 0.00001);
    }

    @Test
    public void finishedTest(){
        ISofaClubObject[] circles = setUpSimpleCircles();
        NanoGiga5000 nanoGiga5000 = new NanoGiga5000(new Vector2D(11, 9.5), new Vector2D(0, 1), circles);
        Pair<Double, Vector2D> p = nanoGiga5000.doRandomAction();
        assertNull(p);
    }

    @Test
    public void changeDirIfCollided(){
        ISofaClubObject[] circles = setUpSimpleCircles();
        NanoGiga5000 nanoGiga5000 = new NanoGiga5000(new Vector2D(2, 2), new Vector2D(0, 1), circles);
        Vector2D prevVelDir = nanoGiga5000.getVectorVelocity();
        Pair<Double, Vector2D> p = nanoGiga5000.doRandomAction();
        assertEquals(p.getA(), 7.0, 0.00001);
        Vector2D newVelDir = nanoGiga5000.getVectorVelocity();
        //Might fail if random chooses same dir somehow
        assertNotEquals(prevVelDir, newVelDir);
        assertNotEquals(prevVelDir.x(), newVelDir.x());
        assertNotEquals(prevVelDir.y(), newVelDir.y());
        System.out.println("Change direction test: ");
        System.out.println("Old x: " + prevVelDir.x() + " new x: " + newVelDir.x());
        System.out.println("Old y: " + prevVelDir.y() + " new y: " + newVelDir.y());
    }


    @Test
    public void changeDirVelocityEqualToOne(){
        ISofaClubObject[] circles = setUpSimpleCircles();
        NanoGiga5000 nanoGiga5000 = new NanoGiga5000(new Vector2D(2, 2), new Vector2D(0, 1), circles);
        Vector2D prevVelDir = nanoGiga5000.getVectorVelocity();
        Pair<Double, Vector2D> p = nanoGiga5000.doRandomAction();
        assertEquals(p.getA(), 7.0, 0.00001);
        Vector2D newVelDir = nanoGiga5000.getVectorVelocity();
        //Might fail if random chooses same dir somehow
        assertNotEquals(prevVelDir, newVelDir);
        assertNotEquals(prevVelDir.x(), newVelDir.x());
        assertNotEquals(prevVelDir.y(), newVelDir.y());
        double totalVelocity = Math.pow(nanoGiga5000.getVectorVelocity().x(), 2) + Math.pow(nanoGiga5000.getVectorVelocity().y(), 2);
        assertEquals(totalVelocity, 1.0, 0.0001);
    }

    @Test
    public void boundariesTest(){
        ISofaClubObject[] circles = setUpSimpleCircles();
        NanoGiga5000 nanoGiga5000 = new NanoGiga5000(new Vector2D(2, 2), new Vector2D(0, -1), circles);
        Pair<Double, Vector2D> p = nanoGiga5000.doRandomAction();
        assertEquals(p.getA(), 2.0, 0.0);
        assertEquals(p.getB().y(), 0.0, 0.0);
    }


    private ISofaClubObject[] setUpSimpleCircles() {
        ISofaClubObject[] circles = new ISofaClubObject[5];
        circles[0] = new Circle(new Vector2D(11, 11));
        circles[1] = new Circle(new Vector2D(5, 5));
        circles[2] = new Circle(new Vector2D(11, 2));
        circles[3] = new Circle(new Vector2D(11, 3));
        circles[4] = new Circle(new Vector2D(2, 9));
        return circles;
    }
}
