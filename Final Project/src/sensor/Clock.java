package sensor;

///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package admin;
//
import java.awt.List;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author LENOVO
 */
public class Clock {
   static Clock clock = new Clock();

   public ArrayList<Sensor> observers = null;
    
    public double notifyFrequency ;
    private int state;
     
     
    private Clock(){
        this.observers = new ArrayList<Sensor>();
    }
    
    private static Clock getInstance(){
        return clock;
    }
    
    

   public int getState() {
      return state;
   }

   public void setState(int state) {
      this.state = state;
      notifyAllObservers();
   }

   public void attach(Sensor observer){
      observers.add(observer);		
   }

   public void notifyAllObservers(){
      for (Sensor observer : observers) {
       //  observer.update();
      }
   } 	
    
}
