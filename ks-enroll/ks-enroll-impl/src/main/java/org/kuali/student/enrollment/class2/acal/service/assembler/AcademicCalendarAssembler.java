package org.kuali.student.enrollment.class2.acal.service.assembler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.infc.AtpAssembler;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;
import org.kuali.student.r2.core.atp.dto.AtpAtpRelationInfo;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;

public class AcademicCalendarAssembler implements AtpAssembler<AcademicCalendarInfo, AtpInfo> {
    private AtpService atpService;
    private AtpAtpRelationAssembler relAssembler;
    
    public AtpService getAtpService() {
        return atpService;
    }

    public void setAtpService(AtpService atpService) {
        this.atpService = atpService;
    }

    
    public AtpAtpRelationAssembler getRelAssembler() {
		return relAssembler;
	}

	public void setRelAssembler(AtpAtpRelationAssembler relAssembler) {
		this.relAssembler = relAssembler;
	}

	@Override
    public AcademicCalendarInfo assemble(AtpInfo atp, ContextInfo context) {
        if(atp != null){
            AcademicCalendarInfo acal = new AcademicCalendarInfo();
            acal.setKey(atp.getKey());
            acal.setName(atp.getName());
            acal.setDescr(atp.getDescr());
            acal.setStartDate(atp.getStartDate());
            acal.setEndDate(atp.getEndDate());
            acal.setTypeKey(atp.getTypeKey());
            acal.setStateKey(atp.getStateKey());
            acal.setMeta(atp.getMeta()); 
            acal.setAttributes(atp.getAttributes()); 
            
            List<AttributeInfo> attributes = atp.getAttributes();
            if(attributes != null && !attributes.isEmpty()){
                for (AttributeInfo attribute : attributes){
                    if(attribute.getKey().equals("CredentialProgramType")){
                        acal.setCredentialProgramTypeKey(attribute.getValue());
                        break;
                    }
                }
            }

            //process atpatprelation-CampusCalendar
            acal.setCampusCalendarKeys(relAssembler.assemble(atp.getKey(), AtpServiceConstants.ATP_CAMPUS_CALENDAR_TYPE_KEY, context));

            return acal;
        }
        else
            return null;
    }

    @Override
    public AtpInfo disassemble(AcademicCalendarInfo acal, ContextInfo context) {
        AtpInfo atp = new AtpInfo();
        atp.setKey(acal.getKey());
        atp.setName(acal.getName());
        atp.setDescr(acal.getDescr());
        atp.setStartDate(acal.getStartDate());
        atp.setEndDate(acal.getEndDate());
        atp.setTypeKey(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY);
        atp.setStateKey(acal.getStateKey());
        atp.setMeta(acal.getMeta());

        List<AttributeInfo> attributes = (null != acal.getAttributes()? acal.getAttributes(): new ArrayList<AttributeInfo>());
        
        if(acal.getCredentialProgramTypeKey() != null){
            AttributeInfo cpt = new AttributeInfo();
            cpt.setKey("CredentialProgramType");
            cpt.setValue(acal.getCredentialProgramTypeKey());
            attributes.add(cpt);
        }
        atp.setAttributes(attributes);
        
        if(acal.getCampusCalendarKeys() != null && !acal.getCampusCalendarKeys().isEmpty()){
            try{
            	relAssembler.disassemble(acal.getKey(), acal.getCampusCalendarKeys(), AtpServiceConstants.ATP_CAMPUS_CALENDAR_TYPE_KEY, context);
            }catch (Exception e){
                return null;
            }
        }        
        
        return atp;   
    }

 }
