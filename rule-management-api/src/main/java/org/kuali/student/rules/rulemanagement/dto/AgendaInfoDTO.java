/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.rules.rulemanagement.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
 * Maps a set of Business Rule Types to form an AgendaInfoDTO and a specific instance of Business Rules form an Agenda for this
 * AgendaInfoDTO
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class AgendaInfoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String agendaTypeKey;

    @XmlElement
    private AgendaDeterminationInfoDTO agendaDeterminationInfo;

    @XmlElement(name = "businessRuletype")
    @XmlElementWrapper(name = "businessRuleTypeInfoList")
    private List<BusinessRuleTypeInfoDTO> businessRuleTypeInfoList;

    @XmlElement
    private String agendaOrchestration;

    /**
     * @return the agendaTypeKey
     */
    public String getAgendaTypeKey() {
        return agendaTypeKey;
    }

    /**
     * @param agendaTypeKey the agendaTypeKey to set
     */
    public void setAgendaTypeKey(String agendaType) {
        this.agendaTypeKey = agendaType;
    }

    /**
     * @return the agendaDeterminationInfo
     */
    public AgendaDeterminationInfoDTO getAgendaDeterminationInfo() {
        return agendaDeterminationInfo;
    }

    /**
     * @param agendaDeterminationInfo the agendaDeterminationInfo to set
     */
    public void setAgendaDeterminationInfo(AgendaDeterminationInfoDTO agendaDeterminationInfo) {
        this.agendaDeterminationInfo = agendaDeterminationInfo;
    }

    /**
     * @return the businessRuleTypeInfoList
     */
    public List<BusinessRuleTypeInfoDTO> getBusinessRuleTypeInfoList() {
        return businessRuleTypeInfoList;
    }

    /**
     * @param businessRuleTypeInfoList
     *            the businessRuleTypeInfoList to set
     */
    public void setBusinessRuleTypeInfoList(List<BusinessRuleTypeInfoDTO> businessRuleTypeList) {
        this.businessRuleTypeInfoList = businessRuleTypeList;
    }

    /**
     * @return the agendaOrchestration
     */
    public String getAgendaOrchestration() {
        return agendaOrchestration;
    }

    /**
     * @param agendaOrchestration the agendaOrchestration to set
     */
    public void setAgendaOrchestration(String agendaOrchestration) {
        this.agendaOrchestration = agendaOrchestration;
    }
}
