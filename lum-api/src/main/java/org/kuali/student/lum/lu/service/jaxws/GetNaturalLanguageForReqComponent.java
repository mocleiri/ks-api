
package org.kuali.student.lum.lu.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.2
 * Mon Mar 30 15:25:45 EDT 2009
 * Generated source version: 2.2
 */

@XmlRootElement(name = "getNaturalLanguageForReqComponent", namespace = "http://student.kuali.org/lum/lu")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getNaturalLanguageForReqComponent", namespace = "http://student.kuali.org/lum/lu", propOrder = {"reqComponentId","nlUsageTypeKey"})

public class GetNaturalLanguageForReqComponent {

    @XmlElement(name = "reqComponentId")
    private java.lang.String reqComponentId;
    @XmlElement(name = "nlUsageTypeKey")
    private java.lang.String nlUsageTypeKey;

    public java.lang.String getReqComponentId() {
        return this.reqComponentId;
    }

    public void setReqComponentId(java.lang.String newReqComponentId)  {
        this.reqComponentId = newReqComponentId;
    }

    public java.lang.String getNlUsageTypeKey() {
        return this.nlUsageTypeKey;
    }

    public void setNlUsageTypeKey(java.lang.String newNlUsageTypeKey)  {
        this.nlUsageTypeKey = newNlUsageTypeKey;
    }

}

