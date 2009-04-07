
package org.kuali.student.lum.lu.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.2
 * Mon Mar 30 15:25:45 EDT 2009
 * Generated source version: 2.2
 */

@XmlRootElement(name = "getNaturalLanguageForLuStatement", namespace = "http://student.kuali.org/lum/lu")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getNaturalLanguageForLuStatement", namespace = "http://student.kuali.org/lum/lu", propOrder = {"cluId","luStatementId","nlUsageTypeKey"})

public class GetNaturalLanguageForLuStatement {

    @XmlElement(name = "cluId")
    private java.lang.String cluId;
    @XmlElement(name = "luStatementId")
    private java.lang.String luStatementId;
    @XmlElement(name = "nlUsageTypeKey")
    private java.lang.String nlUsageTypeKey;

    public java.lang.String getCluId() {
        return this.cluId;
    }

    public void setCluId(java.lang.String newCluId)  {
        this.cluId = newCluId;
    }

    public java.lang.String getLuStatementId() {
        return this.luStatementId;
    }

    public void setLuStatementId(java.lang.String newLuStatementId)  {
        this.luStatementId = newLuStatementId;
    }

    public java.lang.String getNlUsageTypeKey() {
        return this.nlUsageTypeKey;
    }

    public void setNlUsageTypeKey(java.lang.String newNlUsageTypeKey)  {
        this.nlUsageTypeKey = newNlUsageTypeKey;
    }

}

