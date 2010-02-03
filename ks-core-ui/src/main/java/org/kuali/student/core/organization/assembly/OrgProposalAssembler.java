package org.kuali.student.core.organization.assembly;

import static org.kuali.student.core.assembly.util.AssemblerUtils.addVersionIndicator;
import static org.kuali.student.core.assembly.util.AssemblerUtils.getVersionIndicator;
import static org.kuali.student.core.assembly.util.AssemblerUtils.isCreated;
import static org.kuali.student.core.assembly.util.AssemblerUtils.isDeleted;
import static org.kuali.student.core.assembly.util.AssemblerUtils.isModified;
import static org.kuali.student.core.assembly.util.AssemblerUtils.isUpdated;
import static org.kuali.student.core.assembly.util.AssemblerUtils.setCreated;
import static org.kuali.student.core.assembly.util.AssemblerUtils.setUpdated;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.kuali.student.core.assembly.Assembler;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.SaveResult;
import org.kuali.student.core.assembly.dictionary.MetadataServiceImpl;
import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.organization.assembly.data.client.OrgMetadata;
import org.kuali.student.core.organization.assembly.data.client.OrgProposalMetadata;
import org.kuali.student.core.organization.assembly.data.client.org.OrgHelper;
import org.kuali.student.core.organization.assembly.data.client.org.OrgSearchHelper;
import org.kuali.student.core.organization.assembly.data.client.org.OrgorgRelationHelper;
import org.kuali.student.core.organization.assembly.data.server.OrgInfoData;
import org.kuali.student.core.organization.assembly.data.server.OrgInfoData.ModificationState;
import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.core.organization.dto.OrgOrgRelationInfo;
import org.kuali.student.core.organization.dto.OrgPositionRestrictionInfo;
import org.kuali.student.core.organization.service.OrganizationService;
import org.kuali.student.core.search.newdto.SearchRequest;
import org.kuali.student.core.search.newdto.SearchResult;
import org.kuali.student.core.validation.dto.ValidationResultInfo;

public class OrgProposalAssembler implements Assembler<Data, OrgHelper>{
    
    private OrganizationService orgService;
    private MetadataServiceImpl metadataService;
    public static final String PROPOSAL_TYPE_CREATE_ORG = "kuali.proposal.type.org.create";
    public static final String ORG_PROPOSAL_DATA_TYPE = "OrgProposal";

    public OrgProposalAssembler(){

    }
//    public OrgProposalAssembler(OrganizationService orgService,MetadataServiceImpl metadataService){
//        this.orgService=orgService;
//        this.metadataService=metadataService;
//    }
    
    @Override
    public Data assemble(OrgHelper input) throws AssemblyException {
        if (input == null) {
            return null;
        }
        Data result = new Data();
        
        return result;
    }

    @Override
    public OrgHelper disassemble(Data input) throws AssemblyException {
        if (input == null) {
            return null;
        }
//        OrgHelper result = new OrgHelper();
//        copyIfExists(input,result,"orgInfo");
        return null;
    }

    @Override
    public Data get(String id) throws AssemblyException {
        // TODO Neerav Agrawal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public Metadata getMetadata(String type, String state) throws AssemblyException {

        Metadata metadata = null;
        try{
        metadata = metadataService.getMetadata(ORG_PROPOSAL_DATA_TYPE,PROPOSAL_TYPE_CREATE_ORG,state);

        }
        catch(Exception e ){
            e.printStackTrace();
        }
        return metadata;
    }

