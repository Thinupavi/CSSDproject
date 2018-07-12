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

/**
 *
 * @author Dila
 */

import java.io.*;
public class Sensor_Monitor {
    private static final String FILENAME="C:\\Users\\Dila\\Desktop\\TESTSENSOR\\ADD.ser";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //CreateObjects();
    //LoadObjects();
    }
    private static void CreateObjects()
    {
      ADDclass a = new ADDclass();
     
    }
    private static void LoadObjects()
    {
      try
        {
            Deserialize(FILENAME);
      
            Deserialize("test_"+FILENAME);
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
        catch (ClassNotFoundException e)
        {
            System.out.println(e);
        }
    }
    private static void Serialize(Object obj1, Object obj2, Object obj3, String filename) throws IOException
  {
    FileOutputStream out=new FileOutputStream(filename);
    ObjectOutputStream oos=new ObjectOutputStream(out);
    oos.writeObject(obj1);
    oos.writeObject(obj2);
    oos.writeObject(obj3);
    out.close();
  }
   private static void Deserialize(String filename) throws IOException, ClassNotFoundException
  {
    FileInputStream in=new FileInputStream(filename);
    ObjectInputStream ois=new ObjectInputStream(in);
    Object obj1=ois.readObject();
    Object obj2=ois.readObject();
    Object obj3=ois.readObject();
    in.close();
    
    System.out.println("\nDeserialising...");
    System.out.println(obj1);
    System.out.println(obj2);
    System.out.println(obj3);
  }
}
