package org.kuali.student.security.trust.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

import org.kuali.student.security.exceptions.KSSecurityException;

/**
 * This class was generated by Apache CXF 2.2.8
 * Wed Jun 09 11:16:39 EDT 2010
 * Generated source version: 2.2.8
 * 
 */
 
@WebService(targetNamespace = "http://schemas.xmlsoap.org/ws/2005/02/trust/wsdl", name = "SecurityTokenService")
@XmlSeeAlso({org.kuali.student.security.wssecurity.secext.dto.ObjectFactory.class,org.kuali.student.security.trust.dto.ObjectFactory.class,org.kuali.student.security.wssecurity.utility.dto.ObjectFactory.class,org.kuali.student.security.addressing.dto.ObjectFactory.class,org.kuali.student.security.policy.dto.ObjectFactory.class,org.kuali.student.security.xmldsig.dto.ObjectFactory.class})
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface SecurityTokenService {

    @WebResult(name = "RequestSecurityTokenResponseCollection", targetNamespace = "http://schemas.xmlsoap.org/ws/2005/02/trust", partName = "responseCollection")
    @WebMethod(operationName = "RequestSecurityToken2")
    public org.kuali.student.security.trust.dto.RequestSecurityTokenResponseCollectionType requestSecurityToken2(
        @WebParam(partName = "request", name = "RequestSecurityToken", targetNamespace = "http://schemas.xmlsoap.org/ws/2005/02/trust")
        org.kuali.student.security.trust.dto.RequestSecurityTokenType request
    ) throws KSSecurityException;

    @WebResult(name = "RequestSecurityTokenResponse", targetNamespace = "http://schemas.xmlsoap.org/ws/2005/02/trust", partName = "response")
    @WebMethod(operationName = "RequestSecurityToken")
    public org.kuali.student.security.trust.dto.RequestSecurityTokenResponseType requestSecurityToken(
        @WebParam(partName = "request", name = "RequestSecurityToken", targetNamespace = "http://schemas.xmlsoap.org/ws/2005/02/trust")
        org.kuali.student.security.trust.dto.RequestSecurityTokenType request
    ) throws KSSecurityException;
}
