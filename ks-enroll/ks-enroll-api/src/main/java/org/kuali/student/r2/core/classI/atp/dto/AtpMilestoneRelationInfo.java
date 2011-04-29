/*
 * Copyright 2009 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.r2.core.classI.atp.dto;

import java.io.Serializable;
import java.util.List;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.dto.RelationshipInfo;
import org.kuali.student.r2.common.infc.ModelBuilder;
import org.kuali.student.r2.core.classI.atp.infc.AtpMilestoneRelation;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AtpMilestoneRelationInfo", propOrder = {"id", "typeKey", "stateKey", "atpKey", "milestoneKey", "effectiveDate", "expirationDate", "metaInfo", "attributes", "_futureElements"})
public class AtpMilestoneRelationInfo extends RelationshipInfo implements AtpMilestoneRelation, Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private final String atpKey;
    @XmlElement
    private final String milestoneKey;
    @XmlAnyElement
    private final List<Element> _futureElements;

    private AtpMilestoneRelationInfo() {
        atpKey = null;
        milestoneKey = null;
        _futureElements = null;
    }

    /**
     * Constructs a new AtpMilestoneRelationInfo from another
     * AtpMilestoneRelation.
     *
     * @param amr the AtpMilestoneRelation to copy
     */
    public AtpMilestoneRelationInfo(AtpMilestoneRelation amr) {
        super(amr);
        this.atpKey = amr.getAtpKey();
        this.milestoneKey = amr.getMilestoneKey();
        _futureElements = null;
    }

    @Override
    public String getAtpKey() {
        return atpKey;
    }

    @Override
    public String getMilestoneKey() {
        return milestoneKey;
    }

    /**
     * The builder class for this AtpMilestoneRelationInfo.
     */
    public static class Builder extends RelationshipInfo.Builder implements ModelBuilder<AtpMilestoneRelationInfo>, AtpMilestoneRelation {

        private String atpKey;
        private String milestoneKey;

        /**
         * Constructs a new builder.
         */
        public Builder() {
        }

        /**
         *  Constructs a new builder initialized from another
         *  AtpMilestoneRelation.
         */
        public Builder(AtpMilestoneRelation amrInfo) {
            super(amrInfo);
            this.atpKey = amrInfo.getAtpKey();
            this.milestoneKey = amrInfo.getMilestoneKey();
        }

        @Override
        public AtpMilestoneRelationInfo build() {
            return new AtpMilestoneRelationInfo(this);
        }

        @Override
        public String getAtpKey() {
            return atpKey;
        }

        public void setAtpKey(String atpKey) {
            this.atpKey = atpKey;
        }

        @Override
        public String getMilestoneKey() {
            return milestoneKey;
        }

        public void setMilestoneKey(String milestoneKey) {
            this.milestoneKey = milestoneKey;
        }
    }
}
