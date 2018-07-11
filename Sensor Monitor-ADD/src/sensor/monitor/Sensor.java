/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sensor.monitor;

/**
 *
 * @author Dila
 */
import java.io.Serializable;
 import java.util.ArrayList;
/**
 *
 * @author Dila
 */
public class Sensor implements Serializable{
    
    public String name;
    public String status;
    public String frequency;
    private int count;
        
    public Sensor(String name,String status,String frequency){
        
        this.name=name;
        this.status=status;
        this.frequency=frequency;
    }
    
    public String getStatus(){
        return this.status;
    }
    
    public void setStatus(String status){
        this.status=status;
    }
    
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name=name;
    }
    
    public String getFrequency()
    {
        return frequency;
    }

    public void setFrequency(String frequency)
    {
        this.frequency=frequency;
    }
    
    public String toString(){
        
        String str="Sensor name:"+name+"/n Status:"+status+"/nFrequency:"+frequency;
 
        return str;
    }
}

