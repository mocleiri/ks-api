
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

@XmlRootElement(name = "removeCluSetFromCluSet", namespace = "http://student.kuali.org/wsdl/lu")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "removeCluSetFromCluSet", namespace = "http://student.kuali.org/wsdl/lu", propOrder = {"cluSetId","removedCluSetId"})

public class RemoveCluSetFromCluSet {

    @XmlElement(name = "cluSetId")
    private java.lang.String cluSetId;
    @XmlElement(name = "removedCluSetId")
    private java.lang.String removedCluSetId;

    public java.lang.String getCluSetId() {
        return this.cluSetId;
    }

    public void setCluSetId(java.lang.String newCluSetId)  {
        this.cluSetId = newCluSetId;
    }

    public java.lang.String getRemovedCluSetId() {
        return this.removedCluSetId;
    }

    public void setRemovedCluSetId(java.lang.String newRemovedCluSetId)  {
        this.removedCluSetId = newRemovedCluSetId;
    }

}

