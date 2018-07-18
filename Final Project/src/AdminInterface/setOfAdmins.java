/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AdminInterface;

import java.io.Serializable;
import java.util.Vector;

/**
 *
 * @author LENOVO
 */
public class setOfAdmins extends Vector<adminClass> implements Serializable{
     
    public setOfAdmins(){
        super();
    }
    
    public void addAdmin(adminClass a){
        super.add(a);
    }
    

        public setOfAdmins findAdminByName(String aAdmin)
    {
        setOfAdmins qResult = new setOfAdmins();
        for(int x = 0; x < super.size(); x++)
        {
            if (super.get(x).getname().toUpperCase().contains(
                    aAdmin.toUpperCase()))
            {
                qResult.addAdmin(super.get(x));
            }
        }
        return qResult;
    }
    
    public boolean login(String username,String password){
        boolean value =false;
       for(int i=0;i<super.size();i++){
           if(super.get(i).getusername().contains(username) && super.get(i).getpassword().contains(password)){
              
               value = true;
           
            } 
        }
       
       return value;
      
    }
    
//    public void removeAdmin(adminClass aa){
//        super.remove(aa);
//    }
    
    
//    public setOfAdmins findAdminByUsername(String username){
//        setOfAdmins result = new setOfAdmins();
//            for(int x=0;x<super.size();x++){
//                if(super.get(x).getusername().toUpperCase().contains(username.toUpperCase())){
//                    result.addAdmin(super.get(x));
//               
//                }
//              
//            }
//            return result;
//    }
    
    
    public adminClass findAdmin(String username){
       
        for(int x=0;x<super.size();x++){
            if(super.get(x).getusername().contains(username)){
                return super.get(x);
            }
    }

        return null;
        
    }
    
    
//   public void removeAdmin(adminClass admin){
//     super.remove(admin);
// }
 
    public boolean updateadmin(String name,String phoneno,String address,String username){
      boolean result = false;
       for(int i=0;i<this.size();i++){
           if(this.get(i).getname().equals(name)){
               this.get(i).setname(name);
               this.get(i).setphoneNo(phoneno);
               this.get(i).setaddress(address);
               this.get(i).setusername(username);
               
            
           }
              result =  true;
       }
          
          
          
        
        return result;
    }
    
   
       public boolean checkUsernameandId(String username){
        boolean result = false;
        for(int x=0;x<super.size();x++){
            if(super.get(x).getusername().contains(username)){
                result =true;
            }
        }
        
        
        
        
        return result;
    } 
   
}
