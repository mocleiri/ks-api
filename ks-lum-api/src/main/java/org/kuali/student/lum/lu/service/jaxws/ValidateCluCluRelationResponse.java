
package org.kuali.student.lum.lu.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.core.validation.dto.ValidationResultContainer;

/**
 * This class was generated by Apache CXF 2.1.4
 * Tue Feb 24 12:25:30 EST 2009
 * Generated source version: 2.1.4
 */

@XmlRootElement(name = "validateCluCluRelationResponse", namespace = "http://student.kuali.org/wsdl/lu")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "validateCluCluRelationResponse", namespace = "http://student.kuali.org/wsdl/lu")

public class ValidateCluCluRelationResponse {

    @XmlElement(name = "return")
    private java.util.List<ValidationResultContainer> _return;

    public java.util.List<ValidationResultContainer> getReturn() {
		if(_return==null){
			_return = new java.util.ArrayList<ValidationResultContainer>(0);
		}
        return this._return;
    }

    public void setReturn(java.util.List<ValidationResultContainer> new_return)  {
        this._return = new_return;
    }

}

