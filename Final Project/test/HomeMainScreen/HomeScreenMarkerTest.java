/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HomeMainScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.jxmapviewer.viewer.GeoPosition;
import librarysystem.SensorStationGUI;
import sensor.SensorStations;
import sensor.SetOfSensorStations;

/**
 *
 * @author HP
 */
public class HomeScreenMarkerTest {
    
    public HomeScreenMarkerTest() {
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
     * Test of getMarkers method, of class HomeScreen.
     */
    @Test
    public void testHomeMarkersData() throws Exception {
        HomeScreen instance = new HomeScreen();
        System.out.println("Creating an Instance\n");
        List<SwingWaypoint> expResult = new ArrayList<>();
        SensorStationGUI stationdata = new SensorStationGUI();
        SetOfSensorStations stations = stationdata.senddata();
        Vector datafromfile = stations;
        List<SensorStations> list =(List<SensorStations>) datafromfile.stream().collect(Collectors.toList());
        System.out.println("Getting Markers without using HomeScreen\n");
        for(int i = 0; i < list.size(); i++) {
            expResult.add(new SwingWaypoint(list.get(i).getDestination(), new GeoPosition(list.get(i).getLongitude(),list.get(i).getLattitude()),list.get(i).getNoofActiveSensors()));
        }
        List<SwingWaypoint> result = instance.getMarkers();
        System.out.println("Getting Markers from HomeScreen instance\n");
        System.out.println("Comparing Results...");
        if(expResult.size() == result.size()){
            System.out.println("Test Passed !..");
        }else{
            System.out.println("Test Failed !..");
        }
        assertEquals(expResult.size(), result.size());
    }

    
}
