
package org.kuali.student.dictionary.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.1.3
 * Fri Nov 07 12:17:29 PST 2008
 * Generated source version: 2.1.3
 */

@XmlRootElement(name = "validateStructureData", namespace = "http://org.kuali.student/dictonary")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "validateStructureData", namespace = "http://org.kuali.student/dictonary", propOrder = {"objectTypeKey","stateKey","info"})

public class ValidateStructureData {

    @XmlElement(name = "objectTypeKey")
    private java.lang.String objectTypeKey;
    @XmlElement(name = "stateKey")
    private java.lang.String stateKey;
    @XmlElement(name = "info")
    private java.lang.String info;

    public java.lang.String getObjectTypeKey() {
        return this.objectTypeKey;
    }

    public void setObjectTypeKey(java.lang.String newObjectTypeKey)  {
        this.objectTypeKey = newObjectTypeKey;
    }

    public java.lang.String getStateKey() {
        return this.stateKey;
    }

    public void setStateKey(java.lang.String newStateKey)  {
        this.stateKey = newStateKey;
    }

    public java.lang.String getInfo() {
        return this.info;
    }

    public void setInfo(java.lang.String newInfo)  {
        this.info = newInfo;
    }

}

