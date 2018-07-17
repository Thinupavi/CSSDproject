package sensor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observer;

public class SensorStations implements Serializable,Subject
{
    private int StationID;
    private String Destination;
    private int NoofActiveSensors;
    private double Lattitude;
    private double Longitude;
   // public Mothership observer;
    private ArrayList allsensormonitors;
    private SetOfSensors currentsensors = new SetOfSensors();
    private static int StationCount = 0;
    private ArrayList<Observer> observers = new ArrayList<Observer>();
    private static int NoofSensorMonitors = 0;
    
    public SensorStations() {

    }

    public SensorStations(int StationID, String Destination, int NoofActiveSensors, double Lattitude, double Longitude,SetOfSensors sSensors) {
        this.StationID = StationID;
        this.Destination = Destination;
        this.NoofActiveSensors = NoofActiveSensors;
        this.Lattitude = Lattitude;
        this.Longitude = Longitude;
         if (sSensors==null){
            currentsensors = new SetOfSensors();
        }else{
            currentsensors = sSensors;
        }
    }

    public SensorStations(int StationID, String Destination, int NoofActiveSensors, double Lattitude, double Longitude) {
        this.StationID = StationID;
        this.Destination = Destination;
        this.NoofActiveSensors = NoofActiveSensors;
        this.Lattitude = Lattitude;
        this.Longitude = Longitude;
     
    }
    public SensorStations(int StationID, String Destination, double Lattitude, double Longitude ) {
        this.StationID = StationID;
        this.Destination = Destination;
        this.Lattitude = Lattitude;
        this.Longitude = Longitude;
        
       
    }
    
    public boolean Addsensorsmonitor(Sensor sensors)
    {
        NoofSensorMonitors++;
        sensors.setSensormontior(this);
        currentsensors.addSensor(sensors);
        return true;
    }
    
    public boolean RemovesensorMontitor(Sensor sensors)
    {
        this.currentsensors.removeSensor(sensors);
        NoofSensorMonitors--;
        return true;
    }
    
    public SetOfSensors getSmonitors(){
        
        return currentsensors;
    }
    
    public ArrayList<Observer> getObservers() {
        return observers;
    }

    public void setObservers(ArrayList<Observer> observers) {
        this.observers = observers;
    }
    
//    public SensorStations(){
//        allsensormonitors = new ArrayList();
//    }

    public ArrayList getAllsensormonitors() {
        return allsensormonitors;
    }

    public void setAllsensormonitors(ArrayList allsensormonitors) {
        this.allsensormonitors = allsensormonitors;
    }
    
       
    public int getStationID() {
        return StationID;
    }

    public void setStationID(int StationID) {
        this.StationID = StationID;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String Destination) {
        this.Destination = Destination;
    }

    public int getNoofActiveSensors() {
        return NoofActiveSensors;
    }

    public void setNoofActiveSensors(int NoofActiveSensors) {
        this.NoofActiveSensors = NoofActiveSensors;
    }

    public double getLattitude() {
        return Lattitude;
    }

    public void setLattitude(double Lattitude) {
        this.Lattitude = Lattitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double Longitude) {
        this.Longitude = Longitude;
    }

  public String toString()
    {
         return StationID + " ~ " +Destination+ " ~ " +NoofActiveSensors+ " ~ " + Lattitude + " ~ " +Longitude;

    }

    public void registerObserver(Observer mothership) {
        observers.add(mothership);
    }

    public void removeObserver(Observer mothership) {
        int i = observers.indexOf(mothership);
        if(i>0)
        {
            observers.remove(mothership);
        }
    }

    public void notifyObservers() {
        for(int i = 0 ;i<observers.size();i++)
           {
               Observer observer = (Observer)observers.get(i);
//               observer.update (this);
           }       
    }
   
}

