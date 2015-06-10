/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package case2ejbs;

import case2dtos.PurchaseOrderEJBDTO;
import case2dtos.PurchaseOrderLineItemEJBDTO;
import case2models.ProductsModel;
import case2models.PurchaseorderlineitemsModel;
import case2models.PurchaseordersModel;
import case2models.VendorsModel;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Cale Gibson
 PO backing bean for Purchase Orders
 Contains methods for generating and retrieving purchase orders
 */
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Stateless
@LocalBean
public class POFacadeBean {
    
     @PersistenceContext
     private EntityManager em;
     
     
     //Add a PurchaseOrder Method - Returns an int
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
     public int addPO(PurchaseOrderEJBDTO poDTO) {
         PurchaseordersModel pm;
         VendorsModel vm;
         int poRowID = -1;
         Date poDate = new java.util.Date();
         
         try {
             vm = em.find(VendorsModel.class, poDTO.getVendorno());
             pm = new PurchaseordersModel(0, poDTO.getTotal(), poDate);
             pm.setVendorno(vm);
             em.persist(pm);
             
             for(PurchaseOrderLineItemEJBDTO line : poDTO.getItems()) {
                 addPOLine(line,pm);
             }
             poRowID = pm.getPonumber().intValue();
             
         }catch (Exception e) {
             System.out.println(e.getMessage());
         }
         return poRowID;
     }
     
     
     //Add POLINE Items, returns nothing - adds poline item
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
     private void addPOLine(PurchaseOrderLineItemEJBDTO line, PurchaseordersModel pm) {
         PurchaseorderlineitemsModel polm;
         try{
             polm = new PurchaseorderlineitemsModel(null, line.getProdcd(), line.getQty() , BigDecimal.valueOf(line.getPrice()));
             polm.setPonumber(pm);             
             em.persist(polm);
             em.flush();
         }catch (Exception e) {
             System.out.println(e.getMessage());
         }
     }
    
    //List of PO's
    public List<PurchaseOrderEJBDTO> getPos(int vendorno){         
        VendorsModel vm;
        List<PurchaseOrderEJBDTO> poOrders = new ArrayList<>();
        try{
            vm = em.find(VendorsModel.class, vendorno);
            Query qry = em.createNamedQuery("PurchaseordersModel.findByVendorNo");
            qry.setParameter("vendorno", vm);
            poOrders = qry.getResultList();            
            
                       
        } catch (Exception e) {
            System.out.println("Error getting Vendornos from Facade - " + e.getMessage());
        }
        return poOrders;
    }
    
    //Get an Single PO
    public List<PurchaseOrderEJBDTO> getAPO(int ponum){         
        
        List<PurchaseOrderEJBDTO> poOrder = new ArrayList<>();
        try{           
            Query qry = em.createNamedQuery("PurchaseordersModel.findByPonumber");
            qry.setParameter("ponumber", ponum);
            poOrder = qry.getResultList();           
                       
        } catch (Exception e) {
            System.out.println("Error getting Vendornos from Facade - " + e.getMessage());
        }
        return poOrder;
    }
        
        
        //Get All the POS for a vendor
    public ArrayList<PurchaseOrderEJBDTO> getAllPOsForVendor(int vendorno) {
        ArrayList<PurchaseOrderEJBDTO> allpoinfo = new ArrayList<>();
        ArrayList<PurchaseOrderLineItemEJBDTO> items = new ArrayList<>();
        List<PurchaseorderlineitemsModel> lines; // lines from the model
        List<PurchaseordersModel> pos; // pos from model
        List<ProductsModel> proditems = new ArrayList<>();
        PurchaseordersModel pm;
        VendorsModel vm;
        PurchaseorderlineitemsModel lm;
        ProductsModel prm;
        PurchaseOrderEJBDTO poDTO;
        
        try {
            vm = em.find(VendorsModel.class, vendorno);
            Query qry = em.createNamedQuery("PurchaseordersModel.findByVendorNo");
            qry.setParameter("vendorno", vm);
            pos = qry.getResultList();
                       
            for( Object o : pos) {
                PurchaseordersModel po = (PurchaseordersModel)o;
                poDTO = new PurchaseOrderEJBDTO();
                poDTO.setTotal(po.getAmount());
                poDTO.setVendorno(vendorno);
                poDTO.setPONumber(po.getPonumber());                
                pm = em.find(PurchaseordersModel.class, po.getPonumber());
                Query qry2 = em.createNamedQuery("PurchaseorderlineitemsModel.findByPonumber");
                qry2.setParameter("ponumber", pm);
                lines = qry2.getResultList();
                items = new ArrayList<>();
                for( Object o2 : lines) { 
                    proditems = new ArrayList<>();
                    PurchaseorderlineitemsModel line = (PurchaseorderlineitemsModel)o2;
                    PurchaseOrderLineItemEJBDTO poLine = new PurchaseOrderLineItemEJBDTO();
                    poLine.setQty(line.getQty());
                    poLine.setPrice(line.getPrice().doubleValue());
                    poLine.setProdcd(line.getProdcd());
                    poLine.setPonumber(po.getPonumber());
                    poLine.setExt(line.getPrice().doubleValue() * line.getQty());
                    
                    Query qry3 = em.createNamedQuery("ProductsModel.findByProdcd");
                    qry3.setParameter("prodcd", line.getProdcd());
                    proditems = qry3.getResultList();
                    poLine.setProdname(proditems.get(0).getProdnam());
                    
                    items.add(poLine);
                }
                
                poDTO.setItems(items);                
               allpoinfo.add(poDTO);
               //items.clear();
             }
            
        }catch (Exception e) {
            System.out.println("Error getting all POS from Facade - " + e.getMessage());
        }
        return allpoinfo;
    }
     
}
