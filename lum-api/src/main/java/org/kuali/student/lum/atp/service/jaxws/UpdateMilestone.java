
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

@XmlRootElement(name = "updateMilestone", namespace = "http://org.kuali.student/lum/atp")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateMilestone", namespace = "http://org.kuali.student/lum/atp", propOrder = {"milestoneKey","milestoneInfo"})

public class UpdateMilestone {

    @XmlElement(name = "milestoneKey")
    private java.lang.String milestoneKey;
    @XmlElement(name = "milestoneInfo")
    private org.kuali.student.lum.atp.dto.MilestoneInfo milestoneInfo;

    public java.lang.String getMilestoneKey() {
        return this.milestoneKey;
    }

    public void setMilestoneKey(java.lang.String newMilestoneKey)  {
        this.milestoneKey = newMilestoneKey;
    }

    public org.kuali.student.lum.atp.dto.MilestoneInfo getMilestoneInfo() {
        return this.milestoneInfo;
    }

    public void setMilestoneInfo(org.kuali.student.lum.atp.dto.MilestoneInfo newMilestoneInfo)  {
        this.milestoneInfo = newMilestoneInfo;
    }

}

