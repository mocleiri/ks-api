
package org.kuali.student.lum.lo.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.2
 * Fri Feb 19 15:17:58 PST 2010
 * Generated source version: 2.2
 */

@XmlRootElement(name = "deleteLo", namespace = "http://student.kuali.org/wsdl/lo")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deleteLo", namespace = "http://student.kuali.org/wsdl/lo")

public class DeleteLo {

    @XmlElement(name = "loId")
    private java.lang.String loId;

    public java.lang.String getLoId() {
        return this.loId;
    }

    public void setLoId(java.lang.String newLoId)  {
        this.loId = newLoId;
    }

}

