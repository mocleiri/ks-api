package org.kuali.student.lum.program.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.student.common.assembly.BaseDTOAssemblyNode;
import org.kuali.student.common.assembly.BaseDTOAssemblyNode.NodeOperation;
import org.kuali.student.common.assembly.BusinessServiceMethodInvoker;
import org.kuali.student.common.assembly.data.AssemblyException;
import org.kuali.student.common.dictionary.dto.DataType;
import org.kuali.student.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.common.dictionary.service.DictionaryService;
import org.kuali.student.common.dto.DtoConstants;
import org.kuali.student.common.dto.StatusInfo;
import org.kuali.student.common.exceptions.AlreadyExistsException;
import org.kuali.student.common.exceptions.CircularReferenceException;
import org.kuali.student.common.exceptions.CircularRelationshipException;
import org.kuali.student.common.exceptions.DataValidationErrorException;
import org.kuali.student.common.exceptions.DependentObjectsExistException;
import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.common.exceptions.IllegalVersionSequencingException;
import org.kuali.student.common.exceptions.InvalidParameterException;
import org.kuali.student.common.exceptions.MissingParameterException;
import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.common.exceptions.PermissionDeniedException;
import org.kuali.student.common.exceptions.UnsupportedActionException;
import org.kuali.student.common.exceptions.VersionMismatchException;
import org.kuali.student.common.search.dto.SearchCriteriaTypeInfo;
import org.kuali.student.common.search.dto.SearchRequest;
import org.kuali.student.common.search.dto.SearchResult;
import org.kuali.student.common.search.dto.SearchResultTypeInfo;
import org.kuali.student.common.search.dto.SearchTypeInfo;
import org.kuali.student.common.search.service.SearchManager;
import org.kuali.student.common.validation.dto.ValidationResultInfo;
import org.kuali.student.common.validator.ServerDateParser;
import org.kuali.student.common.validator.Validator;
import org.kuali.student.common.validator.ValidatorFactory;
import org.kuali.student.common.validator.ValidatorUtils;
import org.kuali.student.common.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.core.atp.dto.AtpInfo;
import org.kuali.student.core.atp.service.AtpService;
import org.kuali.student.core.document.dto.RefDocRelationInfo;
import org.kuali.student.core.document.service.DocumentService;
import org.kuali.student.core.statement.dto.ReqCompFieldInfo;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.lum.course.dto.LoDisplayInfo;
import org.kuali.student.lum.course.service.impl.CourseServiceUtils;
import org.kuali.student.lum.lu.dto.CluCluRelationInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.dto.CluSetInfo;
import org.kuali.student.lum.lu.dto.LuTypeInfo;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.lu.service.LuServiceConstants;
import org.kuali.student.lum.program.dto.CoreProgramInfo;
import org.kuali.student.lum.program.dto.CredentialProgramInfo;
import org.kuali.student.lum.program.dto.HonorsProgramInfo;
import org.kuali.student.lum.program.dto.MajorDisciplineInfo;
import org.kuali.student.lum.program.dto.MinorDisciplineInfo;
import org.kuali.student.lum.program.dto.ProgramRequirementInfo;
import org.kuali.student.lum.program.dto.ProgramVariationInfo;
import org.kuali.student.lum.program.service.ProgramService;
import org.kuali.student.lum.program.service.ProgramServiceConstants;
import org.kuali.student.lum.program.service.assembler.CoreProgramAssembler;
import org.kuali.student.lum.program.service.assembler.CredentialProgramAssembler;
import org.kuali.student.lum.program.service.assembler.MajorDisciplineAssembler;
import org.kuali.student.lum.program.service.assembler.ProgramAssemblerConstants;
import org.kuali.student.lum.statement.typekey.ReqComponentFieldTypes;
import org.springframework.transaction.annotation.Transactional;

public class ProgramServiceImpl implements ProgramService {
	final static Logger LOG = Logger.getLogger(ProgramServiceImpl.class);

    private LuService luService;
    private ValidatorFactory validatorFactory;
    private BusinessServiceMethodInvoker programServiceMethodInvoker;
    private DictionaryService dictionaryService;
    private SearchManager searchManager;
    private MajorDisciplineAssembler majorDisciplineAssembler;
    private ProgramRequirementAssembler programRequirementAssembler;
    private CredentialProgramAssembler credentialProgramAssembler;
    private CoreProgramAssembler coreProgramAssembler;
//    private StatementService statementService;
    private AtpService atpService;
    private DocumentService documentService;
    
    
    @Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public CredentialProgramInfo createCredentialProgram(
            CredentialProgramInfo credentialProgramInfo)
            throws AlreadyExistsException, DataValidationErrorException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        checkForMissingParameter(credentialProgramInfo, "CredentialProgramInfo");

        // Validate
        List<ValidationResultInfo> validationResults = validateCredentialProgram("OBJECT", credentialProgramInfo);
        if (ValidatorUtils.hasErrors(validationResults)) {
            throw new DataValidationErrorException("Validation error!", validationResults);
        }