    @Override
    public SaveResult<Data> save(Data input) throws AssemblyException {
        // TODO Neerav Agrawal - THIS METHOD NEEDS JAVADOCS
        OrgHelper orgHelper = OrgHelper.wrap((Data)input.get("orgInfo"));
        OrgInfoData orgInfoData = buildOrgInfo(orgHelper);
        SaveResult<Data> result = new SaveResult<Data>();
        List<ValidationResultInfo> validationResults = validate(input);
//        result.setValidationResults(validationResults);
        try {
            saveOrg(orgInfoData);   //orgInfoData contains the dto for OrgInfo
//            OrgHelper resultOrg = buildOrgDataMap(orgInfoData);   //this will create the Data Map for the returned OrgInfo dto
//            Data samp = new Data();
//            Data resultData = new Data();
//            resultData.set("orgInfo", resultOrg.getData());       //Set the map to the key "orgInfo"
            
            String orgId = orgInfoData.getOrgInfo().getId();
            orgHelper.setId(orgId);
            if(orgId!=null&&input.get("orgOrgRelationInfo")!=null){
//                OrgorgRelationHelper orgOrgRelation=  OrgorgRelationHelper.wrap(input);
                OrgOrgRelationAssembler orgOrgRelationAssembler = new OrgOrgRelationAssembler();
                orgOrgRelationAssembler.setOrgService(orgService);
                Data relationData = orgOrgRelationAssembler.save(input).getValue();

            }
            if(orgId!=null&&input.get("OrgPositionRestrictionInfo")!=null){
                OrgPositionRestrictionAssembler orgPositionRestrictionAssembler= new OrgPositionRestrictionAssembler();
                orgPositionRestrictionAssembler.setOrgService(orgService);
                Data positionData = orgPositionRestrictionAssembler.save(input).getValue();
            }
           
            result.setValue(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    public void setOrgService(OrganizationService service){
        orgService = service;
    }
    
    public void setMetadataService(MetadataServiceImpl metadataService) {
        this.metadataService = metadataService;
    }
    
    private OrgInfoData buildOrgInfo(OrgHelper org){
        OrgInfo orgInfo = new OrgInfo();
        OrgInfoData result = new OrgInfoData();
                
        orgInfo.setType(org.getType());
        orgInfo.setLongDesc(org.getDescription());
        orgInfo.setShortName(org.getAbbreviation());
        orgInfo.setLongName(org.getName());
        orgInfo.setEffectiveDate(org.getEffectiveDate());
        orgInfo.setExpirationDate(org.getExpirationDate());
        if(org.getId()!=null){
            orgInfo.setId(org.getId());
        }
        
        if (isModified(org.getData())) {
           if (isUpdated(org.getData())) {
                MetaInfo metaInfo = new MetaInfo();
                orgInfo.setMetaInfo(metaInfo);
                result.setModificationState(ModificationState.UPDATED);
            } else if (isDeleted(org.getData())) {
                result.setModificationState(ModificationState.DELETED);
            }
        }
        else{
            setCreated(org.getData(), true);
            result.setModificationState(ModificationState.CREATED);
        }
        if(orgInfo.getMetaInfo()!=null){
            orgInfo.getMetaInfo().setVersionInd(getVersionIndicator(org.getData()));
        }
//        result.setModificationState(ModificationState.CREATED);
        result.setOrgInfo(orgInfo);
        return result;
    }

    private void saveOrg(OrgInfoData input) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, VersionMismatchException {
        OrgInfo result = null;
        OrgInfo orgInfo = input.getOrgInfo();
        if (input.getModificationState() != null) {
            switch (input.getModificationState()) {
                case CREATED:
                    result = orgService.createOrganization(orgInfo.getType(), orgInfo);
                    break;
                case UPDATED:
                    result = orgService.updateOrganization(orgInfo.getId(), orgInfo);
                default:
            }
        }
        if (result != null) {
            input.setOrgInfo(result);
        }
    }

    @Override
    public List<ValidationResultInfo> validate(Data input) throws AssemblyException {
        // TODO Neerav Agrawal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    private <T extends Data> T copyIfExists(Data source, T target, String key) {
        Data d = source.get(key);
        if (d == null) {
            return null;
        } else {
            d.copy(target, false);
            source.set(key, target);
            return target;
        }
    }
    
    private OrgHelper buildOrgDataMap(OrgInfoData orgInfoData){
        OrgHelper org =  OrgHelper.wrap(new Data());
        org.setId(orgInfoData.getOrgInfo().getId());
        org.setType(orgInfoData.getOrgInfo().getType());
        org.setName(orgInfoData.getOrgInfo().getLongName());
        org.setAbbreviation(orgInfoData.getOrgInfo().getShortName());
        org.setDescription(orgInfoData.getOrgInfo().getLongDesc());
        org.setEffectiveDate(orgInfoData.getOrgInfo().getEffectiveDate());
        org.setExpirationDate(orgInfoData.getOrgInfo().getExpirationDate());
        setUpdated(org.getData(), true);
        addVersionIndicator(org.getData(),OrgInfo.class.getName(),orgInfoData.getOrgInfo().getId(),orgInfoData.getOrgInfo().getMetaInfo().getVersionInd());

        return org;
    }

	@Override
	public SearchResult search(SearchRequest searchRequest) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Data fetchOrgInfo(Data orgSearch){
	    OrgSearchHelper orgSearchHelper = OrgSearchHelper.wrap((Data)orgSearch.get("orgSearchInfo"));
	    String orgId = orgSearchHelper.getOrgId();
	    OrgInfo orgInfo = new OrgInfo();
	    List<OrgPositionRestrictionInfo> positions = new ArrayList<OrgPositionRestrictionInfo>();
	    List<OrgOrgRelationInfo> relations = new ArrayList<OrgOrgRelationInfo>();
	    Data result = new Data();
//	    SaveResult<Data> result = new SaveResult<Data>();
	    try{
	        orgInfo = orgService.getOrganization(orgId);
	        OrgInfoData orgInfoData = new OrgInfoData();
	        orgInfoData.setOrgInfo(orgInfo);
	        OrgHelper resultOrg = buildOrgDataMap(orgInfoData);
	        OrgOrgRelationAssembler orgOrgRelationAssembler = new OrgOrgRelationAssembler();
	        orgOrgRelationAssembler.setOrgService(orgService);
	        OrgPositionRestrictionAssembler orgPositionRestrictionAssembler= new OrgPositionRestrictionAssembler();
            orgPositionRestrictionAssembler.setOrgService(orgService);
	        Data orgOrgRelationMap = orgOrgRelationAssembler.fetchOrgOrgRelationInfo(orgId);
	        Data orgPositionMap = orgPositionRestrictionAssembler.fetchOrgPositions(orgId);
	        result.set("orgInfo", resultOrg.getData());
	        result.set("orgOrgRelationInfo", orgOrgRelationMap);
	        result.set("OrgPositionRestrictionInfo", orgPositionMap);
	        
	    }
	    catch(Exception e){
	        
	    }
	    
	    return result;
	}
   
}
