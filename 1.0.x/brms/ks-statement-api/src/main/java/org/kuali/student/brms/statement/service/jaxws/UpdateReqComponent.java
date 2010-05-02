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


package org.kuali.student.brms.statement.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.2
 * Tue Jan 26 22:57:16 PST 2010
 * Generated source version: 2.2
 */

@XmlRootElement(name = "updateReqComponent", namespace = "http://student.kuali.org/wsdl/statement")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateReqComponent", namespace = "http://student.kuali.org/wsdl/statement", propOrder = {"reqComponentId","reqComponentInfo"})

public class UpdateReqComponent {

    @XmlElement(name = "reqComponentId")
    private java.lang.String reqComponentId;
    @XmlElement(name = "reqComponentInfo")
    private org.kuali.student.brms.statement.dto.ReqComponentInfo reqComponentInfo;

    public java.lang.String getReqComponentId() {
        return this.reqComponentId;
    }

    public void setReqComponentId(java.lang.String newReqComponentId)  {
        this.reqComponentId = newReqComponentId;
    }

    public org.kuali.student.brms.statement.dto.ReqComponentInfo getReqComponentInfo() {
        return this.reqComponentInfo;
    }

    public void setReqComponentInfo(org.kuali.student.brms.statement.dto.ReqComponentInfo newReqComponentInfo)  {
        this.reqComponentInfo = newReqComponentInfo;
    }

}

