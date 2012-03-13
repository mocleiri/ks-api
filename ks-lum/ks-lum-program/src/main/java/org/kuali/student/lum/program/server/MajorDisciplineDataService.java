package org.kuali.student.lum.program.server;

import java.util.List;
import java.util.Map;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r1.common.dto.DtoConstants;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.common.ui.server.gwt.AbstractDataService;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.lum.program.client.ProgramClientConstants;
import org.kuali.student.r2.lum.program.dto.MajorDisciplineInfo;
import org.kuali.student.r2.lum.program.service.ProgramService;

/**
 * @author Igor
 */
public class MajorDisciplineDataService extends AbstractDataService {

    private static final long serialVersionUID = 1L;
    
    private ProgramService programService;
    private CluService cluService;

    @Override
    protected String getDefaultWorkflowDocumentType() {
        return null;
    }

    @Override
    protected String getDefaultMetaDataState() {
        return null;
    }

    @Override
    protected Object get(String id, ContextInfo contextInfo) throws Exception {
    	//TODO Just Major Discipline for now - need to check for other types later
        MajorDisciplineInfo returnDTO;
        if (null == id || id.length() == 0) {
            returnDTO = new MajorDisciplineInfo();
            returnDTO.setTypeKey(ProgramClientConstants.MAJOR_PROGRAM);
            returnDTO.setStateKey(DtoConstants.STATE_DRAFT);
            returnDTO.setCredentialProgramId(getCredentialId());
        } else {
            returnDTO = programService.getMajorDiscipline(id, ContextUtils.getContextInfo());
        }
        return returnDTO;
    }

    @Override
    protected Object save(Object dto, Map<String, Object> properties, ContextInfo contextInfo) throws Exception {
        if (dto instanceof MajorDisciplineInfo) {
            MajorDisciplineInfo mdInfo = (MajorDisciplineInfo) dto;
            if (mdInfo.getId() == null && mdInfo.getVersionInfo() != null) {
            	String majorVersionIndId = null;
            	// TODO KSCM majorVersionIndId = mdInfo.getVersionInfo().getVersionIndId();
            	mdInfo = programService.createNewMajorDisciplineVersion(majorVersionIndId, "New major discipline version",ContextUtils.getContextInfo());
            } else if (mdInfo.getId() == null){
                mdInfo = programService.createMajorDiscipline(mdInfo.getId(), mdInfo, ContextUtils.getContextInfo());
            } else {
            	mdInfo = programService.updateMajorDiscipline(mdInfo, ContextUtils.getContextInfo());
            }
            return mdInfo;
        } else {
            throw new InvalidParameterException("Only persistence of MajorDiscipline is supported by this DataService implementation.");
        }
    }  

    
    @Override
	protected List<ValidationResultInfo> validate(Object dto, ContextInfo contextInfo) throws Exception {
		return programService.validateMajorDiscipline("OBJECT", (MajorDisciplineInfo)dto, ContextUtils.getContextInfo());
	}
    
    @Override
    protected Class<?> getDtoClass() {
        return MajorDisciplineInfo.class;
    }

    private String getCredentialId() throws Exception {
            List<String> credIds = cluService.getCluIdsByLuType(ProgramClientConstants.CREDENTIAL_BACCALAUREATE_PROGRAM, DtoConstants.STATE_ACTIVE, ContextUtils.getContextInfo());
            if (null == credIds || credIds.size() != 1) {
                throw new OperationFailedException("A single credential program of type " + ProgramClientConstants.CREDENTIAL_BACCALAUREATE_PROGRAM + " is required; database contains " +
                                                    (null == credIds ? "0" : credIds.size() +
                                                    "."));
            }
            return credIds.get(0);
    }
    
    

    public void setProgramService(ProgramService programService) {
        this.programService = programService;
    }

    public void setLuService(CluService cluService) {
        this.cluService = cluService;
    }

}
