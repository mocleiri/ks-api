
package org.kuali.student.core.atp.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.2
 * Tue Mar 31 14:06:06 EDT 2009
 * Generated source version: 2.2
 */

@XmlRootElement(name = "getAtpSeasonalType", namespace = "http://student.kuali.org/core/atp")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getAtpSeasonalType", namespace = "http://student.kuali.org/core/atp")

public class GetAtpSeasonalType {

    @XmlElement(name = "atpSeasonalTypeKey")
    private java.lang.String atpSeasonalTypeKey;

    public java.lang.String getAtpSeasonalTypeKey() {
        return this.atpSeasonalTypeKey;
    }

    public void setAtpSeasonalTypeKey(java.lang.String newAtpSeasonalTypeKey)  {
        this.atpSeasonalTypeKey = newAtpSeasonalTypeKey;
    }

}

