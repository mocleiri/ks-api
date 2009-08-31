
package org.kuali.student.lum.lu.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.1.4
 * Tue Feb 24 12:25:30 EST 2009
 * Generated source version: 2.1.4
 */

@XmlRootElement(name = "getAllowedLrScaleTypesForLuType", namespace = "http://student.kuali.org/wsdl/lu")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getAllowedLrScaleTypesForLuType", namespace = "http://student.kuali.org/wsdl/lu", propOrder = {"luTypeKey","lrTypeKey"})

public class GetAllowedLrScaleTypesForLuType {

    @XmlElement(name = "luTypeKey")
    private java.lang.String luTypeKey;
    @XmlElement(name = "lrTypeKey")
    private java.lang.String lrTypeKey;

    public java.lang.String getLuTypeKey() {
        return this.luTypeKey;
    }

    public void setLuTypeKey(java.lang.String newLuTypeKey)  {
        this.luTypeKey = newLuTypeKey;
    }

    public java.lang.String getLrTypeKey() {
        return this.lrTypeKey;
    }

    public void setLrTypeKey(java.lang.String newLrTypeKey)  {
        this.lrTypeKey = newLrTypeKey;
    }

}

