package org.kuali.student.enrollment.class2.courseoffering.service.assembler;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.R1ToR2CopyHelper;
import org.kuali.student.enrollment.courseregistration.dto.RegGroupRegistrationInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lui.dto.LuiIdentifierInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.dto.LuiLuiRelationInfo;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.r2.common.assembler.DTOAssembler;
import org.kuali.student.r2.common.assembler.EntityDTOAssembler;
import org.kuali.student.r2.common.assembler.RelationshipDTOAssembler;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;

public class CourseOfferingAssembler implements DTOAssembler<CourseOfferingInfo, LuiInfo>{
	private LuiService luiService;
	

	public LuiService getLuiService() {
		return luiService;
	}

	public void setLuiService(LuiService luiService) {
		this.luiService = luiService;
	}


	@Override
	public CourseOfferingInfo assemble(LuiInfo lui, ContextInfo context) {
		if(lui != null){
			CourseOfferingInfo co = new CourseOfferingInfo();
            EntityDTOAssembler<LuiInfo, CourseOfferingInfo> commonAssembler = new EntityDTOAssembler<LuiInfo, CourseOfferingInfo>();
            co = commonAssembler.assemble(lui, co, context);
			co.setId(lui.getId());
			co.setMaximumEnrollment(lui.getMaximumEnrollment());
			co.setMinimumEnrollment(lui.getMinimumEnrollment());

			//TODO: co.setIsHonorsOffering(isHonorsOffering) -- lui.getLuiCodes() ?
			
			//below undecided
			//co.setHasWaitlist(lui.getHasWaitlist());
			//co.setWaitlistTypeKey(lui.getWaitlistTypeKey());
			//co.setWaitlistMaximum(lui.getWaitlistMaximum());
			//co.setIsWaitlistCheckinRequired(lui.getIsWaitlistCheckinRequired());
			//co.setWaitlistCheckinFrequency(lui.getWaitlistCheckinFrequency());
			
			co.setCourseId(lui.getCluId());
			co.setTermKey(lui.getAtpKey());
			co.setUnitsDeployment(lui.getUnitsDeployment());
			co.setUnitsContentOwner(lui.getUnitsContentOwner());
			
			co.setFees(lui.getFees());
			co.setRevenues(lui.getRevenues());
			co.setExpenditure(lui.getExpenditure());
			co.setFormatIds(lui.getCluCluRelationIds());
			
			assembleIdentifier(lui, co);
			
			//TODO: lui.getResultOptionIds() -- co.setCreditOptions & co.setGradingOptionIds --- call LRCService.getResultValuesByIdList
			//TODO: co.setInstructors(instructors) -- call LPRService.findPersonIdsRelatedToLui?
			
			//lui.getAlternateIdentifiers() -- where to map?
			//lui.getName() -- where to map?
			//lui.getReferenceURL() -- where to map?
		
		
			//LuiLuiRelation (to set jointOfferingIds, hasFinalExam)
			 assembleLuiLuiRelations(co, lui.getId(), context);
			
			return co;
		}
		else
			return null;
	}

