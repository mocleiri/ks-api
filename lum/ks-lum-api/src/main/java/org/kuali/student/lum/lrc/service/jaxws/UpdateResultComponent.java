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
package org.kuali.student.lum.lrc.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.2
 * Wed Apr 22 09:59:25 EDT 2009
 * Generated source version: 2.2
 */

@XmlRootElement(name = "updateResultComponent", namespace = "http://student.kuali.org/wsdl/lrc")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateResultComponent", namespace = "http://student.kuali.org/wsdl/lrc", propOrder = {"resultComponentId","resultComponentInfo"})

public class UpdateResultComponent {

    @XmlElement(name = "resultComponentId")
    private java.lang.String resultComponentId;
    @XmlElement(name = "resultComponentInfo")
    private org.kuali.student.lum.lrc.dto.ResultComponentInfo resultComponentInfo;

    public java.lang.String getResultComponentId() {
        return this.resultComponentId;
    }

    public void setResultComponentId(java.lang.String newResultComponentId)  {
        this.resultComponentId = newResultComponentId;
    }

    public org.kuali.student.lum.lrc.dto.ResultComponentInfo getResultComponentInfo() {
        return this.resultComponentInfo;
    }

    public void setResultComponentInfo(org.kuali.student.lum.lrc.dto.ResultComponentInfo newResultComponentInfo)  {
        this.resultComponentInfo = newResultComponentInfo;
    }

}

