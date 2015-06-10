/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.servlet.ServletContext;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

/**
 *
 * @author Cale Gibson
 
 VERSION 2.0 Stax Revision
 Updated to use stax xml processing for menu items
 */
@WebServlet(name = "Case2ControllerServlet", 
            urlPatterns = {"/C2Control"},
            initParams = {@WebInitParam(name = "Webpages",
                                        value= "/WebPages.xml")})
public class Case2ControllerServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        session.invalidate();
        
        // QName stands for Qualified Name
        QName qNode = new QName("webpage");
        QName qAttributeID = new QName("id");
        QName qAttributeURL = new QName("url");        
        
        try{
            //Get the XML File
            String fileName = getServletConfig().getInitParameter("Webpages");
            
            //get jsfID
            String jsfPageID = request.getParameter("jsf");
        
            //Load XML file from file system
            ServletContext application = getServletConfig().getServletContext();
            InputStream in = application.getResourceAsStream(fileName);
            
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            //Setup a new Reader
            XMLStreamReader parser = inputFactory.createXMLStreamReader(in);
            
            while (true) {
                int event = parser.next();
                if (event == XMLStreamConstants.END_DOCUMENT) {
                    parser.close();
                    break;
                }

                if (event == XMLStreamConstants.START_ELEMENT) {

                    if (parser.getName().getLocalPart().equals(qNode.getLocalPart())) {
                        if(jsfPageID.equalsIgnoreCase(parser.getAttributeValue(null, qAttributeID.getLocalPart().toString())))
                        {
                            response.sendRedirect(response.encodeRedirectURL(parser.getAttributeValue(null,qAttributeURL.getLocalPart().toString())));
                        }                      
                    }
                }
            }

            parser.close();
            
        }catch (Exception e){e.getMessage();}         
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
