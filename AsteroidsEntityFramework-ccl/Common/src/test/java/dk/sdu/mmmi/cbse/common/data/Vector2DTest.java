/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ccl
 */
public class Vector2DTest {
    
    private final static float X = 30.22f;
    private final static float Y = 122.22f;
    
    public Vector2DTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    public static Vector2D getTestVector() {
        return new Vector2D(Vector2DTest.X, Vector2DTest.Y);
    }

    /**
     * Test of getX method, of class Vector2D.
     */
    @Test
    public void testGetX() {
        System.out.println("getX");
        Vector2D instance = getTestVector();
        float expResult = Vector2DTest.X;
        float result = instance.getX();
        assertEquals(expResult, result, 0.0f);
    }

    /**
     * Test of getY method, of class Vector2D.
     */
    @Test
    public void testGetY() {
        System.out.println("getY");
        Vector2D instance = getTestVector();
        float expResult = Vector2DTest.Y;
        float result = instance.getY();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of setX method, of class Vector2D.
     */
    @Test
    public void testSetX() {
        System.out.println("setX");
        float x = 0.0F;
        Vector2D instance = getTestVector();
        instance.setX(x);
        
        float result = instance.getX();
        
        assertEquals(0.0f, result, 0.0);
    }

    /**
     * Test of setY method, of class Vector2D.
     */
    @Test
    public void testSetY() {
        System.out.println("setY");
        float y = 0.0F;
        Vector2D instance = getTestVector();
        instance.setY(y);
        
        float result = instance.getY();
        
        assertEquals(0.0f, result, 0.0);
    }

    /**
     * Test of rotate90degrees method, of class Vector2D.
     */
    @Test
    public void testRotate90degrees() {
        System.out.println("rotate90degrees");
        Vector2D instance = getTestVector();
        Vector2D expResult = new Vector2D(-getTestVector().getY(), getTestVector().getX());
        Vector2D result = instance.rotate90degrees();
        assertEquals(expResult, result);
    }

    /**
     * Test of length method, of class Vector2D.
     */
    @Test
    public void testLength() {
        System.out.println("length");
        Vector2D instance = getTestVector();
        float expResult = (float)Math.sqrt(getTestVector().getX()*getTestVector().getX()+getTestVector().getY()*getTestVector().getY());
        float result = instance.length();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of unitVector method, of class Vector2D.
     */
    @Test
    public void testUnitVector() {
        System.out.println("unitVector");
        Vector2D instance = getTestVector();
        
        float expResult = 1.0f;
        
        Vector2D vect = instance.unitVector();
        float result = vect.length();
        
        assertEquals(expResult, result, 0.0f);
    }

    /**
     * Test of projectOnto method, of class Vector2D.
     */
    @Test
    public void testProjectOnto() {
        System.out.println("projectOnto");
        Vector2D other = new Vector2D(5.123f, 10.123f);
        Vector2D instance = getTestVector();
        float factor = (instance.getX()*other.getX() + instance.getY()*other.getY())/(other.length()*other.length());
        Vector2D expResult = new Vector2D(5.123f*factor, 10.123f*factor);
        Vector2D result = instance.projectOnto(other);
        assertEquals(expResult, result);
    }

    /**
     * Test of dot method, of class Vector2D.
     */
    @Test
    public void testDot() {
        System.out.println("dot");
        Vector2D a = new Vector2D(11.11f, 12.12f);
        Vector2D b = new Vector2D(55.55f, 65.65f);;
        float expResult = 1412.8385F;
        float result = Vector2D.dot(a, b);
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of getVectors method, of class Vector2D.
     */
    @Test
    public void testGetVectors() {
        System.out.println("getVectors");
        float[] shapex = new float[] {1.0f, 3.0f, 3.0f, 0.0f};
        float[] shapey = new float[] {5.0f, 2.0f, 1.0f, 1.0f};
        List<Vector2D> expResult = new ArrayList<>();
        expResult.add(new Vector2D(1.0f, 4.0f));
        expResult.add(new Vector2D(2.0f, -3.0f));
        expResult.add(new Vector2D(0.0f, -1.0f));
        expResult.add(new Vector2D(-3.0f, 0.0f));
        
        List<Vector2D> result = Vector2D.getVectors(shapex, shapey);
        assertEquals(expResult, result);
    }

    /**
     * Test of getOriginVectors method, of class Vector2D.
     */
    @Test
    public void testGetOriginVectors() {
        System.out.println("getOriginVectors");
        float[] shapex = new float[] {1.0f, 3.0f, 3.0f, 0.0f};
        float[] shapey = new float[] {5.0f, 2.0f, 1.0f, 1.0f};
        List<Vector2D> expResult = new ArrayList<>();
        expResult.add(new Vector2D(1.0f, 5.0f));
        expResult.add(new Vector2D(3.0f, 2.0f));
        expResult.add(new Vector2D(3.0f, 1.0f));
        expResult.add(new Vector2D(0.0f, 1.0f));
        List<Vector2D> result = Vector2D.getOriginVectors(shapex, shapey);
        assertEquals(expResult, result);
    }

    /**
     * Test of getNormals method, of class Vector2D.
     */
    @Test
    public void testGetNormals() {
        System.out.println("getNormals");
        List<Vector2D> vectors = new ArrayList<>();
        vectors.add(new Vector2D(1.0f, 4.0f));
        vectors.add(new Vector2D(2.0f, -3.0f));
        vectors.add(new Vector2D(0.0f, -1.0f));
        vectors.add(new Vector2D(-3.0f, 0.0f));
        
        List<Vector2D> expResult = new ArrayList<>();
        expResult.add(new Vector2D(-4.0f, 1.0f));
        expResult.add(new Vector2D(3.0f, 2.0f));
        expResult.add(new Vector2D(1.0f, 0.0f));
        expResult.add(new Vector2D(0.0f, -3.0f));
        
        List<Vector2D> result = Vector2D.getNormals(vectors);
        assertEquals(expResult, result);
    }

    /**
     * Test of projectAndGetMinMax method, of class Vector2D.
     */
    @Test
    public void testProjectAndGetMinMax() {
        System.out.println("projectAndGetMinMax");
        
        Vector2D normal = new Vector2D(-4, 1);
        List<Vector2D> shapeVectors = new ArrayList<>();
        shapeVectors.add(new Vector2D(1, 5));
        shapeVectors.add(new Vector2D(3, 2));
        shapeVectors.add(new Vector2D(3, 1));
        shapeVectors.add(new Vector2D(0, 1));
        
        Point[] expResult = new Point[]{ new Point(-0.23529412f, 0.05882353f), new Point(2.5882354f, -0.64705884f) };
        Point[] result = Vector2D.projectAndGetMinMax(normal, shapeVectors);
        
        assertArrayEquals(expResult, result);
    }
    
}
