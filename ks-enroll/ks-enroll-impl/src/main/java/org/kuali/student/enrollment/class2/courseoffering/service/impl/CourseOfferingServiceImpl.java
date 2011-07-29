package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.courseoffering.service.assembler.ActivityOfferingAssembler;
import org.kuali.student.enrollment.class2.courseoffering.service.assembler.CourseOfferingAssembler;
import org.kuali.student.enrollment.class2.courseoffering.service.assembler.RegistrationGroupAssembler;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.dto.LuiLuiRelationInfo;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.service.CourseService;
import org.kuali.student.r2.common.datadictionary.dto.DictionaryEntryInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StateInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.CircularReferenceException;
import org.kuali.student.r2.common.exceptions.CircularRelationshipException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.service.StateService;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly=true,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
public class CourseOfferingServiceImpl implements CourseOfferingService{
	private LuiService luiService;
	private CourseService courseService;
	private AcademicCalendarService acalService;
	private CourseOfferingAssembler coAssembler;
	private ActivityOfferingAssembler aoAssembler;
	private RegistrationGroupAssembler rgAssembler;
	private StateService stateService;
	
	public LuiService getLuiService() {
		return luiService;
	}

	public void setLuiService(LuiService luiService) {
		this.luiService = luiService;
	}

	public CourseService getCourseService() {
		return courseService;
	}

	public void setCourseService(CourseService courseService) {
		this.courseService = courseService;
	}

	public AcademicCalendarService getAcalService() {
		return acalService;
	}

	public void setAcalService(AcademicCalendarService acalService) {
		this.acalService = acalService;
	}

	public CourseOfferingAssembler getCoAssembler() {
		return coAssembler;
	}

	public void setCoAssembler(CourseOfferingAssembler coAssembler) {
		this.coAssembler = coAssembler;
	}

	public ActivityOfferingAssembler getAoAssembler() {
		return aoAssembler;
	}

	public void setAoAssembler(ActivityOfferingAssembler aoAssembler) {
		this.aoAssembler = aoAssembler;
	}

	public RegistrationGroupAssembler getRgAssembler() {
		return rgAssembler;
	}

	public void setRgAssembler(RegistrationGroupAssembler rgAssembler) {
		this.rgAssembler = rgAssembler;
	}

	public StateService getStateService() {
		return stateService;
	}

	public void setStateService(StateService stateService) {
		this.stateService = stateService;
	}

	@Override
	public List<String> getDataDictionaryEntryKeys(ContextInfo context)
			throws OperationFailedException, MissingParameterException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DictionaryEntryInfo getDataDictionaryEntry(String entryKey,
			ContextInfo context) throws OperationFailedException,
			MissingParameterException, PermissionDeniedException,
			DoesNotExistException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CourseOfferingInfo getCourseOffering(String courseOfferingId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		LuiInfo lui = luiService.getLui(courseOfferingId, context);		
		return coAssembler.assemble(lui, context);
	}

