package org.kuali.student.lum.workflow;
import java.security.InvalidParameterException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.namespace.QName;

import org.kuali.rice.core.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kew.actionitem.ActionItem;
import org.kuali.rice.kew.dto.DocumentContentDTO;
import org.kuali.rice.kew.dto.WorkflowIdDTO;
import org.kuali.rice.kew.postprocessor.ActionTakenEvent;
import org.kuali.rice.kew.postprocessor.AfterProcessEvent;
import org.kuali.rice.kew.postprocessor.BeforeProcessEvent;
import org.kuali.rice.kew.postprocessor.DeleteEvent;
import org.kuali.rice.kew.postprocessor.DocumentLockingEvent;
import org.kuali.rice.kew.postprocessor.DocumentRouteLevelChange;
import org.kuali.rice.kew.postprocessor.DocumentRouteStatusChange;
import org.kuali.rice.kew.postprocessor.PostProcessor;
import org.kuali.rice.kew.postprocessor.ProcessDocReport;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.kew.service.WorkflowDocument;
import org.kuali.rice.kew.service.WorkflowInfo;
import org.kuali.rice.kew.util.KEWConstants;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.service.LuService;
public class CluPostProcessor implements PostProcessor{

	private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
	.getLogger(CluPostProcessor.class);
	
	public ProcessDocReport afterProcess(AfterProcessEvent arg0)
			throws Exception {
        return new ProcessDocReport(true, "");
	}

	public ProcessDocReport beforeProcess(BeforeProcessEvent arg0)
			throws Exception {
        return new ProcessDocReport(true, "");
	}

	public ProcessDocReport doActionTaken(ActionTakenEvent arg0)
			throws Exception {
        return new ProcessDocReport(true, "");
	}

	public ProcessDocReport doDeleteRouteHeader(DeleteEvent arg0)
			throws Exception {
        return new ProcessDocReport(true, "");
	}

	public ProcessDocReport doRouteLevelChange(DocumentRouteLevelChange documentRouteLevelChange)
			throws Exception {
		/*
		Long routeHeaderId = documentRouteLevelChange.getRouteHeaderId();
		
		//Clear all the current Collab FYIs on the current document
        Collection<ActionItem> actionItems = KEWServiceLocator.getActionListService().getActionListForSingleDocument(routeHeaderId);
        for (Iterator<ActionItem> iterator = actionItems.iterator(); iterator.hasNext();) {
            ActionItem item = iterator.next();
            if(KEWConstants.ACTION_REQUEST_FYI_REQ.equals(item.getActionRequestCd())&&item.getRequestLabel()!=null
            		&&(item.getRequestLabel().startsWith("Co-Author")||
            		   item.getRequestLabel().startsWith("Commentor")||
            		   item.getRequestLabel().startsWith("Viewer")||
            		   item.getRequestLabel().startsWith("Delegate"))){
        		WorkflowIdDTO principalIdVO = new WorkflowIdDTO("admin");
        		WorkflowDocument workflowDocument = new WorkflowDocument(principalIdVO, item.getRouteHeaderId());
        		workflowDocument.clearFYI();
            }
        }
		
		//Clear all pending Collab request documents for this document
		Collection<Long> pendingCollabIds = KEWServiceLocator.getRouteHeaderService().findByDocTypeAndAppId("CluCollaboratorDocument", routeHeaderId.toString());
        if(pendingCollabIds!=null){
        	for(Long pendingCollabId:pendingCollabIds){
        		WorkflowIdDTO principalIdVO = new WorkflowIdDTO("admin");
        		WorkflowDocument workflowDocument = new WorkflowDocument(principalIdVO, pendingCollabId);
        		workflowDocument.disapprove("Collaboration request has been revoked because CluProposal status has changed");
        	}
        }
		*/
		return new ProcessDocReport(true, "");
	}

	public ProcessDocReport doRouteStatusChange(DocumentRouteStatusChange statusChangeEvent)
			throws Exception {
		if (statusChangeEvent.getNewRouteStatus().equals(KEWConstants.ROUTE_HEADER_APPROVED_CD)) {			
			LOG.info("CluApprovalPostProcessor: Status change to APPROVED");
			WorkflowInfo workflowInfo = new WorkflowInfo();
			DocumentContentDTO document = workflowInfo.getDocumentContent(statusChangeEvent.getRouteHeaderId());
			updateCluStatus(document);						
		}
        return new ProcessDocReport(true, "");
	}

	public List<Long> getDocumentIdsToLock(DocumentLockingEvent arg0)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
    private void updateCluStatus(DocumentContentDTO document) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, org.kuali.student.core.exceptions.InvalidParameterException, VersionMismatchException{
    	LuService luService = (LuService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/lum/lu","LuService"));
    	
    	Pattern pattern = Pattern.compile("<cluId>\\s*([^<\\s]+)");
		Matcher matcher = pattern.matcher(document.getApplicationContent());
		matcher.find();
		String cluId = matcher.group(1);
    	
		CluInfo clu = luService.getClu(cluId);
    	clu.setState("Approved by Workflow");
		luService.updateClu(cluId, clu);
    }
}
