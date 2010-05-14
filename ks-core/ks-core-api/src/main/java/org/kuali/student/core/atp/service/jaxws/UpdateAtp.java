/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

package org.kuali.student.core.atp.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.2
 * Tue Mar 31 14:06:06 EDT 2009
 * Generated source version: 2.2
 */

@XmlRootElement(name = "updateAtp", namespace = "http://student.kuali.org/wsdl/atp")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateAtp", namespace = "http://student.kuali.org/wsdl/atp", propOrder = {"atpKey","atpInfo"})

public class UpdateAtp {

    @XmlElement(name = "atpKey")
    private java.lang.String atpKey;
    @XmlElement(name = "atpInfo")
    private org.kuali.student.core.atp.dto.AtpInfo atpInfo;

    public java.lang.String getAtpKey() {
        return this.atpKey;
    }

    public void setAtpKey(java.lang.String newAtpKey)  {
        this.atpKey = newAtpKey;
    }

    public org.kuali.student.core.atp.dto.AtpInfo getAtpInfo() {
        return this.atpInfo;
    }

    public void setAtpInfo(org.kuali.student.core.atp.dto.AtpInfo newAtpInfo)  {
        this.atpInfo = newAtpInfo;
    }

}

