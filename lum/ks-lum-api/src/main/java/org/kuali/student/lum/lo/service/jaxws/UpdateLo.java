
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

@XmlRootElement(name = "updateLo", namespace = "http://student.kuali.org/wsdl/lo")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateLo", namespace = "http://student.kuali.org/wsdl/lo", propOrder = {"loId","loInfo"})

public class UpdateLo {

    @XmlElement(name = "loId")
    private java.lang.String loId;
    @XmlElement(name = "loInfo")
    private org.kuali.student.lum.lo.dto.LoInfo loInfo;

    public java.lang.String getLoId() {
        return this.loId;
    }

    public void setLoId(java.lang.String newLoId)  {
        this.loId = newLoId;
    }

    public org.kuali.student.lum.lo.dto.LoInfo getLoInfo() {
        return this.loInfo;
    }

    public void setLoInfo(org.kuali.student.lum.lo.dto.LoInfo newLoInfo)  {
        this.loInfo = newLoInfo;
    }

}

