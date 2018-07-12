/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartcityapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observer;

/**
 *
 * @author USER
 */
public class SensorStations implements Subject,Serializable {
    
    private ArrayList observers;
    private ArrayList allsensormonitors;
    private String StationID;
    private String Address;
    private Float NoofSensors;
    private Float Lattitude;
    private Float Longitude;

    
     public SensorStations(String StationID, String Address, Float NoofSensors, Float Lattitude, Float Longitude) {
        this.StationID = StationID;
        this.Address = Address;
        this.NoofSensors = NoofSensors;
        this.Lattitude = Lattitude;
        this.Longitude = Longitude;
    }
     
    public SensorStations(){
        observers = new ArrayList();
        allsensormonitors = new ArrayList();
    }

    public String getStationID() {
        return StationID;
    }

    public void setStationID(String StationID) {
        this.StationID = StationID;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }
      
    
     public Float getNoofSensors(){
        return NoofSensors;
    }
    
    public void setNoofSensors(Float c){
        NoofSensors =  c;
    } 
    
    public Float getLattitude(){
        return Lattitude;
    }
    
    public void setLattitude(Float Sa){
        Lattitude =  Sa;
    }
    
    public Float getLongitude(){
        return Longitude;
    }
    
    public void setLongitude(Float f){
        Longitude =  f;
    } 
    
    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        int i = observers.indexOf(o);
        if(i>0)
        {
            observers.remove(o);
        }
    }

    @Override
    public void notifyObservers() {
           for(int i = 0 ;i<observers.size();i++)
           {
               Observer observer = (Observer)observers.get(i);
//               observer.update (this);
           }
    }
//    
//    public void measurementschanged(){
//        notifyObservers();
//    }
//    
    
    public String toString()
    {
        return StationID + " ~ " +Address + " ~ " + NoofSensors + " ~ " + Lattitude + " ~ " +Longitude;
    }
     
    public ArrayList<SensorStations>getState()
    {
        return allsensormonitors;
    }
    
    public void addSensorStations(SensorStations sensorstations){
        
        allsensormonitors.add(sensorstations);
    }

    @Override
    public Object getUpdate(Observer o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
