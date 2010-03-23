
package org.kuali.student.lum.lu.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.core.validation.dto.ValidationResultInfo;

/**
 * This class was generated by Apache CXF 2.2
 * Thu Jan 21 10:05:23 PST 2010
 * Generated source version: 2.2
 */

@XmlRootElement(name = "validateLuiResponse", namespace = "http://student.kuali.org/wsdl/lu")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "validateLuiResponse", namespace = "http://student.kuali.org/wsdl/lu")

public class ValidateLuiResponse {

    @XmlElement(name = "return")
    private java.util.List<ValidationResultInfo> _return;

    public java.util.List<ValidationResultInfo> getReturn() {
        return this._return;
    }

    public void setReturn(java.util.List<ValidationResultInfo> new_return)  {
        this._return = new_return;
    }

}

