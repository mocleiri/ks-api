
package org.kuali.student.lum.lu.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.1.4
 * Tue Feb 24 12:25:30 EST 2009
 * Generated source version: 2.1.4
 */

@XmlRootElement(name = "getCluIdsByLoId", namespace = "http://student.kuali.org/wsdl/lu")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getCluIdsByLoId", namespace = "http://student.kuali.org/wsdl/lu")

public class GetCluIdsByLoId {

    @XmlElement(name = "loId")
    private java.lang.String loId;

    public java.lang.String getLoId() {
        return this.loId;
    }

    public void setLoId(java.lang.String newLoId)  {
        this.loId = newLoId;
    }

}

