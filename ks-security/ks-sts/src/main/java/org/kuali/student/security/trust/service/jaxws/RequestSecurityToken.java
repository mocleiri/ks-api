
package org.kuali.student.security.trust.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.2
 * Wed Jun 09 11:32:58 EDT 2010
 * Generated source version: 2.2
 */

@XmlRootElement(name = "RequestSecurityToken", namespace = "http://schemas.xmlsoap.org/ws/2005/02/trust/wsdl")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestSecurityToken", namespace = "http://schemas.xmlsoap.org/ws/2005/02/trust/wsdl")

public class RequestSecurityToken {

    @XmlElement(name = "RequestSecurityToken", namespace = "http://schemas.xmlsoap.org/ws/2005/02/trust")
    private org.kuali.student.security.trust.dto.RequestSecurityTokenType RequestSecurityToken;

    public org.kuali.student.security.trust.dto.RequestSecurityTokenType getRequestSecurityToken() {
        return this.RequestSecurityToken;
    }

    public void setRequestSecurityToken(org.kuali.student.security.trust.dto.RequestSecurityTokenType newRequestSecurityToken)  {
        this.RequestSecurityToken = newRequestSecurityToken;
    }

}

