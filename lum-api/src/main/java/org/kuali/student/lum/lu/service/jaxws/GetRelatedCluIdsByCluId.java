
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

@XmlRootElement(name = "getRelatedCluIdsByCluId", namespace = "http://student.kuali.org/lum/lu")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getRelatedCluIdsByCluId", namespace = "http://student.kuali.org/lum/lu", propOrder = {"cluId","luLuRelationTypeKey"})

public class GetRelatedCluIdsByCluId {

    @XmlElement(name = "cluId")
    private java.lang.String cluId;
    @XmlElement(name = "luLuRelationTypeKey")
    private java.lang.String luLuRelationTypeKey;

    public java.lang.String getCluId() {
        return this.cluId;
    }

    public void setCluId(java.lang.String newCluId)  {
        this.cluId = newCluId;
    }

    public java.lang.String getLuLuRelationTypeKey() {
        return this.luLuRelationTypeKey;
    }

    public void setLuLuRelationTypeKey(java.lang.String newLuLuRelationTypeKey)  {
        this.luLuRelationTypeKey = newLuLuRelationTypeKey;
    }

}

