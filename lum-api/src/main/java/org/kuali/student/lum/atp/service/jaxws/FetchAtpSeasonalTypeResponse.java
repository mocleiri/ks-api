
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

@XmlRootElement(name = "fetchAtpSeasonalTypeResponse", namespace = "http://org.kuali.student/lum/atp")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fetchAtpSeasonalTypeResponse", namespace = "http://org.kuali.student/lum/atp")

public class FetchAtpSeasonalTypeResponse {

    @XmlElement(name = "return")
    private org.kuali.student.lum.atp.dto.AtpSeasonalTypeInfo _return;

    public org.kuali.student.lum.atp.dto.AtpSeasonalTypeInfo getReturn() {
        return this._return;
    }

    public void setReturn(org.kuali.student.lum.atp.dto.AtpSeasonalTypeInfo new_return)  {
        this._return = new_return;
    }

}

