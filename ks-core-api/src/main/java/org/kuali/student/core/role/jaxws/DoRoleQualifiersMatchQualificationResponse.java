
package org.kuali.student.core.role.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.1.3
 * Thu Nov 20 12:17:38 EST 2008
 * Generated source version: 2.1.3
 */

@XmlRootElement(name = "doRoleQualifiersMatchQualificationResponse", namespace = "http://student.kuali.org/wsdl/roleType")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "doRoleQualifiersMatchQualificationResponse", namespace = "http://student.kuali.org/wsdl/roleType")

public class DoRoleQualifiersMatchQualificationResponse {

    @XmlElement(name = "return")
    private boolean _return;

    public boolean getReturn() {
        return this._return;
    }

    public void setReturn(boolean new_return)  {
        this._return = new_return;
    }

}

