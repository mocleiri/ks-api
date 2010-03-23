
package org.kuali.student.brms.repository.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.2
 * Mon Jul 13 20:53:46 PDT 2009
 * Generated source version: 2.2
 */

@XmlRootElement(name = "createCategory", namespace = "http://student.kuali.org/wsdl/brms/RuleRepository")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "createCategory", namespace = "http://student.kuali.org/wsdl/brms/RuleRepository", propOrder = {"path","name","description"})

public class CreateCategory {

    @XmlElement(name = "path")
    private java.lang.String path;
    @XmlElement(name = "name")
    private java.lang.String name;
    @XmlElement(name = "description")
    private java.lang.String description;

    public java.lang.String getPath() {
        return this.path;
    }

    public void setPath(java.lang.String newPath)  {
        this.path = newPath;
    }

    public java.lang.String getName() {
        return this.name;
    }

    public void setName(java.lang.String newName)  {
        this.name = newName;
    }

    public java.lang.String getDescription() {
        return this.description;
    }

    public void setDescription(java.lang.String newDescription)  {
        this.description = newDescription;
    }

}

