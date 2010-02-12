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

@XmlRootElement(name = "validateLuStatement", namespace = "http://student.kuali.org/wsdl/lu")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "validateLuStatement", namespace = "http://student.kuali.org/wsdl/lu", propOrder = {"validationType","luStatementInfo"})

public class ValidateLuStatement {

    @XmlElement(name = "validationType")
    private java.lang.String validationType;
    @XmlElement(name = "luStatementInfo")
    private org.kuali.student.lum.lu.dto.LuStatementInfo luStatementInfo;

    public java.lang.String getValidationType() {
        return this.validationType;
    }

    public void setValidationType(java.lang.String newValidationType)  {
        this.validationType = newValidationType;
    }

    public org.kuali.student.lum.lu.dto.LuStatementInfo getLuStatementInfo() {
        return this.luStatementInfo;
    }

    public void setLuStatementInfo(org.kuali.student.lum.lu.dto.LuStatementInfo newLuStatementInfo)  {
        this.luStatementInfo = newLuStatementInfo;
    }

}

