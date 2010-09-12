/*
 * Copyright 2009 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * 
 * http://www.osedu.org/licenses/ECL-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
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

@XmlRootElement(name = "getNaturalLanguageForStatementInfoAsTree", namespace = "http://student.kuali.org/wsdl/nlt")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getNaturalLanguageForStatementInfoAsTree", namespace = "http://student.kuali.org/wsdl/nlt", propOrder = {"cluId","statementInfo","nlUsageTypeKey","language"})

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

