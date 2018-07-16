package sensor;

import java.io.Serializable;

public class Sensor implements Serializable
{
    private String name;
    private int sensorNumber;
    private String status;
    private String frequency;
    private SensorStations Sensormontior = null;
    private static int sensorCount = 0;
    
    public Sensor() {

    }
    
    public Sensor(Integer senID, String aName,String astatus,String afrequency,SensorStations SStation)
    {
        sensorNumber = senID;
        name = aName;
        status = astatus;
        frequency = afrequency;
        this.Sensormontior =SStation;
    }

    public Sensor(int sensorNumber,String name,String astatus) {
        this.name = name;
        this.sensorNumber = sensorNumber;
        status = astatus;
    }

    
    public SensorStations getSensormontior() {
        return Sensormontior;
    }

    public void setSensormontior(SensorStations theSensormontior) {
        Sensormontior = theSensormontior;
    }
    
    
    public String toString()
    {
        return Integer.toString(sensorNumber) + " ~ " + name + " ~ " + status + " ~ " + frequency;
    }
 
   
    public String getName(){
        return name;
    }
    
    public void setName(String aName){
        name = aName;
    }
    public String getStatus(){
        return status;
    }
    
    public void setStatus(String astatus){
        status = astatus;
    }
    public String getFrequency(){
        return frequency;
    }
    
    public void setFrequency(String afrequency){
        frequency = afrequency;
    }
   
    public int getSensorNumber(){
        return sensorNumber;
    }
    public boolean isOnLoan(){
        
        return (Sensormontior!=null);
    }
}

