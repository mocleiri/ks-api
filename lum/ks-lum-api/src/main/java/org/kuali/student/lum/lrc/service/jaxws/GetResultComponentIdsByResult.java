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

@XmlRootElement(name = "getResultComponentIdsByResult", namespace = "http://student.kuali.org/wsdl/lrc")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getResultComponentIdsByResult", namespace = "http://student.kuali.org/wsdl/lrc", propOrder = {"resultValueId","resultComponentTypeKey"})

public class GetResultComponentIdsByResult {

    @XmlElement(name = "resultValueId")
    private java.lang.String resultValueId;
    @XmlElement(name = "resultComponentTypeKey")
    private java.lang.String resultComponentTypeKey;

    public java.lang.String getResultValueId() {
        return this.resultValueId;
    }

    public void setResultValueId(java.lang.String newResultValueId)  {
        this.resultValueId = newResultValueId;
    }

    public java.lang.String getResultComponentTypeKey() {
        return this.resultComponentTypeKey;
    }

    public void setResultComponentTypeKey(java.lang.String newResultComponentTypeKey)  {
        this.resultComponentTypeKey = newResultComponentTypeKey;
    }

}

