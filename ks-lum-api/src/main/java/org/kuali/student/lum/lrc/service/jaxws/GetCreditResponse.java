
package org.kuali.student.lum.lrc.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.2
 * Tue Apr 21 14:42:30 PDT 2009
 * Generated source version: 2.2
 */

@XmlRootElement(name = "getCreditResponse", namespace = "http://service.lrc.lum.student.kuali.org/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getCreditResponse", namespace = "http://service.lrc.lum.student.kuali.org/")

public class GetCreditResponse {

    @XmlElement(name = "return")
    private org.kuali.student.lum.lrc.dto.CreditInfo _return;

    public org.kuali.student.lum.lrc.dto.CreditInfo getReturn() {
        return this._return;
    }

    public void setReturn(org.kuali.student.lum.lrc.dto.CreditInfo new_return)  {
        this._return = new_return;
    }

}

