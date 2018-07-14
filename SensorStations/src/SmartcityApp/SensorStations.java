package SmartcityApp;

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
    private static int StationCount = 0;
    private ArrayList<Observer> observers = new ArrayList<Observer>();

    public SensorStations(int StationID, String Destination, int NoofActiveSensors, double Lattitude, double Longitude) {
        this.StationID = StationID;
        this.Destination = Destination;
        this.NoofActiveSensors = NoofActiveSensors;
        this.Lattitude = Lattitude;
        this.Longitude = Longitude;
    }

    public ArrayList<Observer> getObservers() {
        return observers;
    }

    public void setObservers(ArrayList<Observer> observers) {
        this.observers = observers;
    }
    
    public SensorStations(){
        allsensormonitors = new ArrayList();
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

    public void setLongitude(Float Longitude) {
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

//    public Object getUpdate(Observer mothership) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
   
}