        try {
            return processCredentialProgramInfo(credentialProgramInfo, NodeOperation.CREATE);
        } catch (AssemblyException e) {
            LOG.error("Error disassembling Credential Program", e);
            throw new OperationFailedException("Error disassembling Credential Program");
        }
    }

    @Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public HonorsProgramInfo createHonorsProgram(
            HonorsProgramInfo honorsProgramInfo) throws AlreadyExistsException,
            DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public ProgramRequirementInfo createProgramRequirement(
            ProgramRequirementInfo programRequirementInfo)
            throws AlreadyExistsException, DataValidationErrorException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(programRequirementInfo, "programRequirementInfo");

        // Validate
        List<ValidationResultInfo> validationResults = validateProgramRequirement("OBJECT", programRequirementInfo);
        if (ValidatorUtils.hasErrors(validationResults)) {
        	throw new DataValidationErrorException("Validation error!", validationResults);
        }

        try {
            return processProgramRequirement(programRequirementInfo, NodeOperation.CREATE);
        } catch (AssemblyException e) {
            LOG.error("Error disassembling Program Requirement", e);
            throw new OperationFailedException("Error disassembling Program Requirement", e);
        }
    }

    @Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public MajorDisciplineInfo createMajorDiscipline(
            MajorDisciplineInfo majorDisciplineInfo)
            throws AlreadyExistsException, DataValidationErrorException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        checkForMissingParameter(majorDisciplineInfo, "MajorDisciplineInfo");

        // Validate
        List<ValidationResultInfo> validationResults = validateMajorDiscipline("OBJECT", majorDisciplineInfo);
        if (ValidatorUtils.hasErrors(validationResults)) {
            throw new DataValidationErrorException("Validation error!", validationResults);
        }

        try {
            return processMajorDisciplineInfo(majorDisciplineInfo, NodeOperation.CREATE);
        } catch (AssemblyException e) {
            LOG.error("Error creating Major Discipline", e);
            throw new OperationFailedException("Error creating Major Discipline");
        }
    }
    
    @Override
	@Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public MajorDisciplineInfo createNewMajorDisciplineVersion(
			String majorDisciplineVerIndId, String versionComment)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, VersionMismatchException, DataValidationErrorException {
		//step one, get the original
		VersionDisplayInfo currentVersion = luService.getCurrentVersion(LuServiceConstants.CLU_NAMESPACE_URI, majorDisciplineVerIndId);
		MajorDisciplineInfo originalMajorDiscipline = getMajorDiscipline(currentVersion.getId());

		//Version the Clu
		CluInfo newVersionClu = luService.createNewCluVersion(majorDisciplineVerIndId, versionComment);

		try {
	        BaseDTOAssemblyNode<MajorDisciplineInfo, CluInfo> results;

	        //Integrate changes into the original. (should this just be just the id?)
			majorDisciplineAssembler.assemble(newVersionClu, originalMajorDiscipline, true);

			//Clear Ids from the original so it will make a copy and do other processing
			processCopy(originalMajorDiscipline, currentVersion.getId());
           
            // Since we are creating a new version, update the requirements and statement
			// tree and set the state to Draft
            List<String> programRequirementIds = originalMajorDiscipline.getProgramRequirements();
            updateRequirementsState(programRequirementIds, DtoConstants.STATE_DRAFT);
            
			//Disassemble the new major discipline
			results = majorDisciplineAssembler.disassemble(originalMajorDiscipline, NodeOperation.UPDATE);
			
			// Use the results to make the appropriate service calls here
			programServiceMethodInvoker.invokeServiceCalls(results);

			return results.getBusinessDTORef();
		} catch(AssemblyException e) {
			throw new OperationFailedException("Error creating new MajorDiscipline version",e);
		} catch (AlreadyExistsException e) {
			throw new OperationFailedException("Error creating new MajorDiscipline version",e);
		} catch (DependentObjectsExistException e) {
			throw new OperationFailedException("Error creating new MajorDiscipline version",e);
		} catch (CircularRelationshipException e) {
			throw new OperationFailedException("Error creating new MajorDiscipline version",e);
		} catch (UnsupportedActionException e) {
			throw new OperationFailedException("Error creating new MajorDiscipline version",e);
		} catch (CircularReferenceException e) {
			throw new OperationFailedException("Error creating new MajorDiscipline version",e);
		}
	}
    
    /**
     * This method will update the requirement state.
     * <p>
     * Note that it uses StatementUtil to update the statement tree.
     * 
     * @param majorDisciplineInfo
     * @param newState
     * @throws Exception
     */
    private void updateRequirementsState(List<String> programRequirementIds, String newState) throws DoesNotExistException,
        InvalidParameterException, MissingParameterException,
        OperationFailedException, PermissionDeniedException,  VersionMismatchException, DataValidationErrorException  {

        /*
         * WARNING: This is an exact copy of the method from ProgramStateChangeServiceImpl.
         * We had to copy it because we cannot reference classes in the 
         * org.kuali.student.lum.program.server
         * 
         * TODO: find a place to put a shared StatementUtil 
         */
         
        for (String programRequirementId : programRequirementIds) {

            // Get program requirement from the program service
            ProgramRequirementInfo programRequirementInfo = getProgramRequirement(programRequirementId, null, null);

            // Look in the requirement for the statement tree
            StatementTreeViewInfo statementTree = programRequirementInfo.getStatement();

            // And recursively update the entire tree with the new state
            updateStatementTreeViewInfoState(newState, statementTree);

            // Update the state of the requirement object
            programRequirementInfo.setState(newState);

            // The write the requirement back to the program service
            updateProgramRequirement(programRequirementInfo);

        }
    }
    
    /**
     * This method will recursively set the state of all statements in the tree.
     * <p>
     * WARNING: you must call the statement service in order to update statements.
     * <p>
     * 
     * @param state is the state we should set all statements in the tree to
     * @param statementTreeViewInfo the tree of statements
     * @throws Exception
     */
    private static void updateStatementTreeViewInfoState(String state, StatementTreeViewInfo statementTreeViewInfo) {
       /*
        * WARNING: This is a copy of the method from StatementUtil.  We had to copy it because 
        * we cannot reference the common.server package from this class.
        * 
        * TODO: find a place to put a shared StatementUtil 
        */
        
        // Set the state on the statement tree itself
        statementTreeViewInfo.setState(state);
         
        // Get all the requirements components for this statement
        List<ReqComponentInfo> reqComponents = statementTreeViewInfo.getReqComponents();
        
        // Loop over requirements and set the state for each requirement
        for(Iterator<ReqComponentInfo> it = reqComponents.iterator(); it.hasNext();)
            it.next().setState(state);
        
        // Loop over each statement and set the state for each statement (recursively calling this method)
        for(Iterator<StatementTreeViewInfo> itr = statementTreeViewInfo.getStatements().iterator(); itr.hasNext();)
            updateStatementTreeViewInfoState(state, (StatementTreeViewInfo)itr.next());
    }
    
	/**
	 * Recurses through the statement tree and clears out ids so the tree can be copied.
	 * Also creates copies of clusets since they are single use
	 * 
	 * @param statementTreeView
	 * @throws OperationFailedException
	 * @see CourseServiceUtils (This is duplicate code because of the weird dependencies cause by program being in its own module)
	 */
	private void clearStatementTreeViewIdsRecursively(StatementTreeViewInfo statementTreeView) throws OperationFailedException{
		if(statementTreeView!=null){
			statementTreeView.setId(null);
			for(ReqComponentInfo reqComp:statementTreeView.getReqComponents()){
				reqComp.setId(null);
				for(ReqCompFieldInfo field:reqComp.getReqCompFields()){
					field.setId(null);
					//copy any clusets that are adhoc'd and set the field value to the new cluset
					if(ReqComponentFieldTypes.COURSE_CLUSET_KEY.getId().equals(field.getType())||
					   ReqComponentFieldTypes.PROGRAM_CLUSET_KEY.getId().equals(field.getType())||
					   ReqComponentFieldTypes.CLUSET_KEY.getId().equals(field.getType())){
						try {
							CluSetInfo cluSet = luService.getCluSetInfo(field.getValue());
							cluSet.setId(null);
							//Clear clu ids if membership info exists, they will be re-added based on membership info 
							if (cluSet.getMembershipQuery() != null){
								cluSet.getCluIds().clear();
								cluSet.getCluSetIds().clear();
							}
							cluSet = luService.createCluSet(cluSet.getType(), cluSet);
							field.setValue(cluSet.getId());
						} catch (Exception e) {
							throw new OperationFailedException("Error copying clusets.", e);
						}
					}
					
				}
			}
			//Recurse through the children
			for(StatementTreeViewInfo child: statementTreeView.getStatements()){
				clearStatementTreeViewIdsRecursively(child);
			}
		}
	}

	/**
     * Clears out any ids so that a subsequent call to create will copy complex structures. 
     * Also updates VersionInfo on variations to match VersionInfo on parent.
     * 
     * @param majorDiscipline
	 * @throws PermissionDeniedException 
	 * @throws OperationFailedException 
	 * @throws MissingParameterException 
	 * @throws InvalidParameterException 
	 * @throws DoesNotExistException 
	 * @throws DataValidationErrorException 
	 * @throws AlreadyExistsException 
	 * @throws VersionMismatchException 
	 * @throws CircularRelationshipException 
     */
    private void processCopy(MajorDisciplineInfo majorDiscipline,String originalId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, AlreadyExistsException, DataValidationErrorException, VersionMismatchException, CircularRelationshipException {
		//Clear Terms (needs to be set on new version anyway so this forces the issue)
    	majorDiscipline.setStartTerm(null);
    	majorDiscipline.setEndTerm(null);
    	majorDiscipline.setEndProgramEntryTerm(null);
    	majorDiscipline.getAttributes().remove("endInstAdmitTerm");
    	
    	//Clear Los
		for(LoDisplayInfo lo:majorDiscipline.getLearningObjectives()){
			resetLoRecursively(lo);
		}
		//Clear OrgCoreProgram
		if(majorDiscipline.getOrgCoreProgram()!=null){
			majorDiscipline.getOrgCoreProgram().setId(null);
		
			if(majorDiscipline.getOrgCoreProgram().getLearningObjectives()!=null){
				for(LoDisplayInfo lo:majorDiscipline.getOrgCoreProgram().getLearningObjectives()){
					resetLoRecursively(lo);
				}
			}
		}
		//Clear Variations
		for(ProgramVariationInfo variation:majorDiscipline.getVariations()){
			//Clear Terms (needs to be set on new version anyway so this forces the issue)
	    	variation.setStartTerm(null);
	    	variation.setEndTerm(null);
	    	variation.setEndProgramEntryTerm(null);
	    	variation.getAttributes().remove("endInstAdmitTerm");
	    	
			//Create new variation version
			String variationVersionIndId = variation.getVersionInfo().getVersionIndId();
			CluInfo newVariationClu = luService.createNewCluVersion(variationVersionIndId, "Variation version for MajorDiscipline version " + majorDiscipline.getVersionInfo().getSequenceNumber());	
			
			//Create relation b/w new major discipline and new variation
			CluCluRelationInfo relation = new CluCluRelationInfo();
	        relation.setCluId(majorDiscipline.getId());
	        relation.setRelatedCluId(newVariationClu.getId());
	        relation.setType(ProgramAssemblerConstants.HAS_PROGRAM_VARIATION);
	        
	        // Relations can only be ACTIVE or Suspended
	        // We will set to ACTIVE for now
	        relation.setState(DtoConstants.STATE_ACTIVE);
			luService.createCluCluRelation(relation.getCluId(), relation.getRelatedCluId(), relation.getType(), relation);
	        
			//Set variation id & versionInfo to new variation clu
			variation.setId(newVariationClu.getId());
			variation.setMetaInfo(newVariationClu.getMetaInfo());
						
			//Set state to parent program's state
			variation.setState(majorDiscipline.getState());
			//Clear Los
			for(LoDisplayInfo lo:variation.getLearningObjectives()){
				resetLoRecursively(lo);
			}
			//Copy Requirements for variation
			copyProgramRequirements(variation.getProgramRequirements(),majorDiscipline.getState());
		}
		
		//Copy requirements for majorDiscipline
		copyProgramRequirements(majorDiscipline.getProgramRequirements(),majorDiscipline.getState());

		//Copy documents(create new relations to the new version)
		List<RefDocRelationInfo> docRelations = documentService.getRefDocRelationsByRef("kuali.org.RefObjectType.ProposalInfo", originalId);
		if(docRelations!=null){
			for(RefDocRelationInfo docRelation:docRelations){
				docRelation.setId(null);
				docRelation.setRefObjectId(majorDiscipline.getId());
				documentService.createRefDocRelation("kuali.org.RefObjectType.ProposalInfo", majorDiscipline.getId(), docRelation.getDocumentId(), docRelation.getType(), docRelation);
			}
		}
	}

	private void processCopy(CredentialProgramInfo originaCredentialProgram,
			String originalId) throws OperationFailedException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, PermissionDeniedException, DoesNotExistException {
		//Clear Terms (needs to be set on new version anyway so this forces the issue)
		originaCredentialProgram.setStartTerm(null);
		originaCredentialProgram.setEndTerm(null);
		originaCredentialProgram.setEndProgramEntryTerm(null);
		
		//Clear Los
		if (originaCredentialProgram.getLearningObjectives() != null){
			for(LoDisplayInfo lo:originaCredentialProgram.getLearningObjectives()){
				resetLoRecursively(lo);
			}
		}

		//Copy requirements for majorDiscipline
		copyProgramRequirements(originaCredentialProgram.getProgramRequirements(),originaCredentialProgram.getState());

		//Copy documents(create new relations to the new version)
		List<RefDocRelationInfo> docRelations = documentService.getRefDocRelationsByRef("kuali.org.RefObjectType.ProposalInfo", originalId);
		if(docRelations!=null){
			for(RefDocRelationInfo docRelation:docRelations){
				docRelation.setId(null);
				docRelation.setRefObjectId(originaCredentialProgram.getId());
				documentService.createRefDocRelation("kuali.org.RefObjectType.ProposalInfo", originaCredentialProgram.getId(), docRelation.getDocumentId(), docRelation.getType(), docRelation);
			}
		}
	}
    
    private void processCopy(CoreProgramInfo originalCoreProgram, String originalId) throws OperationFailedException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, PermissionDeniedException, DoesNotExistException {
		//Clear Terms (needs to be set on new version anyway so this forces the issue)
    	originalCoreProgram.setStartTerm(null);
    	originalCoreProgram.setEndTerm(null);
    	originalCoreProgram.setEndProgramEntryTerm(null);
		
    	//Clear Los
		for(LoDisplayInfo lo:originalCoreProgram.getLearningObjectives()){
			resetLoRecursively(lo);
		}
		//Copy requirements for majorDiscipline
		copyProgramRequirements(originalCoreProgram.getProgramRequirements(),originalCoreProgram.getState());

		//Copy documents(create new relations to the new version)
		List<RefDocRelationInfo> docRelations = documentService.getRefDocRelationsByRef("kuali.org.RefObjectType.ProposalInfo", originalId);
		if(docRelations!=null){
			for(RefDocRelationInfo docRelation:docRelations){
				docRelation.setId(null);
				docRelation.setRefObjectId(originalCoreProgram.getId());
				documentService.createRefDocRelation("kuali.org.RefObjectType.ProposalInfo", originalCoreProgram.getId(), docRelation.getDocumentId(), docRelation.getType(), docRelation);
			}
		}
	}
    
    /**
     * Copy requirements (these exist external to the program save process and are referenced by id)
     * @param originalProgramRequirementIds
     * @param state
     * @throws OperationFailedException
     * @throws AlreadyExistsException
     * @throws DataValidationErrorException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws PermissionDeniedException
     * @throws DoesNotExistException
     */
    private void copyProgramRequirements(List<String> originalProgramRequirementIds,String state) throws OperationFailedException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, PermissionDeniedException, DoesNotExistException{
		//Pull out the current requirement ids to be replaced by the ids of the new copies 
		List<String> programRequirementIds = new ArrayList<String>(originalProgramRequirementIds);
		originalProgramRequirementIds.clear();
		
		for(String programRequirementId:programRequirementIds){
			//Grab the original 
			ProgramRequirementInfo programRequirementInfo = getProgramRequirement(programRequirementId, null, null);
			//Clear the id
			programRequirementInfo.setId(null);
			
			programRequirementInfo.setState(state);
			//Clear statement tree ids
			clearStatementTreeViewIdsRecursively(programRequirementInfo.getStatement());
			//Clear learning objectives
			for(LoDisplayInfo lo:programRequirementInfo.getLearningObjectives()){
				resetLoRecursively(lo);
			}
			//Create the new copy
			ProgramRequirementInfo createdProgramRequirement = createProgramRequirement(programRequirementInfo);
			//add the copy's id back to the majorDiscipline's list of requirements
			originalProgramRequirementIds.add(createdProgramRequirement.getId());
		}
    }
    
	/**
	 * Recursively clears out the ids in a Lo and in its child Los
	 * @param lo
	 */
	private void resetLoRecursively(LoDisplayInfo lo){
		lo.getLoInfo().setId(null);
		for(LoDisplayInfo nestedLo:lo.getLoDisplayInfoList()){
			resetLoRecursively(nestedLo);
		}
	}

	@Override
	@Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public StatusInfo setCurrentMajorDisciplineVersion(
			String majorDisciplineId, Date currentVersionStart)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, IllegalVersionSequencingException,
			OperationFailedException, PermissionDeniedException {
		StatusInfo status = luService.setCurrentCluVersion(majorDisciplineId, currentVersionStart);
		
		//Update the variations to be current as well
		List<ProgramVariationInfo> variationList = getVariationsByMajorDisciplineId(majorDisciplineId);
		for (ProgramVariationInfo variationInfo:variationList){
			String variationId = variationInfo.getId();
			//If null set to current (non-null value means version is first and is already current)
			if (variationInfo.getVersionInfo().getCurrentVersionStart() == null){
				luService.setCurrentCluVersion(variationId, currentVersionStart);
			}
		}
		
		return status;
	}

	@Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public MinorDisciplineInfo createMinorDiscipline(
            MinorDisciplineInfo minorDisciplineInfo)
            throws AlreadyExistsException, DataValidationErrorException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public StatusInfo deleteCredentialProgram(String credentialProgramId)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {

//        try {
//        	CredentialProgramInfo credentialProgram = getCredentialProgram(credentialProgramId);
//
//            processCredentialProgramInfo(credentialProgram, NodeOperation.DELETE);
//
//            return getStatus();
//
//        } catch (AssemblyException e) {
//            LOG.error("Error disassembling CredentialProgram", e);
//            throw new OperationFailedException("Error disassembling CredentialProgram");
//        }
    	throw new OperationFailedException("Deletion of CredentialProgram is not supported."); 
    }

    @Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public StatusInfo deleteHonorsProgram(String honorsProgramId)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public StatusInfo deleteMajorDiscipline(String majorDisciplineId)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        try {
            MajorDisciplineInfo majorDiscipline = getMajorDiscipline(majorDisciplineId);

            processMajorDisciplineInfo(majorDiscipline, NodeOperation.DELETE);

            return getStatus();

        } catch (AssemblyException e) {
            LOG.error("Error disassembling MajorDiscipline", e);
            throw new OperationFailedException("Error disassembling MajorDiscipline");
        }
    }

    @Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public StatusInfo deleteMinorDiscipline(String minorDisciplineId)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public StatusInfo deleteProgramRequirement(String programRequirementId)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
    	checkForMissingParameter(programRequirementId, "programRequirementId");
        try {
        	ProgramRequirementInfo programRequirement = getProgramRequirement(programRequirementId, null, null);

        	processProgramRequirement(programRequirement, NodeOperation.DELETE);

            return getStatus();

        } catch (AssemblyException e) {
            LOG.error("Error disassembling MajorDiscipline", e);
            throw new OperationFailedException("Error disassembling ProgramRequirement", e);
        }

    }

    @Override
    @Transactional(readOnly=true)
    public CredentialProgramInfo getCredentialProgram(String credentialProgramId)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {

    	CredentialProgramInfo credentialProgramInfo = null;

        try {
            CluInfo clu = luService.getClu(credentialProgramId);

            if ( ! ProgramAssemblerConstants.CREDENTIAL_PROGRAM_TYPES.contains(clu.getType()) ) {
                throw new DoesNotExistException("Specified CLU is not a Credential Program");
            }

            credentialProgramInfo = credentialProgramAssembler.assemble(clu, null, false);
        } catch (AssemblyException e) {
            LOG.error("Error assembling CredentialProgram", e);
            throw new OperationFailedException("Error assembling CredentialProgram");
        }
        return credentialProgramInfo;

		// comment out the above, and uncomment below to get auto-generated data
        // (and vice-versa)
//		try {
//			return new CredentialProgramDataGenerator(ProgramAssemblerConstants.BACCALAUREATE_PROGRAM).getCPTestData();
//		} catch (Exception e) {
//			return null;
//		}
    }

    @Override
    public LuTypeInfo getCredentialProgramType(String credentialProgramTypeKey)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<LuTypeInfo> getCredentialProgramTypes()
            throws OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> getHonorsByCredentialProgramType(String programType)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public HonorsProgramInfo getHonorsProgram(String honorsProgramId)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @Transactional(readOnly=true)
    public MajorDisciplineInfo getMajorDiscipline(String majorDisciplineId)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {


        MajorDisciplineInfo majorDiscipline = null;

        try {
            CluInfo clu = luService.getClu(majorDisciplineId);
            if ( ! ProgramAssemblerConstants.MAJOR_DISCIPLINE.equals(clu.getType()) ) {
                throw new DoesNotExistException("Specified CLU is not a Major Discipline");
            }
            majorDiscipline = majorDisciplineAssembler.assemble(clu, null, false);
        } catch (AssemblyException e) {
            LOG.error("Error assembling MajorDiscipline", e);
            throw new OperationFailedException("Error assembling MajorDiscipline");
        }
        return majorDiscipline;
		// comment out the above, and uncomment below to get auto-generated data
        // (and vice-versa)
//		try {
//			return new MajorDisciplineDataGenerator().getMajorDisciplineInfoTestData();
//		} catch (Exception e) {
//			return null;
//		}
	}

	@Override
	public List<String> getMajorIdsByCredentialProgramType(String programType)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MinorDisciplineInfo getMinorDiscipline(String minorDisciplineId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getMinorsByCredentialProgramType(String programType)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
    @Transactional(readOnly=true)
	public ProgramRequirementInfo getProgramRequirement(String programRequirementId, String nlUsageTypeKey, String language) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

		checkForMissingParameter(programRequirementId, "programRequirementId");

		CluInfo clu = luService.getClu(programRequirementId);
		if (!ProgramAssemblerConstants.PROGRAM_REQUIREMENT.equals(clu.getType())) {
			throw new DoesNotExistException("Specified CLU is not a Program Requirement");
		}
		try {
			ProgramRequirementInfo progReqInfo = programRequirementAssembler.assemble(clu, null, false);
			return progReqInfo;
		} catch (AssemblyException e) {
            LOG.error("Error assembling program requirement", e);
            throw new OperationFailedException("Error assembling program requirement: " + e.getMessage(), e);
		}
	}

	@Override
    @Transactional(readOnly=true)
	public List<ProgramVariationInfo> getVariationsByMajorDisciplineId(
			String majorDisciplineId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
    	List<ProgramVariationInfo> pvInfos = new ArrayList<ProgramVariationInfo>();

    	try {
    			List<CluInfo> clus = luService.getRelatedClusByCluId(majorDisciplineId, ProgramAssemblerConstants.HAS_PROGRAM_VARIATION);

		        if(clus != null && clus.size() > 0){
		        	for(CluInfo clu : clus){
		        		ProgramVariationInfo pvInfo = majorDisciplineAssembler.getProgramVariationAssembler().assemble(clu, null, false);
		        		if(pvInfo != null){
		        			pvInfos.add(pvInfo);
		        		}
		        	}
		        }
		    } catch (AssemblyException e) {
		        LOG.error("Error assembling ProgramVariation", e);
		        throw new OperationFailedException("Error assembling ProgramVariation");
		    }

        return pvInfos;
    }

    @Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public CredentialProgramInfo updateCredentialProgram(
            CredentialProgramInfo credentialProgramInfo)
            throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            VersionMismatchException, OperationFailedException,
            PermissionDeniedException {

        checkForMissingParameter(credentialProgramInfo, "CredentialProgramInfo");

        // Validate
        List<ValidationResultInfo> validationResults = validateCredentialProgram("OBJECT", credentialProgramInfo);
        if (ValidatorUtils.hasErrors(validationResults)) {
            throw new DataValidationErrorException("Validation error!", validationResults);
        }

        try {

            return processCredentialProgramInfo(credentialProgramInfo, NodeOperation.UPDATE);

        } catch (AssemblyException e) {
            LOG.error("Error disassembling Credential Program", e);
            throw new OperationFailedException("Error disassembling Credential Program");
        }
    }

    @Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public HonorsProgramInfo updateHonorsProgram(
            HonorsProgramInfo honorsProgramInfo)
            throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            VersionMismatchException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public MajorDisciplineInfo updateMajorDiscipline(
            MajorDisciplineInfo majorDisciplineInfo)
            throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            VersionMismatchException, OperationFailedException,
            PermissionDeniedException {

        checkForMissingParameter(majorDisciplineInfo, "MajorDisciplineInfo");

        // Validate
        List<ValidationResultInfo> validationResults = validateMajorDiscipline("OBJECT", majorDisciplineInfo);
        if (ValidatorUtils.hasErrors(validationResults)) {
            throw new DataValidationErrorException("Validation error!", validationResults);
        }

        try {

            return processMajorDisciplineInfo(majorDisciplineInfo, NodeOperation.UPDATE);

        } catch (AssemblyException e) {
            LOG.error("Error disassembling majorDiscipline", e);
            throw new OperationFailedException("Error disassembling majorDiscipline");
        }
    }

    @Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public MinorDisciplineInfo updateMinorDiscipline(
            MinorDisciplineInfo minorDisciplineInfo)
            throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            VersionMismatchException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public ProgramRequirementInfo updateProgramRequirement(
            ProgramRequirementInfo programRequirementInfo)
            throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            VersionMismatchException, OperationFailedException,
            PermissionDeniedException {
    	checkForMissingParameter(programRequirementInfo, "programRequirementInfo");
        // Validate
        List<ValidationResultInfo> validationResults = validateProgramRequirement("OBJECT", programRequirementInfo);
        if (ValidatorUtils.hasErrors(validationResults)) {
        	throw new DataValidationErrorException("Validation error!", validationResults);
        }

        try {
			return processProgramRequirement(programRequirementInfo, NodeOperation.UPDATE);
		} catch (AssemblyException e) {
			throw new OperationFailedException("Unable to update ProgramRequirement", e);
		}
    }

    @Override
    public List<ValidationResultInfo> validateCredentialProgram(
            String validationType, CredentialProgramInfo credentialProgramInfo)
            throws InvalidParameterException,
            MissingParameterException, OperationFailedException {

        List<ValidationResultInfo> validationResults = new ArrayList<ValidationResultInfo>();
//        if ( ! ProgramAssemblerConstants.DRAFT.equals(credentialProgramInfo.getState()) ) {
            ObjectStructureDefinition objStructure = this.getObjectStructure(CredentialProgramInfo.class.getName());
            Validator validator = validatorFactory.getValidator();
            validationResults.addAll(validator.validateObject(credentialProgramInfo, objStructure));
//        }

        return validationResults;
    }

    @Override
    public List<ValidationResultInfo> validateHonorsProgram(
            String validationType, HonorsProgramInfo honorsProgramInfo)
            throws InvalidParameterException,
            MissingParameterException, OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateMajorDiscipline(
            String validationType, MajorDisciplineInfo majorDisciplineInfo)
            throws InvalidParameterException,
            MissingParameterException, OperationFailedException {

        List<ValidationResultInfo> validationResults = new ArrayList<ValidationResultInfo>();
//        if ( ! ProgramAssemblerConstants.DRAFT.equalsIgnoreCase(majorDisciplineInfo.getState()) ) {
            ObjectStructureDefinition objStructure = this.getObjectStructure(MajorDisciplineInfo.class.getName());
            Validator validator = validatorFactory.getValidator();
            validationResults.addAll(validator.validateObject(majorDisciplineInfo, objStructure));
//        }
        validateMajorDisciplineAtps(majorDisciplineInfo,validationResults);
        return validationResults;
    }

    @Override
    public List<ValidationResultInfo> validateMinorDiscipline(
            String validationType, MinorDisciplineInfo minorDisciplineInfo)
            throws InvalidParameterException,
            MissingParameterException, OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateProgramRequirement(
            String validationType, ProgramRequirementInfo programRequirementInfo)
            throws InvalidParameterException,
            MissingParameterException, OperationFailedException {

        ObjectStructureDefinition objStructure = this.getObjectStructure(ProgramRequirementInfo.class.getName());
        Validator validator = validatorFactory.getValidator();
        List<ValidationResultInfo> validationResults = validator.validateObject(programRequirementInfo, objStructure);

        return validationResults;
    }

    @Override
    public ObjectStructureDefinition getObjectStructure(String objectTypeKey) {
        return dictionaryService.getObjectStructure(objectTypeKey);
    }

    @Override
    public List<String> getObjectTypes() {
        return dictionaryService.getObjectTypes();
    }

    @Override
    public SearchCriteriaTypeInfo getSearchCriteriaType(
            String searchCriteriaTypeKey) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<SearchCriteriaTypeInfo> getSearchCriteriaTypes()
            throws OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SearchResultTypeInfo getSearchResultType(String searchResultTypeKey)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<SearchResultTypeInfo> getSearchResultTypes()
            throws OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SearchTypeInfo getSearchType(String searchTypeKey)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<SearchTypeInfo> getSearchTypes()
            throws OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<SearchTypeInfo> getSearchTypesByCriteria(
            String searchCriteriaTypeKey) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<SearchTypeInfo> getSearchTypesByResult(
            String searchResultTypeKey) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SearchResult search(SearchRequest searchRequest)
            throws MissingParameterException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Check for missing parameter and throw localized exception if missing
     *
     * @param param
     * @param parameter name
     * @throws MissingParameterException
     */
    private void checkForMissingParameter(Object param, String paramName)
            throws MissingParameterException {
        if (param == null) {
            throw new MissingParameterException(paramName + " can not be null");
        }
    }

    // TODO - when CRUD for a second ProgramInfo is implemented, pull common code up from its process*() and this

    private MajorDisciplineInfo processMajorDisciplineInfo(MajorDisciplineInfo majorDisciplineInfo, NodeOperation operation) throws AssemblyException {

        BaseDTOAssemblyNode<MajorDisciplineInfo, CluInfo> results = majorDisciplineAssembler.disassemble(majorDisciplineInfo, operation);
        invokeServiceCalls(results);
        return results.getBusinessDTORef();
    }

    private CredentialProgramInfo processCredentialProgramInfo(CredentialProgramInfo credentialProgramInfo, NodeOperation operation) throws AssemblyException {

        BaseDTOAssemblyNode<CredentialProgramInfo, CluInfo> results = credentialProgramAssembler.disassemble(credentialProgramInfo, operation);
        invokeServiceCalls(results);
        return results.getBusinessDTORef();
    }

    private ProgramRequirementInfo processProgramRequirement(ProgramRequirementInfo programRequirementInfo, NodeOperation operation) throws AssemblyException {
        BaseDTOAssemblyNode<ProgramRequirementInfo, CluInfo> results = programRequirementAssembler.disassemble(programRequirementInfo, operation);
        invokeServiceCalls(results);
        return results.getBusinessDTORef();
    }

	private void invokeServiceCalls(BaseDTOAssemblyNode<?, CluInfo> results) throws AssemblyException{
        // Use the results to make the appropriate service calls here
        try {
            programServiceMethodInvoker.invokeServiceCalls(results);
        } catch (AssemblyException e) {
        	throw e;
        } catch (Exception e) {
            throw new AssemblyException(e);
        }
    }

    //Spring setters. Used by spring container to inject corresponding dependencies.

    public void setLuService(LuService luService) {
        this.luService = luService;
    }

    public LuService getLuService() {
		return luService;
	}

	public void setDictionaryService(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    public DictionaryService getDictionaryService() {
		return dictionaryService;
	}

    public void setSearchManager(SearchManager searchManager) {
        this.searchManager = searchManager;
    }
    
	public SearchManager getSearchManager() {
		return searchManager;
	}

	public void setMajorDisciplineAssembler(MajorDisciplineAssembler majorDisciplineAssembler) {
        this.majorDisciplineAssembler = majorDisciplineAssembler;
    }

	public MajorDisciplineAssembler getMajorDisciplineAssembler() {
		return majorDisciplineAssembler;
	}

	public void setCredentialProgramAssembler(
			CredentialProgramAssembler credentialProgramAssembler) {
		this.credentialProgramAssembler = credentialProgramAssembler;
	}

	public CredentialProgramAssembler getCredentialProgramAssembler() {
		return credentialProgramAssembler;
	}

	public void setProgramRequirementAssembler(ProgramRequirementAssembler programRequirementAssembler) {
        this.programRequirementAssembler = programRequirementAssembler;
    }

    public ProgramRequirementAssembler getProgramRequirementAssembler() {
		return programRequirementAssembler;
	}

	public void setProgramServiceMethodInvoker(BusinessServiceMethodInvoker serviceMethodInvoker) {
        this.programServiceMethodInvoker = serviceMethodInvoker;
    }

    public BusinessServiceMethodInvoker getProgramServiceMethodInvoker() {
		return programServiceMethodInvoker;
	}

	public void setValidatorFactory(ValidatorFactory validatorFactory) {
        this.validatorFactory = validatorFactory;
    }

	public ValidatorFactory getValidatorFactory() {
		return validatorFactory;
	}
	
	public void setCoreProgramAssembler(CoreProgramAssembler coreProgramAssembler) {
		this.coreProgramAssembler = coreProgramAssembler;
	}

	public CoreProgramAssembler getCoreProgramAssembler() {
		return coreProgramAssembler;
	}

	private StatusInfo getStatus(){
        StatusInfo status = new StatusInfo();
        status.setSuccess(true);
        return status;
	}

    private CoreProgramInfo processCoreProgramInfo(CoreProgramInfo coreProgramInfo, NodeOperation operation) throws AssemblyException {

        BaseDTOAssemblyNode<CoreProgramInfo, CluInfo> results = coreProgramAssembler.disassemble(coreProgramInfo, operation);
        invokeServiceCalls(results);
        return results.getBusinessDTORef();
    }

    @Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public CoreProgramInfo createCoreProgram(CoreProgramInfo coreProgramInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(coreProgramInfo, "CoreProgramInfo");
        
        // Validate
        List<ValidationResultInfo> validationResults = validateCoreProgram("OBJECT", coreProgramInfo);
        if (ValidatorUtils.hasErrors(validationResults)) {
            throw new DataValidationErrorException("Validation error!", validationResults);
        }

        try {
            return processCoreProgramInfo(coreProgramInfo, NodeOperation.CREATE);
        } catch (AssemblyException e) {
            LOG.error("Error disassembling CoreProgram", e);
            throw new OperationFailedException("Error disassembling CoreProgram");
        }
    }

	@Override
	@Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public CoreProgramInfo createNewCoreProgramVersion(
			String coreProgramId, String versionComment)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, VersionMismatchException,
			DataValidationErrorException {
		//step one, get the original
		VersionDisplayInfo currentVersion = luService.getCurrentVersion(LuServiceConstants.CLU_NAMESPACE_URI, coreProgramId);
		CoreProgramInfo originalCoreProgram = getCoreProgram(currentVersion.getId());

		//Version the Clu
		CluInfo newVersionClu = luService.createNewCluVersion(coreProgramId, versionComment);

		try {
	        BaseDTOAssemblyNode<CoreProgramInfo, CluInfo> results;

	        //Integrate changes into the original. (should this just be just the id?)
			coreProgramAssembler.assemble(newVersionClu, originalCoreProgram, true);

			//Clear Ids from the original so it will make a copy and do other processing
			processCopy(originalCoreProgram, currentVersion.getId());

			//Disassemble the new
			results = coreProgramAssembler.disassemble(originalCoreProgram, NodeOperation.UPDATE);

			// Use the results to make the appropriate service calls here
			programServiceMethodInvoker.invokeServiceCalls(results);

			return results.getBusinessDTORef();
		} catch(AssemblyException e) {
			throw new OperationFailedException("Error creating new MajorDiscipline version",e);
		} catch (AlreadyExistsException e) {
			throw new OperationFailedException("Error creating new MajorDiscipline version",e);
		} catch (DependentObjectsExistException e) {
			throw new OperationFailedException("Error creating new MajorDiscipline version",e);
		} catch (CircularRelationshipException e) {
			throw new OperationFailedException("Error creating new MajorDiscipline version",e);
		} catch (UnsupportedActionException e) {
			throw new OperationFailedException("Error creating new MajorDiscipline version",e);
		} catch (CircularReferenceException e) {
			throw new OperationFailedException("Error creating new MajorDiscipline version",e);
		}
	}
    


	@Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public StatusInfo deleteCoreProgram(String coreProgramId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
//        try {
//        	CoreProgramInfo coreProgramInfo = getCoreProgram(coreProgramId);
//
//            processCoreProgramInfo(coreProgramInfo, NodeOperation.DELETE);
//
//            return getStatus();
//
//        } catch (AssemblyException e) {
//            LOG.error("Error disassembling CoreProgram", e);
//            throw new OperationFailedException("Error disassembling CoreProgram");
//        }
    	throw new OperationFailedException("Deletion of CoreProgram is not supported."); 
    }

    @Override
    @Transactional(readOnly=true)
    public CoreProgramInfo getCoreProgram(String coreProgramId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    	CoreProgramInfo coreProgramInfo = null;

        try {
            CluInfo clu = luService.getClu(coreProgramId);
            if ( ! ProgramAssemblerConstants.CORE_PROGRAM.equals(clu.getType()) ) {
                throw new DoesNotExistException("Specified CLU is not a CoreProgram");
            }
            coreProgramInfo = coreProgramAssembler.assemble(clu, null, false);
        } catch (AssemblyException e) {
            LOG.error("Error assembling CoreProgram", e);
            throw new OperationFailedException("Error assembling CoreProgram");
        }
        return coreProgramInfo;
		// comment out the above, and uncomment below to get auto-generated data
        // (and vice-versa)
//		try {
//			return new CoreProgramDataGenerator().getCoreProgramInfoTestData();
//		} catch (Exception e) {
//			return null;
//		}
    }

    @Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public CoreProgramInfo updateCoreProgram(CoreProgramInfo coreProgramInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, VersionMismatchException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(coreProgramInfo, "CoreProgramInfo");
        
        // Validate
        List<ValidationResultInfo> validationResults = validateCoreProgram("OBJECT", coreProgramInfo);
        if (ValidatorUtils.hasErrors(validationResults)) {
            throw new DataValidationErrorException("Validation error!", validationResults);
        }

        try {

            return processCoreProgramInfo(coreProgramInfo, NodeOperation.UPDATE);

        } catch (AssemblyException e) {
            LOG.error("Error disassembling CoreProgram", e);
            throw new OperationFailedException("Error disassembling CoreProgram");
        }
    }

    @Override
    public List<ValidationResultInfo> validateCoreProgram(String validationType, CoreProgramInfo coreProgramInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        List<ValidationResultInfo> validationResults = new ArrayList<ValidationResultInfo>();
//        if ( ! ProgramAssemblerConstants.DRAFT.equals(coreProgramInfo.getState()) ) {
	        ObjectStructureDefinition objStructure = this.getObjectStructure(CoreProgramInfo.class.getName());
	        Validator validator = validatorFactory.getValidator();
            validationResults.addAll(validator.validateObject(coreProgramInfo, objStructure));
//        }
        return validationResults;
    }
        
        
	@Override
	@Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public CredentialProgramInfo createNewCredentialProgramVersion(
			String credentialProgramId, String versionComment)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, VersionMismatchException,
			DataValidationErrorException {
		//step one, get the original
		VersionDisplayInfo currentVersion = luService.getCurrentVersion(LuServiceConstants.CLU_NAMESPACE_URI, credentialProgramId);
		CredentialProgramInfo originaCredentialProgram = getCredentialProgram(currentVersion.getId());

		//Version the Clu
		CluInfo newVersionClu = luService.createNewCluVersion(credentialProgramId, versionComment);

		try {
	        BaseDTOAssemblyNode<CredentialProgramInfo, CluInfo> results;

	        //Integrate changes into the original. (should this just be just the id?)
			credentialProgramAssembler.assemble(newVersionClu, originaCredentialProgram, true);

			//Clear Ids from the original so it will make a copy and do other processing
			processCopy(originaCredentialProgram, currentVersion.getId());

			//Disassemble the new
			results = credentialProgramAssembler.disassemble(originaCredentialProgram, NodeOperation.UPDATE);

			// Use the results to make the appropriate service calls here
			programServiceMethodInvoker.invokeServiceCalls(results);

			return results.getBusinessDTORef();
		} catch(AssemblyException e) {
			throw new OperationFailedException("Error creating new MajorDiscipline version",e);
		} catch (AlreadyExistsException e) {
			throw new OperationFailedException("Error creating new MajorDiscipline version",e);
		} catch (DependentObjectsExistException e) {
			throw new OperationFailedException("Error creating new MajorDiscipline version",e);
		} catch (CircularRelationshipException e) {
			throw new OperationFailedException("Error creating new MajorDiscipline version",e);
		} catch (UnsupportedActionException e) {
			throw new OperationFailedException("Error creating new MajorDiscipline version",e);
		} catch (CircularReferenceException e) {
			throw new OperationFailedException("Error creating new MajorDiscipline version",e);
		}
	}


	@Override
	@Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public StatusInfo setCurrentCoreProgramVersion(String coreProgramId,
			Date currentVersionStart) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			IllegalVersionSequencingException, OperationFailedException,
			PermissionDeniedException {
		StatusInfo status = luService.setCurrentCluVersion(coreProgramId, currentVersionStart);
		
		return status;
	}

	@Override
	@Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public StatusInfo setCurrentCredentialProgramVersion(
			String credentialProgramId, Date currentVersionStart)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, IllegalVersionSequencingException,
			OperationFailedException, PermissionDeniedException {
		StatusInfo status = luService.setCurrentCluVersion(credentialProgramId, currentVersionStart);
		
		return status;
	}

	@Override
    @Transactional(readOnly=true)
	public VersionDisplayInfo getCurrentVersion(String refObjectTypeURI,
			String refObjectId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		if(ProgramServiceConstants.PROGRAM_NAMESPACE_MAJOR_DISCIPLINE_URI.equals(refObjectTypeURI)){
			return luService.getCurrentVersion(LuServiceConstants.CLU_NAMESPACE_URI, refObjectId);
		}
		throw new InvalidParameterException("Object type: " + refObjectTypeURI + " is not known to this implementation");
	}

	@Override
    @Transactional(readOnly=true)
	public VersionDisplayInfo getCurrentVersionOnDate(String refObjectTypeURI,
			String refObjectId, Date date) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		if(ProgramServiceConstants.PROGRAM_NAMESPACE_MAJOR_DISCIPLINE_URI.equals(refObjectTypeURI)){
			return luService.getCurrentVersionOnDate(LuServiceConstants.CLU_NAMESPACE_URI, refObjectId, date);
		}
		throw new InvalidParameterException("Object type: " + refObjectTypeURI + " is not known to this implementation");
	}

	@Override
    @Transactional(readOnly=true)
	public VersionDisplayInfo getFirstVersion(String refObjectTypeURI,
			String refObjectId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		if(ProgramServiceConstants.PROGRAM_NAMESPACE_MAJOR_DISCIPLINE_URI.equals(refObjectTypeURI)){
			return luService.getFirstVersion(LuServiceConstants.CLU_NAMESPACE_URI, refObjectId);
		}
		throw new InvalidParameterException("Object type: " + refObjectTypeURI + " is not known to this implementation");

	}

	@Override
    @Transactional(readOnly=true)
	public VersionDisplayInfo getLatestVersion(String refObjectTypeURI,
			String refObjectId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		if(ProgramServiceConstants.PROGRAM_NAMESPACE_MAJOR_DISCIPLINE_URI.equals(refObjectTypeURI)){
			return luService.getLatestVersion(LuServiceConstants.CLU_NAMESPACE_URI, refObjectId);
		}
		throw new InvalidParameterException("Object type: " + refObjectTypeURI + " is not known to this implementation");

	}

	@Override
    @Transactional(readOnly=true)
	public VersionDisplayInfo getVersionBySequenceNumber(
			String refObjectTypeURI, String refObjectId, Long sequence)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		if(ProgramServiceConstants.PROGRAM_NAMESPACE_MAJOR_DISCIPLINE_URI.equals(refObjectTypeURI)){
			return luService.getVersionBySequenceNumber(LuServiceConstants.CLU_NAMESPACE_URI, refObjectId, sequence);
		}
		throw new InvalidParameterException("Object type: " + refObjectTypeURI + " is not known to this implementation");
	}

	@Override
    @Transactional(readOnly=true)
	public List<VersionDisplayInfo> getVersions(String refObjectTypeURI,
			String refObjectId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		if(ProgramServiceConstants.PROGRAM_NAMESPACE_MAJOR_DISCIPLINE_URI.equals(refObjectTypeURI)){
			return luService.getVersions(LuServiceConstants.CLU_NAMESPACE_URI, refObjectId);
		}
		throw new InvalidParameterException("Object type: " + refObjectTypeURI + " is not known to this implementation");
	}

	@Override
    @Transactional(readOnly=true)
	public List<VersionDisplayInfo> getVersionsInDateRange(
			String refObjectTypeURI, String refObjectId, Date from, Date to)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		if(ProgramServiceConstants.PROGRAM_NAMESPACE_MAJOR_DISCIPLINE_URI.equals(refObjectTypeURI)){
			return luService.getVersionsInDateRange(LuServiceConstants.CLU_NAMESPACE_URI, refObjectId, from, to);
		}
		throw new InvalidParameterException("Object type: " + refObjectTypeURI + " is not known to this implementation");
	}

	public void setAtpService(AtpService atpService) {
		this.atpService = atpService;
	}

	public AtpService getAtpService() {
		return atpService;
	}

	private void validateMajorDisciplineAtps(MajorDisciplineInfo majorDisciplineInfo, List<ValidationResultInfo> validationResults) throws InvalidParameterException, MissingParameterException, OperationFailedException{
		
		String startTerm = majorDisciplineInfo.getStartTerm();
		
		if(!isEmpty(majorDisciplineInfo.getAttributes().get("endInstAdmitTerm"))){
			compareAtps(startTerm, majorDisciplineInfo.getAttributes().get("endInstAdmitTerm"), validationResults, "End Inst Admin Term", "endInstAdmitTerm");
		}
		
		if(!isEmpty(majorDisciplineInfo.getEndProgramEntryTerm())){
			compareAtps(startTerm, majorDisciplineInfo.getEndProgramEntryTerm(), validationResults, "End Program Entry Term", "endProgramEntryTerm");
		}
		
		if(!isEmpty(majorDisciplineInfo.getEndTerm())){
			compareAtps(startTerm, majorDisciplineInfo.getEndTerm(), validationResults, "End Program Enroll Term", "endTerm");
		}		
		
		List<ProgramVariationInfo> variations = majorDisciplineInfo.getVariations();
		if(variations != null && !variations.isEmpty()){
			int idx = 0;
			for(ProgramVariationInfo variation : variations){
				validateVariationAtps(variation, validationResults, idx);
				idx ++;
			}
		}
	}
	
	//FIXME, this validation should be moved into a custom validation class + configuration
	private void validateVariationAtps(ProgramVariationInfo programVariationInfo, List<ValidationResultInfo> validationResults, int idx) throws InvalidParameterException, MissingParameterException, OperationFailedException{
		
		String startTerm = programVariationInfo.getStartTerm();
		
		if(!isEmpty(programVariationInfo.getAttributes().get("endInstAdmitTerm"))){
			compareAtps(startTerm, programVariationInfo.getAttributes().get("endInstAdmitTerm"), validationResults, "End Inst Admin Term",  "variations/" + idx + "/endInstAdmitTerm");
		}
	
		if(!isEmpty(programVariationInfo.getEndProgramEntryTerm())){
			compareAtps(startTerm, programVariationInfo.getEndProgramEntryTerm(), validationResults, "End Program Entry Term", "variations/" + idx + "/endProgramEntryTerm");
		}
		
		if(!isEmpty(programVariationInfo.getEndTerm())){
			compareAtps(startTerm, programVariationInfo.getEndTerm(), validationResults, "End Program Enroll Term", "variations/" + idx + "/endTerm");
		}
	}
	
	private AtpInfo getAtpInfo(String atpKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException{
		if(atpKey==null){
			return null;
		}
		return atpService.getAtp(atpKey);
	}
	//FIXME error should return using message service and not static text
	private void compareAtps(String aptKey1, String aptKey2, List<ValidationResultInfo> validationResults, String field, String path) throws InvalidParameterException, MissingParameterException, OperationFailedException{
		AtpInfo atpInfo1 = null;
		AtpInfo atpInfo2 = null;
		
		try{
			atpInfo1 = getAtpInfo(aptKey1);
			atpInfo2 = getAtpInfo(aptKey2);
		}catch(DoesNotExistException e){}
		
		if(atpInfo1 != null && atpInfo1 != null){
			if(atpInfo1.getStartDate()!= null && atpInfo2.getStartDate() != null){			
				boolean compareResult = ValidatorUtils.compareValues(atpInfo2.getStartDate(), atpInfo1.getStartDate(), DataType.DATE, "greater_than_equal", true, new ServerDateParser());
				if(!compareResult){
					ValidationResultInfo vri = new ValidationResultInfo();
					vri.setElement(path);
					vri.setError(field + " should be greater than Start Term");
					validationResults.add(vri);
				}
			}
		}
			
	}
	
	private boolean isEmpty(String value){
		return value == null || (value != null && "".equals(value));
	}

	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
	}

	public DocumentService getDocumentService() {
		return documentService;
	}

}
