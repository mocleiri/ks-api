
package org.kuali.student.core.atp.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.2
 * Tue Mar 31 14:06:06 EDT 2009
 * Generated source version: 2.2
 */

@XmlRootElement(name = "deleteAtp", namespace = "http://student.kuali.org/wsdl/atp")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deleteAtp", namespace = "http://student.kuali.org/wsdl/atp")

public class DeleteAtp {

    @XmlElement(name = "atpKey")
    private java.lang.String atpKey;

    public java.lang.String getAtpKey() {
        return this.atpKey;
    }

    public void setAtpKey(java.lang.String newAtpKey)  {
        this.atpKey = newAtpKey;
    }

}

