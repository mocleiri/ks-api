
package org.kuali.student.core.enumerationmanagement.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.2
 * Fri May 15 12:08:08 EDT 2009
 * Generated source version: 2.2
 */

@XmlRootElement(name = "removeEnumeratedValueResponse", namespace = "http://student.kuali.org/wsdl/EnumerationManagementService")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "removeEnumeratedValueResponse", namespace = "http://student.kuali.org/wsdl/EnumerationManagementService")

public class RemoveEnumeratedValueResponse {

    @XmlElement(name = "return")
    private org.kuali.student.core.dto.StatusInfo _return;

    public org.kuali.student.core.dto.StatusInfo getReturn() {
        return this._return;
    }

    public void setReturn(org.kuali.student.core.dto.StatusInfo new_return)  {
        this._return = new_return;
    }

}

