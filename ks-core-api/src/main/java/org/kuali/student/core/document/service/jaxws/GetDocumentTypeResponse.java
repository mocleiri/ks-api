
package org.kuali.student.core.document.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.2
 * Tue Jun 16 14:57:19 EDT 2009
 * Generated source version: 2.2
 */

@XmlRootElement(name = "getDocumentTypeResponse", namespace = "http://student.kuali.org/wsdl/document")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getDocumentTypeResponse", namespace = "http://student.kuali.org/wsdl/document")

public class GetDocumentTypeResponse {

    @XmlElement(name = "return")
    private org.kuali.student.core.document.dto.DocumentTypeInfo _return;

    public org.kuali.student.core.document.dto.DocumentTypeInfo getReturn() {
        return this._return;
    }

    public void setReturn(org.kuali.student.core.document.dto.DocumentTypeInfo new_return)  {
        this._return = new_return;
    }

}

