
package org.kuali.student.core.enumerationmanagement.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.1.3
 * Tue Dec 09 14:18:40 PST 2008
 * Generated source version: 2.1.3
 */

@XmlRootElement(name = "removeEnumerationMetaResponse", namespace = "http://student.kuali.org/wsdl/EnumerationManagementService")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "removeEnumerationMetaResponse", namespace = "http://student.kuali.org/wsdl/EnumerationManagementService")

public class RemoveEnumerationMetaResponse {

    @XmlElement(name = "return")
    private boolean _return;

    public boolean getReturn() {
        return this._return;
    }

    public void setReturn(boolean new_return)  {
        this._return = new_return;
    }

}

