
package org.kuali.student.enumeration.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.1.3
 * Mon Nov 10 15:24:27 PST 2008
 * Generated source version: 2.1.3
 */

@XmlRootElement(name = "addEnumeratedValueResponse", namespace = "http://student.kuali.org/wsdl/EnumerationService")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addEnumeratedValueResponse", namespace = "http://student.kuali.org/wsdl/EnumerationService")

public class AddEnumeratedValueResponse {

    @XmlElement(name = "return")
    private org.kuali.student.enumeration.dto.EnumeratedValue _return;

    public org.kuali.student.enumeration.dto.EnumeratedValue getReturn() {
        return this._return;
    }

    public void setReturn(org.kuali.student.enumeration.dto.EnumeratedValue new_return)  {
        this._return = new_return;
    }

}

