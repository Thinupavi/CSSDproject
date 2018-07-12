/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sensor.monitor;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Dila
 */
public class SetOfSensors extends ArrayList<Sensor> implements Serializable {


public SetOfSensors(){

        super();
    }
    
    public void addSensor(Sensor sensor){
        
        super.add(sensor);
    }

}
