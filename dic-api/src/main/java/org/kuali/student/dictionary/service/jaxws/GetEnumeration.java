
package org.kuali.student.dictionary.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.1
 * Mon Oct 20 10:47:37 EDT 2008
 * Generated source version: 2.1
 */

@XmlRootElement(name = "getEnumeration", namespace = "http://org.kuali.student/dictonary")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getEnumeration", namespace = "http://org.kuali.student/dictonary", propOrder = {"enumerationKey","enumContextKey","contextValue"})

public class GetEnumeration {

    @XmlElement(name = "enumerationKey")
    private java.lang.String enumerationKey;
    @XmlElement(name = "enumContextKey")
    private java.lang.String enumContextKey;
    @XmlElement(name = "contextValue")
    private java.lang.String contextValue;

    public java.lang.String getEnumerationKey() {
        return this.enumerationKey;
    }

    public void setEnumerationKey(java.lang.String newEnumerationKey)  {
        this.enumerationKey = newEnumerationKey;
    }

    public java.lang.String getEnumContextKey() {
        return this.enumContextKey;
    }

    public void setEnumContextKey(java.lang.String newEnumContextKey)  {
        this.enumContextKey = newEnumContextKey;
    }

    public java.lang.String getContextValue() {
        return this.contextValue;
    }

    public void setContextValue(java.lang.String newContextValue)  {
        this.contextValue = newContextValue;
    }

}

