
package org.kuali.student.brms.rulemanagement.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.1.2
 * Thu Jan 15 15:29:33 EST 2009
 * Generated source version: 2.1.2
 */

@XmlRootElement(name = "fetchAgendaInfo", namespace = "http://student.kuali.org/wsdl/brms/RuleManagement")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fetchAgendaInfo", namespace = "http://student.kuali.org/wsdl/brms/RuleManagement", propOrder = {"agendaTypeKey","agendaDeterminationInfo"})

public class FetchAgendaInfo {

    @XmlElement(name = "agendaTypeKey")
    private java.lang.String agendaTypeKey;
    @XmlElement(name = "agendaDeterminationInfo")
    private org.kuali.student.brms.rulemanagement.dto.AgendaDeterminationInfoDTO agendaDeterminationInfo;

    public java.lang.String getAgendaTypeKey() {
        return this.agendaTypeKey;
    }

    public void setAgendaTypeKey(java.lang.String newAgendaTypeKey)  {
        this.agendaTypeKey = newAgendaTypeKey;
    }

    public org.kuali.student.brms.rulemanagement.dto.AgendaDeterminationInfoDTO getAgendaDeterminationInfo() {
        return this.agendaDeterminationInfo;
    }

    public void setAgendaDeterminationInfo(org.kuali.student.brms.rulemanagement.dto.AgendaDeterminationInfoDTO newAgendaDeterminationInfo)  {
        this.agendaDeterminationInfo = newAgendaDeterminationInfo;
    }

}

