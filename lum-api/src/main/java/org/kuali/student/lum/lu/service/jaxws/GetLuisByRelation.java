
package org.kuali.student.lum.lu.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.1.4
 * Tue Feb 24 12:25:30 EST 2009
 * Generated source version: 2.1.4
 */

@XmlRootElement(name = "getLuisByRelation", namespace = "http://student.kuali.org/lum/lu")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getLuisByRelation", namespace = "http://student.kuali.org/lum/lu", propOrder = {"relatedLuiId","luLuRelationType"})

public class GetLuisByRelation {

    @XmlElement(name = "relatedLuiId")
    private java.lang.String relatedLuiId;
    @XmlElement(name = "luLuRelationType")
    private org.kuali.student.lum.lu.dto.LuLuRelationTypeInfo luLuRelationType;

    public java.lang.String getRelatedLuiId() {
        return this.relatedLuiId;
    }

    public void setRelatedLuiId(java.lang.String newRelatedLuiId)  {
        this.relatedLuiId = newRelatedLuiId;
    }

    public org.kuali.student.lum.lu.dto.LuLuRelationTypeInfo getLuLuRelationType() {
        return this.luLuRelationType;
    }

    public void setLuLuRelationType(org.kuali.student.lum.lu.dto.LuLuRelationTypeInfo newLuLuRelationType)  {
        this.luLuRelationType = newLuLuRelationType;
    }

}

