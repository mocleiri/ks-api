
package org.kuali.student.lum.lu.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.1.4
 * Tue Feb 17 15:12:56 EST 2009
 * Generated source version: 2.1.4
 */

@XmlRootElement(name = "getLuisByIdList", namespace = "http://student.kuali.org/lum/lu")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getLuisByIdList", namespace = "http://student.kuali.org/lum/lu")

public class GetLuisByIdList {

    @XmlElement(name = "luiIdList")
    private java.util.List<java.lang.String> luiIdList;

    public java.util.List<java.lang.String> getLuiIdList() {
        return this.luiIdList;
    }

    public void setLuiIdList(java.util.List<java.lang.String> newLuiIdList)  {
        this.luiIdList = newLuiIdList;
    }

}

