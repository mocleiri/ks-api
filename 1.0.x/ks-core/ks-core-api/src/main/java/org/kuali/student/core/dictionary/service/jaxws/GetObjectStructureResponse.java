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

package org.kuali.student.core.dictionary.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.1.3
 * Fri Jan 16 15:32:21 EST 2009
 * Generated source version: 2.1.3
 */

@XmlRootElement(name = "getObjectStructureResponse", namespace = "http://student.kuali.org/wsdl/dictionary")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getObjectStructureResponse", namespace = "http://student.kuali.org/wsdl/dictionary")

public class GetObjectStructureResponse {

    @XmlElement(name = "return")
    private org.kuali.student.core.dictionary.dto.ObjectStructure _return;

    public org.kuali.student.core.dictionary.dto.ObjectStructure getReturn() {
        return this._return;
    }

    public void setReturn(org.kuali.student.core.dictionary.dto.ObjectStructure new_return)  {
        this._return = new_return;
    }

}

