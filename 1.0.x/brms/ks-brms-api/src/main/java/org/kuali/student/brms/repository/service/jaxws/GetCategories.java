
package org.kuali.student.brms.repository.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.2
 * Mon Jul 13 20:53:46 PDT 2009
 * Generated source version: 2.2
 */

@XmlRootElement(name = "getCategories", namespace = "http://student.kuali.org/wsdl/brms/RuleRepository")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getCategories", namespace = "http://student.kuali.org/wsdl/brms/RuleRepository")

public class GetCategories {

    @XmlElement(name = "path")
    private java.lang.String path;

    public java.lang.String getPath() {
        return this.path;
    }

    public void setPath(java.lang.String newPath)  {
        this.path = newPath;
    }

}

