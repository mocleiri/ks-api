
package org.kuali.student.lum.lu.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.2
 * Tue Jun 02 13:05:55 PDT 2009
 * Generated source version: 2.2
 */

@XmlRootElement(name = "getNaturalLanguageForStatementInfoAsTreeResponse", namespace = "http://student.kuali.org/lum/lu")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getNaturalLanguageForStatementInfoAsTreeResponse", namespace = "http://student.kuali.org/lum/lu")

public class GetNaturalLanguageForStatementInfoAsTreeResponse {

    @XmlElement(name = "return")
    private org.kuali.student.lum.lu.dto.NLTranslationNodeInfo _return;

    public org.kuali.student.lum.lu.dto.NLTranslationNodeInfo getReturn() {
        return this._return;
    }

    public void setReturn(org.kuali.student.lum.lu.dto.NLTranslationNodeInfo new_return)  {
        this._return = new_return;
    }

}

