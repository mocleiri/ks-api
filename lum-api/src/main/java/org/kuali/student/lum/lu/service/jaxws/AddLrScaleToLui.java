
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

@XmlRootElement(name = "addLrScaleToLui", namespace = "http://student.kuali.org/lum/lu")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addLrScaleToLui", namespace = "http://student.kuali.org/lum/lu", propOrder = {"lrScaleTypeKey","lrTypeKey","luiId"})

public class AddLrScaleToLui {

    @XmlElement(name = "lrScaleTypeKey")
    private java.lang.String lrScaleTypeKey;
    @XmlElement(name = "lrTypeKey")
    private java.lang.String lrTypeKey;
    @XmlElement(name = "luiId")
    private java.lang.String luiId;

    public java.lang.String getLrScaleTypeKey() {
        return this.lrScaleTypeKey;
    }

    public void setLrScaleTypeKey(java.lang.String newLrScaleTypeKey)  {
        this.lrScaleTypeKey = newLrScaleTypeKey;
    }

    public java.lang.String getLrTypeKey() {
        return this.lrTypeKey;
    }

    public void setLrTypeKey(java.lang.String newLrTypeKey)  {
        this.lrTypeKey = newLrTypeKey;
    }

    public java.lang.String getLuiId() {
        return this.luiId;
    }

    public void setLuiId(java.lang.String newLuiId)  {
        this.luiId = newLuiId;
    }

}

