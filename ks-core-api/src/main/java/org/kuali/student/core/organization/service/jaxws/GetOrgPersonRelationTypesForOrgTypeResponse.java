
package org.kuali.student.core.organization.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.core.organization.dto.OrgPersonRelationTypeInfo;

/**
 * This class was generated by Apache CXF 2.1.3
 * Fri Jan 16 11:42:38 EST 2009
 * Generated source version: 2.1.3
 */

@XmlRootElement(name = "getOrgPersonRelationTypesForOrgTypeResponse", namespace = "http://student.kuali.org/wsdl/organization")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getOrgPersonRelationTypesForOrgTypeResponse", namespace = "http://student.kuali.org/wsdl/organization")

public class GetOrgPersonRelationTypesForOrgTypeResponse {

    @XmlElement(name = "return")
    private java.util.List<OrgPersonRelationTypeInfo> _return;

    public java.util.List<OrgPersonRelationTypeInfo> getReturn() {
		if(_return==null){
			_return = new java.util.ArrayList<OrgPersonRelationTypeInfo>(0);
		}
        return this._return;
    }

    public void setReturn(java.util.List<OrgPersonRelationTypeInfo> new_return)  {
        this._return = new_return;
    }

}

