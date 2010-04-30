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

package org.kuali.student.core.document.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.2
 * Tue Jun 16 14:57:19 EDT 2009
 * Generated source version: 2.2
 */

@XmlRootElement(name = "getDocumentType", namespace = "http://student.kuali.org/wsdl/document")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getDocumentType", namespace = "http://student.kuali.org/wsdl/document")

public class GetDocumentType {

    @XmlElement(name = "documentTypeKey")
    private java.lang.String documentTypeKey;

    public java.lang.String getDocumentTypeKey() {
        return this.documentTypeKey;
    }

    public void setDocumentTypeKey(java.lang.String newDocumentTypeKey)  {
        this.documentTypeKey = newDocumentTypeKey;
    }

}

