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
 * Tue Apr 21 14:42:30 PDT 2009
 * Generated source version: 2.2
 */

@XmlRootElement(name = "getResultComponentTypeResponse", namespace = "http://service.lrc.lum.student.kuali.org/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getResultComponentTypeResponse", namespace = "http://service.lrc.lum.student.kuali.org/")

public class GetResultComponentTypeResponse {

    @XmlElement(name = "return")
    private org.kuali.student.lum.lrc.dto.ResultComponentTypeInfo _return;

    public org.kuali.student.lum.lrc.dto.ResultComponentTypeInfo getReturn() {
        return this._return;
    }

    public void setReturn(org.kuali.student.lum.lrc.dto.ResultComponentTypeInfo new_return)  {
        this._return = new_return;
    }

}

