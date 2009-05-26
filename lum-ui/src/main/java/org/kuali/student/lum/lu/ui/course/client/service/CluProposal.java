/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.lum.lu.ui.course.client.service;

import java.util.List;

import org.kuali.student.core.dto.HasTypeState;
import org.kuali.student.core.dto.Idable;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.ui.course.client.service.dto.ProposalInfo;

/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in. 
 * 
 * @author Kuali Student Team
 *
 */
public class CluProposal implements Idable, HasTypeState{

    CluInfo cluInfo;
    List<CluInfo> activities;
    ProposalInfo proposalInfo;
    
    /**
     * @see org.kuali.student.core.dto.Idable#getId()
     */
    @Override
    public String getId() {
        return proposalInfo.getId();
    }
    
    /**
=     * @see org.kuali.student.core.dto.Idable#setId(java.lang.String)
     */
    @Override
    public void setId(String id) {
        proposalInfo.setId(id);
        
    }

    public CluInfo getCluInfo() {
        return cluInfo;
    }

    public void setCluInfo(CluInfo cluInfo) {
        this.cluInfo = cluInfo;
    }

    public List<CluInfo> getActivities() {
        return activities;
    }

    public void setActivities(List<CluInfo> activities) {
        this.activities = activities;
    }

    public ProposalInfo getProposalInfo() {
        return proposalInfo;
    }

    public void setProposalInfo(ProposalInfo proposalInfo) {
        this.proposalInfo = proposalInfo;
    }

    /**
     * @see org.kuali.student.core.dto.HasTypeState#getState()
     */
    @Override
    public String getState() {
        return proposalInfo.getState();
    }

    /**
     * @see org.kuali.student.core.dto.HasTypeState#getType()
     */
    @Override
    public String getType() {
        return proposalInfo.getType();
    }

    /**
     * @see org.kuali.student.core.dto.HasTypeState#setState(java.lang.String)
     */
    @Override
    public void setState(String type) {
        proposalInfo.setState(type);
    }

    /**
     * @see org.kuali.student.core.dto.HasTypeState#setType(java.lang.String)
     */
    @Override
    public void setType(String type) {
        proposalInfo.setType(type);
    }
    
    
}
