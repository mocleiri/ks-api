/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

package org.kuali.student.lum.lu.ui.course.server.gwt;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.kuali.student.common.dto.ContextInfo;
import org.kuali.student.common.dto.StatusInfo;
import org.kuali.student.common.ui.client.service.DataSaveResult;
import org.kuali.student.common.ui.server.gwt.DataGwtServlet;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.lum.lu.ui.course.client.requirements.CourseRequirementsDataModel;
import org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService;

public class CreditCourseProposalRpcGwtServlet extends DataGwtServlet implements
        CreditCourseProposalRpcService {

	final static Logger LOG = Logger.getLogger(CreditCourseProposalRpcGwtServlet.class);

	private static final long serialVersionUID = 1L;
	private CopyCourseServiceImpl copyCourseService;
	
	@Override
	public DataSaveResult createCopyCourse(String originalCluId, ContextInfo contextInfo)
			throws Exception {
		try {
			return copyCourseService.createCopyCourse(originalCluId, contextInfo);
		} catch (Exception e) {
			LOG.error("Error copying course with id:" + originalCluId, e);
			throw e;
		}
	}

	@Override
	public DataSaveResult createCopyCourseProposal(String originalProposalId, ContextInfo contextInfo)
			throws Exception {
		try {
			return copyCourseService.createCopyCourseProposal(originalProposalId, contextInfo);
		} catch (Exception e) {
			LOG.error("Error copying proposal with id:" + originalProposalId, e);
			throw e;
		}

	}
	
    @Override
    public List<StatementTreeViewInfo> getCourseStatements(String courseId, String nlUsageTypeKey, String language, ContextInfo contextInfo) throws Exception {
    	throw new UnsupportedOperationException("This method is not implemented.");
    }

    @Override
    public Map<Integer, StatementTreeViewInfo> storeCourseStatements(String courseId, String courseState, Map<Integer, CourseRequirementsDataModel.requirementState> states,
    			Map<Integer, StatementTreeViewInfo> rules, ContextInfo contextInfo) throws Exception {
    	throw new UnsupportedOperationException("This method is not implemented.");
    }

    @Override
    public StatementTreeViewInfo createCourseStatement(String courseId, String courseState, StatementTreeViewInfo statementTreeViewInfo, ContextInfo contextInfo) throws Exception {
    	throw new UnsupportedOperationException("This method is not implemented.");
    }

    @Override
    public StatusInfo deleteCourseStatement(String courseId, StatementTreeViewInfo statementTreeViewInfo, ContextInfo contextInfo) throws Exception {
    	throw new UnsupportedOperationException("This method is not implemented.");
    }
    
    @Override
    public StatementTreeViewInfo updateCourseStatement(String courseId, String courseState, StatementTreeViewInfo statementTreeViewInfo, ContextInfo contextInfo) throws Exception {
    	throw new UnsupportedOperationException("This method is not implemented.");
    }
    
    @Override
    public StatusInfo changeState(String courseId, String newState, ContextInfo contextInfo) throws Exception {
    	throw new UnsupportedOperationException("This method is not implemented.");
    }
    
    public StatusInfo changeState(String courseId, String newState, String prevEndTerm, ContextInfo contextInfo) throws Exception {
    	throw new UnsupportedOperationException("This method is not implemented.");
    }
	
    @Override
	public Boolean isLatestVersion(String versionIndId, Long versionSequenceNumber, ContextInfo contextInfo) throws Exception {
    	throw new UnsupportedOperationException("This method is not implemented.");
	}

	public void setCopyCourseService(CopyCourseServiceImpl copyCourseService) {
		this.copyCourseService = copyCourseService;
	}

}