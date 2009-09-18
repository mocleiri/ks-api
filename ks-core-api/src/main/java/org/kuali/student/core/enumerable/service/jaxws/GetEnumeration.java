
package org.kuali.student.core.enumerable.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.1.4
 * Fri Feb 13 09:40:30 EST 2009
 * Generated source version: 2.1.4
 */

@XmlRootElement(name = "getEnumeration", namespace = "http://student.kuali.org/wsdl/enumerable")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getEnumeration", namespace = "http://student.kuali.org/wsdl/enumerable", propOrder = {"enumerationKey","enumContextKey","contextValue","contextDate"})

public class GetEnumeration {

    @XmlElement(name = "enumerationKey")
    private java.lang.String enumerationKey;
    @XmlElement(name = "enumContextKey")
    private java.lang.String enumContextKey;
    @XmlElement(name = "contextValue")
    private java.lang.String contextValue;
    @XmlElement(name = "contextDate")
    private java.util.Date contextDate;

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

    public java.util.Date getContextDate() {
        return this.contextDate;
    }

    public void setContextDate(java.util.Date newContextDate)  {
        this.contextDate = newContextDate;
    }

}

