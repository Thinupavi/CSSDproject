/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SmartcityApp;

import java.util.ArrayList;
import java.util.Observer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author USER
 */
public class SensorStationsIT {
    
    public SensorStationsIT() {
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

    /**
     * Test of getObservers method, of class SensorStations.
     */
  
    @Test
    public void testGetStationID() {
        System.out.println("getStationID");
        SensorStations instance = new SensorStations(1,"Station_Name1",20,79.098,6.789);
        int expResult = 1;
        int result = instance.getStationID();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }



    /**
     * Test of getDestination method, of class SensorStations.
     */
    @Test
    public void testGetDestination() {
        System.out.println("getDestination");
        SensorStations instance = new SensorStations(1,"Station_Name1",20,79.098,6.789);
        String expResult = "Station_Name1";
        String result = instance.getDestination();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNoofActiveSensors method, of class SensorStations.
     */
    @Test
    public void testGetNoofActiveSensors() {
        System.out.println("getNoofActiveSensors");
        SensorStations instance = new SensorStations();
        int expResult = 20;
        int result = instance.getNoofActiveSensors();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }



    /**
     * Test of getLattitude method, of class SensorStations.
     */
    @Test
    public void testGetLattitude() {
        System.out.println("getLattitude");
        SensorStations instance = new SensorStations();
        double expResult = 6.9870;
        double result = instance.getLattitude();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }


    /**
     * Test of getLongitude method, of class SensorStations.
     */
    @Test
    public void testGetLongitude() {
        System.out.println("getLongitude");
        SensorStations instance = new SensorStations();
        double expResult = 79.567;
        double result = instance.getLongitude();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
