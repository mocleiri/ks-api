
package org.kuali.student.r1.lum.lu.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.2
 * Wed Jun 16 17:52:50 PDT 2010
 * Generated source version: 2.2
 */

@XmlRootElement(name = "getCluSetTreeView", namespace = "http://student.kuali.org/wsdl/lu")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getCluSetTreeView", namespace = "http://student.kuali.org/wsdl/lu")

public class GetCluSetTreeView {

    @XmlElement(name = "cluSetId")
    private java.lang.String cluSetId;

    public java.lang.String getCluSetId() {
        return this.cluSetId;
    }

    public void setCluSetId(java.lang.String newCluSetId)  {
        this.cluSetId = newCluSetId;
    }

}

