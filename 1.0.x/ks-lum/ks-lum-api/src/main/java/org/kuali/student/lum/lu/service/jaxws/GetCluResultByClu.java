
package org.kuali.student.lum.lu.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.2
 * Thu Jan 21 10:05:23 PST 2010
 * Generated source version: 2.2
 */

@XmlRootElement(name = "getCluResultByClu", namespace = "http://student.kuali.org/wsdl/lu")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getCluResultByClu", namespace = "http://student.kuali.org/wsdl/lu")

public class GetCluResultByClu {

    @XmlElement(name = "cluId")
    private java.lang.String cluId;

    public java.lang.String getCluId() {
        return this.cluId;
    }

    public void setCluId(java.lang.String newCluId)  {
        this.cluId = newCluId;
    }

}

