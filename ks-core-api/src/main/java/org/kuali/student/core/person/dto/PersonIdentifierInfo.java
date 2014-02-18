/*
 * Copyright 2013 The Kuali Foundation Licensed under the
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
package org.kuali.student.core.person.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import org.kuali.student.core.person.infc.PersonIdentifier;
import org.kuali.student.r2.common.dto.IdNamelessEntityInfo;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PersonIdentifierInfo", propOrder = {"id",
    "typeKey",
    "stateKey",
    "personId",
    "identifier",
    "meta",
    "attributes", "_futureElements"})
public class PersonIdentifierInfo extends IdNamelessEntityInfo implements PersonIdentifier, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String personId;
    @XmlElement
    private String identifier;

    @XmlAnyElement
    private List<Object> _futureElements;

    public PersonIdentifierInfo() {
    }

    public PersonIdentifierInfo(PersonIdentifier infc) {
        super(infc);
        this.setPersonId(infc.getPersonId());
        this.setIdentifier(infc.getIdentifier());
    }

    @Override
    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return "PersonIdentifierInfo{" + super.toString() + ", personId=" + personId + ", identifier=" + identifier + '}';
    }

}