	@Override
	public List<CourseOfferingInfo> getCourseOfferingsForCourseAndTerm(
			String courseId, String termKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getCourseOfferingIdsForTerm(String termKey,
			Boolean useIncludedTerm, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getCourseOfferingIdsBySubjectArea(String termKey,
			String subjectArea, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getCourseOfferingIdsByUnitContentOwner(String termKey,
			String unitOwnerId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public CourseOfferingInfo createCourseOfferingFromCanonical(
			String courseId, String termKey, List<String> formatIdList,
			ContextInfo context) throws AlreadyExistsException,
			DoesNotExistException, DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        CourseOfferingInfo courseOfferingInfo = null;

        CourseInfo courseInfo = getCourse(courseId);
        if (courseInfo != null){
        	courseOfferingInfo = coAssembler.assemble(courseInfo);
        	courseOfferingInfo.setCourseId(courseId);
        }
        else
        	throw new DoesNotExistException("The course does not exist. course: " + courseId);
        
        //TODO: uncomment when make the following insert in ks-lui.sql working 
        //INSERT INTO KSEN_ATP (ID, NAME, START_DT, END_DT, ATP_TYPE_ID, ATP_STATE_ID, RT_DESCR_ID, VER_NBR) VALUES ('testTermId1', 'testTerm1', {ts '2000-01-01 00:00:00.0'}, {ts '2100-12-31 00:00:00.0'}, 'kuali.atp.type.Fall', 'kuali.atp.state.Draft', 'RICHTEXT-201', 0)
//        if(acalService.getTerm(termKey, context) != null)
//        	courseOfferingInfo.setTermKey(termKey);
//        else
//        	throw new DoesNotExistException("The term does not exist. term: " + termKey);
        courseOfferingInfo.setTermKey(termKey);
        
        if (checkExistenceForFormats(formatIdList, context))
        	courseOfferingInfo.setFormatIds(formatIdList);
        
        courseOfferingInfo.setStateKey(getStateKey(LuiServiceConstants.COURSE_OFFERING_PROCESS_KEY, LuiServiceConstants.LUI_DRAFT_STATE_KEY, context));
        courseOfferingInfo.setTypeKey(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY);
        
        LuiInfo luiInfo = coAssembler.disassemble(courseOfferingInfo, context);
        LuiInfo created = luiService.createLui(courseId, termKey, luiInfo, context);
        
        if (created != null)
        	courseOfferingInfo.setId(created.getId());
		return courseOfferingInfo;
	}

	private CourseInfo getCourse(String courseId)throws DoesNotExistException, DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException{
		CourseInfo course = null;
		try {
			course = courseService.getCourse(courseId);
		} catch (org.kuali.student.common.exceptions.DoesNotExistException e) {
			throw new DoesNotExistException("The course does not exist. course: " + courseId);
		} catch (org.kuali.student.common.exceptions.InvalidParameterException e) {
			throw new InvalidParameterException("The course has invalid parameter. course: " + courseId);
		} catch (org.kuali.student.common.exceptions.MissingParameterException e) {
			throw new MissingParameterException("The course is missing parameter. course: " + courseId);
		} catch (org.kuali.student.common.exceptions.OperationFailedException e) {
			throw new OperationFailedException("Operation failed when getting course: " + courseId);
		} catch (org.kuali.student.common.exceptions.PermissionDeniedException e) {
			throw new PermissionDeniedException("Permission denied when getting course: " + courseId);
		}
		
		return course;
	}
	
	//TODO:call LuService 
	private boolean checkExistenceForFormats(List<String> formatIds, ContextInfo context){
		if(formatIds != null && !formatIds.isEmpty()){
			for(String formatId : formatIds){
				//luService.getClu(formatId, context);
			}
		}

    	return true;
    }
	
	private String getStateKey(String processKey, String defaultState, ContextInfo context) throws DoesNotExistException, InvalidParameterException, 
			MissingParameterException, OperationFailedException{
        String stateKey = null;
        List<StateInfo> ivStates = stateService.getInitialValidStates(processKey, context);
        if(ivStates != null && ivStates.size() > 0)
        	stateKey = ivStates.get(0).getKey();
        else
        	stateKey = defaultState;	
        
        return stateKey;
	}
	
	@Override
	@Transactional
	public CourseOfferingInfo updateCourseOffering(String courseOfferingId,
			CourseOfferingInfo courseOfferingInfo, ContextInfo context)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
        try{
        	LuiInfo existing = luiService.getLui(courseOfferingId, context);
        	if(existing != null){
        		LuiInfo lui = coAssembler.disassemble(courseOfferingInfo, context);
	
	            if(lui != null){
	            	LuiInfo updated = luiService.updateLui(courseOfferingId, lui, context);
	            	if (updated != null)
	            		processRelationsForCourseOffering(courseOfferingInfo, context);
	            }
        	}
        	else
        		throw new DoesNotExistException("The CourseOffering does not exist: " + courseOfferingId);
        } catch (DoesNotExistException e1) {
            throw new DoesNotExistException("The CourseOffering does not exist: " + courseOfferingId);
        }
        
        return courseOfferingInfo;
	}
	
	private void processRelationsForCourseOffering(CourseOfferingInfo co, ContextInfo context) throws DataValidationErrorException, 
			DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
		//TODO: co.getInstructors() -- wire up with LuiPersonRelationService
		
		//TODO: hasFinalExam -- ignore for core slice
		//how to determine that the lui already exist?
		if(co.getHasFinalExam()) processFinalExam(co, context);
			
		//TODO:jointOfferingIds -- ignore for core slice

		//TODO: creditOptions -- ignore for core slice
		
		//TODO: gradingOptionIds -- ignore for core slice
	}

	private void processFinalExam(CourseOfferingInfo co, ContextInfo context) throws DataValidationErrorException, 
	DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
		String cluId = co.getCourseId();
		String atpKey = co.getTermKey();
		LuiInfo finalExam = new LuiInfo();
		finalExam.setCluId(cluId);
		finalExam.setAtpKey(atpKey);
		finalExam.setStateKey(co.getStateKey());
		//TODO: not sure what type
		finalExam.setTypeKey("kuali.lui.type.course.finalExam");
		//TODO: what else inherit or fill into finalExam?
		LuiInfo created;
		try {
			created = luiService.createLui(cluId, atpKey, finalExam, context);
		} catch (AlreadyExistsException e1) {
			throw new OperationFailedException("AlreadyExistsException when createLui. cluId: " + cluId + ", atpKey: " + atpKey);
		}
		
		if(created != null){
			try {
				createLuiLuiRelation(co.getId(), created.getId(), "kuali.lui.lui.relation.IsDeliveredVia", context);
			} catch (AlreadyExistsException e1) {
				throw new OperationFailedException();
			}
		}
	}
	
