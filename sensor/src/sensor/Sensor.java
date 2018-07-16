package sensor;

import java.io.Serializable;

public class Sensor implements Serializable
{
    private String name;
    private int sensorNumber;
    private String status;
    private String frequency;
    private static int sensorCount = 0;
    
    public Sensor(Integer senID, String aName,String astatus,String afrequency)
    {
        name = aName;
        sensorNumber = senID;
        status = astatus;
        frequency = afrequency;
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
}

