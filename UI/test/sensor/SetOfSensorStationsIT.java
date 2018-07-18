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
public class SetOfSensorStationsIT {
    
    private static SensorStations[] sensorstations;
    private SetOfSensorStations instance;
    
    public SetOfSensorStationsIT() {
    }
    
    @BeforeClass
    public static void setUpBeforeClass() {
        
        sensorstations = new SensorStations[3];
        
        sensorstations[0] = new SensorStations(1,"Colombo01",2,79.087,6.908);
        sensorstations[1] = new SensorStations(2,"Colombo02",3,69.087,7.908);
        sensorstations[2] = new SensorStations(3,"Colombo03",4,89.087,8.908);
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        instance = new SetOfSensorStations();
        for(int i = 0;i<sensorstations.length;++i){
           instance.addSensorStations(sensorstations[i]);
        }
    }
    
    @After
    public void tearDown() {
    }
    /**
     * Test of getSensorStationsFromSearch method, of class SetOfSensorStations.
     */
    @Test
    public void testGetSensorStationsFromSearch() {
        
           SetOfSensorStations result;
         
         // Exact match
         result = instance.getSensorStationsFromSearch(1);
         assertEquals(1, result.size());
         assertEquals(sensorstations[0], result.get(0));
         
         // Exact match
         result = instance.getSensorStationsFromSearch(2);
         assertEquals(1, result.size());
         assertEquals(sensorstations[1], result.get(0));

         // Exact match
         result = instance.getSensorStationsFromSearch(3);
         assertEquals(1, result.size());
         assertEquals(sensorstations[2], result.get(0));
         
         // Unknown SensorStations
         result = instance.getSensorStationsFromSearch(4);
         assertEquals(0, result.size());
        

    }

    /**
     * Test of getSensorStationsnameFromSearch method, of class SetOfSensorStations.
     */
    @Test
    public void testGetSensorStationsnameFromSearch() {
        
        SetOfSensorStations result;
        // Exact match
         result = instance.getSensorStationsnameFromSearch("Colombo01");
         assertEquals(1, result.size());
         assertEquals(sensorstations[0], result.get(0));
         
         // Exact match
         result = instance.getSensorStationsnameFromSearch("Colombo02");
         assertEquals(1, result.size());
         assertEquals(sensorstations[1], result.get(0));

         // Exact match
         result = instance.getSensorStationsnameFromSearch("Colombo03");
         assertEquals(1, result.size());
         assertEquals(sensorstations[2], result.get(0));
         
         // Unknown SensorStations
         result = instance.getSensorStationsnameFromSearch("Colombo06");
         assertEquals(0, result.size());
        
    }

    @Test(expected=NullPointerException.class)
    public void testGetSensorStationsnameFromSearch_Null() {

        SetOfSensorStations result;
        result = instance.getSensorStationsnameFromSearch(null);
        assertEquals(0, result.size());
        // TODO review the generated test code and remove the default call to fail.
       // fail("The test case is a prototype.");
    }
       
}
