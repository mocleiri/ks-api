
package org.kuali.student.lum.lrc.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.2
 * Tue Apr 21 14:42:30 PDT 2009
 * Generated source version: 2.2
 */

@XmlRootElement(name = "getResultComponentIdsByResultComponentTypeResponse", namespace = "http://service.lrc.lum.student.kuali.org/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getResultComponentIdsByResultComponentTypeResponse", namespace = "http://service.lrc.lum.student.kuali.org/")

public class GetResultComponentIdsByResultComponentTypeResponse {

    @XmlElement(name = "return")
    private java.util.List<String> _return;

    public java.util.List<String> getReturn() {
        return this._return;
    }

    public void setReturn(java.util.List<String> new_return)  {
        this._return = new_return;
    }

}

