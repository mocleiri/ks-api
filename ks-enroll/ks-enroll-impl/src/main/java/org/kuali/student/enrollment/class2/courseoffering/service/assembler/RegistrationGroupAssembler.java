package org.kuali.student.enrollment.class2.courseoffering.service.assembler;

import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.infc.DTOAssembler;

public class RegistrationGroupAssembler implements DTOAssembler<RegistrationGroupInfo, LuiInfo>{
	private CourseOfferingAssemblerUtils coAssemblerUtils;
	
	public CourseOfferingAssemblerUtils getCoAssemblerUtils() {
		return coAssemblerUtils;
	}

	public void setCoAssemblerUtils(CourseOfferingAssemblerUtils coAssemblerUtils) {
		this.coAssemblerUtils = coAssemblerUtils;
	}
	
	@Override
	public RegistrationGroupInfo assemble(LuiInfo lui, ContextInfo context) {
		if(lui != null){
			RegistrationGroupInfo rg = new RegistrationGroupInfo();
			coAssemblerUtils.assembleBasics(lui, rg);
			coAssemblerUtils.assembleCommons(lui, rg);
			
			return rg;
		}
		else
			return null;
	}

	@Override
	public LuiInfo disassemble(RegistrationGroupInfo rg, ContextInfo context) {
		if(rg != null){			
			LuiInfo lui = new LuiInfo();
			coAssemblerUtils.disassembleBasics(lui, rg);
			coAssemblerUtils.disassembleCommons(lui, rg);
			
			return lui;
		}
		return null;
	}

}
