
package org.kuali.student.lum.lu.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.lum.lu.dto.LuiInfo;

/**
 * This class was generated by Apache CXF 2.1.4
 * Tue Feb 24 12:25:30 EST 2009
 * Generated source version: 2.1.4
 */

@XmlRootElement(name = "getLuisByIdListResponse", namespace = "http://student.kuali.org/lum/lu")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getLuisByIdListResponse", namespace = "http://student.kuali.org/lum/lu")

public class GetLuisByIdListResponse {

    @XmlElement(name = "return")
    private java.util.List<LuiInfo> _return;

    public java.util.List<LuiInfo> getReturn() {
		if(_return==null){
			_return = new java.util.ArrayList<LuiInfo>(0);
		}
        return this._return;
    }

    public void setReturn(java.util.List<LuiInfo> new_return)  {
        this._return = new_return;
    }

}

