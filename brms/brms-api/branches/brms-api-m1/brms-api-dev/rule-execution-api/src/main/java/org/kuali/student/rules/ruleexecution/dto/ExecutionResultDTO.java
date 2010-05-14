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
package org.kuali.student.rules.ruleexecution.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class ExecutionResultDTO implements java.io.Serializable {
	/** Class serial version uid */
    private static final long serialVersionUID = 1L;

    @XmlElement
	private Boolean executionResult = null;

    @XmlElement
	private PropositionReportDTO propositionReport;

    @XmlElement
	private String executionLog;
	
    @XmlElement
	private String errorMessage;

    /**
     * Constructor
     */
	public ExecutionResultDTO() {
	}

	public void setExecutionLog(final String log) {
		this.executionLog = log;
	}

	public String getExecutionLog() {
		return this.executionLog;
	}

	public Boolean getExecutionResult() {
		return executionResult;
	}

	public void setExecutionResult(final Boolean executionResult) {
		this.executionResult = executionResult;
	}

	public PropositionReportDTO getReport() {
		return propositionReport;
	}

	public void setReport(final PropositionReportDTO report) {
		this.propositionReport = report;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public String toString() {
		return "ExecutionResultDTO[executionResult=" + this.executionResult 
			+ ", propositionReport=" + this.propositionReport + "]";
 	}
}
