package org.kuali.student.common.ui.server.gwt;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.kew.dto.ActionRequestDTO;
import org.kuali.rice.kew.dto.DocumentContentDTO;
import org.kuali.rice.kew.dto.DocumentDetailDTO;
import org.kuali.rice.kew.service.WorkflowUtility;
import org.kuali.rice.kew.util.KEWConstants;
import org.kuali.rice.kew.webservice.DocumentResponse;
import org.kuali.rice.kew.webservice.SimpleDocumentActionsWebService;
import org.kuali.rice.kew.webservice.StandardResponse;
import org.kuali.student.common.assembly.Assembler;
import org.kuali.student.common.assembly.client.AssemblyException;
import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.common.assembly.client.Metadata;
import org.kuali.student.common.assembly.client.SaveResult;
import org.kuali.student.common.ui.client.service.BaseDataOrchestrationRpcService;
import org.kuali.student.common.ui.client.service.DataSaveResult;
import org.kuali.student.common.ui.client.service.exceptions.OperationFailedException;
import org.kuali.student.common.util.security.SecurityUtils;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.core.validation.dto.ValidationResultInfo.ErrorLevel;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Generic implementation of data orchestration calls and workflow calls
 *
 */
public abstract class AbstractBaseDataOrchestrationRpcGwtServlet extends RemoteServiceServlet implements BaseDataOrchestrationRpcService {
	//FIXME issues:
	// -The Type/state config is hardcoded here which will cause troubles with different types and states
	// -Workflow filter should be combined with this for save
	// -The exception handling here needs standardization.  Should RPC errors throw operation failed with just the message and log the message and exception?
	// also should calls that return Boolean ever throw exceptions?
	
	private static final long serialVersionUID = 1L;
	
	final Logger LOG = Logger.getLogger(AbstractBaseDataOrchestrationRpcGwtServlet.class);
	
	private Assembler<Data, Void> assembler;

    private SimpleDocumentActionsWebService simpleDocService;
    private WorkflowUtility workflowUtilityService;
	
	@Override
	public Data getData(String dataId) {
		try {
			return assembler.get(dataId);
		} catch (AssemblyException e) {
			LOG.error("Error getting Data.",e);
		}
		return null;
	}

	@Override
	public Metadata getMetadata() {

		try {
			return assembler.getMetadata(getDefaultMetaDataType(), getDefaultMetaDataState());
		} catch (AssemblyException e) {
			LOG.error("Error getting Metadata.",e);
		}
		return null;
	}

	@Override
	public DataSaveResult saveData(Data data) throws OperationFailedException {
		try {
			SaveResult<Data> saveResult = assembler.save(data);
			if (saveResult != null) {
				return new DataSaveResult(saveResult.getValidationResults(), saveResult.getValue());
			}
		} catch (Exception e) {
			LOG.error("Unable to retrieve credit course proposal", e);
			throw new OperationFailedException("Unable to save credit course proposal");
		}
		return null;
	}

	@Override
	public Boolean acknowledgeDocumentWithId(String dataId) throws OperationFailedException {
		if(simpleDocService==null){
        	throw new OperationFailedException("Workflow Service is unavailable");
        }

		try{
			//get a user name
            String username=getCurrentUser();

            //Lookup the workflowId from the dataId
            DocumentDetailDTO docDetail = workflowUtilityService.getDocumentDetailFromAppId(getDefaultWorkflowDocumentType(), dataId);
            if(docDetail==null){
            	throw new OperationFailedException("Error found gettting document. " );
            }
            
	        String acknowledgeComment = "Acknowledged";

	        StandardResponse stdResp = simpleDocService.acknowledge(docDetail.getRouteHeaderId().toString(), username, acknowledgeComment);

	        if(stdResp==null||StringUtils.isNotBlank(stdResp.getErrorMessage())){
        		throw new OperationFailedException("Error found acknowledging document: " + stdResp.getErrorMessage());
        	}

		}catch(Exception e){
            e.printStackTrace();
		}
        return new Boolean(true);
	}

