
package edu.umd.ks.poc.lum.lu.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by the CXF 2.0.4-incubator
 * Fri Apr 18 16:30:07 EDT 2008
 * Generated source version: 2.0.4-incubator
 * 
 */

@XmlRootElement(name = "deleteClu", namespace = "http://edu.umd.ks/poc/lum/lu")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deleteClu", namespace = "http://edu.umd.ks/poc/lum/lu")

public class DeleteClu {

    @XmlElement(name = "cluId")
    private java.lang.String cluId;

    public java.lang.String getCluId() {
        return this.cluId;
    }
    
    public void setCluId( java.lang.String newCluId ) {
        this.cluId = newCluId;
    }
    
}

