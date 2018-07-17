/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sensor;

import java.io.Serializable;

/**
 *
 * @author LENOVO
 */
public class adminClass implements Serializable {
  
    private int adminID;
    private String name;
    private String phoneNo;
    private String address;
    private String username;
    private String password;
    
    
    public adminClass(int id,String name,String phone,String address,String username,String password){
        this.adminID=id;
        this.name=name;
        this.phoneNo=phone;
        this.address=address;
        this.username=username;
        this.password=password;
        
    }

    public adminClass() {
    }
    
    public int getAdminId(){
        return adminID;
    }
    public String getname(){
        return name;
    }
    public String getphoneNo(){
        return phoneNo;
    }
    public String getaddress(){
        return address;
    }
    public String getusername(){
        return username;
    }
    public String getpassword(){
        return password;
    }
    
    public void setAdminID(int id){
        adminID=id;
    }
    public void setname(String aname){
        name=aname;
        System.out.println("passes" + name);
    }
    public void setphoneNo(String no){
        phoneNo=no;
    }
    public void setaddress(String a){
        address=a;
    }
    public void setusername(String ausername){
        username=ausername;
    }
    public void setpassword(String apassword){
        password=apassword;
    }
    
      public String toString()
    {
        return Integer.toString(adminID) + " ~ " + name + " ~ " + phoneNo + " ~ " + address+ " ~ " + username+ " ~ " + password;
    }
}
