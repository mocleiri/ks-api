/*
 * Copyright 2011 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.core.process.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.infc.DateRange;
import org.kuali.student.r2.common.dto.RelationshipInfo;
import org.kuali.student.r2.common.dto.DateRangeInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.core.process.infc.Instruction;

import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InstructionInfo", propOrder = { "id", "typeKey", "stateKey", 
                "effectiveDate", "expirationDate",
                "processKey", "checkId", "appliedOrgIds", 
                "appliedPopulationIds", "appliedAtpTypeKeys",
                "appliedAtpKeys", "appliedDateRanges",
                "message", "position", "isWarning", 
                "continueOnFail", "isExemptable", 
                "meta", "attributes",
		"_futureElements" })

public class InstructionInfo extends RelationshipInfo 
    implements Instruction, Serializable {

    private static final long serialVersionUID = 1L;
    
    @XmlElement 
    private String processKey;

    @XmlElement 
    private String checkId;

    @XmlElement 
    private List<String> appliedOrgIds;

    @XmlElement 
    private List<String> appliedPopulationIds;

    @XmlElement 
    private List<String> appliedAtpTypeKeys;

    @XmlElement 
    private List<String> appliedAtpKeys;

    @XmlElement 
    private List<DateRangeInfo> appliedDateRanges;

    @XmlElement 
    private RichTextInfo message;

    @XmlElement 
    private Integer position;

    @XmlElement 
    private Boolean isWarning;

    @XmlElement 
    private Boolean continueOnFail;

    @XmlElement 
    private Boolean isExemptable;

    @XmlAnyElement
    private List<Element> _futureElements;
    

    /**
     * Constructs a new InstructionInfo.
     */
    public InstructionInfo() {
    }

    /**
     * Constructs a new InstructionInfo from another Instruction.
     * 
     * @param instruction the Instruction to copy
     */
    public InstructionInfo(Instruction instruction) {
        super(instruction);

        if (instruction != null) {
            this.processKey= instruction.getProcessKey();
            this.checkId = instruction.getCheckId();
            if (instruction.getAppliedOrgIds() != null) {
                this.appliedOrgIds = new ArrayList<String>(instruction.getAppliedOrgIds());
            }

            if (instruction.getAppliedPopulationIds() != null) {
                this.appliedPopulationIds = new ArrayList<String>(instruction.getAppliedPopulationIds());
            }

            if (instruction.getAppliedAtpTypeKeys() != null) {
                this.appliedAtpTypeKeys = new ArrayList<String>(instruction.getAppliedAtpTypeKeys());
            }

            if (instruction.getAppliedAtpKeys() != null) {
                this.appliedAtpKeys = new ArrayList<String>(instruction.getAppliedAtpKeys());
            }

            if (instruction.getAppliedDateRanges() != null) {
                this.appliedDateRanges = new ArrayList<DateRangeInfo>();
                for (DateRange dr : instruction.getAppliedDateRanges()) {
                    this.appliedDateRanges.add(new DateRangeInfo(dr));
                }
            }

            this.message = new RichTextInfo(instruction.getMessage());
            this.position = instruction.getPosition();
            this.isWarning = instruction.getIsWarning();
            this.continueOnFail = instruction.getContinueOnFail();
            this.isExemptable = instruction.getIsExemptable();
        }
    }

    @Override
    public String getProcessKey() {
        return this.processKey;
    }

    public void setProcessKey(String processKey) {
        this.processKey = processKey;
    }

    @Override
    public String getCheckId() {
        return this.checkId;
    }

    public void setCheckId(String checkId) {
        this.checkId = checkId;
    }

    @Override
    public List<String> getAppliedOrgIds() {
        if (this.appliedOrgIds == null) {
            this.appliedOrgIds = new ArrayList<String>();
        }

        return this.appliedOrgIds;
    }

    public void setAppliedOrgIds(List<String> appliedOrgIds) {
        this.appliedOrgIds = appliedOrgIds;
    }

    @Override
    public List<String> getAppliedPopulationIds() {
        if (this.appliedPopulationIds == null) {
            this.appliedPopulationIds = new ArrayList<String>();
        }

        return this.appliedPopulationIds;
    }

    public void setAppliedPopulationIds(List<String> appliedPopulationIds) {
        this.appliedPopulationIds = appliedPopulationIds;
    }

    @Override
    public List<String> getAppliedAtpTypeKeys() {
        if (this.appliedAtpTypeKeys == null) {
            this.appliedAtpTypeKeys = new ArrayList<String>();
        }

        return this.appliedAtpTypeKeys;
    }

    public void setAppliedAtpTypeKeys(List<String> appliedAtpTypeKeys) {
        this.appliedAtpTypeKeys = appliedAtpTypeKeys;
    }

    @Override
    public List<String> getAppliedAtpKeys() {
        if (this.appliedAtpKeys == null) {
            this.appliedAtpKeys = new ArrayList<String>();
        }

        return this.appliedAtpKeys;
    }

    public void setAppliedAtpKeys(List<String> appliedAtpKeys) {
        this.appliedAtpKeys = appliedAtpKeys;
    }

    @Override
    public List<DateRangeInfo> getAppliedDateRanges() {
        if (this.appliedDateRanges == null) {
            this.appliedDateRanges = new ArrayList<DateRangeInfo>();
        }

        return this.appliedDateRanges;
    }

    public void setAppliedDateRanges(List<DateRangeInfo> appliedDateRanges) {
        this.appliedDateRanges = appliedDateRanges;
    }

    @Override
    public RichTextInfo getMessage() {
        return this.message;
    }

    public void setMessage(RichTextInfo message) {
        this.message = message;
    }

    @Override
    public Integer getPosition() {
        return this.position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    @Override
    public Boolean getIsWarning() {
        return this.isWarning;
    }

    public void setIsWarning(Boolean isWarning) {
        this.isWarning = isWarning;
    }

    @Override
    public Boolean getContinueOnFail() {
        return this.continueOnFail;
    }

    public void setContinueOnFail(Boolean continueOnFail) {
        this.continueOnFail = continueOnFail;
    }

    @Override
    public Boolean getIsExemptable() {
        return this.isExemptable;
    }

    public void setIsExemptable(Boolean isExemptable) {
        this.isExemptable = isExemptable;
    }
}
