
package org.kuali.student.core.role.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.student.core.ws.binding.AttributeSetAdapter;

/**
 * This class was generated by Apache CXF 2.1.3
 * Thu Nov 20 12:17:38 EST 2008
 * Generated source version: 2.1.3
 */

@XmlRootElement(name = "getAllImpliedQualificationsResponse", namespace = "http://student.kuali.org/wsdl/roleType")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getAllImpliedQualificationsResponse", namespace = "http://student.kuali.org/wsdl/roleType")

public class GetAllImpliedQualificationsResponse {

    @XmlElement(name = "return")
    @XmlJavaTypeAdapter(AttributeSetAdapter.class)
    private java.util.List<AttributeSet> _return;

    public java.util.List<AttributeSet> getReturn() {
        return this._return;
    }

    public void setReturn(java.util.List<AttributeSet> new_return)  {
        this._return = new_return;
    }

}

