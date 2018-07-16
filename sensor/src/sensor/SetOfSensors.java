package sensor;

import java.io.Serializable;
import java.util.Vector;

public class SetOfSensors extends Vector<Sensor> implements Serializable
{
    void addSensor(Sensor aSensor) 
    {
        super.add(aSensor);
    }
    
    Sensor getSensorFromName(String aSensor) 
    {
        for(int x = 0; x < super.size(); x++)
        {
            if (super.get(x).getName().contains(aSensor))
            {
                return super.get(x);
            }
        }
        return null;
    }
    
    Sensor getSensorFromStatus(String aSensor) 
    {
        for(int x = 0; x < super.size(); x++)
        {
            if (super.get(x).getStatus().contains(aSensor))
            {
                return super.get(x);
            }
        }
        return null;
    }
    Sensor getSensorFromFrequency(String aSensor) 
    {
        for(int x = 0; x < super.size(); x++)
        {
            if (super.get(x).getFrequency().contains(aSensor))
            {
                return super.get(x);
            }
        }
        return null;
    }
    Sensor getSensorFromNumber(int aSensor) 
    {
        for(int x = 0; x < super.size(); x++)
        {
            if (aSensor == super.get(x).getSensorNumber())
            {
                return super.get(x);
            }
        }
        return null;
    }
    
    public SetOfSensors getSensorsFromSearch(String sensName){
        SetOfSensors qResult = new SetOfSensors();
        for(int x = 0; x < super.size(); x++)
        {
            if (super.get(x).getName().toUpperCase().contains(
                    sensName.toUpperCase()))
            {
                qResult.addSensor(super.get(x));
            }
        }
        return qResult;
    }
    
    public SetOfSensors getSensorsFromSearch(int sensID) {
        SetOfSensors qResult = new SetOfSensors();
        for(int x = 0; x < super.size(); x++)
        {
            if (sensID == (super.get(x).getSensorNumber()))
            {
                qResult.addSensor(super.get(x));
            }
        }
        return qResult;
    }

    void removeSensor(Sensor aSensor) 
    {
        super.remove(aSensor);
    }
}