
package org.kuali.student.rules.factfinder.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.1
 * Fri Oct 17 14:37:23 EDT 2008
 * Generated source version: 2.1
 */

@XmlRootElement(name = "fetchFactResponse", namespace = "http://student.kuali.org/poc/wsdl/brms/factfinder")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fetchFactResponse", namespace = "http://student.kuali.org/poc/wsdl/brms/factfinder")

public class FetchFactResponse {

    @XmlElement(name = "return")
    private org.kuali.student.rules.factfinder.dto.FactResultDTO _return;

    public org.kuali.student.rules.factfinder.dto.FactResultDTO getReturn() {
        return this._return;
    }

    public void setReturn(org.kuali.student.rules.factfinder.dto.FactResultDTO new_return)  {
        this._return = new_return;
    }

}

