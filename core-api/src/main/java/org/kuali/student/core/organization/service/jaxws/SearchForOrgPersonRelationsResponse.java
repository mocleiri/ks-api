
package org.kuali.student.core.organization.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.1.3
 * Fri Jan 16 11:42:38 EST 2009
 * Generated source version: 2.1.3
 */

@XmlRootElement(name = "searchForOrgPersonRelationsResponse", namespace = "http://service.organization.core.student.kuali.org/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "searchForOrgPersonRelationsResponse", namespace = "http://service.organization.core.student.kuali.org/")

public class SearchForOrgPersonRelationsResponse {

    @XmlElement(name = "return")
    private java.util.List<String> _return;

    public java.util.List<String> getReturn() {
        return this._return;
    }

    public void setReturn(java.util.List<String> new_return)  {
        this._return = new_return;
    }

}

