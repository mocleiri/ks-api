
package org.kuali.student.lum.lu.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.1.4
 * Tue Feb 17 15:12:57 EST 2009
 * Generated source version: 2.1.4
 */

@XmlRootElement(name = "removeOutcomeLoFromLui", namespace = "http://student.kuali.org/lum/lu")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "removeOutcomeLoFromLui", namespace = "http://student.kuali.org/lum/lu", propOrder = {"loId","luiId"})

public class RemoveOutcomeLoFromLui {

    @XmlElement(name = "loId")
    private java.lang.String loId;
    @XmlElement(name = "luiId")
    private java.lang.String luiId;

    public java.lang.String getLoId() {
        return this.loId;
    }

    public void setLoId(java.lang.String newLoId)  {
        this.loId = newLoId;
    }

    public java.lang.String getLuiId() {
        return this.luiId;
    }

    public void setLuiId(java.lang.String newLuiId)  {
        this.luiId = newLuiId;
    }

}

