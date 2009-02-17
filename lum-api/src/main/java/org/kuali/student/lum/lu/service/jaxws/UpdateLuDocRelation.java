
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

@XmlRootElement(name = "updateLuDocRelation", namespace = "http://student.kuali.org/lum/lu")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateLuDocRelation", namespace = "http://student.kuali.org/lum/lu", propOrder = {"luDocRelationId","luDocRelationInfo"})

public class UpdateLuDocRelation {

    @XmlElement(name = "luDocRelationId")
    private java.lang.String luDocRelationId;
    @XmlElement(name = "luDocRelationInfo")
    private org.kuali.student.lum.lu.dto.LuDocRelationInfo luDocRelationInfo;

    public java.lang.String getLuDocRelationId() {
        return this.luDocRelationId;
    }

    public void setLuDocRelationId(java.lang.String newLuDocRelationId)  {
        this.luDocRelationId = newLuDocRelationId;
    }

    public org.kuali.student.lum.lu.dto.LuDocRelationInfo getLuDocRelationInfo() {
        return this.luDocRelationInfo;
    }

    public void setLuDocRelationInfo(org.kuali.student.lum.lu.dto.LuDocRelationInfo newLuDocRelationInfo)  {
        this.luDocRelationInfo = newLuDocRelationInfo;
    }

}