	@Override
	@Transactional
	public CourseOfferingInfo updateCourseOfferingFromCanonical(
			String courseOfferingId, ContextInfo context)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public StatusInfo deleteCourseOffering(String courseOfferingId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StatementTreeViewInfo> getCourseOfferingRestrictions(
			String courseOfferingId, String nlUsageTypeKey, String language,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatementTreeViewInfo createCourseOfferingRestriction(
			String courseOfferingId, StatementTreeViewInfo restrictionInfo,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			DataValidationErrorException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatementTreeViewInfo updateCourseOfferingRestriction(
			String courseOfferingId, StatementTreeViewInfo restrictionInfo,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			DataValidationErrorException, CircularReferenceException,
			VersionMismatchException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo deleteCourseOfferingRestriction(String courseOfferingId,
			String restrictionId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypeInfo getActivityOfferingType(String activityOfferingTypeKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TypeInfo> getAllActivityOfferingTypes(ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TypeInfo> getActivityOfferingTypesForActivityType(
			String activityTypeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActivityOfferingInfo getActivityOffering(String activityOfferingId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		LuiInfo lui = luiService.getLui(activityOfferingId, context);		
		return aoAssembler.assemble(lui, context);
	}

	@Override
	public List<ActivityOfferingInfo> getActivitiesForCourseOffering(
			String courseOfferingId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		return getActivitiesForRelation(courseOfferingId, "kuali.lui.lui.relation.IsDeliveredVia", "kuali.lui.type.course.finalExam", context);
	}


	private List<ActivityOfferingInfo> getActivitiesForRelation(String relatedLuiId, String relType, String exludedLuiType, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException{
		//TODO: implement LuiService.getLuiIdsByRelation and call it instead
		List<ActivityOfferingInfo> aos = new ArrayList<ActivityOfferingInfo>();
		List<String> aoIds = new ArrayList<String>();
		List<LuiLuiRelationInfo> rels = luiService.getLuiLuiRelationsByLui(relatedLuiId, context);
		if(rels != null && !rels.isEmpty()){                  
            for(LuiLuiRelationInfo rel : rels){
            	if(rel.getRelatedLuiId().equals(relatedLuiId)){
            		if(rel.getTypeKey().equals(relType)){
            			String luiId = rel.getLuiId();
            			LuiInfo lui = luiService.getLui(luiId, context);
            			if(lui != null && !lui.getTypeKey().equals(exludedLuiType) && !aoIds.contains(luiId)){
            				aoIds.add(luiId);
            				aos.add(getActivityOffering(luiId, context));
            			}
            		}
            	}
            }
		}
		
		return aos;		
	}
	
	@Override
	@Transactional
	public ActivityOfferingInfo createActivityOffering(
			List<String> courseOfferingIdList,
			ActivityOfferingInfo activityOfferingInfo, ContextInfo context)
			throws AlreadyExistsException, DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
       
		if(courseOfferingIdList != null && !courseOfferingIdList.isEmpty()){
			LuiInfo lui = aoAssembler.disassemble(activityOfferingInfo, context);
			try {
				LuiInfo created = luiService.createLui(activityOfferingInfo.getActivityId(), activityOfferingInfo.getTermKey(), lui, context);
				
				if(created != null){
					activityOfferingInfo.setId(created.getId());
					
					processRelationsForActivityOffering(courseOfferingIdList, activityOfferingInfo, context);
				}
				
				return activityOfferingInfo;
			} catch (DoesNotExistException e) {
				throw new OperationFailedException();
			}
		}
		else
			return null;
	}
	
	private void processRelationsForActivityOffering(List<String> courseOfferingIdList, ActivityOfferingInfo activityOfferingInfo, ContextInfo context)  
			throws AlreadyExistsException,DataValidationErrorException, InvalidParameterException, MissingParameterException, 
			OperationFailedException, PermissionDeniedException{
		
		processLuiluiRelationsForActivityOffering(courseOfferingIdList, activityOfferingInfo, context);
		
		//TODO: ao.getInstructors() -- wire up with LuiPersonRelationService
		
		//TODO: ao.setGradingOptionIds -- ignore for core slice
}

	
	private void processLuiluiRelationsForActivityOffering(List<String> courseOfferingIdList,
			ActivityOfferingInfo activityOfferingInfo, ContextInfo context) throws AlreadyExistsException,
			DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{

	    //TODO: Change this based on the changed structures for courseOferringInfo and activityOfferingInfo
	    
	    //		String luiId = activityOfferingInfo.getId();
//		for (String courseOfferingId : courseOfferingIdList) {
//			createLuiLuiRelation(luiId, courseOfferingId,"kuali.lui.lui.relation.IsDeliveredVia", context);
//		}
//		
//		if(activityOfferingInfo.getRegistrationGroupIds() != null && !activityOfferingInfo.getRegistrationGroupIds().isEmpty()){
//			for (String registrationGroupId : activityOfferingInfo.getRegistrationGroupIds()){
//				createLuiLuiRelation(luiId, registrationGroupId, "kuali.lui.lui.relation.RegisteredForVia", context);
//			}
//		}		
	}
	
	private void createLuiLuiRelation(String luiId, String relatedLuiId, String luLuRelationTypeKey, ContextInfo context) throws AlreadyExistsException, 
			DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
		try {
			luiService.createLuiLuiRelation(luiId, relatedLuiId, luLuRelationTypeKey, 
					initLuiLuiRelationInfo(luiId, relatedLuiId, luLuRelationTypeKey, context), context);
		} catch (CircularRelationshipException e) {
			throw new OperationFailedException();
		} catch (DoesNotExistException e) {
			throw new OperationFailedException();
		}
	}
	private LuiLuiRelationInfo initLuiLuiRelationInfo(String luiId, String relatedLuiId, String typeKey, ContextInfo context) throws InvalidParameterException, 
			MissingParameterException, OperationFailedException{
		LuiLuiRelationInfo luiRel = new LuiLuiRelationInfo();
		luiRel.setLuiId(luiId);
		luiRel.setRelatedLuiId(relatedLuiId);
		luiRel.setTypeKey(typeKey);
		try {
			luiRel.setStateKey(getStateKey(LuiServiceConstants.LUI_LUI_RELATION_PROCESS_KEY, LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY, context));
		} catch (DoesNotExistException e) {
			throw new OperationFailedException();
		}
		return luiRel;
	}

	@Override
	@Transactional
	public ActivityOfferingInfo updateActivityOffering(
			String activityOfferingId,
			ActivityOfferingInfo activityOfferingInfo, ContextInfo context)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public StatusInfo deleteActivityOffering(String activityOfferingId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StatementTreeViewInfo> getActivityOfferingRestrictions(
			String activityOfferingId, String nlUsageTypeKey, String language,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatementTreeViewInfo createActivityOfferingRestriction(
			String activityOfferingId, StatementTreeViewInfo restrictionInfo,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			DataValidationErrorException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatementTreeViewInfo updateActivityOfferingRestriction(
			String activityOfferingId, StatementTreeViewInfo restrictionInfo,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			DataValidationErrorException, CircularReferenceException,
			VersionMismatchException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo deleteActivityOfferingRestriction(
			String activityOfferingId, String restrictionId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Float calculateInClassContactHoursForTerm(String activityOfferingId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Float calculateOutofClassContactHoursForTerm(
			String activityOfferingId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Float calculateTotalContactHoursForTerm(String activityOfferingId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ActivityOfferingInfo> copyActivityOffering(
			String activityOfferingId, Integer numberOfCopies,
			String copyContextTypeKey, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RegistrationGroupInfo getRegistrationGroup(
			String registrationGroupId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RegistrationGroupInfo> getRegGroupsForCourseOffering(
			String courseOfferingId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RegistrationGroupInfo> getRegGroupsByFormatForCourse(
			String courseOfferingId, String formatTypeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public RegistrationGroupInfo createRegistrationGroup(
			String courseOfferingId,
			RegistrationGroupInfo registrationGroupInfo, ContextInfo context)
			throws AlreadyExistsException, DoesNotExistException,
			DataValidationErrorException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
//		if(courseOfferingId != null){
//			LuiInfo lui = rgAssembler.disassemble(registrationGroupInfo, context);
//			try {
//				LuiInfo created = luiService.createLui(registrationGroupInfo.get, registrationGroupInfo.getTermKey(), lui, context);
//				
//				if(created != null){
//					registrationGroupInfo.setId(created.getId());
//					
//					processRelationsForRegGroup(courseOfferingId, registrationGroupInfo, context);
//				}
				
//				return registrationGroupInfo;
//			} catch (DoesNotExistException e) {
//				throw new OperationFailedException();
//			}
//		}
//		else
			return null;
	}

	@Override
	@Transactional
	public RegistrationGroupInfo updateRegistrationGroup(
			String registrationGroupId,
			RegistrationGroupInfo registrationGroupInfo, ContextInfo context)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public StatusInfo deleteRegistrationGroup(String registrationGroupId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SeatPoolDefinitionInfo getSeatPoolDefinition(
			String seatPoolDefinitionId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SeatPoolDefinitionInfo> getSeatPoolsForCourseOffering(
			String courseOfferingId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SeatPoolDefinitionInfo> getSeatPoolsForRegGroup(
			String registrationGroupId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SeatPoolDefinitionInfo createSeatPoolDefinition(
			SeatPoolDefinitionInfo seatPoolDefinitionInfo, ContextInfo context)
			throws AlreadyExistsException, DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SeatPoolDefinitionInfo updateSeatPoolDefinition(
			String seatPoolDefinitionId,
			SeatPoolDefinitionInfo seatPoolDefinitionInfo, ContextInfo context)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo deleteSeatPoolDefinition(String seatPoolDefinitionId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public List<ValidationResultInfo> validateCourseOffering(String validationType, CourseOfferingInfo courseOfferingInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateCourseOfferingRestriction(String validationType, StatementTreeViewInfo restrictionInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateActivityOffering(String validationType, ActivityOfferingInfo activityOfferingInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateActivityOfferingRestriction(String validationType, StatementTreeViewInfo restrictionInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateRegistrationGroup(String validationType, RegistrationGroupInfo registrationGroupInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }
    
    @Override
    public List<CourseOfferingInfo> getCourseOfferingsByIdList(List<String> courseOfferingIds, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("getCourseOfferingsByIdList not supported");
    }

    @Override
    public List<ActivityOfferingInfo> getActivityOfferingsByIdList(List<String> activityOfferingIds, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("getActivityOfferingsByIdList not supported");
    }

    @Override
    public StatusInfo assignActivityToCourseOffering(String activityOfferingId, List<String> courseOfferingIdList, ContextInfo context) throws AlreadyExistsException, DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("assignActivityToCourseOffering not supported");    }

    @Override
    public List<RegistrationGroupInfo> getRegistrationGroupsByIdList(List<String> registrationGroupIds, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("getRegistrationGroupsByIdList not supported");
    }    
}
