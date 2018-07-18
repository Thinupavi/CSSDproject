/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sensor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author Dila
 */
public class SetOfSensorsTest {
    private static Sensor[] sensors;
    
    private SetOfSensors instance;
    
    public SetOfSensorsTest() {
    }
    
    @BeforeClass
    public static void setUpBeforeClass() {
        // We include this initialisation as we expect the sensor array not
        // to be changed as part of any of the tests - if we intended to modify
        // it as part of the test cases then it belongs in setUp()
        sensors = new Sensor[3];
       
        sensors[0] = new Sensor(1,"Sensor1", "Active", "10",null);
        sensors[1] = new Sensor(2,"Sensor2", "Not-Active", "11",null);
        sensors[2] = new Sensor(3,"Sensor3", "Active","12",null);
    }

    @Before
    public void setUp() {
        instance = new SetOfSensors();
        for(int i = 0; i < sensors.length; ++i) {
            instance.addSensor(sensors[i]);
        }
    }
    
    @After
    public void tearDown() {
    }

    @Test 
    public void testFindSensorByName() {
         // For those interested: This test can be made more concise by using a so-called
         // "parameterised test class"
         SetOfSensors result;
         
         // Exact match
         result = instance.findSensorByName("Sensor1");
         assertEquals(1, result.size());
         assertEquals(sensors[0], result.get(0));
         
         // Exact match
         result = instance.findSensorByName("Sensor2");
         assertEquals(1, result.size());
         assertEquals(sensors[1], result.get(0));

         // Exact match
         result = instance.findSensorByName("Sensor3");
         assertEquals(1, result.size());
         assertEquals(sensors[2], result.get(0));
         
         // Unknown Sensor
         result = instance.findSensorByName("Sensor6");
         assertEquals(0, result.size());
        
    }
    
    @Test(expected=NullPointerException.class)
    public void testFindSensorByAuthor_Null() {
        SetOfSensors result;
        result = instance.findSensorByName(null);
        // OR: should it rather be the case that we return an empty set?
        assertEquals(0, result.size()); // this won't be reached due to exception
    }   
   
}
