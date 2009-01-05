
package org.kuali.student.rules.rulemanagement.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.1.2
 * Mon Jan 05 10:43:34 EST 2009
 * Generated source version: 2.1.2
 */

@XmlRootElement(name = "fetchAgendaInfo", namespace = "http://student.kuali.org/wsdl/brms/RuleManagement")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fetchAgendaInfo", namespace = "http://student.kuali.org/wsdl/brms/RuleManagement", propOrder = {"agendaTypeKey","agendaInfoDeterminationStructure"})

public class FetchAgendaInfo {

    @XmlElement(name = "agendaTypeKey")
    private java.lang.String agendaTypeKey;
    @XmlElement(name = "agendaInfoDeterminationStructure")
    private org.kuali.student.rules.rulemanagement.dto.AgendaInfoDeterminationStructureDTO agendaInfoDeterminationStructure;

    public java.lang.String getAgendaTypeKey() {
        return this.agendaTypeKey;
    }

    public void setAgendaTypeKey(java.lang.String newAgendaTypeKey)  {
        this.agendaTypeKey = newAgendaTypeKey;
    }

    public org.kuali.student.rules.rulemanagement.dto.AgendaInfoDeterminationStructureDTO getAgendaInfoDeterminationStructure() {
        return this.agendaInfoDeterminationStructure;
    }

    public void setAgendaInfoDeterminationStructure(org.kuali.student.rules.rulemanagement.dto.AgendaInfoDeterminationStructureDTO newAgendaInfoDeterminationStructure)  {
        this.agendaInfoDeterminationStructure = newAgendaInfoDeterminationStructure;
    }

}

