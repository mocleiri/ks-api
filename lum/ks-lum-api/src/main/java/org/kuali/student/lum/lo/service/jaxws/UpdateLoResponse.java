
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

@XmlRootElement(name = "updateLoResponse", namespace = "http://student.kuali.org/wsdl/lo")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateLoResponse", namespace = "http://student.kuali.org/wsdl/lo")

public class UpdateLoResponse {

    @XmlElement(name = "return")
    private org.kuali.student.lum.lo.dto.LoInfo _return;

    public org.kuali.student.lum.lo.dto.LoInfo getReturn() {
        return this._return;
    }

    public void setReturn(org.kuali.student.lum.lo.dto.LoInfo new_return)  {
        this._return = new_return;
    }

}

