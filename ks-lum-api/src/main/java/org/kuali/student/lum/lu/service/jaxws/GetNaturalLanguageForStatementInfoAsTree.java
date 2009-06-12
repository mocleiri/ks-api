
package org.kuali.student.lum.lu.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.2
 * Tue Jun 02 13:15:41 PDT 2009
 * Generated source version: 2.2
 */

@XmlRootElement(name = "getNaturalLanguageForStatementInfoAsTree", namespace = "http://student.kuali.org/lum/lu")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getNaturalLanguageForStatementInfoAsTree", namespace = "http://student.kuali.org/lum/lu", propOrder = {"cluId","statementInfo","nlUsageTypeKey","language"})

public class GetNaturalLanguageForStatementInfoAsTree {

    @XmlElement(name = "cluId")
    private java.lang.String cluId;
    @XmlElement(name = "statementInfo")
    private org.kuali.student.lum.lu.dto.LuStatementInfo statementInfo;
    @XmlElement(name = "nlUsageTypeKey")
    private java.lang.String nlUsageTypeKey;
    @XmlElement(name = "language")
    private java.lang.String language;

    public java.lang.String getCluId() {
        return this.cluId;
    }

    public void setCluId(java.lang.String newCluId)  {
        this.cluId = newCluId;
    }

    public org.kuali.student.lum.lu.dto.LuStatementInfo getStatementInfo() {
        return this.statementInfo;
    }

    public void setStatementInfo(org.kuali.student.lum.lu.dto.LuStatementInfo newStatementInfo)  {
        this.statementInfo = newStatementInfo;
    }

    public java.lang.String getNlUsageTypeKey() {
        return this.nlUsageTypeKey;
    }

    public void setNlUsageTypeKey(java.lang.String newNlUsageTypeKey)  {
        this.nlUsageTypeKey = newNlUsageTypeKey;
    }

    public java.lang.String getLanguage() {
        return this.language;
    }

    public void setLanguage(java.lang.String newLanguage)  {
        this.language = newLanguage;
    }

}

