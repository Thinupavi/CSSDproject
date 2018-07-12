/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartcityapp;

/**
 *
 * @author USER
 */
public interface Observer {
    
    //method to update the observer, used by subject
    public void update (String Sensors,Float MoofSensors,Float Lattitude,Float Longitude);
    
    //attach with subject to observe
    public void setSubject(Subject sub);
    
}
