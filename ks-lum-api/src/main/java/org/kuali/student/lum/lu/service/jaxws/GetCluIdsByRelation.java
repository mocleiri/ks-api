
package org.kuali.student.lum.lu.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.1.4
 * Wed Mar 11 10:08:47 PDT 2009
 * Generated source version: 2.1.4
 */

@XmlRootElement(name = "getCluIdsByRelation", namespace = "http://student.kuali.org/wsdl/lu")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getCluIdsByRelation", namespace = "http://student.kuali.org/wsdl/lu", propOrder = {"relatedCluId","luLuRelationTypeKey"})

public class GetCluIdsByRelation {

    @XmlElement(name = "relatedCluId")
    private java.lang.String relatedCluId;
    @XmlElement(name = "luLuRelationTypeKey")
    private java.lang.String luLuRelationTypeKey;

    public java.lang.String getRelatedCluId() {
        return this.relatedCluId;
    }

    public void setRelatedCluId(java.lang.String newRelatedCluId)  {
        this.relatedCluId = newRelatedCluId;
    }

    public java.lang.String getLuLuRelationTypeKey() {
        return this.luLuRelationTypeKey;
    }

    public void setLuLuRelationTypeKey(java.lang.String newLuLuRelationTypeKey)  {
        this.luLuRelationTypeKey = newLuLuRelationTypeKey;
    }

}