	private void assembleLuiLuiRelations(CourseOfferingInfo co, String luiId, ContextInfo context){
		try {
			List<String> jointOfferingIds = new ArrayList<String>();
			List<String> finalExams = new ArrayList<String>();
			List<LuiLuiRelationInfo> rels = luiService.getLuiLuiRelationsByLui(luiId, context);
			if(rels != null && !rels.isEmpty()){                  
                for(LuiLuiRelationInfo rel : rels){
                	if(rel.getLuiId().equals(luiId)){
                		if(rel.getTypeKey().equals(LuiServiceConstants.LUI_LUI_RELATION_ASSOCIATED_TYPE_KEY)){
                			LuiInfo lui1 = luiService.getLui(rel.getRelatedLuiId(), context);
                			if(lui1 != null && lui1.getTypeKey().equals(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY) && !jointOfferingIds.contains(rel.getRelatedLuiId())){
                				jointOfferingIds.add(rel.getRelatedLuiId());
                			}
                		}
                		
                   		if(rel.getTypeKey().equals("kuali.lui.lui.relation.IsDeliveredVia")){
                			LuiInfo lui2 = luiService.getLui(rel.getRelatedLuiId(), context);
                			if(lui2 != null && lui2.getTypeKey().equals("kuali.lui.type.course.finalExam") && !finalExams.contains(rel.getRelatedLuiId())){
                				finalExams.add(rel.getRelatedLuiId());
                			}
                		}
                	}
                }
			}
			
			if (!jointOfferingIds.isEmpty()) co.setJointOfferingIds(jointOfferingIds);
				
			if (finalExams.size() > 0) co.setHasFinalExam(true);
			
		} catch (DoesNotExistException e) {
			return;
		} catch (InvalidParameterException e) {
			e.printStackTrace();
		} catch (MissingParameterException e) {
			e.printStackTrace();
		} catch (OperationFailedException e) {
			e.printStackTrace();
		}
	}
	@Override
	public LuiInfo disassemble(CourseOfferingInfo co, ContextInfo context) {
		if(co != null){			
			LuiInfo lui = new LuiInfo();
			lui.setId(co.getId());
			lui.setTypeKey(co.getTypeKey());
			lui.setStateKey(co.getStateKey());
			lui.setDescr(co.getDescr());
			lui.setMeta(co.getMeta());
			lui.setAttributes(co.getAttributes());
			
			//TODO: co.getIsHonorsOffering() --store in a generic lui luCodes type of field?
			
			//below undecided
			//lui.setHasWaitlist(co.getHasWaitlist());
			//lui.setIsWaitlistCheckinRequired(co.getIsWaitlistCheckinRequired());
			//lui.setWaitlistCheckinFrequency(co.getWaitlistCheckinFrequency());
			//lui.setWaitlistMaximum(co.getWaitlistMaximum());
			//lui.setWaitlistTypeKey(co.getWaitlistTypeKey());
			
			lui.setCluId(co.getCourseId());
			lui.setCluCluRelationIds(co.getFormatIds());
			lui.setAtpKey(co.getTermKey());
			lui.setUnitsContentOwner(co.getUnitsContentOwner());
			lui.setUnitsDeployment(co.getUnitsDeployment());
		
			lui.setFees(co.getFees());
			lui.setExpenditure(co.getExpenditure());
			lui.setRevenues(co.getRevenues());
	        
			disassembleIdentifier(co, lui);
					
			//TODO: the following mapping undecided on wiki
			//gradeRosterLevelTypeKey
			//fundingSource
			//isFinancialAidEligible
			//registrationOrderTypeKey
						
			return lui;
		}
		else
			return null;
	}

	private void disassembleIdentifier(CourseOfferingInfo co, LuiInfo lui){
		LuiIdentifierInfo identifier = new LuiIdentifierInfo();
		identifier.setCode(co.getCourseOfferingCode());
		identifier.setSuffixCode(co.getCourseNumberSuffix());
		identifier.setLongName(co.getCourseTitle());
		identifier.setDivision(co.getSubjectArea());
		lui.setOfficialIdentifier(identifier);		
	}
	
	private void assembleIdentifier(LuiInfo lui, CourseOfferingInfo co){
		LuiIdentifierInfo identifier = lui.getOfficialIdentifier();
		if(identifier != null){
			co.setCourseOfferingCode(identifier.getCode());
			co.setCourseNumberSuffix(identifier.getSuffixCode());
			co.setCourseTitle(identifier.getLongName());
			co.setSubjectArea(identifier.getDivision());
		}
	}
	
	public CourseOfferingInfo assemble(CourseInfo courseInfo){
		CourseOfferingInfo courseOfferingInfo = new CourseOfferingInfo();
		courseOfferingInfo.setCourseId(courseInfo.getId());
		courseOfferingInfo.setCourseNumberSuffix(courseInfo.getCourseNumberSuffix());
		courseOfferingInfo.setCourseTitle(courseInfo.getCourseTitle());
		courseOfferingInfo.setSubjectArea(courseInfo.getSubjectArea());
		courseOfferingInfo.setCourseOfferingCode(courseInfo.getCode());
		courseOfferingInfo.setUnitsContentOwner(courseInfo.getUnitsContentOwner());
		courseOfferingInfo.setUnitsDeployment(courseInfo.getUnitsDeployment());
		courseOfferingInfo.setGradingOptionIds(courseInfo.getGradingOptions());
		if (courseInfo.getCreditOptions() == null) {
		    courseOfferingInfo.setCreditOptions(null);
		} else if (courseInfo.getCreditOptions().isEmpty()) {
		    courseOfferingInfo.setCreditOptions(null);
		} else {
		    courseOfferingInfo.setCreditOptions(new R1ToR2CopyHelper().copyResultValuesGroup(courseInfo.getCreditOptions().get(0)));
		}
		courseOfferingInfo.setDescr(new R1ToR2CopyHelper().copyRichText(courseInfo.getDescr()));
		courseOfferingInfo.setExpenditure(new R1ToR2CopyHelper().copyCourseExpenditure(courseInfo.getExpenditure()));
		courseOfferingInfo.setFees(new R1ToR2CopyHelper().copyCourseFeeList(courseInfo.getFees()));
		courseOfferingInfo.setInstructors(new R1ToR2CopyHelper().copyInstructors(courseInfo.getInstructors()));		
		
		return courseOfferingInfo;
	}

}
