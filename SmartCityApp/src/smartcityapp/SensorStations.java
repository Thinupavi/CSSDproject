/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartcityapp;

import java.util.ArrayList;
import java.util.Observer;

/**
 *
 * @author USER
 */
public class SensorStations implements Subject {
    
    private ArrayList observers;
    private String SensorDescription;
    private Float CurrentData;
    private String Status;
    private Float Frequency;
    
    
    public void SetSensorStations(String SensorDescription,Float CurrentData,String Status,Float Frequency)
    {
        this.SensorDescription = SensorDescription;
        this.CurrentData = CurrentData;
        this.Status = Status;
        this.Frequency = Frequency;
        measurementschanged();
    }

    public SensorStations(){
        observers = new ArrayList();
    }
    public String getSensorDescription(){
        return SensorDescription;
    }
    
    public void setSensorDescription(String s){
        SensorDescription =  s;
    }
    
     public Float getCurrentData(){
        return CurrentData;
    }
    
    public void setCurrentData(Float c){
        CurrentData =  c;
    } 
    
    public String getStatus(){
        return Status;
    }
    
    public void setStatus(String Sa){
        Status =  Sa;
    }
    
    public Float getFrequency(){
        return Frequency;
    }
    
    public void setFrequency(Float f){
        Frequency =  f;
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
               observer.update(SensorDescription,CurrentData,Status,Frequency);
           }
    }
    
    public void measurementschanged(){
        notifyObservers();
    }
    
}
