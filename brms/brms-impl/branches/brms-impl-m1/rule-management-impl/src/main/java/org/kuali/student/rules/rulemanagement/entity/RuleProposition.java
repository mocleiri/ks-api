/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.rules.rulemanagement.entity;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.kuali.student.poc.common.util.UUIDHelper;
import org.kuali.student.rules.internal.common.entity.ComparisonOperator;

/**
 * Contains meta data about the WHEN part of Drool rules. The Rule Proposition consists of left hand side, operator and right
 * hand side of a given rule.
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 */
@Entity
@Table(name = "RuleProposition_T")
public class RuleProposition {

    @Id
    private String id;
    private String name;
    private String description;
    private String failureMessage;
    private String comparisonDataType;
    
    @OneToOne(cascade = {CascadeType.ALL} )
    private LeftHandSide leftHandSide;
    
    @Enumerated(EnumType.STRING)
    private ComparisonOperator operator;
    
    @Embedded
    private RightHandSide rightHandSide;


    /**
     * @return the name
     */
    public final String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public final void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public final String getDescription() {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public final void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the leftHandSide
     */
    public final LeftHandSide getLeftHandSide() {
        return leftHandSide;
    }

    /**
     * @param leftHandSide
     *            the leftHandSide to set
     */
    public final void setLeftHandSide(LeftHandSide leftHandSide) {
        this.leftHandSide = leftHandSide;
    }

    /**
     * @return the operator
     */
    public final ComparisonOperator getOperator() {
        return operator;
    }

    /**
     * @param operator
     *            the operator to set
     */
    public final void setOperator(ComparisonOperator operator) {
        this.operator = operator;
    }

    /**
     * @return the rightHandSide
     */
    public final RightHandSide getRightHandSide() {
        return rightHandSide;
    }

    /**
     * @param rightHandSide
     *            the rightHandSide to set
     */
    public final void setRightHandSide(RightHandSide rightHandSide) {
        this.rightHandSide = rightHandSide;
    }

    /**
     * AutoGenerate the Id
     */
    @PrePersist
    public void prePersist() {
        this.id = UUIDHelper.genStringUUID();
    }

    /**
     * @return the id
     */
    public final String getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public final void setId(String id) {
        this.id = id;
    }

    /**
     * @return the failureMessage
     */
    public final String getFailureMessage() {
        return failureMessage;
    }

    /**
     * @param failureMessage
     *            the failureMessage to set
     */
    public final void setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
    }

    /**
     * @return the comparisonDataType
     */
    public String getComparisonDataType() {
        return comparisonDataType;
    }

    /**
     * @param comparisonDataType the comparisonDataType to set
     */
    public void setComparisonDataType(String comparisonDataType) {
        this.comparisonDataType = comparisonDataType;
    }    
}
