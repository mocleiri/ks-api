
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

@XmlRootElement(name = "getCluIdsByRelation", namespace = "http://student.kuali.org/lum/lu")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getCluIdsByRelation", namespace = "http://student.kuali.org/lum/lu", propOrder = {"relatedCluId","luLuRelationType"})

public class GetCluIdsByRelation {

    @XmlElement(name = "relatedCluId")
    private java.lang.String relatedCluId;
    @XmlElement(name = "luLuRelationType")
    private org.kuali.student.lum.lu.dto.LuLuRelationTypeInfo luLuRelationType;

    public java.lang.String getRelatedCluId() {
        return this.relatedCluId;
    }

    public void setRelatedCluId(java.lang.String newRelatedCluId)  {
        this.relatedCluId = newRelatedCluId;
    }

    public org.kuali.student.lum.lu.dto.LuLuRelationTypeInfo getLuLuRelationType() {
        return this.luLuRelationType;
    }

    public void setLuLuRelationType(org.kuali.student.lum.lu.dto.LuLuRelationTypeInfo newLuLuRelationType)  {
        this.luLuRelationType = newLuLuRelationType;
    }

}

