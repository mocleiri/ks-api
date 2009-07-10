
package org.kuali.student.lum.nlt.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.2
 * Fri Jul 10 15:41:26 PDT 2009
 * Generated source version: 2.2
 */

@XmlRootElement(name = "getNaturalLanguageForLuStatementInfo", namespace = "http://student.kuali.org/lum/nlt")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getNaturalLanguageForLuStatementInfo", namespace = "http://student.kuali.org/lum/nlt", propOrder = {"cluId","statementInfo","nlUsageTypeKey","language"})

public class GetNaturalLanguageForLuStatementInfo {

    @XmlElement(name = "cluId")
    private java.lang.String cluId;
    @XmlElement(name = "statementInfo")
    private org.kuali.student.lum.nlt.dto.LuNlStatementInfo statementInfo;
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

    public org.kuali.student.lum.nlt.dto.LuNlStatementInfo getStatementInfo() {
        return this.statementInfo;
    }

    public void setStatementInfo(org.kuali.student.lum.nlt.dto.LuNlStatementInfo newStatementInfo)  {
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

