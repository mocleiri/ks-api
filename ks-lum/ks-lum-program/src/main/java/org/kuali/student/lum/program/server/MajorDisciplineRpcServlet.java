package org.kuali.student.lum.program.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.kuali.student.common.assembly.data.Data;
import org.kuali.student.common.dto.RichTextInfo;
import org.kuali.student.common.dto.StatusInfo;
import org.kuali.student.common.exceptions.DataValidationErrorException;
import org.kuali.student.common.ui.client.service.DataSaveResult;
import org.kuali.student.common.ui.server.gwt.DataGwtServlet;
import org.kuali.student.common.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.core.statement.service.StatementService;
import org.kuali.student.core.statement.ui.client.widgets.rules.ReqComponentInfoUi;
import org.kuali.student.core.statement.ui.client.widgets.rules.RulesUtil;
import org.kuali.student.lum.common.server.StatementUtil;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.requirements.ProgramRequirementsDataModel;
import org.kuali.student.lum.program.client.requirements.ProgramRequirementsSummaryView;
import org.kuali.student.lum.program.client.rpc.MajorDisciplineRpcService;
import org.kuali.student.lum.program.dto.ProgramRequirementInfo;
import org.kuali.student.lum.program.service.ProgramService;
import org.kuali.student.lum.program.service.ProgramServiceConstants;

public class MajorDisciplineRpcServlet extends DataGwtServlet implements MajorDisciplineRpcService {

    public static final String PREVIOUS_VERSION_INFO = "previousVersionInfo";
    
    final Logger LOG = Logger.getLogger(MajorDisciplineRpcServlet.class);
    
    private static final long serialVersionUID = 1L;

    private ProgramService programService;
    private StatementService statementService;
    protected StateChangeService stateChangeService;
 
    /**
     * 
     * This method will update the state of a major discipline. 
     * 
     * @see org.kuali.student.lum.program.client.rpc.MajorDisciplineRpcService#updateStatus(org.kuali.student.common.assembly.data.Data, java.lang.String)
     */
	public DataSaveResult updateState(Data data, String state ) throws Exception {
 	    try {
    	    // Pull program ID from model
    	    String programId = data.get(ProgramConstants.ID);
    	    
    	    // Pull endEntryTerm and endEnrollTerm from model
    	    // These are set using drop downs when a program is activated
      	    Data previousVersionInfo = data.query(PREVIOUS_VERSION_INFO);
      	    String endEntryTerm = null;
      	    String endEnrollTerm = null;
     	    if (previousVersionInfo != null) {
     	      endEntryTerm = previousVersionInfo.get(ProgramConstants.END_PROGRAM_ENTRY_TERM); 
              endEnrollTerm = previousVersionInfo.get(ProgramConstants.END_PROGRAM_ENROLL_TERM);
              stateChangeService.changeState(endEntryTerm, endEnrollTerm,  programId, state);
     	    }
     	    else{
     	       // previousVersionInfo is null if this is the first version 
     	       stateChangeService.changeState( programId, state);  
     	    }
          
    
    	    // Return updates to view
    		DataSaveResult result = new DataSaveResult();
    		result.setValue(data);
    		return result;
 	    } catch(DataValidationErrorException e){
 	    	LOG.error("Error Updating Major Dicipline State", e); 	        
 	    	DataSaveResult result = new DataSaveResult();
    		result.setValidationResults(e.getValidationResults());
    		return result;
 	    } catch(Exception e){
 	    	LOG.error("Error Updating Major Dicipline State", e); 	        
 	        throw e;
 	    }
	         
	}
    public List<ProgramRequirementInfo> getProgramRequirements(List<String> programRequirementIds) throws Exception {

        List<ProgramRequirementInfo> programReqInfos = new ArrayList<ProgramRequirementInfo>();

        for (String programReqId : programRequirementIds) {
            ProgramRequirementInfo rule = programService.getProgramRequirement(programReqId, null, null);
            setProgReqNL(rule);
            programReqInfos.add(rule);
        }

        return programReqInfos;
    }

