/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import AdminInterface.adminClass;
import AdminInterface.setOfAdmins;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author Lenovo
 */
public class setOfAdminsTest {
    private static adminClass[] admins;
    
    private setOfAdmins instance;
    
    public setOfAdminsTest() {
    }
    
    @BeforeClass
    public static void setUpBeforeClass() {
        // We include this initialisation as we expect the sensor array not
        // to be changed as part of any of the tests - if we intended to modify
        // it as part of the test cases then it belongs in setUp()
        admins = new adminClass[3];
       
        admins[0] = new adminClass(1,"Hasanthika Lakshani","071-6399726","Malabe","lakshani","123"); 
        admins[1] = new adminClass(2,"Thinusha Premashanker","077-145096","Australia","thinupavi","1234"); 
        admins[2] = new adminClass(3,"Mudara Bandaranayke","071-4528909","Maharagama","mudara","12345"); 
    }

    @Before
    public void setUp() {
        instance = new setOfAdmins();
        for(int i = 0; i < admins.length; ++i) {
            instance.addAdmin(admins[i]);
        }
    }
    
    @After
    public void tearDown() {
    }

    @Test 
    public void testFindSensorByName() {
         // For those interested: This test can be made more concise by using a so-called
         // "parameterised test class"
         setOfAdmins result;
         
         // Exact match
         result = instance.findAdminByName("Hasanthika Lakshani");
         assertEquals(1, result.size());
         assertEquals(admins[0], result.get(0));
         
         // Exact match
         result = instance.findAdminByName("Thinusha Premashanker");
         assertEquals(1, result.size());
         assertEquals(admins[1], result.get(0));

         // Exact match
         result = instance.findAdminByName("Mudara Bandaranayke");
         assertEquals(1, result.size());
         assertEquals(admins[2], result.get(0));
         
         // Unknown Sensor
         result = instance.findAdminByName("ABC");
         assertEquals(0, result.size());
        
    }
    
    @Test(expected=NullPointerException.class)
    public void testFindSensorByAuthor_Null() {
        setOfAdmins result;
        result = instance.findAdminByName(null);
        // OR: should it rather be the case that we return an empty set?
        assertEquals(0, result.size()); // this won't be reached due to exception
    }   
   
}
