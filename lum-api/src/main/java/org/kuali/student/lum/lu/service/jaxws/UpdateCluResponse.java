
package org.kuali.student.lum.lu.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.1.4
 * Tue Feb 17 15:12:56 EST 2009
 * Generated source version: 2.1.4
 */

@XmlRootElement(name = "updateCluResponse", namespace = "http://student.kuali.org/lum/lu")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateCluResponse", namespace = "http://student.kuali.org/lum/lu")

public class UpdateCluResponse {

    @XmlElement(name = "return")
    private org.kuali.student.lum.lu.dto.CluInfo _return;

    public org.kuali.student.lum.lu.dto.CluInfo getReturn() {
        return this._return;
    }

    public void setReturn(org.kuali.student.lum.lu.dto.CluInfo new_return)  {
        this._return = new_return;
    }

}

