
/*
 * 
 */

package org.kuali.student.wsdl.comment;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.2.10
 * Wed Sep 08 11:26:22 EDT 2010
 * Generated source version: 2.2.10
 * 
 */


@WebServiceClient(name = "CommentService", 
                  wsdlLocation = "file:/D:/svn/maven-dictionary-generator/trunk/src/main/resources/wsdl/CommentService.wsdl",
                  targetNamespace = "http://student.kuali.org/wsdl/comment") 
public class CommentService_Service extends Service {

    public final static URL WSDL_LOCATION;
    public final static QName SERVICE = new QName("http://student.kuali.org/wsdl/comment", "CommentService");
    public final static QName CommentServicePort = new QName("http://student.kuali.org/wsdl/comment", "CommentServicePort");
    static {
        URL url = null;
        try {
            url = new URL("file:/D:/svn/maven-dictionary-generator/trunk/src/main/resources/wsdl/CommentService.wsdl");
        } catch (MalformedURLException e) {
            System.err.println("Can not initialize the default wsdl from file:/D:/svn/maven-dictionary-generator/trunk/src/main/resources/wsdl/CommentService.wsdl");
            // e.printStackTrace();
        }
        WSDL_LOCATION = url;
    }

    public CommentService_Service(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public CommentService_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public CommentService_Service() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     * 
     * @return
     *     returns CommentService
     */
    @WebEndpoint(name = "CommentServicePort")
    public CommentService getCommentServicePort() {
        return super.getPort(CommentServicePort, CommentService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns CommentService
     */
    @WebEndpoint(name = "CommentServicePort")
    public CommentService getCommentServicePort(WebServiceFeature... features) {
        return super.getPort(CommentServicePort, CommentService.class, features);
    }

}
