
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

@XmlRootElement(name = "getLrTypeResponse", namespace = "http://student.kuali.org/wsdl/lu")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getLrTypeResponse", namespace = "http://student.kuali.org/wsdl/lu")

public class GetLrTypeResponse {

    @XmlElement(name = "return")
    private org.kuali.student.lum.lu.dto.LrTypeInfo _return;

    public org.kuali.student.lum.lu.dto.LrTypeInfo getReturn() {
        return this._return;
    }

    public void setReturn(org.kuali.student.lum.lu.dto.LrTypeInfo new_return)  {
        this._return = new_return;
    }

}