    public Map<Integer, ProgramRequirementInfo> storeProgramRequirements(Map<Integer, ProgramRequirementsDataModel.requirementState> states,
                                                                        Map<Integer, ProgramRequirementInfo> progReqs) throws Exception {
        Map<Integer, ProgramRequirementInfo> storedRules = new HashMap<Integer, ProgramRequirementInfo>();

        for (Integer key : progReqs.keySet()) {
            ProgramRequirementInfo rule = progReqs.get(key);
            switch (states.get(key)) {
                case STORED:
                    //rule was not changed so continue
                    storedRules.put(key, null);
                    break;
                case ADDED:
                    storedRules.put(key, createProgramRequirement(rule));
                    break;
                case EDITED:
                    storedRules.put(key, updateProgramRequirement(rule));
                    break;
                case DELETED:
                    storedRules.put(key, null);
                    deleteProgramRequirement(rule.getId());
                    break;
                default:
                    break;
            }
        }
        return storedRules;
    }

 
    public ProgramRequirementInfo createProgramRequirement(ProgramRequirementInfo programRequirementInfo) throws Exception {

        // If this requirement is using a temporary statement ID set the state to null
        if (programRequirementInfo.getId().indexOf(ProgramRequirementsSummaryView.NEW_PROG_REQ_ID) >= 0) {
            programRequirementInfo.setId(null);
        }
        
        // Strip the temporary statement IDs and allow permanent IDs to be created when written to the web service
        StatementUtil.stripStatementIds(programRequirementInfo.getStatement());
        
        // Update the state of the statement tree to match the state of the requirement
        // Note: the requirement state already matches the program state (e.g. Draft, Approved, etc)
        StatementUtil.updateStatementTreeViewInfoState(programRequirementInfo.getState(), programRequirementInfo.getStatement());
       
        // Call the web service to create the requirement and statement tree in the database
        ProgramRequirementInfo rule = programService.createProgramRequirement(programRequirementInfo);
        
        // Translate the requirement into its natural language equivalent
        setProgReqNL(rule);

        return rule;
    }

    public StatusInfo deleteProgramRequirement(String programRequirementId) throws Exception {
        return programService.deleteProgramRequirement(programRequirementId);
    }

    public ProgramRequirementInfo updateProgramRequirement(ProgramRequirementInfo programRequirementInfo) throws Exception {
        
        // Strip the temporary statement IDs and allow permanent IDs to be created when written to the web service
        StatementUtil.stripStatementIds(programRequirementInfo.getStatement());

        // Update the state of the statement tree to match the state of the requirement
        // Note: the requirement state already matches the program state (e.g. Draft, Approved, etc)
        StatementUtil.updateStatementTreeViewInfoState(programRequirementInfo.getState(), programRequirementInfo.getStatement());
        
        //TODO temporary fix - see KSLUM 1421
        if (programRequirementInfo.getDescr() == null) {
            programRequirementInfo.setDescr(new RichTextInfo());    
        }

        ProgramRequirementInfo rule = programService.updateProgramRequirement(programRequirementInfo);
        setProgReqNL(rule);
        return rule;
    }

    private void setProgReqNL(ProgramRequirementInfo programRequirementInfo) throws Exception {
        setReqCompNL(programRequirementInfo.getStatement());
    }

    private void setReqCompNL(StatementTreeViewInfo tree) throws Exception {
        List<StatementTreeViewInfo> statements = tree.getStatements();
        List<ReqComponentInfo> reqComponentInfos = tree.getReqComponents();

         if ((statements != null) && (statements.size() > 0)) {
            // retrieve all statements
            for (StatementTreeViewInfo statement : statements) {
                setReqCompNL(statement); // inside set the children of this statementTreeViewInfo
            }
        } else if ((reqComponentInfos != null) && (reqComponentInfos.size() > 0)) {
            // retrieve all req. component LEAFS
        	for (int i = 0; i < reqComponentInfos.size(); i++) {
        		ReqComponentInfoUi reqUi = RulesUtil.clone(reqComponentInfos.get(i));
        		reqUi.setNaturalLanguageTranslation(statementService.translateReqComponentToNL(reqUi, "KUALI.RULE", "en"));
        		reqUi.setPreviewNaturalLanguageTranslation(statementService.translateReqComponentToNL(reqUi, "KUALI.RULE.PREVIEW", "en"));
        		reqComponentInfos.set(i, reqUi);
        	}
        }
    }
    
    @Override
	public Boolean isLatestVersion(String versionIndId, Long versionSequenceNumber) throws Exception {
    	VersionDisplayInfo versionDisplayInfo = programService.getLatestVersion(ProgramServiceConstants.PROGRAM_NAMESPACE_MAJOR_DISCIPLINE_URI, versionIndId);
    	Long latestSequenceNumber = versionDisplayInfo.getSequenceNumber();
    	boolean isLatest = latestSequenceNumber.equals(versionSequenceNumber); 
    	
    	return isLatest;
	}

	public void setProgramService(ProgramService programService) {
        this.programService = programService;
    }

    public void setStatementService(StatementService statementService) {
        this.statementService = statementService;
    }

    public void setStateChangeService(StateChangeService stateChangeService) {
        this.stateChangeService = stateChangeService;
    }
    
}
