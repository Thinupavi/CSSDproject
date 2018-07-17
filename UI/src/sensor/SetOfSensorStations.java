package sensor;

import java.io.Serializable;
import java.util.Vector;

public class SetOfSensorStations extends Vector<SensorStations> implements Serializable,Observer
{
    public SetOfSensorStations(){

        super();
    }
    
    public void addSensorStations(SensorStations sensorstations){
        
        super.add(sensorstations);
    }

    void removeSensorStations(SensorStations sensorstations) 
    {
        super.remove(sensorstations);
    }
    
    SensorStations getSensorStations(int ID)
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
    
    public SetOfSensorStations getSensorStationsFromSearch(int SSID) {
        SetOfSensorStations qResult = new SetOfSensorStations();
        for(int x = 0; x < super.size(); x++)
        {
            if (SSID== (super.get(x).getStationID()))
            {
                qResult.addSensorStations(super.get(x));
            }
        }
        return qResult;
    }
    
    SensorStations getSensorStations(String destination)
    {
        for(int x = 0; x < super.size(); x++)
        {
            if (destination == super.get(x).getDestination())
            {
                return super.get(x);
            }
        }
        return null;
    }
    
    public SetOfSensorStations getSensorStationsnameFromSearch(String destination) {
        SetOfSensorStations qResult = new SetOfSensorStations();
        for(int x = 0; x < super.size(); x++)
        {
            if (destination == (super.get(x).getDestination()))
            {
                qResult.addSensorStations(super.get(x));
            }
        }
        return qResult;
    }

  

    public void setSubject(Subject sub) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void update(int Sensors, String Stationname, int NoofSensors, double Lattitude, double Longitude) {
       
    }
    
}