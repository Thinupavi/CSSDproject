/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sensor.monitor;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JOptionPane;
/**
 *
 * @author Dila
 */
public class ADDclass implements Serializable {
    private Sensor[] Sensor;
    private static ADDclass addclass;
    
    private ArrayList<Sensor> allSensor = new ArrayList<Sensor>();
    
    private static SetOfSensors SS=new SetOfSensors();
    
    
    
    public static void main(String args[])throws Exception{  
        
    }  
    public int GetNoOfSensor()
    {
        return (Sensor==null?0:Sensor.length);
    }
  
    public Sensor GetSensor(int index)
    {
        return Sensor[index];
    }
  
    public void Sensor()
    {
        this.allSensor = new ArrayList<Sensor>();
    }
    public boolean add(Sensor sensor) {
        if (sensor != null && !sensor.equals("")) {
            throw new IllegalArgumentException("Can't be empty");
        }
        allSensor.add(sensor);
        return true;
    }
    public ArrayList<Sensor> findTitles(String title) {
        for(Sensor s: allSensor) {
            if(title.compareTo(s.getName())== 0) {
                return allSensor;
            }
        }
        return null;
    }
    public void AddSensor(Sensor s)
    {
        if (s!=null && !FindSensor(s))
        {
        int len=GetNoOfSensor();
        Sensor[] tmp=new Sensor[len+1];
        for (int i=0; i<len; i++)
        tmp[i]=Sensor[i];
        Sensor=tmp;
        Sensor[len]=s;
           
        }
    }
   

    public boolean FindSensor(Sensor p)
    {
        if (p==null)
            return false;
        else
        {
            for (int i=GetNoOfSensor()-1; i>=0; i--)
                if (Sensor[i]==p)
                    return true;
                return false;
        }
    }
  
  
    public String toString()
    {
        StringBuilder res=new StringBuilder();
    
        int val=GetNoOfSensor();
        res.append(val);
        res.append(" Sensors");
        for (int i=0; i<val; i++)
        {
            res.append("\n  ");
            res.append(Sensor[i]);
        }
    
        return res.toString();
      }
    
    
}

