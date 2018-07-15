/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartcityapp;

import java.io.Serializable;
import java.util.Vector;

/**
 *
 * @author USER
 */
public class Mothership extends Vector<SensorStations>implements Serializable{
    
    public Mothership(){

        super();
    }
    
    public void addSensorStations(SensorStations sensorstations){
        
        super.add(sensorstations);
    }

    void removeSensorStations(SensorStations sensorstations) 
    {
        super.remove(sensorstations);
    }
    
    SensorStations getSensorStations(String ID)
    {
        for(int x = 0; x < super.size(); x++)
        {
            if (ID == super.get(x).getStationID())
            {
                return super.get(x);
            }
        }
        return null;
    }
    
    public Mothership getSensorStationsFromSearch(String SSID) {
        Mothership qResult = new Mothership();
        for(int x = 0; x < super.size(); x++)
        {
            if (SSID== (super.get(x).getStationID()))
            {
                qResult.addSensorStations(super.get(x));
            }
        }
        return qResult;
    }
}
