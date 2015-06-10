/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import case2dtos.PurchaseOrderEJBDTO;
import case2dtos.VendorEJBDTO;
import case2ejbs.POFacadeBean;
import case2ejbs.VendorFacadeBean;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.enterprise.context.RequestScoped;

/**
 * REST Web Service
 *
 * @author Cale Gibson
 Rest Service Methods for POViewer html
 Conatains methods to retrieve purchase order and vendor information in Json
 */
@Path("vendors")
@RequestScoped
public class VendorsResource {

    @Context
    private UriInfo context;

    @EJB
    private VendorFacadeBean vbf;
    
    @EJB
    private POFacadeBean pbf;
    
    /**
     * Creates a new instance of VendorsResource
     */
    public VendorsResource() {
    }

    @GET
    @Path("getPos/{vendorno}")
    @Produces("application/json")
    public List<PurchaseOrderEJBDTO> getPos(@PathParam("vendorno") int vendorno) {
        return pbf.getPos(vendorno);
    }
    
    @GET
    @Path("getAVendor/{vendorno}")
    @Produces("application/json")
    public VendorEJBDTO getAVendor(@PathParam("vendorno") int vendorno) {
        return vbf.getVendor(vendorno);
    }
    
    @GET
    @Path("getAPO/{ponumber}")
    @Produces("application/json")
    public List<PurchaseOrderEJBDTO> getAPO(@PathParam("ponumber") int ponum) {
        return pbf.getAPO(ponum);
    }
    
    @GET
    @Path("APOS/{vendorno}")
    @Produces("application/json")
    public List<PurchaseOrderEJBDTO> getAPOS(@PathParam("vendorno") int vendorno) {
        return pbf.getAllPOsForVendor(vendorno);
    }
    
    @GET
    @Path("vendorlist")
    @Produces("application/json")
    public List<Integer> getAllVendorNos(){
        return vbf.getVendornos();
    }
   
}