	@Override
	public Boolean adhocRequest(String docId, String recipientPrincipalId,
			RequestType requestType, String annotation) throws OperationFailedException {
        if (simpleDocService == null) {
            throw new OperationFailedException("Workflow Service is unavailable");
        }

        try {
            //Get a user name
            String username = getCurrentUser();
            
            String fyiAnnotation = "FYI";
            String approveAnnotation = "Approve";
            String ackAnnotation = "Ack";
            
            if (RequestType.FYI.equals(requestType)) {
                StandardResponse stdResp = simpleDocService.requestAdHocFyiToPrincipal(docId,recipientPrincipalId, username, fyiAnnotation);
                if (stdResp == null || StringUtils.isNotBlank(stdResp.getErrorMessage())) {
                    throw new OperationFailedException("Error found in Adhoc FYI: " + stdResp.getErrorMessage());
                }
            }
            if (RequestType.APPROVE.equals(requestType)) {
                StandardResponse stdResp = simpleDocService.requestAdHocApproveToPrincipal(docId, recipientPrincipalId,username, approveAnnotation);
                if (stdResp == null || StringUtils.isNotBlank(stdResp.getErrorMessage())) {
                    throw new OperationFailedException("Error found in Adhoc Approve: " + stdResp.getErrorMessage());
                }
            }
            if (RequestType.ACKNOWLEDGE.equals(requestType)) {
                StandardResponse stdResp = simpleDocService.requestAdHocAckToPrincipal(docId,recipientPrincipalId,username, ackAnnotation);
                if (stdResp == null || StringUtils.isNotBlank(stdResp.getErrorMessage())) {
                    throw new OperationFailedException("Error found in Adhoc Ack: " + stdResp.getErrorMessage());
                }
            }

        } catch (Exception e) {
            LOG.error("Error adhoc routing",e);
            return new Boolean(false);
        }
        return new Boolean(true);
	}

	@Override
	public DataSaveResult approveDocumentWithData(Data data) throws OperationFailedException {
		//First Save
		DataSaveResult saveResult = saveData(data);
		if(isValid(saveResult)){
			try{
	            //get a user name
	            String username = getCurrentUser();
	            
	            //Lookup the workflowId from the cluId
	            DocumentDetailDTO docDetail = workflowUtilityService.getDocumentDetailFromAppId(getDefaultWorkflowDocumentType(), deriveAppIdFromData(data));
	            if(docDetail==null){
	            	throw new OperationFailedException("Error found getting document. " );
	            }
	            
		        String approveComment = "Approved by CluProposalService";
		        
		        StandardResponse stdResp = simpleDocService.approve(docDetail.getRouteHeaderId().toString(), username, docDetail.getDocTitle(), deriveDocContentFromData(data), approveComment);
	            if(stdResp==null||StringUtils.isNotBlank(stdResp.getErrorMessage())){
	        		throw new OperationFailedException("Error found approving document: " + stdResp.getErrorMessage());
	        	}

			}catch(Exception e){
	            LOG.error("Could not approve document",e);
	            throw new OperationFailedException("Could not approve document");
			}
		}
		return saveResult;
	}

	@Override
	public Boolean approveDocumentWithId(String dataId) throws OperationFailedException {
      
        if(simpleDocService==null){
        	throw new OperationFailedException("Workflow Service is unavailable");
        }

		try{
            //get a user name
            String username = getCurrentUser();
            
            //Lookup the workflowId from the id
            DocumentDetailDTO docDetail = workflowUtilityService.getDocumentDetailFromAppId(getDefaultWorkflowDocumentType(), dataId);
			DocumentContentDTO docContent = workflowUtilityService.getDocumentContent(Long.parseLong(docDetail.getAppDocId()));
            if(docDetail==null){
            	throw new OperationFailedException("Error found getting document. " );
            }
            
	        String approveComment = "Approved";
	        
	        StandardResponse stdResp = simpleDocService.approve(docDetail.getRouteHeaderId().toString(), username, docDetail.getDocTitle(), docContent.getApplicationContent(), approveComment);
            if(stdResp==null||StringUtils.isNotBlank(stdResp.getErrorMessage())){
        		throw new OperationFailedException("Error found approving document: " + stdResp.getErrorMessage());
        	}

		}catch(Exception e){
            LOG.error("Error approving document",e);
            return Boolean.FALSE;
		}
        return Boolean.TRUE;
	}

