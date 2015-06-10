/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package case2ejbs;

import case2dtos.VendorEJBDTO;
import case2models.VendorsModel;
import com.google.common.primitives.Ints;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 *
 * @author Cale Gibson
 Vendor Facade Backing bean for vendors
 Has get vendor numbers, and get vendor methods
 */
@Stateless
@LocalBean
public class VendorFacadeBean {

    @PersistenceContext
    private EntityManager em;

    //Get the Vendor numbers, Return Array List
    public int[] getVendorNos(){
        List<Integer>nos = null;
        int [] a = null;
        try{
            Query qry = em.createNamedQuery("VendorsModel.findAllVendorNos");
            nos = qry.getResultList();
            a = Ints.toArray(nos);
        } catch (Exception e) {
            System.out.println("Error getting Vendornos from Facade - " + e.getMessage());
        }
        return a;
    }
    
    //Get the vendor numbers, Retirn Integer List
    public List<Integer> getVendornos(){
        List<Integer>nos = null;        
        try{
            Query qry = em.createNamedQuery("VendorsModel.findAllVendorNos");
            nos = qry.getResultList();           
        } catch (Exception e) {
            System.out.println("Error getting Vendornos from Facade - " + e.getMessage());
        }
        return nos;
    }
    
    //Get a Single Vendor
    public VendorEJBDTO getVendor(int vendorno){
        VendorsModel vm;
        VendorEJBDTO dto = new VendorEJBDTO();
        try{
            vm = em.find(VendorsModel.class, vendorno);
            dto.setAddress1(vm.getAddress1());
            dto.setCity(vm.getCity());
            dto.setEmail(vm.getEmail());
            dto.setName(vm.getName());
            dto.setPhone(vm.getPhone());
            dto.setPostalCode(vm.getPostalcode());
            dto.setProvince(vm.getProvince());
            dto.setType(vm.getVendortype());
            dto.setVendorno(vm.getVendorno());
            
            
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return dto;
    }
    
    //Add Vendor Method
    public int addVendor(VendorEJBDTO ven) {

        VendorsModel vm;
        int retVal = -1;

        try {
            vm = new VendorsModel(null, ven.getAddress1(), ven.getCity(),
                    ven.getProvince(), ven.getPostalCode(),
                    ven.getPhone(), ven.getType(), ven.getName(),
                    ven.getEmail());
            em.persist(vm);
            em.flush();
            retVal = vm.getVendorno().intValue();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return retVal;
    }

}
