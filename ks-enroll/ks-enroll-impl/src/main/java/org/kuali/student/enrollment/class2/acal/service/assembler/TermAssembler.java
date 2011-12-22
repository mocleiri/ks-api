package org.kuali.student.enrollment.class2.acal.service.assembler;


import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.r2.common.assembler.AssemblyException;
import org.kuali.student.r2.common.assembler.DTOAssembler;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.atp.dto.AtpInfo;

public class TermAssembler implements DTOAssembler<TermInfo, AtpInfo>{
     
    @Override
    public TermInfo assemble(AtpInfo atp, ContextInfo context) throws AssemblyException {
        if(atp != null){
            TermInfo term = new TermInfo();
            term.setId(atp.getId());
            term.setName(atp.getName());
            term.setDescr(atp.getDescr());
            term.setStartDate(atp.getStartDate());
            term.setEndDate(atp.getEndDate());
            term.setTypeKey(atp.getTypeKey());
            term.setStateKey(atp.getStateKey());
            term.setMeta(atp.getMeta());
            term.setAttributes(atp.getAttributes());
            
            return term;
        }
        else
            return null;
    }

    @Override
    public AtpInfo disassemble(TermInfo term, ContextInfo context) throws AssemblyException{
        AtpInfo atp = new AtpInfo();
        atp.setId(term.getId());
        atp.setName(term.getName());
        atp.setDescr(term.getDescr());
        atp.setStartDate(term.getStartDate());
        atp.setEndDate(term.getEndDate());
        atp.setTypeKey(term.getTypeKey());
        atp.setStateKey(term.getStateKey());
        atp.setMeta(term.getMeta());
        atp.setAttributes(term.getAttributes());

        return atp;
    }

}
