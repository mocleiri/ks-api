
package org.kuali.student.core.dictionary.service.old.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.2
 * Fri May 14 11:26:06 PDT 2010
 * Generated source version: 2.2
 */

@XmlRootElement(name = "getObjectStructureResponse", namespace = "http://student.kuali.org/wsdl/dictionary")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getObjectStructureResponse", namespace = "http://student.kuali.org/wsdl/dictionary")

public class GetObjectStructureResponse {

    @XmlElement(name = "return")
    private org.kuali.student.core.dictionary.old.dto.ObjectStructure _return;

    public org.kuali.student.core.dictionary.old.dto.ObjectStructure getReturn() {
        return this._return;
    }

    public void setReturn(org.kuali.student.core.dictionary.old.dto.ObjectStructure new_return)  {
        this._return = new_return;
    }

}

