
package org.kuali.student.lum.atp.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.1.3
 * Wed Nov 19 10:43:53 EST 2008
 * Generated source version: 2.1.3
 */

@XmlRootElement(name = "fetchAtpSeasonalType", namespace = "http://org.kuali.student/lum/atp")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fetchAtpSeasonalType", namespace = "http://org.kuali.student/lum/atp")

public class FetchAtpSeasonalType {

    @XmlElement(name = "atpSeasonalTypeKey")
    private java.lang.String atpSeasonalTypeKey;

    public java.lang.String getAtpSeasonalTypeKey() {
        return this.atpSeasonalTypeKey;
    }

    public void setAtpSeasonalTypeKey(java.lang.String newAtpSeasonalTypeKey)  {
        this.atpSeasonalTypeKey = newAtpSeasonalTypeKey;
    }

}

