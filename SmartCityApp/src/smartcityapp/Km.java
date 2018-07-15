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
public class Km implements Observer  {
    
    String ID;
    
    public Km(String ID)
    {
        this.ID = ID;
    }

    @Override
    public void update(String Sensors,Float MoofSensors,Float Lattitude,Float Longitude) {
        
    }

    @Override
    public void setSubject(Subject sub) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