	@Override
	public Boolean disapproveDocumentWithId(String dataId) {
        if(simpleDocService==null){
        	LOG.error("Workflow Service is unavailable");
        	return Boolean.FALSE;
        }

		try{
			DocumentDetailDTO docDetail = workflowUtilityService.getDocumentDetailFromAppId(getDefaultWorkflowDocumentType(), dataId);
            //get a user name
            String username = getCurrentUser();
	        String disapproveComment = "Disapproved";

	        StandardResponse stdResp = simpleDocService.disapprove(docDetail.getRouteHeaderId().toString(), username, disapproveComment);
	        if(stdResp==null||StringUtils.isNotBlank(stdResp.getErrorMessage())){
        		LOG.error("Error  disapproving document: " + stdResp.getErrorMessage());
        		return Boolean.FALSE;
        	}
		}catch(Exception e){
            LOG.error("Error disapproving document",e);
            return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

	@Override
	public Boolean fyiDocumentWithId(String dataId) {
        if(simpleDocService==null){
        	LOG.error("Workflow Service is unavailable");
        	return Boolean.FALSE;
        }

		try{
			DocumentDetailDTO docDetail = workflowUtilityService.getDocumentDetailFromAppId(getDefaultWorkflowDocumentType(), dataId);
            //get a user name
            String username = getCurrentUser();

	        StandardResponse stdResp = simpleDocService.fyi(docDetail.getRouteHeaderId().toString(), username);
	        if(stdResp==null||StringUtils.isNotBlank(stdResp.getErrorMessage())){
        		LOG.error("Error FYIing document: " + stdResp.getErrorMessage());
        		return Boolean.FALSE;
        	}
		}catch(Exception e){
            LOG.error("Error FYIing document",e);
            return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

	@Override
	public String getActionsRequested(String dataId) throws OperationFailedException {
        try{
    		if(workflowUtilityService==null){
            	throw new OperationFailedException("Workflow Service is unavailable");
            }

    		if(null==dataId){
    			LOG.info("No Id was set for the data");
    			return "";
    		}

            //get a user name
            String username = getCurrentUser();

            //Lookup the workflowId from the cluId
            DocumentDetailDTO docDetail = workflowUtilityService.getDocumentDetailFromAppId(getDefaultWorkflowDocumentType(), dataId);
            if(docDetail==null){
            	throw new OperationFailedException("Error found gettting document. " );
            }
            
    		//Build up a string of actions requested from the attribute set.  The actions can be S, F,A,C,K. examples are "A" "AF" "FCK" "SCA"
            LOG.debug("Calling action requested with user:"+username+" and docId:"+docDetail.getRouteHeaderId());

            Map<String,String> results = new HashMap<String,String>();
            for(ActionRequestDTO request:docDetail.getActionRequests()){
        		if(request.getPrincipalId()!=null&&request.getPrincipalId().equals(username)){
        			results.put(request.getActionRequested(), "true");
        		}
            }
            
            String actionsRequested = "";

            String documentStatus = workflowUtilityService.getDocumentStatus(docDetail.getRouteHeaderId());
            
            for(Map.Entry<String,String> entry:results.entrySet()){
            	// if saved or initiated status... must show only 'complete' button
            	if (KEWConstants.ROUTE_HEADER_SAVED_CD.equals(documentStatus) || KEWConstants.ROUTE_HEADER_INITIATED_CD.equals(documentStatus)) {
            		// show only complete button if complete or approve code in this doc status
            		if ( (KEWConstants.ACTION_REQUEST_COMPLETE_REQ.equals(entry.getKey()) || KEWConstants.ACTION_REQUEST_APPROVE_REQ.equals(entry.getKey())) && ("true".equals(entry.getValue())) ) {
            			actionsRequested+="S";
            		}
            		// if not Complete or Approve code then show the standard buttons
            		else {
    	            	if("true".equals(entry.getValue())){
    	            		actionsRequested+=entry.getKey();
    	            	}
            		}
            	}
            	else {
                	if("true".equals(entry.getValue())){
                		actionsRequested+=entry.getKey();
                	}
            	}
            }
            return actionsRequested;
        } catch (Exception e) {
        	LOG.error("Error getting actions Requested",e);
            throw new OperationFailedException("Error getting actions Requested");
        }
	}

	@Override
	public Data getDataFromWorkflowId(String workflowId) throws OperationFailedException {
        if(simpleDocService==null){
        	throw new OperationFailedException("Workflow Service is unavailable");
        }
        String username = getCurrentUser();

        DocumentResponse docResponse = simpleDocService.getDocument(workflowId, username);
        if(docResponse==null||StringUtils.isNotBlank(docResponse.getErrorMessage())){
        	throw new OperationFailedException("Error found gettting document: " + docResponse.getErrorMessage());
        }

        return getData(docResponse.getAppDocId());
	}

	@Override
	public String getWorkflowIdFromDataId(String dataId) throws OperationFailedException {
		if(null==simpleDocService){
        	throw new OperationFailedException("Workflow Service is unavailable");
        }

        DocumentDetailDTO docDetail;
		try {
			docDetail = workflowUtilityService.getDocumentDetailFromAppId(getDefaultWorkflowDocumentType(), dataId);
	        if(null==docDetail){
	        	LOG.error("Nothing found for id: "+dataId);
	        	return null;
	        }
	        Long workflowId=docDetail.getRouteHeaderId();
	        return null==workflowId?null:workflowId.toString();
		} catch (Exception e) {
			LOG.error("Call Failed getting workflowId for id: "+dataId, e);
		}
		return null;
	}

	@Override
	public DataSaveResult submitDocumentWithData(Data data) throws OperationFailedException {
		//First Save
		DataSaveResult saveResult = saveData(data);
		if(isValid(saveResult)){
			try {
	            if(simpleDocService==null){
	            	throw new OperationFailedException("Workflow Service is unavailable");
	            }

	            //get a user name
	            String username = getCurrentUser();

	            //Get the workflow ID
	            DocumentDetailDTO docDetail = workflowUtilityService.getDocumentDetailFromAppId(getDefaultWorkflowDocumentType(), deriveAppIdFromData(data));

	            if(docDetail==null){
	            	throw new OperationFailedException("Error found getting document. " );
	            }

	            String routeComment = "Routed";

	            StandardResponse stdResp = simpleDocService.route(docDetail.getRouteHeaderId().toString(), username, docDetail.getDocTitle(), deriveDocContentFromData(data), routeComment);

	            if(stdResp==null||StringUtils.isNotBlank(stdResp.getErrorMessage())){
	        		throw new OperationFailedException("Error found routing document: " + stdResp.getErrorMessage());
	        	}

	        } catch (Exception e) {
	            LOG.error("Error found routing document",e);
	            throw new OperationFailedException("Error found routing document");
	        }
		}
		return saveResult;
	}

	@Override
	public Boolean submitDocumentWithId(String dataId) {
		try {
            if(simpleDocService==null){
            	throw new OperationFailedException("Workflow Service is unavailable");
            }

            //get a user name
            String username = getCurrentUser();

            //Get the workflow ID
            DocumentDetailDTO docDetail = workflowUtilityService.getDocumentDetailFromAppId(getDefaultWorkflowDocumentType(), dataId);
            DocumentContentDTO docContent = workflowUtilityService.getDocumentContent(Long.parseLong(docDetail.getAppDocId()));
            
            if(docDetail==null){
            	throw new OperationFailedException("Error found getting document. " );
            }

            String routeComment = "Routed";

            StandardResponse stdResp = simpleDocService.route(docDetail.getRouteHeaderId().toString(), username, docDetail.getDocTitle(), docContent.getApplicationContent(), routeComment);

            if(stdResp==null||StringUtils.isNotBlank(stdResp.getErrorMessage())){
        		throw new OperationFailedException("Error found routing document: " + stdResp.getErrorMessage());
        	}

        } catch (Exception e) {
            LOG.error("Error found routing document",e);
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
	}

	protected String getCurrentUser() {
		String username = SecurityUtils.getCurrentUserId();
		//backdoorId is only for convenience
		if(username==null&&this.getThreadLocalRequest().getSession().getAttribute("backdoorId")!=null){
			username=(String)this.getThreadLocalRequest().getSession().getAttribute("backdoorId");
        }
		return username;
	}
	private boolean isValid(DataSaveResult saveResult) {
		if(saveResult.getValidationResults()!=null){
			for(ValidationResultInfo validationResult:saveResult.getValidationResults()){
				if(ErrorLevel.ERROR.equals(validationResult.getLevel())){
					return false;
				}
			}
		}
		return true;
	}
	
	protected abstract String deriveAppIdFromData(Data data);
	protected abstract String deriveDocContentFromData(Data data);
	protected abstract String getDefaultWorkflowDocumentType();
	protected abstract String getDefaultMetaDataState();
	protected abstract String getDefaultMetaDataType();
	
	//POJO methods
	public void setAssembler(Assembler<Data, Void> assembler) {
		this.assembler = assembler;
	}

	public void setSimpleDocService(SimpleDocumentActionsWebService simpleDocService) {
		this.simpleDocService = simpleDocService;
	}

	public void setWorkflowUtilityService(WorkflowUtility workflowUtilityService) {
		this.workflowUtilityService = workflowUtilityService;
	}

	protected Assembler<Data, Void> getAssembler() {
		return assembler;
	}

	protected SimpleDocumentActionsWebService getSimpleDocService() {
		return simpleDocService;
	}

	protected WorkflowUtility getWorkflowUtilityService() {
		return workflowUtilityService;
	}


}
