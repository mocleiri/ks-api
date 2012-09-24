/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 *
 * Created by Charles on 9/21/12
 */
package org.kuali.student.enrollment.class2.courseoffering.form;

import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.class2.courseoffering.dto.SocRolloverResultItemWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class DiagnoseRolloverForm extends UifFormBase {
    // Properties of the form
    private String targetTermCode;
    private String sourceTermCode;
    private String courseOfferingCode;
    private String displayedTargetTermCode;
    private String targetTermStartDate;
    private String targetTermEndDate;
    private String targetLastRolloverDate;
    private String displayedSourceTermCode;
    private String sourceTermStartDate;
    private String sourceTermEndDate;
    private String statusField;
    private String displayedCourseOfferingCode;
    private String coCodeId;
    private String courseOfferingTitle;
    private TermInfo sourceTerm;
    private TermInfo targetTerm;
    private boolean isRolloverButtonDisabled;
    private boolean isGoTargetButtonDisabled;
    private boolean isGoCocDisabled; // Coc = Course Offering Code
    private boolean isCourseOfferingInfoRendered;

    public DiagnoseRolloverForm(){
        sourceTermCode = "";
        resetForm();
    }

    public String getCourseOfferingTitle() {
        return courseOfferingTitle;
    }

    public void setCourseOfferingTitle(String courseOfferingTitle) {
        this.courseOfferingTitle = courseOfferingTitle;
    }

    public String getDisplayedCourseOfferingCode() {
        return displayedCourseOfferingCode;
    }

    public void setDisplayedCourseOfferingCode(String displayedCourseOfferingCode) {
        this.displayedCourseOfferingCode = displayedCourseOfferingCode;
    }

    public String getCoCodeId() {
        return coCodeId;
    }

    public void setCoCodeId(String coCodeId) {
        this.coCodeId = coCodeId;
    }

    public void setIsCourseOfferingInfoRendered(boolean value) {
        isCourseOfferingInfoRendered = value;
    }

    public boolean getIsCourseOfferingInfoRendered() {
        return isCourseOfferingInfoRendered;
    }

    public boolean getIsGoCocDisabled() {
        return isGoCocDisabled;
    }

    public void setIsGoCocDisabled(boolean value) {
        isGoCocDisabled = value;
    }

    public boolean getIsRolloverButtonDisabled() {
        return isRolloverButtonDisabled;
    }

    public void setIsRolloverButtonDisabled(boolean value) {
        isRolloverButtonDisabled = value;
    }

    public boolean getIsGoTargetButtonDisabled() {
        return isGoTargetButtonDisabled;
    }

    public void setIsGoTargetButtonDisabled(boolean value) {
        isGoTargetButtonDisabled = value;
    }

    public String getTargetTermCode() {
        return targetTermCode;
    }

    public void setTargetTermCode(String targetTermCode) {
        this.targetTermCode = targetTermCode;
    }

    public String getSourceTermCode() {
        return sourceTermCode;
    }

    public void setSourceTermCode(String sourceTermCode) {
        this.sourceTermCode = sourceTermCode;
    }

    public String getCourseOfferingCode() {
        return courseOfferingCode;
    }

    public void setCourseOfferingCode(String courseOfferingCode) {
        this.courseOfferingCode = courseOfferingCode;
    }

    public String getDisplayedTargetTermCode() {
        return displayedTargetTermCode;
    }

    public void setDisplayedTargetTermCode(String displayedTargetTermCode) {
        this.displayedTargetTermCode = displayedTargetTermCode;
    }

    public String getTargetTermStartDate() {
        return targetTermStartDate;
    }

    public void setTargetTermStartDate(String targetTermStartDate) {
        this.targetTermStartDate = targetTermStartDate;
    }

    public String getTargetTermEndDate() {
        return targetTermEndDate;
    }

    public void setTargetTermEndDate(String targetTermEndDate) {
        this.targetTermEndDate = targetTermEndDate;
    }

    public String getTargetLastRolloverDate() {
        return targetLastRolloverDate;
    }

    public void setTargetLastRolloverDate(String targetLastRolloverDate) {
        this.targetLastRolloverDate = targetLastRolloverDate;
    }

    public String getDisplayedSourceTermCode() {
        return displayedSourceTermCode;
    }

    public void setDisplayedSourceTermCode(String displayedSourceTermCode) {
        this.displayedSourceTermCode = displayedSourceTermCode;
    }

    public String getSourceTermStartDate() {
        return sourceTermStartDate;
    }

    public void setSourceTermStartDate(String sourceTermStartDate) {
        this.sourceTermStartDate = sourceTermStartDate;
    }

    public String getSourceTermEndDate() {
        return sourceTermEndDate;
    }

    public void setSourceTermEndDate(String sourceTermEndDate) {
        this.sourceTermEndDate = sourceTermEndDate;
    }

    public String getStatusField() {
        return statusField;
    }

    public void setStatusField(String statusField) {
        this.statusField = statusField;
    }

    public TermInfo getSourceTerm() {
        return sourceTerm;
    }

    public void setSourceTerm(TermInfo sourceTerm) {
        this.sourceTerm = sourceTerm;
    }

    public TermInfo getTargetTerm() {
        return targetTerm;
    }

    public void setTargetTerm(TermInfo targetTerm) {
        this.targetTerm = targetTerm;
    }

    public void resetForm() {
        targetTermCode = "";
        courseOfferingCode = "";
        displayedTargetTermCode = "";
        targetTermStartDate = "";
        targetTermEndDate = "";
        targetLastRolloverDate = "";
        displayedSourceTermCode = "";
        sourceTermStartDate = "";
        sourceTermEndDate = "";
        statusField = "";
        displayedCourseOfferingCode = "";
        coCodeId = "";

        isRolloverButtonDisabled = true;
        isGoTargetButtonDisabled = true;
        isGoCocDisabled = true;
        isCourseOfferingInfoRendered = false;
    }

    public void alertSourceTermValid(boolean value) {
        // if target term is valid, we can set isGoCocDisabled to false
        if (value) {
            isGoCocDisabled = false; // activate CoC button
        } else {
            isGoCocDisabled = true;
            isGoTargetButtonDisabled = true;
            isRolloverButtonDisabled = true;
        }
    }

    public void alertCourseOfferingInfoValid(boolean value) {
        if (value) {
            isCourseOfferingInfoRendered = true;
        } else {
            isGoTargetButtonDisabled = true;
            isRolloverButtonDisabled = true;
        }
    }

    public void alertTargetTermValid(boolean value) {
        // if target term is valid, we can set isGoCocDisabled to false
        if (value) {
            isGoCocDisabled = false; // activate CoC button
        } else {
            isGoCocDisabled = true;
            isGoTargetButtonDisabled = true;
            isRolloverButtonDisabled = true;
        }
    }
}
