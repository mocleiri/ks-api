
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

@XmlRootElement(name = "createLuDocRelationForClu", namespace = "http://student.kuali.org/lum/lu")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "createLuDocRelationForClu", namespace = "http://student.kuali.org/lum/lu", propOrder = {"luDocRelationType","documentId","cluId","luDocRelationInfo"})

public class CreateLuDocRelationForClu {

    @XmlElement(name = "luDocRelationType")
    private java.lang.String luDocRelationType;
    @XmlElement(name = "documentId")
    private java.lang.String documentId;
    @XmlElement(name = "cluId")
    private java.lang.String cluId;
    @XmlElement(name = "luDocRelationInfo")
    private org.kuali.student.lum.lu.dto.LuDocRelationInfo luDocRelationInfo;

    public java.lang.String getLuDocRelationType() {
        return this.luDocRelationType;
    }

    public void setLuDocRelationType(java.lang.String newLuDocRelationType)  {
        this.luDocRelationType = newLuDocRelationType;
    }

    public java.lang.String getDocumentId() {
        return this.documentId;
    }

    public void setDocumentId(java.lang.String newDocumentId)  {
        this.documentId = newDocumentId;
    }

    public java.lang.String getCluId() {
        return this.cluId;
    }

    public void setCluId(java.lang.String newCluId)  {
        this.cluId = newCluId;
    }

    public org.kuali.student.lum.lu.dto.LuDocRelationInfo getLuDocRelationInfo() {
        return this.luDocRelationInfo;
    }

    public void setLuDocRelationInfo(org.kuali.student.lum.lu.dto.LuDocRelationInfo newLuDocRelationInfo)  {
        this.luDocRelationInfo = newLuDocRelationInfo;
    }

}

