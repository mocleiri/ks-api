
package org.kuali.student.rules.repository.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.1.4
 * Wed Mar 11 13:47:09 EDT 2009
 * Generated source version: 2.1.4
 */

@XmlRootElement(name = "removeState", namespace = "http://student.kuali.org/wsdl/brms/RuleRepository")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "removeState", namespace = "http://student.kuali.org/wsdl/brms/RuleRepository")

public class RemoveState {

    @XmlElement(name = "name")
    private java.lang.String name;

    public java.lang.String getName() {
        return this.name;
    }

    public void setName(java.lang.String newName)  {
        this.name = newName;
    }

}

