
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

@XmlRootElement(name = "getCategoriesByDocument", namespace = "http://student.kuali.org/core/document")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getCategoriesByDocument", namespace = "http://student.kuali.org/core/document")

public class GetCategoriesByDocument {

    @XmlElement(name = "documentId")
    private java.lang.String documentId;

    public java.lang.String getDocumentId() {
        return this.documentId;
    }

    public void setDocumentId(java.lang.String newDocumentId)  {
        this.documentId = newDocumentId;
    }

}

