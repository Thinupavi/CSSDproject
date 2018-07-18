/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sensor;

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
public class SetOfSensorsTest {
    
    public SetOfSensorsTest() {
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
     * Test of addSensor method, of class SetOfSensors.
     */
    @Test
    public void testAddSensor() {
        System.out.println("addSensor");
        Sensor aSensor = null;
        SetOfSensors instance = new SetOfSensors();
        instance.addSensor(aSensor);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSensorFromName method, of class SetOfSensors.
     */
    @Test
    public void testGetSensorFromName() {
        System.out.println("getSensorFromName");
        String aSensor = "";
        SetOfSensors instance = new SetOfSensors();
        Sensor expResult = null;
        Sensor result = instance.getSensorFromName(aSensor);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSensorFromStatus method, of class SetOfSensors.
     */
    @Test
    public void testGetSensorFromStatus() {
        System.out.println("getSensorFromStatus");
        String aSensor = "";
        SetOfSensors instance = new SetOfSensors();
        Sensor expResult = null;
        Sensor result = instance.getSensorFromStatus(aSensor);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSensorFromFrequency method, of class SetOfSensors.
     */
    @Test
    public void testGetSensorFromFrequency() {
        System.out.println("getSensorFromFrequency");
        String aSensor = "";
        SetOfSensors instance = new SetOfSensors();
        Sensor expResult = null;
        Sensor result = instance.getSensorFromFrequency(aSensor);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSensorFromNumber method, of class SetOfSensors.
     */
    @Test
    public void testGetSensorFromNumber() {
        System.out.println("getSensorFromNumber");
        int aSensor = 0;
        SetOfSensors instance = new SetOfSensors();
        Sensor expResult = null;
        Sensor result = instance.getSensorFromNumber(aSensor);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSensorsFromSearch method, of class SetOfSensors.
     */
    @Test
    public void testGetSensorsFromSearch_String() {
        System.out.println("getSensorsFromSearch");
        String sensName = "";
        SetOfSensors instance = new SetOfSensors();
        SetOfSensors expResult = null;
        SetOfSensors result = instance.getSensorsFromSearch(sensName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSensorsFromSearch method, of class SetOfSensors.
     */
    @Test
    public void testGetSensorsFromSearch_int() {
        System.out.println("getSensorsFromSearch");
        int sensID = 0;
        SetOfSensors instance = new SetOfSensors();
        SetOfSensors expResult = null;
        SetOfSensors result = instance.getSensorsFromSearch(sensID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeSensor method, of class SetOfSensors.
     */
    @Test
    public void testRemoveSensor() {
        System.out.println("removeSensor");
        Sensor aSensor = null;
        SetOfSensors instance = new SetOfSensors();
        instance.removeSensor(aSensor);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
