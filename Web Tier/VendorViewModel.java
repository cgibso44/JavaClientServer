/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewmodels;

import case2dtos.VendorEJBDTO;
import case2ejbs.VendorFacadeBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.inject.Inject;
import models.*;

/**
 *
 * @author Cale Gibson
 * A backing bean that exposes properties and methods
 * REVISION 1.0:
 * UPDATED: OCT 28 2013
 * AddVendor method changed to use facade bean
 * getVendorNos method changed to use facade bean
  *	* REVISION 1.1
 * UPDATE: NOV 2 2013
 * getVendor() updated to used Vendor dto instance from facade and populate
 *             member variables from its contents.
 */
@Named(value="vendorViewModel")
@RequestScoped
public class VendorViewModel implements Serializable {
    @EJB(name = "vfb")
    private VendorFacadeBean vbf;   
    
    //Constructor
    VendorViewModel() {}
    
    @Inject
    VendorModel model;
    
    //Properties
   private int vendorno;
   private String name;
   private String address;
   private String city;
   private String province;
   private String postalcode;
   private String phone;
   private String type;
   private String email;   
   private String msg;
   
   private ArrayList<SelectItem> vendornos = null;
    
   public String getMsg() {
        return (msg);
    }
    
    public void setMsg(String m) {
        this.msg = m;
    }
   
    public int getVendorNo() {
        return (vendorno);
    }
    
     public void setVendorNo(int v) {
        this.vendorno = v;
    }
    
    public String getName() {
        return(name);
    }
    
    public void setName(String uName) {
        this.name = uName;
    }
    
    public String getAddress() {
        return(address);
    }
    
    public void setAddress(String uAddress) {
        this.address = uAddress;
    }
    
    public String getCity() {
        return(city);
    }
    
    public void setCity(String uCity) {
        this.city = uCity;
    }
    
    public String getProvince() {
        return(province);
    }
    
    public void setProvince(String uProvince) {
        this.province = uProvince;
    }
    
    public String getPostalCode() {
        return(postalcode);
    }
    
    public void setPostalCode(String uPostalCode) {
        this.postalcode = uPostalCode;
    }
    
    public String getPhone() {
        return(phone);
    }
    
    public void setPhone(String uPhone) {
        this.phone = uPhone;
    }
    
    public String getType() {
        return(type);
    }
    
    public void setType(String uType) {
        this.type = uType;
    }
    
    public String getEmail() {
        return(email);
    }
    
    public void setEmail(String uEmail) {
        this.email = uEmail;
    }
     
    
    //UPDATED getVendor() : NOV 2 2013
    public void getVendor() {
        try{
            VendorEJBDTO details = vbf.getVendor(vendorno);
            name = details.getName();
            address = details.getAddress1();
            city = details.getCity();
            province = details.getProvince();
            postalcode = details.getPostalCode();
            phone = details.getPhone();
            type = details.getType();
            email = details.getEmail();
        }catch(Exception e){msg = e.getMessage();}
    }
    
    //Add Vendor Method, updated to use vbf
    public void addVendor(){
        vendorno = -1;
        try{
            VendorEJBDTO details = new VendorEJBDTO();
            details.setAddress1(address);
            details.setName(name);
            details.setCity(city);
            details.setProvince(province);
            details.setPhone(phone);
            details.setType(type);
            details.setEmail(email);
            details.setPostalCode(postalcode);
            vendorno = vbf.addVendor(details);
            
            if(vendorno > 0){
                msg = "Vendor " + vendorno + " Added!";                
            }
            else {
                msg = "Vendor not added - check log";
            }
            
        } catch(Exception e) {
            msg = e.getMessage();
        }
    }
    
   
   //Get Vendor Numbers, updated to used VBF
    public List<SelectItem> getVendornos() {
        List<SelectItem> vendornos = new ArrayList<SelectItem>();
        int[] nos = null;
        try {
           nos = vbf.getVendorNos();
            for(Integer i : nos) {
                SelectItem item = new SelectItem(Integer.toString(i));
                vendornos.add(item);
            }
        } catch (Exception e){
            System.out.println("Cant get vendornos " + e);
        }
        return vendornos;
    }
    

}
